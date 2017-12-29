package mandelbrot;

import java.awt.Color;

import fractal.FractalDepthPainter;

public class DummyFractalDepthPainter implements FractalDepthPainter {
	
	private static final Color[] COLORS = new Color[] {
			new Color(66, 30, 15),
			new Color(25, 7, 26),
			new Color(9, 1, 47),
			new Color(4, 4, 73),
			new Color(0, 7, 100),
			new Color(12, 44, 138),
			new Color(24, 82, 177),
			new Color(57, 125, 209),
			new Color(134, 181, 229),
			new Color(211, 236, 248),
			new Color(241, 233, 191),
			new Color(248, 201, 95),
			new Color(255, 170, 0),
			new Color(204, 128, 0),
			new Color(153, 87, 0),
			new Color(106, 52, 3),
	};

	@Override
	public Color generateColor(int depth, int maxDepth) {
		if (depth == maxDepth)
			return Color.BLACK;
		else
			return COLORS[depth % COLORS.length];
	}

}
