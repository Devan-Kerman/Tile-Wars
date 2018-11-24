package main;

import java.io.File;

import util.datamanagement.GenericDatabase;

public class Boot {
	public static GenericDatabase<Integer> nationdb;
	public static void boot() {
		nationdb = new GenericDatabase<Integer>("General.data");
		File chunkdir = new File("Chunkdata");
		if(!chunkdir.exists())
			chunkdir.mkdirs();
	}
	
}
