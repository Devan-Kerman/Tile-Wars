package generators.chunk;

import java.awt.Point;
import java.io.Serializable;

import generators.terrain.Generator;
import tile.Tile;

public class Chunk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7297134163391801440L;
	public Tile[][] data;
	public static int chunksize = 500;
	public Point coors;
	
	public Chunk(int cx, int cy) {
		data = new Tile[chunksize][chunksize];
		for(int x = 0; x < chunksize; x++)
			for (int y = 0; y < chunksize; y++)
				data[x][y] = Generator.generate(cx*chunksize + x, cy*chunksize + y);
		coors = new Point(cx,cy);
	}
	public static long longerHashCode(int x, int y) {
		return ((long)x<<32)+y;
	}
}
