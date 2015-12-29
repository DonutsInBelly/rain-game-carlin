package graphics;

public class Screen 
{
	// Width and height for the Screen object
	private int width;
	private int height;
	public int[] pixels;
	
	// Screen Constructor
	public Screen(int width, int height)
	{
		this.width = width;
		this.height = height;
		// Creates an integer for each pixel on the screen
		pixels = new int[width*height];
	}
}
