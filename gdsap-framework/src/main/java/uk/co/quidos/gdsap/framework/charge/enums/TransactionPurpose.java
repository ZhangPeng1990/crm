package uk.co.quidos.gdsap.framework.charge.enums;

import java.text.MessageFormat;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

public enum TransactionPurpose implements BaseEnum<Integer>
{
	Add_By_Admin("Manual add by admin", 0, TransactionType.Income, "Administrators {0} manually add {1} points"), 
	Purchase_From_website("Purchase from Quidos website", 1, TransactionType.Income, "assessor {0} purchase {1} points from Quidos website"),
	GDIP_Charge("GDIP lodgement charge", 2, TransactionType.Charge_Off, "assessor {0} spent {1} points,used for lodge GDIP the gdip rrn is {2}");
	
	private String desc;
	private Integer code;
	private TransactionType type;
	private String desc_detailed;
	
	private TransactionPurpose(String desc,Integer code, TransactionType type, String desc_detailed)
	{
		this.desc = desc;
		this.code = code;
		this.type = type;
		this.desc_detailed = desc_detailed;
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

	public TransactionType getType() {
		return type;
	}

	public String getDesc_detailed(String[] values) {
		String result = MessageFormat.format(desc_detailed , values);
		return result;
	}
}
