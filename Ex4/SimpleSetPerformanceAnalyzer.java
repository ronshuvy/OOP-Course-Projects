
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

/**
 * Performance Analysis - A class that measures the run-times of a different sets.
 * @author ronshuvy
 */
public class SimpleSetPerformanceAnalyzer {

	// -----------------Class constants------------------
	private static final String INPUT_FILE_PATH1 = "data1.txt";
	private static final String INPUT_FILE_PATH2 = "data2.txt";
	private static final String OUTPUT_FILE_PATH = "RESULTS";
	private static final String CONTAINS_WORD = "hi";
	private static final String CONTAINS_NEG_NUM = "-13170890158";
	private static final String CONTAINS_POS_NUM = "23";
	private static final String ANALYSIS_1 = "AddData1";
	private static final String ANALYSIS_2 = "Contains_hi1";
	private static final String ANALYSIS_3 = "Contains_negative";
	private static final String ANALYSIS_4 = "AddData2";
	private static final String ANALYSIS_5 = "Contains_23";
	private static final String ANALYSIS_6 = "Contains_hi2";
	private static final int TOTAL_ITERATIONS = 70000;
	private static final int TOTAL_ITERATIONS_LINKED_LIST = 7000;
	private static final int FROM_NANO_TO_MILLI_SECONDS = 1000000;
	// --------------------------------------------------

	/**
	 * Main method
	 */
	public static void main(String[] args) throws IOException {
		SimpleSet[] sets = createSets();
		String results = "";

		// Measure adding all words from DATA1
		boolean [] toTest1 = {true, true, true, true, true};
		results += measureAddition(sets, toTest1, INPUT_FILE_PATH1, ANALYSIS_1);

		// Measure 'contains(CONTAINS_WORD)' operations with DATA1
		boolean [] toTest2 = {true, true, true, true, true};
		results += measureContains(sets, toTest2, CONTAINS_WORD, ANALYSIS_2);

		// Measure 'contains(CONTAINS_NEG_NUM)' operations with DATA1
		boolean [] toTest3 = {true, true, true, true, true};
		results += measureContains(sets, toTest3, CONTAINS_NEG_NUM, ANALYSIS_3);

		sets = createSets();

		// Measure adding all words from data2
		boolean [] toTest4 = {true, true, true, true, true};
		results += measureAddition(sets, toTest4, INPUT_FILE_PATH2, ANALYSIS_4);

		// Measure 'contains(CONTAINS_POS_NUM)' operations with DATA2
		boolean [] toTest5 = {true, true, true, true, true};
		results += measureContains(sets, toTest5, CONTAINS_POS_NUM, ANALYSIS_5);

		// Measure 'contains(CONTAINS_WORD)' operations with DATA2
		boolean [] toTest6 = {true, true, true, true, true};
		results += measureContains(sets, toTest6, CONTAINS_WORD, ANALYSIS_6);

		// Write results
		writeResults(results);
	}

	/*
	 * @return An array of the following sets :
	 * 0 -> OpenHashSet
	 * 1 -> ClosedHashSet
	 * 2 -> Java's TreeSet
	 * 3 -> Java's HashSet
	 * 4 -> Java's LinkedList
	 */
	private static SimpleSet[] createSets(){
		SimpleSet[] sets = new SimpleSet[5];
		sets[0] = new OpenHashSet();
		sets[1] = new ClosedHashSet();
		sets[2] = new CollectionFacadeSet(new TreeSet<String>());
		sets[3] = new CollectionFacadeSet(new HashSet<String>());
		sets[4] = new CollectionFacadeSet(new LinkedList<String>());
		return sets;
	}

	/*
	 * Measures addition operation on a selected sets
	 * @param sets A list of data structures that implements SimpleSet interface
	 * @param toTest Choose which data structure to measure
	 * @param path Filename
	 * @param analysisType Analysis type description
	 * @return Results in a string format
	 */
	private static String measureAddition(SimpleSet[] sets, boolean[] toTest, String path,
										  String analysisType){
		String[] words = Ex4Utils.file2array(path);
		long[] results = new long[sets.length];
		long timeBefore;

		for (int i = 0; i < sets.length; i++){
			if (toTest[i]){
				timeBefore = System.nanoTime();
				for (String word : words)
					sets[i].add(word);
				results[i] = (System.nanoTime() - timeBefore) / FROM_NANO_TO_MILLI_SECONDS;
			}
		}
		return resultsToString(results, toTest, analysisType);
	}

	/*
	 * Measures contains operation on a selected sets
	 * @param sets A list of data structures that implements SimpleSet interface
	 * @param toTest Choose which data structure to measure
	 * @param word Filename
	 * @param analysisType Analysis type description
	 * @return Results in a string format
	 */
	private static String measureContains(SimpleSet[] sets, boolean[] toTest, String word,
										  String analysisType){
		long[] results = new long[sets.length];
		long timeBefore;
		int lastIndex = sets.length -1;

		for (int i = 0; i < lastIndex; i++) {
			if (toTest[i]){
				// warm-up
				warmUp(sets[i], word);
				//measure
				timeBefore = System.nanoTime();
				for (int j = 0; j < TOTAL_ITERATIONS; j++)
					sets[i].contains(word);
				// add result
				results[i] = (System.nanoTime() - timeBefore) / TOTAL_ITERATIONS;
			}
		}

		// measure Linked-List data structure
		if (toTest[lastIndex]){
			timeBefore = System.nanoTime();
			for (int i = 0; i < TOTAL_ITERATIONS_LINKED_LIST; i++)
				sets[lastIndex].contains(word);
			// add result
			results[lastIndex] = (System.nanoTime() - timeBefore) / TOTAL_ITERATIONS_LINKED_LIST;
		}

		return resultsToString(results,toTest, analysisType);
	}

	/*
	 * Perform warm-up iterations on a given set and method
	 */
	private static void warmUp(SimpleSet set, String word){
		for (int i = 0; i < TOTAL_ITERATIONS; i++)
			set.contains(word);
	}

	/*
	 * Write the given results into a string format
	 * @param toWriteResult A list of booleans that indicates which sets contains actual measure results
	 * @param results List of measure results
	 * @param analysisType Analysis type description
	 * @return Results in a string format
	 */
	private static String resultsToString(long[] results, boolean[] toWriteResult, String analysisType){
		String res = "\n";
		if (toWriteResult[0])
			res += "OpenHashSet_" + analysisType + " = " + results[0] + "\n";
		if (toWriteResult[1])
			res += "ClosedHashSet_" + analysisType + " = " + results[1] + "\n";
		if (toWriteResult[2])
			res += "TreeSet_" + analysisType + " = " + results[2] + "\n";
		if (toWriteResult[4])
			res += "LinkedList_" + analysisType + " = " + results[4] + "\n";
		if (toWriteResult[3])
			res += "HashSet_" + analysisType + " = " + results[3] + "\n";
		return res;
	}

	/*
	 * Writes results to a file
	 * @param results String of all results
	 * @throws IOException If file's open/read operation failed
	 */
	private static void writeResults(String results) throws IOException {
		Path file = Paths.get(OUTPUT_FILE_PATH);
		byte[] buffer = results.getBytes();
		Files.write(file, buffer);
	}

}
