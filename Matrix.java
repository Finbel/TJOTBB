package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

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
	private int size;
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
		size = n;
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
	 * @return The requested node. Null if not existing or out of bounds.
	 */
	public Node getNode(int x, int y) {
		if (x < 0 || x > size-1 || y < 0 || y > size-1) {
			return null;
		}
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
	
	public void createVictoryBox(int x, int y) {
		matrix.get(x).remove(y);
		matrix.get(x).put(y, new VictoryBox(x, y, 1));
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
	 * Returns all neighbours to the Node with
	 * the coordinates x and y.
	 * 
	 * @param x
	 * @param y
	 * @return all neighbours.
	 */
	public LinkedList<Node> neighbours(int x, int y) {
		LinkedList<Node> neighbours = new LinkedList<Node>();
		// Prevent Nodes on the edge of the map from being added as neighbours.
		if (!(x - 1 == 0 && x - 1 == size && y == 0 && y == size)) {
			Node node = getNode(x - 1, y);
			if (node != null) {
				neighbours.add(node);
			}
		}
		if (!(x + 1 == 0 && x + 1 == size && y == 0 && y == size)) {
			Node node = getNode(x + 1, y);
			if (node != null) {
				neighbours.add(node);
			}
		}
		if (!(x == 0 && x == size && y - 1 == 0 && y - 1 == size)) {
			Node node = getNode(x, y - 1);
			if (node != null) {
				neighbours.add(node);
			}
		}
		if (!(x == 0 && x == size && y + 1 == 0 && y + 1 == size)) {
			Node node = getNode(x, y + 1);
			if (node != null) {
				neighbours.add(node);
			}
		}
		
		return neighbours;
	}
	
	/**
	 * Returns all neighbours (inluding diagonal) to the Node with
	 * the coordinates x and y.
	 * 
	 * @param x
	 * @param y
	 * @return all neighbours.
	 */
	public LinkedList<Node> allNeighbours(int x, int y) {
		LinkedList<Node> neighbours = new LinkedList<Node>();
		int thisX = x - 1;
		int thisY = y - 1;
		// Prevent Nodes on the edge of the map from being added as neighbours.
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (i == 1 && j == 1) {
					continue;
				}
				neighbours.add(getNode(thisX + i, thisY + j));
			}
		}
		
		return neighbours;
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
	public int getSize() {
		//TBD!
		return size;
	}
	public void columnWall(int xLevel, int yStart, int yFinish){
		for (int i = yStart; i <= yFinish; i++) {
			createWall(xLevel, i);
			createWall(xLevel, i);
		}
	}
	
	public void rowWall(int yLevel, int xStart, int xFinish){
		for (int i = xStart; i <= xFinish; i++) {
			createWall(i, yLevel);
			createWall(i, yLevel);
		}
	}

	/** 
	 * Returns a list of nodes denoting the 
	 * the shortest route to the player's location.
	 * @param matrix The matrix to analyze.
	 * @param player The player to find.
	 * @param bulletPath Whether to calculate bulletpath (diagonal; discriminates walls).
	 * @return a list of the shortest route.
	*/ 
	public ArrayList<Node> shortestRoute(Node initial, Node target, int range, boolean bulletPath) {
		HashMap<Node, Boolean> visited = new HashMap<Node, Boolean>();
		for (int i = 0; i < getSize(); i++) {
			for (int j = 0; j < getSize(); j++) {
				visited.put(getNode(i, j), false);
			}
		}
		// This is a regular BFS algorithm with the exception that it stores a list of
		// all different routes until it finds the sought after vertex.
		LinkedList<Node> q = new LinkedList<Node>();
		ArrayList<ArrayList<Node>> routes = new ArrayList<ArrayList<Node>>();
		ArrayList<Node> initialRoute = new ArrayList<Node>();
		initialRoute.add(initial);
		routes.add(initialRoute);
		visited.put(initial, true);
		q.add(initial);
		
		while (!q.isEmpty()) {
			Node current = q.removeFirst();
			ArrayList<Node> currentRoute = findCurrentRoute(current, routes);
			
			if (current == target) {
				return currentRoute;
			}
			
			if (currentRoute.size() > range) {
				return null;
			}
			
			LinkedList<Node> neighbours = new LinkedList<Node>();
			if (bulletPath) {
				// Takes only the necessary neighbours that are not in the general direction, for efficiency.

				int x = current.getX();
				int y = current.getY();
				int deltaX = target.getX() - x;
				int deltaY = target.getY() - y ;
				
				if (deltaY < 0) {
					y -= 1;
				} else if (deltaY > 0) {
					y += 1;
				}
				if (deltaX < 0) {
					x -= 1;
				} else if (deltaX > 0) {
					x += 1;
				}
				
				neighbours.add(getNode(x, y));
			} else {
				neighbours = neighbours(current.getX(), current.getY());
			}
			
			for (Node neighbour : neighbours) {
				if (neighbour == null) {
					continue;
				}
				if (!bulletPath) {
					if (!(neighbour instanceof Square)) {
						continue;
					}
					if (neighbour instanceof Door) {
						if (!((Door)neighbour).isOpen()) {
							continue;
						}
					}
				}
				
				if (visited.get(neighbour) == true) {
					continue;
				}
				visited.put(current, true);
				
				// creates a new route by adding the current neighbor to the current route (the route of its
				// parents), and adds this route to the routes.
				ArrayList<Node> newRoute = new ArrayList<Node>(currentRoute);
				newRoute.add(neighbour);
				routes.add(newRoute);
				q.add(neighbour);
			}
		}
		
		return null;
	}
		
	/**
	 * Returns the route which has the current vertex as its
	 * latest addition. This means it is the current route.
	 * Returns null if none of the routes matched.
	 * 
	 * @param current the current vertex.
	 * @param routes the list of different routes to analyze.
	 * @return the current route.
	 */ 
	public static ArrayList<Node> findCurrentRoute(Node current, ArrayList<ArrayList<Node>> routes) {
		for (ArrayList<Node> route : routes) {
			if (route.get(route.size() - 1) == current) {
				return new ArrayList<Node>(route);
			}
		}
		return null;
	}
}