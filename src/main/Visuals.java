package main;

import java.awt.Dimension;

import javax.swing.JFrame;

import game.GlobalData;
import util.datamanagement.ChunkManager;
import visualizer.VPanel;

public class Visuals {
	private Visuals() {}
	/**
	 * Opens a JPanel with a display of the requested chunk
	 * @param cx
	 * @param cy
	 */
	public static void visualize(int cx, int cy) {
		JFrame jf = new JFrame("Chunk viewer");
		jf.add(new VPanel(ChunkManager.safeChunk(cx, cy).data));
		jf.pack();
		jf.setVisible(true);
		jf.setSize(new Dimension(GlobalData.CHUNKSIZE, GlobalData.CHUNKSIZE));
	}
}
