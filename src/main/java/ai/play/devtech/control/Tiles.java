package ai.play.devtech.control;

import ai.play.devtech.core.world.chunk.Chunk;
import ai.play.devtech.core.world.tile.*;


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
