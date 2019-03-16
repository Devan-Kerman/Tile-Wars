package server.devan;

import play.ai.devtech.core.api.modules.Mod;
import play.ai.devtech.core.api.modules.Module;
import play.ai.devtech.core.api.objects.Registry;
import play.ai.devtech.core.world.entity.Entity;
import play.ai.devtech.core.world.tile.TileEntity;
import play.ai.devtech.tilewars.DLogger;
import server.devan.example.ThatchHut;
import server.network.Opcode;

@Module(getMod = DevanS.class)
public class DevanS extends Mod {

	@Override
	public void init() {
		DLogger.debug("Devan S. loaded");
	}

	@Override
	public void registerTiles(Registry<TileEntity> registryTE) {
		registryTE.register(0, ThatchHut.class);
	}

	@Override
	public void registerEntities(Registry<Entity> registryE) {
		/*Have never tested this*/
	}

	@Override
	public void registerOpcodes(Registry<Opcode> oRegister) {
		
	}

	

}
