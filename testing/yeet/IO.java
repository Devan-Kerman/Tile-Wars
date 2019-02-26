package yeet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import play.ai.devtech.core.util.DLogger;
import play.ai.devtech.core.util.math.Bytes;
import play.ai.devtech.network.Network;

public class IO {

	public static void main(String[] args) throws IOException {
		Network network = new Network(3456);
		
		Socket sock = new Socket("localhost", 3456);
		network.acceptClient();
		DLogger.debug("Accepted");
		OutputStream dos = new GZIPOutputStream(sock.getOutputStream(), 4096);
		dos.flush();
		InputStream dis = new GZIPInputStream(sock.getInputStream(), 4096);
		
		dos.write(Bytes.fromInt(0));
		dos.write(Bytes.fromInt(1));
		dos.write(new byte[] {1});
		dos.flush();
		byte[] f = new byte[4];
		int resp = dis.read(f);
		sock.close();
		System.out.println(resp+":"+Bytes.toInt(f));
	}

}
