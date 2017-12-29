package math;
/**
* Created by Doston Hamrakulov doston.hamrakulov@gmail.com 15.11.2017
*
*/
public class JavaComplex implements Mcomplex {
	
	private Mfloat real, imag;
	
	public JavaComplex(Mfloat real, Mfloat imag) {
		this.real = real;
		this.imag = imag;
	}

	@Override
	public Mfloat real() {
		return real;
	}

	@Override
	public Mfloat imag() {
		return imag;
	}

	@Override
	public Mcomplex add(Mcomplex c) {
		return new JavaComplex(real.add(c.real()), imag.add(c.imag()));
	}

	@Override
	public Mcomplex sub(Mcomplex c) {
		return new JavaComplex(real.sub(c.real()), imag.sub(c.imag()));
	}

	@Override
	public Mcomplex mul(Mcomplex c) {
		return new JavaComplex(
				real.mul(c.real()).sub(imag.mul(c.imag())), 
				imag.mul(c.real()).add(real.mul(c.imag())));
	}

	@Override
	public Mcomplex div(Mcomplex c) {
		Mfloat denom = c.imag().mul(c.imag()).add(c.real().mul(c.real()));
		return new JavaComplex(
				(real.mul(c.real()).add(imag().mul(c.imag()))).div(denom), 
				(imag().mul(c.real()).sub(real.mul(c.imag()))).div(denom));
	}

	@Override
	public String toString() {
		return String.format("%s + i*%s", real.toString(), imag.toString());
	}
}
