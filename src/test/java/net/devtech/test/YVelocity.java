package net.devtech.test;

public class YVelocity {
	public int start;
	public int end;
	public double startVelocity;
	public double endVelocity;

	public YVelocity(int start, int end, double startVelocity, double endVelocity) {
		this.start = start;
		this.end = end;
		this.startVelocity = startVelocity;
		this.endVelocity = endVelocity;
	}

	/**
	 * get the maximum downwards velocity
	 * @return
	 */
	public double getMin() {
		return Math.min(endVelocity, startVelocity); // intentional
	}

	public int duration() {
		return end-start;
	}

}
