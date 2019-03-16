package play.ai.devtech.control;

import java.awt.Point;

import play.ai.devtech.core.world.chunk.ChunkManager;
import play.ai.devtech.core.world.tile.LocalPoint;

public class Claims {
	/**
	 * Claims the tile to that nation at the given point
	 * @param ch
	 * 		the chunks coordinates
	 * @param l
	 * 		the tile's relitive chunk coordinate
	 * @param nation
	 * 		the nation's id
	 */
	public static void claim(Point ch, LocalPoint l, int nation) {
		ChunkManager.safeChunk(ch).get(l).ownerid = nation;
	}
}
