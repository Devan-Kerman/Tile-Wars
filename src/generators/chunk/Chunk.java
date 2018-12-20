package generators.chunk;

import java.io.Serializable;

import generators.terrain.Generator;
import tile.Tile;

public class Chunk implements Serializable {
	public static final int CHUNKSIZE = 100;
	private static final long serialVersionUID = 7297134163391801440L;
	public Tile[][] data;
	public int x, y;
	
	public Chunk(final int cx, final int cy) {
		this.data = new Tile[CHUNKSIZE][CHUNKSIZE];
		for (int r = 0; r < CHUNKSIZE; r++)
			for (int c = 0; c < CHUNKSIZE; c++)
				data[r][c] = Generator.generate(cx * CHUNKSIZE + x, cy * CHUNKSIZE + y);
		this.x = cx;
		this.y = cy;
	}
}
