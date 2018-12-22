package main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.InputMismatchException;
import java.util.Scanner;

import util.datamanagement.ChunkManager;
import util.testing.StopWatch;
import visualizer.VPanel;

public class Serverside {
	public static DLogger logger;

	public static void main(String[] args) {
		logger = new DLogger();
		logger.info("Booting");
		Boot.boot();
		logger.relief("Booted");
		cmds();
	}

	public static void cmds() {
		Scanner s = new Scanner(System.in);
		Scanner t;
		while (true) {
			t = new Scanner(s.nextLine());
			String temp = t.next();
			if (temp.equals("close") || temp.equals("Exit")) {
				break;
			} else if (temp.equals("help")) {
				help();
			} else if (temp.equals("render")) {
				render(t.nextInt(), t.nextInt());
			} else if (temp.equals("export")) {
				export(t.nextInt(), t.nextInt());
			} else if (temp.equals("generate")) {
				generate(t.nextInt(), t.nextInt());
			} else
				logger.info("invalid command, try \"help\" for a list of avilable commands");
		}
		t.close();
		s.close();
		Boot.nationdb.write();
	}
	public static void help() {
		logger.info("avilable commands:");
		logger.info("close - closes application");
		logger.info("help - shows this list");
		logger.info("render <Xcoord> <Ycoord> - render a specific chunck at specified coordinates");
		logger.info("export <Xcoord> <Ycoord> - exports a chunk to a png file");
		logger.info("generate - generates a chunk within time");
		logger.info("stress - stress tests the generation function");
	}
	public static void render(int x, int y) {
		try {
			Visuals.visualize(x, y);
			logger.info("Sucessfully rendered chunk at (" + x + ", " + y + ")");
		} catch (InputMismatchException e) {
			logger.info("Invalid command, usage : render <Xcord> <Ycord>");
		}
	}
	public static void generate(int x, int y) {
		StopWatch.start();
		ChunkManager.safeChunk(x, y);
		long duration = StopWatch.stop();
		logger.info("MS: " + duration);
	}
	public static void export(int x, int y) {
		try {
			File f = new File("yeet.png");
			Files.delete(f.toPath());
			new VPanel(ChunkManager.safeChunk(x, y).data).export(f);
			Desktop.getDesktop().open(f);
			// insert future code here
			logger.info("Sucessfully exported chunk at (" + x + ", " + y + ")");
		} catch (InputMismatchException e) {
			logger.info("Invalid command, usage : export <Xcord> <Ycord>");
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		}
	}
}
