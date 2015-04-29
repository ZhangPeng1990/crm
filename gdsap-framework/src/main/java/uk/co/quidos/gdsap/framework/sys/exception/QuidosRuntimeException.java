/**
 * 
 */
package uk.co.quidos.gdsap.framework.sys.exception;

/**
 * @author peng.shi
 *
 */
public class QuidosRuntimeException extends RuntimeException
{
	private static final long serialVersionUID = -3979301942179432763L;

	public QuidosRuntimeException() {
		super();
	}
	
	public QuidosRuntimeException(String message) {
		super(message);
	}

	public QuidosRuntimeException(Throwable cause) {
		super(cause);
	}

	public QuidosRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
