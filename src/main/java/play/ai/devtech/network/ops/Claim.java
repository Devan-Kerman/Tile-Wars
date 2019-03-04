package play.ai.devtech.network.ops;

import play.ai.devtech.core.api.area.BPoint;
import play.ai.devtech.core.api.bytes.ByteReader;
import play.ai.devtech.core.world.chunk.ChunkManager;
import play.ai.devtech.core.world.tile.Tile;
import play.ai.devtech.network.Client;

public class Claim {
	public static byte[] claim(byte[] t, Client u) {
		ByteReader reader = new ByteReader(t);
		Tile tile = ChunkManager.safeChunk(reader.readPoint()).get(reader.read(BPoint.class));
		byte[] b = {0};
		if(tile.ownerid == 0) {
			tile.ownerid = u.player.id;
			b[0] = 1;
		}
		return b;
	}
}
