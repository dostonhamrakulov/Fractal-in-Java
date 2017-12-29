package math;

public interface Mcomplex {

	Mfloat real();
	
	Mfloat imag();
	
	Mcomplex add(Mcomplex c);

	Mcomplex sub(Mcomplex c);

	Mcomplex mul(Mcomplex c);

	Mcomplex div(Mcomplex c);
	
}
