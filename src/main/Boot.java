package main;

import java.io.File;

import main.networking.Network;
import util.datamanagement.GenericDatabase;

public class Boot {
	private Boot() {}
	public static GenericDatabase<Integer> nationdb;
	public static Network mainet;
	/**
	 * Initializes the game
	 */
	public static void boot() {
		DLogger.info("Initializing network");
		mainet = new Network(6702);
		DLogger.info("Starting network"); 
		new Thread(mainet).start();
		DLogger.info("Reading data");
		nationdb = new GenericDatabase<>("General.data");
		File chunkdir = new File("Chunkdata");
		if(!chunkdir.exists())
			chunkdir.mkdirs();
		DLogger.info("Booted!");
	}
	
}
