package generators.terrain;

import generators.math.CurveGen;
import tile.Tile;

public class Generator {
	public static Simplexor tg = new Simplexor(7, 0.6f, 0.0050f); //Terrain generator
	public static Simplexor hg = new Simplexor(7, 0.6f, 0.0050f); //Humidity generator
	
	public static CurveGen irongen = new CurveGen(10,10);
	public static CurveGen bauxgen = new CurveGen(10,100);
	public static CurveGen tingen = new CurveGen(10, -50);
	public static CurveGen coalgen = new CurveGen(10, 150);
	public static CurveGen oilgen = new CurveGen(10, -150);
	
	public static Tile generate(int x, int y) {
		Tile t = new Tile();
		t.elevation = (byte) (tg.genPoint(x, y)*150);
		t.iron_ore = irongen.calculate(t.elevation);
		t.bauxite_ore = bauxgen.calculate(t.elevation);
		t.tin_ore = tingen.calculate(t.elevation);
		t.gold_ore = t.tin_ore;
		t.silver_ore = t.gold_ore;
		t.coal_ore = coalgen.calculate(t.elevation);
		t.platinum_ore = t.gold_ore;
		t.nat_gas = t.coal_ore;
		t.oil = oilgen.calculate(t.elevation);
		t.gems = t.oil;
		if(t.elevation < -25)
			t.wildlife = 50;
		else if(t.elevation < 25 && t.elevation >= -25)
			t.wildlife = 15;
		else {
			t.wildlife = (byte) (hg.genPoint(x, y)*25);
			t.lumber = (byte) (t.wildlife*Math.sqrt(Math.random()));
		}
		t.humidity = t.wildlife;
		return t;
	}
}


