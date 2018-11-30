package generators.chunk;

import java.io.Serializable;

import generators.terrain.Generator;
import tile.Tile;

public class Chunk implements Serializable {
	public static final int chunksize = 100;
	private static final long serialVersionUID = 7297134163391801440L;
	public Tile[][] data;
	public int x, y;
	
	public Chunk(final int cx, final int cy) {
		this.data = new Tile[chunksize][chunksize];
		for (int x = 0; x < chunksize; x++)
			for (int y = 0; y < chunksize; y++)
				data[x][y] = Generator.generate(cx * chunksize + x, cy * chunksize + y);
		this.x = cx;
		this.y = cy;
	}
}
