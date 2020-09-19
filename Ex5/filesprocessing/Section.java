package filesprocessing;

import filesprocessing.filtering.Filter;
import filesprocessing.ordering.Order;

import java.util.List;

/**
 * A class represents a section of commands that includes a filter, an order and line warnings.
 */
public class Section {

	private final Filter filter;
	private final Order order;
	private final List<Integer> lineWarnings;

	/**
	 * A constructor of Section object
	 * @param filter Filter type
	 * @param order Order type
	 * @param warningsLines A list of line warnings
	 */
	public Section(Filter filter, Order order, List<Integer> warningsLines){
		this.filter = filter;
		this.order = order;
		this.lineWarnings = warningsLines;
	}

	/**
	 * @return Section's filter type
	 */
	public Filter getFilter() {
		return filter;
	}

	/**
	 * @return Section's order type
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * @return A list of the section line warnings
	 */
	public List<Integer> getLineWarnings() {
		return lineWarnings;
	}

}
