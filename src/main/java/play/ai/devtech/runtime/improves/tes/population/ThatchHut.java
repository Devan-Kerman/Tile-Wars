package play.ai.devtech.runtime.improves.tes.population;

import play.ai.devtech.core.api.bytes.Assembable;
import play.ai.devtech.core.api.bytes.ByteReader;
import play.ai.devtech.core.api.bytes.Packer;
import play.ai.devtech.core.api.bytes.Packetable;
import play.ai.devtech.core.nation.Nation;
import play.ai.devtech.core.nation.resources.Inventory;
import play.ai.devtech.core.nation.resources.ItemStack;
import play.ai.devtech.core.nation.resources.Resource;
import play.ai.devtech.core.world.chunk.Chunk;
import play.ai.devtech.core.world.tile.Tile;
import play.ai.devtech.core.world.tile.TileEntity;
import play.ai.devtech.runtime.improves.templates.Populated;

public class ThatchHut extends TileEntity implements Populated {
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
	public float getPopulation() {
		return data.population;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TileEntity ID: ").append(getId()).append('\n');
		builder.append("Population: ").append(getPopulation()).append('\n');
		builder.append("TileEntity string id: ").append("ThatchHut").append('\n');
		builder.append("TileEntity human name: ").append("Thatch Hut").append('\n');
		return builder.toString();
	}


	@Override
	public void run(Nation n, Chunk c, Tile t) {
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
}
