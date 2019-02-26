package play.ai.devtech.core.api.memory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InstancePool<T> {
	private static final Executor POOLER = Executors.newCachedThreadPool();
	private Class<T> tclass;
	private Queue<T> queue;
	
	public InstancePool(Class<T> toPool, int size) {
		tclass = toPool;
		queue = new ConcurrentLinkedQueue<>();
		POOLER.execute(() -> {
			while(true) {
				if(queue.size() < size) {
					try {
						queue.add(tclass.newInstance());
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	public T get() {
		T t = queue.poll();
		if(t == null)
			try {
				return tclass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		return t;
	}
	
	public InstancePool(Class<T> toPool) {
		this(toPool, 10);
	}
}
