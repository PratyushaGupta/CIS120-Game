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
public class GameCourt extends JPanel {

	// the state of the game logic
	private Bricks[][] bricks;

	public boolean playing = false; // whether the game is running
	private JLabel status;       	// Current status text (i.e. Running...)
	private int points;				// Number of points for game
	private int points1 = 0;		// Number of points for completed level 1
	private int points2 = 0;		// Number of points for completed level 2
	private int points3 = 0;		// Number of points for completed level 3
	private int level;				// Level number
	private boolean won;			// Checks whether the player has completed a level

	// Game constants
	public static final int COURT_WIDTH = 600;
	public static final int COURT_HEIGHT = 300;

	// Update interval for timer in milliseconds 
	public static final int INTERVAL = 35;

	// Deletes bricks if there are at least two connected with each other

	public void deleteHelper(int i, int j, int num) {
		// Saves the color of the "focused" brick
		int color = bricks[i][j].color;

		// Checks whether any deletion will occur
		boolean deletion = false;

		// Each loop checks whether the brick is within the array and
		// has the same color as the focused brick

		// Cell below
		if (j + 1 < bricks[0].length &&
				bricks[i][j + 1].color == color &&
				bricks[i][j + 1].state == true) {
			bricks[i][j + 1].state = false;
			deletion = true;
			deleteHelper(i, j + 1, num + 1);
		}

		// Cell above
		if (j - 1 >= 0 &&
				bricks[i][j - 1].color == color &&
				bricks[i][j - 1].state == true) {
			bricks[i][j - 1].state = false;
			deletion = true;
			deleteHelper(i, j - 1, num + 1);
		}

		// Cell to the left
		if (i + 1 < bricks.length &&
				bricks[i + 1][j].color == color &&
				bricks[i + 1][j].state == true) {
			bricks[i + 1][j].state = false;
			deletion = true;
			deleteHelper(i + 1, j, num + 1);
		}

		// Cell to the right
		if (i - 1 >= 0 &&
				bricks[i - 1][j].color == color &&
				bricks[i - 1][j].state == true) {
			bricks[i - 1][j].state = false;
			deletion = true;
			deleteHelper(i - 1, j, num + 1);
		}

		// Deletes the "focused" brick if something else is also being deleted
		// Prevents a single brick from being removed
		if (deletion) {
			bricks[i][j].state = false;
		}		

		// Calculates the number of points 
		if (deletion)
			points += (int)(num * level * 0.5);		
	}

	// Shifts the bricks down after they have been deleted

	public void moveDown() {

		// Iterates over each column
		for (int i = 0; i < bricks.length; i++) {

			// Index 11 is the bottom most row
			int botrow = 11;

			// Iterates over each row from bottom to top
			for (int j = bricks[0].length - 1; j >= 0; j--) {

				// If the brick is visible
				if (bricks[i][j].state == true) {

					// Creates a new brick at the location of the bottom most row if
					// the bottom most row is not the same as the current row
					// (ie. if the brick need to be moved)
					if (botrow != j) {
						bricks[i][botrow] = new Bricks(COURT_WIDTH, COURT_HEIGHT, i, botrow, 
								level, bricks[i][j].color, bricks[i][j].state);
						bricks[i][j].state = false;
					}
					
					// Increments the bottom row (moves it up)
					botrow = botrow - 1;
				}
			}
		}
	}

	// Moves over one row
	// Maintains a counter with moveOver() to ensure that all of the bricks get moved
	
	// Takes in the index of the row that needs to be moved over and a counter

	public void singleMove(int l, int counter) {
		for (int i = l + 1; i < bricks.length; i++) {
			for (int j = 0; j < bricks[0].length; j++) {
				
				// Moves over every brick and makes the old bricks invisible
				bricks[i - 1][j] = new Bricks(COURT_WIDTH, COURT_HEIGHT, 
						i - 1, j, level, bricks[i][j].color, bricks[i][j].state);
				bricks[i][j].state = false;
			}
		}
		
		// If fewer than 12 columns have been moved over, checks for more moves
		if (counter < 12)
			moveOver(counter);
	}

	// Iterates over the columns and calls singleMove() on the first empty one
	public void moveOver(int counter) {
		for (int i = 0; i < bricks.length; i++) {
			
			// Boolean for if the column is empty
			boolean isempty = true;
			for (int j = 0; j < bricks[0].length; j++) {
				if (bricks[i][j].state == true)
					isempty = false;
			}
			
			// If the column is empty, calls single move and increments the counter
			// Breaks out of the loop
			if (isempty) {
				counter++;
				singleMove(i, counter);
				break;
			}
		}
	}
	
	// Special effect for the black brick - creates new tiles from the top
	// In each column, creates a new brick in the first spot that doesn't
	// Have a visible one

