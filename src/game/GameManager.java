package game;

import game.nation.Nation;
import game.nation.TilePoint;
import game.tile.Improvement;
import game.tile.TileEntity;
import generators.chunk.Chunk;
import main.DLogger;
import util.datamanagement.ChunkManager;

/**
 * Makes the world go round :D
 * (runs the game, execution on nations and imprvoement handleing)
 * @author devan
 *
 */
public class GameManager {
	/**
	 * Sets the improvement at the specified tile to a new one
	 * @param n
	 * 		The new owner
	 * @param point
	 * 		The location of the tile
	 * @param i
	 * 		The improvement to place
	 */
	public static void addImprovement(Nation n, TilePoint point, Improvement i) {
		if (n.getInventory().hasEnough(i.defaultCost())) {
			Chunk c = ChunkManager.safeChunk(point.chunk);
			TileEntity t = new TileEntity(c.data[point.tx][point.ty]);
			if(t.i != null)
				t.i.demolish(n);
			n.getInventory().takeAll(i.defaultCost());
			i.setTile(t);
			t.i = i;
			t.ownerid = n.id;
			c.data[point.tx][point.ty] = t;
			n.addTilePoint(point);
			DLogger.debug("Added improvements!");
			ChunkManager.saveIfDeCached(c);
		} else
			DLogger.warn("Not enough resources!");

	}

	/**
	 * Execution of a nation, runs through all improvements and adds the resources
	 * @param n
	 * 		nation to execute on
	 */
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

	/**
	 * This is a utility class
	 */
	private GameManager() {
	}
}
