
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Billiard extends JPanel {
	// Members
	
	//Sets the bounds for the balls (the walls the ball hits)
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int) screenSize.getWidth();//800;
	public static final int HEIGHT = (int) screenSize.getHeight();//600;
	
	
	public static final int BALLS = 16;
	public static Ball ball[] = new Ball[BALLS];
	
	private double next_collision;
	private Ball first;
	private Ball second;
	
	private static boolean paused = false;
	private static boolean queued_collision_update = false;
	
	
	double init_radius = 7;
	double init_mass = 5;
	

	
	
	
//	public static final Color YELLOW = new Color(225, 175, 0);
//	public static final Color BLUE = new Color(1, 78, 146);
//	public static final Color RED = new Color(247, 0, 55);
//	public static final Color PURPLE = new Color(77, 30, 110);
//	public static final Color ORANGE = new Color(255, 97, 36);
//	public static final Color GREEN = new Color(16, 109, 62);
//	public static final Color BROWN = new Color(129, 30, 33);
//	public static final Color BLACK = new Color(20, 20, 20);
//	public static final Color WHITE = new Color(255, 255, 255);
//	public static final Color DARK_RED = new Color(63, 5, 14);

	
	
	
	double METER_TO_PIXEL = (800 / 2.84);
	int TABLE_WIDTH = (int) (1.624 * METER_TO_PIXEL);//PLay with values to figure out 
													 //exactly how to get the balls ordered
	int TABLE_HEIGHT = (int) (3.048 * METER_TO_PIXEL);
	int PLAY_WIDTH = (int) (1.42 * METER_TO_PIXEL);
	int PLAY_HEIGHT = (int) (2.84 * METER_TO_PIXEL);
	int WIDTH_GAP = (TABLE_WIDTH - PLAY_WIDTH);
	int HEIGHT_GAP = (TABLE_HEIGHT - PLAY_HEIGHT);
	
	
	double dx = WIDTH_GAP / 6 + init_radius;
	double dy = HEIGHT_GAP / 6 + init_radius;
	
	
	// initializing the cue ball
	double centerX = WIDTH_GAP / 2 + PLAY_WIDTH / 2;
	double centerY = HEIGHT_GAP / 2 + PLAY_HEIGHT / 2;
	
	double initialPosX = centerX;
	double initialPosY = centerY - PLAY_HEIGHT / 4;

	//dx = Math.sin(30.0 / 180.0 * Math.PI) * init_radius * 2;
	//dy = Math.cos(30.0 / 180.0 * Math.PI) * init_radius * 2;
	
	// Constructor
	public Billiard () {
		super ();
		
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton exit= Main.button("Exit");
		
		add(exit);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					SwingUtilities.getWindowAncestor(Billiard.this).dispose();
					//grabs the parent of the panel and closes it
				}
			}
		});
		
		setOpaque (true);
		setBackground (new Color (255, 255, 255));	
		
		
		
		
		ball[0] = new Ball(centerX, centerY + PLAY_HEIGHT / 4, 25, init_mass, new Speed(0, -10));
		
		ball[1] = new Ball(initialPosX, initialPosY, init_radius, init_mass, new Speed(0, 0));

		ball[2] = new Ball(initialPosX - dx, initialPosY - dy, init_radius, init_mass, new Speed(0, 0));
		ball[3] = new Ball(initialPosX + dx, initialPosY - dy, init_radius, init_mass, new Speed(0, 0));
		
		ball[4] = new Ball(initialPosX - 2 * dx, initialPosY - 2 * dy, init_radius, init_mass, new Speed(0, 0));
		ball[5] = new Ball(initialPosX, initialPosY - 2 * dy, init_radius, init_mass, new Speed(1, 2));
		ball[6] = new Ball(initialPosX + 2 * dx, initialPosY - 2 * dy, init_radius, init_mass, new Speed(0, 0));

		ball[7] = new Ball(initialPosX - 3 * dx, initialPosY - 3 * dy, init_radius, init_mass, new Speed(0, 0));
		ball[8] = new Ball(initialPosX - dx, initialPosY - 3 * dy, init_radius, init_mass, new Speed(0, 0));
		ball[9] = new Ball(initialPosX + dx, initialPosY - 3 * dy, init_radius, init_mass, new Speed(0, 0));
		ball[10] = new Ball(initialPosX + 3 * dx, initialPosY - 3 * dy, init_radius, init_mass, new Speed(0, 0));

		ball[11] = new Ball(initialPosX - 4 * dx, initialPosY - 4 * dy, init_radius, init_mass, new Speed(0, 0));
		ball[12] = new Ball(initialPosX - 2 * dx, initialPosY - 4 * dy, init_radius, init_mass, new Speed(0, 0));
		ball[13] = new Ball(initialPosX, initialPosY - 4 * dy, init_radius, init_mass, new Speed(0, 0));
		ball[14] = new Ball(initialPosX + 2 * dx, initialPosY - 4 * dy, init_radius, init_mass, new Speed(0, 0));
		ball[15] = new Ball(initialPosX + 4 * dx, initialPosY - 4 * dy, init_radius, init_mass, new Speed(0, 0));
		
		
		
		
		
		
		
		
		
		
		
