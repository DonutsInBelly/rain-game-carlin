package graphics;

public class Screen 
{
	// Width and height for the Screen object
	private int width;
	private int height;
	public int[] pixels;
	public int xtime=0, ytime=50, counter=0;
	
	// Screen Constructor
	public Screen(int width, int height)
	{
		this.width = width;
		this.height = height;
		// Creates an integer for each pixel on the screen
		pixels = new int[width*height];
	}
	
	// We need something to flush the buffer so that it can update whats on the screen
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void render() {
		counter++;
		if(counter % 10 == 0) xtime++;
		if(counter % 80 == 0) ytime++;
		// Nested for loops to iterate through all the pixels in the Screen
		// Renders Vertically (Top Down)
		for(int y = 0; y<height; y++) {
			// handle ArrayOutOfBounds Exception
			if (ytime >= height || ytime < 0) break;
			for(int x = 0; x<width; x++) {
				// handle ArrayOutOfBounds Exception
				if (xtime >= width || xtime < 0) break;
				// Changes pixels to be magenta?
				// x + y * width is a way to access the pixels from a single dimension array
				// width accounts for the width of the pixels in a screen; y accounts for the row.
				// width acts as a displacement for the rows of pixels in the one dimensional array.
				pixels[xtime+ytime*width] = 0xff00ff;
			}
		}
	}
}
