package main.networking.updates;

import tile.Tile;

public class TileUpdate {
	public long tx;
	public long ty;
	public Tile t;

	public TileUpdate(long tx, long ty, Tile t) {
		super();
		this.tx = tx;
		this.ty = ty;
		this.t = t;
	}

}