//	    int diameter = 0;
//
//	    int balls_in_row = 1;
//	    int counter = 0;
//	    int rownum = 1;
//	    int current_x = WIDTH + (HEIGHT * 3 / 4);
//	    int current_y = 0;
//	    Ball b;
//
//	    /* set of loops and offset calculations to lay
//	     * the balls out in a triangle */
//		boolean drawsolid = true;
//		for (rownum = 1; rownum <= BALLS; rownum++) {
//
//			current_y = WIDTH + ((int) HEIGHT / 2) + ((balls_in_row - 1) * diameter / 2);
//
//			for (counter = 0; counter < balls_in_row; counter++) {
//				
//				ball[counter] = new Ball (
//						current_x,
//						current_y,
//		                init_radius,
//		                init_mass,
//		                new Speed (0, 0));
//				
//				drawsolid = !drawsolid;
//				//b.setVertex(current_x, current_y);
//				
//				current_y -= diameter;
//			}
//	      
//	      
//	      balls_in_row++;
//	      current_x += diameter;
//	    }
		
		
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
		//Fix ball placement, then maybe start working on adding spins, etc, 
	}
	
	// Draw
	public void paintComponent (Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint (RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g2d.setRenderingHint (RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		super.paintComponent (g);
		
		if (!paused) {
			double passed = 0.0;
			while (passed + next_collision < 1.0) {
				for (int i = 0; i < BALLS; i++) {
					if (ball[i] == first) {
						ball[i].collide (second, next_collision);
					}
					else if (ball[i] != second) {
						ball[i].move (next_collision);
					}
				}
				passed += next_collision;
				collision_update ();	
			}
			next_collision += passed;
			next_collision -= 1.0;
			for (int i = 0; i < BALLS; i++) {
				ball[i].move (1.0 - passed);
			}
		}
		
		for (int i = 0; i < BALLS; i++) {
			ball[i].paint (g2d);
		}
		
		/*
		g2d.setColor (new Color (0, 0, 0));
		if (next_collision < 1000 && first != null && second != null)
			g2d.drawLine ((int)(first.getX() + first.getSpeed().getX() * next_collision),
			              (int)(first.getY() + first.getSpeed().getY() * next_collision),
			              (int)(second.getX() + second.getSpeed().getX() * next_collision),
			              (int)(second.getY() + second.getSpeed().getY() * next_collision));
		*/
		
		if (queued_collision_update)
			collision_update ();
	}
	
	// Pause
	public static void pause () {
		paused = true;
	}
	
	public static void play () {
		paused = false;
	}
	
	public static void toggle_play_pause () {
		paused = !paused;
	}
	
	public static boolean is_paused () {
		return paused;
	}
	
	// Update
	public static void queue_collision_update () {
		queued_collision_update = true;
	}
	
	public void collision_update () {
		next_collision = Double.POSITIVE_INFINITY;
		for (int i = 0; i < BALLS-1; i++) {
			for (int j = i+1; j < BALLS; j++) {
				double minimo = ball[i].next_collision (ball[j]);
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
