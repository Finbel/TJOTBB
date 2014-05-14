package Main;

public class Player extends Character{

	/**
	 * Creates a player with name "name", health "health" and (x,y)-coordinates x and y.
	 * 
	 * @param name
	 * @param health
	 * @param x
	 * @param y
	 */
	public Player(String name, int health, int damage, int x, int y){
		super(name, health, damage, x, y);
	}
}
