package ai.play.devtech.control;

import ai.play.devtech.core.world.chunk.ChunkManager;
import ai.play.devtech.core.world.tile.LocalPoint;
import java.awt.Point;

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
