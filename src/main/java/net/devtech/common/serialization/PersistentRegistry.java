package net.devtech.common.serialization;

import java.util.function.Supplier;

public interface PersistentRegistry {
	/**
	 * register a new class with the default instance supplier
	 *
	 * @param type the class of the persistent
	 * @return the persistent id
	 */
	int register(String name, Class<?> type);

	/**
	 * register a new class with a custom instance supplier
	 *
	 * @param persistent the custom persistent supplier
	 * @param <T> the type of the persistent
	 * @return the persistent id
	 */
	<T extends Persistent> int register(Supplier<T> persistent);

	/**
	 * create a new uninitialized persistent with an id
	 *
	 * @param id the id of the persistent
	 * @param <T> the type of the expected persistent
	 * @return a new instance of the persistent, or null if no persistent with the given name was registered
	 */
	<T extends Persistent> T forId(int id);

	/**
	 * creates a new uninitialized persistent with an id
	 * @param name the name of the persistent
	 * @param <T> the expected type
	 * @return the persistent, or null if no persistent with the given name was registered
	 */
	<T extends Persistent> T forName(String name);

	/**
	 * gets the integer id of the persistent registered with the given name
	 * @param name the name of the persistent
	 * @return the integer id or -1 if none found
	 */
	int idFromName(String name);

	/**
	 * creates a new instance of an object with the given id
	 * @param id the int id of the persistent initializer
	 * @param <O> the type of the object
	 * @return null or a new instance of the object
	 */
	default <O> O getNewOfId(int id) {
		return (O) forId(id).init();
	}

	/**
	 * creates a new instance of an object with the given id and input stream
	 * @param id the id of the persistent initializer
	 * @param input the initialization stream
	 * @param <O> the type of the object
	 * @return null or a new instance of the object
	 */
	default <O> O getNewOfId(int id, PersistentInputStream input) {
		return (O) forId(id).init(input);
	}

	/**
	 * slower, don't use this unless needed
	 * {@link PersistentRegistry#idFromName(String)}
	 * {@link PersistentRegistry#getNewOfId(int)}
	 */
	@Deprecated
	default <O> O getNewOfId(String name) {
		return (O) forName(name).init();
	}

	/**
	 * slower, don't use this unless needed
	 * {@link PersistentRegistry#idFromName(String)}
	 * {@link PersistentRegistry#getNewOfId(int, PersistentInputStream)}
	 */
	@Deprecated
	default <O> O getNewOfId(String name, PersistentInputStream input) {
		return (O) forName(name).init(input);
	}
}
