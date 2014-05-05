package Main;

import java.util.HashMap;

/**
 * A matrix of nodes.
 * 
 * The coordinate system is oriented as such:
 *   ---------------> x
 *  |(0,1)
 *  |(0,2)
 *  V
 *  y
 *
 *  etc.
 */
public class Matrix {
	// A hashmap containing one hashmap for every x-coordinate.
	private final HashMap<Integer, HashMap<Integer, Node>> matrix;
	
	/**
	 * Constructs a matrix, size n*n.
	 * 
	 * @param n
	 */
	public Matrix(int n) {
		if (n < 0)
			throw new IllegalArgumentException("n = " + n);
		
		HashMap<Integer, HashMap<Integer, Node>> a = new HashMap<Integer, HashMap<Integer, Node>>();
		matrix = a;
		
		// Initiates all x-coordinates and each x-coordinate's HashMap
		// (y-dimension, each coordinate features one node).
		for (int i = 0; i < n; i++) {
			// Initiates all x-coordinates (the key is i, ranging from 0 to n, the value
			// is the current x-coordinate's hashmap in the y-dimension).
			matrix.put(i, new HashMap<Integer, Node>());
			
			// Initiates all y-coordinates and each y-coordinate's node.
			// All nodes are Spaces by default.
			for (int k = 0; k < n; k++) {
				matrix.get(i).put(k, new Space(i, k));
			}
		}
	}
	
	/**
	 * Get the node at coordinate (x, y).
	 * 
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 * @return The requested node. Null if not existing.
	 */
	public Node getNode(int x, int y) {
		return matrix.get(x).get(y);
	}
	
	/**
	 * Make the node a wall.
	 * 
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 */
	public void createWall(int x, int y) {
		matrix.get(x).remove(y);
		matrix.get(x).put(y, new Wall(x, y));
	}
	
	/**
	 * Make the node a door.
	 * 
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 * @param unlocked The exit's status.
     * @param key The key that fits the lock (null if there's
     * no lock).
	 */
	public void createDoor(int x, int y, boolean unlocked, Item key) {
		matrix.get(x).remove(y);
		matrix.get(x).put(y, new Door(x, y, unlocked, key));
	}
	
	/**
	 * Make the node a space.
	 * 
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 */
	public void createSpace(int x, int y) {
		matrix.get(x).remove(y);
		matrix.get(x).put(y, new Space(x, y));
	}
	
	/**
	 * Prints a string representation of the level.
	 */
	public String toString() {
		String string = "A matrix consisting of " + matrix.size() + "*" + matrix.size() + " nodes.\n\n";
		
		for (int y = 0; y < matrix.size(); y++) {
			for (int x = 0; x < matrix.size(); x++) {
				Node node = getNode(x, y);
				if (node instanceof Wall) {
					string += "/|/";
				} else if (node instanceof Door) {
					string += " | ";
				} else {
					string += " O ";
				}
			}
			string += "\n";
		}
		
		string += "\nKey: '/|/', ' | ' and ' O ' represent walls, doors and spaces, respectively.";
		return string;
	}
}