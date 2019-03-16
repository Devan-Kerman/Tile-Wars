package server.network.ops;

import play.ai.devtech.core.api.io.ByteReader;
import play.ai.devtech.core.api.io.Packer;
import play.ai.devtech.core.api.testing.Bencher;
import play.ai.devtech.core.api.testing.Benchmark;
import play.ai.devtech.core.world.chunk.Chunk;
import play.ai.devtech.core.world.chunk.ChunkManager;
import server.network.Client;

public class GetChunk {

	public static Bencher serializationBencher = new Bencher("benchmarks/ChunkSerialization.txt");
	public static byte[] apply(byte[] t, Client u) {
		Packer p = new Packer();
		Chunk c = ChunkManager.safeChunk(new ByteReader(t).readPoint());
		Benchmark bench = serializationBencher.getBenchmarker();
		bench.start();
		p.autoPack(c);
		bench.stop();
		return p.unpack();
	}

}
