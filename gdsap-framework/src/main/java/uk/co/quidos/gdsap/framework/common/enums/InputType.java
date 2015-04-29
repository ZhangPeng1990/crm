/**
 * 
 */
package uk.co.quidos.gdsap.framework.common.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 */
public enum InputType implements BaseEnum<Integer>
{
	Integer("Integer",0),Float("Float",1),String("Varchar",2);
	
	private String desc;
	private Integer code;
	
	private InputType(String desc,Integer code)
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
