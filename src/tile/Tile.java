package tile;

import java.io.Serializable;

public class Tile implements Serializable {
	private static final long serialVersionUID = 382610168546865107L;
	
	private int ownerid;
	private Improvement i;

	private byte elevation; // 'Z' (Elevation)
	private byte iron_ore;
	private byte bauxite_ore;
	private byte tin_ore;
	private byte copper_ore;
	private byte gold_ore;
	private byte silver_ore;
	private byte coal_ore;
	private byte platinum_ore;
	private byte nat_gas;
	private byte oil;
	private byte gems;
	private byte wildlife;
	private byte lumber;
	private byte humidity;

}
