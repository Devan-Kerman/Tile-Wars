package main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.datamanagement.manager.ChunkManager;
import util.testing.StopWatch;
import visualizer.VPanel;

public class Serverside {
	private static final Logger LOGGER = LoggerFactory.getLogger(Serverside.class);
	
	public static void main(String[] args) throws IOException {
		Boot.boot();
		cmds();
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
				LOGGER.info("avilable commands:");
				LOGGER.info("close - closes application");
				LOGGER.info("help - shows this list");
				LOGGER.info("render <Xcoord> <Ycoord> - render a specific chunck at specified coordinates");
			} else if (temp.equals("render")) {
				try {
					int x = t.nextInt();
					int y = t.nextInt();
					Visuals.visualize(x, y);					
					LOGGER.info("Sucessfully rendered chunk at (" + x + ", " + y + ")");				
				} catch(InputMismatchException e) {
					LOGGER.info("Invalid command, usage : render <Xcord> <Ycord>");
				}catch(NoSuchElementException e) {
					LOGGER.info("Invalid command, usage : render <Xcord> <Ycord>");
				}
			} else if (temp.equals("export")) {
				try {
					int x = t.nextInt();
					int y = t.nextInt();
					File f = new File("yeet.png");
					f.delete();
					new VPanel(ChunkManager.getChunk(x, y).getData()).Export(f);
					Desktop.getDesktop().open(f);
					//insert future code here
					LOGGER.info("Sucessfully exported chunk at (" + x + ", " + y + ")");
				}catch(InputMismatchException e)
				{
					LOGGER.info("Invalid command, usage : export <Xcord> <Ycord>");
				}catch(NoSuchElementException e)
				{
					LOGGER.info("Invalid command, usage : export <Xcord> <Ycord>");
				}
			}
			else if(temp.equals("generate")) {
				StopWatch.start();
				int x = t.nextInt();
				int y = t.nextInt();
				ChunkManager.getChunk(x, y);
				long duration = StopWatch.stop();
		        System.out.println("MS: " +duration);
			}
			else if(temp.equals("StressTest")) {
				StopWatch.start();
				for(int c = 0; c < 100; c++)
					for(int b = 0; b < 100; b++)
						ChunkManager.getChunk(c, b);
				long duration = StopWatch.stop();
		        System.out.println("MS: " +duration);
			}
			else
				LOGGER.info("invalid command, try \"help\" for a list of avilable commands");
		}
		t.close();
		s.close();
	}
}
