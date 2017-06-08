
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Ball {
	private double x;
	private double y;
	private double radius = 10;
	private double mass = 1;
	private Speed speed;	
	private Color color;
	private boolean solid;
	private int ballNumber;
	
	
	double slowDownSpeed = 0.015;//Double sets the slow down speed for each of the balls
	
	int distence = 100;//this sets the boundaries for the balls to bounce 
					   //off the walls inside of the playing area and not the JFrame.
	
	private Speed initialSpeed = new Speed();
	double deceleration = 0;
	boolean posSpeedX = true;
	boolean posSpeedY = true;

	
	
	
	// Constructor
	public Ball (double x, double y, double radius, double mass, Speed speed, Color ballColor, boolean solid, int ballNumber) {
		this.x = x;
		this.y = y;
		setRadius (radius);
		setMass (mass);
		this.speed = speed;
		this.color = ballColor;
		this.solid = solid;
		this.ballNumber = ballNumber;
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
	
	public void setColor (Color ballColor) {
		this.color = ballColor;
	}
	
	public Color getColor () {
		return color;
	}
	
	public void setSolid (boolean solid) {
		this.solid = solid;
	}
	
	public boolean getSolid () {
		return solid;
	}
	
	public void setBallNumber (int ballNumber) {
		this.ballNumber = ballNumber;
	}
	
	public int getBallNumber () {
		return ballNumber;
	}
	
	
	// Move
	public void move (double time) {
		move (speed.getX () * time, speed.getY () * time);
	}
	
	public void setInitialSpeed(double x, double y) {
		initialSpeed.setX(x);
		initialSpeed.setY(y);
	}
	
	
	//Look up 8 ball github and see if i can find a full game with nice code
	
	public void move(double x, double y) {
		// Gets the ball to move
		if (posSpeedX == true) {
			this.x += x;
		} else if (posSpeedX == false) {
			this.x -= x;
		}

		if (posSpeedY == true) {
			this.y += y;
		} else if (posSpeedY == false) {
			this.y -= y;
		}

		deceleration += 0.2;
		if (initialSpeed.getX() > 0 && speed.getX() > 0) {
			speed.setX(
					Math.pow(1.1, (-deceleration + (Math.log10(initialSpeed.getX() + 0.2)) / Math.log10(1.1))) - 0.2);
		} else if (speed.getX() < 0) {
			speed.setX(0);
		}

		if (initialSpeed.getY() > 0 && speed.getY() > 0) {
			speed.setY(
					Math.pow(1.1, (-deceleration + (Math.log10(initialSpeed.getY()) + 0.2) / Math.log10(1.1))) - 0.2);
		} else if (speed.getY() < 0) {
			speed.setY(0);
		}

		// These 4 variables hold the play area for the balls
		double playX = this.x - distence;
		double playY = this.y - distence;
		double playX2 = Main.WIDTH - distence;
		double playY2 = Main.HEIGHT - distence;

		// Following statements check if the ball hits the play area boundaries
		// to reverse the direction
		// as if it hit the wall, mimicking a wall bounce.


		if (playX < radius) {// Left wall
			playX = 2 * radius - playX;
			posSpeedX = true;
			Level.queue_collision_update();
		}

		if (this.x > playX2 - radius) {// Right wall
			this.x = 2 * (playX2 - radius) - this.x;
			posSpeedX = false;
			Level.queue_collision_update();
		}

		if (playY < radius) {// Top wall
			playY = 2 * radius - playY;
			posSpeedY = true;
			Level.queue_collision_update();
		}

		if (this.y > playY2 - radius) {// Bottom wall
			this.y = 2 * (playY2 - radius) - this.y;
			posSpeedY = false;
			Level.queue_collision_update();
		}
	}
	
	
	
//	public void move (double x, double y) {
//		//Gets the ball to move
//		
//
//		this.x += x;
//		this.y += y;
//
//		
////		//Makes the balls slow down better and look more realistic
////		if (speed.getX() > 1 || speed.getY()  > 1) {
////			slowDownSpeed += 0.0075;
////		}
////		else{			
////			slowDownSpeed = 0.015;
////		}
//		
//		//uncomment this and it fucks up
////	
//		
//		//These 4 variables hold the play area for the balls
//		double playX = this.x - distence;
//		double playY = this.y - distence;
//		double playX2 = Main.WIDTH - distence;
//		double playY2 = Main.HEIGHT - distence;
//		
//		
//		//Following statements check the value of the X and Y values to slow down the ball accordingly 
//		if (speed.getX() > 0) speed.subtractX(slowDownSpeed);
//		if (speed.getX() < 0) speed.addX(slowDownSpeed);
//		if (speed.getY() > 0) speed.subtractY(slowDownSpeed);
//		if (speed.getY() < 0) speed.addY(slowDownSpeed);
//		
//		
//		
//		
//		//(ballNumber == 0 && speed.getX() == 0 && speed.getY() == 0
//		if (speed.getX() < 0.005 && speed.getX() > 0.0001 || speed.getY() < 0.005 && speed.getY() > 0.0001) {
//			//Level.pause();
//			
//			speed.setX(0.0);
//			speed.setY(0.0);
//		}
//		
//			
//		
//		//Following statements check if the ball hits the play area boundaries to reverse the direction 
//		//as if it hit the wall, mimicking a wall bounce.
//		if (playX < radius) {//Left wall
//			playX = 2 * radius - playX;
//			speed.addX (-2 * speed.getX ());
//			Level.queue_collision_update ();
//		}
//		
//		if (this.x > playX2 - radius) {//Right wall
//			this.x = 2 * (playX2 - radius) - this.x;
//			speed.addX (-2 * speed.getX ());
//			Level.queue_collision_update ();
//		}
//		
//		if (playY < radius) {//Top wall
//			playY = 2 * radius - playY;
//			speed.addY (-2 * speed.getY ());
//			Level.queue_collision_update ();
//		}
//		
//		if (this.y > playY2 - radius) {//Bottom wall
//			this.y = 2 * (playY2 - radius) - this.y;
//			speed.addY (-2 * speed.getY ());
//			Level.queue_collision_update ();
//		}
//	}
//	
	
	
	
	
	// Paint
	public void paint (Graphics2D g) {//This is where i should make it stripped
		if (solid) {
			g.setColor(this.color);
			g.fill(new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius));
			
		}else{
			g.setColor(Color.WHITE);
			g.fill(new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius));
			
			//What sets it as a stripe
			g.setColor(this.color);
			g.fillRoundRect((int) (x - 6.3), (int) (y - 15), 13, 31, 15, 5);
		}
		
		
		//Setting the numbers for each ball
		if (ballNumber > 0 && ballNumber < 10) {
			g.setColor(Color.WHITE);
			g.fillOval((int) (x - 9), (int) (y - 7.3), 15, 15);
			
			g.setColor(Color.BLACK);
			g.drawString(String.valueOf(this.ballNumber), (int) (x - 6.3), (int) (y + 4));
		
		}else if(ballNumber >= 10){//When the number is larger than 10 it messes up the position of the inner white circle to show off the number, also its making sure the cue ball doesn't get a number
			g.setColor(Color.WHITE);
			g.fillOval((int) (x - 9), (int) (y - 7.3), 15, 15);
			
			g.setColor(Color.BLACK);
			g.drawString(String.valueOf(this.ballNumber), (int) (x - 10), (int) (y + 4));
		}
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
