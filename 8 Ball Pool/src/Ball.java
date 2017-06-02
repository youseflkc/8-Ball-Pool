
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class Ball {
	private double x;
	private double y;
	private double radius = 10;
	private double mass = 1;
	private Speed speed;
	
	public static final Color lighter[] = {new Color (239, 41, 41), new Color (114, 159, 207),
	                                       new Color (252, 234, 79), new Color (252, 175, 62),
	                                       new Color (138, 226, 52), new Color (173, 127, 168),
	                                       new Color (136, 138, 133), new Color (233, 185, 110)};
	public static final Color colors[] = {new Color (204, 0, 0), new Color (52, 101, 164),
	                                      new Color (237, 212, 0), new Color (245, 121, 0),
	                                      new Color (115, 210, 22), new Color (117, 80, 123),
	                                      new Color (85, 87, 83), new Color (193, 125, 17)};
	public static final Color darker[] = {new Color (164, 0, 0), new Color (32, 74, 135),
	                                      new Color (196, 160, 0), new Color (206, 92, 0),
	                                      new Color (78, 154, 6), new Color (92, 53, 102),
	                                      new Color (46, 52, 54), new Color (143, 89, 2)};
	
	private static final int COLOR_DELAY = 20;
	private static int color_count = 0;
	private int color = 0;
	
	
	
	
	
	double slowDownSpeed = 0.015;//Double sets the slow down speed for each of the balls
	
	int distence = 100;//this sets the boundaries for the balls to bounce 
					   //off the walls inside of the playing area and not the JFrame.
	
	
	
	
	
	// Constructor
	public Ball (double x, double y, double radius, double mass, Speed speed) {
		this.x = x;
		this.y = y;
		setRadius (radius);
		setMass (mass);
		this.speed = speed;
		this.color = color_count ++ % 8;
	}
	
	// Getters and Setters
	public Speed getSpeed () {
		return speed;
	}
	
	public double getX () {
		return x;
	}
	
	public double getY () {
		return y;
	}
	
	public double getRadius () {
		return radius;
	}
	
	public void setRadius (double radius) {
		if (radius > 0)
			this.radius = radius;
	}
	
	public double getMass () {
		return mass;
	}
	
	public void setMass (double mass) {
		this.mass = mass;
	}
	
	public int getColorId () {
		return color;
	}
	
	public void setColorId (int color_id) {
		this.color = (color_id + 8) % 8;
	}
	
	// Move
	public void move (double time) {
		move (speed.getX () * time, speed.getY () * time);
	}
	
	public void move (double x, double y) {
		//Gets the ball to move
		this.x += x;
		this.y += y;
		
		
	    //Makes the balls slow down better and look more realistic
		if (speed.getX() > 1 || speed.getY()  > 1) {
			slowDownSpeed += 0.0008;
		}
		else{			
			slowDownSpeed = 0.015;
		}
		
		
		//These 4 variables hold the play area for the balls
		double playX = this.x - distence;
		double playY = this.y - distence;
		double playX2 = Main.WIDTH - distence;
		double playY2 = Main.HEIGHT - distence;
		
		
		//Following statements check the value of the X and Y values to slow down the ball accordingly 
		if (speed.getX() > 0) speed.subtractX(slowDownSpeed);
		if (speed.getX() < 0) speed.addX(slowDownSpeed);
		if (speed.getY() > 0) speed.subtractY(slowDownSpeed);
		if (speed.getY() < 0) speed.addY(slowDownSpeed);
		
			
		
		//Following statements check if the ball hits the play area boundaries to reverse the direction 
		//as if it hit the wall, mimicking a wall bounce.
		if (playX < radius) {//Left wall
			playX = 2 * radius - playX;
			speed.addX (-2 * speed.getX ());
			Level.queue_collision_update ();
		}
		
		if (this.x > playX2 - radius) {//Right wall
			this.x = 2 * (playX2 - radius) - this.x;
			speed.addX (-2 * speed.getX ());
			Level.queue_collision_update ();
		}
		
		if (playY < radius) {//Top wall
			playY = 2 * radius - playY;
			speed.addY (-2 * speed.getY ());
			Level.queue_collision_update ();
		}
		
		if (this.y > playY2 - radius) {//Bottom wall
			this.y = 2 * (playY2 - radius) - this.y;
			speed.addY (-2 * speed.getY ());
			Level.queue_collision_update ();
		}
	}
	
	
	
	
	
	// Paint
	public void paint (Graphics2D g) {//This is where i should make it stripped
		g.setColor (colors[color]);
		g.fill (new Ellipse2D.Double (x - radius, y - radius, 2 * radius, 2 * radius));
	}
	
	// Next collision
	public double next_collision (Ball next) {
		double d_x = getX() - next.getX ();
		double d_y = getY() - next.getY ();
		double d_vx = speed.getX () - next.getSpeed ().getX ();
		double d_vy = speed.getY () - next.getSpeed ().getY ();
		
		if (d_vx == 0 && d_vy == 0)
			return Double.POSITIVE_INFINITY;
		
		double a = d_vx * d_vx + d_vy * d_vy;
		double b_mezzi = d_vx * d_x + d_vy * d_y;
		double delta_quarti = Math.pow (radius + next.getRadius (), 2) * a - Math.pow (d_vx * d_y - d_vy * d_x, 2);
		
		if (delta_quarti < 0)
			return Double.POSITIVE_INFINITY;
		
		double inizio = (- b_mezzi - Math.sqrt (delta_quarti)) / a;
		double fine = (- b_mezzi + Math.sqrt (delta_quarti)) / a;

		if (fine < 0)
			return Double.POSITIVE_INFINITY;
		
		if (inizio < (inizio - fine) / 2) // Large approximation, but it should work
			return Double.POSITIVE_INFINITY;
		
		if (inizio < 0)
			return 0.0;
		
		return inizio;
	}
	
	// Collide
	public void collide (Ball next, double time) {
		move (speed.getX () * time, speed.getY () * time);
		next.move (next.getSpeed ().getX () * time, next.getSpeed ().getY () * time);
		
		double theta = Math.atan2 (next.getY () - getY(), next.getX () - getX());
		
		double v_1 = speed.getComponent (theta);
		double v_2 = next.getSpeed ().getComponent (theta);
		
		double m_1 = getMass ();
		double m_2 = next.getMass ();
		
		double w_1 = ((m_1 - m_2) * v_1 + 2 * m_2 * v_2) / (m_1 + m_2);
		double w_2 = ((m_2 - m_1) * v_2 + 2 * m_1 * v_1) / (m_1 + m_2);
		
		speed.addComponent (theta, - v_1 + w_1);
		next.getSpeed ().addComponent (theta, - v_2 + w_2);
		
//		move (speed.getX () * (1.0 - time), speed.getY () * (1.0 - time));
//		next.move (next.getSpeed ().getX () * (1.0 - time), next.getSpeed ().getY () * (1.0 - time));
	}
}
