package uk.co.quidos.gdsap.framework.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * <strong>UserType</strong><br>
 * <strong>Create on : 2012-3-12<br>
 * </strong>
 * @author peng.shi peng.shi@ecointel.com.cn<br>
 * @version <strong>ecointel-roomyi v1.0.0</strong><br>
 */
public enum UserType implements BaseEnum<Integer>
{
	GDA("Green Deal Advisor(GDA)", 0),
	GDP("Green Deal Provider(GDP)", 1);// installer
	
	private String desc;
	private Integer code;
	
	private UserType(String desc, Integer code)
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
