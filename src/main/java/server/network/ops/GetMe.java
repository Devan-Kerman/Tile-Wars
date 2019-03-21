package server.network.ops;

import ai.play.devtech.core.api.io.Packer;
import server.network.Client;

public class GetMe {
	public static byte[] apply(byte[] t, Client u) {
		Packer p = new Packer();
		p.autoPack(u.player);
		return p.unpack();
	}
}
