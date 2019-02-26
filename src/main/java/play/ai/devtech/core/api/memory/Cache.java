package play.ai.devtech.core.api.memory;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
public class Cache<T> {
	private Map<Integer, T> cache = new ConcurrentHashMap<>();
	private Queue<Integer> queue = new ConcurrentLinkedQueue<>();
	private final int SIZE;
	public Cache(int size) {
		this.SIZE = size;
	}
	
	public void cache(int id, T t) {
		queue.add(id);
		cache.put(id, t);
		if(queue.size() > SIZE) {
			int p = queue.peek();
			cache.remove(p);
		}
	}
	
	public T get(int id) {
		return cache.get(id);
	}
}
