import oop.ex3.spaceship.*;

/**
 * LongTermStorage class
 * @author ronshuvy
 */
public class LongTermStorage extends Storage {

	//----------------CLASS CONSTANTS----------------
	private static final int SUCCESS = 0;
	private static final int FAILURE = -1;
	private static final String ERROR_MSG = "Error: Your request cannot be completed at this time. ";
	private static final String NOT_ROOM_ERR = "Problem: no room for %d items of type %s";
	private static final int STORAGE_UNITS = 1000;
	//----------------------------------------------


	/**
	 * This constructor initializes a Long-Term Storage object.
	 */
	public LongTermStorage(){
		super(STORAGE_UNITS);
	}

	/**
	 * This method adds n Items of the given type to storage.
	 * @param item Item type
	 * @param n Amount of items to add
	 * @return 0 on success, -1 on failure
	 */
	public int addItem(Item item, int n){
		int requiredCapacity = item.getVolume() * n;

		if (n < 0 || requiredCapacity > availableCapacity)
		{
			String errorMessage = String.format((ERROR_MSG + NOT_ROOM_ERR), n, item.getType());
			System.out.println(errorMessage);
			return FAILURE;
		}

		int countItem = inventory.containsKey(item.getType()) ? inventory.get(item.getType()) + n : n;
		inventory.put(item.getType(), countItem);
		availableCapacity -= requiredCapacity;
		return SUCCESS;
	}

	/**
	 * This methods removes all items from storage.
	 */
	public void resetInventory(){
		inventory.clear();
		availableCapacity = totalCapacity;
	}


}
