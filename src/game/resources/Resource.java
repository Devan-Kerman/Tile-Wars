package game.resources;

public class Resource {
<<<<<<< HEAD
<<<<<<< HEAD
	public String resourceID;
	public int amount;

	public Resource(String resourceID, final int amount) {
=======
=======
>>>>>>> parent of 8d1b4b2... Merge branch 'master' of https://github.com/Devan-Kerman/Tile-Wars
	private final String resourceID;
	private int amount;
	
	public Resource(final String resourceID, final int amount) {
>>>>>>> parent of 8d1b4b2... Merge branch 'master' of https://github.com/Devan-Kerman/Tile-Wars
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
