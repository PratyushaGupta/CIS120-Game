/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


/**
 * GameCourt
 * 
 * This class holds the primary game logic of how different objects 
 * interact with one another.  Take time to understand how the timer 
 * interacts with the different methods and how it repaints the GUI 
 * on every tick().
 *
 */
@SuppressWarnings("serial")
public class ZenCourt extends JPanel {

	// the state of the game logic
	private Bricks[][] bricks;

	public boolean playing = false; // whether the game is running
	private JLabel status;       	// Current status text (i.e. Running...)
	private int points;				// Current number of points
	private int level;				// Level number
	private int nummoves;			// Number of moves that have been made

	// Game constants
	public static final int COURT_WIDTH = 600;
	public static final int COURT_HEIGHT = 300;

	// Update interval for timer in milliseconds 
	public static final int INTERVAL = 35;

	// Deletes bricks if there are at least two connected with each other
	// Game logic same as in GameCourt
	public void deleteHelper(int i, int j, int num) {
		int color = bricks[i][j].color;
		boolean deletion = false;

		if (j + 1 < bricks[0].length) {
			if (bricks[i][j + 1].color == color &&
					bricks[i][j + 1].state == true && num + 1 >= 2) {
				bricks[i][j + 1].state = false;
				deletion = true;
				deleteHelper(i, j + 1, num + 1);
			}
		}
		if (j - 1 >= 0) {
			if (bricks[i][j - 1].color == color &&
					bricks[i][j - 1].state == true && num + 1 >= 2) {
				bricks[i][j - 1].state = false;
				deletion = true;
				deleteHelper(i, j - 1, num + 1);
			}
		}
		if (i + 1 < bricks.length) {
			if (bricks[i + 1][j].color == color &&
					bricks[i + 1][j].state == true && num + 1 >= 2) {
				bricks[i + 1][j].state = false;
				deletion = true;
				deleteHelper(i + 1, j, num + 1);
			}
		}
		if (i - 1 >= 0) {
			if (bricks[i - 1][j].color == color &&
					bricks[i - 1][j].state == true && num + 1 >= 2) {
				bricks[i - 1][j].state = false;
				deletion = true;
				deleteHelper(i - 1, j, num + 1);
			}
		}
		if (deletion && num > 1) {
			bricks[i][j].state = false;
		}		
		if (deletion && num > 1)
			points += (int)(num * level * 0.5);		
	}

	// Shifts the bricks down after they have been deleted
	public void moveDown() {
		for (int i = 0; i < bricks.length; i++) {
			int currow = 11;
			for (int j = bricks[0].length - 1; j >= 0; j--) {
				if (bricks[i][j].state == true) {
					bricks[i][currow] = new Bricks(COURT_WIDTH, COURT_HEIGHT, i, currow, 
							level, bricks[i][j].color, bricks[i][j].state);
					if (currow > j)
						bricks[i][j].state = false;
					currow = currow - 1;
				}
			}
		}
	}

	// Moves over one row
	// Maintains a counter with moveOver() to ensure that all of the bricks get moved
	public void singleMove(int l, int counter) {
		for (int i = l + 1; i < bricks.length; i++) {
			for (int j = 0; j < bricks[0].length; j++) {
				bricks[i - 1][j] = new Bricks(COURT_WIDTH, COURT_HEIGHT, 
						i - 1, j, level, bricks[i][j].color, bricks[i][j].state);
				bricks[i][j].state = false;
			}
		}
		if (counter < 12)
			moveOver(counter);
	}

	// Iterates over the rows and calls singleMove() on the first empty one
	public void moveOver(int counter) {
		for (int i = 0; i < bricks.length; i++) {
			boolean isempty = true;
			for (int j = 0; j < bricks[0].length; j++) {
				if (bricks[i][j].state == true)
					isempty = false;
			}
			if (isempty) {
				counter++;
				singleMove(i, counter);
				break;
			}
		}
	}
	
	// Adds a layer of bricks
	public void addlayer() {
		for (int i = 0; i < bricks.length; i++) {
			boolean added = false;
			for (int j = bricks[0].length - 1; j >= 0; j--) {
				if (!added) {
					if (bricks[i][j].state == false) {
						bricks[i][j] = new Bricks(COURT_WIDTH, COURT_HEIGHT, i, j, level);
						added = true;
					}
				}
			}
		}
	}
	
	// Deletes bricks for the bomb functionality
	public void bombbrick(int c, int r) {
		int remove = 1;
		for (int i = c - remove; i <= c + remove; i++) {
			for (int j = r - remove; j <= r + remove; j++) {
				if (i >= 0 && i < bricks.length && j >= 0 && j < bricks[0].length) {
					bricks[i][j].state = false;
				}
			}
		}
	}

