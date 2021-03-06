package ai.play.devtech.control;

import ai.play.devtech.core.errors.ClaimException;
import ai.play.devtech.core.nation.NationCache;
import ai.play.devtech.core.nation.TilePoint;
import ai.play.devtech.core.nation.Nation;
import ai.play.devtech.core.world.chunk.Chunk;
import ai.play.devtech.core.world.chunk.ChunkManager;
import ai.play.devtech.core.world.tile.LocalPoint;
import ai.play.devtech.core.world.tile.Tile;
import ai.play.devtech.core.world.tile.TileEntity;
import ai.play.devtech.runtime.Game;
import java.awt.Point;

public class Improvements {
	
	/**
	 * Adds the improvement to the specified locations
	 * @param point
	 * 		the tiles relative location
	 * @param c
	 * 		the chunk u need to improve
	 * @param impid
	 * 		the improvement ID
	 */
	public static void improve(TilePoint point, Game game, int impid) {
		Chunk c = ChunkManager.safeChunk(point.chunk);
		TileEntity ent = game.instOfTE(impid);
		Tile tile = c.get(new LocalPoint(point.tx, point.ty));
		if(tile.ownerid == 0)
			throw new ClaimException("Tile not claimed!");
		Nation owner = NationCache.getNation(tile.ownerid);
		TilePoint tp = new TilePoint(new Point(c.cx, c.cy), point.tx, point.ty);
		owner.addTilePoint(tp);
		c.tileEnts.put(new Point(point.tx, point.ty), ent);
	}
	
	public static void improve(TilePoint point, Game game, int impid, int nation) {
		Chunk c = ChunkManager.safeChunk(point.chunk);
		TileEntity ent = game.instOfTE(impid);
		Tile tile = c.get(new LocalPoint(point.tx, point.ty));
		if(tile.ownerid == 0 || tile.ownerid != nation)
			throw new ClaimException("Tile not claimed!");
		Nation owner = NationCache.getNation(tile.ownerid);
		ent.build(owner);
		TilePoint tp = new TilePoint(new Point(c.cx, c.cy), point.tx, point.ty);
		owner.addTilePoint(tp);
		c.tileEnts.put(new Point(point.tx, point.ty), ent);
	}
	
	public static void destroy(TilePoint point, Game game, Nation n) {
		Chunk c = ChunkManager.safeChunk(point.chunk);
		int own = c.get(new LocalPoint(point.tx, point.ty)).ownerid;
		if(own != n.id)
			throw new ClaimException("Tile not owner!");
		n.removeTilePoint(new TilePoint(new Point(c.cx, c.cy), point.tx, point.ty));
		c.tileEnts.remove(new LocalPoint(point.tx, point.ty));
	}
	
	/**
	 * Claims the chunk?
	 * tbh idk why this is here
	 * @param point
	 * @param c
	 * @param nationid
	 */
	public static void set(LocalPoint point, Chunk c, int nationid) {
		Tile t = c.get(point);
		t.ownerid = nationid;
	}
	
	private Improvements() {/*Utility class*/}
}
