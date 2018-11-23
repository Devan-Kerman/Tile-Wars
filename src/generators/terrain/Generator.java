package generators.terrain;

import tile.Tile;

public class Generator {
	public static Simplexor tg = new Simplexor(7, 0.6f, 0.0050f); //Terrain generator
	public static Simplexor hg = new Simplexor(7, 0.6f, 0.0050f); //Humidity generator
	
	
	public Tile generate(int x, int y) {
		Tile t = new Tile();
		t.elevation = (byte) (tg.genPoint(x, y)*125);
		
		
		return t;
	}
}


