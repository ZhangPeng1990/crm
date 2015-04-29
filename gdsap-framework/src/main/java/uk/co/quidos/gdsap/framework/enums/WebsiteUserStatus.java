package uk.co.quidos.gdsap.framework.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

public enum WebsiteUserStatus implements BaseEnum<Integer>
{
	Active("Active",1),InActive("InActive",0);
	
	private String desc;
	private Integer code;
	
	private WebsiteUserStatus(String desc,Integer code)
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
