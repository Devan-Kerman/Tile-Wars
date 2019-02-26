package play.ai.devtech.control;

import play.ai.devtech.core.api.area.BPoint;
import play.ai.devtech.core.world.chunk.Chunk;
import play.ai.devtech.core.world.tile.Tile;
import play.ai.devtech.core.world.tile.TileEntity;

public class Tiles {
	public static String toString(Chunk c, BPoint point) {
		Tile t = c.get(point);
		TileEntity te = c.tileEnts.at(point.x, point.y);
		return "\n"+((t==null)?"null":t.toString()) + "\nTE: " + ((te==null)?"null":te.toString());
	}
}
