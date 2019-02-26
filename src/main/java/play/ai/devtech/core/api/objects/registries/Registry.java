package play.ai.devtech.core.api.objects.registries;

import java.util.HashMap;
import java.util.Map;

import play.ai.devtech.core.api.memory.TypePool;

public class Registry<T> extends HashMap<Integer, Class<? extends T>> {
	private static final long serialVersionUID = 1L;
	
	public TypePool<T> tilePool;
	public Registry() {
		tilePool = new TypePool<>();
	}
	
	@Override
	public Class<? extends T> put(Integer key, Class<? extends T> value) {
		tilePool.register(value);
		return super.put(key, value);
	}
	
	@Override
	public Class<? extends T> putIfAbsent(Integer key, Class<? extends T> value) {
		tilePool.register(value);
		return super.putIfAbsent(key, value);
	}
	
	@Override
	public void putAll(Map<? extends Integer, ? extends Class<? extends T>> m) {
		m.forEach(this::putIfAbsent);
		super.putAll(m);
	}
	
	public T getInstance(int id) {
		return tilePool.newObj(get(id));
	}

	
}
