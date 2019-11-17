package net.devtech.common.world;

public interface WorldProvider {
	/**
	 * gets the world with the specified dimension
	 * @param dimension the dimension id
	 * @return the world or null if none found
	 */
	World getWorld(int dimension);
}
