package play.ai.devtech.network;

import play.ai.devtech.network.ops.AttLogin;
import play.ai.devtech.network.ops.GetChunk;
import play.ai.devtech.network.ops.Ping;
import play.ai.devtech.network.ops.Register;

public class Operations {
	
	public static void loadOperations(Client k) {
		k.queue(0, Ping::apply);
		k.queue(1, Register::apply);
		k.queue(2, GetChunk::apply);
		k.queue(3, AttLogin::apply);
	}
	
	private Operations() {/*no.*/}

}
