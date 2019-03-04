package play.ai.devtech.core.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * For logging and debugging, helps because it shows the recent stack trace so u can see where the print statement was
 * @author devan
 *
 */
public class DLogger {
	
	/**
	 * Utility class
	 */
	private DLogger() {}

	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static PrintWriter errout;
	
	public static void writeError(Exception e) {
		e.printStackTrace(errout);
		errout.flush();
	}
	static {
		try {
			errout = new PrintWriter(new BufferedWriter(new FileWriter("errors.txt")));
		} catch (IOException e) {
			errout = null;
			e.printStackTrace();
		}
	}
	
	/**
	 * Initializes the logger
	 */
	static {
		LOGGER.setLevel(Level.FINEST);
		LOGGER.setUseParentHandlers(false);
		LOGGER.addHandler(new Handler() {

			@Override
			public void close() {
				/* dunno what this does */
			}

			@Override
			public void flush() {
				/* dunno what this does */
			}

			@Override
			public void publish(LogRecord r) {
				Level l = r.getLevel();
				if(l.intValue() != Level.SEVERE.intValue())
					System.out.printf("%s -> %s : %s\n", r.getParameters()[0], l.toString(), r.getMessage());
				else
					System.err.printf("(%s:%d) -> %s : %s\n", r.getParameters()[0], (Integer)r.getParameters()[1], l.toString(), r.getMessage());
			}
		});

	}

	/**
	 * Puts an error message on the consol with the stack trace
	 * @param info
	 */
	public static void error(String info) {
		LOGGER.log(Level.SEVERE, info, Thread.currentThread().getStackTrace()[2]);
	}

	/**
	 * Puts an warn message on the consol with the stack trace
	 * @param info
	 */
	public static void warn(String info) {
		LOGGER.log(Level.WARNING, info, Thread.currentThread().getStackTrace()[2]);
	}

	/**
	 * Puts an info message on the consol with the stack trace
	 * @param info
	 */
	public static void info(String info) {
		LOGGER.log(Level.INFO, info, Thread.currentThread().getStackTrace()[2]);
	}

	/**
	 * Puts an debug message on the consol with the stack trace
	 * @param info
	 */
	public static void debug(String info) {
		LOGGER.log(Level.CONFIG, info, Thread.currentThread().getStackTrace()[2]);
	}

	/**
	 * Puts an relief message on the consol with the stack trace
	 * @param info
	 */
	public static void relief(String info) {
		LOGGER.log(Level.FINE, info, Thread.currentThread().getStackTrace()[2]);
	}

}
