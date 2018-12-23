package generators.chunk;

import java.io.Serializable;

import game.GlobalData;
import generators.terrain.Generator;
import tile.Tile;
import util.datamanagement.ChunkManager;

public class Chunk implements Serializable {
	private static final long serialVersionUID = 7297134163391801440L;
	public Tile[][] data;
	public int x, y;
	
	/**
	 * Sets itsef as a brand new chunk at the specified coordinate, This is not meant to be used
	 * anywhere other than in {@linkplain ChunkManager}, or for testing
	 * 
	 * @param cx
	 * 		Chunk x coordinate
	 * @param cy
	 * 		Chunk y coordinate
	 */
	public Chunk(final int cx, final int cy) {
		this.x = cx;
		this.y = cy;
		this.data = new Tile[GlobalData.CHUNKSIZE][GlobalData.CHUNKSIZE];
		for (int r = 0; r < GlobalData.CHUNKSIZE; r++)
			for (int c = 0; c < GlobalData.CHUNKSIZE; c++)
				data[r][c] = Generator.generate(cx * GlobalData.CHUNKSIZE + (long)r, cy * GlobalData.CHUNKSIZE + (long)c);
	}
	
	/**
	 * For serialization! do not use!!
	 */
	public Chunk() {
		super();
		data = new Tile[GlobalData.CHUNKSIZE][GlobalData.CHUNKSIZE];
	}
	
	public void save() {
		ChunkManager.write(this);
	}
}


