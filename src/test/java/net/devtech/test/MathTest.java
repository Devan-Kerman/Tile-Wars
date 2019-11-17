package net.devtech.test;

import java.util.function.Consumer;

public class MathTest {
	public static final int OVERSHOOT = 2;
	public static final int ACCURACY = 1;
	public static final double TEST_X_DISPLACEMENT = 1000;
	public static final double TEST_Y_DISPLACEMENT = 1000;
	public static final int MAX_TICKS = 100;
	public static final int VELOCITIES_SAMPLE_RANGE = 10_000;
	public static final double[] VELOCITIES = getVelocities(VELOCITIES_SAMPLE_RANGE);
	public static final ThreadLocal<Vector> VECTOR_THREAD_LOCAL = new ThreadLocal<>();

	public static void main(String[] args) {
		System.out.printf("Vector Sample Range: [%f-%f]\n", VELOCITIES[0], VELOCITIES[VELOCITIES.length-1]);
		System.out.printf("Displacement: [%f, %f]\n", TEST_X_DISPLACEMENT, TEST_Y_DISPLACEMENT);

		getYVelocities(y -> {
			double xspeed = TEST_X_DISPLACEMENT / y.duration();
			if(VECTOR_THREAD_LOCAL.get() == null || new Vector(xspeed, y.getMin()).getMagnitude() < VECTOR_THREAD_LOCAL.get().getMagnitude())
				VECTOR_THREAD_LOCAL.set(new Vector(xspeed, y.startVelocity));
		},VELOCITIES, TEST_Y_DISPLACEMENT, MAX_TICKS);

		if(VECTOR_THREAD_LOCAL.get() != null)
			System.out.printf("%s at %f degrees", VECTOR_THREAD_LOCAL.get(), Math.toDegrees(VECTOR_THREAD_LOCAL.get().getAngle()));
		else System.out.println("No Vector Found!");
	}


	// O(N^2)
	public static void getYVelocities(Consumer<YVelocity> consumer, double[] velocities, double displacement, int ticks) {
		for (int x = 0; x < velocities.length; x++) {
			int end = firstValidTick(velocities, displacement, x, ticks);
			if (end != -1)
				consumer.accept(new YVelocity(x, end, velocities[x], velocities[end]));
		}
	}


	public static int firstValidTick(double[] velocities, double displacement, int start, int iters) {
		for (int i = start; i < Math.min(iters+start, VELOCITIES_SAMPLE_RANGE); i++)
			if ((displacement -= velocities[i]) < OVERSHOOT)
				if (displacement > ACCURACY)
					return i;
		return -1;
	}

	public static double reverse(double velocity, int ticks) {
		while (true) {
			if (ticks == 0)
				return velocity;
			ticks--;
			velocity = velocity / .98 + .08;
		}
	}

	public static double[] getVelocities(int ticks) {
		double[] velocities = new double[ticks];
		velocities[0] = reverse(0, ticks/2);
		for (int x = 1; x < velocities.length; x++)
			velocities[x] = (velocities[x-1] - .08) * .98;
		return velocities;
	}

	// TODO 3.92
}
