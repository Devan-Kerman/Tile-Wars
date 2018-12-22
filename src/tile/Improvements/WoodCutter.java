package tile.Improvements;

import game.resources.Inventory;
import game.resources.ItemStack;
import game.resources.Resource;
import tile.Improvement;
import tile.TileEntity;

public class WoodCutter implements Improvement {
	private TileEntity t;
	public int tier;
	private static ItemStack[][] upgradeCosts = new ItemStack[][] {
			new ItemStack[] { new ItemStack(Resource.MONEY, 1000) },
			new ItemStack[] { new ItemStack(Resource.MONEY, 2500), new ItemStack(Resource.RAWWOOD, 250) },
			new ItemStack[] { new ItemStack(Resource.MONEY, 5000), new ItemStack(Resource.STONE, 250) },
			new ItemStack[] { new ItemStack(Resource.MONEY, 10000), new ItemStack(Resource.TIN, 250) },
			new ItemStack[] { new ItemStack(Resource.MONEY, 100000), new ItemStack(Resource.BRONZE, 250) },
			new ItemStack[] { new ItemStack(Resource.MONEY, 250000), new ItemStack(Resource.LEAD, 250) },
			new ItemStack[] { new ItemStack(Resource.MONEY, 500000), new ItemStack(Resource.IRON, 250) }, };

	public WoodCutter(TileEntity t, Inventory i) {
		t.i = this;
		this.t = t;
		tier = 0;
		i.takeAll(upgradeCosts[0]);
	}

	@Override
	public void execute(Inventory i) {
		i.take(Resource.MONEY, t.population);
		i.put(Resource.RAWWOOD, (int) Math.sqrt(t.population * (double) tier * t.lumber));
	}

	@Override
	public void upgrade(Inventory i) {
		if (tier < upgradeCosts.length && i.hasEnough(upgradeCosts[tier + 1]))
			i.takeAll(upgradeCosts[++tier]);
	}

	@Override
	public ItemStack[] upgradeCost() {
		if(tier+1 < upgradeCosts.length)
			return upgradeCosts[tier + 1];
		return new ItemStack[0];
	}
}
