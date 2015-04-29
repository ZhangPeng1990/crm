/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.wsclient;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author shipeng
 */
public enum ResponseStatus implements BaseEnum<String> {

	Success("success", "success"), 
	Fail_400("HTTP 400 error", "validate error."), 
	Fail_401("HTTP 401 error", "HTTP 401 Unauthorized."), 
	Fail_403("HTTP 403 error", "HTTP 403 Unauthorized."), 
	Fail_500("HTTP 500 error", "HTTP 500 Internal Server Error."),
	Fail_SCT("SCT Error" , "SCT Error");
	
	private String desc;
	private String code;

	private ResponseStatus(String desc, String code) {
		this.desc = desc;
		this.code = code;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getDesc() {
		return this.desc;
	}

}
