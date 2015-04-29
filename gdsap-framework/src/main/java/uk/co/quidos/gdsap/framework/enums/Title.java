/**
 * 
 */
package uk.co.quidos.gdsap.framework.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 *
 */
public enum Title implements BaseEnum<Integer>
{
	Dr("Dr",0),Miss("Miss",1),Mrs("Mrs",2),Mr("Mr",3),Prof("Prof",4);
	
	private String desc;
	private int code;
	
	private Title(String desc,int code)
	{
		this.desc = desc;
		this.code = code;
	}
	
	@Override
	public Integer getCode()
	{
		return this.code;
	}

	@Override
	public String getDesc()
	{
		return this.desc;
	}
	
}
