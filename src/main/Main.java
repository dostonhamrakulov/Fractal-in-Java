package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import fractal.Fractal;
import fractal.FractalDepthPainter;
import fractal.FractalEvaluator;
import fractal.FractalListener;
import mandelbrot.DummyFractalFunction;
import mandelbrot.DummyFractalDepthPainter;
import mandelbrot.GrayFractalDepthPainter;
import mandelbrot.MandelbrotFractalEvaluator;
import math.Mfloat;
import math.Number;

public class Main extends JFrame implements ActionListener, FractalListener {

	// Fractal constants
	private static final int PIXEL_SIZE = 2;
	private static final int DEFAULT_DEPTH = 1000;
	private static final Mfloat DEFAULT_X = Number.buildFloat(0.0);
    private static final Mfloat DEFAULT_Y = Number.buildFloat(0.0);
    private static final Mfloat DEFAULT_VIEW_SIZE = Number.buildFloat(4.1);

	// Zoom constant
	private static final double MIN_ZOOM = 0.03;
	
	// Canvas constants
	private static final int CANVAS_WIDTH = 1200;
	private static final int CANVAS_HEIGHT = 700;

	// Input size for Mfloats
	private static final int FLOAT_INPUT_SIZE = 16;

	// Depth painters
    private static final String DEFAULT_PAINTER_NAME = "Default";
    private static final FractalDepthPainter DEFAULT_PAINTER = new DummyFractalDepthPainter();
    private static final Map<String, FractalDepthPainter> DEPTH_PAINTERS;

    static {
        DEPTH_PAINTERS = new HashMap<>();
        DEPTH_PAINTERS.put(DEFAULT_PAINTER_NAME, DEFAULT_PAINTER);
        DEPTH_PAINTERS.put("Gray", new GrayFractalDepthPainter());
    }

	private JTextField pixelSizeField;
	private JTextField fractalDepthField;
	private JTextField fractalEdgeField;
	private JComboBox fractalPainterDropdown;
	private JButton updateButton;

	private JTextField fractalXField;
	private JTextField fractalYField;
	private JTextField fractalViewSizeField;
	private JButton updateCoordinatesButton;
    private JButton resetZoomButton;

    private MandelbrotFractalEvaluator evaluator;
	private Fractal fractal;

	private int tryParseInteger(String s, int defaultValue) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	private void updateFractalEvaluator() {
	    Mfloat edge = null;
        int depth = evaluator.getMaxDepth();

        try {
            edge = Number.buildFloat(fractalEdgeField.getText());
        } catch (NumberFormatException ignored) { }

        try {
            depth = Integer.parseInt(fractalDepthField.getText());
        } catch (NumberFormatException ignored) { }

        if (edge != null) {
            evaluator.setEdge(edge);
        }

        evaluator.setMaxDepth(depth);
    }
	
	private void updateFractal() {
	    Mfloat x, y, size;
	    x = y = size = null;

		if (fractal != null) {
			try {
                x = Number.buildFloat(fractalXField.getText());
                y = Number.buildFloat(fractalYField.getText());
                size = Number.buildFloat(fractalViewSizeField.getText());
            } catch (NumberFormatException ignored) { }

            updateFractalEvaluator();

            fractal.setPixelSize(tryParseInteger(pixelSizeField.getText(), PIXEL_SIZE));

            if (x != null && y != null && size != null) {
                fractal.setViewSize(size);
                fractal.setCenterX(x);
                fractal.setCenterY(y);
            }

            updateUI();
		}
	}

