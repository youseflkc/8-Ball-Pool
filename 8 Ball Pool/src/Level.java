import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Thomas on 2017-05-30.
 */
public class Level extends JPanel {
	private BufferedImage wooden_tile;
	private BufferedImage wooden_tile_rotated90;
	private BufferedImage black_dot;
	private BufferedImage table_grass;
	Sound ballHit = new Sound();

	private Cue cue = new Cue();

	// Lazar variables

	// Sets the bounds for the balls (the walls the ball hits)
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int) screenSize.getWidth();// 800;
	public static final int HEIGHT = (int) screenSize.getHeight();// 600;

	public static final int BALLS = 16;
	public static Ball ball[] = new Ball[BALLS];

	private double next_collision;
	private Ball first;
	private Ball second;

	private static boolean paused = false;
	private static boolean queued_collision_update = false;

	double init_radius = 15;
	double init_mass = 5;

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

	double dx = WIDTH_GAP / 6 + init_radius;
	double dy = HEIGHT_GAP / 6 + init_radius;

	double centerX = WIDTH_GAP / 2 + PLAY_WIDTH / 2;
	double centerY = HEIGHT_GAP / 2 + PLAY_HEIGHT / 2;

	double initialPosX = Main.WIDTH - 400;
	double initialPosY = Main.HEIGHT / 2;

	public Level() {

		setLayout(new FlowLayout(FlowLayout.RIGHT));

		wooden_tile = loadTextures("8 Ball Pool/resource/Images/wooden_tile.png");
		wooden_tile_rotated90 = loadTextures("8 Ball Pool/resource/Images/wooden_tile_rotated90.png");
		black_dot = loadTextures("8 Ball Pool/resource/Images/black_dot.png");
		table_grass = loadTextures("8 Ball Pool/resource/Images/table_grass.png");

		// Lazar Code
		// setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton exit = Main.button("Exit");

		Object[] exitOptions = { "Close Program", "Return to Main Menu", "Cancel" };

		add(exit);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opt = JOptionPane.showOptionDialog(null, "Are you sure you want to return to the main menu?",
						"Exit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, exitOptions,
						null);

				if (opt == JOptionPane.YES_OPTION) {
					System.exit(0);// Makes my life easy
				} else if (opt == JOptionPane.NO_OPTION) {
					SwingUtilities.getWindowAncestor(Level.this).dispose();
					// grabs the parent of the panel and closes it
				}
			}
		});

		// Cue Ball
		ball[0] = new Ball(initialPosX / 2.7, initialPosY, 15, init_mass, new Speed(30, 20), WHITE, true, 0);
		ball[0].setInitialSpeed(30, 20);
		// First ball in the triangle
		ball[1] = new Ball(initialPosX, initialPosY, init_radius, init_mass, new Speed(0, 0), YELLOW, false, 9);

		ball[2] = new Ball(initialPosX + dx, initialPosY + dy, init_radius, init_mass, new Speed(0, 0), RED, true, 7);
		ball[3] = new Ball(initialPosX + dx, initialPosY - dy, init_radius, init_mass, new Speed(0, 0), PURPLE, false,
				12);

		ball[4] = new Ball(initialPosX + 2 * dx, initialPosY + 2 * dy, init_radius, init_mass, new Speed(0, 0), RED,
				false, 15);
		ball[5] = new Ball(initialPosX + 2 * dx, initialPosY, init_radius, init_mass, new Speed(0, 0), BLACK, true, 8);
		ball[6] = new Ball(initialPosX + 2 * dx, initialPosY - 2 * dy, init_radius, init_mass, new Speed(0, 0), YELLOW,
				true, 1);

		ball[7] = new Ball(initialPosX + 3 * dx, initialPosY + 3 * dy, init_radius, init_mass, new Speed(0, 0), GREEN,
				true, 6);
		ball[8] = new Ball(initialPosX + 3 * dx, initialPosY + 1 * dy, init_radius, init_mass, new Speed(0, 0), BLUE,
				false, 10);
		ball[9] = new Ball(initialPosX + 3 * dx, initialPosY - 1 * dy, init_radius, init_mass, new Speed(0, 0), RED,
				true, 3);
		ball[10] = new Ball(initialPosX + 3 * dx, initialPosY - 3 * dy, init_radius, init_mass, new Speed(0, 0), GREEN,
				false, 14);

		ball[11] = new Ball(initialPosX + 4 * dx, initialPosY + 4 * dy, init_radius, init_mass, new Speed(0, 0), RED,
				false, 11);
		ball[12] = new Ball(initialPosX + 4 * dx, initialPosY + 2 * dy, init_radius, init_mass, new Speed(0, 0), BLUE,
				true, 2);
		ball[13] = new Ball(initialPosX + 4 * dy, initialPosY, init_radius, init_mass, new Speed(0, 0), ORANGE, false,
				13);
		ball[14] = new Ball(initialPosX + 4 * dx, initialPosY - 2 * dy, init_radius, init_mass, new Speed(0, 0), PURPLE,
				true, 4);
		ball[15] = new Ball(initialPosX + 4 * dx, initialPosY - 4 * dy, init_radius, init_mass, new Speed(0, 0), ORANGE,
				true, 5);

		repaint();

		// fix the slow movement
	}

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
		cue.update();
		cue.render(g2d);

		// if (!paused) {
		// double passed = 0.0;
		// while (passed + next_collision < 1.0) {
		// for (int i = 0; i < BALLS; i++) {
		// if (ball[i] == first) {
		// ball[i].collide(second, next_collision);
		// if (ball[i].getSpeed().getX() + second.getSpeed().getX() > 4
		// || ball[i].getSpeed().getY() + second.getSpeed().getY() > 4) {
		// System.out.println("hit");
		// try {
		// ballHit.loadSound("8 Ball Pool/resource/Music/pool Ball hit.wav");
		// ballHit.playSound();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (Throwable e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }
		//
		// } else if (ball[i] != second) {
		// ball[i].move(next_collision);
		// }
		// }
		// passed += next_collision;
		// collision_update();
		// }
		// next_collision += passed;
		// next_collision -= 1.0;
		
		for (int i = 0; i < BALLS; i++) {
			ball[i].paint(g2d);
		}

		for (int i = 0; i < BALLS; i++) {
			if (ball[i].getSpeed().getX() != 0 || ball[i].getSpeed().getY() != 0) {
				ball[i].move(ball[i].getSpeed().getX(), ball[i].getSpeed().getY(), ball);
			}
		}

		

		/*
		 * g2d.setColor (new Color (0, 0, 0)); if (next_collision < 1000 &&
		 * first != null && second != null) g2d.drawLine ((int)(first.getX() +
		 * first.getSpeed().getX() * next_collision), (int)(first.getY() +
		 * first.getSpeed().getY() * next_collision), (int)(second.getX() +
		 * second.getSpeed().getX() * next_collision), (int)(second.getY() +
		 * second.getSpeed().getY() * next_collision));
		 */

		if (queued_collision_update) {
			collision_update();

		}
	}

	// Lazar code

	// Pause
	public static void pause() {
		paused = true;
	}

	public static void play() {
		paused = false;
	}

	public static void toggle_play_pause() {
		paused = !paused;
	}

	public static boolean is_paused() {
		return paused;
	}

	// Update
	public static void queue_collision_update() {
		queued_collision_update = true;
	}

	public void collision_update() {
		next_collision = Double.POSITIVE_INFINITY;
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
}
