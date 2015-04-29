/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.utils;

import org.apache.commons.lang.StringUtils;


/**
 * @author peng.shi
 *
 */
public class UPRNPatcher
{
	public static String getFullUPRN(String uprn)
	{
//		if (StringUtils.isEmpty(uprn) || StringUtils.isBlank(uprn))
//		{
//			return "000000000000";
//		}
//		if (uprn.length() == 12)
//		{
//			return uprn;
//		}
//		if (uprn.length() >12)
//		{
//			return uprn.substring(0,12);
//		}
//		else
//		{
//			int length = uprn.length();
//			StringBuffer sb = new StringBuffer();
//			for (int i=0;i<12-length;i++)
//			{
//				sb.append("0");
//			}
//			return uprn + sb.toString();
//		}
		
		return uprn;
	}
	
	public static void main(String[] args)
	{
		String uprn = "01234567891234";
		System.out.println(getFullUPRN(uprn));
	}
}
