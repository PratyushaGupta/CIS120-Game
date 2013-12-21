import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Introduction extends GameObj {
	
	// This file creates a GameObj that displays the intro screen

	private static final String intro = "intro.png";
	private static BufferedImage imgstructs;
	public static final int WIDTH = 600;
	public static final int HEIGHT = 300;
	public static final int INIT_POS_X = 0;  
	public static final int INIT_POS_Y = 0; 
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;

	public Introduction(int court_width, int court_height) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, 
				WIDTH, HEIGHT, court_width, court_height);
			try {
				imgstructs = ImageIO.read(new File(intro));
			} catch (IOException e) {
				System.out.println("Internal Error: " + e.getMessage());
			}
	}
	

	public void draw(Graphics g) {
		g.drawImage(imgstructs, pos_x, pos_y, width, height, null);
	}

}