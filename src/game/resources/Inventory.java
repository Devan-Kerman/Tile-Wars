package game.resources;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import exceptions.NotEnoughResourcesException;

public class Inventory {
	

	public Map<Resource, Double> resources;

	public Inventory() {
		resources = new EnumMap<>(Resource.class);
	}
	public boolean hasEnough(Resource r, double amount) {
		return resources.getOrDefault(r, 0d) >= amount;
	}
	public boolean hasEnough(ItemStack r) {
		return resources.getOrDefault(r.r, 0d) >= r.amount;
	}
	public boolean hasEnough(ItemStack[] stacks) {
		for(ItemStack is : stacks)
			if(!hasEnough(is))
				return false;
		return true;
	}
	

	public ItemStack take(Resource resource, double amount) {
		if(amount <= 0)
			throw new IllegalArgumentException("Amount must be greater than zero");
		
		double current = resources.getOrDefault(resource, 0d);
		
		if (current >= amount) {
			resources.put(resource, current - amount);
			return new ItemStack(resource, amount);
		} else
			throw new NotEnoughResourcesException("Not enough " + resource + " current amount: " + current);
	}

	public void put(Resource resource, double amount) {
		resources.merge(resource, amount, (a, b) -> a + b);
	}

	public void putAll(List<ItemStack> resources) {
		for (ItemStack stack: resources)
			put(stack.r, stack.amount);
	}
	public void putAll(ItemStack[] resources) {
		for (ItemStack stack: resources)
			put(stack.r, stack.amount);
	}
	
	public void takeAll(List<ItemStack> resources) {
		for (ItemStack stack: resources)
			take(stack.r, stack.amount);
	}
	public void takeAll(ItemStack[] resources) {
		for (ItemStack stack: resources)
			take(stack.r, stack.amount);
	}
	
	
}
