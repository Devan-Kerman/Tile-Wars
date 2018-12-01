package game.resources;
public class ItemStack {
		
		public Resource r;
		public ItemStack(Resource r, int amount) {
			super();
			this.r = r;
			this.amount = amount;
		}

		public int amount;
	
		public ItemStack() {
			
		}

	}