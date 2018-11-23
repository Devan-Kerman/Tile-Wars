package generators.math;

public class CurveGen {
	byte multiplier;
	byte shift;
	
	public CurveGen(byte multiplier, byte shift) {
		this.multiplier = multiplier;
		this.shift = shift;
	}
	
	public byte calculate(byte input) {
		return (byte) (multiplier*(input));
	}
	
}
