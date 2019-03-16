package play.ai.devtech.core.api.structures;

import java.io.Serializable;
import java.lang.reflect.Array;

import play.ai.devtech.core.errors.InvalidTypeException;

public class ArrayBuilder implements Serializable {
	private static final long serialVersionUID = 1L;
	Class<?> type;
	Object array;
	int len;
	
	public ArrayBuilder(Class<?> type) {
		this(Array.newInstance(type, 0), type);
	}
	
	public ArrayBuilder(Object array, Class<?> type) {
		if(!(array.getClass().isArray() || type.isArray()))
			throw new InvalidTypeException(array.getClass() + " | " + type + " is not an array!");
		else if(array.getClass().getComponentType() != type)
			throw new InvalidTypeException(array.getClass().getComponentType() + " != " + type);
		this.type = type;
		this.array = array;
	}
	
	public void append(Object o) {
		Object nen = Array.newInstance(type, len+1);
		System.arraycopy(array, 0, nen, 0, len);
		Array.set(nen, len++, o);
		array = nen;
	}
	
	public void append(Object...os) {
		Object nen = Array.newInstance(type, len+os.length);
		System.arraycopy(array, 0, nen, 0, len);
		System.arraycopy(os, 0, nen, len, os.length);
		len += os.length;
		array = nen;
	}
	
	public Object build() {
		return array;
	}

}
