package game.resources;

public class Resource {
	public String resourceID;
	public int amount;

	public Resource(String resourceID, final int amount) {
		this.resourceID = resourceID;
		this.amount = amount;
	}

}
