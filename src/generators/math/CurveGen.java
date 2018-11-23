package generators.math;

public class CurveGen {
	int multiplier;
	int shift;
	
	public CurveGen(int i, int j) {
		this.multiplier = i;
		this.shift = j;
	}
	
	public byte calculate(byte input) {
		int x = input - shift;
		return (byte) (multiplier*((Math.abs(x)+14)/(Math.pow((.1*(x)-1),2)+5)));
	}
	
}
