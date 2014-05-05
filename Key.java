package Main;

/**
 * Class Key - represents a key..
 */
public class Key extends Item
{
    /**
     * Create an key.
     * 
     * @param name The item's name.
     * @param questItem Determines whether item is flagged as "quest item".
     */
    public Key(String name, boolean questItem) {
        super(name, questItem);
    }
}
