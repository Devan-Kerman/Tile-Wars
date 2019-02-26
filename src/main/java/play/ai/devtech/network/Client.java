package play.ai.devtech.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import play.ai.devtech.core.util.DLogger;
import play.ai.devtech.core.util.math.Bytes;

public class Client {

	private InputStream input;
	private OutputStream output;

	private boolean connected;

	private Map<Integer, Function<byte[], byte[]>> executors = new ConcurrentHashMap<>();
	private ExecutorService service = Executors.newSingleThreadExecutor();

	private Runnable kill;

	public void setKill(Runnable kill) {
		this.kill = kill;
	}

	public void queue(int opcode, Function<byte[], byte[]> executor) {
		executors.put(opcode, executor);
	}

	public Client(Socket connection) {
		Operations.loadOperations(this);
		try {
			if (connection.isClosed() || !connection.isConnected())
				throw new IOException("Closed connection");
			output = new GZIPOutputStream(connection.getOutputStream(), 4096);
			output.flush();
			InputStream is = connection.getInputStream();
			input = new GZIPInputStream(is, 4096);
			DLogger.debug("Established");	
		} catch (Exception e) {
			connected = false;
			DLogger.warn("Client failed to connect!");
			e.printStackTrace();
		}

		service.execute(() -> {
			byte[] i = new byte[4];
			while (true) {
				if (connected) {
					try {
						int bs = input.read(i);
						if (bs != 4)
							throw new IOException("Unable to recieve opcode");
						int code = Bytes.toInt(i);
						bs = input.read(i);
						if (bs != 4)
							throw new IOException("Unable to recieve packet length!");
						int len = Bytes.toInt(i);
						byte[] alloc = new byte[len];
						int ret = input.read(alloc);
						if (ret != len)
							throw new IOException("Packet loss!");
						byte[] send = executors.get(code).apply(alloc);
						output.write(Bytes.fromInt(send.length));
						output.write(send);
					} catch (IOException e) {
						connected = false;
						e.printStackTrace();
					}
				} else {
					kill.run();
					service.shutdown();
					return;
				}
			}
		});
	}
}
