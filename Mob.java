package Main;

import java.util.ArrayList;
import java.util.HashMap;
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
		LinkedList<Node> neighbours = matrix.allNeighbours(x, y);
		if (neighbours.contains(matrix.getNode(player.getX(), player.getY()))) {
			hit(player);
			return;
		}
		
		HashMap<Node, Boolean> visited = new HashMap<Node, Boolean>();
		for (int i = 0; i < matrix.getSize(); i++) {
			for (int j = 0; j < matrix.getSize(); j++) {
				visited.put(matrix.getNode(i, j), false);
			}
		}
		
		ArrayList<Node> shortestRoute = shortestRoute(matrix, player, visited);
		
		if (shortestRoute == null) {
			moveRandom(matrix);
			return;
		}
		move(matrix, (Square)shortestRoute.get(1));
	}
	
	/** 
	 * Returns a list of nodes denoting the 
	 * the shortest route to the player's location.
	 * @param matrix The matrix to analyze.
	 * @param player The player to find.
	 * @return a list of the shortest route.
	*/ 
	public ArrayList<Node> shortestRoute(Matrix matrix, Player player, HashMap<Node, Boolean> visited) { 
		// This is a regular BFS algorithm with the exception that it stores a list of
		// all different routes until it finds the sought after vertex.
		LinkedList<Node> q = new LinkedList<Node>();
		ArrayList<ArrayList<Node>> routes = new ArrayList<ArrayList<Node>>();
		ArrayList<Node> initialRoute = new ArrayList<Node>();
		initialRoute.add(matrix.getNode(x, y));
		routes.add(initialRoute);
		visited.put(matrix.getNode(x, y), true);
		q.add(matrix.getNode(x, y));
		
		while (!q.isEmpty()) {
			Node current = q.removeFirst();
			ArrayList<Node> currentRoute = findCurrentRoute(current, routes);
			
			if (current == matrix.getNode(player.getX(), player.getY())) {
				return currentRoute;
			}
			
			if (currentRoute.size() > range) {
				return null;
			}
			
			LinkedList<Node> neighbours = matrix.neighbours(current.getX(), current.getY());
			
			for (Node neighbour : neighbours) {
				if (!(neighbour instanceof Space)) {
					continue;
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