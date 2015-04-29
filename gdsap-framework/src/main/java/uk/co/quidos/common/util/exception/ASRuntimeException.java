/**
 * 
 */
package uk.co.quidos.common.util.exception;

/**
 * @author peng.shi
 *
 */
public class ASRuntimeException extends RuntimeException
{
	private static final long serialVersionUID = -3979301942179432763L;

	public ASRuntimeException() {
		super();
	}
	
	public ASRuntimeException(String message) {
		super(message);
	}

	public ASRuntimeException(Throwable cause) {
		super(cause);
	}

	public ASRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
