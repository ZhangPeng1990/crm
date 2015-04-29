package uk.co.quidos.gdsap.framework.exception;

import uk.co.quidos.gdsap.framework.sys.exception.ServiceException;

public class CalculateException extends ServiceException {

	private static final long serialVersionUID = -3573082640341574115L;

	public CalculateException() {
		super();
	}

	public CalculateException(String message) {
		super(message);
	}

	public CalculateException(Throwable cause) {
		super(cause);
	}

	public CalculateException(String message, Throwable cause) {
		super(message, cause);
	}

}
