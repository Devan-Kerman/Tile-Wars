package generators.terrain;

import tile.Tile;

public class Generator {
	public static Terrain tg = new Terrain(7, 0.6f, 0.0050f); //Terrain generator
	public static Terrain hg = new Terrain(7, 0.6f, 0.0050f); //Humidity generator
	
	public Tile generate(int x, int y) {
		Tile t = new Tile(x,y);
		return t;
	}
}

