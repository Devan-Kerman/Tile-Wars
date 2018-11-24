package exceptions;

public class NotEnoughResourcesException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -24889688620699569L;

	public NotEnoughResourcesException(String s) {
		super(s);
	}
}
