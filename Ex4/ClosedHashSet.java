
/**
 * A hash-set based on closed-hashing with quadratic probing.
 * @author ronshuvy
 */
public class ClosedHashSet extends SimpleHashSet{

	private static final int ELEMENT_NOT_FOUND = -1;

	/* Table increase/decrease multipliers */
	private static final int RESIZE_MULTIPLIER = 2;

	/* A flag to mark a slot as deleted */
	private static final String DELETED_SLOT = "";

	/* Total number of elements inside the hash set */
	private int size;

	/* The hash set elements. value meanings :
	 * null - empty slot
	 * string - occupied slot
	 * DELETED_SLOT - empty slot when inserting, occupied slot during a search
	 */
	private String[] table;

	/**
	 * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load
	 * factor (0.75) and lower load factor (0.25).
	 */
	public ClosedHashSet(){
		this(DEFAULT_HIGHER_CAPACITY, DEFAULT_LOWER_CAPACITY);
	}

	/**
	 * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
	 * @param upperLoadFactor The upper load factor of the hash table.
	 * @param lowerLoadFactor The lower load factor of the hash table.
	 */
	public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor){
		super(upperLoadFactor, lowerLoadFactor);
		table = new String[INITIAL_CAPACITY];
		size = 0;
	}

	/**
	 * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should
	 * be ignored. The new table has the default values of initial capacity (16), upper load factor (0.75),
	 * and lower load factor (0.25).
	 * @param data Values to add to the set.
	 */
	public ClosedHashSet(String[] data){
		this(DEFAULT_HIGHER_CAPACITY, DEFAULT_LOWER_CAPACITY);
		for (String value : data)
			add(value);
	}

	/**
	 * Add a specified element to the set if it's not already in it.
	 * @param newValue New value to add to the set
	 * @return False iff newValue already exists in the set
	 */
	@Override
	public boolean add(String newValue) {
		// Check for duplicates
		if (contains(newValue))
			return false;

		// Check if table resizing is required
		float loadFactor = (float) (size + 1) / capacity;
		if (loadFactor > getUpperLoadFactor())
			resizeTable(capacity * RESIZE_MULTIPLIER);

		// Add newVal to the table
		table[findSlot(newValue)] = newValue;
		size++;
		return true;
	}

	/**
	 * Remove the input element from the set.
	 * @param toDelete Value to delete
	 * @return True iff toDelete is found and deleted
	 */
	@Override
	public boolean delete(String toDelete) {

		int index = search(toDelete);
		if (index == ELEMENT_NOT_FOUND)
			return false;

		table[index] = DELETED_SLOT;
		size--;

		// Check if table resizing is required
		float loadFactor = (float) size / capacity;
		if (loadFactor < getLowerLoadFactor())
			resizeTable(capacity / RESIZE_MULTIPLIER);

		return true;
	}

	/**
	 * Look for a specified value in the set.
	 * @param searchVal Value to search for
	 * @return True iff searchVal is found in the set
	 */
	@Override
	public boolean contains(String searchVal) {
		return search(searchVal) != ELEMENT_NOT_FOUND;
	}

	/**
	 * @return The number of elements currently in the set
	 */
	@Override
	public int size() {
		return size;
	}

	/*
	 * Resize the table and insert all existing elements by re-hashing.
	 * @param newTableSize New capacity size
	 */
	private void resizeTable(int newTableSize){
		capacity = newTableSize;
		String[] oldTable = table;
		table = new String[capacity];

		// copy elements to new table
		for (String val : oldTable)
			if (val != null && val != DELETED_SLOT)
				table[findSlot(val)] = val;
	}

	/*
	 * Returns a hash code for this string in a valid range of the table indices.
	 * @param val Value to hash
	 * @param i Attempts counter
	 * @return Table index
	 */
	private int quadraticProbing(String val, int i){
		return clamp(val.hashCode() + (i + i*i)/2);
	}

	/*
	 * Search for a given string inside the table.
	 * If the element is found, it is relocated to the first location marked for deletion that was
	 * probed during the search.
	 * @param searchVal Value to search
	 * @return its table index, -1 if not found
	 */
	private int search(String searchVal){
		int currLocation = ELEMENT_NOT_FOUND, prevLocation = ELEMENT_NOT_FOUND;

		for (int i = 0; i < table.length; i++){
			int currIndex = quadraticProbing(searchVal, i);
			String slot = table[currIndex];

			if (slot == null)
				return ELEMENT_NOT_FOUND;

			if (slot == DELETED_SLOT && prevLocation == currLocation){
				prevLocation = currIndex;
				continue;
			}

			if (slot.equals(searchVal) && slot != DELETED_SLOT){
				currLocation = currIndex;
				break;
			}
		}

		// relocate searchVal for optimization
		if (prevLocation != ELEMENT_NOT_FOUND){
			table[prevLocation] = searchVal;
			table[currLocation] = DELETED_SLOT;
			currLocation = prevLocation;
		}
		return currLocation;
	}

	/*
	 * Returns the first empty slot index for this string.
	 * @param value Value to hash
	 * @return An integer between 0 and (size-1) OR -1 if no available slot exists
	 */
	private int findSlot(String val){
		int location = -1;
		for (int i = 0; i < table.length; i++){
			location = quadraticProbing(val, i);
			if (table[location] == null || table[location] == DELETED_SLOT)
				break;
		}
		return location;
	}

}
