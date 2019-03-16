package play.ai.devtech.core.nation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import play.ai.devtech.core.api.interfaces.Assembable;
import play.ai.devtech.core.api.interfaces.Packetable;
import play.ai.devtech.core.api.io.ByteReader;
import play.ai.devtech.core.api.io.Packer;
import play.ai.devtech.core.nation.resources.Inventory;
import play.ai.devtech.core.nation.resources.ItemStack;
import play.ai.devtech.core.nation.resources.Resource;
import play.ai.devtech.tilewars.DLogger;

/**
 * Any new values/data added to this class will not be saved unless you edit the
 * serializer and deserializer
 * 
 * @author devan
 *
 */
public class Nation implements Packetable, Assembable {
	/**
	 * The list of the current owned improvments/tile entities this nation has
	 */
	public List<TilePoint> tiles;
	
	/**
	 * The inventory of the nation, has multiple ItemStacks
	 */
	private Inventory inv;
	
	/**
	 * The unique id of the nation
	 */
	public int id;
	
	/**
	 * Number id generator
	 */
	private static final Random r;
	
	/**
	 * Stores a map of the enum and its "ID" for deserialization
	 */
	protected static final Map<Short, Resource> enummap = new HashMap<>();
	
	/**
	 * Same thing but in reverse for serialization
	 */
	protected static final Map<Resource, Short> idmap = new EnumMap<>(Resource.class);
	
	@Override
	public void from(ByteReader reader) {
		tiles = reader.readList(TilePoint.class);
		inv = reader.read(Inventory.class);
	}
	
	@Override
	public void pack(Packer p) {
		p.packList(tiles);
		p.autoPack(inv);
		p.packInt(id);
	}
	
	static {
		r = new Random();
		File f = new File("NationData/");
		if (!f.exists()) {
			boolean failed = !f.mkdir();
			if(failed) DLogger.warn("Failed to create NationData folder!!!");
		}
		for (Resource res : Resource.values()) {
			enummap.put(res.getID(), res);
			idmap.put(res, res.getID());
		}
	}

	/**
	 * Generates a new Nation with a random id, this should only be used when a new
	 * player joins the game, or for testing
	 * @param startinginv
	 * 		Whether the player should start with the starting inventory (1000$ and 100 raw wood)
	 */
	Nation(boolean startinginv) {
		tiles = new ArrayList<>();
		inv = new Inventory();
		if (startinginv) {
			inv.putAll(new ItemStack[] { new ItemStack(Resource.MONEY, 1000), new ItemStack(Resource.RAWWOOD, 100) });
		}
		do {
			id = r.nextInt();
		} while (id == 0 || new File("NationData/" + id + ".nation").exists());
		boolean b;
		try {
			b = new File("NationData/" + id + ".nation").createNewFile();
		} catch (IOException e) {
			b = false;
		}
		if (b)
			DLogger.debug("New nation with id=" + id + " was created!");
		else
			DLogger.error("Nation with id=" + id + " failed to create!");
	}

	/**
	 * Generic nation for serialization
	 */
	public Nation() {
		tiles = new ArrayList<>();
		inv = new Inventory();
	}

	/**
	 * toString() [RSSID, Amount]...\n [TilePoint.toString()]...
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (ItemStack itsa : inv.getStacks())
			sb.append(String.format(" [%s, %d]", itsa.r.name(), itsa.amount));
		sb.append('\n');
		for (TilePoint point2 : tiles) {
			sb.append(' ');
			sb.append(point2.toString());
		}
		return String.format("%s : %s", super.toString(), sb.toString());
	}

	/**
	 * Returns this Nation's inventory
	 * 
	 * @return {@link Inventory}
	 */
	public Inventory getInventory() {
		if (inv == null)
			inv = new Inventory();
		return inv;
	}

	/**
	 * Adds a point to this Nation's tile roster.
	 * 
	 * @param p {@link TilePoint}
	 */
	public void addTilePoint(TilePoint p) {
		tiles.add(p);
	}
	
	public void removeTilePoint(TilePoint p) {
		tiles.remove(p);
	}
	
	@Override
	protected void finalize() throws Throwable {
		NationCache.save(this);
		super.finalize();
	}

	
}
