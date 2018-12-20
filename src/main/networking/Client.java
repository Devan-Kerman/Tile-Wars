package main.networking;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import generators.chunk.Chunk;
import main.Boot;
import main.Serverside;
import main.networking.updates.TileUpdate;
import tile.Improvement;
import tile.Tile;
import util.datamanagement.manager.ChunkManager;

public class Client implements Runnable {
	private Input ois;
	private Output oos;
	private static Kryo k;
	static {
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
	}
	private ArrayList<TileUpdate> edits;

	/*
	 * Op codes 0 cx -> cy -> <- 3x3 Chunk Array 1 <- Tile updates
	 */
	public Client(Socket s) throws IOException {
		Serverside.logger.info("New Client!");
		edits = new ArrayList<>();
		oos = new Output(s.getOutputStream());
		oos.flush();
		ois = new Input(s.getInputStream());
		Serverside.logger.info("Client Connection Established!");
	}
	int renderdistance = 1;
	public void run() {
		try {
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
				} else if(opcode == 2) {	
					renderdistance = k.readObject(ois, Integer.class);
				} else {
					Serverside.logger.warn("Connection Aborted!");
					return;
				}
			}
		} catch (Exception e) {
			Boot.mainet.clients.remove(this);
		}
		System.out.println(Thread.getAllStackTraces().keySet().size());
	}

	public Chunk[][] retrieve(int x, int y) {
		Chunk[][] cs = new Chunk[renderdistance*2+1][renderdistance*2+1];
		x -= renderdistance;
		y -= renderdistance;
		for (int s = 0; s < cs.length; s++)
			for (int d = 0; d < cs[0].length; d++)
				cs[s][d] = ChunkManager.safeChunk(x + s, y + d);
		return cs;
	}

	public void addUpdate(TileUpdate t) {
		edits.add(t);
	}
}
