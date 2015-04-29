package uk.co.quidos.gdsap.evaluation.utils;


public class ClgLodgeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8965330078490117501L;
	public ClgLodgeException() {
	}

	public ClgLodgeException(String message) {
		super(message);
	}

	public ClgLodgeException(Throwable cause) {
		super(cause);
	}

	public ClgLodgeException(String message, Throwable cause) {
		super(message, cause);
	}
//	public ClgLodgeException(MessageKey messageKey) {
//		this.setMessageKey(messageKey);
//	}
}
