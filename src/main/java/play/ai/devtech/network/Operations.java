package play.ai.devtech.network;

import play.ai.devtech.network.ops.Ping;

public class Operations {
	
	public static void loadOperations(Client k) {
		k.queue(0, new Ping());
	}

}
