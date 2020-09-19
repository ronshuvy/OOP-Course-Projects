package filesprocessing.filtering;

import java.io.File;
import java.util.function.Predicate;

/**
 * A factory of filter instances
 * @author roshuvy
 */
public class FilterFactory {

	/* -------------------- CLASS CONSTANTS -------------------- */
	private static final String GREATER_THAN_FILTER = "greater_than";
	private static final String SMALLER_THAN_FILTER = "smaller_than";
	private static final String BETWEEN_FILTER = "between";
	private static final String FILE_FILTER = "file";
	private static final String CONTAINS_FILTER = "contains";
	private static final String PREFIX_FILTER = "prefix";
	private static final String SUFFIX_FILTER = "suffix";
	private static final String WRITABLE_FILTER = "writable";
	private static final String HIDDEN_FILTER = "hidden";
	private static final String EXECUTABLE_FILTER = "executable";
	private static final String ALL_FILTER = "all";
	private static final String NEGATE_COMMAND = "NOT";
	private static final String TRUE_VALUE = "YES";
	private static final String FALSE_VALUE = "NO";
	private static final String EMPTY_STRING = "";
	/* -------------------- CLASS CONSTANTS -------------------- */

	/**
	 * @return A default filter such that all files are matched.
	 */
	public static Filter createDefaultFilter(){
		return new Filter(FilePredicates.allFilter());
	}

	/**
	 * Creates a new filter from a given parameters.
	 * @param filterData An array of filter parameters. the first cell should be the filter name.
	 * @return A new filter
	 * @throws BadFilterNameException If no such filter exist
	 * @throws BadFilterParameterException If no such parameter exist
	 */
	public static Filter createFilter(String[] filterData) throws BadFilterNameException,
																  BadFilterParameterException {
		Predicate<File> predicate;
		String filterType = filterData[0];
		double min, max;

		switch (filterType) {
		case GREATER_THAN_FILTER:
			min = Double.parseDouble(filterData[1]);
			verifyNonNegative(min);
			predicate = FilePredicates.greaterThanFilter(min);
			break;
		case SMALLER_THAN_FILTER:
			max = Double.parseDouble(filterData[1]);
			verifyNonNegative(max);
			predicate = FilePredicates.smallerThanFilter(max);
			break;
		case BETWEEN_FILTER:
			min = Double.parseDouble(filterData[1]);
			max = Double.parseDouble(filterData[2]);
			verifyRangeNumbers(min, max);
			predicate = FilePredicates.betweenFilter(min, max);
			break;
		case FILE_FILTER:
			predicate = FilePredicates.fileFilter(getTextValue(filterData));
			break;
		case CONTAINS_FILTER:
			predicate = FilePredicates.containsFilter(getTextValue(filterData));
			break;
		case PREFIX_FILTER:
			predicate = FilePredicates.prefixFilter(getTextValue(filterData));
			break;
		case SUFFIX_FILTER:
			predicate = FilePredicates.suffixFilter(getTextValue(filterData));
			break;
		case WRITABLE_FILTER:
			predicate = FilePredicates.writableFilter(getTruthValue(filterData[1]));
			break;
		case HIDDEN_FILTER:
			predicate = FilePredicates.hiddenFilter(getTruthValue(filterData[1]));
			break;
		case EXECUTABLE_FILTER:
			predicate = FilePredicates.executableFilter(getTruthValue(filterData[1]));
			break;
		case ALL_FILTER:
			predicate = FilePredicates.allFilter();
			break;
		default:
			throw new BadFilterNameException();
		}
		return filterData[filterData.length-1].equals(NEGATE_COMMAND) ?
			   new Filter(predicate.negate()) : new Filter(predicate);
	}


	/*
	 * @param data An array of filter data
	 * @return A string parameter for text filters such as file, contains, prefix, and suffix filters.
	 */
	private static String getTextValue(String[] data){
		return (data.length == 1) ? EMPTY_STRING : data[1];
	}

	/*
	 * Verify that min is smaller than max and they are both non-negative
	 * @throws BadFilterParamsException
	 */
	private static void verifyRangeNumbers(double min, double max) throws BadFilterParameterException {
		if (min < 0)
			throw new NegativeSizeException();
		if (min > max)
			throw new UnorderedRangeException();
	}

	/*
	 * Verify that a given number is non-negative
	 * @param num A given number
	 * @throws BadFilterParamsException if num is negative
	 */
	private static void verifyNonNegative(double num) throws BadFilterParameterException {
		if (num < 0)
			throw new NegativeSizeException();
	}

	/*
	 * Returns {@code true} if text equals to TRUE_VALUE, {@code false} if equals to FALSE_VALUE
	 * @param text A given string
	 * @throws BadFilterParamsException if text does not equal to TRUE_VALUE or FALSE_VALUE
	 */
	private static boolean getTruthValue(String text) throws BadFilterParameterException {
		if (text.equals(TRUE_VALUE))
			return true;

		if (text.equals(FALSE_VALUE))
			return false;

		throw new BadBooleanValueException();
	}

	/* A class defining all file predicates */
	private static class FilePredicates {

		private static final int KILOBYTE = 1024;

		/**
		 * File size is strictly greater than the given number of k-bytes
		 */
		private static Predicate<File> greaterThanFilter(double size) {
			return f -> f.length() > size * KILOBYTE;
		}

		/**
		 * File size is strictly less than the given number of k-bytes
		 */
		private static Predicate<File> smallerThanFilter(double size) {
			return f -> f.length() < size * KILOBYTE;
		}

		/**
		 * File size is between (inclusive) the given numbers (in k-bytes)
		 */
		private static Predicate<File> betweenFilter(double min, double max) {
			return f -> f.length() >= min * KILOBYTE && f.length() <= max * KILOBYTE;
		}

		/**
		 * fileName equals the file name (excluding path)
		 */
		private static Predicate<File> fileFilter(String fileName) {
			return f -> f.getName().equals(fileName);
		}

		/**
		 * val is contained in the file name (excluding path)
		 */
		private static Predicate<File> containsFilter(String val) {
			return f -> f.getName().contains(val);
		}

		/**
		 * prefix is the prefix of the file name (excluding path)
		 */
		private static Predicate<File> prefixFilter(String prefix) {
			return f -> f.getName().startsWith(prefix);
		}

		/**
		 * suffix is the suffix of the file name (excluding path)
		 */
		private static Predicate<File> suffixFilter(String suffix) {
			return f -> f.getName().endsWith(suffix);
		}

		/**
		 * Does file have writing permission? (for the current user)
		 */
		private static Predicate<File> writableFilter(boolean val) {
			return f -> f.canWrite() == val;
		}

		/**
		 * Does file have execution permission? (for the current user)
		 */
		private static Predicate<File> executableFilter(boolean val) {
			return f -> f.canExecute() == val;
		}

		/**
		 * Is the file a hidden file?
		 */
		private static Predicate<File> hiddenFilter(boolean val) {
			return f -> f.isHidden() == val;
		}

		/**
		 * all files are matched
		 */
		private static Predicate<File> allFilter() {
			return f -> true;
		}
	}
}











