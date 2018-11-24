package util.datamanagement.manager;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import generators.chunk.Chunk;
import gnu.trove.map.hash.TIntObjectHashMap;
import tile.Tile;

public class ChunkManager {
	public static TIntObjectHashMap<TIntObjectHashMap<Chunk>> chunks = new TIntObjectHashMap<>();
	public static void clear() {
		chunks = new TIntObjectHashMap<>();
	}
	
	public static Chunk gen(int x, int y) {
		Chunk c = new Chunk(x, y);
		if(chunks.containsKey(x))
			chunks.get(x).put(y, c);
		else {
			TIntObjectHashMap<Chunk> hm = new TIntObjectHashMap<>();
			hm.put(y, c);
			chunks.put(x, hm);
		}
		write(c);
		return c;
	}
	
	public static long longerHashCode(int x, int y) {
		long newx = x >> 32;
		return newx+y;
	}
	
	public static Chunk getUnsafe(int x, int y) {
		return chunks.get(x).get(y);
	}
	public static boolean inChunks(int x, int y) {
		return chunks.containsKey(x) && chunks.get(x).containsKey(y);
	}
	public static Chunk getSafe(int x, int y) {
		Chunk c;
		if(inChunks(x,y)) {
			c = getUnsafe(x,y);
		}
		else if(inDrive(x,y)) {
			try {
				c = read(x,y);
			} catch(Exception e) {
				c = null;
			}
		}
		else {
			c = gen(x,y);
		}
		
		return c;
	}
	
	public static Tile getTile(int tx, int ty) {
		Chunk c = getSafe(tx/Chunk.chunksize, ty/Chunk.chunksize);
		return c.data[tx%Chunk.chunksize][ty%Chunk.chunksize];
	}
	
	public static boolean inDrive(int x, int y) {
		return new File("Chunkdata/["+x+","+y+"].chunk").exists();
	}
	
	public static void write(Chunk c) {
		try {
			File f = new File("Chunkdata/["+c.coors.x+","+c.coors.y+"].temp");
			f.delete();
			f.createNewFile();
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f), 300000));
			oos.writeObject(c);
			oos.close();
			f.renameTo(new File("Chunkdata/["+c.coors.x+","+c.coors.y+"].chunk"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(new File("Chunkdata/["+c.coors.x+","+c.coors.y+"].chunk").exists())
			System.out.println("Chunk ["+c.coors.x+","+c.coors.y+"] was successfully saved");
	}
	public static Chunk read(int x, int y) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Chunkdata/["+x+","+y+"].chunk"), 300000));
		Chunk c = (Chunk) ois.readObject();
		ois.close();
		System.out.println("Chunk: ["+x+","+y+"] was found and retrieved");
		return c;
	}
}