	public void newrow() {
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
	
	// The bomb brick removes a certain number of bricks around it

	public void bombbrick(int c, int r) {
		
		// The number of bricks removed (the effectiveness of the bomb) depends on level
		int remove;
		if (level == 1)
			remove = 2;
		else
			remove = 1;
		
		// Makes bricks invisible
		for (int i = c - remove; i <= c + remove; i++) {
			for (int j = r - remove; j <= r + remove; j++) {
				if (i >= 0 && i < bricks.length && j >= 0 && j < bricks[0].length) {
					bricks[i][j].state = false;
				}
			}
		}
	}

	public GameCourt(JLabel status){

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
				
				// Checks which brick the mouse is clicking inside of
				for (int i = 0; i < bricks.length; i++) {
					for (int j = 0; j < bricks[0].length; j++) {
						if (bricks[i][j].pos_x + bricks[i][j].width - 1 >= p.x
								&& bricks[i][j].pos_y + bricks[i][j].height - 1 >= p.y
								&& bricks[i][j].pos_x + 1 <= p.x 
								&& bricks[i][j].pos_y + 1 <= p.y) {
							
							// If the brick is a rainbow brick, we randomize the color of
							// all of the other bricks and the rainbow brick selected
							if (bricks[i][j].color == 6 && bricks[i][j].state == true) {
								bricks[i][j].randomcolor();
								for (int k = 0; k < bricks.length; k++) {
									for (int h = 0; h < bricks[0].length; h++) {
										bricks[k][h].randomize();
									}
								}
							}
							
							// If the brick is a bomb brick, deletes the necessary bricks
							// and moves down and over
							else if (bricks[i][j].color == 7 && bricks[i][j].state == true) {
								points = points + 25;
								bombbrick(i, j);
								moveDown();
								moveOver(0);
							}
							
							// If the brick is a black brick, adds a new row and creates a
							// new brick in the location of the black brick
							else if (bricks[i][j].color == 8 && bricks[i][j].state == true) {
								newrow();
								bricks[i][j] = new Bricks(COURT_WIDTH, COURT_HEIGHT, i, j, level);
							}
							
							// If it is not a special brick, deletes the necessary elements and
							// moves down and over
							else {
								deleteHelper(i, j, 1);
								moveDown();
								moveOver(0);
							}
						}
					}
				}
			}
			
			// On mouse press, basically just preview which bricks will be deleted,
			// Without calling moveDown() or moveOver()
			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				for (int i = 0; i < bricks.length; i++) {
					for (int j = 0; j < bricks[0].length; j++) {
						if (bricks[i][j].pos_x + bricks[i][j].width - 1 >= p.x
								&& bricks[i][j].pos_y + bricks[i][j].height - 1 >= p.y
								&& bricks[i][j].pos_x + 1 <= p.x 
								&& bricks[i][j].pos_y + 1 <= p.y) {
							
							// Previews for the normal bricks
							if (bricks[i][j].color < 6)
								deleteHelper(i, j, 1);
							
							// Previews for the bomb bricks
							else if (bricks[i][j].color == 7 && bricks[i][j].state == true) {
								bombbrick(i, j);
							}
							
							// Other bricks don't need previews
						}
					}
				}
			}
		});

		this.status = status;
	}

	/** (Re-)set the state of the game to its initial state.
	 */
	public void reset(int lvl) {

		// Sets up the playing field
		bricks = new Bricks[12][12];
		points = 0;
		level = lvl;
		won = false;

		// Creates the new bricks
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

	// Checks if there are any remaining moves on the block selected
	// Runs the same algorithm as deleteHelper() but does not make
	// Any changes to the bricks - returns if there are moves left
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

	// Goes through each brick and calls remaining() to check if there
	// Are any remaining moves
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

	/**
	 * This method is called every time the timer defined
	 * in the constructor triggers.
	 */

	// Checks for a perfect clear
	public boolean perfectclear() {
		for (int i = 0; i < bricks.length; i++) {
			for (int j = 0; j < bricks[0].length; j++) {
				if (bricks[i][j].state)
					return false;
			}
		}
		return true;
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

			status.setText("Level Points: " + points + "    Game Points: " + (points1 + points2 + points3) + 
					"    Bricks Remaining: " + nremain + "    Level: " + level);

			// Checks for the end of the level/game
			if (!movesremaining()) {
				
				// Makes sure that all movements have been made
				moveDown();
				moveOver(0);
				if (!movesremaining()) {
					won = true;
					String perf = "";
					if (perfectclear()) {
						perf = "Perfect Clear! +50 points! ";
						points += 50;
					}
					if (level == 1) {
						points1 = points;
						status.setText(perf + "Level over with " + points + " points!"
								+ " Please press 'Next Level'");
					}
					if (level == 2) {
						points2 = points;
						status.setText(perf + "Level over with " + (points1 + points) + " points!"
								+ " Please press 'Next Level'");
					}
					if (level == 3) {
						points3 = points;
						status.setText(perf + "Game over! Final points: " + (points1 + points2 + points)
								+ ". Please press 'Reset Game' if you would like to continue playing.");

					}
					points = 0;
					playing = false;
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

	// Sets the next level based on the current level
	public void nextlvl() {
		if (level == 1 && won == true) {
			won = false;
			reset(2);
		}
		else if (level == 2 && won == true) {
			won = false;
			reset(3);
		}
	}

	// Resets the last level played
	public void resetlvl() {
		won = false;
		if (level == 1) {
			points1 = 0;
			reset(1);
		}
		else if (level == 2)
			reset(2);
		else
			reset(3);
	}

	// Ends the game
	public void endgame() {
		playing = false;
	}
}
