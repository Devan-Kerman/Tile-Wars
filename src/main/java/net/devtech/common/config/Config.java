package net.devtech.common.config;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * @implNote when extending config, annotate a variable or setter method with the {@link Option} annotation to specify that it is a config option
 * they must be static, and can optionally be final, for default values, simply set the variable to its default value in its declaration
 */
public abstract class Config {
	public static final Object NO_VALUE = new Object();
	private final ConfigSupplier supplier;
	public Config(ConfigSupplier supplier) {
		supplier.load();
		this.supplier = supplier;
		Class<? extends Config> curr = getClass();
		iter(supplier, curr::getDeclaredFields, (f, o) -> {
			try {
				f.set(null, o);
			} catch (ReflectiveOperationException e) {
				throw new RuntimeException(e);
			}
		});
		iter(supplier, curr::getDeclaredMethods, (m, o) -> {
			try {
				if(!m.getAnnotation(Option.class).getter())
					m.invoke(null, o);
			} catch (ReflectiveOperationException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public void save() {
		Class<? extends Config> curr = getClass();
		iter(supplier, curr::getDeclaredFields, (f, o) -> {
			try {
				supplier.set(f.getAnnotation(Option.class).path(), f.get(null));
			} catch (ReflectiveOperationException e) {
				throw new RuntimeException(e);
			}
		});
		iter(supplier, curr::getDeclaredMethods, (m, o) -> {
			try {
				Option option = m.getAnnotation(Option.class);
				if(option.getter())
					supplier.set(option.path(), m.invoke(null));
			} catch (ReflectiveOperationException e) {
				throw new RuntimeException(e);
			}
		});
		supplier.save();
	}

	private <T extends AccessibleObject & Member> void iter(ConfigSupplier supplier, Supplier<T[]> components, BiConsumer<T, Object> iterator) {
		for (T component : components.get())
			if (Modifier.isStatic(component.getModifiers())) {
				Option option = component.getAnnotation(Option.class);
				if (option != null) {
					component.setAccessible(true);
					Object object = supplier.at(option.path());
					if (object != NO_VALUE) iterator.accept(component, object);
				}
			}
	}
}
