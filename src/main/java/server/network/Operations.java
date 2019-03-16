package server.network;

import server.network.ops.AttLogin;
import server.network.ops.Claim;
import server.network.ops.Delet;
import server.network.ops.GetChunk;
import server.network.ops.GetMe;
import server.network.ops.Improve;
import server.network.ops.Ping;
import server.network.ops.Register;

public class Operations {
	
	public static void loadOperations(Client k) {
		k.queue(0, Ping::apply);
		k.queue(1, Register::apply);
		k.queue(2, GetChunk::apply);
		k.queue(3, AttLogin::apply);
		k.queue(4, Claim::claim);
		k.queue(5, GetMe::apply);
		k.queue(6, Improve::apply);
		k.queue(7, Delet::apply);
		
	}
	
	private Operations() {/*no.*/}

}
