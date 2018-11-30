package game.resources;

import java.util.Objects;

public class ItemStack {
	private final Resource resource;
	private int amount;

	public ItemStack(final Resource resource, final int amount) {
		Objects.requireNonNull(resource);
		this.resource = resource;
		this.setAmount(amount);
	}

	public Resource getResource() {
		return resource;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		if(amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
		}
		
		this.amount = amount;
	}	
}
