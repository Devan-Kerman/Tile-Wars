package net.devtech.client;

import net.devtech.common.Environment;
import net.devtech.common.GameSide;
import net.devtech.common.world.WorldProvider;

public class ClientSide implements GameSide {

	@Override
	public void init() {
		if(Environment.SERVER.isInitialized())
			throw new IllegalStateException("Server already initialized!");
		// TODO implement
	}

	@Override
	public WorldProvider provider() {
		// TODO implement
		return null;
	}

	@Override
	public Environment getEnvironment() {
		return Environment.CLIENT;
	}
}
