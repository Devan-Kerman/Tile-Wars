package net.devtech.common.world.chunks.tile.state;


import org.jetbrains.annotations.NotNull;

/**
 * TileState should NEVER be null
 */
@NotNull
public interface TileState {
	TileState EMPTY = () -> 0;

	int tileType();
}
