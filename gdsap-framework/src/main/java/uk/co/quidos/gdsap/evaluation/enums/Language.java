/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 */
public enum Language implements BaseEnum<String>
{
	EN("English","1","en"),CY("Welsh","2","cy");
	
	private String desc;
	private String code;
	private String countryCode;
	
	private Language(String desc,String code,String countryCode)
	{
		this.desc = desc;
		this.code = code;
		this.countryCode = countryCode;
	}
	
	/**
	 * @return the countryCode
	 */
	public String getCountryCode()
	{
		return countryCode;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum#getCode()
	 */
	@Override
	public String getCode()
	{
		return this.code;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum#getDesc()
	 */
	@Override
	public String getDesc()
	{
		return this.desc;
	}

}
