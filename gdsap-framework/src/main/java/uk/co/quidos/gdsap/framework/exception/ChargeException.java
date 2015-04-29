package uk.co.quidos.gdsap.framework.exception;

import uk.co.quidos.gdsap.framework.sys.exception.ServiceException;

public class ChargeException extends ServiceException {

	private static final long serialVersionUID = -3573082640341574115L;

	public ChargeException() {
		super();
	}

	public ChargeException(String message) {
		super(message);
	}

	public ChargeException(Throwable cause) {
		super(cause);
	}

	public ChargeException(String message, Throwable cause) {
		super(message, cause);
	}

}
