package uk.co.quidos.gdsap.framework.charge.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

public enum TransactionType implements BaseEnum<Integer>
{
	Income("Income", "Pay in", 0), Charge_Off("Charge Off", "Pay out", 1);
	
	private String desc;
	private String webDesc;
	private Integer code;
	
	private TransactionType(String desc,String webDesc, Integer code)
	{
		this.desc = desc;
		this.webDesc = webDesc;
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

	public String getWebDesc() {
		return webDesc;
	}
}
