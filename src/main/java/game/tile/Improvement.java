package game.tile;

import java.io.Serializable;

import game.nation.Nation;
import game.resources.ItemStack;
import game.tile.Improvements.WoodCutter;

/**
 * An instance of this abstract class represents a building in the game
 * anything that extends it must implement these methods
 * @author devan
 *
 */
public abstract class Improvement implements Serializable {
	private static final long serialVersionUID = 5435731092896677938L;
	protected int tier;
	protected TileEntity tile;

	/**
	 * Creates a new improvement by the given ID
	 * @param id
	 * 		id of the improvement
	 * @return
	 * 		new instance of that improvement
	 */
	public static Improvement getImprovement(int id) {
		if (id == 0)
			return new WoodCutter();
		return new WoodCutter();
	}

	/**
	 * Null improvement, has no significant use other than super, doesn't do anything
	 */
	public Improvement() {
		tier = 0;
	}

	public abstract void setTile(TileEntity tile);

	/**
	 * Runs the tiles execution method
	 * @param n
	 * 		Nation to execute on
	 */
	public abstract void execute(Nation n);

	/**
	 * Demolishes the improvment, make sure to run this rather than just reasigning
	 * the Improvement, or old data might persist.
	 * @param n
	 * 		Nation to execute on
	 */
	public abstract void demolish(Nation n);

	/**
	 * Returns whether or not the improvment needs to be "ticked"
	 * 
	 * @return true/false
	 */
	public abstract boolean tickable();

	/**
	 * Gets the cost required to get the first tier
	 * 
	 * @return {@link ItemStack} array of costs
	 */
	public abstract ItemStack[] defaultCost();

	/**
	 * Increases the tier of the improvement, simply ignores the request if there is
	 * not enough resources
	 * @param n
	 * 		Nation to execute on
	 */
	public abstract void upgrade(Nation n);

	/**
	 * Gets the cost of improving the structure 1 more tier
	 * 
	 * @return The resources required ({@link ItemStack} array)
	 */
	public abstract ItemStack[] upgradeCost();

	/**
	 * Returns the tier of the improvment
	 * 
	 * @return (int)
	 */
	public abstract int getTier();
	
	public abstract boolean canRun(Nation n);

}
