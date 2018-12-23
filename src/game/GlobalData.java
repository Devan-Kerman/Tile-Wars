package game;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;

import game.tile.Improvement;
import game.tile.Tile;
import game.tile.TileEntity;
import generators.chunk.Chunk;
import main.DLogger;

/**
 * Contains static information that is global
 * 
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
		kryo.register(HashMap.class);
		kryo.register(String.class);
		for (Class<? extends Improvement> improv : getClasses(GlobalData.class.getClassLoader(), "tile/Improvements"))
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
			DataInputStream dis = new DataInputStream((InputStream) upackage.getContent());
			String line = null;
			while ((line = dis.readLine()) != null) {
				if (line.endsWith(".class")) {
					classes.add((Class<? extends Improvement>) Class
							.forName(dottedPackage + "." + line.substring(0, line.lastIndexOf('.'))));
				}
			}
		} catch (Exception e) {
			DLogger.error(e.getMessage());
		}
		return classes;
	}
}
