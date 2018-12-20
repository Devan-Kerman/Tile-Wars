package util.datamanagement.manager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;

import generators.chunk.Chunk;
import util.datamanagement.ChunkCache;

public class ChunkManager {
	private ChunkManager() {}
	
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

	public static Chunk safeChunk(final int x, final int y) {
		if (cache.containsKey(x, y)) {
			return cache.get(x, y);
		} else if (inDrive(x, y)) try {
			return read(x, y);
		} catch(Exception e) {e.printStackTrace(); return null;}
		else {
			return gen(x, y);
		}
	}

	private static boolean inDrive(final int x, final int y) {
		return new File("Chunkdata/[" + x + "," + y + "].chunk").exists();
	}

	private static void write(final Chunk c) {
		try {
			File f = new File("Chunkdata/[" + c.x + "," + c.y + "].temp");
			Files.delete(f.toPath());
			f.createNewFile();
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f), 300000));
			oos.writeObject(c);
			oos.close();
			f.renameTo(new File("Chunkdata/[" + c.x + "," + c.y + "].chunk"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Chunk read(int x, int y) throws  IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Chunkdata/[" + x + "," + y + "].chunk"), 300000));
		Chunk c = (Chunk) ois.readObject();
		ois.close();
		return c;
	}
}
