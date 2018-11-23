package util.datamanagement.manager;


import generators.chunk.Chunk;
import tile.Tile;
import util.datamanagement.maps.MaxMap;

public class ChunkManager {
	public static MaxMap<Integer,MaxMap<Integer, Chunk>> chunks = new MaxMap<Integer, MaxMap<Integer, Chunk>>(30);
	
	public static Chunk gen(int x, int y) {
		Chunk c = new Chunk(x, y);
		if(chunks.containsKey(x))
			chunks.get(x).put(y, c);
		else {
			MaxMap<Integer, Chunk> hm = new MaxMap<Integer, Chunk>(30);
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
		if(inChunks(x,y))
			return getUnsafe(x,y);
		else
			return gen(x,y);
	}
	
	public static Tile getTile(int tx, int ty) {
		Chunk c = getSafe(tx/Chunk.chunksize, ty/Chunk.chunksize);
		return c.data[tx%Chunk.chunksize][ty%Chunk.chunksize];
	}
}
