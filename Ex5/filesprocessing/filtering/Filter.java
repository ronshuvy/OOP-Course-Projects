package filesprocessing.filtering;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * A class represents a filter.
 */
public class Filter {

	private final Predicate<File> predicate;

	/**
	 * A filter constructor.
	 * @param predicate Filter type
	 */
	public Filter(Predicate<File> predicate){
		this.predicate = predicate;
	}

	/**
	 * Applies the filter on the given file
	 * @param file A given file
	 * @return {@code true} if the file passed the filter, {@code false} otherwise.
	 */
	public boolean isPass(File file){
		return predicate.test(file);
	}

	/**
	 * @param files A list of files
	 * @return A list of matched files, which passed the filter (given in the constructor).
	 */
	public List<File> applyList(List<File> files){
		List<File> filtered = new ArrayList<>();
		for (File f : files)
			if (isPass(f))
				filtered.add(f);
		return filtered;
	}

}


