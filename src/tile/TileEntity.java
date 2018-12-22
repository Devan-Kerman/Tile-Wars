package tile;

import java.util.Map;

import util.Editor;

/**
 * Litterally just a tile with an improvment/thing
 * @author devan
 *
 */
public class TileEntity extends Tile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2129487004281541842L;
	public Improvement i;
	private Map<String, Object> data;

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
	@SuppressWarnings("unchecked")
	public <T> T getData(String key) {
		return (T) data.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public <T> void editData(String key, Editor<T> editor) {
		data.put(key, editor.edit((T) data.remove(key)));
	}
	
	public void deleteData(String key) {
		data.remove(key);
	}
	
}
