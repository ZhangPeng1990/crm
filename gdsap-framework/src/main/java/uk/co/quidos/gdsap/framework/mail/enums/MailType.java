package uk.co.quidos.gdsap.framework.mail.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author shipeng
 *
 */
public enum MailType implements BaseEnum<Integer>
{
	
	Simple("Simple",0),Html("Html",1),Multi("Multi",2);
	
	private String desc;
	private Integer code;
	
	private MailType(String desc,Integer code)
	{
		this.desc = desc;
		this.code = code;
	}
	
	public String getValue() {
		return this.toString();
	}

	/* (non-Javadoc)
	 * @see cn.com.ecointel.roomyi.framework.sys.lang.enums.BaseEnum#getCode()
	 */
	@Override
	public Integer getCode()
	{
		return this.code;
	}

	/* (non-Javadoc)
	 * @see cn.com.ecointel.roomyi.framework.sys.lang.enums.BaseEnum#getDesc()
	 */
	@Override
	public String getDesc()
	{
		return this.desc;
	}
	
}
