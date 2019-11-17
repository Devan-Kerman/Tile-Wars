package net.devtech.test;

public class Vector {
	public double x;
	public double y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getMagnitude() {
		return Math.sqrt(x*x+y*y);
	}

	public double getAngle() {
		return Math.atan2(y, x);
	}

	@Override
	public String toString() {
		return "Vector{" + "x=" + x + ", y=" + y + '}';
	}
}
