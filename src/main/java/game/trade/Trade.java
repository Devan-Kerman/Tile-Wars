package game.trade;

import java.util.Comparator;

import game.resources.Resource;

public class Trade {
	public static final Comparator<Trade> PRICE_COMPARATOR = new PriceComaparator();
	
	private final Resource resource;
	private final int nationID;
	private int price, amount;
	
	public Trade(final Resource resource, final int nationID, final int price, final int amount) {
		this.resource = resource;
		this.nationID = nationID;
		if(price < 0)
			throw new IllegalArgumentException("Price must be greater than or equal to zero");
		
		this.price = price;
		if(amount <= 0) 
			throw new IllegalArgumentException("Amount must be greater than zero");
		
		this.amount = amount;
	}
	
	public boolean isFinished() {
		return amount == 0;
	}
	
	public void setAmount(final int amount) {
		if(amount < 0) 
			throw new IllegalArgumentException("Amount must be greater than or equal to zero");
		
		
		this.amount = amount;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		if(price < 0) 
			throw new IllegalArgumentException("Price must be greater than or equal to zero");
		
		this.price = price;
	}

	public Resource getResource() {
		return resource;
	}

	public int getAmount() {
		return amount;
	}

	public int getNationID() {
		return nationID;
	}
	
	static class PriceComaparator implements Comparator<Trade> {
		private PriceComaparator() {
			
		}
		
		@Override
		public int compare(Trade arg0, Trade arg1) {
			if(arg0.getPrice() != arg1.getPrice()) {
				return Integer.compare(arg0.getPrice(), arg1.getPrice());
			}
			
			if(arg0.getAmount() != arg1.getAmount()) {
				return Integer.compare(arg0.getAmount(), arg1.getAmount());
			}
			
			return 0;
		}
	}
}
