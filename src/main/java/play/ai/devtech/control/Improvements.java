package play.ai.devtech.control;

import java.awt.Point;

import play.ai.devtech.core.api.area.BPoint;
import play.ai.devtech.core.nation.Nation;
import play.ai.devtech.core.nation.NationCache;
import play.ai.devtech.core.world.chunk.Chunk;
import play.ai.devtech.core.world.tile.Tile;
import play.ai.devtech.core.world.tile.TileEntity;
import play.ai.devtech.tilewars.Serverside;

public class Improvements {
	
	public static void improve(BPoint point, Chunk c, int impid) {
		TileEntity ent = Serverside.teRegister.getInstance(impid);
		c.tileEnts.put(new Point(point.x, point.y), ent);
	}
	
	public static void set(BPoint point, Chunk c, int nationid) {
		Nation n = NationCache.getNation(nationid);
		Tile t = c.get(point);
		t.ownerid = n.id;
	}
	
	private Improvements() {/*Utility class*/}
}
