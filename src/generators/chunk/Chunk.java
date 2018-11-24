package generators.chunk;

import generators.terrain.Generator;
import tile.Tile;

public class Chunk {
	public Tile[][] data;
	public static int chunksize = 100;
	public Chunk(int cx, int cy) {
		data = new Tile[chunksize][chunksize];
		for(int x = 0; x < chunksize; x++)
			for (int y = 0; y < chunksize; y++)
				data[x][y] = Generator.generate(cx*chunksize + x, cy*chunksize + y);
	}
}
