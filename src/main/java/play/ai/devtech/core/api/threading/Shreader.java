package play.ai.devtech.core.api.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Used for shreading tasks into multiple threads
 * @author devan
 *
 */
public class Shreader {
	
	public static final ExecutorService ses = Executors.newCachedThreadPool();
	
	Runnable[] tasks;
	
	@SafeVarargs
	public Shreader(Runnable...tasks) {
		this.tasks = tasks;
	}
	
	public void run() {
		for(Runnable run : tasks)
			ses.submit(run);
	}

}
