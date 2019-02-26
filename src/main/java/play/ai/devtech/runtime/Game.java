package play.ai.devtech.runtime;

import java.util.function.Consumer;

import play.ai.devtech.core.nation.Nation;
import play.ai.devtech.core.world.chunk.Chunk;
import play.ai.devtech.core.world.chunk.ChunkManager;
import play.ai.devtech.core.world.tile.Tile;
import play.ai.devtech.core.world.tile.TileEntity;

public class Game implements Consumer<Nation> {

	public Game() {
		
	}
	@Override
	public void accept(Nation n) {
		n.tiles.forEach(point -> {
			Chunk at = ChunkManager.safeChunk(point.chunk);
			TileEntity te = at.tileEnts.at(point.tx, point.ty);
			Tile t = at.get(point.tx, point.ty);
			te.run(n, at, t);
		});
	}
	
	

}
