package net.devtech;

import net.devtech.common.Environment;
import net.devtech.common.reflection.Reflection;

public class TileWars {
	public static final Environment ENVIRONMENT = Environment.UNKNOWN; // reset via reflection

	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Select environment server / client ex: java TileWars.jar client");
			System.exit(0);
		}

		Environment environment = Environment.getEnvironment(args[0]);
		Reflection.setField(TileWars.class, null, "ENVIRONMENT", environment);
		if(environment == Environment.UNKNOWN) {
			System.out.println("Unknown environment " + args[0] + " options are \"client\" or \"server\"!");
			System.exit(0);
		}


	}

	public static boolean isServer() {
		return ENVIRONMENT == Environment.SERVER;
	}
}
