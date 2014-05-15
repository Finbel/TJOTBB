package Main;

public class VictoryBox extends Square {
	int nextLevel;
	public VictoryBox(int x, int y, int nextLevel) {
		super(x, y);
		this.nextLevel = nextLevel;
	}
	public int getNextLevel() {
		return nextLevel;
	}

}
