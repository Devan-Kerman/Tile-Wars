package play.ai.devtech.core.errors;

public class ClaimException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClaimException() {
		super();
	}

	public ClaimException(String message) {
		super(message);
	}

	public ClaimException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClaimException(Throwable cause) {
		super(cause);
	}

	protected ClaimException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
