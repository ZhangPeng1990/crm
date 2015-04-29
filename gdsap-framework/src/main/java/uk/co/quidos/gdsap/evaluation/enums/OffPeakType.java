package uk.co.quidos.gdsap.evaluation.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 */
public enum OffPeakType implements BaseEnum<Integer>
{
	OffPeak_7("7-hour",2),OffPeak_10("10-hour",3),OffPeak_18("18-hour",4);
	
	private String desc;
	private Integer code;
	
	private OffPeakType(String desc,Integer code)
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
