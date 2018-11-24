package util.datamanagement.manager;


import generators.chunk.Chunk;
import gnu.trove.map.hash.TIntObjectHashMap;
import tile.Tile;

public class ChunkManager {
	public static final TIntObjectHashMap<TIntObjectHashMap<Chunk>> chunks = new TIntObjectHashMap<>();
	
	public static Chunk gen(int x, int y) {
		Chunk c = new Chunk(x, y);
		if(chunks.containsKey(x))
			chunks.get(x).put(y, c);
		else {
			TIntObjectHashMap<Chunk> hm = new TIntObjectHashMap<>();
			hm.put(y, c);
			chunks.put(x, hm);
		}
		return c;
	}
	
	public static Chunk getUnsafe(int x, int y) {
		return chunks.get(x).get(y);
	}
	
	public static boolean inChunks(int x, int y) {
		return chunks.containsKey(x) && chunks.get(x).containsKey(y);
	}
	
	public static Chunk getSafe(int x, int y) {
		
		Chunk c;
		if(inChunks(x,y)) {
			c = getUnsafe(x,y);
		}
		else {
			c = gen(x,y);
		}
		
		return c;
	}
	
	public static Tile getTile(int tx, int ty) {
		Chunk c = getSafe(tx/Chunk.chunksize, ty/Chunk.chunksize);
		return c.data[tx%Chunk.chunksize][ty%Chunk.chunksize];
	}
}
