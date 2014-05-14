package Main;

import java.util.ArrayList;

/**
 * Class Square - represents a square (door or space) and
 * keeps track of the items and its neighbours.
 */
public class Square extends Node
{
    // an arraylist keeping track of the square's neighbours.
    private ArrayList<Square> neighbours;
    // a map keeping track of what items are in the square.
    protected Item item = null;
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
        this.item = item;
    }
    
    public void addDeadCharacter(Character character) {
        this.deadCharacter = character;
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
    
    /**
     * Remove an item from this Node.
     *
     * @param name The name of the item to be removed.
     * @return The item removed. Null if unsuccessful.
     */
    public void removeItem() {
        item = null;
    }
    
    /**
     * Return item whose name is "name". If not found,
     * return null.
     * 
     * @param name The name to look for.
     * @return The item whose name is "name".
     */
    public Item getItem() {
        return item;
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