package filesprocessing.ordering;

import java.io.File;
import java.util.Comparator;
import java.util.List;

/**
 * A class represents an order type.
 */
public class Order {

	private final Comparator<File> comparator;

	/**
	 * An order constructor
	 * @param comp Comparator
	 */
	public Order(Comparator<File> comp){
		this.comparator = comp;
	}

	/**
	 * Sorts a given list of files by using the comparator, given in the constructor.
	 * @param files A list of files.
	 */
	public void sort(List<File> files){
		Sorter.sort(files, comparator);
	}
}


