package net.devtech.common.world.chunks;

import net.devtech.common.world.chunks.tile.entity.TileEntity;
import net.devtech.common.world.chunks.tile.state.TileState;

/**
 * represents a chunk in memory
 */
public interface Chunk {
	/**
	 * gets the generated natural value of the coordinate
	 * @return the natural
	 */
	int natural(int x, int y);

	/**
	 * gets the tile state at that location
	 * @param x the relative x coordinate
	 * @param y the relative y coordinate
	 * @return the tile state at the location
	 */
	TileState getState(int x, int y);

	/**
	 * gets the tile entity at that location
	 * @param x the relative x coordinate
	 * @param y the relative y coordinate
	 * @return the tile entity at the given location, or null
	 */
	TileEntity getEntity(int x, int y);
}
