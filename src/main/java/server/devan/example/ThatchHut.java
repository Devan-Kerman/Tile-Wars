package server.devan.example;

import ai.play.devtech.core.api.interfaces.Assembable;
import ai.play.devtech.core.api.interfaces.Packetable;
import ai.play.devtech.core.api.io.ByteReader;
import ai.play.devtech.core.api.io.Packer;
import ai.play.devtech.core.errors.NotEnoughResourcesException;
import ai.play.devtech.core.nation.Nation;
import ai.play.devtech.core.nation.resources.Inventory;
import ai.play.devtech.core.nation.resources.ItemStack;
import ai.play.devtech.core.nation.resources.Resource;
import ai.play.devtech.core.world.tile.TileEntity;
import ai.play.devtech.runtime.Game;

public class ThatchHut extends TileEntity {
	private Data data;
	
	public ThatchHut() {
		data = new Data();
	}


	@Override
	public int getId() {
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Assembable & Packetable> T data() {
		return (T) data;
	}
	
	class Data implements Assembable, Packetable {

		float population = 2;
		
		@Override
		public void pack(Packer p) {
			p.packFloat(population);
		}

		@Override
		public void from(ByteReader reader) {
			population = reader.readFloat();
		}
		
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TileEntity ID: ").append(getId()).append('\n');
		builder.append("Population: ").append(data.population).append('\n');
		builder.append("TileEntity string id: ").append("ThatchHut").append('\n');
		builder.append("TileEntity human name: ").append("Thatch Hut").append('\n');
		return builder.toString();
	}
	
	@Override
	public void run(Game g, Nation n) {
		Inventory inv = n.getInventory();
		float pop = data.population;
		ItemStack cons = new ItemStack(Resource.FOOD, (int)pop);
		if(inv.hasEnough(cons)) {
			inv.take(cons);
			if(data.population < 10)
				data.population*=1.1;
		} else if(data.population > 2)
				data.population *=.9;
	}


	@Override
	public void build(Nation n) throws NotEnoughResourcesException {
		Inventory inv = n.getInventory();
		inv.take(new ItemStack(Resource.MONEY, 100));
	}
}
