package util.datamanagement;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.esotericsoftware.kryo.io.Input;
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
		write(c);
		return c;
	}

	/**
	 * This method will first check if the specified chunk is already in the cache
	 * if not, it checks in the ChunkData folder if found, it inputs it into the
	 * cache and returns it, if all else fails, it generates a brand new chunk, and
	 * it inputs it into the cache and returns it.
	 * 
	 * @param x The Chunk's x coordinate
	 * @param y The Chunk's y coordinate
	 * @return The Chunk at specified position
	 */
	public static Chunk safeChunk(final int x, final int y) {
		Chunk c;
		if (cache.containsKey(x, y))
			c = cache.get(x, y);
		else if (inDrive(x, y))
			try {c = read(x, y);} catch (Exception e) {e.printStackTrace();return null;}
		else
			c = gen(x, y);
		cache.put(c.x, c.y, c);
		return c;
	}

	public static Chunk safeChunk(Point p) {
		return safeChunk(p.x, p.y);
	}

	public static void saveIfDeCached(Chunk c) {
		if (!cache.containsKey(c.x, c.y))
			write(c);
	}

	private static boolean inDrive(final int x, final int y) {
		return new File("Chunkdata/[" + x + "," + y + "].chunk").exists();
	}

	/**
	 * Writes the chunk to disc
	 * 
	 * @param c The chunk that will be written to disk
	 */
	public static void write(final Chunk c) {
		try {
			String pathstrn = "Chunkdata/[" + c.x + "," + c.y + "].temp";
			Files.deleteIfExists(Paths.get(pathstrn));
			File f = new File(pathstrn);
			f.createNewFile();
			Output o = new Output(new FileOutputStream(f));
			GlobalData.kryo.writeObject(o, c);
			o.flush();
			o.close();
			Files.move(f.toPath(), new File("Chunkdata/[" + c.x + "," + c.y + "].chunk").toPath(), StandardCopyOption.REPLACE_EXISTING);
			Files.deleteIfExists(Paths.get(pathstrn));
		} catch (Exception e) {
			e.printStackTrace();
			// DLogger.error(e.getStackTrace()[2].toString());
		}
		DLogger.info("Wrote chunk " + c.x + "," + c.y + " to disk");
	}

	private static Chunk read(int x, int y) throws IOException {
		return GlobalData.kryo.readObject(new Input(new FileInputStream("Chunkdata/[" + x + "," + y + "].chunk")),
				Chunk.class);
	}
}
