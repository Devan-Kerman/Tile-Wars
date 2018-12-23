package game;


import com.esotericsoftware.kryo.Kryo;
import generators.chunk.Chunk;
import tile.Improvement;
import tile.Tile;
import tile.TileEntity;

/**
 * Contains static information that is global
 * @author devan
 *
 */
public class GlobalData {
	
	public static final int CHUNKSIZE = 100;
	public static final Kryo kryo = new Kryo();
	static {
		kryo.register(Chunk[][].class);
		kryo.register(Chunk[].class);
		kryo.register(Chunk.class);
		kryo.register(Tile.class);
		kryo.register(Tile[].class);
		kryo.register(Tile[][].class);
		kryo.register(TileEntity.class);
		kryo.register(Improvement.class);
	}

	private GlobalData() {
		throw new IllegalStateException("Utility class");
	}
	
}
