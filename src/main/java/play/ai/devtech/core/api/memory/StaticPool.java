package play.ai.devtech.core.api.memory;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
@SuppressWarnings("rawtypes")
public class StaticPool {
	private static final Map<Class<?>, Queue<?>> POOL;
	private static final Executor POOLER = Executors.newCachedThreadPool();
	
	static {
		POOL = new ConcurrentHashMap<>();
	}
	
	public static void register(Class<?> clas, int pool) {
		poolThread(clas, pool);
	}
	
	public static void register(Class<?> clas) {
		poolThread(clas, 10);
	}
	
	@SuppressWarnings("unchecked")
	private static void poolThread(Class<?> pool, final int size) {
		POOLER.execute(() -> {
			while(true) {
				
				Queue q = POOL.get(pool);
				if(q.size() < size) {
					try {
						q.add(pool.newInstance());
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}
