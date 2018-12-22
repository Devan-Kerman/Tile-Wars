package game.resources;

public class ItemStack {

	public Resource r;
	public double amount;

	public ItemStack(Resource r, double amount) {
		super();
		this.r = r;
		this.amount = amount;
	}
	
	public void multiply(double multiplier) {
		this.amount*=multiplier;
	}
}