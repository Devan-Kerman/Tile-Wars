package play.ai.devtech.network.ops;

import play.ai.devtech.core.util.math.Bytes;
import play.ai.devtech.network.Client;

/**
 * Pings the client
 * @author devan
 *
 */
public class Ping {
	
	public static byte[] apply(byte[] t, Client c) {
		return Bytes.fromInt(80085);
	} // Hehe

}
