import oop.ex3.spaceship.*;

/**
 * Spaceship class
 * @author ronshuvy
 */
public class Spaceship {

	//----------------CLASS CONSTANTS----------------
	private static final int SUCCESS = 0;
	private static final int INVALID_ID_ERR = -1;
	private static final int INVALID_CAPACITY_ERR = -2;
	private static final int FULL_LOCKERS_ERR = -3;
	//----------------------------------------------

	/* Spaceship's name */
	private final String name;

	/* Long-term storage */
	private final LongTermStorage longTermStorage;

	/* A list of pairs of two items that are NOT allowed to reside together in that locker.*/
	private final Item[][] constraints;

	/*  The created lockers */
	private final Locker[] lockers;

	/* Current number of created lockers */
	private int countLockers;

	/* Crew ID's */
	private final int[] crewIDs;

	/**
	 * Constructs a Spaceship object.
	 * @param name Spaceship's name
	 * @param crewIDs list of crew id numbers
	 * @param numOfLockers total num of lockers that spaceship can hold
	 * @param constraints A list of pairs of two items that are NOT allowed to reside together in that locker.
	 */
	public Spaceship(String name, int[] crewIDs, int numOfLockers, Item[][] constraints){
		this.name = name;
		this.crewIDs = crewIDs;
		this.lockers = new Locker[numOfLockers];
		this.countLockers = 0;
		this.constraints = constraints;
		this.longTermStorage = new LongTermStorage();
	}

	/**
	 * @return This method returns the long-term storage object associated with that Spaceship
	 */
	public LongTermStorage getLongTermStorage(){
		return longTermStorage;
	}

	/**
	 * This method creates a Locker object, and adds it as part of the Spaceship’s storage.
	 * @param crewID A crew member ID
	 * @param capacity Locker's capacity
	 * @return
	 * 0 if a locker was created successfully.
	 * -1 If the id is not valid.
	 * -2 If the given capacity does not meet the Locker class requirements.
	 * -3 If the Spaceship already contains the allowed number of lockers (as defined in the constructor).
	 */
	public int createLocker(int crewID, int capacity){

		if (!idListContains(crewID))
			return INVALID_ID_ERR;

		if (capacity < 0)
			return INVALID_CAPACITY_ERR;

		if (countLockers == lockers.length)
			return FULL_LOCKERS_ERR;

		lockers[countLockers] = new Locker(longTermStorage, capacity, constraints);
		countLockers++;
		return SUCCESS;
	}

	/*
	 * @param crewID ID number
	 * @return True if CrewID's list contains a given ID
	 */
	private boolean idListContains(int crewID){
		for (int id : crewIDs)
			if (id == crewID)
				return true;
		return false;
	}

	/**
	 * @return This methods returns an array with the crew’s ids
	 */
	public int[] getCrewIDs(){
		return crewIDs;
	}

	/**
	 * @return This methods returns an array of the Lockers, whose length is the allowed number of lockers
	 * (as defined in the constructor).
	 */
	public Locker[] getLockers(){
		return lockers;
	}
}
