package play.ai.devtech.network.ops;

import java.nio.charset.StandardCharsets;

import play.ai.devtech.core.api.bytes.ByteReader;
import play.ai.devtech.core.nation.Nation;
import play.ai.devtech.core.nation.NationCache;
import play.ai.devtech.core.util.DLogger;
import play.ai.devtech.core.util.math.Bytes;
import play.ai.devtech.network.Client;
import play.ai.devtech.network.players.Login;
import play.ai.devtech.network.players.Players;

public class Register {
	public static byte[] apply(byte[] t, Client k) {
		ByteReader reader = new ByteReader(t);
		Login login = new Login(reader.readString(StandardCharsets.US_ASCII), reader.readString(StandardCharsets.US_ASCII));
		if(Players.nations.containsKey(login)) {
			DLogger.debug("Failed to register!\n" + login);
			return Bytes.fromInt(0);
		}
		DLogger.debug("Valid Registration!\n"+login);
		Nation n = NationCache.newNation();
		Players.nations.put(login, n.id);
		k.player = n;
		return Bytes.fromInt(n.id);
	}
}
