package net.devtech.common;

import net.devtech.client.ClientSide;
import net.devtech.common.util.Lazy;
import net.devtech.server.ServerSide;
import java.util.function.Supplier;

public enum Environment {
	CLIENT(ClientSide::new),
	SERVER(ServerSide::new),
	UNKNOWN(() -> null);

	private Lazy<GameSide> side;

	<T extends GameSide> Environment(Supplier<T> side) {
		this.side = new Lazy<>((Supplier)side);
	}

	/**
	 * get the game side for the object
	 * @return the game side instance
	 */
	public GameSide getSide() {
		return side.get();
	}

	/**
	 * returns if the current environment has been initialized
	 * @return true/false
	 */
	public boolean isInitialized() {
		return side.isSupplied();
	}

	/**
	 * gets the environment with the specified name
	 * @param name the name of the environment
	 * @return CLIENT, SERVER or UNKNOWN
	 */
	public static Environment getEnvironment(String name) {
		switch (name) {
			case "client": return CLIENT;
			case "server": return SERVER;
			default: return UNKNOWN;
		}
	}



	/**
	 * check if the game has already started
	 * @return
	 */
	public static boolean gameInitialized() {
		return CLIENT.side.isSupplied() || SERVER.side.isSupplied();
	}
}
