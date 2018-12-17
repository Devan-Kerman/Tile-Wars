package tile;


import java.util.Map;

import game.resources.Nation;

public interface Improvement {
	public void execute(Nation n);
	public default boolean tickable() {
		return false;
	}
	public Map<String, String> getData();
}
