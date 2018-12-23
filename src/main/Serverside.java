package main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.InputMismatchException;
import java.util.Scanner;

import game.GameManager;
import game.nation.Nation;
import game.nation.NationCache;
import game.nation.TilePoint;
import game.resources.Resource;
import game.tile.Improvement;
import util.datamanagement.ChunkManager;
import util.testing.StopWatch;
import visualizer.VPanel;

public class Serverside {

	public static void main(String[] args) {
		DLogger.info("Booting");
		Boot.boot();
		DLogger.relief("Booted");
		cmds();
		
	}

	/**
	 * Commands and shit
	 */
	public static void cmds() {
		Scanner s = new Scanner(System.in);
		Scanner t;
		while (true) {
			try {
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
			} else if (temp.equals("create")) {
				createNation();
			} else if (temp.equals("addresources")) {
				addResources(t.next(), t.nextInt(), t.nextInt());
			} else if (temp.equals("addimprovement")) {
				addImprove(new TilePoint(t.nextInt(), t.nextInt(), t.nextByte(), t.nextByte()), t.nextInt(), t.nextInt());
			} else if (temp.equals("execnation")) {
				execNation(t.nextInt());
			} else if (temp.equals("displaystats")) {
				display(t.nextInt());
			} else if (temp.equals("testser")) {
				getN(t.nextInt());
			} else
				DLogger.info("invalid command, try \"help\" for a list of avilable commands");
			} catch (Exception e) {
				e.printStackTrace();
				DLogger.warn(e.getMessage());
			}
		}
		t.close();
		s.close();
	}
	
	public static void display(int nationid) {
		Nation n = NationCache.getNation(nationid);
		DLogger.debug(n.toString());
		n.save();
	}
	
	public static void getN(int nationid) {
		NationCache.getNation(nationid).save();
	}
	
	public static void execNation(int nationid) {
		Nation n = NationCache.getNation(nationid);
		GameManager.runNation(n);
		n.save();
	}
	
	public static void addImprove(TilePoint tp, int iid, int nationid) {
		Nation n = NationCache.getNation(nationid);
		GameManager.addImprovement(n, tp, Improvement.getImprovement(iid));
		n.save();
	}
	
	public static void addResources(String name, int amount, int nid) {
		Nation n = NationCache.getNation(nid);
		n.getInventory().put(Enum.valueOf(Resource.class, name),amount);
		n.save();
		DLogger.debug("Added Resources");
	}
	public static void createNation() {
		Nation n = NationCache.newNation();
		n.save();
	}
	
	public static void help() {
		DLogger.info("avilable commands:");
		DLogger.info("close - closes application");
		DLogger.info("help - shows this list");
		DLogger.info("render <Xcoord> <Ycoord> - render a specific chunck at specified coordinates");
		DLogger.info("export <Xcoord> <Ycoord> - exports a chunk to a png file");
		DLogger.info("generate - generates a chunk within time");
		DLogger.info("create - creates a new nation");
		DLogger.info("addresources <ResourceName> <amount> <nationid>");
		DLogger.info("addimprovment <ChunkX> <ChunkY> <TileX> <TileY> <ImprovementID> <NationID>");
	}
	public static void render(int x, int y) {
		try {
			Visuals.visualize(x, y);
			DLogger.info("Sucessfully rendered chunk at (" + x + ", " + y + ")");
		} catch (InputMismatchException e) {
			DLogger.info("Invalid command, usage : render <Xcord> <Ycord>");
		}
	}
	public static void generate(int x, int y) {
		StopWatch.start();
		ChunkManager.safeChunk(x, y);
		long duration = StopWatch.stop();
		DLogger.info("MS: " + duration);
	}
	public static void export(int x, int y) {
		try {
			File f = new File("yeet.png");
			Files.delete(f.toPath());
			new VPanel(ChunkManager.safeChunk(x, y).data).export(f);
			Desktop.getDesktop().open(f);
			// insert future code here
			DLogger.info("Sucessfully exported chunk at (" + x + ", " + y + ")");
		} catch (InputMismatchException e) {
			DLogger.info("Invalid command, usage : export <Xcord> <Ycord>");
		} catch (IOException e) {
			DLogger.error(e.getLocalizedMessage());
		}
	}
}
