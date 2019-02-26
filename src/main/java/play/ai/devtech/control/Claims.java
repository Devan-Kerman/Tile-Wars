package play.ai.devtech.control;

import java.awt.Point;

import play.ai.devtech.core.api.area.BPoint;
import play.ai.devtech.core.world.chunk.ChunkManager;

public class Claims {
	public static void claim(Point ch, BPoint l, int nation) {
		ChunkManager.safeChunk(ch).get(l).ownerid = nation;
	}
}
