package play.ai.devtech.core.api.math;

import java.awt.Point;

import play.ai.devtech.core.world.tile.LocalPoint;

public class TileMath {
	public static Point getChunk(long x, long y) {
		return new Point((int) (Math.floorDiv(x, 100l)), (int) (Math.floorDiv(y, 100l)));
	}
	
	public static LocalPoint getArray(long x, long y) {
		byte mx = (byte) (x % 100), my = (byte) (y % 100);
		return new LocalPoint((byte)(mx < 0 ? mx + 100 : mx), (byte)(my < 0 ? my + 100 : my));
	}
}
