package main.networking;

import game.nation.TilePoint;
import game.tile.Tile;

/**
 * Contains the data needed to send an update to a client, including the position and the new tile
 * @author devan
 *
 */
public class TileUpdate {

	public final TilePoint point;
	public final Tile tile;
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
