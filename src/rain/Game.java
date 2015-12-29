package rain;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import graphics.Screen;

/**
 * @author carlin
 * Game Object
 * Uses Runnable interface and inherits from Canvas
 * Game is a sub-class of Canvas
 */
public class Game extends Canvas implements Runnable
{
	// Serializable class (extends Canvas!), so add serialization ID
	private static final long serialVersionUID = 1L;	
	
	public static int width = 300;
	public static int height = width/16*9;
	public static int scale = 3;
	
	// private so that its only viewable from this class
	private boolean running = false;// boolean to switch the game loop on and off
	private JFrame frame;// A window, a frame, to display game
	private Thread thread;// Thread is a Java subprocess
	
	// Converts image object into an array of integers to signal pixels to change to respective colors
	// BufferedImage object is an image with an accessible buffer of image data
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// Main image object, final rendered view
	// Casted to a DataBufferInt, class that returns integers
	// Takes BufferedImage image, getRaster() returns writable raster,getDataBuffer() returns the DataBuffer of the raster
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();// sets up writable raster
	
	// Declares Screen object for Game object
	private Screen screen;
	
	// Game constructor
	// Sets up a new instance of a Game Object
	public Game()
	{
		// Sets up dimensions of the window/frame
		Dimension size = new Dimension(width*scale, height*scale);
		// Method from Canvas that sets the size of the Canvas to the Dimension called size
		setPreferredSize(size);
		
		// Creates a new instance of the Screen object
		screen = new Screen(width, height);
		
		// creates a new instance of JFrame to frame
		frame = new JFrame();
	}
	
	// Synchronized is a strategy for preventing thread interferences and memory consistency errors
	// ensures that there's no overlap of instructions to the thread
	public synchronized void start()
	{
		running = true;
		// thread to handle the game
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop()
	{
		running = false;
		try
		{
			thread.join();
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	// implements Runnable requires run() method
	// run() method is automatically run from the thread when thread.start() is called.
	// This works because the class implements Runnable and thread was set with this class
	public void run()
	{
		while(running)
		{
			// Update handles the logic part of the game; updates at a set speed
			// Render handles the rendering part of the game; renders as fast as we can
			update();
			render();
		}
	}
	
	public void update()
	{
		
	}
	
	// Being called as fast as we can
	public void render()
	{
		// BufferStrategy to handle storing the next frame to be displayed
		// Canvas actually has a default BufferStrategy, 
		// so we can just access that through getBufferStrategy()
		BufferStrategy bs = getBufferStrategy();
		// If BufferStrategy hasn't been created, create it. (getBufferStrategy() is returning null)
		if(bs==null)
		{
			// Triple Buffering = 3. Basically, we can store 2 images in memory with triple buffering
			// Helps with speed
			createBufferStrategy(3);
			return;
		}
		
		// Renders the screen; constantly updates the screen
		screen.render();
		
		// Changes all the pixels in the frame for the Buffered Image to display
		for(int i = 0; i<pixels.length; i++)
		{
			pixels[i] = screen.pixels[i];
		}
		
		// getDrawGraphics() creates a link between you drawing graphics to the screen and the buffer
		Graphics g = bs.getDrawGraphics();
		// {
		// g.setColor(Color.BLACK);
		// // Fills rectangle
		// // (0,0) is the top left corner of the window in Java
		// // getWidth() & getHeight() returns the width and height of the
		// window, comes from Component class
		// g.fillRect(0, 0, getWidth(), getHeight());
		// }
		// Draws the Buffered image to the Screen (image is the Buffered Image)
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		// Releases System Resources
		// After updating a frame, we want to remove the previous frame
		g.dispose();
		bs.show(); // makes next available buffer visible
	}
	
	// Main method
	public static void main(String[] args)
	{
		// Constructs new Game object
		Game game = new Game();
		
		// ensures that the windows doesn't get resized
		game.frame.setResizable(false);
		game.frame.setTitle("Rain"); // sets the frame title to "Rain"
		// fills the window/frame with the game object; do able because Game is a subclass of Canvas 
		game.frame.add(game);
		game.frame.pack(); // sets the size of the frame to be the same as our component (Dimension size in Game Constructor)
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminate application when the exit button is hit
		game.frame.setLocationRelativeTo(null); // opens into the center of the screen
		game.frame.setVisible(true); //ensures that the window is visible
		
		// Starts the game
		game.start();
	}
}

/**
 * Notes:
 * Games usually contain two parts:
 * -Programming logical part
 * 		-Player movement, game mechanics, etc.
 * -Rendering
 * 		-Graphics rendering
 * The reason for this is because of timing issues. Movement may be based on frames.
 * The way each computer displays frames is different. Some computers display frames faster than others.
 * The speed at which a computer will render a frame may impact the player's performance
 * if the player's movement is based on frames.
 * To solve this, we use a timer, to ensure that player performance is the same across the board.
 * 
 * Buffers
 * Can be thought of as a temporary storage space
 * When we calculate something we don't need to apply it immediately, we can put it aside for later.
 * Basically, we render an image and save it for later.
 * When we render things, we can't render it live.
 * When we render something, you'll see the pixels update live on the screen, 1 by 1, continuously.
 * This can cause graphical issues. We want to calculate the color for every pixel beforehand, 
 * store it as a buffer, and then display the image when we need it.
 * 
 * Colors are represented by numbers, in many cases, they're represented by hexadecimal numbers.
 * 
 * Rasters
 * Raster is a data structure that represents a rectangular array of pixels.
 */

