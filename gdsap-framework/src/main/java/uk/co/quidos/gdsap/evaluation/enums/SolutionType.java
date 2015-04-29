package uk.co.quidos.gdsap.evaluation.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

public enum SolutionType implements BaseEnum<Integer>
{
	GDAR("GDAR Solution",0),GDIP("GDIP Solution",1);
	
	private String desc;
	private Integer code;
	
	private SolutionType(String desc,Integer code)
	{
		this.desc = desc;
		this.code = code;
	}
	
	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum#getCode()
	 */
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