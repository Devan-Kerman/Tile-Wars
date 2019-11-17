package net.devtech.server;

import net.devtech.common.Environment;
import net.devtech.common.GameSide;
import net.devtech.common.world.WorldProvider;

public class ServerSide implements GameSide {
	@Override
	public void init() {
		if(Environment.CLIENT.isInitialized())
			throw new IllegalStateException("Client already initialized!");
		// TODO implement
	}

	@Override
	public WorldProvider provider() {
		// TODO implement
		return null;
	}

	@Override
	public Environment getEnvironment() {
		return Environment.SERVER;
	}
}
