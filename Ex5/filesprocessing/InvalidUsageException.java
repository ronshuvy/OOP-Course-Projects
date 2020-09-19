package filesprocessing;

/**
 * Invalid Usage Exception
 */
public class InvalidUsageException extends Type2Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with {@code null} as its detail message.
	 * The cause is not initialized, and may subsequently be initialized by a
	 * call to {@link #initCause}.
	 */
	public InvalidUsageException() {
		super("Usage - filesprocessing.DirectoryProcessor <sourcedir> <commandfile>");
	}
}
