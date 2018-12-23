package main.networking;

import game.nation.TilePoint;
import tile.Tile;

public class TileUpdate {

	public TilePoint point;
	public Tile tile;
	/**
	 * Used to send updates to the client if they need it
	 * @param point
	 * 		Position of update
	 * @param tile
	 * 		Tile of data
	 */
	public TileUpdate(TilePoint point, Tile tile) {
		super();
		this.point = point;
		this.tile = tile;
	}
}
