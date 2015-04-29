/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 */
public enum RoomType implements BaseEnum<Integer>
{
	LiveRoom("Live Room",0),OtherRoom("Other Room",1);
	
	private String desc;
	private Integer code;
	
	private RoomType(String desc,Integer code)
	{
		this.desc = desc;
		this.code = code;
	}
	
	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum#getCode()
	 */
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
