package server.network.ops;

import ai.play.devtech.core.api.io.ByteReader;
import ai.play.devtech.core.api.io.Bytes;
import ai.play.devtech.core.nation.Nation;
import ai.play.devtech.core.nation.NationCache;
import ai.play.devtech.tilewars.DLogger;
import server.network.Client;
import server.network.players.Login;
import server.network.players.Players;
import java.nio.charset.StandardCharsets;

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
