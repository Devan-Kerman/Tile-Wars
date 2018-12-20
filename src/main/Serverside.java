package main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.InputMismatchException;
import java.util.Scanner;

import tile.Tile;
import util.datamanagement.manager.ChunkManager;
import util.testing.StopWatch;
import visualizer.VPanel;

public class Serverside {
	public static DLogger logger;
	
	
	public static void main(String[] args) {
		logger.info("Booting");
		logger = new DLogger();
		Boot.boot();
		logger.relief("Booted");
		//cmds();
	}

	public static void cmds() throws IOException {
		Scanner s = new Scanner(System.in);
		Scanner t;
		while (true) {
			t = new Scanner(s.nextLine());
			String temp = t.next();
			if (temp.equals("close") || temp.equals("Exit")) {
				break;
			} else if (temp.equals("help")) {
				logger.info("avilable commands:");
				logger.info("close - closes application");
				logger.info("help - shows this list");
				logger.info("render <Xcoord> <Ycoord> - render a specific chunck at specified coordinates");
				logger.info("export <Xcoord> <Ycoord> - exports a chunk to a png file");
				logger.info("generate - generates a chunk within time");
				logger.info("stress - stress tests the generation function");
			} else if (temp.equals("render")) {
				try {
					int x = t.nextInt();
					int y = t.nextInt();
					Visuals.visualize(x, y);
					logger.info("Sucessfully rendered chunk at (" + x + ", " + y + ")");
				} catch (InputMismatchException e) {
					logger.info("Invalid command, usage : render <Xcord> <Ycord>");
				}
			} else if (temp.equals("export")) {
				try {
					int x = t.nextInt();
					int y = t.nextInt();
					File f = new File("yeet.png");
					Files.delete(f.toPath());
					new VPanel(ChunkManager.safeChunk(x, y).data).export(f);
					Desktop.getDesktop().open(f);
					// insert future code here
					logger.info("Sucessfully exported chunk at (" + x + ", " + y + ")");
				} catch (InputMismatchException e) {
					logger.info("Invalid command, usage : export <Xcord> <Ycord>");
				}
			} else if (temp.equals("generate")) {
				StopWatch.start();
				int x = t.nextInt();
				int y = t.nextInt();
				ChunkManager.safeChunk(x, y);
				long duration = StopWatch.stop();
		        logger.info("MS: " +duration);
			}
			else if(temp.equals("stress")) {
				StopWatch.start();
				for (int c = 0; c < 100; c++)
					for (int b = 0; b < 100; b++)
						ChunkManager.safeChunk(c, b);
				for (int c = 0; c < 100; c++)
					for (int b = 0; b < 100; b++) {
						Tile[][] chunk = ChunkManager.safeChunk(c, b).data;
						for (int x = 0; x < 100; x++)
							for (int y = 0; y < 100; y++) {
								if(chunk[x][y].i.tickable()) {
									chunk[x][y].i.execute(null);
									logger.info("Tickable!");
								}
							}
					}
				long duration = StopWatch.stop();
				logger.info("MS: " + duration);
			} else
				logger.info("invalid command, try \"help\" for a list of avilable commands");
		}
		t.close();
		s.close();
		Boot.nationdb.write();
	}
}
