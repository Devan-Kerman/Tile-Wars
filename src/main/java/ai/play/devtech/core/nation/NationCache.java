package ai.play.devtech.core.nation;

import org.cache2k.Cache2kBuilder;
import org.cache2k.IntCache;
import ai.play.devtech.core.api.io.Input;
import ai.play.devtech.core.api.io.Output;
import ai.play.devtech.tilewars.DLogger;
import java.io.*;
import java.nio.file.Files;

/**
 * Used as a cache for nations, holds a buffer of 100 nations, and automatically
 * writes them to disk if the buffer overflows. All methods are static
 * 
 * @author devan
 *
 */
public class NationCache {

	static IntCache<Nation> cache;
	
	static {
		Cache2kBuilder<Integer, Nation> builder = new Cache2kBuilder<Integer, Nation>() {};
		builder.loader(i -> new Input(new BufferedInputStream(new FileInputStream(new File("NationData/" + i + ".nation")))).read(Nation.class));
		builder.entryCapacity(1000);
		cache = builder.buildForIntKey();
	}

	/**
	 * Will first search the cache if the target ID exists in the cache, if not it
	 * attempts to read from the disk
	 * 
	 * @param id The id of the desired nation
	 * @return The Nation Object
	 */
	public static Nation getNation(int id) {
		return cache.get(id);
	}

	/**
	 * Returns a list of the ids of all the currently saved nations, do not use
	 * often, not very fast
	 * 
	 * @return int[] id list
	 */
	public static int[] allNations() {
		File[] files = new File("NationData/").listFiles();
		if (files == null) {
			DLogger.warn("there are no nations in the NationData directory, did you make sure to delete pass.dnt?");
			return new int[0];
		}
		int[] ids = new int[files.length];
		if (ids.length == 0)
			DLogger.warn("there are no nations in the NationData directory, did you make sure to delete pass.dnt?");
		for (int c = 0; c < ids.length; c++) {
			String name = files[c].getName();
			ids[c] = Integer.parseInt(name.substring(0, name.indexOf('.')));
		}
		return ids;
	}

	/**
	 * Creates a brand new nation with the starting inventory
	 * 
	 * @return
	 */
	public static Nation newNation() {
		Nation n = new Nation(true);
		cache.put(n.id, n);
		return n;
	}

	/**
	 * This class is for static methods only
	 */
	private NationCache() {
	}
	
	public static Nation save(Nation n) {
		try {
			File file = new File("NationData/" + n.id + ".nation");
			if (file.exists())
				Files.delete(file.toPath());
			file.createNewFile();
			Output out = new Output(new BufferedOutputStream(new FileOutputStream(file), 512));
			out.write(n);
			out.push();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return n;
	}
	
	public static void saveAll() {
		cache.asMap().forEach((i, n) -> save(n));
	}
	
}
