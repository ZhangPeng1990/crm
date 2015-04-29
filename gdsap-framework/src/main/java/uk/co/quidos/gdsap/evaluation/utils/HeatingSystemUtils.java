package uk.co.quidos.gdsap.evaluation.utils;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author peng.shi
 */
public class HeatingSystemUtils
{
	private static final int GAS = 2;
	
	private static final int OIL = 3;
	
	private static final int SOLID = 4;
	
	private static final int ELECTRIC = 5;
	
	private static final int[] GAS_HTS = new int[]{601,603,605,607,609,610,611,612,613};
	
	private static final int[] OIL_HTS = new int[]{621,623,625};
	
	private static final int[] SOLID_HTS = new int[]{631,633,635};
	
	private static final int[] ELECTRIC_HTS = new int[]{691,694,693};
	
	/**
	 * 获取Heating System 对应的 xml code
	 * @param code
	 * @return
	 */
	public static int getXmlCode(int code)
	{
		if (ArrayUtils.contains(GAS_HTS, code))
		{
			return GAS;
		}
		if (ArrayUtils.contains(OIL_HTS, code))
		{
			return OIL;
		}
		if (ArrayUtils.contains(SOLID_HTS, code))
		{
			return SOLID;
		}
		if (ArrayUtils.contains(ELECTRIC_HTS, code))
		{
			return ELECTRIC;
		}
		throw new IllegalArgumentException();
	}
}
