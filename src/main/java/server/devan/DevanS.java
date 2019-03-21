package server.devan;

import ai.play.devtech.core.api.modules.Mod;
import ai.play.devtech.core.api.modules.Module;
import ai.play.devtech.core.api.objects.Registry;
import ai.play.devtech.core.world.entity.Entity;
import ai.play.devtech.core.world.tile.TileEntity;
import ai.play.devtech.tilewars.DLogger;
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
