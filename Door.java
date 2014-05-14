package Main;

/**
 * Class Door - represents an door square (door).
 */
public class Door extends Square
{
    // determines whether the exit is unlocked or not.
    private boolean isUnlocked = true;
    // stores what item is needed to unlock the exit.
    private Item key;
    private boolean isOpen = false;
	
    /**
     * Creates a door.
     * Initially, it has no neighbours or items.
     * 
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param isUnlocked The exit's unlocked status.
     * @param key The key that fits the lock (null if there's
     * no lock).
     * 
     */
    public Door(int x, int y, boolean isUnlocked, Item key) {
    	super(x, y);
    	this.key = key;
    	// prevents creating locked doors if no key is present.
    	if (key != null) {
    		this.isUnlocked = isUnlocked;
    	}
    }
   
    public void setKey(Key key) {
    	this.key = key;
    }
    
    /**
     * Lock the door.
     */
    public void lock() {
    	if (key != null) {
    		isUnlocked = false;
    	}
    }
    
    /**
     * Unlock the door.
     */
    public void unlock() {
    	if (key != null) {
    		isUnlocked = true;
    	}
    }
    
    /**
     * Return key associated with exit.
     * 
     * @return Key associated with exit.
     */
    public Item getKey() {
        return key;
    }
    
    public void open() {
        isOpen = true;
    }
    
    public void close() {
        isOpen = false;
    }
    
    public boolean isOpen() {
        return isOpen;
    }
    
    /**
     * Return the status of the exit (unlocked
     * or locked).
     * 
     * @return true if exit is unlocked.
     */
    public boolean isUnlocked() {
        return isUnlocked;
    }
}