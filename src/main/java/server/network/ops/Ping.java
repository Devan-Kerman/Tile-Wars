package server.network.ops;

import play.ai.devtech.core.api.io.Bytes;
import server.network.Client;

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
