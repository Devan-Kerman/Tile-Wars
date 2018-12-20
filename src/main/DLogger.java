package main;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class DLogger {

	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public DLogger() {
		LOGGER.setLevel(Level.FINEST);
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
				System.out.printf("%s -> %s : ", r.getParameters()[0], r.getMessage(), l.toString());
			}

		});

	}

	/*
	 * Error = #ff0000 Warn = #ff6600 Info = #ffffff Debug = #33ccff Relief =
	 * #99ff66
	 */

	public void error(String info) {
		LOGGER.log(Level.SEVERE, info, Thread.currentThread().getStackTrace()[2]);
	}

	public void warn(String info) {
		LOGGER.log(Level.WARNING, info, Thread.currentThread().getStackTrace()[2]);
	}

	public void info(String info) {
		LOGGER.log(Level.INFO, info, Thread.currentThread().getStackTrace()[2]);
	}

	public void debug(String info) {
		LOGGER.log(Level.CONFIG, info, Thread.currentThread().getStackTrace()[2]);
	}

	public void relief(String info) {
		LOGGER.log(Level.FINE, info, Thread.currentThread().getStackTrace()[2]);
	}

}
