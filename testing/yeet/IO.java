package yeet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import play.ai.devtech.core.util.DLogger;
import play.ai.devtech.core.util.math.Bytes;
import play.ai.devtech.network.Network;

public class IO {
	
	public static void main(String[] args) {
		Executor exec = Executors.newCachedThreadPool();
		exec.execute(() -> new Network(3456).acceptClient());
		exec.execute(() -> {
			Socket sock;
			try {
				sock = new Socket("localhost", 3456);
				if(sock.isConnected() && !sock.isClosed())
					DLogger.debug("Accepted");
				else
					DLogger.warn("Client is not connected");
				
				InputStream dis = new GZIPInputStream(sock.getInputStream(), 4096);
				OutputStream dos = new GZIPOutputStream(sock.getOutputStream(), 4096, true);
				dos.flush();
				
				dos.write(Bytes.fromInt(0));
				dos.write(Bytes.fromInt(1));
				dos.write(new byte[] {1});
				dos.flush();
				byte[] f = new byte[4];
				int resp = dis.read(f);
				System.out.println(resp+":"+Bytes.toInt(f));
				dos.close();
				sock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		});
		
		
	}

}
