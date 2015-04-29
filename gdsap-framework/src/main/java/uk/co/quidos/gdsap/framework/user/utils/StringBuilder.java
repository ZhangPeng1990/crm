/**
 * 
 */
package uk.co.quidos.gdsap.framework.user.utils;

import java.util.UUID;

import uk.co.quidos.common.util.random.RandomString;

/**
 * @author peng.shi
 *
 */
public class StringBuilder
{
	/**
	 * 获取build code
	 * @return
	 */
	public static String buildRegsiterCode()
	{
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 建立随即密码
	 * @param length
	 * @return
	 */
	public static String buildPassword(int length)
	{
		return RandomString.randomString(length);
	}
}
