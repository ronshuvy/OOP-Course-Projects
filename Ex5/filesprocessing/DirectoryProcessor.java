package filesprocessing;

import filesprocessing.parsing.Parser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This program filter the files in a given directory according to various conditions,
 * and order the filenames that passed the filtering according to various properties.
 * @author ronshuvy
 */
public class DirectoryProcessor {

	/* -------------------- CLASS CONSTANTS -------------------- */
	private static final int TOTAL_COMMAND_LINE_ARGS = 2;
	private static final String ERROR_PREFIX = "ERROR: ";
	private static final String WARNING_PREFIX = "Warning in line ";
	/* -------------------- CLASS CONSTANTS -------------------- */

	/**
	 * Driver method
	 */
	public static void main(String[] args){
		try {
			checkUsage(args);
			String sourceDir = args[0], commandFile = args[1];

			List<Section> sections = Parser.parseFile(commandFile);
			List<File> files = loadFilesFromDir(sourceDir);

			for (Section section : sections){
				printWarnings(section.getLineWarnings());

				List<File> filteredFiles = section.getFilter().applyList(files);
				section.getOrder().sort(filteredFiles);

				printMatchedFiles(filteredFiles);
			}
		}
		catch (Type2Exception | IOException e) {
			System.err.println(ERROR_PREFIX + e.getMessage());
		}
	}

	/*
	 * Check correct program usage.
	 * @param args Command line arguments
	 * @throws InvalidUsageException Value of
	 */
	private static void checkUsage(String[] args) throws InvalidUsageException {
		if (args.length != TOTAL_COMMAND_LINE_ARGS)
			throw new InvalidUsageException();
	}

	/*
	 * Load all files from a given directory.
	 * @param dirPath Directory path
	 * @return A list of files
	 */
	private static List<File> loadFilesFromDir(String dirPath){
		List<File> result = new ArrayList<File>();
		File directory = new File(dirPath);
		File[] filesInDirectory = directory.listFiles();

		for (File file : filesInDirectory) {
			if (!file.isDirectory()) {
				result.add(file);
			}
		}
		return result;
	}


	/*
	 * Prints line warnings
	 * @param warnings A list of warning lines
	 */
	private static void printWarnings(List<Integer> warnings){
		for (int line : warnings)
			System.err.println(WARNING_PREFIX + line);
	}

	/*
	 * Prints all the matched file names
	 * @param files A list of filtered files
	 */
	private static void printMatchedFiles(List<File> files){
		for (File file : files)
			System.out.println(file.getName());
	}

}
