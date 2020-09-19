import oop.ex3.spaceship.Item;
import java.util.HashMap;
import java.util.Map;

/**
 * Storage class
 * @author ronshuvy
 */
public abstract class Storage {

	/* Total amount of storage units it can hold */
	protected final int totalCapacity;

	/* Current available storage units */
	protected int availableCapacity;

	/* The current items type it stores inside and each type's quantity */
	protected final Map<String, Integer> inventory;

	/**
	 * This constructor initializes a Storage object, with a given capacity.
	 * @param capacity Total amount of storage units it can hold.
	 */
	public Storage(int capacity){
		this.totalCapacity = capacity;
		this.availableCapacity = capacity;
		this.inventory = new HashMap<String, Integer>();
	}

	/**
	 * This method adds n Items of the given type to the locker.
	 * In case of an error, it prints an informative message.
	 * @param item Item type
	 * @param n Amount of items to add
	 * @return 0 or 1 on success, -1 or -2 on failure
	 */
	public abstract int addItem(Item item, int n);

	/**
	 * @param type Item type
	 * @return This method returns the number of Items of a given type the locker contains.
	 */
	public int getItemCount(String type){
		return inventory.containsKey(type) ? inventory.get(type) : 0;
	}

	/**
	 * @return This method returns a map of all the Items contained in the long-term storage unit, and
	 * their respective quantities.
	 */
	public Map<String, Integer> getInventory(){
		return inventory;
	}

	/**
	 * @return This method returns the locker’s total capacity
	 */
	public int getCapacity(){
		return totalCapacity;
	}

	/**
	 * This method returns the locker’s available capacity
	 * @return How many storage units are unoccupied by Items.
	 */
	public int getAvailableCapacity(){
		return availableCapacity;
	}
}
