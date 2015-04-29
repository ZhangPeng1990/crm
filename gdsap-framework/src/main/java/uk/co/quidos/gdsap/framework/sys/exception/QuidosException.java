/**
 * 
 */
package uk.co.quidos.gdsap.framework.sys.exception;

/**
 * @author peng.shi
 *
 */
public class QuidosException extends Exception
{
	private static final long serialVersionUID = 6063007064094713117L;
	
	public QuidosException() {
		super();
	}
	public QuidosException(String message) {
		super(message);
	}

	public QuidosException(Throwable cause) {
		super(cause);
	}

	public QuidosException(String message, Throwable cause) {
		super(message, cause);
	}
}
