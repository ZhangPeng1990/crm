/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 *
 */
public enum ImprovementScope implements BaseEnum<Integer>
{
	Suggested("Suggested",0),Alternative("Alternative",1);
	
	private ImprovementScope(String desc,Integer code)
	{
		this.desc = desc;
		this.code = code;
	}
	
	private String desc;
	private Integer code;
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
