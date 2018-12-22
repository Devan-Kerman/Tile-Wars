package tile;

import game.nation.Nation;
import game.resources.ItemStack;

public abstract class Improvement {
	protected Nation owner;
	protected int tier;
	protected TileEntity tile;
	
	public Improvement(Nation owner, TileEntity tile) {
		this.owner = owner;
		tier = 0;
		tile.i = this;
		this.tile = tile;
	}
	
	/**
	 * Runs the tiles execution method
	 */
	public abstract void execute();
	
	/**
	 * 
	 */
	public abstract void demolish();
	
	/**
	 * Returns whether or not the improvment needs to be "ticked"
	 * @return
	 * 		true/false
	 */
	public abstract boolean tickable();
	
	/**
	 * Gets the cost required to get the first tier
	 * @return
	 *		{@link ItemStack} array of costs
	 */
	public abstract ItemStack[] defaultCost();
	
	/**
	 * Increases the tier of the improvement, simply ignores the request if there is not enough resources
	 */
	public abstract void upgrade();
	
	/**
	 * Gets the cost of improving the structure 1 more tier
	 * @return
	 * 		The resources required ({@link ItemStack} array)
	 */
	public abstract ItemStack[] upgradeCost();
	
	public abstract int getTier();
	
}
