package filesprocessing.parsing;

/**
 * Bad order sub-section name
 */
public class BadOrderSectionNameException extends BadSubSectionNameException {
	private static final long serialVersionUID = 1L;
	/**
	 * Constructs a new exception with {@code null} as its detail message.
	 * The cause is not initialized, and may subsequently be initialized by a
	 * call to {@link #initCause}.
	 */
	public BadOrderSectionNameException() {
		super("Bad order sub-section name");
	}
}
