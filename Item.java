package Main;

/**
 * Class Item - represents an item.
 */
public class Item
{
    // the name of the item.
    private String name;
    // determines whether the item is flagged as quest item
    // or not. Flagged items can't be dropped or given to
    // others.
    private boolean questItem;

    /**
     * Create an item.
     * 
     * @param name The item's name.
     * @param questItem Determines whether item is flagged as "quest item".
     */
    public Item(String name, boolean questItem) {
        this.name = name;
        this.questItem = questItem;
    }
    
    /**
     * Return the name of the item.
     * 
     * @return The name of the item.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Return the quest item status of the item.
     * 
     * @return The quest item status of the item.
     */
    public boolean isQuestItem() {
        return questItem;
    }
}
