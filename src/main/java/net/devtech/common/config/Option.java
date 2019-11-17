package net.devtech.common.config;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Option {
	/**
	 * Yaml / json / whatever path
	 * @return
	 */
	String path();

	/**
	 * return true if getter for the config option, ignore if field
	 * @return
	 */
	boolean getter() default false;
}
