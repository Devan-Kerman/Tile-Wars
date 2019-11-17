package net.devtech.common.serialization;

import io.github.microevents.util.IDHolder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.function.Supplier;

public class PersistentRegistryImpl implements PersistentRegistry {
	private final Int2ObjectMap<Supplier<Persistent<?>>> objectIds = new Int2ObjectOpenHashMap<>();
	private final IDHolder holder = new IDHolder(1024);

	@Override
	public int register(String name, Class<?> type) {
		return 0; // TODO auto impl
	}

	@Override
	public <T extends Persistent> int register(Supplier<T> persistent) {
		return 0;
	}

	@Override
	public <T extends Persistent> T forId(int id) {
		return null;
	}

	@Override
	public <T extends Persistent> T forName(String name) {
		return null;
	}

	@Override
	public int idFromName(String name) {
		return 0;
	}
}
