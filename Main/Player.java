package Main;

import java.util.HashMap;

public class Player extends Character{
	
	//A HashMap over the items in the players inventory.
	private HashMap<String, Item> inventory;
	
	/**
	 * Creates a player with name "name", health "health" and (x,y)-coordinates x and y.
	 * 
	 * @param name
	 * @param health
	 * @param x
	 * @param y
	 */
	public Player(String name, int health, int x, int y){
		super(name, health, x, y);
	}
	
	/**
	 * Adds an item to the players inventory.
	 * 
	 * @param item
	 */
    public void addItem(Item item) {
        inventory.put(item.getName(), item);
    }
    
    /**
     * Returns the item in players inventory with the name "name"
     * If not in inventory, return null
     * 
     * @param name
     * @return Item
     */
    public Item getItem(String name) {
        return inventory.get(name);
    }
    
    /**
     * Removes the item in players inventory with the name "name".
     * It then returns this item.
     * 
     * @param name
     * @return Item
     */
    public Item removeItem(String name) {
        return inventory.remove(name);
    }
	
    /**
     * Returns a string of all the items in the players inventory
     * 
     * @return String of all the items in the players inventory
     */
    public String getInventoryString() {
        if (inventory.isEmpty()) {
            return "Your inventory seem to be empty.";
        }
        
        String itemsString = "Your inventory contains: ";
        for (String key : items.keySet()) {
            itemsString += key + "; ";
        }
        
        return itemsString.substring(0, itemsString.length()-2) + ".";
    }
    
}
