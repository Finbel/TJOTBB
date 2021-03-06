package Main;

import java.util.ArrayList;
import java.util.LinkedList;


public class Mob extends Character{
	private int range;

	/**
	 * Creates a mob with name "name", health "health" and (x,y)-coordinates x and y.
	 * 
	 * @param name
	 * @param health
	 * @param damage
	 * @param x
	 * @param y
	 * @param range
	 */
	public Mob(String name, int health, int damage, int x, int y, int range){
		super(name, health, damage, x, y);
		this.range = range;
	}
	
	/**
	 * makes the move around, charge players or stay dead, for good
	 * @param matrix
	 * @param player
	 */
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