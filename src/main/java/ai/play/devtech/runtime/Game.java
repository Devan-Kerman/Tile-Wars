package ai.play.devtech.runtime;

import ai.play.devtech.core.world.entity.Entity;
import ai.play.devtech.core.api.math.TileMath;
import ai.play.devtech.core.api.modules.Mod;
import ai.play.devtech.core.api.objects.Registry;
import ai.play.devtech.core.nation.Nation;
import ai.play.devtech.core.world.chunk.Chunk;
import ai.play.devtech.core.world.chunk.ChunkManager;
import ai.play.devtech.core.world.tile.Tile;
import ai.play.devtech.core.world.tile.TileEntity;
import server.network.Opcode;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Game implements Consumer<Nation> {
	public static Game instance;

	protected static Registry<TileEntity> teRegister;
	protected static Registry<Entity> eRegister;
	public static Registry<Opcode> oRegister;
	protected static List<Mod> mods;
	
	public Game() {
		mods = new LinkedList<>();
		teRegister = new Registry<>();
		eRegister = new Registry<>();
		instance = this;
	}
	
	public Chunk getChunk(int x, int y) {
		return ChunkManager.safeChunk(x, y);
	}
	
	public Tile getTile(long x, long y) {
		return ChunkManager.safeChunk(TileMath.getChunk(x, y)).get(TileMath.getArray(x, y));
	}
	
	public TileEntity getTileEntity(long x, long y) {
		return ChunkManager.safeChunk(TileMath.getChunk(x, y)).tileEnts.at(TileMath.getArray(x, y));
	}
	
	public void initMods() {
		mods.forEach(Mod::init);
	}
	
	public TileEntity instOfTE(int id) {
		return teRegister.getInstance(id);
	}
	
	public void print() {
		System.out.println("Modules: ");
		mods.forEach(System.out::println);
		System.out.println("TileEntities: ");
		teRegister.forEach((i, t) -> System.out.println(t));
		System.out.println("Entities: ");
		eRegister.forEach((i, e) -> System.out.println(e));
	}
	
	public void registerTiles() {
		mods.forEach(m -> m.registerTiles(teRegister));
	}
	
	public void registerEntities() {
		mods.forEach(m -> m.registerEntities(eRegister));
	}
	
	public void registerOpcodes() {
		mods.forEach(m -> m.registerOpcodes(oRegister));
	}
	
	@Override
	public void accept(Nation n) {
		n.tiles.forEach(point -> ChunkManager.safeChunk(point.chunk).tileEnts.at(point.tx, point.ty).run(this, n));
	}
	
	public void addMod(Mod m) {
		mods.add(m);
	}

}
