package server.network.ops;

import java.nio.charset.StandardCharsets;

import play.ai.devtech.core.api.io.ByteReader;
import play.ai.devtech.core.api.io.Packer;
import play.ai.devtech.core.nation.NationCache;
import play.ai.devtech.tilewars.DLogger;
import server.network.Client;
import server.network.players.Login;
import server.network.players.Players;

public class AttLogin {

	public static byte[] apply(byte[] t, Client u) {
		ByteReader reader = new ByteReader(t);
		String user = reader.readString(StandardCharsets.US_ASCII);
		String pass = reader.readString(StandardCharsets.US_ASCII);
		Login login = new Login(user, pass);
		Packer p = new Packer();
		Integer n = Players.nations.get(login);
		if(n == null || n == 0) {
			p.packInt(0);
			DLogger.debug("Invalid login! \nUsername: " + user + "\nPassword: " + pass);
		} else {
			DLogger.debug("Login success! \nUsername: " + user + "\nPassword: " + pass);
			p.packInt(n);
		}
		u.player = NationCache.getNation(n);
		return p.unpack();
	}

}
