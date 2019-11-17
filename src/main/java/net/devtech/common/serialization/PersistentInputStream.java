package net.devtech.common.serialization;

import org.jetbrains.annotations.NotNull;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PersistentInputStream extends DataInputStream {
	private final PersistentRegistry registry;
	public PersistentInputStream(@NotNull InputStream in, PersistentRegistry registry) {
		super(in);
		this.registry = registry;
	}

	public <T> T nextObject() throws IOException {
		Persistent<T> persistent = registry.forId(readInt());
		return persistent.init(this);
	}


}
