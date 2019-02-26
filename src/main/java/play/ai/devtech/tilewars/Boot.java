package play.ai.devtech.tilewars;

import java.io.File;

import play.ai.devtech.core.util.DLogger;

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
	
	/**
	 * Initializes the game
	 */
	public static void boot() {
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
