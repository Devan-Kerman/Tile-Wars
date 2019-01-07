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

/**
 * Main class of the game
 * @author devan
 *
 */
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
			} else if(temp.equals("EXIT")) {
				DLogger.warn("Writing all chunks in cache to disk...");
				ChunkManager.writeAll();
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
	
	/**
	 * Prints the nations toString
	 * @param nationid
	 */
	public static void display(int nationid) {
		Nation n = NationCache.getNation(nationid);
		DLogger.debug(n.toString());
		n.save();
	}
	
	/**
	 * Tests serialization and deserialization
	 * @param nationid
	 * 		int id of nation
	 */
	public static void getN(int nationid) {
		NationCache.getNation(nationid).save();
	}
	
	/**
	 * Executes a nation
	 * @param nationid
	 * 		int id of nation
	 */
	public static void execNation(int nationid) {
		Nation n = NationCache.getNation(nationid);
		GameManager.runNation(n);
		n.save();
	}
	
	/**
	 * Adds an improvement to the nation at the specified coordinates provdided he has the resources
	 * @param tp
	 * 		Point of the placement
	 * @param iid
	 * 		Improvement id
	 * @param nationid
	 * 		int id of the nation
	 */
	public static void addImprove(TilePoint tp, int iid, int nationid) {
		Nation n = NationCache.getNation(nationid);
		GameManager.addImprovement(n, tp, Improvement.getImprovement(iid));
		n.save();
	}
	
	/**
	 * Adds the resources to the target nation
	 * @param name
	 * 		ResourceID
	 * @param amount
	 * 		amount of the resource
	 * @param nid
	 * 		id of the nation
	 */
	public static void addResources(String name, int amount, int nid) {
		Nation n = NationCache.getNation(nid);
		n.getInventory().put(Enum.valueOf(Resource.class, name),amount);
		n.save();
		DLogger.debug("Added Resources");
	}
	
	/**
	 * Creates a new nation without a password, for server testing only
	 */
	public static void createNation() {
		Nation n = NationCache.newNation();
		n.save();
	}
	
	/**
	 * Prints a usually outdated help text
	 */
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
	
	/**
	 * Generates/Reads the specified chunk from the memory/disk, and renders it on the screen
	 * @param x
	 * 		chunk x int
	 * @param y
	 * 		chunk y int
	 */
	public static void render(int x, int y) {
		try {
			Visuals.visualize(x, y);
			DLogger.info("Sucessfully rendered chunk at (" + x + ", " + y + ")");
		} catch (InputMismatchException e) {
			DLogger.info("Invalid command, usage : render <Xcord> <Ycord>");
		}
	}
	/**
	 * Generates/Reads a new chunk
	 * @param x
	 * 		chunk x int
	 * @param y
	 * 		chunk y int
	 */
	public static void generate(int x, int y) {
		StopWatch.start();
		ChunkManager.safeChunk(x, y);
		long duration = StopWatch.stop();
		DLogger.info("MS: " + duration);
	}
	
	/**
	 * Same thing as {@link #render(int, int)} but opens it in default image viewer for computer
	 * @param x
	 * 		Chunk X int
	 * @param y
	 * 		Chunk Y int
	 */
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
