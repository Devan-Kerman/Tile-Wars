package generators.math;

public class CurveGen {
	private final int multiplier;
	private final int shift;

	public CurveGen(final int i, final int j) {
		this.multiplier = i;
		this.shift = j;
	}

	public byte calculate(final byte input) {
		int x = input - shift;
		return (byte) (multiplier * ((Math.abs(x) + 14) / (Math.pow((.1 * (x) - 1), 2) + 5)));
	}

}
