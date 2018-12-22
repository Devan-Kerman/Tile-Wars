package tile;

public class TileEntity extends Tile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2129487004281541842L;
	public Improvement i;
	public int population;

	public TileEntity(Tile t, Improvement i) {
		elevation = t.elevation;
		ironOre = t.ironOre;
		bauxiteOre = t.bauxiteOre;
		tinOre = t.tinOre;
		copperOre = t.copperOre;
		goldOre = t.goldOre;
		silverOre = t.silverOre;
		coalOre = t.coalOre;
		platinumOre = t.platinumOre;
		natGas = t.natGas;
		oil = t.oil;
		gems = t.gems;
		wildlife = t.wildlife;
		lumber = t.lumber;
		humidity = t.humidity;
		ownerid = t.ownerid;
		this.i = i;
	}
}
