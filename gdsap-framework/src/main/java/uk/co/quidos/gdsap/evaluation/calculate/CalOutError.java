package uk.co.quidos.gdsap.evaluation.calculate;

/**
 * 引擎错误信息返回
 * @author Administrator
 *
 */
public class CalOutError {
	
	private String ErrorCode;
	private String Message;
	private String Stack;
	
	public String getErrorCode() {
		return ErrorCode;
	}
	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getStack() {
		return Stack;
	}
	public void setStack(String stack) {
		Stack = stack;
	}
}