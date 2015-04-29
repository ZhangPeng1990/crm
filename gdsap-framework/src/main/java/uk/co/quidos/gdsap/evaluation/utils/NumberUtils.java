package uk.co.quidos.gdsap.evaluation.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author peng.shi
 */
public class NumberUtils
{
	/**
	 * 四舍五入Double值
	 * @param d
	 * @return
	 */
	public static int doubleHalfUp(double d)
	{
		BigDecimal bigDecimal = new BigDecimal(d);
		return bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
	}
	
//	public static void main(String[] args)
//	{
//		System.out.println(doubleHalfUp(0.1));
//		System.out.println(doubleHalfUp(0.2));
//		System.out.println(doubleHalfUp(0.5));
//		System.out.println(doubleHalfUp(1.8));
//	}
	
	private static final DecimalFormat	formatter	= new DecimalFormat("0000000000.00");

	public static String pad(double n) {
		return formatter.format(n);
	}

	private static boolean matches(String n, String p) {
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(n);
		return matcher.matches();
	}

	public static boolean isNumber(String n) {
		return org.apache.commons.lang.math.NumberUtils.isNumber(n);
	}
	public static boolean isDigits(String n) {
		return matches(n, "^[0-9]+$");
	}
	public static boolean isInteger(String n) {
		return matches(n, "^-?[1-9]\\d*$");
	}

	public static boolean isPositiveInteger(String n) {
		return matches(n, "^[1-9]\\d*$");
	}

	public static int random(int start, int end) {
		return (int) Math.round(Math.random() * (end - start)) + start;
	}

	public static double round(double v, int scale) {
		return round(v, scale, BigDecimal.ROUND_HALF_UP);
	}

	public static double round(double v, int scale, int round_mode) {

		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		BigDecimal b = new BigDecimal(Double.toString(v));

		return b.setScale(scale, round_mode).doubleValue();
	}

	public static int changeMaxInt(float f1,float f2) {
		int i1 = (int)f1;
		int i2 = (int)f2;
		int i = Math.max(i1, i2);
		if (i<= 2000) {
			return 2000;
		}
		String iString = String.valueOf(i);
		int iLength = iString.length();
		String firstLetter = iString.substring(0,1);
		int tmp = Integer.parseInt(firstLetter) + 1;
		String tmpString = String.valueOf(tmp);
		for (int n =0 ;n < iLength -1;n++) {
			tmpString = tmpString + "0";
		}
		return Integer.parseInt(tmpString);
	}
	
	public static void main(String[] args) {
		System.out.println(changeMaxInt(10000, 12231));
	}
//	public static void main(String[] args) {
//		String k = "-1212333";
//		System.out.println(isNumber(k));
//		System.out.println(isDigits(k));
//		System.out.println(isPositiveInteger(k));
//		System.out.println(round(139.825, 2));
//		System.out.println(random(0, 99));
//	}
}
