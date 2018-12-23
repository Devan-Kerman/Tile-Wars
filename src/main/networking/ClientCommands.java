package main.networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import game.GlobalData;
import game.nation.TilePoint;
import generators.chunk.Chunk;
import util.datamanagement.ChunkManager;

public class ClientCommands {
	private static Kryo k = GlobalData.kryo;
	private Input ois;
	private Output oos;
	int renderdistance = 1;
	private Client c;
	
	public ClientCommands(Client c) {
		ois = c.ois;
		oos = c.oos;
		this.c = c;
	}
	public void ping() {
		write(0);
	}
	
	public void changeRender() {
		int in = k.readObject(ois, Integer.class);
		if(in < 3) {
			renderdistance = in;
			write(0);
		} else
			write(1);
	}
	
	public void getChunks() {
		Integer x = k.readObject(ois, Integer.class);
		Integer y = k.readObject(ois, Integer.class);
		c.p.x = x;
		c.p.y = y;
		write(retrieve(x, y));
	}
	
	public void getUpdates() {
		write(0);
		for (TilePoint tu : c.edits)
			write(tu);
		write(1);
		c.edits.clear();
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
	public void write(Object o) {
		k.writeObject(oos, o);
		oos.flush();
	}

}
