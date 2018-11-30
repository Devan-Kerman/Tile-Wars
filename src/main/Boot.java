package main;

import java.io.File;

import util.datamanagement.GenericDatabase;

public class Boot {
	public static GenericDatabase<Integer> nationdb;
	public static void boot() {
		new Network(6702).start();
		nationdb = new GenericDatabase<Integer>("General.data");
		File chunkdir = new File("Chunkdata");
		if(!chunkdir.exists())
			chunkdir.mkdirs();
	}
	
}
