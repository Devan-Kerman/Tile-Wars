package game.resources;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import exceptions.NotEnoughResourcesException;

public class Inventory {
	

	public Map<Resource, Integer> resources;

	public Inventory() {
		resources = new EnumMap<>(Resource.class);
	}

	public ItemStack take(Resource resource, int amount) {
		if(amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
		}
		
		int current = resources.getOrDefault(resource, 0);
		if (current > amount) {
			resources.put(resource, current - amount);
			return new ItemStack(resource, amount);
		} else
			throw new NotEnoughResourcesException("Not enough " + resource + " current amount: " + current);
	}

	public void put(Resource resource, int amount) {
		resources.merge(resource, amount, (a, b) -> a + b);
	}

	public void putAll(List<ItemStack> resources) {
		for (ItemStack stack: resources) {
			put(stack.r, stack.amount);
		}
	}
}
