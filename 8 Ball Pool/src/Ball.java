import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Ball {
	private double x;
	private double y;
	private double radius = 15;
	private double mass = 1;
	private Speed speed;
	private Color color;
	private boolean solid;
	private int ballNumber;
	Sound ballHit = new Sound();
	Boolean pocketed = false;

	double slowDownSpeed = 0.02;// Double sets the slow down speed for each of
								// the balls

	int distance = 100;// this sets the boundaries for the balls to bounce
	// off the walls inside of the playing area and not the JFrame.

	// Constructor
	public Ball(double x, double y, double radius, double mass, Speed speed, Color ballColor, boolean solid,
			int ballNumber) {
		this.x = x;
		this.y = y;
		setRadius(radius);
		setMass(mass);
		this.speed = speed;
		this.color = ballColor;
		this.solid = solid;
		this.ballNumber = ballNumber;
	}

	// Getters and Setters
	public Speed getSpeed() {
		return speed;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		if (radius > 0)
			this.radius = radius;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public void setColor(Color ballColor) {
		this.color = ballColor;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public boolean getSolid() {
		return solid;
	}

	public void setBallNumber(int ballNumber) {
		this.ballNumber = ballNumber;
	}

	public int getBallNumber() {
		return ballNumber;
	}

	
	
	
	
	
	public void setSpeedZero() {
		this.speed.setX(0.0);
		this.speed.setY(0.0);
	}

	//Sets the current ball to "pocket" once ball has been detected inside of a pocket
	public void pocketed() {
		this.pocketed = true;
	}
	
	//Checks to see if the current ball is moving
	public boolean moving() {
		if (speed.getX() == 0 && speed.getY() == 0) {
			return false;
		}
		return true;
	}

	//Method is used to have the ball moving at a current speed using the time
	public void move(double time) {
		move(speed.getX() * time, speed.getY() * time);
	}

	//
	public void move(double x, double y) {

		// Makes the balls slow down better and look more realistic
		if (speed.getX() > 1 && speed.getY() > 1) {
			slowDownSpeed += 0.0075;
		} else {
			slowDownSpeed = 0.04;
		}

		
		// Following statements check the value of the X and Y values to slow
		// down the ball accordingly
		if (speed.getX() < 1 && speed.getX() > 0) {
			speed.setX(0);
		} else if (speed.getX() > -1 && speed.getX() < 0) {
			speed.setX(0);
		} else if (speed.getX() > 0) {
			speed.subtractX(slowDownSpeed);
		} else if (speed.getX() < 0) {
			speed.addX(slowDownSpeed);
		}
		if (speed.getY() < 1 && speed.getY() > 0) {
			speed.setY(0);
		} else if (speed.getY() > -1 && speed.getY() < 0) {
			speed.setY(0);
		} else if (speed.getY() > 0) {
			speed.subtractY(slowDownSpeed);
		} else if (speed.getY() < 0) {
			speed.addY(slowDownSpeed);
		}

		//Updates object variables
		this.x += x;
		this.y += y;

		// These 4 variables hold the play area for the balls
		double playX = this.x - distance;
		double playY = this.y - distance;
		double playX2 = Main.WIDTH - distance;
		double playY2 = Main.HEIGHT - distance;

		// Following statements check if the ball hits the play area boundaries
		// to reverse the direction as if it hit the wall, mimicking a wall bounce.
		if (playX < radius + 2) {// Left wall
			playX = 2 * radius - playX;
			speed.addX(-2 * speed.getX());
			Level.queue_collision_update();
		}

		if (this.x > playX2 - radius + 2) {// Right wall
			this.x = 2 * (playX2 - radius) - this.x;
			speed.addX(-2 * speed.getX());
			Level.queue_collision_update();
		}

		if (playY < radius + 2) {// Top wall
			playY = 2 * radius - playY;
			speed.addY(-2 * speed.getY());
			Level.queue_collision_update();
		}

		if (this.y > playY2 - radius + 2) {// Bottom wall
			this.y = 2 * (playY2 - radius) - this.y;
			speed.addY(-2 * speed.getY());
			Level.queue_collision_update();
		}

		Level.queue_collision_update();
	}

	//Called once when the ball object is first being created
	public void paint(Graphics2D g) {
		
		//Makes sure to color the ball correctly if its striped or solid
			if (solid) {
				//Checks to see if the current ball is the pocket ball and draws the ball appropriately.
				if (ballNumber == 77) {
					g.setColor(Color.BLACK);
					g.fill(new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius));
				} else {
					g.setColor(this.color);
					g.fill(new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius));
				}

			} else {
				g.setColor(Color.WHITE);
				g.fill(new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius));

				// What sets it as a stripe
				g.setColor(this.color);
				g.fillRoundRect((int) (x - 6.3), (int) (y - 15), 13, 31, 15, 5);
			}

			// Setting the numbers for each ball
			if (ballNumber > 0 && ballNumber < 10 && ballNumber != 77) {
				g.setColor(Color.WHITE);
				g.fillOval((int) (x - 9), (int) (y - 7.3), 15, 15);

				g.setColor(Color.BLACK);
				g.drawString(String.valueOf(this.ballNumber), (int) (x - 6.3), (int) (y + 4));

				
				// When the number is larger than 10 it messes up the position
				// of the inner
				// white circle to show off the number, also its making sure the
				// cue ball doesn't get a number
			} else if (ballNumber >= 10 && ballNumber != 77) {
				g.setColor(Color.WHITE);
				g.fillOval((int) (x - 9), (int) (y - 7.3), 15, 15);

				g.setColor(Color.BLACK);
				g.drawString(String.valueOf(this.ballNumber), (int) (x - 10), (int) (y + 4));
			}
		
	}

	//Finds the next collision that will occur
	public double next_collision(Ball next) {
		
		//Grabs the position of the ball and angle of speed
		double d_x = getX() - next.getX();
		double d_y = getY() - next.getY();
		double d_vx = speed.getX() - next.getSpeed().getX();
		double d_vy = speed.getY() - next.getSpeed().getY();

		//Makes sure ball has velocity
		if (d_vx == 0 && d_vy == 0)
			return Double.POSITIVE_INFINITY;

		//Creates the top range angle 
		double a = d_vx * d_vx + d_vy * d_vy;
		
		//Creates the bottom range angle
		double b_mezzi = d_vx * d_x + d_vy * d_y;
		
		//The range which the bounce angle can go into
		double delta_quarti = Math.pow(radius + next.getRadius(), 2) * a - Math.pow(d_vx * d_y - d_vy * d_x, 2);

		if (delta_quarti < 0)
			return Double.POSITIVE_INFINITY;

		//Both points calculate the point which will cause the next next collision
		double beginning = (-b_mezzi - Math.sqrt(delta_quarti)) / a;
		double end = (-b_mezzi + Math.sqrt(delta_quarti)) / a;

		if (end < 0)
			return Double.POSITIVE_INFINITY;

		//Makes sure a proper collision will occur, if not return "nothing" (positive value, 
		//but doesn't have a significant purpose besides not throwing errors)
		if (beginning < (beginning - end) / 2) // Large approximation, but it should work
			return Double.POSITIVE_INFINITY;

		if (beginning < 0)
			return 0.0;

		return beginning;
	}

	// Collide
	public void collide(Ball next, double time) {
		try {
			ballHit.loadSound("8 Ball Pool/resource/Music/pool Ball hit.wav");
			ballHit.playSound();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Sets the angle for the bounce
		double theta = Math.atan2(next.getY() - getY(), next.getX() - getX());
		
		//Finds both current angles both balls are following
		double v_1 = speed.getComponent(theta);
		double v_2 = next.getSpeed().getComponent(theta);

		double m_1 = getMass();
		double m_2 = next.getMass();
		
		//Determines the bounce angles for both balls
		double w_1 = ((m_1 - m_2) * v_1 + 2 * m_2 * v_2) / (m_1 + m_2);
		double w_2 = ((m_2 - m_1) * v_2 + 2 * m_1 * v_1) / (m_1 + m_2);
		
		//Set each ball and their new angles to follow
		speed.addComponent(theta, -v_1 + w_1);
		next.getSpeed().addComponent(theta, -v_2 + w_2);
		
//		move(speed.getX() * (1.0 - time), speed.getY() * (1.0 - time));
//		next.move(next.getSpeed().getX() * (1.0 - time), next.getSpeed().getY() * (1.0 - time));
	}
}
