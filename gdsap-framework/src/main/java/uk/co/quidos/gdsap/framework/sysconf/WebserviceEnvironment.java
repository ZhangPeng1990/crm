/**
 * 
 */
package uk.co.quidos.gdsap.framework.sysconf;

/**
 * @author shipeng
 *
 */
public enum WebserviceEnvironment {
	
	Uat,Ote,Live;
	
	public static WebserviceEnvironment getWebserviceEnvironment(String str) {
		WebserviceEnvironment[] envs = WebserviceEnvironment.values();
		for (WebserviceEnvironment env : envs) {
			if (env.toString().equalsIgnoreCase(str)) {
				return env;
			}
		}
		throw new IllegalArgumentException();
	}
	
	public String getValue() {
		return this.toString().toLowerCase();
	}
}
