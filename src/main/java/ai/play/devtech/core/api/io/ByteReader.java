package ai.play.devtech.core.api.io;

import ai.play.devtech.core.api.interfaces.Assembable;
import ai.play.devtech.tilewars.DLogger;
import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;

/**
 * A reader that can read objects and primitives from a sequence of bytes
 * 
 * @author devan
 *
 */
public class ByteReader {
	protected byte[] data;
	protected int index;

	public ByteReader(byte[] data) {
		this.data = data;
	}

	/**
	 * Reads the next byte in the array
	 * 
	 * @return ze byte
	 */
	public byte read() {
		return data[index++];
	}

	/**
	 * Reads the rest of the array without changing the index counter
	 * 
	 * @return
	 */
	public byte[] peekNext() {
		byte[] next = new byte[data.length - index];
		System.arraycopy(data, index, next, 0, next.length);
		return next;
	}

	/**
	 * Reads the next double in the array
	 * 
	 * @return
	 */
	public double readDouble() {
		return Bytes.toDouble(read(8));
	}

	/**
	 * Reads the next long in the array
	 * 
	 * @return
	 */
	public long readLong() {
		return Bytes.toLong(read(8));
	}

	@SuppressWarnings("unchecked")
	public <T> T readObject() {
		int len = readInt();
		try {
			ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(new ByteArrayInputStream(read(len))));
			return (T) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * checks if the buffer has any remaining data
	 * 
	 * @return
	 */
	public boolean hasNext() {
		return index < data.length;
	}

	/**
	 * Reads a string from the buffer -> reads an int with the length
	 * 
	 * @param charset the charset in which to perform the operation
	 * @return
	 */
	public String readString(Charset charset) {
		int ln = readInt();
		return new String(read(ln), charset);
	}

	/**
	 * Reads and int from the buffer
	 * 
	 * @return
	 */
	public int readInt() {
		return Bytes.toInt(data[index++], data[index++], data[index++], data[index++]);
	}

	/**
	 * Reads a float from the buffer
	 * 
	 * @return
	 */
	public float readFloat() {
		return Bytes.toFloat(data[index++], data[index++], data[index++], data[index++]);
	}

	/**
	 * Reads 2 ints from the buffer and assembles them into a point
	 * 
	 * @return
	 */
	public Point readPoint() {
		return Bytes.toPoint(data[index++], data[index++], data[index++], data[index++], data[index++], data[index++],
				data[index++], data[index++]);
	}

	/**
	 * Reads a short from the buffer
	 * 
	 * @return
	 */
	public short readShort() {
		return Bytes.toShort(data[index++], data[index++]);
	}

	/**
	 * Reads a list of assembable objects from the buffer
	 * 
	 * @param clas class of T
	 * @return
	 */
	public <T extends Assembable> List<T> readList(Class<T> clas) {
		return Bytes.toList(this, clas);
	}

	/**
	 * Reads a HashMap of Assambable objects from the buffer
	 * 
	 * @param kl
	 * @param vl
	 * @return
	 */
	public <K extends Assembable, V extends Assembable> Map<K, V> readMap(Class<K> kl, Class<V> vl) {
		return Bytes.toMap(this, kl, vl);
	}

	/**
	 * Consume a set amount of bytes
	 * 
	 * @param cons   consumer
	 * @param amount amount to consume
	 */
	public void read(Consumer<Byte> cons, int amount) {
		for (int x = 0; x < amount; x++)
			cons.accept(read());
	}

	/**
	 * Read x amount of bytes
	 * 
	 * @param len x
	 * @return
	 */
	public byte[] read(int len) {
		byte[] arr = new byte[len];
		System.arraycopy(data, index, arr, 0, len);
		index += len;
		return arr;
	}

	/**
	 * Automatically create a new instance and read it from the buffer
	 * @param clas
	 * 		the type
	 * @return
	 */
	public <T extends Assembable> T read(Class<T> clas) {
		try {
			T t = clas.newInstance();
			t.from(this);
			return t;
		} catch (InstantiationException | IllegalAccessException e) {
			DLogger.error("No Zero Parameter Constructor for: " + clas);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * The total length of the buffer
	 * @return
	 */
	public int len() {
		return data.length;
	}
	
	/**
	 * The amount of bytes left in the buffer
	 * @return
	 */
	public int left() {
		return data.length-index;
	}

}
