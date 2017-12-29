package fractal;
/**
* Created by Doston Hamrakulov doston.hamrakulov@gmail.com 15.11.2017
*
*/
public interface Zoomable {
	
	/**
	 * Zooms 2 dimensional object into some
	 * 	inner rectangular section.
	 * Coordinates are given as scales where
	 * 	0 is for start of the object and 1 is the end.
	 * @param x - Zoom center x coordinate (in range [0, 1])
	 * @param y - Zoom center y coordinate (in range [0, 1])
	 * @param scale - How much to scale the object (in range [0, 1])
	 */
	void zoom(double x, double y, double scale);

}
