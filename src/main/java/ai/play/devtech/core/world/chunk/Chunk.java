package ai.play.devtech.core.world.chunk;

import ai.play.devtech.core.world.entity.Entity;
import ai.play.devtech.core.world.tile.LocalPoint;
import ai.play.devtech.core.world.tile.Tile;
import ai.play.devtech.core.world.tile.TileEntity;
import ai.play.devtech.core.api.interfaces.Assembable;
import ai.play.devtech.core.api.interfaces.Packetable;
import ai.play.devtech.core.api.io.ByteReader;
import ai.play.devtech.core.api.io.Packer;
import ai.play.devtech.core.world.chunk.generation.Generator;

public class Chunk implements Packetable, Assembable {
	/**
	 * The dimensions of the chunk (size x size)
	 */
	public static final int CHUNKSIZE = 100;

	public final Tile[][] tiles;

	public ObjectMap<TileEntity> tileEnts;
	public ObjectMap<Entity> entities;

	public int cx, cy;

	/**
	 * Sets itsef as a brand new chunk at the specified coordinate, This is not
	 * meant to be used anywhere other than in {@linkplain ChunkManager}, or for
	 * testing
	 * 
	 * @param cx Chunk x coordinate
	 * @param cy Chunk y coordinate
	 */
	public Chunk(final int cx, final int cy) {
		this();
		this.cx = cx;
		this.cy = cy;
		for (int r = 0; r < CHUNKSIZE; r++)
			for (int c = 0; c < CHUNKSIZE; c++)
				tiles[r][c] = Generator.generate(cx * CHUNKSIZE + (long) r, cy * CHUNKSIZE + (long) c);
	}

	/**
	 * For serialization! do not use!!
	 */
	public Chunk() {
		tiles = new Tile[CHUNKSIZE][CHUNKSIZE];
		entities = new ObjectMap<>(Entity.class);
		tileEnts = new ObjectMap<>(TileEntity.class);
	}

	@Override
	public void pack(Packer packer) {
		for (Tile[] row : tiles)
			for (Tile t : row)
				t.pack(packer);
		packer.autoPack(tileEnts, entities);
	}

	@Override
	public void from(ByteReader reader) {
		for (int x = 0; x < tiles.length; x++)
			for (int y = 0; y < tiles[x].length; y++)
					tiles[x][y] = reader.read(Tile.class);
		tileEnts.from(reader);
		entities.from(reader);
	}
	
	public Tile get(byte x, byte y) {
		return tiles[x][y];
	}
	
	public Tile get(LocalPoint b) {
		return tiles[b.x][b.y];
	}
}
