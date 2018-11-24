package game.resources;

public class Resource {
	private final String resourceID;
	private int amount;
	
	public Resource(final String resourceID, final int amount) {
		this.resourceID = resourceID;
		this.setAmount(amount);
	}

	public String getResourceID() {
		return resourceID;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(final int amount) {
		this.amount = amount;
	}
}
