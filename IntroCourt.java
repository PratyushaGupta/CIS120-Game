import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class IntroCourt extends JPanel {
	
	// A basic court that just displays the Introduction object
	
	private GameObj intro;
	public static final int COURT_WIDTH = 600;
	public static final int COURT_HEIGHT = 300;
	private JLabel status;
	
	// Update interval for timer in milliseconds 
	public static final int INTERVAL = 35;

	
	public IntroCourt(JLabel status){

		// creates border around the court area, JComponent method

		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// Enable keyboard focus on the court area
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.

		setFocusable(true);

		this.status = status;
	}	
	
	
	public void reset() {
		intro = new Introduction(COURT_WIDTH, COURT_HEIGHT);
		status.setText("Welcome to Destruct-O-Match");
	}
	
	@Override 
	public void paintComponent(Graphics g){	
		super.paintComponent(g);
		intro.draw(g);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH,COURT_HEIGHT);
	}

}
