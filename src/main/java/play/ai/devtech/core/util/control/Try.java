package play.ai.devtech.core.util.control;

import play.ai.devtech.core.util.DLogger;

public class Try {

	private Try() {}
	
	public static void tryIgnore(Runnable r) {
		try {
			r.run();
		} catch(Exception e) {
			
		}
	}
	
	public static void tryWarn(Runnable r) {
		try {
			r.run();
		} catch(Exception e) {
			DLogger.warn(e.getMessage());
		}
	}

}
