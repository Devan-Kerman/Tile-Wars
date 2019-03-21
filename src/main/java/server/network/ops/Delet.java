package server.network.ops;

import ai.play.devtech.control.Improvements;
import ai.play.devtech.core.api.io.ByteReader;
import ai.play.devtech.core.api.io.Packer;
import ai.play.devtech.core.errors.ClaimException;
import ai.play.devtech.core.errors.NotEnoughResourcesException;
import ai.play.devtech.core.nation.TilePoint;
import ai.play.devtech.runtime.Game;
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
