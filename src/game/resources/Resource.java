package game.resources;

public class Resource {
	public final String resourceID;
	public int amount;
	
	public Resource(final String resourceID, final int amount) {
		this.resourceID = resourceID;
		this.amount = amount;
	}

}
