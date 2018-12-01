package main.networking;

import java.io.IOException;
import java.net.Socket;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import generators.chunk.Chunk;
import tile.Improvement;
import tile.Tile;
import util.datamanagement.manager.ChunkManager;

public class Client implements Runnable {
	private Input ois;
	private Output oos;
	private Kryo k;
	
	public Client(Socket s) {
		System.out.println("New Client!");
		try {
			k = new Kryo();
			k.register(Chunk[][].class);
			k.register(Chunk[].class);
			k.register(Chunk.class);
			k.register(Tile[].class);
			k.register(Tile[][].class);
			k.register(Tile.class);
			k.register(Improvement.class);
			oos = new Output(s.getOutputStream());
			oos.flush();
			ois = new Input(s.getInputStream());
			System.out.println("Intialized");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		System.out.println("Started!");
		try {
			while(true) {
				Integer opcode = k.readObject(ois, Integer.class);
				System.out.println("Packet Recieved!");
				if(opcode == 0) {
					System.out.print("And...");
					Integer x = k.readObject(ois, Integer.class);
					Integer y = k.readObject(ois, Integer.class);
					k.writeObject(oos, retrieve(x,y));
					oos.flush();
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