	public ZenCourt(JLabel status){

		// creates border around the court area, JComponent method

		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called 
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.

		Timer timer = new Timer(INTERVAL, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tick();
			}
		});

		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.

		setFocusable(true);

		// This mouseListener checks which brick has been pressed
		// It also handles any special events


		addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				Point p = e.getPoint();
				
				// Checks in which brick the mouse is pressing
				for (int i = 0; i < bricks.length; i++) {
					for (int j = 0; j < bricks[0].length; j++) {
						if (bricks[i][j].pos_x + bricks[i][j].width - 1 >= p.x
								&& bricks[i][j].pos_y + bricks[i][j].height - 1 >= p.y
								&& bricks[i][j].pos_x + 1 <= p.x 
								&& bricks[i][j].pos_y + 1 <= p.y) {
							
							// Rainbow brick
							if (bricks[i][j].color == 6 && bricks[i][j].state == true) {
								bricks[i][j].randomcolor();
								for (int k = 0; k < bricks.length; k++) {
									for (int h = 0; h < bricks[0].length; h++) {
										bricks[k][h].randomize();
									}
								}
							}
							
							// Bomb brick
							else if (bricks[i][j].color == 7 && bricks[i][j].state == true) {
								bombbrick(i, j);
								moveDown();
								moveOver(0);
							}
							
							// Black brick
							else if (bricks[i][j].color == 8 && bricks[i][j].state == true) {
								addlayer();
								bricks[i][j] = new Bricks(COURT_WIDTH, COURT_HEIGHT, i, j, level);
							}
							
							// All normal bricks
							else {
								nummoves++;
								deleteHelper(i, j, 1);
								moveDown();
								moveOver(0);
							}
						}
					}
				}
			}

			// Previewing functionality
			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				for (int i = 0; i < bricks.length; i++) {
					for (int j = 0; j < bricks[0].length; j++) {
						if (bricks[i][j].pos_x + bricks[i][j].width - 1 >= p.x
								&& bricks[i][j].pos_y + bricks[i][j].height - 1 >= p.y
								&& bricks[i][j].pos_x + 1 <= p.x 
								&& bricks[i][j].pos_y + 1 <= p.y) {
							if (bricks[i][j].color < 6)
								deleteHelper(i, j, 1);
							else if (bricks[i][j].color == 7 && bricks[i][j].state == true) {
								bombbrick(i, j);
							}
						}
					}
				}
			}
		});

		this.status = status;
	}

	/** (Re-)set the state of the game to its initial state.
	 */
	public void reset() {
		
		// Game invariants
		bricks = new Bricks[12][12];
		points = 0;
		level = 1;

		// Creates bricks
		for (int i = 0; i < bricks.length; i++) {
			for (int j = 0; j < bricks[0].length; j++) {
				bricks[i][j] = new Bricks(COURT_WIDTH, COURT_HEIGHT, i, j, level);
			}
		}

		playing = true;
		status.setText("Running...");

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}
	
	// Calculates if there are any remaining moves based on the same alogrithm
	// as deleteHelper()
	public boolean remaining(int i, int j, int num) {
		int color = bricks[i][j].color;
		boolean deletion = false;

		if (j + 1 < bricks[0].length) {
			if (bricks[i][j + 1].color == color &&
					bricks[i][j + 1].state == true && num + 1 >= 2) {
				deletion = true;
			}
		}
		if (j - 1 >= 0) {
			if (bricks[i][j - 1].color == color &&
					bricks[i][j - 1].state == true && num + 1 >= 2) {
				deletion = true;
			}
		}
		if (i + 1 < bricks.length) {
			if (bricks[i + 1][j].color == color &&
					bricks[i + 1][j].state == true && num + 1 >= 2) {
				deletion = true;
			}
		}
		if (i - 1 >= 0) {
			if (bricks[i - 1][j].color == color &&
					bricks[i - 1][j].state == true && num + 1 >= 2) {
				deletion = true;
			}
		}
		if (deletion) {
			return true;
		}
		else {
			return false;
		}
	}

	// Goes through each brick and calls remaining()
	public boolean movesremaining() {
		for (int i = 0; i < bricks.length; i++) {
			for (int j = 0; j < bricks[0].length; j++) {
				if (bricks[i][j].state == true) {
					boolean playing = remaining(i, j, 1);
					if (bricks[i][j].color >= 6 && bricks[i][j].color <= 8)
						playing = true;
					if (playing)
						return true;
				}
			}
		}
		return false;
	}
	
	// Detects whether a new layer needs to be added and then class addlayer()
	public void newlayer() {
		boolean add = true;
		for (int i = 0; i < bricks.length; i++) {
			if (bricks[i][0].state == true || bricks[i][1].state == true)
				add = false;
		}
		
		if (add)
			addlayer();
	}

	void tick(){
		if (playing) {

			int nremain = 0;

			// Calculates the number of remaining bricks
			for (int i = 0; i < bricks.length; i++) {
				for (int j = 0; j < bricks[0].length; j++) {
					if (bricks[i][j].state == true)
						nremain += 1;
				}
			}
			
			// Progresses through the levels
			if (nummoves > 100 && nummoves <= 200)
				level = 2;
			if (nummoves > 200)
				level = 3;

			status.setText("Points: " + points + "    Bricks Remaining: " + nremain + "    Level: " + level);
			
			newlayer();
			
			// If there are no more moves remaining, it randomizes the color of the bricks
			if (!movesremaining()) {
				for (int i = 0; i < bricks.length; i++) {
					for (int j = 0; j < bricks[0].length; j++) {
						bricks[i][j].randomcolor();
					}
				}
			}

			repaint();
		} 
	}

	// Paints each component
	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for (int i = 0; i < bricks.length; i++) {
			for (int j = 0; j < bricks[0].length; j++) {
				if (bricks[i][j].state) {
					bricks[i][j].draw(g);
				}
			}
		}
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(COURT_WIDTH,COURT_HEIGHT);
	}
	
	// Ends the game
	public void endgame() {
		playing = false;
	}
}
