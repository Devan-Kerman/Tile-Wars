package util.datamanagement;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;

import com.esotericsoftware.kryo.io.Output;

import game.GlobalData;
import generators.chunk.Chunk;
import main.DLogger;

public class ChunkManager {
	private ChunkManager() {
	}

	private static ChunkCache cache = new ChunkCache();

	public static void clear() {
		cache = new ChunkCache();
	}

	private static Chunk gen(final int x, final int y) {
		Chunk c = new Chunk(x, y);
		cache.put(x, y, c);
		write(c);
		return c;
	}
	/**
	 * This method will first check if the specified chunk is already in the cache
	 * if not, it checks in the ChunkData folder if found, it inputs it into the cache and returns it,
	 * if all else fails, it generates a brand new chunk, and it inputs it into the cache and returns it.
	 * 
	 * @param x
	 * 		The Chunk's x coordinate
	 * @param y
	 * 		The Chunk's y coordinate
	 * @return
	 * 		The Chunk at specified position
	 */
	public static Chunk safeChunk(final int x, final int y) {
		if (cache.containsKey(x, y)) {
			return cache.get(x, y);
		} else if (inDrive(x, y))
			try {
				return read(x, y);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		else {
			return gen(x, y);
		}
	}
	
	public static Chunk safeChunk(Point p) {
		return safeChunk(p.x, p.y);
	}
	
	public static void saveIfDeCached(Chunk c) {
		if(cache.containsKey(c.x, c.y))
			c.save();
	}
	
	private static boolean inDrive(final int x, final int y) {
		return new File("Chunkdata/[" + x + "," + y + "].chunk").exists();
	}
	
	/**
	 * Writes the chunk to disc
	 * @param c
	 * 	The chunk that will be written to disk
	 */
	public static void write(final Chunk c) {
		try {
			File f = new File("Chunkdata/[" + c.x + "," + c.y + "].temp");
			if (f.exists())
				Files.delete(f.toPath());
			f.createNewFile();
			GlobalData.kryo.writeObject(new Output(new FileOutputStream(f)), c);
			f.renameTo(new File("Chunkdata/[" + c.x + "," + c.y + "].chunk"));
		} catch (Exception e) {
			DLogger.error(e.getMessage());
		}
	}

	private static Chunk read(int x, int y) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream("Chunkdata/[" + x + "," + y + "].chunk"), 300000));
		Chunk c = (Chunk) ois.readObject();
		ois.close();
		return c;
	}
}
