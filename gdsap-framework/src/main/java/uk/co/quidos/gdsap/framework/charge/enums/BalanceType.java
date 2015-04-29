package uk.co.quidos.gdsap.framework.charge.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * 用户余额的类型
 * @author ZP
 *
 */
public enum BalanceType implements BaseEnum<Integer>
{

	GDIP_Credit("GDIP credit", 0);
	
	private String desc;
	private Integer code;
	
	private BalanceType(String desc,Integer code)
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
