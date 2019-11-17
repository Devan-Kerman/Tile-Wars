package net.devtech.test.cursed;

import net.devtech.common.serialization.ID;

public interface TestInterface {

	default ID getID0() {
		try {
			return getClass().getMethod("none").getAnnotation(ID.class);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	@ID
	void none();
}
