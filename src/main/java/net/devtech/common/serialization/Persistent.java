package net.devtech.common.serialization;

/**
 * a pseudo constructor / serializer
 * @param <T>
 */
public interface Persistent<T> {
	/**
	 * initialize this class with an input stream
	 * @param stream the stream to initialize
	 * @return the newly initialized object
	 */
	T init(PersistentInputStream stream);

	/**
	 * initialize this class without any stream
	 * @return the newly initialized object
	 */
	T init();

	/**
	 * serialize the object to the outputstream
	 * @param stream the stream to serialize to
	 */
	void write(PersistentOutputStream stream);

	/**
	 * a unique identifier for this class
	 * @return the id of this class
	 */
	String getName();
}