	private void resetFractalZoom() {
	    fractal.setCenterX(DEFAULT_X);
        fractal.setCenterY(DEFAULT_Y);
        fractal.setViewSize(DEFAULT_VIEW_SIZE);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == updateButton || source == updateCoordinatesButton) {
			updateFractal();
        } else if (source == resetZoomButton) {
            resetFractalZoom();
        } else if (source == fractalPainterDropdown) {
            String painterName = (String) fractalPainterDropdown.getSelectedItem();
            FractalDepthPainter painter = DEPTH_PAINTERS.get(painterName);
            if (painter != null) {
                evaluator.setDepthPainter(painter);
                fractal.repaint();
            }
        }
    }
	
	private static MandelbrotFractalEvaluator buildFractalEvaluator() {
		return new MandelbrotFractalEvaluator(
                DEFAULT_DEPTH,
				DEFAULT_PAINTER,
				new DummyFractalFunction());
	}
	
	private static Fractal buildFractal(FractalEvaluator evaluator) {
		return new Fractal(DEFAULT_X, DEFAULT_Y, DEFAULT_VIEW_SIZE, PIXEL_SIZE, evaluator);
	}
	
	private Component buildCenterUI() {
		evaluator = buildFractalEvaluator();
		fractal = buildFractal(evaluator);
		fractal.addFractalListener(this);
		new CanvasZoomable(MIN_ZOOM, fractal, fractal);
		return fractal;
	}
	
	private Component buildTopUI() {
		JPanel res = new JPanel();
		res.setLayout(new FlowLayout());

        resetZoomButton = new JButton("Reset Zoom");
        resetZoomButton.addActionListener(this);
        res.add(resetZoomButton);

		res.add(new JLabel("Center coordinates (x, y): "));
		fractalXField = new JTextField(FLOAT_INPUT_SIZE);
        fractalYField = new JTextField(FLOAT_INPUT_SIZE);
        res.add(fractalXField);
        res.add(fractalYField);

        res.add(new JLabel("View area size: "));
        fractalViewSizeField = new JTextField(FLOAT_INPUT_SIZE);
        res.add(fractalViewSizeField);

        updateCoordinatesButton = new JButton("Update");
        updateCoordinatesButton.addActionListener(this);
        res.add(updateCoordinatesButton);

		return res;
	}

    private JComboBox buildFractalPainterDropdown() {
        JComboBox<String> res = new JComboBox<>();
        for (String name : DEPTH_PAINTERS.keySet()) {
            res.addItem(name);
        }
        res.setSelectedItem(DEFAULT_PAINTER_NAME);

        res.addActionListener(this);
        return res;
    }

	private Component buildBottomUI() {
		JPanel res = new JPanel();
		res.setLayout(new FlowLayout());
		
		res.add(new JLabel("Pixel size: "));
		pixelSizeField = new JTextField(5);
		pixelSizeField.setText(String.valueOf(PIXEL_SIZE));
		res.add(pixelSizeField);

		res.add(new JLabel("Depth: "));
		fractalDepthField = new JTextField(5);
		fractalDepthField.setText(String.valueOf(DEFAULT_DEPTH));
		res.add(fractalDepthField);

		res.add(new JLabel("Edge: "));
		fractalEdgeField = new JTextField(5);
		fractalEdgeField.setText(evaluator.getEdge().toString());
		res.add(fractalEdgeField);

		res.add(new JLabel("Coloring: "));
		fractalPainterDropdown = buildFractalPainterDropdown();
		res.add(fractalPainterDropdown);

		updateButton = new JButton("Update");
		updateButton.addActionListener(this);
		res.add(updateButton);

		return res;
	}

    @Override
    public void fractalUpdated(Fractal f) {
        updateUI();
    }

	private void updateUI() {
        fractalXField.setText(fractal.getCenterX().toString());
        fractalYField.setText(fractal.getCenterY().toString());
        fractalViewSizeField.setText(fractal.getViewSize().toString());
        pixelSizeField.setText(String.valueOf(fractal.getPixelSize()));

        // Evaluator fields
        fractalDepthField.setText(String.valueOf(evaluator.getMaxDepth()));
        fractalEdgeField.setText(String.valueOf(evaluator.getEdge()));
    }

	private Main() {
		setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
		setLayout(new BorderLayout());

		add(buildCenterUI(), BorderLayout.CENTER);
		add(buildTopUI(), BorderLayout.NORTH);
		add(buildBottomUI(), BorderLayout.SOUTH);

		updateUI();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            Main main = new Main();
            main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            main.setVisible(true);
        });
	}

}
