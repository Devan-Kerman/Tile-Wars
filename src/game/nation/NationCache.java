package game.nation;

import java.io.File;
import java.util.Map;

import util.datamanagement.MaxMap;

public class NationCache {

	static Map<Integer, Nation> nations;
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
	public static Nation getNation(int id) {
		if(!nations.containsKey(id)) {
			Nation n = Nation.getNation(id);
			nations.put(id, n);
			return n;
		}
		else 
			return nations.get(id);
	}
	public static int[] allNations() {
		File[] files = new File("NationData/").listFiles();
		int[] ids = new int[files.length];
		for (int c = 0; c < ids.length; c++) {
			String name = files[c].getName();
			ids[c] = Integer.parseInt(name.substring(0, name.indexOf('.')));
		}
		return ids;
	}
	
	
	private NationCache() {}
}
