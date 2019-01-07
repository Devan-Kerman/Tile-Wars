package main;

import java.io.File;

import main.networking.Network;

/**
 * Responsible for setting up the server
 * @author devan
 *
 */
public class Boot {
	/**
	 * Utility Class
	 */
	private Boot() {}
	public static Network mainnet;
	
	/**
	 * Initializes the game
	 */
	public static void boot() {
		DLogger.info("Initializing network");
		mainnet = new Network(6702);
		DLogger.info("Starting network"); 
		new Thread(mainnet).start();
		DLogger.info("Reading data");
		File chunkdir = new File("Chunkdata");
		if(!chunkdir.exists()) {
			boolean success = chunkdir.mkdirs();
			if(!success)
				DLogger.info("Inability to create Chunkdata Folder!!!");
		}
		DLogger.info("Booted!");
	}
	
}
