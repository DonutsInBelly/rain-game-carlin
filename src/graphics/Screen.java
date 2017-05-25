package graphics;

import java.util.Random;

public class Screen 
{
	// Width and height for the Screen object
	private int width;
	private int height;
	public int[] pixels;
	
	// Array which keeps track of the tiles - each tile is 1 int
	public int[] tiles = new int[64*64];
	
	private Random random = new Random();

	// Screen Constructor
	public Screen(int width, int height)
	{
		this.width = width;
		this.height = height;
		// Creates an integer for each pixel on the screen
		pixels = new int[width*height];
		
		// Sets random color to each tile
		for (int i = 0; i < 64 * 64; i++) {
			tiles[i] = random.nextInt(0xffffff);
		}
	}
	
	// We need something to flush the buffer so that it can update whats on the screen
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void render() {
		// Nested for loops to iterate through all the pixels in the Screen
		// Renders Vertically (Top Down)
		for(int y = 0; y < height; y++) {
			int yy = y;
			// handle ArrayOutOfBounds Exception
			if (yy >= height || yy < 0) break;
			for(int x = 0; x < width; x++) {
				int xx = x - 16;
				// handle ArrayOutOfBounds Exception
				if (xx >= width || xx < 0) break;
				// x + y * width is a way to access the pixels from a single dimension array
				// width accounts for the width of the pixels in a screen; y accounts for the row.
				// width acts as a displacement for the rows of pixels in the one dimensional array.
				// Bitwise operation instead of dividing by 16 brought a performance improvement of ~150 FPS!!!!
				int tileIndex = (xx >> 4) + (yy >> 4) * 64; // 16 * 16 tiles
				pixels[x + y * width] = tiles[tileIndex];
			}
		}
	}
}
