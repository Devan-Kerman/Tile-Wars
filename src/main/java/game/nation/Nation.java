package game.nation;

import game.resources.Inventory;
import game.resources.ItemStack;
import game.resources.Resource;
import main.DLogger;
import java.io.*;
import java.util.*;

/**
 * Any new values/data added to this class will not be saved unless you edit the
 * serializer and deserializer
 * 
 * @author devan
 *
 */
public class Nation implements Serializable {
	private static final long serialVersionUID = -5982268049046188288L;
	
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
		} while (new File("NationData/" + id + ".nation").exists());
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
		save();
	}

	/**
	 * Generic nation for serialization
	 */
	Nation() {
		tiles = new ArrayList<>();
		inv = new Inventory();
	}

	/**
	 * Writes this instance to the disk
	 */
	public void save() {
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("NationData/" + id + ".nation"));
			bos.write(serialize());
			bos.close();
		} catch (Exception e) {
			DLogger.error(e.getMessage());
		}
	}

	/**
	 * Not meant to be used outside of the nationcache, use {@link NationCache}
	 * 
	 * @param id
	 * @return
	 */
	static Nation getNation(int id) {
		Nation n = new Nation();
		n.id = id;
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream("NationData/" + id + ".nation"));
			byte[] intput = new byte[4];
			byte[] shortput = new byte[2];
			int tiles = 0;
			if (bis.read(intput) == 4)
				tiles = toInt(intput, 0);
			else
				DLogger.error("Serialization issues");
			for (int x = 0; x < tiles; x++) {
				TilePoint p = new TilePoint();
				if (bis.read(intput) == 4)
					p.chunk.x = toInt(intput, 0);
				else
					DLogger.error("Serialization issues");
				if (bis.read(intput) == 4)
					p.chunk.y = toInt(intput, 0);
				else
					DLogger.error("Serialization issues");
				p.tx = (byte) bis.read();
				p.ty = (byte) bis.read();
				n.addTilePoint(p);
			}
			int rss = 0;
			if (bis.read(intput) == 4)
				rss = toInt(intput, 0);
			for (int x = 0; x < rss; x++) {
				short rid = 0;
				if (bis.read(shortput) == 2)
					rid = toShort(shortput);
				else
					DLogger.error("Serialization issues");
				int am = 0;
				if (bis.read(intput) == 4)
					am = toInt(intput, 0);
				else
					DLogger.error("Serialization issues");
				n.inv.put(enummap.get(rid), am);
			}
			bis.close();
		} catch (Exception e) {
			DLogger.error(e.getMessage());
		}
		DLogger.debug("Nation was de-serialized");
		return n;
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

	/**
	 * Converts a set of 4 bytes to an integer
	 * 
	 * @param bytes  an array of bytes
	 * @param offset where to begin the read
	 * @return calculated (int)eger
	 */
	private static int toInt(byte[] bytes, int offset) {
		int ret = 0;
		for (int i = 0; i < 4 && i + offset < bytes.length; i++) {
			ret <<= 8;
			ret |= (int) bytes[i] & 0xFF;
		}
		return ret;
	}

	/**
	 * Converts a set of 2 bytes to a short
	 * 
	 * @param bs an array of bytes
	 * @return calculated short
	 */
	private static short toShort(byte[] bs) {
		return (short) (bs[0] << 8 | bs[1] & 0xFF);
	}

	/**
	 * Serializes this instance into an array of bytes
	 * 
	 * @return data in bytes
	 */
	private byte[] serialize() {
		int tilessize = tiles.size();
		int invsize = inv.getSize();
		byte[] buffer = new byte[tilessize * 10 + invsize * 6 + 8];
		int bytecounter = 0;
		bytecounter = serializeInt(bytecounter, tilessize, buffer);
		bytecounter = serializePoints(bytecounter, buffer);
		bytecounter = serializeInt(bytecounter, invsize, buffer);
		for (ItemStack stack : inv.getStacks()) {
			bytecounter = serializeShort(bytecounter, stack.r.getID(), buffer);
			bytecounter = serializeInt(bytecounter, stack.amount, buffer);
		}
		return buffer;
	}

	/**
	 * Serializes the TilePoints of this class
	 * 
	 * @param start  starting number
	 * @param buffer the array to read it from
	 * @return ending number
	 */
	private int serializePoints(int start, byte[] buffer) {
		int bytecounter = start;
		for (TilePoint tile : tiles) {
			buffer[bytecounter++] = (byte) (tile.chunk.x >> 24);
			buffer[bytecounter++] = (byte) (tile.chunk.x >> 16);
			buffer[bytecounter++] = (byte) (tile.chunk.x >> 8);
			buffer[bytecounter++] = (byte) (tile.chunk.x);
			buffer[bytecounter++] = (byte) (tile.chunk.y >> 24);
			buffer[bytecounter++] = (byte) (tile.chunk.y >> 16);
			buffer[bytecounter++] = (byte) (tile.chunk.y >> 8);
			buffer[bytecounter++] = (byte) (tile.chunk.y);
			buffer[bytecounter++] = tile.tx;
			buffer[bytecounter++] = tile.ty;
		}
		return bytecounter;
	}

	/**
	 * converts the int into bytes and puts it in the buffer
	 * 
	 * @param start  the starting index
	 * @param i      the integer to convert
	 * @param buffer the buffer to put the bytes in
	 * @return the ending index
	 */
	private int serializeInt(int start, int i, byte[] buffer) {
		buffer[start++] = (byte) (i >> 24);
		buffer[start++] = (byte) (i >> 16);
		buffer[start++] = (byte) (i >> 8);
		buffer[start++] = (byte) (i);
		return start;
	}

	/**
	 * converts the short into bytes and puts it in the buffer
	 * 
	 * @param start  the starting index
	 * @param i      the short to convert
	 * @param buffer the buffer to put the bytes in
	 * @return the ending index
	 */
	private int serializeShort(int start, short i, byte[] buffer) {
		buffer[start++] = (byte) (i >> 8);
		buffer[start++] = (byte) (i);
		return start;
	}
}
