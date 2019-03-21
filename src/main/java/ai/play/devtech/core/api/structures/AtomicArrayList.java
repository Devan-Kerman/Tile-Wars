package ai.play.devtech.core.api.structures;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicArrayList<E> extends ArrayList<E> {
	private static final long serialVersionUID = -6278013388326608043L;
	AtomicBoolean trim = new AtomicBoolean(false);

	@Override
	public void trimToSize() {
		while (true)
			if (!trim.get())
				break;
		trim.set(true);
		super.trimToSize();
		trim.set(false);
	}

	@Override
	public void ensureCapacity(int minCapacity) {
		while (true)
			if (!trim.get())
				break;
		trim.set(true);
		super.ensureCapacity(minCapacity);
		trim.set(false);
	}

	@Override
	public Object clone() {
		while (true)
			if (!trim.get())
				break;
		trim.set(true);
		Object o = super.clone();
		trim.set(false);
		return o;
	}

	@Override
	public E set(int index, E element) {
		while (true)
			if (!trim.get())
				break;
		trim.set(true);
		E e = super.set(index, element);
		trim.set(false);
		return e;
	}

	@Override
	public boolean add(E e) {
		while (true)
			if (!trim.get())
				break;
		trim.set(true);
		boolean b = super.add(e);
		trim.set(false);
		return b;
	}

	@Override
	public void add(int index, E e) {
		while (true)
			if (!trim.get())
				break;
		trim.set(true);
		super.add(index, e);
		trim.set(false);
	}

	@Override
	public E remove(int index) {
		while (true)
			if (!trim.get())
				break;
		trim.set(true);
		E e = super.remove(index);
		trim.set(false);
		return e;
	}
	
	@Override
	public boolean remove(Object o) {
		while (true)
			if (!trim.get())
				break;
		trim.set(true);
		super.remove(o);
		trim.set(false);
		return true;
	}
	
	@Override
	public void clear() {
		while (true)
			if (!trim.get())
				break;
		trim.set(true);
		super.clear();
		trim.set(false);
	}
}
