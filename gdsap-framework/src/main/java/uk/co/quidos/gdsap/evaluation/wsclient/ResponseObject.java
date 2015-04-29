/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.wsclient;

/**
 * @author shipeng
 */
public class ResponseObject {
	
	private ResponseStatus responseStatus;
	private ResponseMessage responseMessage;
	
	private String successContent;
	
	public String getSuccessContent() {
		return successContent;
	}

	public void setSuccessContent(String successContent) {
		this.successContent = successContent;
	}

	public ResponseObject(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}
	
	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	/**
	 * 返回ResponseMessage
	 * @return
	 */
	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	/**
	 * 添加ResponseMessage
	 * @param responseMessage
	 */
	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}
}
