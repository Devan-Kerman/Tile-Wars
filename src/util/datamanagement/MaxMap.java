package util.datamanagement;

import java.util.LinkedHashMap;
import java.util.Map;

public class MaxMap<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = 200779303091620632L;
	private final int maxSize;

    public MaxMap(int maxSize) {
        this.maxSize = maxSize;
    }


	@Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }
	
	public int getMaxSize() {
		return maxSize;
	}
}
