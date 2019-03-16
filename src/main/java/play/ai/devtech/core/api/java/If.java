package play.ai.devtech.core.api.java;

import java.util.function.Supplier;

public class If {
	public static void ifThen(Supplier<Boolean> sup, Runnable r) {
		if(sup.get())
			r.run();
	}
}
