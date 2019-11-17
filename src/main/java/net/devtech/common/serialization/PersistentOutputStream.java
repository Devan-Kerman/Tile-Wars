package net.devtech.common.serialization;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PersistentOutputStream extends DataOutputStream {
	private PersistentRegistry registry;
	public PersistentOutputStream(OutputStream out, PersistentRegistry registry) {
		super(out);
		this.registry = registry;
	}

	public void write(Persistent<?> persistent) throws IOException {
		writeInt(registry.idFromName(persistent.getName()));
		persistent.write(this);
	}
}
