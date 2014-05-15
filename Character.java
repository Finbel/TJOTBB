package Main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Character {
	
	// The name of the character
	protected String name;
	
	// the health of the character
	protected int health;
	
	// the damage of the character
	protected int damage;
	
	// the alive status of the character
	protected boolean isAlive = true;
	
	// x and y position to keep track of where the player is.
	protected int x;
	protected int y;
	//A HashMap over the items in the characters inventory.
	protected HashMap<String, Item> inventory = new HashMap<String, Item>();
	

	/**
	 * Creates a character with a name, health and a (x,y)-position on the matrix.
	 * 
	 * @param name 		The name of the character
	 * @param health 	The health of the character
	 * @param x 		The X-coordinate of the character
	 * @param y			The Y-coordinate of the character
	 */
	public Character(String name, int health, int damage, int x, int y){
		this.name = name;
		this.health = health;
		this.damage = damage;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the name of the character
	 * 
	 * @return name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Returns the health of the character
	 * 
	 * @return health
	 */
	public int getHealth(){
		return health;
	}
	
	/**
	 * Returns true if character is alive
	 * 
	 * @return true if character is alive
	 */
	public boolean isAlive(){
		return isAlive;
	}
	
	/**
	 * Return the Y-coordinate of the character
	 * 
	 * @return y
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * Return the X-coordinate of the character
	 * 
	 * @return x
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * Set the name of the character
	 * 
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Set the health of the character
	 * 
	 * @param health
	 */
	public void setHealth(int health){
		this.health = health;
	}
	
	/**
	 * Set the x-coordinate of the character
	 * 
	 * @param x
	 */
	public void setX(int x){
		this.x = x;
	}
	
	/**
	 * Set the y-coordinate of the character
	 * 
	 * @param y
	 */
	public void setY(int y){
		this.y = y;
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
    
    public int getDamage() {
        return damage;
    }
    
    public boolean hasItem(Item item) {
        return inventory.containsValue(item);
    }
    
    /**
     * Inflicts damage on target.
     * 
     * @param character
     */
    public void hit(Character target) {
    	target.gotHit(damage);
    }
    
    /**
     * Receives damage.
     * 
     * @param character
     */
    public void gotHit(int damage) {
    	health -= damage;
    	if(health <= 0) {
    		isAlive = false;
    	}
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
    
    public boolean move(Matrix matrix, Square destination) {
    	if (destination.getCharacter() != null) {
    		if (destination.getCharacter().isAlive()) {
        		return false;
    		}
    	}
    	if (destination instanceof Door) {
    		if (!((Door)destination).isOpen()) {
    			return false;
    		}
    	}
    	
    	((Square)matrix.getNode(x, y)).removeCharacter();
		destination.addCharacter(this);
    	x = destination.getX();
    	y = destination.getY();
    	if (destination.getItem() != null) {
    		addItem(destination.getItem());
    		destination.removeItem();
    		if (destination.getItem() instanceof ZombieSlayer) {
    			damage += ((ZombieSlayer)destination.getItem()).getDamage();
    		}
    	}
    	return true;

    }
    
    public void moveRandom(Matrix matrix) {
    	Random random = new Random();
    	LinkedList<Node> neighbours = matrix.neighbours(x, y);
		Node destination = neighbours.get(random.nextInt(neighbours.size()));
		if (destination instanceof Square) {
			move(matrix, (Square)destination);
    	}
    }
    
    public boolean tryOpen(Matrix matrix) {
    	LinkedList<Node> neighbours = matrix.allNeighbours(x, y);
    	for (Node neighbour : neighbours) {
    		if (neighbour instanceof Door) {
    			if (((Door)neighbour).isOpen()) {
    				((Door)neighbour).close();
    				if (inventory.containsValue(((Door)neighbour).getKey())) {
    					((Door)neighbour).close();
    				}
    				return true;
    			}
	    		if (!((Door)neighbour).isOpen()) {
	    			if (!((Door)neighbour).isUnlocked()) {
	    				if (inventory.containsValue(((Door)neighbour).getKey())) {
	    					((Door)neighbour).unlock();
	    					((Door)neighbour).open();
	    					return true;
	    				}
	    				return false;
	    			}
	    			((Door)neighbour).open();
	    			return true;
    	    	}
    		}
    	}
    	return false;
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
        //for (String key : items.keySet()) {
          //  itemsString += key + "; ";
        //}
        
        return itemsString.substring(0, itemsString.length()-2) + ".";
    }
}
