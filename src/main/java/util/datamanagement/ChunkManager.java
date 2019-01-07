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

/**
 * Manages chunk cache use, generation, serialization and deserialization
 * @author devan
 *
 */
public class ChunkManager {
	/**
	 * Utility Class
	 */
	private ChunkManager() {
	}

	private static ChunkCache cache = new ChunkCache();

	/**
	 * Clears the cache
	 */
	public static void clear() {
		cache = new ChunkCache();
	}

	/**
	 * Generates a new chunk at the given position
	 * @param x
	 * 		chunk x (int)
	 * @param y
	 * 		chunk y (int)
	 * @return
	 * 		Chunk
	 */
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

	/**
	 * calls {@link #safeChunk(int, int)} using safeChunk
	 * @param p
	 * 		Point to get chunk
	 * @return
	 * 		Chunk at the point
	 */
	public static Chunk safeChunk(Point p) {
		return safeChunk(p.x, p.y);
	}

	/**
	 * If the chunk no longer exists in the cache, it will save it
	 * @param c
	 * 		The chunk
	 */
	public static void saveIfDeCached(Chunk c) {
		if (!cache.containsKey(c.x, c.y))
			write(c);
	}

	/**
	 * Checks if the specified chunk is in the drive
	 * @param x
	 * 		x coor
	 * @param y
	 * 		y coor
	 * @return
	 * 		true/false
	 */
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
			GlobalData.kryo.writeClassAndObject(o, c);
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

	
	public static void writeAll() {
		cache.map.forEach((hash, chunk) -> write(chunk));
	}
	
	/**
	 * Reads a chunk from the disk
	 * @param x
	 * 		chunk x
	 * @param y
	 * 		chunk y
	 * @return
	 * 		Chunk
	 * @throws IOException
	 */
	private static Chunk read(int x, int y) throws IOException {
		return (Chunk) GlobalData.kryo.readClassAndObject(new Input(new FileInputStream("Chunkdata/[" + x + "," + y + "].chunk")));
	}
}
