package Main;

/**
 * Class Door - represents an door square (door).
 */
public class Door extends Square
{
    // determines whether the exit is unlocked or not.
    private boolean unlocked = true;
    // stores what item is needed to unlock the exit.
    private Item key;
	
    /**
     * Creates a door.
     * Initially, it has no neighbours or items.
     * 
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param unlocked The exit's status.
     * @param key The key that fits the lock (null if there's
     * no lock).
     * 
     */
    public Door(int x, int y, boolean unlocked, Item key) {
    	super(x, y);
    	this.key = key;
    	// prevents creating locked doors if no key is present.
    	if (key != null) {
    		this.unlocked = unlocked;
    	}
    }
    
    /**
     * Lock the door.
     */
    public void lock() {
    	if (key != null) {
    		unlocked = false;
    	}
    }
    
    /**
     * Unlock the door.
     */
    public void unlock() {
    	if (key != null) {
    		unlocked = false;
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
    
    /**
     * Return the status of the exit (unlocked
     * or locked).
     * 
     * @return true if exit is unlocked.
     */
    public boolean unlocked() {
        return unlocked;
    }
}