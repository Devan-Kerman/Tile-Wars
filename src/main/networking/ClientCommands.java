package main.networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import game.GlobalData;
import game.nation.NationCache;
import game.nation.TilePoint;
import generators.chunk.Chunk;
import util.datamanagement.ChunkManager;
import util.datamanagement.GenericDatabase;

public class ClientCommands {
	private static Kryo k = GlobalData.kryo;
	private Input ois;
	private Output oos;
	int renderdistance = 1;
	private Client c;
	private static GenericDatabase<Integer, String> passdb;
	static {
		passdb = new GenericDatabase<>("pass.dnt");
	}
	
	public ClientCommands(Client c) {
		ois = c.ois;
		oos = c.oos;
		this.c = c;
	}
	
	
	public void register() {
		c.n = NationCache.newNation();
		String salt = PasswordUtils.getSalt(30);
		passdb.put(c.n.id, PasswordUtils.generateSecurePassword(k.readObject(ois, String.class), salt) + ":" + salt);
		c.loggedin = true;
		write(c.n.id);
	}
	
	public void login() {
		int id = read(Integer.class);
		String pass = read(String.class);
		String[] args = passdb.get(id).split(":");
		if(PasswordUtils.verifyUserPassword(pass, args[0], args[1])) {
			 c.n = NationCache.getNation(id);
			 write(1);
			 c.loggedin = true;
		}
		else
			write(0);
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
	public <T> T read(Class<T> type) {
		T r = k.readObject(ois, type);
		oos.flush();
		return r;
	}

}
