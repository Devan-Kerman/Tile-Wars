package play.ai.devtech.tilewars;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import play.ai.devtech.control.Claims;
import play.ai.devtech.control.Improvements;
import play.ai.devtech.control.Tiles;
import play.ai.devtech.core.api.area.BPoint;
import play.ai.devtech.core.api.objects.registries.Registry;
import play.ai.devtech.core.nation.Nation;
import play.ai.devtech.core.nation.NationCache;
import play.ai.devtech.core.nation.resources.Resource;
import play.ai.devtech.core.util.DLogger;
import play.ai.devtech.core.util.StopWatch;
import play.ai.devtech.core.world.chunk.Chunk;
import play.ai.devtech.core.world.chunk.ChunkManager;
import play.ai.devtech.core.world.tile.TileEntity;
import play.ai.devtech.runtime.Game;
import play.ai.devtech.runtime.improves.tes.population.ThatchHut;

/**
 * Main class of the game
 * 
 * @author devan
 *
 */
public class Serverside {

	public static Registry<TileEntity> teRegister;
	public static Game game;
	public static void main(String[] args) {
		DLogger.info("Starting game...");
		game = new Game();
		DLogger.info("Booting...");
		Boot.boot();
		DLogger.info("Starting TileEntity pools...");
		teRegister = new Registry<>();
		teRegister.put(0, ThatchHut.class);
		
		DLogger.relief("Booted");
		cmds();
	}
	
	/**
	 * Prints a usually outdated help text
	 */
	public static void help() {
		DLogger.info("avilable commands:");
		DLogger.info("close/exit/EXIT - closes application");
		DLogger.info("help - shows this list");
		DLogger.info("export <Xcoord> <Ycoord> - exports a chunk to a png file");
		DLogger.info("generate - generates a chunk within time");
		DLogger.info("create - creates a new nation");
		DLogger.info("addresources <ResourceName> <amount> <nationid>");
		DLogger.info("displaystats <Nation ID> - shows the resources and stats this nation has");
		DLogger.info("testser <Nation ID> - tests nation serialization");
	}

