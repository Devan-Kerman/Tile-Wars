package game.resources;

import java.util.ArrayList;
import java.util.EnumMap;

import exceptions.NotEnoughResourcesException;

public class Inventory {
	public EnumMap<Resource, Integer> resources;

	public Inventory() {
		resources = new EnumMap<>(Resource.class);
	}

	public ItemStack take(Resource resource, int amount) throws NotEnoughResourcesException {
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

	public void putAll(ArrayList<ItemStack> resources) {
		for (ItemStack stack: resources) {
			put(stack.getResource(), stack.getAmount());
		}
	}
}
