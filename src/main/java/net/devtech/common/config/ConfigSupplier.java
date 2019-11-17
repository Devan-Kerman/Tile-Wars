package net.devtech.common.config;

/**
 * a special interface in the event multiple config formats want to be supported
 */
public interface ConfigSupplier {
	/**
	 * loads the config
	 */
	void load();

	/**
	 * get the object at the specified path
	 * @param path the path to the object
	 * @return the object at the specified class, or {@link Config#NO_VALUE}
	 */
	Object at(String path);

	/**
	 * sets the object at the specified path
	 * @param path
	 * @param object
	 */
	void set(String path, Object object);

	/**
	 * saves the config
	 */
	void save();
}
