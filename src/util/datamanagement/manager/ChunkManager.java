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
import tile.Tile;
import util.datamanagement.ChunkCache;

public class ChunkManager {
	private static ChunkCache cache = new ChunkCache();
	public static void clear() {
		cache = new ChunkCache();
	}
	
	public static Chunk gen(final int x, final int y) {
		Chunk c = new Chunk(x, y);
		cache.put(x, y, c);
		write(c);
		return c;
	}
	
	public static Chunk getChunk(final int x, final int y) {
		return cache.get(x, y);
	}
	
	public static boolean inChunks(final int x, final int y) {
		return cache.containsKey(x, y);
	}
	
	public static Tile getTile(int tx, int ty) {
		Chunk c = getChunk(tx/Chunk.chunksize, ty/Chunk.chunksize);
		return c.getData()[tx%Chunk.chunksize][ty%Chunk.chunksize];
	}
	
	public static boolean inDrive(int x, int y) {
		return new File("Chunkdata/["+x+","+y+"].chunk").exists();
	}
	
	public static void write(Chunk c) {
		try {
			File f = new File("Chunkdata/["+c.getX()+","+c.getY()+"].temp");
			f.delete();
			f.createNewFile();
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f), 300000));
			oos.writeObject(c);
			oos.close();
			f.renameTo(new File("Chunkdata/["+c.getX()+","+c.getY()+"].chunk"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(new File("Chunkdata/["+c.getX()+","+c.getY()+"].chunk").exists())
			System.out.println("Chunk ["+c.getX()+","+c.getY()+"] was successfully saved");
	}
	public static Chunk read(int x, int y) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Chunkdata/["+x+","+y+"].chunk"), 300000));
		Chunk c = (Chunk) ois.readObject();
		ois.close();
		System.out.println("Chunk: ["+x+","+y+"] was found and retrieved");
		return c;
	}
}
