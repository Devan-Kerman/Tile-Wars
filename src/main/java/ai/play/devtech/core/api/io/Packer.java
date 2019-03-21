package ai.play.devtech.core.api.io;

import ai.play.devtech.core.api.interfaces.Assembable;
import ai.play.devtech.core.api.interfaces.Packetable;
import java.awt.Point;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/**
 * An advanced arraylist equivalent but for native bytes only, but doesn't have all the
 * same methods
 * 
 * @author devan
 *
 */
public class Packer implements Packetable, Assembable {

	private byte[] arr;
	int last;
	int allocated;
	
	/**
	 * Pre-allocates an amount of bytes to the buffer
	 * @param bytes
	 */
	public void allocate(int bytes) {
		this.allocated += bytes;
		byte[] all = new byte[arr.length+bytes];
		System.arraycopy(arr, 0, all, 0, arr.length);
		arr = all;
	}
	
	/**
	 * Pack multiple bytes at once faster than {@link Packer#pack(byte)}
	 * @param c
	 */
	public void packAll(byte[] c) {
		if(allocated >= c.length) {
			System.arraycopy(c, 0, arr, last, c.length);
			allocated -= c.length;
			last += c.length;
			return;
		}
		allocated = 0;
		byte[] ner = new byte[arr.length + c.length];
		System.arraycopy(arr, 0, ner, 0, arr.length);
		System.arraycopy(c, 0, ner, last, c.length);
		last+=c.length;
		arr = ner;
	}
	
	/**
	 * Reads a set amount of bytes from the input stream (will automatically pre-allocate)
	 * @param stream
	 * @param len
	 */
	public void read(InputStream stream, int len) {
		allocate(len);
		int alloc = len/1024;
		byte[] buffer = new byte[alloc];
		while(true) {
			try {
				int read = stream.read(buffer);
				if(read == -1)
					break;
				packLen(buffer, read);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void packLen(byte[] c, int len) {
		if(allocated >= len) {
			System.arraycopy(c, 0, arr, last, len);
			allocated -= len;
			last += len;
			return;
		}
		allocated = 0;
		byte[] ner = new byte[arr.length + len];
		System.arraycopy(arr, 0, ner, 0, arr.length);
		System.arraycopy(c, 0, ner, last, len);
		last+=len;
		arr = ner;
	}
	
	/**
	 * Packs a single byte into the buffer, not recommended to use.
	 * @param b
	 */
	public void pack(byte b) {
		if(allocated > 0) {
			allocated--;
			arr[last++] = b;
			return;
		}
		byte[] ner = new byte[arr.length+1];
		System.arraycopy(arr, 0, ner, 0, arr.length);
		ner[arr.length] = b;
		arr = ner;
		last++;
		allocated = 0;
	}

	@Override
	public void pack(Packer pack) {
		pack.packAll(Bytes.fromInt(arr.length));
		pack.packAll(arr);
	}
	
	@Override
	public void from(ByteReader reader) {
		packAll(reader.read(reader.readInt()));
	}
	
	/**
	 * Default 0 size packer
	 */
	public Packer() {
		arr = new byte[0];
	}

	/**
	 * Size of the current buffer array
	 * @return
	 */
	public int size() {
		return arr.length;
	}

	/**
	 * Checks if the array is empty
	 * @return
	 */
	public boolean isEmpty() {
		return arr == null || arr.length == 0;
	}
	
	/**
	 * Automatically packs the object into a stream
	 * @param o
	 */
	public <T extends Serializable> void packObj(T o) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(baos));
			oos.writeObject(o);
			oos.flush();
			oos.close();
			baos.flush();
			byte[] ba = baos.toByteArray();
			packInt(ba.length);
			packAll(ba);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Inserts a string into the array
	 * inserts an integer length and then a byte array representing the string
	 * @param strn
	 * 		the string
	 * @param charset
	 * 		what charset to use when serializing
	 */
	public void packString(String strn, Charset charset) {
		byte[] to = strn.getBytes(charset);
		packInt(to.length);
		packAll(to);
	}

	/**
	 * Inserts an integer
	 * @param i
	 */
	public void packInt(int i) {
		packAll(Bytes.fromInt(i));
	}
	
	/**
	 * Inserts a point
	 * (2 ints)
	 * @param p
	 */
	public void packPoint(Point p) {
		packAll(Bytes.fromPoint(p));
	}
	
	/**
	 * Inserts a short
	 * @param s
	 */
	public void packShort(short s) {
		packAll(Bytes.fromShort(s));
	}
	
	/**
	 * Inserts a double
	 * @param d
	 */
	public void packDouble(double d) {
		packAll(Bytes.fromDouble(d));
	}
	
	/**
	 * Inserts a float
	 * @param f
	 */
	public void packFloat(float f) {
		packAll(Bytes.fromFloat(f));
	}
	
	/**
	 * Inserts a long into the array
	 * @param l
	 */
	public void packLong(long l) {
		packAll(Bytes.fromLong(l));
	}
	
	/**
	 * Inserts a list
	 * 1 int for the length, then the values
	 * @param l
	 */
	public <T extends Packetable> void packList(List<T> l) {
		Bytes.fromList(l, this);
	}
	
	/**
	 * Inserts a map
	 * 1 int for the length then the values
	 * @param map
	 */
	public <K extends Packetable, V extends Packetable> void packMap(Map<K, V> map) {
		Bytes.fromMap(map, this);
	}
	
	/**
	 * Automatically packs Packetable items in series
	 * @param packets
	 */
	public void autoPack(Packetable...packets) {
		for(Packetable p : packets)
			p.pack(this);
	}
	
	public void packV(byte...vars) {
		packAll(vars);
	}
	
	public void clear() {
		arr = new byte[0];
	}

	public byte get(int index) {
		return arr[index];
	}

	public byte set(int index, byte element) {
		byte retur = 0;
		if(arr[index] == 0)
			arr[index] = element;
		else {
			arr[index] = element;
			retur = element;
		}
		return retur;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(arr);
	}
	
	public ByteReader getReader() {
		return new ByteReader(arr);
	}
	
	public byte[] unpack() {
		return arr;
	}
	
	public byte[] cpyUnpack() {
		return Arrays.copyOf(arr, arr.length);
	}
	

}
