
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Billiard extends JPanel {
	// Members
	
	//Sets the bounds for the balls (the walls the ball hits)
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int) screenSize.getWidth();//800;
	public static final int HEIGHT = (int) screenSize.getHeight();//600;
	
	
	public static final int BALLS = 2;
	public static Ball ball[] = new Ball[BALLS];
	
	private double next_collision;
	private Ball first;
	private Ball second;
	private static boolean paused = false;
	private static boolean queued_collision_update = false;
	
	// Constructor
	public Billiard () {
		super ();
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton exit= Main.button("Exit");
		setOpaque (true);
		setBackground (new Color (255, 255, 255));		
		add(exit);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					SwingUtilities.getWindowAncestor(Billiard.this).dispose();//grabs the parent of this panel and closes it
				}
			}
		});
		double init_radius = 24;
		double init_mass = 5;
		
		//Start/White ball
		ball[0] = new Ball (400,
				HEIGHT/2,
		        init_radius,
		        init_mass,
		        new Speed (0, 0));
		
		//First ball in the triangle needs to be in a certain spot so the other 15 balls line up properly
		ball[1] = new Ball (
				WIDTH-500,
				HEIGHT/2,
                init_radius,
                init_mass,
                new Speed (0, 0));
		
//		for (int i = 0; i <= 14; i++) {//Adjusted for the one ball ahead
//			ball[i] = new Ball (
//					ball[i-1].getX(),
//					HEIGHT/2,
//	                init_radius,
//	                init_mass,
//	                new Speed (0, 0));
//		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//balls.removeAllElements();
	    int diameter = 0;

//	    CueBall c = new CueBall(borderWidth, borderWidth, playingWidth, playingHeight);
//	    c.setVertex(borderWidth + (0.25 * playingWidth), 
//			borderWidth + (0.50 * playingHeight));
	    
	    //diameter = Ball.getRadius() * 2;

	    int balls_in_row = 1;
	    int counter = 0;
	    int rownum = 1;
	    int current_x = WIDTH + (HEIGHT * 3 / 4);
	    int current_y = 0;
	    Ball b;
//	    Color ballColors[][] = {
//	      {Color.red},
//	      {Color.red, Color.blue}, 
//	      {Color.blue, Color.black, Color.yellow},
//	      {Color.yellow, color_brown, color_brown, color_orange},
//	      {color_orange, color_purple, color_purple, Color.green, Color.green}
//	    };

	    /* set of loops and offset calculations to lay
	     * the balls out in a triangle */
		boolean drawsolid = true;
		for (rownum = 1; rownum <= BALLS; rownum++) {

			current_y = WIDTH + ((int) HEIGHT / 2) + ((balls_in_row - 1) * diameter / 2);

			for (counter = 0; counter < balls_in_row; counter++) {
				if (drawsolid == true) {

					
					
//					b = new SolidBall(ballColors[rownum - 1][counter], borderWidth, borderWidth, playingWidth,
//							playingHeight);
				} else {
					
					
					
//					b = new StripedBall(ballColors[rownum - 1][counter], borderWidth, borderWidth, playingWidth,
//							playingHeight);
				}

				drawsolid = !drawsolid;
				//b.setVertex(current_x, current_y);
				
				current_y -= diameter;
			}
	      
	      
	      balls_in_row++;
	      current_x += diameter;
	    }
		
		
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
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
