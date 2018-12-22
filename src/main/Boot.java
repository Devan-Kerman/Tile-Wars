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
		Serverside.logger.info("Initializing network");
		mainet = new Network(6702);
		Serverside.logger.info("Starting network"); 
		new Thread(mainet).start();
		Serverside.logger.info("Reading data");
		nationdb = new GenericDatabase<>("General.data");
		File chunkdir = new File("Chunkdata");
		if(!chunkdir.exists())
			chunkdir.mkdirs();
		Serverside.logger.info("Booted!");
	}
	
}
