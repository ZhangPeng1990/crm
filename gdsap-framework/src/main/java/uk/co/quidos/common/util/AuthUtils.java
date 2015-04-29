package uk.co.quidos.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import uk.co.quidos.gdsap.evaluation.utils.Base64;

public class AuthUtils {

	public static final String KEY_USER_NAME = "KEYUSERNAME";
	public static final String KEY_USER_PASSWORD = "KEYUSERPASSWORD";
	public static final String DEC = ":";
	
	private static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";  
	    Random random = new Random();  
	    StringBuffer sb = new StringBuffer();  
	    for (int i = 0; i < length; i++) {  
	        int number = random.nextInt(base.length());  
	        sb.append(base.charAt(number));  
	    }  
	    return sb.toString();  
	 }  
	
	public static String getAuthId(String userName, String password){
		String authId = null;
		if(userName != null && password != null && userName.length() > 0 && password.length() > 0){
			String auth = getRandomString(4) + userName + DEC + password + getRandomString(4);
			try {
				authId = Base64.dotNetStrToUnicodeBase64(Base64.dotNetStrToUnicodeBase64(Base64.dotNetStrToUnicodeBase64(auth)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return authId;
	}
	
	public static Map<String, String> getAuth(String authId){
		Map<String, String> authMap = new HashMap<String, String>();
		if(authId != null && authId.length() > 0){
			String auth = null;
			try {
				auth = Base64.dotNetUnicodeBase64ToStr(Base64.dotNetUnicodeBase64ToStr(Base64.dotNetUnicodeBase64ToStr(authId)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			auth = auth.substring(4, auth.length());
			auth = auth.substring(0, auth.length() - 4);
	        String[] t = auth.split(":");
	        if(t != null && t.length == 2){
	        	authMap.put(KEY_USER_NAME, t[0]);
	        	authMap.put(KEY_USER_PASSWORD, t[1]);
	        }
		}
		return authMap;
	}
}
