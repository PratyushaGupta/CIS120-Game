/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/** A basic game object displayed as a yellow circle, starting in the 
 * upper left corner of the game court.
 *
 */
public class Bricks extends GameObj {

	// Invariants for the brick
	public static final int WIDTH = 50;
	public static final int HEIGHT = 25;
	public int INIT_POS_X = 0;  
	public int INIT_POS_Y = 0; 
	public static final int INIT_VEL_X = 50;
	public static final int INIT_VEL_Y = 50;
	public boolean state = true;
	public int color = 0;
	public int level;
	
	// Image locations for the different bricks
	private static final String redblock = "red.png";
	private static final String blueblock = "blue.png";
	private static final String whiteblock = "white.png";
	private static final String greenblock = "green.png";
	private static final String purpleblock = "purple.png";
	private static final String specialblock = "special.png";
	private static final String blackblock = "black.png";
	private static final String bombblock = "bomb.png";
	
	// Images for the different bricks
	private static BufferedImage imgred;
	private static BufferedImage imgblue;
	private static BufferedImage imgwhite;
	private static BufferedImage imggreen;
	private static BufferedImage imgpurple;
	private static BufferedImage imgspecial;
	private static BufferedImage imgblack;
	private static BufferedImage imgbomb;
	
	
	// Bricks initialized with color
	public Bricks(int courtWidth, int courtHeight, int i, int j, int level, int color) {
		super(INIT_VEL_X, 
				INIT_VEL_Y, 
				i * 50, 
				j * 25, 
				WIDTH, 
				HEIGHT, 
				courtWidth, 
				courtHeight);
		INIT_POS_X = i * 50;
		INIT_POS_Y = j * 25;
		this.level = level;
		this.color = color;
	}

	// Bricks initialized with color and state
	public Bricks(int courtWidth, int courtHeight, int i, int j, int level, int color, boolean state) {
		super(INIT_VEL_X, 
				INIT_VEL_Y, 
				i * 50, 
				j * 25, 
				WIDTH, 
				HEIGHT, 
				courtWidth, 
				courtHeight);
		INIT_POS_X = i * 50;
		INIT_POS_Y = j * 25;
		this.level = level;
		this.color = color;
		this.state = state;
	}
	
	// Normal brick initialization
	public Bricks(int courtWidth, int courtHeight, int i, int j, int level) {
		super(INIT_VEL_X, 
				INIT_VEL_Y, 
				i * 50, 
				j * 25, 
				WIDTH, 
				HEIGHT, 
				courtWidth, 
				courtHeight);
		INIT_POS_X = i * 50;
		INIT_POS_Y = j * 25;
		this.level = level;
		color = getcolor();

		try {
			if (imgred == null) {
				imgred = ImageIO.read(new File(redblock));
			}
		} catch (IOException e) {
			System.out.println("Internal Error: " + e.getMessage());
		}

		try {
			if (imgblue == null) {
				imgblue = ImageIO.read(new File(blueblock));
			}
		} catch (IOException e) {
			System.out.println("Internal Error: " + e.getMessage());
		}

		try {
			if (imgwhite == null) {
				imgwhite = ImageIO.read(new File(whiteblock));
			}
		} catch (IOException e) {
			System.out.println("Internal Error: " + e.getMessage());
		}

		try {
			if (imggreen == null) {
				imggreen = ImageIO.read(new File(greenblock));
			}
		} catch (IOException e) {
			System.out.println("Internal Error: " + e.getMessage());
		}

		try {
			if (imgpurple == null) {
				imgpurple = ImageIO.read(new File(purpleblock));
			}
		} catch (IOException e) {
			System.out.println("Internal Error: " + e.getMessage());
		}

		try {
			if (imgspecial == null) {
				imgspecial = ImageIO.read(new File(specialblock));
			}
		} catch (IOException e) {
			System.out.println("Internal Error: " + e.getMessage());
		}
		
		try {
			if (imgblack == null) {
				imgblack = ImageIO.read(new File(blackblock));
			}
		} catch (IOException e) {
			System.out.println("Internal Error: " + e.getMessage());
		}
		
		try {
			if (imgbomb == null) {
				imgbomb = ImageIO.read(new File(bombblock));
			}
		} catch (IOException e) {
			System.out.println("Internal Error: " + e.getMessage());
		}

	}

