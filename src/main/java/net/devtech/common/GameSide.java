package net.devtech.common;

import net.devtech.common.world.WorldProvider;

public interface GameSide {
	void init();
	WorldProvider provider();
	Environment getEnvironment();
}
