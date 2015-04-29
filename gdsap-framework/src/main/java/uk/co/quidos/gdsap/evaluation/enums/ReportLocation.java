package uk.co.quidos.gdsap.evaluation.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 */
public enum ReportLocation implements BaseEnum<String>
{
	EAW("EAW","England and Welsh"),SCT("SCT","Scotland"),NIR("NIR","Northern Ireland");
	
	private String code;
	private String desc;
	private ReportLocation(String code,String desc)
	{
		this.code = code;
		this.desc = desc;
	}
	@Override
	public String getCode()
	{
		return this.code;
	}

	@Override
	public String getDesc()
	{
		return this.desc;
	}

}
