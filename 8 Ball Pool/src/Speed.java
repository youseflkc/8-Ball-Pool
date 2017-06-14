class Speed {
	private double x = 0;
	private double y = 0;
	
	public Speed (double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Speed(){
		x=0;
		y=0;
	}
	
	public double getX () {
		return x;
	}
	
	public double getY () {
		return y;
	}
	
	public double getComponent (double theta) {
		return Math.cos (theta) * x + Math.sin (theta) * y;
	}
	
	public void addX (double speed) {
		x += speed;
	}
	
	public void addY (double speed) {
		y += speed;
	}

	public void subtractX (double speed) {
		x -= speed;
	}
	
	public void subtractY (double speed) {
		y -= speed;
	}
	public void setX(double xSpeed){
		x=xSpeed;
	}
	public void setY(double ySpeed){
		y=ySpeed;
	}
	
	public boolean isEmpty(){
		if(x==0&&y==0){
			return true;
		}else{
			return false;
		}
	}
	
	public void addComponent (double theta, double speed) {
		x += Math.cos (theta) * speed;
		y += Math.sin (theta) * speed;
	}
}