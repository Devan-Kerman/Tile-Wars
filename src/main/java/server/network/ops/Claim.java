package server.network.ops;

import ai.play.devtech.core.api.io.ByteReader;
import ai.play.devtech.core.world.chunk.ChunkManager;
import ai.play.devtech.core.world.tile.LocalPoint;
import ai.play.devtech.core.world.tile.Tile;
import server.network.Client;

public class Claim {
	public static byte[] claim(byte[] t, Client u) {
		ByteReader reader = new ByteReader(t);
		Tile tile = ChunkManager.safeChunk(reader.readPoint()).get(reader.read(LocalPoint.class));
		byte[] b = {0};
		if(tile.ownerid == 0) {
			tile.ownerid = u.player.id;
			b[0] = 1;
		}
		return b;
	}
}
