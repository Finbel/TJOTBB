package Main;

import java.util.HashMap;
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
    private HashMap<String, Item> items;
    private Player player = null;
   // private Mob mob = null;

    /**
     * Initially, it has no neighbours or items.
     * 
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Square(int x, int y) {
    	super(x, y);
        neighbours = new ArrayList<Square>();
        items = new HashMap<String, Item>();
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
    
    public void addPlayer(Player player) {
    	this.player = player;
    }
    
    public void removePlayer() {
    	player = null;
    }
    /*
     
     
    public void addMob(Mob mob) {
    	this.mob = mob;
    }*/
    
    /**
     * Remove an item from this Node.
     *
     * @param name The name of the item to be removed.
     * @return The item removed. Null if unsuccessful.
     */
    public Item removeItem(String name) {
        return items.remove(name);
    }
    
    /**
     * Return item whose name is "name". If not found,
     * return null.
     * 
     * @param name The name to look for.
     * @return The item whose name is "name".
     */
    public Item getItem(String name) {
        return items.get(name);
    }
    
    public Player getPlayer() {
    	return player;
    }
    
    /**
     * Return all neighbours.
     * 
     * @return The neighbours as an ArrayList.
     */
    public ArrayList<Square> getNeighbours() {
        return neighbours;
    }
    
    /**
     * Return a string listing all items in this square.
     * 
     * @return A string listing all items.
     */
    public String getItemsString() {
        if (items.isEmpty()) {
            return "The square doesn't seem to contain anything of interest.";
        }
        
        String itemsString = "Items of interest: ";
        for (String key : items.keySet()) {
            itemsString += key + "; ";
        }
        
        return itemsString.substring(0, itemsString.length()-2) + ".";
    }
}