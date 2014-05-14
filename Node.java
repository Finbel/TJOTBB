package Main;

/**
 * Class Node - represents a Node and keeps
 * track of the items and exits in it.
 */
public class Node
{
    // the coordinates of the Node.
    protected int x;
    protected int y;

    /**
     * Initially, it has no exits or items.
     * 
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
	 * Return the Y-coordinate of this Node.
	 * 
	 * @return y
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * Return the X-coordinate of this Node.
	 * 
	 * @return x
	 */
	public int getX(){
		return x;
	}
    
    public String toString() {
    	return "Node at (" + x + ", " + y + ").";
    }
}