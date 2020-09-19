
/**
 * A hash-set based on chaining.
 * @author ronshuvy
 */
public class OpenHashSet extends SimpleHashSet{

	/* Table increase/decrease multipliers */
	private static final int RESIZE_MULTIPLIER = 2;

	/* Total number of elements inside the hash set */
	private int size;

	/* The hash set elements */
	private StringList[] table;

	/**
	 * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load
	 * factor (0.75) and lower load factor (0.25).
	 */
	public OpenHashSet(){
		this(DEFAULT_HIGHER_CAPACITY, DEFAULT_LOWER_CAPACITY);
	}

	/**
	 * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
	 * @param upperLoadFactor The upper load factor of the hash table.
	 * @param lowerLoadFactor The lower load factor of the hash table.
	 */
	public OpenHashSet(float upperLoadFactor, float lowerLoadFactor){
		super(upperLoadFactor, lowerLoadFactor);
		size = 0;
		table = new StringList[INITIAL_CAPACITY];

		// initialize array elements
		for (int i = 0; i < table.length; i++)
			table[i] = new StringList();
	}

	/**
	 * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should
	 * be ignored. The new table has the default values of initial capacity (16), upper load factor (0.75),
	 * and lower load factor (0.25).
	 * @param data Values to add to the set.
	 */
	public OpenHashSet(String[] data){
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
	public boolean add(String newValue){
		// Check for duplicates
		if (contains(newValue))
			return false;

		// Check if table resizing is required
		float loadFactor = (float) (size + 1) / capacity;
		if (loadFactor > getUpperLoadFactor())
			resizeTable(capacity * RESIZE_MULTIPLIER);

		// Add newVal to the table
		table[getTableIndex(newValue)].add(newValue);
		size++;
		return true;
	}

	/**
	 * Remove the input element from the set.
	 * @param toDelete Value to delete
	 * @return True iff toDelete is found and deleted
	 */
	@Override
	public boolean delete(String toDelete){

		if (!table[getTableIndex(toDelete)].remove(toDelete))
			return false;

		// Element was found and deleted
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
	public boolean contains(String searchVal){
		return table[getTableIndex(searchVal)].contains(searchVal);
	}

	/**
	 * @return The number of elements currently in the set
	 */
	@Override
	public int size(){
		return size;
	}

	/*
	 * Resize the table and insert all existing elements by re-hashing.
	 * @param newTableSize New capacity size
	 */
	private void resizeTable(int newTableSize){
		capacity = newTableSize;
		StringList[] oldTable = table;
		table = new StringList[capacity];

		// initialize array elements
		for (int i = 0; i < table.length; i++)
			table[i] = new StringList();

		// copy elements to new table
		for (StringList strings : oldTable)
			for (String val : strings)
				table[getTableIndex(val)].add(val);
	}

	/*
	 * Returns a hash code for this string in a valid range of the table indices.
	 * @param value Value to hash
	 * @return An integer between 0 and (size-1)
	 */
	private int getTableIndex(String value){
		return clamp(value.hashCode());
	}
}
