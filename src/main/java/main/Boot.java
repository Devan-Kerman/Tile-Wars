package main;

import java.io.File;

import main.networking.Network;

/**
 * Reponsible for setting up the server
 * @author devan
 *
 */
public class Boot {
	/**
	 * Utility Class
	 */
	private Boot() {}
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
		File chunkdir = new File("Chunkdata");
		if(!chunkdir.exists())
			chunkdir.mkdirs();
		DLogger.info("Booted!");
	}
	
}
