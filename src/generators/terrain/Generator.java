package generators.terrain;

import generators.math.CurveGen;
import tile.Tile;

public class Generator {
	public final static Simplexor TG = new Simplexor(7, 0.6f, 0.0050f); // Terrain generator
	public final static Simplexor HG = new Simplexor(7, 0.6f, 0.0050f); // Humidity generator

	public static final CurveGen IRONGEN = new CurveGen(10, 10);
	public static final CurveGen BAUXGEN = new CurveGen(10, 100);
	public static final CurveGen TINGEN = new CurveGen(10, -50);
	public static final CurveGen COALGEN = new CurveGen(10, 150);
	public static final CurveGen OILGEN = new CurveGen(10, -150);

	public static Tile generate(int x, int y) {
		Tile t = new Tile();
		t.setElevation((byte) (TG.genPoint(x, y) * 125));
		t.setIronOre((byte) IRONGEN.calculate(t.getElevation()));
		t.setBauxiteOre(BAUXGEN.calculate(t.getElevation()));
		t.setTinOre(TINGEN.calculate(t.getElevation()));
		t.setGoldOre((byte) t.getTinOre());
		t.setSilverOre((byte) t.getGoldOre());
		t.setCoalOre((byte) COALGEN.calculate(t.getElevation()));
		t.setPlatinumOre(t.getGoldOre());
		t.setNaturalGas((byte) t.getCoalOre());
		t.setOil(OILGEN.calculate(t.getElevation()));
		t.setGems((byte) t.getOil());
		if (t.getElevation() < -25)
			t.setWildlife((byte) 50);
		else if (t.getElevation() < 25 && t.getElevation() >= -25)
			t.setWildlife((byte) 15);
		else {
			t.setWildlife((byte) (HG.genPoint(x, y) * 25));
			t.setLumber((byte) (t.getWildlife() * Math.sqrt(Math.random())));
		}
		t.setHumidity(t.getWildlife());
		return t;
	}
}
