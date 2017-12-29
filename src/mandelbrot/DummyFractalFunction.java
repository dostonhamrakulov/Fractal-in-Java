package mandelbrot;
/**
* Created by Doston Hamrakulov doston.hamrakulov@gmail.com 15.11.2017
*
*/
import fractal.FractalFunction;
import math.Mcomplex;

public class DummyFractalFunction implements FractalFunction {
	
	@Override
	public Mcomplex evaluate(Mcomplex value, Mcomplex add) {
		return value.mul(value).add(add);
	}

}
