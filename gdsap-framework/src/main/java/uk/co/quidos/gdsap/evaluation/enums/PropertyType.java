/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * PropertyType Enum列表值
 * @author peng.shi
 */
public enum PropertyType implements BaseEnum<Integer>
{
	House("House",0),Bungalow("Bungalow",1),Flat("Flat",2),Maisonette("Maisonette",3),
	ParkHome("ParkHome",4);
	
	private String desc;
	private Integer code;
	
	private PropertyType(String desc,Integer code)
	{
		this.desc = desc;
		this.code = code;
	}
	
	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum#getCode()
	 */
	@Override
	public Integer getCode()
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
