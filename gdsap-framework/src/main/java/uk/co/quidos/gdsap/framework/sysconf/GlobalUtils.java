/**
 * 
 */
package uk.co.quidos.gdsap.framework.sysconf;

import java.util.Date;

import org.springframework.util.Assert;

import uk.co.quidos.common.util.DateUtil;


/**
 * @author peng.shi
 *
 */
public class GlobalUtils
{
	/**
	 * 以 "/" 为开头和结尾
	 * @param date
	 * @return
	 */
	public static String dateDir(Date date)
	{
		Assert.notNull(date);
		return DateUtil.date2String(date, "/yyyy/MM/dd/");
	}
}
