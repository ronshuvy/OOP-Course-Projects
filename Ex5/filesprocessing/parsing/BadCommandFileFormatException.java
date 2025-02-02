package filesprocessing.parsing;

import filesprocessing.Type2Exception;

/**
 * Bad command file format exception
 */
public class BadCommandFileFormatException extends Type2Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with {@code null} as its detail message.
	 * The cause is not initialized, and may subsequently be initialized by a
	 * call to {@link #initCause}.
	 */
	public BadCommandFileFormatException() {
		super("Bad commands file format");
	}
}
