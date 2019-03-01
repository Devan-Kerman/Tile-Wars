package play.ai.devtech.network.ops;

import play.ai.devtech.core.api.bytes.ByteReader;
import play.ai.devtech.core.api.bytes.Packer;
import play.ai.devtech.core.world.chunk.ChunkManager;
import play.ai.devtech.network.Client;

public class GetChunk {

	public static byte[] apply(byte[] t, Client u) {
		Packer p = new Packer();
		p.autoPack(ChunkManager.safeChunk(new ByteReader(t).readPoint()));
		return p.unpack();
	}

}
