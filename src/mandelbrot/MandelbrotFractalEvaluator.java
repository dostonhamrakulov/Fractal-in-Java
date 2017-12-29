package mandelbrot;
/**
* Created by Doston Hamrakulov doston.hamrakulov@gmail.com 15.11.2017
*
*/
import java.awt.Color;

import fractal.FractalFunction;
import fractal.FractalDepthPainter;
import fractal.FractalEvaluator;
import math.Mcomplex;
import math.Mfloat;
import math.Number;

public class MandelbrotFractalEvaluator implements FractalEvaluator {
	
	public final static double EDGE = 2.0;
	
	private int maxDepth;
	private Mfloat edge;
	private FractalDepthPainter painter;
	private FractalFunction function;
	
	public MandelbrotFractalEvaluator(int maxDepth,
										FractalDepthPainter painter,
										FractalFunction function) {
		this.maxDepth = maxDepth;
		this.painter = painter;
		this.function = function;
		this.edge = Number.buildFloat(EDGE);
	}
	
	private Mfloat squareAbs(Mcomplex complex) {
		Mfloat real = complex.real();
		Mfloat imag = complex.imag();
		return real.mul(real).add(imag.mul(imag));
	}

	/**
	 * @return Maximum function evaluation number.
	 */
	public int getMaxDepth() {
		return maxDepth;
	}

	/**
     * Change maximum evaluation number.
	 * @param maxDepth - Positive integer
	 */
	public void setMaxDepth(int maxDepth) {
		if (maxDepth <= 0)
			maxDepth = 1;

		this.maxDepth = maxDepth;
	}

	/**
	 * @return Function divergence edge.
	 */
	public Mfloat getEdge() {
		return edge;
	}

	/**
	 * Set function divergence edge.
	 * @param edge Mfloat
	 */
	public void setEdge(Mfloat edge) {
		this.edge = edge;
	}

	/**
	 * @return Fractal depth painter
	 */
	public FractalDepthPainter getDepthPainter() {
		return painter;
	}

	/**
	 * Updates depth painter
	 * @param painter
	 */
	public void setDepthPainter(FractalDepthPainter painter) {
		this.painter = painter;
	}

	/**
	 * @return Fractal function
	 */
	public FractalFunction getFunction() {
		return function;
	}

	/**
	 * Updates fractal function.
	 * @param function Fractal function
	 */
	public void setFunction(FractalFunction function) {
		this.function = function;
	}

	@Override
	public Color evaluate(Mcomplex value) {
		Mfloat edgeSquared = edge.mul(edge);
		Mcomplex v = Number.buildComplex(0, 0);

		int depth = 0;
		while (depth < maxDepth && 
				squareAbs(v).compareTo(edgeSquared) < 0) {
			v = function.evaluate(v, value);
			depth++;
		}

		return painter.generateColor(depth, maxDepth);
	}

}
