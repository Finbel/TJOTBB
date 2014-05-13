package Main;

import java.util.ArrayList;
import java.util.LinkedList;


public class Mob extends Character{

	/**
	 * Creates a player with name "name", health "health" and (x,y)-coordinates x and y.
	 * 
	 * @param name
	 * @param health
	 * @param x
	 * @param y
	 */
	public Mob(String name, int health, int x, int y){
		super(name, health, x, y);
	}
	
	/** 
	 * Returns a list of nodes denoting the 
	 * the shortest route to the sought after vertex.
	 * @param g the graph to analyze.
	 * @param from the vertex to begin at.
	 * @return to vertex to find.
	*/ 
	public static ArrayList<Integer> shortestRoute(Graph graph, int from, int to) { 
		// This is a regular BFS algorithm with the exception that it stores a list of
		// all different routes until it finds the sought after vertex.
		
		LinkedList<Integer> q = new LinkedList<Integer>();
		ArrayList<ArrayList<Integer>> routes = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> initialRoute = new ArrayList<Integer>();
		initialRoute.add(from);
		routes.add(initialRoute);
		visited[from] = true;
		q.add(new Integer(from));
		
		while (!q.isEmpty()) {
			int current = q.removeFirst().intValue();
			
			ArrayList<Integer> currentRoute = findCurrentRoute(current, routes);
			
			if (current == to) {
				return currentRoute;
			}
			
			VertexIterator neighbors = graph.neighbors(current);
			
			while (neighbors.hasNext()) {
				int neighbor = neighbors.next();
				if (visited[neighbor] == true) {
					continue;
				} visited[neighbor] = true;
				
				// creates a new route by adding the current neighbor to the current route (the route of its // parents), and adds this route to the routes.
				ArrayList<Integer> newRoute = new ArrayList<Integer>(currentRoute);
				newRoute.add(new Integer(neighbor));
				routes.add(newRoute);
				q.add(new Integer(neighbor));
				
			}
			
			return null;
		}
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
	public static ArrayList<Integer> findCurrentRoute(int current, ArrayList<ArrayList<Integer>> routes) {
		for (ArrayList<Integer> route : routes) {
			if (route.get(route.size() - 1).intValue() == current) {
				return new ArrayList<Integer>(route);
				}
			}
		return null;
		}
	}
}

