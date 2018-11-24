package main;

import java.io.File;

public class Boot {
	public static void boot() {
		File chunkdir = new File("Chunkdata");
		if(!chunkdir.exists())
			chunkdir.mkdirs();
		
	}
	
}
