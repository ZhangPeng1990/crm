/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 *
 */
public enum Country implements BaseEnum<String>
{
	EAW("E&W","EAW",57),SCT("Scotland","SCT",0),NIR("Northern Ireland","NIR",57);
	
	private String desc;
	private String code;
	private Integer averageSAPRating;
	
	private Country(String desc,String code,Integer averageSAPRating)
	{
		this.desc = desc;
		this.code = code;
		this.averageSAPRating = averageSAPRating;
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
	/**
	 * @return the averageSAPRating
	 */
	public Integer getAverageSAPRating()
	{
		return averageSAPRating;
	}
	
}
