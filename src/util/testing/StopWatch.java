package util.testing;

import java.time.Clock;

public class StopWatch {
	private StopWatch() {
	}

	private static long begin = 0l;

	public static void start() {
		begin = Clock.systemDefaultZone().millis();
	}

	public static long stop() {
		return Clock.systemDefaultZone().millis() - begin;
	}
}
