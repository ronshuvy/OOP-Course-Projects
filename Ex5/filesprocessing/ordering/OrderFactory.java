package filesprocessing.ordering;

import java.io.File;
import java.util.Comparator;

/**
 * A factory of order instances.
 * @author ronshuvy
 */
public class OrderFactory {

	/* -------------------- CLASS CONSTANTS -------------------- */
	private static final String ABS_ORDER = "abs";
	private static final String TYPE_ORDER = "type";
	private static final String SIZE_ORDER = "size";
	private static final String REVERSED_COMMAND = "REVERSE";
	/* -------------------- CLASS CONSTANTS -------------------- */

	/**
	 * Creates a default order object with abs order
	 * @return A new order object
	 */
	public static Order createDefaultOrder() {
		return new Order(new AbsCompare());
	}

	/**
	 * Creates a new order object with a given order name
	 * @param orderParams An array of order parameters. first cell should be the order name.
	 * @return A new order object
	 * @throws BadOrderNameException If no such order name exist
	 */
	public static Order createOrder(String[] orderParams) throws BadOrderNameException{
		Comparator<File> comp;
		String orderType = orderParams[0];

		switch (orderType){
		case ABS_ORDER:
			comp = new AbsCompare();
			break;
		case TYPE_ORDER:
			comp = new TypeCompare().thenComparing(new AbsCompare());
			break;
		case SIZE_ORDER:
			comp = new SizeCompare().thenComparing(new AbsCompare());
			break;
		default:
			throw new BadOrderNameException();
		}
		return (orderParams[orderParams.length-1].equals(REVERSED_COMMAND)) ?
			   new Order(comp.reversed()) : new Order(comp);
	}

	/** Class to compare files by absolute name, going from 'a' to 'z' */
	static class AbsCompare implements Comparator<File> {
		public int compare(File f1, File f2)
		{
			return f1.getAbsolutePath().compareTo(f2.getAbsolutePath());
		}
	}

	/** Class to compare files by file type, going from 'a' to 'z' */
	static class TypeCompare implements Comparator<File> {
		private static final String EXTENSION_DELIMITER = ".";
		private static final String EMPTY_STRING = ".";

		public int compare(File f1, File f2)
		{
			int i = f1.getName().lastIndexOf(EXTENSION_DELIMITER);
			String type1 = (i > 0) ? f1.getName().substring(i+1) : EMPTY_STRING;

			i = f2.getName().lastIndexOf(EXTENSION_DELIMITER);
			String type2 = (i > 0) ? f2.getName().substring(i+1) : EMPTY_STRING;

			return type1.compareTo(type2);
		}
	}

	/** Class to compare files by file size, going from smallest to largest */
	static class SizeCompare implements Comparator<File> {
		public int compare(File f1, File f2)
		{
			return (int) (f1.length() - f2.length());
		}
	}

}

