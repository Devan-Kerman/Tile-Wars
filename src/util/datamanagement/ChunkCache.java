package util.datamanagement;

import generators.chunk.Chunk;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;

public class ChunkCache {
	private final Long2ObjectLinkedOpenHashMap<Chunk> map;
	private final int maxSize;
	
	public ChunkCache() {
		this(1000);
	}
	
	public ChunkCache(final int maxSize) {
		this.map = new Long2ObjectLinkedOpenHashMap<>();
		this.maxSize = maxSize;
	}
	
	public Chunk get(final int x, final int y) {
		return map.getAndMoveToFirst(twoIntsToLong(x, y));
	}
	
	public void put(final int x, final int y, final Chunk chunk) {
		map.putAndMoveToFirst(twoIntsToLong(x, y), chunk);
		
		if(map.size() > maxSize) {
			map.removeLast();
		}
	}
	
	
	
	public boolean containsKey(final int x, final int y) {
		return map.containsKey(twoIntsToLong(x, y));
	}
	
	public static long twoIntsToLong(final int x, final int y) {
		return (((long)x) << 32) | (y & 0xffffffffL);
	}
}
