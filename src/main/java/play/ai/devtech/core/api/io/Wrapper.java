package play.ai.devtech.core.api.io;

import java.util.function.Function;

import play.ai.devtech.core.api.bytes.Assembable;
import play.ai.devtech.core.api.bytes.ByteReader;
import play.ai.devtech.core.api.bytes.Packer;
import play.ai.devtech.core.api.bytes.Packetable;

public class Wrapper<T> implements Assembable, Packetable {
	
	Function<T, byte[]> conv;
	Function<ByteReader, T> read;
	T obj;
	
	public Wrapper(Function<T, byte[]> conv, Function<ByteReader, T> read) {
		this.conv = conv;
		this.read = read;
	}
	
	public T getObj() {
		return obj;
	}
	
	public Wrapper<T> setObj(T obj) {
		this.obj = obj;
		return this;
	}

	@Override
	public void pack(Packer p) {
		p.packAll(conv.apply(obj));
	}

	@Override
	public void from(ByteReader reader) {
		obj = read.apply(reader);
	}
}
