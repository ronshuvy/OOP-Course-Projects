
/**
 * A superclass for implementations of hash-sets implementing the SimpleSet interface.
 * @author ronshuvy
 */
public abstract class SimpleHashSet implements SimpleSet{

	/** Describes the higher load factor of a newly created hash set. */
	protected static float DEFAULT_HIGHER_CAPACITY = 0.75f;

	/** Describes the lower load factor of a newly created hash set. */
	protected static float DEFAULT_LOWER_CAPACITY = 0.25f;

	/** Describes the capacity of a newly created hash set. */
	protected static int INITIAL_CAPACITY = 16;

	/* The lower load factor before rehashing */
	private final float lowerLoadFactor;

	/* The upper load factor before rehashing */
	private final float upperLoadFactor;

	/** The capacity of the hash set */
	protected int capacity;

	/**
	 * Constructs a new hash set with the default capacities given in DEFAULT_LOWER_CAPACITY and
	 * DEFAULT_HIGHER_CAPACITY.
	 */
	protected SimpleHashSet(){
		this(DEFAULT_HIGHER_CAPACITY, DEFAULT_LOWER_CAPACITY);
	}

	/**
	 * Constructs a new hash set with capacity INITIAL_CAPACITY.
	 * @param upperLoadFactor the upper load factor before rehashing
	 * @param lowerLoadFactor the lower load factor before rehashing
	 */
	protected SimpleHashSet(float upperLoadFactor, float lowerLoadFactor){
		this.lowerLoadFactor = lowerLoadFactor;
		this.upperLoadFactor = upperLoadFactor;
		this.capacity = INITIAL_CAPACITY;
	}

	/**
	 * Clamps hashing indices to fit within the current table capacity
	 * @param index The index before clamping.
	 * @return An index properly clamped.
	 */
	protected int clamp(int index){
		return index & (capacity - 1);
	}

	/**
	 *
	 * @return The lower load factor of the table.
	 */
	protected float getLowerLoadFactor(){
		return lowerLoadFactor;
	}

	/**
	 * @return The higher load factor of the table.
	 */
	protected float getUpperLoadFactor(){
		return upperLoadFactor;
	}

	/**
	 * @return The current capacity (number of cells) of the table.
	 */
	public int capacity() {
		return capacity;
	}
}