	/**
	 * Commands and shit
	 */
	public static void cmds() {
		Scanner s = new Scanner(System.in);
		Scanner t;
		label: while (true) {
			try {
				t = new Scanner(s.nextLine());
				String temp = t.next();
				switch (temp) {
				case "close":
					DLogger.warn("Writing all chunks in cache to disk...");
					break label;
				case "Exit":
					DLogger.warn("Writing all chunks in cache to disk...");
					break label;
				case "help":
					help();
					break;
				case "export":
					export(t.nextInt(), t.nextInt());
					break;
				case "generate":
					generate(t.nextInt(), t.nextInt());
					break;
				case "create":
					createNation();
					break;
				case "addresources":
					addResources(t.next(), t.nextInt(), t.nextInt());
					break;
				case "displaystats":
					display(t.nextInt());
					break;
				case "testser":
					getN(t.nextInt());
					break;
				case "EXIT":
					DLogger.warn("Writing all chunks in cache to disk...");
					ChunkManager.writeAll();
					break;
				case "setImp":
					Improvements.improve(new BPoint(t.nextByte(), t.nextByte()), ChunkManager.safeChunk(t.nextInt(), t.nextInt()), t.nextInt());
					DLogger.relief("Placed!");
					break;
				case "claim":
					Claims.claim(new Point(t.nextInt(), t.nextInt()), new BPoint(t.nextByte(), t.nextByte()), t.nextInt());
					DLogger.relief("Claimed!");
					break;
				case "run":
					game.accept(NationCache.getNation(t.nextInt()));
					DLogger.debug("Ran!");
					break;
				case "view":
					DLogger.debug("\n");
					DLogger.info(Tiles.toString(ChunkManager.safeChunk(t.nextInt(), t.nextInt()), new BPoint(t.nextByte(), t.nextByte())));
					break;
				default:
					DLogger.info("invalid command, try \"help\" for a list of avilable commands");
					break;
				}
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
	 * 
	 * @param nationid
	 */
	public static void display(int nationid) {
		Nation n = NationCache.getNation(nationid);
		DLogger.debug(n.toString());
		NationCache.save(n);
	}

	/**
	 * Tests serialization and deserialization
	 * 
	 * @param nationid int id of nation
	 */
	public static void getN(int nationid) {
		NationCache.save(NationCache.getNation(nationid));
		DLogger.relief("All's good :)");
	}

	/**
	 * Adds the resources to the target nation
	 * 
	 * @param name   ResourceID
	 * @param amount amount of the resource
	 * @param nid    id of the nation
	 */
	public static void addResources(String name, int amount, int nid) {
		Nation n = NationCache.getNation(nid);
		n.getInventory().put(Enum.valueOf(Resource.class, name), amount);
		NationCache.save(n);
		DLogger.debug("Added Resources");
	}

	/**
	 * Creates a new nation without a password, for server testing only
	 */
	public static void createNation() {
		Nation n = NationCache.newNation();
		NationCache.save(n);
	}

	/**
	 * Generates/Reads a new chunk
	 * 
	 * @param x chunk x int
	 * @param y chunk y int
	 */
	public static void generate(int x, int y) {
		StopWatch.start();
		ChunkManager.safeChunk(x, y);
		long duration = StopWatch.stop();
		DLogger.info("MS: " + duration);
	}

	/**
	 * Same thing as {@link #render(int, int)} but opens it in default image viewer
	 * for computer
	 * 
	 * @param x Chunk X int
	 * @param y Chunk Y int
	 */
	public static void export(int x, int y) {
		try {
			File f = new File("yeet.png");
			Chunk chunk = ChunkManager.safeChunk(x, y);
			BufferedImage bi = new BufferedImage(Chunk.CHUNKSIZE*5, Chunk.CHUNKSIZE*5, BufferedImage.TYPE_INT_RGB);
			Graphics g2d = bi.createGraphics();
			for (int c = 0; c < chunk.tiles.length; c++)
				for (int r = 0; r < chunk.tiles[c].length; r++) {
					g2d.setColor(getColor(chunk.tiles[c][r].elevation));
					g2d.fillRect((int) (c * 5), (int) (r * 5), 5, 5);
				}
			ImageIO.write(bi, "png", f);
			Desktop.getDesktop().open(f);
			// insert future code here
			DLogger.info("Sucessfully exported chunk at (" + x + ", " + y + ")");
		} catch (InputMismatchException e) {
			DLogger.info("Invalid command, usage : export <Xcord> <Ycord>");
		} catch (IOException e) {
			DLogger.error(e.getLocalizedMessage());
		}
	}
	private static Color getColor(int temp) {
		if (temp >= 240)
			return new Color(255, 255, 255);
		else if (temp >= 200 && temp < 240)
			return new Color(68 + (int) ((temp - 200) * 4.675), 44 + (int) ((temp - 200) * 5.275),
					0 + (int) ((temp - 200) * 6.375));
		else if (temp >= 150 && temp < 200)
			return new Color(0 + (int) ((temp - 150) * 1.36), 112 + (int) ((temp - 150) * 1.36),
					3 - (int) ((temp - 150) * 0.06));
		else if (temp >= 100 && temp < 150)
			return new Color(0, 221 - (int) ((temp - 100) * 2.18), 20 + (int) ((temp - 100) * 0.34));
		else if (temp >= 20 && temp < 100)
			return new Color(0, 255 - (int) ((temp - 20) * 0.05), 6 + (int) ((temp - 20) * 0.175));
		else if (temp >= 1 && temp < 20)
			return new Color(255 - (int) ((temp - 1) * 12.75), 250 + (int) ((temp - 1) * 0.25),
					0 + (int) ((temp - 1) * 0.3));
		else if (temp == 0)
			return new Color(0, 242, 255);
		else if (temp <= -1 && temp > -20)
			return new Color(0, 199 + (int) ((temp + 1) * 2.55), 255);
		else if (temp <= -20 && temp > -100)
			return new Color(0, 148 + (int) ((temp + 20) * 0.5), 255 + (int) ((temp + 20) * 1.38));
		else if (temp <= -100 && temp > -150)
			return new Color(0, 108 + (int) ((temp + 100) * 0.92), 186 + (int) ((temp + 100) * 0.06));
		else if (temp <= -150 && temp > -200)
			return new Color(0, 62 + (int) ((temp + 150) * 1.14), 183 + (int) ((temp + 150) * 1.28));
		else if (temp <= -200 && temp > -240)
			return new Color(0, 5 + (int) ((temp + 200) * 0.125), 119 + (int) ((temp + 200) * 2.975));
		else if (temp <= -240)
			return new Color(0, 0, 0);
		DLogger.error("Invalid Elevation! " + temp);
		return Color.BLACK;
	}
}
