package ai.play.devtech.core.world.chunk;

import ai.play.devtech.core.api.io.Input;
import ai.play.devtech.core.api.io.Output;
import ai.play.devtech.tilewars.DLogger;
import java.awt.Point;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
		cache.put(c.cx, c.cy, c);
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
		if (!cache.containsKey(c.cx, c.cy))
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
			String pathstrn = "Chunkdata/[" + c.cx + "," + c.cy + "].temp";
			Files.deleteIfExists(Paths.get(pathstrn));
			File f = new File(pathstrn);
			f.createNewFile();
			Output o = new Output(new BufferedOutputStream(new FileOutputStream(f)));
			o.write(c);
			o.push();
			Files.move(f.toPath(), new File("Chunkdata/[" + c.cx + "," + c.cy + "].chunk").toPath(), StandardCopyOption.REPLACE_EXISTING);
			Files.deleteIfExists(Paths.get(pathstrn));
		} catch (Exception e) {
			e.printStackTrace();
			// DLogger.error(e.getStackTrace()[2].toString());
		}
		DLogger.info("Wrote chunk " + c.cx + "," + c.cy + " to disk");
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
		return new Input(new BufferedInputStream(new FileInputStream("Chunkdata/[" + x + "," + y + "].chunk"))).read(Chunk.class);
	}
}
