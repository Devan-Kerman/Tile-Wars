package main;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class DLogger {

	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
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
					System.err.printf("%s -> %s : %s\n", r.getParameters()[0], l.toString(), r.getMessage());
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
