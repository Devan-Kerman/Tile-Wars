package play.ai.devtech.network.ops;

import java.nio.charset.StandardCharsets;

import play.ai.devtech.core.api.bytes.ByteReader;
import play.ai.devtech.core.nation.Nation;
import play.ai.devtech.core.nation.NationCache;
import play.ai.devtech.core.util.math.Bytes;
import play.ai.devtech.network.Client;
import play.ai.devtech.network.players.Players;

public class Register {
	public static byte[] apply(byte[] t, Client k) {
		ByteReader reader = new ByteReader(t);
		String user = reader.readString(StandardCharsets.US_ASCII);
		String pass = reader.readString(StandardCharsets.US_ASCII);
		if(Players.logins.containsKey(user))
			return Bytes.fromInt(0);
		Players.logins.put(user, pass);
		Nation n = NationCache.newNation();
		k.player = n;
		return Bytes.fromInt(n.id);
	}
}
