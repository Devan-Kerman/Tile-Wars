package game.tile;

import java.io.Serializable;

/**
 * This is 1 unit of space on the map, it represents roughly ~2.2 acres of space
 * @author devan
 *
 */
public class Tile implements Serializable {

	public static final long serialVersionUID = 382610168546865107L;
	// 24 bytes (16 + 8)
	/**
	 * Nation that has claimed it
	 */
	public int ownerid;
	/**
	 * The height of that tile's terrain
	 */
	public byte elevation; // 'Z' (Elevation)
	/**
	 * The amount of iron ore in the tile
	 */
	public byte ironOre;
	/**
	 * The amount of bauxite ore in the tile
	 */
	public byte bauxiteOre;
	/**
	 * The amount of tin ore in the tile
	 */
	public byte tinOre;
	/**
	 * The amount of copper ore in the tile
	 */
	public byte copperOre;
	/**
	 * The amount of gold ore in the tile
	 */
	public byte goldOre;
	/**
	 * The amount of silver ore in the tile
	 */
	public byte silverOre;
	/**
	 * The amount of coal ore in the tile
	 */
	public byte coalOre;
	/**
	 * The amount of platinum ore in the tile
	 */
	public byte platinumOre;
	/**
	 * The amount of natural gas in the tile
	 */
	public byte natGas;
	/**
	 * The amount of oil in the tile
	 */
	public byte oil;
	/**
	 * The amount of gems (diamonds, ruby, etc.) in the tile
	 */
	public byte gems;
	/**
	 * The amount of nature, life in the tile
	 */
	public byte wildlife;
	/**
	 * The amount of lumber, wood, trees, etc. in the tile
	 */
	public byte lumber;
	/**
	 * The humidity level of the tile
	 */
	public byte humidity;
	
	/**
	 * The amount of metalic ore (other metals, cobalt, uranium, etc.) in the tile
	 */
	public byte metalicOre;
	
}
