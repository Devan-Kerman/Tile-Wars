package play.ai.devtech.tilewars;

import java.io.File;

import com.impetus.annovention.ClasspathDiscoverer;
import com.impetus.annovention.Discoverer;
import com.impetus.annovention.listener.ClassAnnotationDiscoveryListener;

import play.ai.devtech.core.api.modules.Mod;
import play.ai.devtech.core.api.modules.Module;
import play.ai.devtech.runtime.Game;

/**
 * Responsible for setting up the server
 * 
 * @author devan
 *
 */
public class Boot {
	/**
	 * Utility Class
	 */
	private Boot() {
	}

	/**
	 * Initializes the game
	 */
	public static void boot() {
		DLogger.info("Reading data");
		File chunkdir = new File("Chunkdata");
		if (!chunkdir.exists()) {
			boolean success = chunkdir.mkdirs();
			if (!success)
				DLogger.info("Inability to create Chunkdata Folder!!!");
		}
		DLogger.info("Booted!");
	}

	static void load(Game game) {
		Discoverer disc = new ClasspathDiscoverer();
		disc.addAnnotationListener(new ClassAnnotationDiscoveryListener() {
			@Override
			public String[] supportedAnnotations() {
				return new String[] { Module.class.getName() };
			}

			@Override
			public void discovered(String clas, String annotation) {
				try {
					game.addMod((Mod) Class.forName(clas).newInstance());
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		disc.discover();
	}

}
