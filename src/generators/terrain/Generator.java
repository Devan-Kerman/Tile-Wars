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
		t.elevation = (byte) (TG.genPoint(x, y)*125);
		t.iron_ore = IRONGEN.calculate(t.elevation);
		t.bauxite_ore = BAUXGEN.calculate(t.elevation);
		t.tin_ore = TINGEN.calculate(t.elevation);
		t.gold_ore = t.tin_ore;
		t.silver_ore = t.gold_ore;
		t.coal_ore = COALGEN.calculate(t.elevation);
		t.platinum_ore = t.gold_ore;
		t.nat_gas = t.coal_ore;
		t.oil = OILGEN.calculate(t.elevation);
		t.gems = t.oil;
		if(t.elevation < -25)
			t.wildlife = 50;
		else if(t.elevation < 25 && t.elevation >= -25)
			t.wildlife = 15;
		else {
			t.wildlife = (byte) (HG.genPoint(x, y)*25);
			t.lumber = (byte) (t.wildlife*Math.sqrt(Math.random()));
		}
		t.humidity = t.wildlife;
		return t;
	}
}
