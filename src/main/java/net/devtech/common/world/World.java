package net.devtech.common.world;

import net.devtech.common.world.chunks.Chunk;
import net.devtech.common.world.chunks.tile.entity.TileEntity;
import net.devtech.common.world.chunks.tile.state.TileState;
import java.util.concurrent.Future;

public interface World {
	/**
	 * gets the chunk at the specified coordinates immediately and waits for the result on the thread it was called on
	 * @param x the x coordinate of the chunk
	 * @param y the y coordinate of the chunk
	 * @return
	 */
	Chunk getChunkImmediately(int x, int y);

	/**
	 * gets the chunk on an async thread and fufils the future when done
	 * @param x the x of the chunk
	 * @param y the y of the chunk
	 * @return a future of the chunk
	 */
	Future<Chunk> getChunkAsync(int x, int y);

	/**
	 * gets the natural value of the location on the thread it was called on
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 * @return the natural
	 */
	int getNaturalImmediately(int x, int y);
	/**
	 * gets the natural on an async thread and fufils the future when done
	 * @param x the x of the natural
	 * @param y the y of the natural
	 * @return a natural of the chunk
	 */
	Future<Integer> getNaturalAsync(int x, int y);

	/**
	 * gets the TileState value of the location on the thread it was called on
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 * @return the TileState
	 */
	TileState getTileStateImmediately(int x, int y);
	/**
	 * gets the TileState on an async thread and fufils the future when done
	 * @param x the x of the TileState
	 * @param y the y of the TileState
	 * @return a TileState of the TileState
	 */
	Future<TileState> getTileStateAsync(int x, int y);

	/**
	 * gets the TileEntity value of the location on the thread it was called on
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 * @return the TileEntity
	 */
	TileEntity getTileEntityImmediately(int x, int y);
	/**
	 * gets the TileEntity on an async thread and fufils the future when done
	 * @param x the x of the TileEntity
	 * @param y the y of the TileEntity
	 * @return a future of the TileEntity
	 */
	Future<TileEntity> getTileEntityAsync(int x, int y);
}
