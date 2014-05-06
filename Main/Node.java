package Main;

/**
 * Class Node - represents a Node and keeps
 * track of the items and exits in it.
 */
public class Node
{
    // the coordinates of the Node.
    private int x;
    private int y;

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
    
    public String toString() {
    	return "Node at (" + x + ", " + y + ").";
    }
}