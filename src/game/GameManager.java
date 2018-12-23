package game;

import game.nation.Nation;
import game.nation.TilePoint;
import generators.chunk.Chunk;
import main.DLogger;
import tile.Improvement;
import tile.TileEntity;
import util.datamanagement.ChunkManager;

public class GameManager {
	public static void addImprovement(Nation n, TilePoint point, Improvement i) {
		if (n.getInventory().hasEnough(i.defaultCost())) {
			Chunk c = ChunkManager.safeChunk(point.chunk);
			TileEntity t = new TileEntity(c.data[point.tx][point.ty]);
			t.demolishImprovment(n);
			n.getInventory().takeAll(i.defaultCost());
			i.setTile(t);
			t.i = i;
			t.ownerid = n.id;
			c.data[point.tx][point.ty] = t;
			n.addTilePoint(point);
			c.save();
			DLogger.debug("Added improvements!");
			// ChunkManager.saveIfDeCached(c);
		} else
			DLogger.warn("Not enough resources!");

	}

	public static void runNation(Nation n) {
		for (TilePoint point : n.tiles) {
			Chunk c = ChunkManager.safeChunk(point.chunk);
			TileEntity te = ((TileEntity) c.data[point.tx][point.ty]);
			if (te.i.canRun(n))
				te.i.execute(n);
			else
				DLogger.warn(n.id + " doesn't have enough resources to run tile entity @" + point.toString());
		}
		DLogger.debug("Run successful!");
	}

	private GameManager() {
	}
}
