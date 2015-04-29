/**
 * 
 */
package uk.co.quidos.gdsap.content.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 *
 */
public enum CategoryType implements BaseEnum<Integer>
{
	MutiItem("Muti items",0),SingleItem("Single Item",1);

	private String desc;
	private Integer code;
	
	private CategoryType(String desc,Integer code)
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
