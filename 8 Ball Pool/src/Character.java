/**Character class
 * 
 */
public class Character {
	String name;
	boolean turn;
	int points;
	boolean striped;
	int betAmount;
	
	public Character(String name, boolean turn, int points, boolean striped, int betAmount) {
		super();
		this.name = name;
		this.turn = turn;
		this.points = points;
		this.striped=striped;
		this.betAmount = betAmount;
	}

	public void incrementScore()
	{
		points += 1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public boolean isStriped() {
		return striped;
	}

	public void setStriped(boolean striped) {
		this.striped = striped;
	}
	
	
}
