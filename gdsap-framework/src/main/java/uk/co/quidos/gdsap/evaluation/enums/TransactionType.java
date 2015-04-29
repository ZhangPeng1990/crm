/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * TransactionType 
 * @author peng.shi
 */
public enum TransactionType implements BaseEnum<Integer>
{
	Marketed_Sale("Marketed Sale",1),
	NonMarketed_Sale("Non-Marketed Sale",2),
	Rental_Social("Rental (Social)",3),
	Rental_Private("Rental (Private)",4),
	Not_Sale_or_Rental("Not Sale or Rental",5),
	New_dwelling("New dwelling",6),
	Not_recorded("not recorded - this is for backwards compatibility only and should not be used",7),
	
	// new add 
	Rental("Rental",8),
	Assessment_for_Green_Deal("Assessment for Green Deal",9),
	Following_Green_Deal("Following Green Deal",10),
	FiT_application("FiT application",11);
	
	
	private String desc;
	private Integer code;
	
	private TransactionType(String desc,Integer code)
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
