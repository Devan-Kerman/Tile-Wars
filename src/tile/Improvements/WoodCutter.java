package tile.Improvements;


import game.nation.Nation;
import game.resources.ItemStack;
import game.resources.Resource;
import tile.Improvement;
import tile.TileEntity;

public class WoodCutter extends Improvement {
	
	
	private static ItemStack[][] upgradeCosts = new ItemStack[][] {
			new ItemStack[] { new ItemStack(Resource.MONEY, 1000) },
			new ItemStack[] { new ItemStack(Resource.MONEY, 2500), new ItemStack(Resource.RAWWOOD, 250) },
			new ItemStack[] { new ItemStack(Resource.MONEY, 5000), new ItemStack(Resource.STONE, 250) },
			new ItemStack[] { new ItemStack(Resource.MONEY, 10000), new ItemStack(Resource.TIN, 250) },
			new ItemStack[] { new ItemStack(Resource.MONEY, 100000), new ItemStack(Resource.BRONZE, 250) },
			new ItemStack[] { new ItemStack(Resource.MONEY, 250000), new ItemStack(Resource.LEAD, 250) },
			new ItemStack[] { new ItemStack(Resource.MONEY, 500000), new ItemStack(Resource.IRON, 250) }, };
	
			
	public WoodCutter(Nation n, TileEntity tile) {
		super(n, tile);
		owner.inv.takeAll(upgradeCosts[0]);
	}
	
	
	@Override
	public void execute() {
		owner.inv.take(Resource.MONEY, (double) tile.data.get("population"));
		owner.inv.put(Resource.RAWWOOD, (int) Math.sqrt((double)tile.data.get("population") * tier * tile.lumber));
	}

	@Override
	public void upgrade() {
		if (tier < upgradeCosts.length && owner.inv.hasEnough(upgradeCosts[tier + 1]))
			owner.inv.takeAll(upgradeCosts[++tier]);
	}

	@Override
	public ItemStack[] upgradeCost() {
		if(tier+1 < upgradeCosts.length)
			return upgradeCosts[tier + 1];
		return new ItemStack[] {new ItemStack(Resource.MONEY, Integer.MIN_VALUE)};
	}

	@Override
	public boolean tickable() {
		return true;
	}

	@Override
	public int getTier() {
		return tier;
	}

	@Override
	public ItemStack[] defaultCost() {
		return upgradeCosts[0];
	}

	@Override
	public void demolish() {
		
	}
}
