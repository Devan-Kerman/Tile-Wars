package game.resources;

public class ItemStack {

	public Resource r;
	public double amount;

	public ItemStack(Resource r, double amount) {
		super();
		this.r = r;
		this.amount = amount;
	}
	
	/**
	 * Multiplies the current amount of resources by the multiplier
	 * @param multiplier
	 * 		the multiplier
	 */
	public void multiply(double multiplier) {
		amount*=multiplier;
	}
	
	/**
	 * Adds the current amount of resources by the given amount
	 * @param add
	 * 		the given amount
	 */
	public void add(double add) {
		amount += add;
	}
}