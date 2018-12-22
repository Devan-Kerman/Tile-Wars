package game.resources;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import exceptions.NotEnoughResourcesException;

public class Inventory {

	public Map<Resource, Double> resources;

	/**
	 * This creates a new "Inventory" of resources using the Enums specified in
	 * game.resources.Resource
	 */
	public Inventory() {
		resources = new EnumMap<>(Resource.class);
	}

	/**
	 * This checks whether the specified item stack can be taken out of the
	 * inventory without throwing a {@link NotEnoughResourcesException}
	 * 
	 * @param r      The Enum of the resource
	 * @param amount The amount of the resource
	 * @return if the inventory has equal/surplus of that resource
	 */
	public boolean hasEnough(Resource r, double amount) {
		return resources.getOrDefault(r, 0d) >= amount;
	}

	/**
	 * This checks whether the specified item stack can be taken out of the
	 * inventory without throwing a {@link NotEnoughResourcesException}
	 * 
	 * @param stacks the item stack
	 * @return if the inventory has equal/surplus of that resource
	 */
	public boolean hasEnough(ItemStack stacks) {
		return resources.getOrDefault(stacks.r, 0d) >= stacks.amount;
	}

	/**
	 * This checks whether the specified {@link ItemStack}s can be taken out of the
	 * inventory without throwing a {@link NotEnoughResourcesException}
	 * 
	 * @param stacks The array of item stacks
	 * @return if the inventory has equal/surplus of those resources
	 */
	public boolean hasEnough(ItemStack[] stacks) {
		for (ItemStack is : stacks)
			if (!hasEnough(is))
				return false;
		return true;
	}

	/**
	 * Removes the specified resources from the inventory,
	 * throws a {@link NotEnoughResourcesException} if the target inventory
	 * does not contain enough resources
	 * @see {@link #hasEnough(ItemStack) hasEnough}, {@link #hasEnough(ItemStack[]) hasEnough}, {@link #hasEnough(Resource, double) hasEnough}
	 * 
	 * @param resource
	 * 		Enum of the resource
	 * @param amount
	 * 		Amount of the resource
	 */
	public void take(Resource resource, double amount) {
		if (amount <= 0)
			throw new IllegalArgumentException("Amount must be greater than zero");

		double current = resources.getOrDefault(resource, 0d);

		if (current >= amount)
			resources.put(resource, current - amount);
		else
			throw new NotEnoughResourcesException("Not enough " + resource + " current amount: " + current);
	}

	public void put(Resource resource, double amount) {
		resources.merge(resource, amount, (a, b) -> a + b);
	}

	public void putAll(List<ItemStack> resources) {
		for (ItemStack stack : resources)
			put(stack.r, stack.amount);
	}

	public void putAll(ItemStack[] resources) {
		for (ItemStack stack : resources)
			put(stack.r, stack.amount);
	}

	public void takeAll(List<ItemStack> resources) {
		for (ItemStack stack : resources)
			take(stack.r, stack.amount);
	}

	public void takeAll(ItemStack[] resources) {
		for (ItemStack stack : resources)
			take(stack.r, stack.amount);
	}

}
