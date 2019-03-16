package play.ai.devtech.control;

import play.ai.devtech.core.world.chunk.Chunk;
import play.ai.devtech.core.world.tile.LocalPoint;
import play.ai.devtech.core.world.tile.Tile;
import play.ai.devtech.core.world.tile.TileEntity;

public class Tiles {
	
	/**
	 * Converts the location to a string, includes the Tile Entity and the actual tile
	 * @param c
	 * @param point
	 * @return
	 */
	public static String toString(Chunk c, LocalPoint point) {
		Tile t = c.get(point);
		TileEntity te = c.tileEnts.at(point.x, point.y);
		return "\n"+((t==null)?"null":t.toString()) + "\nTE: " + ((te==null)?"null":te.toString());
	}
}
