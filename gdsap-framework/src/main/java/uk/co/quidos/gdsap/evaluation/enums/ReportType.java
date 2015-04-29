/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 *
 */
public enum ReportType implements BaseEnum<Integer>
{
	EPC("Energy Performance Certificate",2),
	FSEPC("Full SAP Energy Performance Certificate",3),
	IREPC("Interim RdSAP Energy Performance Certificate (to be superseded by SAP EPC)",4),
	OAR("Occupancy Assessment report", 5),
	;
	
	private String desc;
	private Integer code;
	
	private ReportType(String desc,Integer code)
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
