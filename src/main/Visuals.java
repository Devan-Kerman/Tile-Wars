package main;

import java.awt.Dimension;

import javax.swing.JFrame;

import generators.chunk.Chunk;
import util.datamanagement.manager.ChunkManager;
import visualizer.VPanel;

public class Visuals {
	private Visuals() {}
	public static void visualize(int cx, int cy) {
		JFrame jf = new JFrame("Chunk viewer");
		jf.add(new VPanel(ChunkManager.safeChunk(cx, cy).data));
		jf.pack();
		jf.setVisible(true);
		jf.setSize(new Dimension(Chunk.CHUNKSIZE, Chunk.CHUNKSIZE));
	}
}
