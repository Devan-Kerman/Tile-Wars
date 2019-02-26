package play.ai.devtech.core.api.memory;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TypePool<T> {
	private final Map<Class<? extends T>, Queue<? extends T>> POOL;
	private final Executor POOLER = Executors.newCachedThreadPool();

	public TypePool() {
		POOL = new ConcurrentHashMap<>();
	}

	public void register(Class<? extends T> clas, int pool) {
		poolThread(clas, pool);
	}

	public void register(Class<? extends T> clas) {
		poolThread(clas, 10);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void poolThread(Class<? extends T> pool, final int size) {
		POOL.put(pool, new ArrayBlockingQueue<>(size));
		POOLER.execute(() -> {
			while (true) {
				Queue q = POOL.get(pool);
				if (q.size() < size) {
					try {
						q.add(pool.newInstance());
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public T newObj(Class<?> tclass) {
		T t = POOL.get(tclass).poll();
		if(t == null)
			try {
				return (T) tclass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		return t;
	}
}
