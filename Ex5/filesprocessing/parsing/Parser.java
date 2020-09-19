package filesprocessing.parsing;

import filesprocessing.*;
import filesprocessing.filtering.*;
import filesprocessing.ordering.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for parsing commands file.
 */
public class Parser {

	/* -------------------- CLASS CONSTANTS -------------------- */
	private static final int MIN_SECTION_SIZE = 3;
	private static final int MAX_SECTION_SIZE = 4;
	private static final int FILTER_PARAMS_INDEX = 2;
	private static final int ORDER_PARAMS_INDEX = 4;
	private static final String FILTER_TITLE = "FILTER";
	private static final String ORDER_TITLE = "ORDER";
	private static final String PARAM_DELIMITER = "#";
	/* -------------------- CLASS CONSTANTS -------------------- */

	/**
	 * Parse a given file and creates a list of Sections.
	 * @param path File path
	 * @return A list of Sections
	 */
	public static List<Section> parseFile(String path) throws IOException, BadSubSectionNameException,
														  BadCommandFileFormatException {
		List<Section> sections = new ArrayList<>();

		try (BufferedReader buffer = new BufferedReader(new FileReader(path))){
			int lineIndex = 0; // current line index
			boolean moreSectionToRead = true;

			// fourth line can either be empty (end of file), filter title or order type
			String fourthLine = null;

			while (moreSectionToRead){

				String filterTitle = (fourthLine == null) ? buffer.readLine() : fourthLine;
				if (filterTitle == null)
					break;
				if (!filterTitle.equals(FILTER_TITLE))
					throw new BadFilterSectionNameException();

				String filterParams = buffer.readLine();
				String orderTitle = buffer.readLine();
				if (filterParams == null || orderTitle == null)
					throw new BadCommandFileFormatException();

				if (!orderTitle.equals(ORDER_TITLE))
					throw new BadOrderSectionNameException();

				fourthLine = buffer.readLine();
				if (fourthLine == null){
					sections.add(parseSection(lineIndex, filterParams, null));
					moreSectionToRead = false;
				} else if (fourthLine.equals(FILTER_TITLE)){
					sections.add(parseSection(lineIndex, filterParams, null));
					lineIndex += MIN_SECTION_SIZE;
				} else{
					sections.add(parseSection(lineIndex, filterParams, fourthLine));
					fourthLine = null;
					lineIndex += MAX_SECTION_SIZE;
				}
			}
		}
		return sections;
	}


	/*
	 * Parses a complete section (filter & order commands) and creates a Section object.
	 * @param section Section's lines
	 * @param lineIndex Current line index
	 * @return new Section object
	 * @throws BadSubSectionNameException
	 */
	private static Section parseSection(int lineIndex, String filterParams, String orderParams){

		Filter filter;
		Order order;
		List<Integer> warningLines = new ArrayList<>();

		String[] filterData = filterParams.split(PARAM_DELIMITER);
		try {
			filter = FilterFactory.createFilter(filterData);
		}
		catch (Type1Exception e){
			filter = FilterFactory.createDefaultFilter();
			warningLines.add(lineIndex + FILTER_PARAMS_INDEX);
		}

		if (orderParams == null)
			order = OrderFactory.createDefaultOrder();
		else{
			String[] orderData = orderParams.split(PARAM_DELIMITER);
			try {
				order = OrderFactory.createOrder(orderData);
			}
			catch (BadOrderNameException e){
				order = OrderFactory.createDefaultOrder();
				warningLines.add(lineIndex + ORDER_PARAMS_INDEX);
			}
		}
		return new Section(filter, order, warningLines);
	}

}
