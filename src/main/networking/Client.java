package main.networking;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import generators.chunk.Chunk;
import util.datamanagement.manager.ChunkManager;

public class Client implements Runnable {
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public Client(Socket s) {
		try {
			ois = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
			oos = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			while(true) {
				Packet p = (Packet) ois.readObject();
				if(p.request.equalsIgnoreCase("position")) {
					Integer x = (Integer) p.data.get("X");
					Integer y = (Integer) p.data.get("Y");
					oos.writeObject(retrieve(x,y));
				}
			}
		} catch(Exception e) {e.printStackTrace(); return;}
	}
	
	public Chunk[][] retrieve(int x, int y) {
		Chunk[][] cs = new Chunk[3][3];
		x -= 1;
		y -= 1;
		for(int s = 0; s < 3; s++)
			for(int d = 0; d < 3; d++)
				cs[s][d] = ChunkManager.safeChunk(x++, y++);
		return cs;
	}
	
}