	// Helps to generate colors based on a predetermined formula
	public int getcolor() {
		
		int multiplier = 200 + 50 * level;
		int rainbow = 5;
		int bomb = 2;
		
		if (level == 1) {
			int hold = (int)(Math.random() * ((3 * multiplier) + 2 + rainbow + bomb));
			if (hold >= 0 && hold <= multiplier - 1)
				return 1;
			else if (hold >= multiplier && hold <= (2 * multiplier) - 1)
				return 2;
			else if (hold >= (2 * multiplier) && hold <= (3 * multiplier) - 1)
				return 3;
			else if (hold >= (3 * multiplier) && hold <= (3 * multiplier) + rainbow)
				return 6;
			else if (hold > (3 * multiplier) + rainbow && hold <= (3 * multiplier) + rainbow + bomb)
				return 7;
			else
				return 8;
		}
		
		if (level == 2) {
			int hold = (int)(Math.random()* ((4 * multiplier) + 2 + rainbow));
			if (hold >= 0 && hold <= multiplier - 1)
				return 1;
			else if (hold >= multiplier && hold <= (2 * multiplier) - 1)
				return 2;
			else if (hold >= (2 * multiplier) && hold <= (3 * multiplier) - 1)
				return 3;
			else if (hold >= (3 * multiplier) && hold <= (4 * multiplier) - 1)
				return 4;
			else if (hold >= (4 * multiplier) && hold <= (4 * multiplier) + rainbow)
				return 6;
			else if (hold > (4 * multiplier) + rainbow && hold <= (4 * multiplier) + rainbow + bomb)
				return 7;
			else
				return 8;
		}
		
		if (level == 3) {
			int hold = (int)(Math.random()* ((5 * multiplier) + 2 + rainbow));
			if (hold >= 0 && hold <= multiplier - 1)
				return 1;
			else if (hold >= multiplier && hold <= (2 * multiplier) - 1)
				return 2;
			else if (hold >= (2 * multiplier) && hold <= (3 * multiplier) - 1)
				return 3;
			else if (hold >= (3 * multiplier) && hold <= (4 * multiplier) - 1)
				return 4;
			else if (hold >= (4 * multiplier) && hold <= (5 * multiplier) - 1)
				return 5;
			else if (hold >= (5 * multiplier) && hold <= (5 * multiplier) + rainbow)
				return 6;
			else if (hold > (3 * multiplier) + rainbow && hold <= (3 * multiplier) + rainbow + bomb)
				return 7;
			else
				return 8;
		}
		return 6;
	}

	@Override
	public void draw(Graphics g) {		
		if (state) {
			if (color == 1)
				g.drawImage(imgred, pos_x, pos_y, width, height, null);
			else if (color == 2)
				g.drawImage(imgblue, pos_x, pos_y, width, height, null);
			else if (color == 3)
				g.drawImage(imgwhite, pos_x, pos_y, width, height, null);
			else if (color == 4)
				g.drawImage(imgpurple, pos_x, pos_y, width, height, null);
			else if (color == 5)
				g.drawImage(imggreen, pos_x, pos_y, width, height, null);
			else if (color == 6)
				g.drawImage(imgspecial, pos_x, pos_y, width, height, null);
			else if (color == 7)
				g.drawImage(imgbomb, pos_x, pos_y, width, height, null);
			else if (color == 8)
				g.drawImage(imgblack, pos_x, pos_y, width, height, null);
		}
	}

	// Only changes the "normal bricks"
	public void randomize() {
		if (color >= 1 && color <= 5)
			color = getcolor();
	}

	// Changes any brick
	public void randomcolor() {
		color = getcolor();
	}
}