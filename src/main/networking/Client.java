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
		System.out.println("New Client!");
		try {
			oos = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
			oos.flush();
			ois = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
			System.out.println("Intialized");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		System.out.println("Started!");
		try {
			while(true) {
				Packet p = (Packet) ois.readObject();
				System.out.println("Packet Recieved!");
				if(p.request.equalsIgnoreCase("position")) {
					System.out.print("And...");
					Integer x = (Integer) p.data.get("X");
					Integer y = (Integer) p.data.get("Y");
					oos.writeObject(retrieve(x,y));
					System.out.println(" Sent!");
				}
				else
					return;
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





