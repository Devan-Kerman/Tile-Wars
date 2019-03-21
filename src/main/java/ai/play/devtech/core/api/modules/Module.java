/**
 * 
 */
package ai.play.devtech.core.api.modules;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(TYPE)
/**
 * @author devan
 *
 */
public @interface Module {
	public Class<? extends Mod> getMod();
}
