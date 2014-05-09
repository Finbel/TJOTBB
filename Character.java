public class Character {
	
	// The name of the character
	private String name;
	
	// the health of the character
	private int health;
	
	// x and y position to keep track of where the player is.
	private int x;
	private int y;
	//A HashMap over the items in the characters inventory.
	private HashMap<String, Item> inventory;
	

	/**
	 * Creates a character with a name, health and a (x,y)-position on the matrix.
	 * 
	 * @param name 		The name of the character
	 * @param health 	The health of the character
	 * @param x 		The X-coordinate of the character
	 * @param y			The Y-coordinate of the character
	 */
	public Character(String name, int health, int x, int y){
		this.name = name;
		this.health = health;
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
