package Main;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class Square - represents a square (door or space) and
 * keeps track of the items and its neighbours.
 */
public class Square extends Node
{
    // an arraylist keeping track of the square's neighbours.
    private ArrayList<Square> neighbours;
    // a map keeping track of what items are in the square.
    protected HashMap<String, Item> items = new HashMap<String, Item>();
    protected Character character = null;
    protected Character deadCharacter = null;
    protected Player player = null;

    /**
     * Initially, it has no neighbours or items.
     * 
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Square(int x, int y) {
    	super(x, y);
        neighbours = new ArrayList<Square>();
    }
    
    /**
     * Define a neighbour for this Square.
     * 
     * @param neighbour The neighbour.
     */
    public void setNeighbour(Square neighbour) {
        neighbours.add(neighbour);
    }
    
    /**
     * Place an item in this Node.
     * 
     * @param item The item to be placed.
     */
    public void addItem(Item item) {
        items.put(item.getName(), item);
    }
    
    public void addDeadCharacter(Character character) {
        deadCharacter = character;
    }
    
    public Character getDeadCharacter() {
        return deadCharacter;
    }
    
    public void addCharacter(Character character) {
        this.character = character;
    }
    
    public void removeCharacter() {
    	character = null;
    }
    
    public void removeItems() {
        items.clear();
    }
    

    public HashMap<String, Item> getItems() {
    	if (items.isEmpty()) {
    		return null;
    	}
        return items;
    }
    
    public Character getCharacter() {
    	return character;
    }
    
    /**
     * Return all neighbours.
     * 
     * @return The neighbours as an ArrayList.
     */
    public ArrayList<Square> getNeighbours() {
        return neighbours;
    }
}