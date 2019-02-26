package play.ai.devtech.core.util.control;

import java.util.function.Supplier;

public class If {
	public static void ifThen(Supplier<Boolean> sup, Runnable r) {
		if(sup.get())
			r.run();
	}
}
