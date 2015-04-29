/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.utils;

/**
 * @author peng.shi@argylesoftware.co.uk
 */
public class SCTWSResponse {
	
	private boolean responseStatus;
	private int responseCode;
	private String responseMessage;
	private String errorCode;
	private String errorMessage;
	private String comment;
	private String successContent;
	
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getSuccessContent() {
		return successContent;
	}
	public void setSuccessContent(String successContent) {
		this.successContent = successContent;
	}
	public boolean isResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(boolean responseStatus) {
		this.responseStatus = responseStatus;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
