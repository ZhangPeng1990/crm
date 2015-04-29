/**
 * 
 */
package uk.co.quidos.common.util.exception;

/**
 * @author peng.shi
 *
 */
public class ASException extends Exception
{
	private static final long serialVersionUID = 6063007064094713117L;
	
	public ASException() {
		super();
	}
	public ASException(String message) {
		super(message);
	}

	public ASException(Throwable cause) {
		super(cause);
	}

	public ASException(String message, Throwable cause) {
		super(message, cause);
	}
}
