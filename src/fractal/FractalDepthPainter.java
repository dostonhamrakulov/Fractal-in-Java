package fractal;
/**
* Created by Doston Hamrakulov doston.hamrakulov@gmail.com 15.11.2017
*
*/
import java.awt.Color;

public interface FractalDepthPainter {
	
	Color generateColor(int depth, int maxDepth);

}
