import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*
 * Description: Level.java is used to display the look of the playing table. Also it check for collisions and
 * handles them appropriately.
 * 
 * Created by: LYT Studios
 * 
 * date: June, 20th, 2017
*/

public class Level extends JPanel {
	
	// BufferedImages hold the images for the look of the pool table and the cue
	private BufferedImage wooden_tile;
	private BufferedImage wooden_tile_rotated90;
	private BufferedImage black_dot;
	private BufferedImage table_grass;
	private BufferedImage graphic_cue;
	
	// Linked list holds the balls in a data structure
	public LinkList ballList;

	//Uses an Array of Ball objects to easily check for collisions
	public Ball[] ball = new Ball[BALLS];
	
	// Created Cue object to render out on the pool table
	private Cue cue = new Cue(this);

	// Two instances of characters to represent two-player mode
	Character player1 = new Character("Player 1", true, 0, true, 0);
	Character player2 = new Character("Player 2", false, 0, false, 0);


	// Sets the bounds for the balls (the walls the ball hits)
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int) screenSize.getWidth();// 800;
	public static final int HEIGHT = (int) screenSize.getHeight();// 600;

	//used to set the amount of balls in play and size of Ball[] ball
	public static final int BALLS = 22;

	// Variable to hold save file
	SaveFile saveFile;

	// Collision Variables
	private double next_collision;
	private Ball first;
	private Ball second;

	JLabel p1;
	JLabel p2;

	private static boolean paused = false;
	private static boolean queued_collision_update = false;

	public static final double INIT_RADIUS = 15;
	public static final double INIT_MASS = 5;

	public static final Color YELLOW = new Color(225, 175, 0);
	public static final Color BLUE = new Color(1, 78, 146);
	public static final Color RED = new Color(247, 0, 55);
	public static final Color PURPLE = new Color(77, 30, 110);
	public static final Color ORANGE = new Color(255, 97, 36);
	public static final Color GREEN = new Color(16, 109, 62);
	public static final Color BROWN = new Color(129, 30, 33);
	public static final Color BLACK = new Color(20, 20, 20);
	public static final Color WHITE = new Color(255, 255, 255);
	public static final Color DARK_RED = new Color(63, 5, 14);

	boolean collisionOccured = false;

	double METER_TO_PIXEL = (800 / 2.84);
	int TABLE_WIDTH = (int) (1.624 * METER_TO_PIXEL);// PLay with values to
	// figure out
	// exactly how to get
	// the balls ordered
	int TABLE_HEIGHT = (int) (3.048 * METER_TO_PIXEL);
	int PLAY_WIDTH = (int) (1.42 * METER_TO_PIXEL);
	int PLAY_HEIGHT = (int) (2.84 * METER_TO_PIXEL);
	int WIDTH_GAP = (TABLE_WIDTH - PLAY_WIDTH);
	int HEIGHT_GAP = (TABLE_HEIGHT - PLAY_HEIGHT);

	double dx = WIDTH_GAP / 6 + INIT_RADIUS + 4;
	double dy = HEIGHT_GAP / 6 + INIT_RADIUS + 4;

	double centerX = WIDTH_GAP / 2 + PLAY_WIDTH / 2;
	double centerY = HEIGHT_GAP / 2 + PLAY_HEIGHT / 2;

	double initialPosX = Main.WIDTH - 400;
	double initialPosY = Main.HEIGHT / 2;

	/**
	 * Constructor: Loads up graphics files, sets button actions, creates game balls
	 */
	public Level() {

		setLayout(new FlowLayout(FlowLayout.RIGHT));

		wooden_tile = loadTextures("8 Ball Pool/resource/Images/wooden_tile.png");
		wooden_tile_rotated90 = loadTextures("8 Ball Pool/resource/Images/wooden_tile_rotated90.png");
		black_dot = loadTextures("8 Ball Pool/resource/Images/black_dot.png");
		table_grass = loadTextures("8 Ball Pool/resource/Images/table_grass.png");
		graphic_cue = loadTextures("8 Ball Pool/resource/Images/cue.png");

		p1 = new JLabel("Player 1: " + player1.points);
		p2 = new JLabel("Player 2: " + player2.points);
		p1.setFont(new Font("Magneto", Font.BOLD, 26));
		p2.setFont(new Font("Magneto", Font.BOLD, 26));
		do {
			try {
				player1.betAmount = Integer.parseInt(
						JOptionPane.showInputDialog(this, "Place your bet:", "Player 1", JOptionPane.QUESTION_MESSAGE));
				player2.betAmount = Integer.parseInt(
						JOptionPane.showInputDialog(this, "Place your bet:", "Player 2", JOptionPane.QUESTION_MESSAGE));
				break;

			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Please enter in a valid number", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} while (true);
		add(p1);
		add(Box.createRigidArea(new Dimension(25, 0)));
		add(p2);
		add(Box.createRigidArea(new Dimension(25, 0)));

		JButton exit = Main.button("Exit");
		JButton save = Main.button("Save");

		add(save);
		add(exit);

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

					System.exit(0);// Makes my life easy
				}
			}
		});

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Do you want to save the current game? Saving will overwrite any" +
						"previous saves.", "Save", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				{
					saveFile = new SaveFile(Main.content, "8 Ball Pool/resource/saveFile.txt");
					System.out.println("Created");
				}
			}
		});

		ballList = new LinkList();

		// Cue Ball
		ballList.insert(
				new Ball(initialPosX / 2.7, initialPosY, INIT_RADIUS, INIT_MASS, new Speed(0, 0), WHITE, true, 0));

		// First ball in the triangle
		ballList.insert(new Ball(initialPosX, initialPosY, INIT_RADIUS, INIT_MASS, new Speed(0, 0), YELLOW, false, 9));

		ballList.insert(
				new Ball(initialPosX + dx, initialPosY + dy, INIT_RADIUS, INIT_MASS, new Speed(0, 0), RED, true, 7));
		ballList.insert(new Ball(initialPosX + dx, initialPosY - dy, INIT_RADIUS, INIT_MASS, new Speed(0, 0), PURPLE,
				false, 12));

		ballList.insert(new Ball(initialPosX + 2 * dx, initialPosY + 2 * dy, INIT_RADIUS, INIT_MASS, new Speed(0, 0),
				RED, false, 15));
		ballList.insert(
				new Ball(initialPosX + 2 * dx, initialPosY, INIT_RADIUS, INIT_MASS, new Speed(0, 0), BLACK, true, 8));
		ballList.insert(new Ball(initialPosX + 2 * dx, initialPosY - 2 * dy, INIT_RADIUS, INIT_MASS, new Speed(0, 0),
				YELLOW, true, 1));

		ballList.insert(new Ball(initialPosX + 3 * dx, initialPosY + 3 * dy, INIT_RADIUS, INIT_MASS, new Speed(0, 0),
				GREEN, true, 6));
		ballList.insert(new Ball(initialPosX + 3 * dx, initialPosY + 1 * dy, INIT_RADIUS, INIT_MASS, new Speed(0, 0),
				BLUE, false, 10));
		ballList.insert(new Ball(initialPosX + 3 * dx, initialPosY - 1 * dy, INIT_RADIUS, INIT_MASS, new Speed(0, 0),
				RED, true, 3));
		ballList.insert(new Ball(initialPosX + 3 * dx, initialPosY - 3 * dy, INIT_RADIUS, INIT_MASS, new Speed(0, 0),
				GREEN, false, 14));

		ballList.insert(new Ball(initialPosX + 4 * dx, initialPosY + 4 * dy, INIT_RADIUS, INIT_MASS, new Speed(0, 0),
				RED, false, 11));
		ballList.insert(new Ball(initialPosX + 4 * dx, initialPosY + 2 * dy, INIT_RADIUS, INIT_MASS, new Speed(0, 0),
				BLUE, true, 2));
		ballList.insert(new Ball(initialPosX + 4 * dy, initialPosY, INIT_RADIUS, INIT_MASS, new Speed(0, 0), ORANGE,
				false, 13));
		ballList.insert(new Ball(initialPosX + 4 * dx, initialPosY - 2 * dy, INIT_RADIUS, INIT_MASS, new Speed(0, 0),
				PURPLE, true, 4));
		ballList.insert(new Ball(initialPosX + 4 * dx, initialPosY - 4 * dy, INIT_RADIUS, INIT_MASS, new Speed(0, 0),
				ORANGE, true, 5));

		addPocketBalls();

		repaint();
	}

	/**
	 * Method to load a previous save
	 *
	 * @param path of the file being saved to
     */
	public void loadGame(String path)
	{
		// Overwrites of existing variables
		ball = SaveFile.readBallInfo(path);
		Character[] characters = SaveFile.readCharacterInfo(path);

		player1 = characters[0];
		player2 = characters[1];

		ballList = new LinkList();
		for (int i = 0; i < 16; i++)
			ballList.insert(ball[i]);

		addPocketBalls();

		System.out.println("Loaded");
	}

	/**
	 * Adds [invisible] balls to the pockets; if these balls are hit, a collision is
	 * detected, and it can be assumed that the moving ball is sufficiently in contact
	 * with the pocket to be considered scored
	 */
	public void addPocketBalls() {
		// Top Pockets
		ballList.insert(new Ball(100, 100, 5, 0, new Speed(0, 0), ORANGE, true, 77));
		ballList.insert(new Ball((Main.WIDTH / 2), 100, 5, 0, new Speed(0, 0), ORANGE, true, 77));
		ballList.insert(new Ball(Main.WIDTH - 110, 110, 5, 0, new Speed(0, 0), ORANGE, true, 77));

		// Bottom Pockets
		ballList.insert(new Ball(100, Main.HEIGHT - 110, 5, 0, new Speed(0, 0), ORANGE, true, 77));
		ballList.insert(new Ball((Main.WIDTH / 2), Main.HEIGHT - 110, 5, 0, new Speed(0, 0), ORANGE, true, 77));
		ballList.insert(new Ball(Main.WIDTH - 110, Main.HEIGHT - 110, 5, 0, new Speed(0, 0), ORANGE, true, 77));
	}

	/**
	 * Loads textures from .png images to BufferedImages
	 *
	 * @param path takes in the path of the .png file
	 * @return BufferedImage of the original .png file
     */
	public BufferedImage loadTextures(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException ex) {
			System.err.println("Error; cannot read in file");
			ex.printStackTrace();
		}

		return image;
	}

	public void paintComponent(Graphics g) {
		ball = ballList.getElements();

		// Lazar code
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);

		super.paintComponent(g2d);

		// RENDERING FOR BILLIARD TABLE
		// Borders
		g.drawImage(wooden_tile, 0, 0, Main.WIDTH, 100, this);
		g.drawImage(wooden_tile, 0, Main.HEIGHT - 100, Main.WIDTH, 100, this);
		g.drawImage(wooden_tile_rotated90, 0, 0, 100, Main.HEIGHT, this);
		g.drawImage(wooden_tile_rotated90, Main.WIDTH - 100, 0, 100, Main.HEIGHT, this);

		// Table Grass
		g.drawImage(table_grass, 100, 100, Main.WIDTH - 200, Main.HEIGHT - 200, this);

		// Top Holes
		g.drawImage(black_dot, 50, 50, 100, 100, this);
		g.drawImage(black_dot, (Main.WIDTH / 2) - 50, 50, 100, 100, this);
		g.drawImage(black_dot, Main.WIDTH - 150, 50, 100, 100, this);

		// Bottom Holes
		g.drawImage(black_dot, 50, Main.HEIGHT - 150, 100, 100, this);
		g.drawImage(black_dot, (Main.WIDTH / 2) - 50, Main.HEIGHT - 150, 100, 100, this);
		g.drawImage(black_dot, Main.WIDTH - 150, Main.HEIGHT - 150, 100, 100, this);

		// RENDERING FOR CUE
		cue.updatePosition((int) ball[0].getX(), (int) ball[0].getY());
		cue.drawBack();
		cue.render(g2d, graphic_cue, this);

		// Following code detects whenever a ball collides with a pocket, and what action to
		// take when the ball sinks
		if (!paused) {
			double passed = 0.0;
			while (passed + next_collision < 1.0) {
				for (int i = 0; i < BALLS; i++) {
					if (ball[i] == first) {
						if (second.getBallNumber() == 77) {
							first.setX(10000000.0);
							first.setY(10000000.0);

							if (first.getBallNumber() == 0) {
								ball[0].setX((double) Main.WIDTH / 2);
								ball[0].setY((double) Main.HEIGHT / 2);

								swapPlayerTurn();
							} else if (first.getBallNumber() == 8) {
								if (player1.turn == true) {
									if (player1.points == 7) {
										// game won
										JFrame win = new JFrame("WINNER");
										win.setSize(500, 500);
										win.setLayout(new BorderLayout());
										JLabel winnerLabel = new JLabel("Player 1 WINS!" +  " Earnings: " + (player1.betAmount
												+ player2.betAmount));
										winnerLabel.setFont(new Font("High Tower Text", Font.BOLD, 30));
										win.getContentPane().setBackground(Color.GREEN);
										winnerLabel.setBackground(Color.GREEN);
										win.add(winnerLabel, BorderLayout.CENTER);
										win.setVisible(true);
										SwingUtilities.getWindowAncestor(Level.this).dispose();

									} else {
										// game over
										JFrame win = new JFrame("WINNER");
										win.setSize(500, 500);
										win.setLayout(new BorderLayout());
										JLabel winnerLabel = new JLabel("Player 1 WINS!" +  " Earnings: " + (player1.betAmount
												+ player2.betAmount));
										winnerLabel.setFont(new Font("High Tower Text", Font.BOLD, 30));
										win.getContentPane().setBackground(Color.GREEN);
										winnerLabel.setBackground(Color.GREEN);
										win.add(winnerLabel, BorderLayout.CENTER);
										win.setVisible(true);
										SwingUtilities.getWindowAncestor(Level.this).dispose();
									}
								} else if (player2.turn) {
									if (player2.points == 7) {
										JFrame win = new JFrame("WINNER");
										win.setSize(500, 500);
										win.setLayout(new BorderLayout());
										JLabel winnerLabel = new JLabel("Player 1 WINS!" +  " Earnings: " + (player1.betAmount
												+ player2.betAmount));
										winnerLabel.setFont(new Font("High Tower Text", Font.BOLD, 30));
										win.getContentPane().setBackground(Color.GREEN);
										winnerLabel.setBackground(Color.GREEN);
										win.add(winnerLabel, BorderLayout.CENTER);
										win.setVisible(true);
										SwingUtilities.getWindowAncestor(Level.this).dispose();

									} else {
										JFrame win = new JFrame("WINNER");
										win.setSize(500, 500);
										win.setLayout(new BorderLayout());
										JLabel winnerLabel = new JLabel("Player 1 WINS!" +  "Earnings: " + (player1.betAmount
												+ player2.betAmount));
										winnerLabel.setFont(new Font("High Tower Text", Font.BOLD, 30));
										win.getContentPane().setBackground(Color.GREEN);
										winnerLabel.setBackground(Color.GREEN);
										win.add(winnerLabel, BorderLayout.CENTER);
										win.setVisible(true);
										SwingUtilities.getWindowAncestor(Level.this).dispose();
									}
								}
							} else if (first.getSolid() == true) {
								player1.incrementScore();

								if (player1.turn != true)
									swapPlayerTurn();
							} else {
								player2.incrementScore();

								if (player2.turn != true)
									swapPlayerTurn();
							}

							first.setSpeedZero();
							first.pocketed();
						} else{
							ball[i].collide(second, next_collision);

							collisionOccured = true;
						}

					} else if (ball[i] != second) {
						ball[i].move(next_collision);
					}
				}
				passed += next_collision;
				collision_update();
			}
			next_collision += passed;
			next_collision -= 1.0;
			for (int i = 0; i < BALLS; i++) {
				ball[i].move(1.0 - passed);
			}
		}

		// Rendering for balls
		int a = 0;
		for (int i = 0; i < BALLS; i++) {
			ball[i].paint(g2d);

		}

		// Checks if balls are still; if yes, turns should be swapped,
		// unless a ball was sunk
		int keys = 0;
		for (int i = 0; i < 16; i++) {
			if (ball[i].moving() == false) {
				keys += 1;
			}
		}

		if (keys == 16 && collisionOccured == true) {
			System.out.println("Swapped");
			collisionOccured = false;
			swapPlayerTurn();
		}

		// Method detects whenever a collision has occured
		if (queued_collision_update) {
			collision_update();
		}

		// CHARACTER TURN CODE
		// Gives an indicator as to whose turn it is (green highlight means player turn,
		// red means to wait until green)
		if (player1.turn == true) {
			p1.setForeground(Color.GREEN);
			p2.setForeground(Color.RED);
		} else {
			p1.setForeground(Color.RED);
			p2.setForeground(Color.green);
		}

		// Displays scores
		p1.setText("Player 1: " + player1.points);
		p2.setText("Player 2: " + player2.points);
	}


	// Update
	public static void queue_collision_update() {
		queued_collision_update = true;
	}

	public void collision_update() {
		next_collision = Double.POSITIVE_INFINITY;
		int a = 0;
		for (int i = 0; i < BALLS - 1; i++) {
			for (int j = i + 1; j < BALLS; j++) {
				double minimo = ball[i].next_collision(ball[j]);
				if (minimo < next_collision) {
					next_collision = minimo;
					first = ball[i];
					second = ball[j];
				}
			}

		}
		queued_collision_update = false;
	}

	/**
	 * Swaps player turn
	 */
	public void swapPlayerTurn() {
		boolean turn;

		turn = !player1.isTurn();
		player1.setTurn(turn);

		turn = !player2.isTurn();
		player2.setTurn(turn);
	}

	/**
	 * etter to get a single ball from this clas
	 *
	 * @param key index position of the ball
	 * @return ball
     */
	public Ball getBall(int key) {
		return ball[key];
	}
}
