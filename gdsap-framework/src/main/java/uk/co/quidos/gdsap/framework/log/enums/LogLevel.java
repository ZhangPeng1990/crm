/**
 * 
 */
package uk.co.quidos.gdsap.framework.log.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 */
public enum LogLevel implements BaseEnum<Integer>
{
	INFO("Information",0),ERROR("Error",1);
	
	private String desc;
	private int code;
	
	private LogLevel(String desc,int code)
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
