package mandelbrot;

import fractal.FractalDepthPainter;

import java.awt.*;

public class GrayFractalDepthPainter implements FractalDepthPainter {

    private static final int DEPTH_REPEAT = 300;

    @Override
    public Color generateColor(int depth, int maxDepth) {
        if (depth == maxDepth) {
            return Color.BLACK;
        }

        depth %= DEPTH_REPEAT;
        if (depth > DEPTH_REPEAT/2)
            depth = DEPTH_REPEAT - depth;

        int value = (int) ((1.0 - (2.0 * depth) / DEPTH_REPEAT) * 255);

        return new Color(value, value, value);
    }

}
