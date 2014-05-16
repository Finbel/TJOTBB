package Main;
/**
 * this is only a square that exsists so you can move on with your life...game
 * it has no code in it because it really doesn't need any 
 * @author tor
 *
 */
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
