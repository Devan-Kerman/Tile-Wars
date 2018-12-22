package tile;

import java.util.Map;

import game.resources.Inventory;
import game.resources.ItemStack;

public interface Improvement {
	
	public void execute(Inventory i);

	public default boolean tickable() {
		return false;
	}

	public default boolean dataHolder() {
		return false;
	}

	public default Map<String, String> getData() {
		return null;
	}
	
	public static ItemStack[] defaultCost() {
		return new ItemStack[0];
	}
	
	public void upgrade(Inventory i);
	
	public ItemStack[] upgradeCost();
	
}
