package play.ai.devtech.core.util;

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
	private static Object[] params() {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
		String[] args = ste.toString().split("\\.");
		StringBuilder s = new StringBuilder();
		for(int x = args.length-4; x < args.length; x++) {
			s.append(args[x]);
			s.append('.');
		}
		s.deleteCharAt(s.length()-1);
		return new Object[] {s};//new Object[]{ste.getFileName(), ste.getLineNumber()};
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
		LOGGER.log(Level.SEVERE, info, params());
	}

	/**
	 * Puts an warn message on the consol with the stack trace
	 * @param info
	 */
	public static void warn(String info) {
		LOGGER.log(Level.WARNING, info, params());
	}

	/**
	 * Puts an info message on the consol with the stack trace
	 * @param info
	 */
	public static void info(String info) {
		LOGGER.log(Level.INFO, info, params());
	}

	/**
	 * Puts an debug message on the consol with the stack trace
	 * @param info
	 */
	public static void debug(String info) {
		LOGGER.log(Level.CONFIG, info, params());
	}

	/**
	 * Puts an relief message on the consol with the stack trace
	 * @param info
	 */
	public static void relief(String info) {
		LOGGER.log(Level.FINE, info, params());
	}

}
