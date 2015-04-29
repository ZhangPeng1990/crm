/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.wsclient;

/**
 * @author shipeng
 * 
 */
public class ResponseMessage {
	
	private String message;
	private ResponseErrorType responseErrorType;
	private int lineNo;
	private int columnNo;
	
	public int getLineNo() {
		return lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	public int getColumnNo() {
		return columnNo;
	}

	public void setColumnNo(int columnNo) {
		this.columnNo = columnNo;
	}

	public ResponseErrorType getResponseErrorType() {
		return responseErrorType;
	}

	public void setResponseErrorType(ResponseErrorType responseErrorType) {
		this.responseErrorType = responseErrorType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
