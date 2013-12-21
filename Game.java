/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** 
 * Game
 * Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	public void run(){
		// NOTE : recall that the 'final' keyword notes inmutability
		// even for local variables. 

		// Top-level frame in which game components live
		// Be sure to change "TOP LEVEL FRAME" to the name of your game
		final JFrame frame = new JFrame("Destruct-O-Match");
		frame.setLocation(300,300);

		// Status panel
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.SOUTH);
		final JLabel status = new JLabel("Running...");

		// Main playing area - Classic Mode
		final GameCourt classic_court = new GameCourt(status);

		// Main playing area - Zen Mode
		final ZenCourt zen_court = new ZenCourt(status);

		// Classic Panel
		final JPanel classic_panel = new JPanel();

		// Zen Panel
		final JPanel zen_panel = new JPanel();

		// Instructions Label
		final JLabel intro_status = new JLabel("Welcome to Destruct-O-Match!");


		// Instructions Main Screen
		final IntroCourt instructions = new IntroCourt(intro_status);

		// Instructions Panel
		final JPanel intro_panel = new JPanel();

		// Set the playing area to the intro screen
		status_panel.add(intro_status);
		frame.add(instructions, BorderLayout.CENTER);
		frame.add(intro_panel, BorderLayout.NORTH);

		// Start button for classic mode

		final JButton startclass = new JButton("Classic Mode");
		startclass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Changes the panel
				frame.add(classic_panel, BorderLayout.NORTH);
				frame.remove(intro_panel);

				// Changes the main area
				frame.add(classic_court, BorderLayout.CENTER);
				frame.remove(instructions);

				// Changes the bottom status
				status_panel.add(status);
				status_panel.remove(intro_status);

				// Repaints and resets
				frame.repaint();
				classic_court.reset(1);

			}
		});
		intro_panel.add(startclass);        

		// Start Button for Zen Mode

		final JButton startzen = new JButton("Zen Mode");
		startzen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Changes the panel
				frame.add(zen_panel, BorderLayout.NORTH);
				frame.remove(intro_panel);

				// Changes the main area
				frame.add(zen_court, BorderLayout.CENTER);
				frame.remove(instructions);

				// Changes the bottom status
				status_panel.add(status);
				status_panel.remove(intro_status);

				// Repaints and resets
				frame.repaint();
				zen_court.reset();
			}
		});
		intro_panel.add(startzen);

		// Button for classic mode in zen mode

		final JButton zentoclass = new JButton("Classic Mode");
		zentoclass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zen_court.endgame();

				// Changes the panel
				frame.add(classic_panel, BorderLayout.NORTH);
				frame.remove(zen_panel);

				// Changes the main area                	
				frame.add(classic_court, BorderLayout.CENTER);
				frame.remove(zen_court);

				// Repaints and resets
				frame.repaint();
				classic_court.reset(1);
			}
		});
		zen_panel.add(zentoclass);

		// Button for zen mode in classic mode

		final JButton classtozen = new JButton("Zen Mode");
		classtozen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				classic_court.endgame();

				// Changes the panel
				frame.add(zen_panel, BorderLayout.NORTH);
				frame.remove(classic_panel);

				// Changes the main area
				frame.add(zen_court, BorderLayout.CENTER);
				frame.remove(classic_court);

				// Repaints and resets
				frame.repaint();
				zen_court.reset();
			}
		});
		classic_panel.add(classtozen);

		// Resets the game - Zen Mode

		final JButton zenreset = new JButton("Reset Game");
		zenreset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zen_court.reset();
			}
		});
		zen_panel.add(zenreset);

		// The following are buttons for classic mode
		// Resets the game - Classic mode

		final JButton reset = new JButton("Reset Game");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				classic_court.reset(1);
			}
		});
		classic_panel.add(reset);

		// Resets the level - Classic mode

		final JButton resetlvl = new JButton("Reset Level");
		resetlvl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				classic_court.resetlvl();
			}
		});
		classic_panel.add(resetlvl);

		// Next Level - Classic Mode

		final JButton nextlvl = new JButton("Next Level");
		nextlvl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				classic_court.nextlvl();
			}
		});
		classic_panel.add(nextlvl);

		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Start instructions
		instructions.reset();
	}

	/*
	 * Main method run to start and run the game
	 * Initializes the GUI elements specified in Game and runs it
	 * NOTE: Do NOT delete! You MUST include this in the final submission of your game.
	 */
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Game());
	}
}
