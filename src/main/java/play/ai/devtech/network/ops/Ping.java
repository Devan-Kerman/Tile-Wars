package play.ai.devtech.network.ops;

import java.util.function.Function;

import play.ai.devtech.core.util.math.Bytes;

/**
 * Pings the client
 * @author devan
 *
 */
public class Ping implements Function<byte[], byte[]> {

	@Override
	public byte[] apply(byte[] t) {
		return Bytes.fromInt(80085);
	} // Hehe

}
