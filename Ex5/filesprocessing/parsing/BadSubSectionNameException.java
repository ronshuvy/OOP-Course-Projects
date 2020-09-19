package filesprocessing.parsing;

import filesprocessing.Type2Exception;

/**
 * Bad sub-section exception
 */
public class BadSubSectionNameException extends Type2Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with {@code null} as its detail message.
	 * The cause is not initialized, and may subsequently be initialized by a
	 * call to {@link #initCause}.
	 */
	public BadSubSectionNameException() {
		super("Bad sub-section name");
	}

	/**
	 * Constructs a new exception with the specified detail message.  The
	 * cause is not initialized, and may subsequently be initialized by
	 * a call to {@link #initCause}.
	 *
	 * @param message the detail message. The detail message is saved for
	 *                later retrieval by the {@link #getMessage()} method.
	 */
	public BadSubSectionNameException(String message) {
		super(message);
	}
}
