package main;

import java.io.File;

import main.networking.Network;
import util.datamanagement.GenericDatabase;

public class Boot {
	public static GenericDatabase<Integer> nationdb;
	public static Network mainet = new Network(6702);
	public static void boot() {
		mainet.start();
		nationdb = new GenericDatabase<>("General.data");
		File chunkdir = new File("Chunkdata");
		if(!chunkdir.exists())
			chunkdir.mkdirs();
	}
	
}
