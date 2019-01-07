package game;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.MapSerializer;

import game.nation.TilePoint;
import game.tile.Improvement;
import game.tile.Tile;
import game.tile.TileEntity;
import generators.chunk.Chunk;
import main.DLogger;
import main.networking.TileUpdate;

/**
 * Contains static information that is global
 * 
 * @author devan
 *
 */
public class GlobalData {

	public static final int CHUNKSIZE = 100;
	public static final Kryo kryo;
	static {
		kryo = new Kryo();
		kryo.register(Chunk[][].class);
		kryo.register(Chunk[].class);
		kryo.register(Chunk.class);
		kryo.register(Tile.class);
		kryo.register(Tile[].class); 	
		kryo.register(Tile[][].class);
		kryo.register(TileEntity.class);
		kryo.register(Improvement.class);
		kryo.register(HashMap.class);
		kryo.register(String.class);
		kryo.register(TileUpdate.class);
		kryo.register(TilePoint.class);
		MapSerializer serializer = new MapSerializer();
		kryo.register(HashMap.class, serializer);
		kryo.register(LinkedHashMap.class, serializer);
		serializer.setKeyClass(String.class, kryo.getSerializer(String.class));
		serializer.setKeysCanBeNull(false);
		serializer.setKeyClass(String.class, kryo.getSerializer(String.class));
		for (Class<? extends Improvement> improv : getClasses(GlobalData.class.getClassLoader(), "game/tile/Improvements"))
			kryo.register(improv);
	}

	private GlobalData() {
		throw new IllegalStateException("Utility class");
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public static List<Class<? extends Improvement>> getClasses(ClassLoader cl, String pack) {
		String dottedPackage = pack.replaceAll("[/]", ".");
		List<Class<? extends Improvement>> classes = new ArrayList<>();
		try {
			URL upackage = cl.getResource(pack);
			DataInputStream dis;
			try {
				dis = new DataInputStream((InputStream) upackage.getContent());
			} catch (Exception e) {
				DLogger.error(e.getLocalizedMessage());
				dis = new DataInputStream(new InputStream() {@Override public int read() {return 0;}});
			}
			String line;
			while ((line = dis.readLine()) != null) {
				if (line.endsWith(".class")) {
					classes.add((Class<? extends Improvement>) Class
							.forName(dottedPackage + "." + line.substring(0, line.lastIndexOf('.'))));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}
}
