package math;
/**
* Created by Doston Hamrakulov doston.hamrakulov@gmail.com 15.11.2017
*
*/
public interface Mcomplex {

	Mfloat real();
	
	Mfloat imag();
	
	Mcomplex add(Mcomplex c);

	Mcomplex sub(Mcomplex c);

	Mcomplex mul(Mcomplex c);

	Mcomplex div(Mcomplex c);
	
}
