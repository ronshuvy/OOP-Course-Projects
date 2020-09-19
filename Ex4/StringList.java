import java.util.Iterator;
import java.util.LinkedList;

/**
 * A wrapper-class which contains LinkedList<String> object and delegates methods to it.
 * @author ronshuvy
 */
public class StringList implements Iterable<String>{

	/* A Linked-list of strings */
	private final LinkedList<String> strings;

	/**
	 * A default constructor.
	 */
	public StringList(){
		this.strings = new LinkedList<String>();
	}

	/**
	 * Add a specified element to the list.
	 * @param toAdd New value to add to the set
	 * @return True iff toAdd successfully added
	 */
	public boolean add(String toAdd){
		return strings.add(toAdd);
	}

	/**
	 * Remove the input element from the list.
	 * @param toDelete Value to delete
	 * @return True iff toDelete is found and deleted
	 */
	public boolean remove(String toDelete){
		return strings.remove(toDelete);
	}

	/**
	 * Look for a specified value in the list.
	 * @param searchVal Value to search for
	 * @return True iff searchVal is found in the set
	 */
	public boolean contains(String searchVal){
		return strings.contains(searchVal);
	}

	/**
	 * Returns an iterator over elements of type String.
	 * @return an Iterator.
	 */
	@Override
	public Iterator<String> iterator() {
		return strings.iterator();
	}
}
