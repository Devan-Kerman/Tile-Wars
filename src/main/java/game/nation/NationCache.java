package game.nation;

import java.io.File;
import java.util.Map;

import main.DLogger;
import util.datamanagement.MaxMap;

/**
 * Used as a cache for nations, holds a buffer of 100 nations, and automatically writes them to disk if the buffer overflows.
 * All methods are static
 * @author devan
 *
 */
public class NationCache {

	static final Map<Integer, Nation> nations;
	static {
		nations = new MaxMap<Integer, Nation>(100) {
			private static final long serialVersionUID = -2803494642119520703L;
			@Override
			protected boolean removeEldestEntry(Map.Entry<Integer, Nation> eldest) {
				if(size() > getMaxSize())
					eldest.getValue().save();
				return super.removeEldestEntry(eldest);
			}
		};
	}
	
	/**
	 * Will first search the cache if the target ID exists in the cache, if not it attempts to read from the disk
	 * @param id
	 * 		The id of the desired nation
	 * @return
	 * 		The Nation Object
	 */
	public static Nation getNation(int id) {
		if(!nations.containsKey(id)) {
			Nation n = Nation.getNation(id);
			nations.put(id, n);
			return n;
		}
		else 
			return nations.get(id);
	}
	
	/**
	 * Returns a list of the ids of all the currently saved nations, do not use often, not very fast
	 * @return
	 * 		int[] id list
	 */
	public static int[] allNations() {
		File[] files = new File("NationData/").listFiles();
		int[] ids = new int[files==null?files.length:0];
		if(ids.length==0) DLogger.warn("there are no nations in the NationData directory, did you make sure to delete pass.dnt?");
		for (int c = 0; c < ids.length; c++) {
			String name = files[c].getName();
			ids[c] = Integer.parseInt(name.substring(0, name.indexOf('.')));
		}
		return ids;
	}
	
	/**
	 * Creates a brand new nation with the starting inventory
	 * @return
	 */
	public static Nation newNation() {
		Nation n = new Nation(true);
		nations.put(n.id, n);
		return n;
	}
	
	/**
	 * This class is for static methods only
	 */
	private NationCache() {}
}
