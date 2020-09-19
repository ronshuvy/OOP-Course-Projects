package filesprocessing.parsing;

/**
 * Bad filter sub-section name
 */
public class BadFilterSectionNameException extends BadSubSectionNameException {
	private static final long serialVersionUID = 1L;
	/**
	 * Constructs a new exception with {@code null} as its detail message.
	 * The cause is not initialized, and may subsequently be initialized by a
	 * call to {@link #initCause}.
	 */
	public BadFilterSectionNameException() {
		super("Bad filter sub-section name");
	}
}
