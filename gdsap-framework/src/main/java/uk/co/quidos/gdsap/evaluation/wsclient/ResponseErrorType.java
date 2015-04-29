/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.wsclient;


/**
 * @author shipeng
 *
 */
public enum ResponseErrorType{
	
	SchemaValidationError,ValidationError;
	
	/**
	 * @param str
	 * @return
	 */
	public static ResponseErrorType getResponseErrorType(String str) {
		ResponseErrorType[] values = ResponseErrorType.values();
		for (ResponseErrorType type : values) {
			if (type.toString().equalsIgnoreCase(str)) {
				return type;
			}
		}
		throw new IllegalArgumentException();
	}
}
