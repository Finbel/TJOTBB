package Main;

import java.util.ArrayList;
import java.util.LinkedList;


public class Mob extends Character{
	private int range;

	/**
	 * Creates a player with name "name", health "health" and (x,y)-coordinates x and y.
	 * 
	 * @param name
	 * @param health
	 * @param x
	 * @param y
	 */
	public Mob(String name, int health, int damage, int x, int y, int range){
		super(name, health, damage, x, y);
		this.range = range;
	}
	
	public void act(Matrix matrix, Player player) {
		if (!isAlive) {
			return;
		}
		LinkedList<Node> neighbours = matrix.allNeighbours(x, y);
		if (neighbours.contains(matrix.getNode(player.getX(), player.getY()))) {
			hit(player);
			return;
		}
		
		ArrayList<Node> shortestRoute = matrix.shortestRoute(matrix.getNode(x, y), matrix.getNode(player.getX(), player.getY()), range, false);
		
		if (shortestRoute == null) {
			moveRandom(matrix);
			return;
		}
		move(matrix, (Square)shortestRoute.get(1));
	}
}