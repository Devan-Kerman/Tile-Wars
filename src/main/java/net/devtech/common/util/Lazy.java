package net.devtech.common.util;

import java.util.function.Supplier;

/**
 * represents an object that can exist, and is created on access rather than immediately
 * @param <T> the type of the object that will be created
 */
public class Lazy<T> {
	private T value;
	private boolean supplied;
	private Supplier<T> supplier;

	/**
	 * creates a new lazy from a source
	 * @param supplier the instance initializer
	 */
	public Lazy(Supplier<T> supplier) {
		this.supplier = supplier;
	}

	/**
	 * creates a new lazy from a source
	 * @param value
	 */
	public Lazy(T value) {
		supplied = true;
		this.value = value;
	}

	/**
	 * returns a/the lazily made value
	 * @return
	 */
	public T get() {
		if (supplied) return value;
		else {
			supplied = true;
			return value = supplier.get();
		}
	}

	/**
	 * returns true if the value has been accessed
	 * @return
	 */
	public boolean isSupplied() {
		return supplied;
	}
}
