
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
	private double radius = 10;
	private double mass = 1;
	private Speed speed;
	private Color color;
	private boolean solid;
	private int ballNumber;
	boolean posSpeedX=true, posSpeedY=true;
	private Speed initialSpeed = new Speed();
	
	Sound ballHit = new Sound();

	double deceleration = 0.2;
	Ellipse2D.Double perimeter;

	int distance = 100;// this sets the boundaries for the balls to bounce
						// off the walls inside of the playing area and not the
						// JFrame.

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

	public void setInitialSpeed(double x, double y) {
		initialSpeed.setX(x);
		initialSpeed.setY(y);
	}

	// Move
	public void move() {
		// Gets the ball to move
		if (posSpeedX == true) {
			this.x += speed.getX();
		} else if (posSpeedX == false) {
			this.x -= speed.getX();
		}

		if (posSpeedY == true) {
			this.y += speed.getY();
		} else if (posSpeedY == false) {
			this.y -= speed.getY();
		}

		deceleration += 0.15;
		if (initialSpeed.getX() > 0 && speed.getX() > 0) {
			speed.setX(
					Math.pow(1.1, (-deceleration + (Math.log10(initialSpeed.getX() + 0.2)) / Math.log10(1.1))) - 0.2);
		} else{
			speed.setX(0);
		}

		if (initialSpeed.getY() > 0 && speed.getY() > 0) {
			speed.setY(
					Math.pow(1.1, (-deceleration + (Math.log10(initialSpeed.getY()) + 0.2) / Math.log10(1.1))) - 0.2);
		} else{
			speed.setY(0);
		}

		// These 4 variables hold the play area for the balls
		double playX = this.x - distance;
		double playY = this.y - distance;
		double playX2 = Main.WIDTH - distance;
		double playY2 = Main.HEIGHT - distance;

		// Following statements check if the ball hits the play area boundaries
		// to reverse the direction
		// as if it hit the wall, mimicking a wall bounce.

		if (playX < radius) {// Left wall
			playX = 2 * radius - playX;
			posSpeedX = true;
		}

		if (this.x > playX2 - radius) {// Right wall
			this.x = 2 * (playX2 - radius) - this.x;
			posSpeedX = false;
		}

		if (playY < radius) {// Top wall
			playY = 2 * radius - playY;
			posSpeedY = true;
		}

		if (this.y > playY2 - radius) {// Bottom wall
			this.y = 2 * (playY2 - radius) - this.y;
			posSpeedY = false;
		}

	}

	// Paint
	public void paint(Graphics2D g) {// This is where i should make it stripped
		if (solid) {
			g.setColor(this.color);
			perimeter = new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius);
			g.fill(perimeter);

		} else {
			g.setColor(Color.WHITE);
			perimeter = new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius);
			g.fill(perimeter);

			// What sets it as a stripe
			g.setColor(this.color);
			g.fillRoundRect((int) (x - 6.3), (int) (y - 15), 13, 31, 15, 5);
		}

		// Setting the numbers for each ball
		if (ballNumber > 0 && ballNumber < 10) {
			g.setColor(Color.WHITE);
			g.fillOval((int) (x - 9), (int) (y - 7.3), 15, 15);

			g.setColor(Color.BLACK);
			g.drawString(String.valueOf(this.ballNumber), (int) (x - 6.3), (int) (y + 4));

		} else if (ballNumber >= 10) {// When the number is larger than 10 it
										// messes up the position of the inner
										// white circle to show off the number,
										// also its making sure the cue ball
										// doesn't get a number
			g.setColor(Color.WHITE);
			g.fillOval((int) (x - 9), (int) (y - 7.3), 15, 15);

			g.setColor(Color.BLACK);
			g.drawString(String.valueOf(this.ballNumber), (int) (x - 10), (int) (y + 4));
		}
	}

	// Next collision
	public boolean next_collision(Ball next) {
		double d_x = Math.abs(getX() - next.getX());
		double d_y = Math.abs(getY() - next.getY());

		if (Math.sqrt(d_x * d_x + d_y * d_y) < (radius * 2) && Math.sqrt(d_x * d_x + d_y * d_y) != 0) {
			System.out.println("hit!");
			return true;
		}
		return false;
	}

	// Collide
	public void collide(Ball next) {
		try {
			ballHit.loadSound("8 Ball Pool/resource/Music/pool Ball hit.wav");
			ballHit.playSound();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		double totalVelA = Math.sqrt(speed.getX() * speed.getX() + speed.getY() * speed.getY());
		double totalVelB = Math.sqrt(
				next.getSpeed().getX() * next.getSpeed().getX() + next.getSpeed().getY() * next.getSpeed().getY());

		double Atheta = Math.atan2(speed.getY(), speed.getX());
		double Btheta = Math.atan2(next.getSpeed().getY(), next.getSpeed().getX());

		double dx = speed.getX() - next.getSpeed().getX();
		double dy = speed.getY() - next.getSpeed().getY();

		double dxx = x - next.getX();
		double dyy = y - next.getY();

		double angle = Math.atan2(dyy, dxx);

		double newAx = totalVelB * Math.cos(Btheta - angle) * Math.cos(angle)
				+ totalVelA * Math.sin(Atheta - angle) * Math.cos(angle + 45);
		double newAy = totalVelB * Math.cos(Btheta - angle) * Math.sin(angle)
				+ totalVelA * Math.sin(Atheta - angle) * Math.sin(angle + 45);

		double newBx = totalVelA * Math.cos(Atheta - angle) * Math.cos(angle)
				+ totalVelB * Math.sin(Btheta - angle) * Math.cos(angle + 45);
		double newBy = totalVelA * Math.cos(Atheta - angle) * Math.sin(angle)
				+ totalVelB * Math.sin(Btheta - angle) * Math.sin(angle + 45);

		speed.setX(-newAx);
		speed.setY(newAy);
		setInitialSpeed(-newAx, newAy);
		if (speed.getX() < 0) {
			speed.setX(speed.getX()*-1);
			setInitialSpeed(-newAx*-1,newAy);
			posSpeedX = false;
		} else {
			posSpeedX = true;
		}
		if (speed.getY() < 0) {
			speed.setY(speed.getY()*-1);
			setInitialSpeed(-newAx,newAy*-1);
			posSpeedY = false;
		} else {
			posSpeedY = true;
		}

		next.getSpeed().setX(newBx);
		next.getSpeed().setY(newBy);
		next.setInitialSpeed(newBx, newBy);
		if (next.getSpeed().getX() < 0) {
			next.speed.setX(next.getSpeed().getX()*-1);
			next.setInitialSpeed(newBx*-1, newBy);
			next.posSpeedX = false;
		} else {
			next.posSpeedX = true;
		}
		if (next.getSpeed().getY() < 0) {
			next.speed.setY(next.getSpeed().getY()*-1);
			next.setInitialSpeed(newBx, newBy*-1);
			next.posSpeedY = false;
		} else {
			next.posSpeedY = true;
		}
		
		move();
		next.move();
		

	}
}
