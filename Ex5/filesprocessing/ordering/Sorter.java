package filesprocessing.ordering;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A sorting class.
 */
public class Sorter {

	/**
	 * Sorts a given list.
	 */
	public static <T> void sort(List<T> list, Comparator<T> comparator){
		mergeSort(list, comparator);
	}

	/*
	 * Sorts a given list using MergeSort algorithm.
	 */
	private static <T> void mergeSort(List<T> listToSort, Comparator<T> comp){
		int size = listToSort.size();
		if (size < 2)
			return;

		int half = size / 2;
		List<T> leftList = new ArrayList<T>(listToSort.subList(0, half));
		List<T> rightList = new ArrayList<T>(listToSort.subList(half,size));

		mergeSort(leftList, comp);
		mergeSort(rightList, comp);

		merge(leftList,rightList,listToSort,comp);
	}

	/*
	 * Merges two sub-list into one list
	 */
	private static <T> void merge(List<T> leftList, List<T> rightList,List<T> mergedList, Comparator<T> comp){
		int i = 0, j = 0, k = 0;

		while(i < leftList.size() && j < rightList.size()) {
			if (comp.compare(leftList.get(i), rightList.get(j)) < 0)
				mergedList.set(k++, leftList.get(i++));
			else
				mergedList.set(k++, rightList.get(j++));
		}

		while (i < leftList.size())
			mergedList.set(k++, leftList.get(i++));

		while (j < rightList.size())
			mergedList.set(k++, rightList.get(j++));
	}

}
