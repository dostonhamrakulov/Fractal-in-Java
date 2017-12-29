package math;
/**
* Created by Doston Hamrakulov doston.hamrakulov@gmail.com 15.11.2017
*
*/
public class JavaFloat implements Mfloat {
	
	private double value;
	
	public JavaFloat(double value) {
		this.value = value;
	}
	
	public double getDouble() {
		return value;
	}

	@Override
	public Mfloat add(Mfloat f) {
		return new JavaFloat(value + ((JavaFloat)f).value);
	}

	@Override
	public Mfloat sub(Mfloat f) {
		return new JavaFloat(value - ((JavaFloat)f).value);
	}

	@Override
	public Mfloat mul(Mfloat f) {
		return new JavaFloat(value * ((JavaFloat)f).value);
	}

	@Override
	public Mfloat div(Mfloat f) {
		return new JavaFloat(value / ((JavaFloat)f).value);
	}

	@Override
	public int compareTo(Mfloat arg0) {
		if (arg0 == null) {
			return 1;
		} else if (this == arg0) {
			return 0;
		} 

		JavaFloat f = (JavaFloat) arg0;	// Assuming argument is JavaFloat
		if (value > f.value) {
			return 1;
		} else if (value < f.value) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
