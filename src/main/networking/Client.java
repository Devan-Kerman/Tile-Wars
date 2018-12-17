package main.networking;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import generators.chunk.Chunk;
import main.Boot;
import main.networking.updates.TileUpdate;
import tile.Improvement;
import tile.Tile;
import util.datamanagement.manager.ChunkManager;

public class Client implements Runnable {
	private Input ois;
	private Output oos;
	private Kryo k;
	private ArrayList<TileUpdate> edits;
	/*
	 * Op codes 0 cx -> cy -> <- 3x3 Chunk Array 1 <- Tile updates
	 */
	public Client(Socket s) throws IOException {
		System.out.println("New Client!");
		edits = new ArrayList<>();
		k = new Kryo();
		k.register(Chunk[][].class);
		k.register(Chunk[].class);
		k.register(Chunk.class);
		k.register(Tile[].class);
		k.register(Tile[][].class);
		k.register(Tile.class);
		k.register(Improvement.class);
		k.register(Integer.class);
		k.register(TileUpdate.class);
		oos = new Output(s.getOutputStream());
		oos.flush();
		ois = new Input(s.getInputStream());
		System.out.println("Intialized");
	}

	public void run() {
		try {
			System.out.println("Started!");
			while (true) {
				Integer opcode = k.readObject(ois, Integer.class);
				if (opcode == 0) {
					Integer x = k.readObject(ois, Integer.class);
					Integer y = k.readObject(ois, Integer.class);
					k.writeObject(oos, retrieve(x, y));
					oos.flush();
				} else if (opcode == 1) {
					k.writeObject(oos, 0);
					oos.flush();
					for (TileUpdate tu : edits) {
						k.writeObject(oos, tu);
						oos.flush();
					}
					k.writeObject(oos, 1);
					edits.clear();
				} else {
					System.out.println("Connection Aborted!");
					return;
				}
			}
		} catch (Exception e) {Boot.mainet.clients.remove(this);}
		System.out.println(Thread.getAllStackTraces().keySet().size());
	}

	public Chunk[][] retrieve(int x, int y) {
		Chunk[][] cs = new Chunk[3][3];
		x -= 1;
		y -= 1;
		for (int s = 0; s < 3; s++)
			for (int d = 0; d < 3; d++)
				cs[s][d] = ChunkManager.safeChunk(x + s, y + d);
		return cs;
	}
	
	public void addUpdate(TileUpdate t) {
		edits.add(t);
	}
}
