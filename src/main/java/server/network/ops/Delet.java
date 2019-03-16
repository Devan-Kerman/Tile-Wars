package server.network.ops;

import play.ai.devtech.control.Improvements;
import play.ai.devtech.core.api.io.ByteReader;
import play.ai.devtech.core.api.io.Packer;
import play.ai.devtech.core.errors.ClaimException;
import play.ai.devtech.core.errors.NotEnoughResourcesException;
import play.ai.devtech.core.nation.TilePoint;
import play.ai.devtech.runtime.Game;
import server.network.Client;

public class Delet {
	public static byte[] apply(byte[] data, Client c) {
		ByteReader reader = new ByteReader(data);
		Packer packer = new Packer();
		try {
			Improvements.destroy(new TilePoint(reader.readPoint(), reader.read(), reader.read()), Game.instance, c.player);
			packer.pack((byte)0);
		} catch(NotEnoughResourcesException e) {
			packer.pack((byte)1);
		} catch(ClaimException e) {
			packer.pack((byte)2);
		}
		return packer.unpack();
	}
}
