package ai.play.devtech.core.api.modules;

import ai.play.devtech.core.world.entity.Entity;
import ai.play.devtech.core.world.tile.TileEntity;
import ai.play.devtech.core.api.objects.Registry;
import server.network.Opcode;

public abstract class Mod {
	public abstract void init();
	public abstract void registerTiles(Registry<TileEntity> registryTE);
	public abstract void registerEntities(Registry<Entity> registryE);
	public abstract void registerOpcodes(Registry<Opcode> oRegister);
}
