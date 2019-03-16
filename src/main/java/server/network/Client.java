package server.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import play.ai.devtech.core.api.io.Bytes;
import play.ai.devtech.core.nation.Nation;
import play.ai.devtech.runtime.Game;
import play.ai.devtech.tilewars.DLogger;

public class Client {

	private InputStream input;
	private OutputStream output;
	public Nation player;

	private boolean connected;

	private Map<Integer, BiFunction<byte[], Client, byte[]>> executors = new ConcurrentHashMap<>();
	private ExecutorService service = Executors.newSingleThreadExecutor();

	private Runnable kill;

	public void setKill(Runnable kill) {
		this.kill = kill;
	}

	public <T extends BiFunction<byte[], Client, byte[]>> void queue(int opcode, T executor) {
		executors.put(opcode, executor);
	}

	public Client(Socket connection) {
		connected = true;
		Operations.loadOperations(this);
		Game.oRegister.forEach((i, o) -> executors.put(i, Game.oRegister.getInstance(i)));
		try {
			if (connection.isClosed() || !connection.isConnected())
				throw new IOException("Closed connection");

			output = new GZIPOutputStream(connection.getOutputStream(), 4096, true);
			output.flush();
			DLogger.debug("Creating connection...");
			input = new GZIPInputStream(connection.getInputStream(), 4096);
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
						byte[] send = executors.get(code).apply(alloc, this);
						output.write(Bytes.fromInt(send.length));
						output.write(send);
						output.flush();
					} catch (IOException e) {
						connected = false;
						DLogger.info("Client disconnected: " + e.getMessage());
						DLogger.writeError(e);
						try {
							output.close();
							connection.close();
						} catch (IOException e1) {
							DLogger.writeError(e1);
						}
					}
				} else {
					if (kill != null)
						kill.run();
					service.shutdown();
					return;
				}
			}
		});
	}
}
