package fractal;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import math.Mcomplex;
import math.Mfloat;
import math.Number;

public class Fractal extends Canvas implements Zoomable {
	
	private static final Mfloat DEFAULT_X  = Number.buildFloat(0.0);
	private static final Mfloat DEFAULT_Y  = Number.buildFloat(0.0);
	private static final Mfloat DEFAULT_VS = Number.buildFloat(4.1);

	private Mfloat x, y, viewSize;
	private int pixelSize;
	private FractalEvaluator evaluator;
	private List<FractalListener> listeners;
	
	public Fractal(Mfloat x, Mfloat y, Mfloat viewSize,
					int pixelSize, FractalEvaluator evaluator) {
		this.x = x;
		this.y = y;
		this.viewSize = viewSize;
		this.pixelSize = pixelSize;
		this.evaluator = evaluator;
		this.listeners = new ArrayList<>();
	}
	
	public Fractal(int pixelSize, FractalEvaluator evaluator) {
		this(DEFAULT_X, DEFAULT_Y, DEFAULT_VS, pixelSize, evaluator);
	}
	
	public void setPixelSize(int pixelSize) {
		this.pixelSize = pixelSize;
		fractalUpdated();
	}

	public Mfloat getCenterX() {
		return x;
	}

	public void setCenterX(Mfloat x) {
		this.x = x;
		fractalUpdated();
	}

	public Mfloat getCenterY() {
		return y;
	}

	public void setCenterY(Mfloat y) {
		this.y = y;
		fractalUpdated();
	}

	public Mfloat getViewSize() {
		return viewSize;
	}

	public void setViewSize(Mfloat viewSize) {
		this.viewSize = viewSize;
		fractalUpdated();
	}

	public void addFractalListener(FractalListener l) {
		this.listeners.add(l);
	}

	public void removeFractalListener(FractalListener l) {
		this.listeners.remove(l);
	}

	public int getPixelSize() {
		return pixelSize;
	}
	
	public void setFractalEvaluator(FractalEvaluator evaluator) {
		this.evaluator = evaluator;
		fractalUpdated();
	}

	public FractalEvaluator getFractalEvaluator() {
		return evaluator;
	}
	
	@Override
	public void paint(Graphics g) {
		int width = getWidth();
		int height = getHeight();

		for (int i=0; i<width; i += pixelSize) {
			for (int j=0; j<height; j += pixelSize) {
				drawPixel(i, j, g);
			}
		}
	}
	
	private void drawRect(Graphics g, int x, int y, int width, int height) {
		for (int dx = 0; dx < width; dx++) {
			g.drawLine(x + dx, y, x + dx, y + height);
		}
	}
	
	private void drawPixel(int x, int y, Graphics g) {
		Mcomplex value = getPixelValue(x + pixelSize/2.0, y + pixelSize/2.0);
		Color color = evaluator.evaluate(value);
		g.setColor(color);
		drawRect(g, x, y, pixelSize, pixelSize);
		g.setColor(Color.WHITE);
	}
	
	private Mcomplex getPixelValue(double x, double y) {
		double w = getWidth();
		double h = getHeight();
		double cx = x - w/2;
		double cy = h/2 - y;

		double minSize = Math.min(w, h);
		Mfloat ratio = viewSize.div(Number.buildFloat(minSize));

		Mfloat real = Number.buildFloat(cx).mul(ratio);
		Mfloat imag = Number.buildFloat(cy).mul(ratio);
		return Number.buildComplex(this.x.add(real), this.y.add(imag));
	}

	@Override
	public void zoom(double x, double y, double scale) {
		x = x - 0.5;
		y = 0.5 - y;

		double w = getWidth();
		double h = getHeight();

		Mfloat width, height;
		if (h < w) {
			height = viewSize;
			width = height.mul(Number.buildFloat(w / h));
		} else {
			width = viewSize;
			height = width.mul(Number.buildFloat(h / w));
		}
		
		this.x = this.x.add(width.mul(Number.buildFloat(x)));
		this.y = this.y.add(height.mul(Number.buildFloat(y)));
		this.viewSize = viewSize.mul(Number.buildFloat(scale));

		fractalUpdated();
	}

	private void fractalUpdated() {
		repaint();
		for (FractalListener l : listeners) {
			l.fractalUpdated(this);
		}
	}
	
}
