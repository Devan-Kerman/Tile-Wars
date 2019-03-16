package play.ai.devtech.core.api.modules;

import play.ai.devtech.core.api.objects.Registry;
import play.ai.devtech.core.world.entity.Entity;
import play.ai.devtech.core.world.tile.TileEntity;
import server.network.Opcode;

public abstract class Mod {
	public abstract void init();
	public abstract void registerTiles(Registry<TileEntity> registryTE);
	public abstract void registerEntities(Registry<Entity> registryE);
	public abstract void registerOpcodes(Registry<Opcode> oRegister);
}
