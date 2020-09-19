import oop.ex3.spaceship.*;

/**
 * Locker class
 * @author ronshuvy
 */
public class Locker extends Storage {

	//----------------CLASS CONSTANTS----------------
	private static final int SUCCESS = 0;
	private static final int FAILURE = -1;
	private static final int ADDITION_DENIED = -2;
	private static final int SOME_ITEMS_ADDED_TO_LTS = 1;
	private static final double SIZE_LIMIT = 0.5;
	private static final double SIZE_AFTER_LTS_TRANSFER = 0.2;
	private static final String ERROR_MSG = "Error: Your request cannot be completed at this time. ";
	private static final String TOO_FEW_ITEMS_ERR =
			"Problem: the locker does not contain %d items of type %s";
	private static final String NEGATIVE_QUANTITY_ERR =
			"Problem: cannot remove a negative number of items of type %s";
	private static final String CONSTRAINT_ERR =
			"Problem: the locker cannot contain items of type %s, as it contains a contradicting item";
	private static final String NOT_ROOM_ERR =
			"Problem: no room for %d items of type %s";
	private static final String ITEMS_MOVED_TO_LST_WARNING =
			"Warning: Action successful, but has caused items to be moved to storage";
	//----------------------------------------------

	/* The associated long-term storage */
	private final LongTermStorage longTermStorage;

	/* A list of pairs of two items that are NOT allowed to reside together in that locker */
	private final Item[][] constraints;


	/**
	 * This constructor initializes a Locker object that is associated with the given long-term storage,
	 * with the given capacity and Item constraints
	 * @param lts The long-term storage in the spaceship.
	 * @param capacity Total amount of storage units it can hold.
	 * @param constraints A list of pairs of two items that are NOT allowed to reside together in that locker.
	 */
	public Locker(LongTermStorage lts, int capacity, Item[][] constraints){
		super(capacity);
		this.longTermStorage = lts;
		this.constraints = constraints;
	}

	/**
	 * This method adds n Items of the given type to the locker.
	 * In case of an error, it prints an informative message.
	 * @param item Item type
	 * @param n Amount of items to add
	 * @return 0 or 1 on success, -1 or -2 on failure
	 */
	public int addItem(Item item, int n){
		String type = item.getType();
		if (isItemSatisfyConstraints(item))
		{
			int requiredCapacity = item.getVolume() * n;

			if (n <= 0 || availableCapacity < requiredCapacity){
				String errorMessage = String.format((ERROR_MSG + NOT_ROOM_ERR), n, type);
				System.out.println(errorMessage);
				return FAILURE;
			}

			// check if there is enough space
				int typeAmount = inventory.containsKey(type) ? inventory.get(type) : 0;
				inventory.put(type, typeAmount + n);
				availableCapacity -= requiredCapacity;
				// Move some items to long-term storage if necessary
				return moveToLongTermStorage(item);
		}

		// The locker contains a contradicting item!
		String errorMessage = String.format((ERROR_MSG + CONSTRAINT_ERR), type);
		System.out.println(errorMessage);
		return ADDITION_DENIED;
	}

	/*
	 * Checks if a given item can be added according to a list of constraints.
	 * @param item Item type
	 * @return True if the item can be added to the locker, -1 otherwise.
	 */
	private boolean isItemSatisfyConstraints(Item item){
		String type = item.getType();
		for (Item[] pair : constraints)
			if (pair[0].getType().equals(type) && inventory.containsKey(pair[1].getType()) ||
				pair[1].getType().equals(type) && inventory.containsKey(pair[0].getType()))
				return false;
		return true;
	}

	/*
	 * Checks for a valid quantity of a given item type, and moves some of the items to long-term storage if
	 * not.
	 * @param item Item type
	 * @return 0 on success, -1 if long-term storage does not contains enough room.
	 */
	private int moveToLongTermStorage(Item item){
		String type = item.getType();
		int totalItems = inventory.get(type);

		// check if moving to long-term storage is required
		if (totalItems * item.getVolume() > SIZE_LIMIT * totalCapacity){
			int itemsToLocker = (int) Math.floor(
					SIZE_AFTER_LTS_TRANSFER * totalCapacity / item.getVolume());
			int itemsToLts = totalItems - itemsToLocker;

			if (itemsToLts * item.getVolume() > longTermStorage.getAvailableCapacity())
			{
				String errorMessage = String.format((ERROR_MSG + NOT_ROOM_ERR), itemsToLts, type);
				System.out.println(errorMessage);
				return FAILURE;
			}
			longTermStorage.addItem(item, itemsToLts);
			inventory.put(type, itemsToLocker);
			availableCapacity += itemsToLts * item.getVolume();

			if (itemsToLts > 0){
				System.out.println(ITEMS_MOVED_TO_LST_WARNING);
				return SOME_ITEMS_ADDED_TO_LTS;
			}
		}
		return SUCCESS;
	}

	/**
	 * This method removes n Items of a given type from the locker.
	 * Prints error message in case there are less than n of this type in the locker, or n is negative.
	 * @param item item type
	 * @param n amount of items to remove
	 * @return 0 if n Items has been successfully removed, -1 if input is invalid
	 */
	public int removeItem(Item item, int n){
		// Input validation
		String type = item.getType();
		if (n < 0){
			String errorMessage = String.format((ERROR_MSG + NEGATIVE_QUANTITY_ERR), type);
			System.out.println(errorMessage);
			return FAILURE;
		}
		if (!inventory.containsKey(type) || inventory.get(type) < n){
			String errorMessage = String.format((ERROR_MSG + TOO_FEW_ITEMS_ERR), n, type);
			System.out.println(errorMessage);
			return FAILURE;
		}

		// Ok, input is valid, removing n items from locker
		int newQuantity = inventory.get(type) - n;
		if (newQuantity == 0)
			inventory.remove(type);
		else
			inventory.put(type, newQuantity);

		availableCapacity += item.getVolume() * n;
		return SUCCESS;
	}

}
