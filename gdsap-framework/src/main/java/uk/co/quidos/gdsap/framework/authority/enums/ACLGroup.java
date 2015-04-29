/**
 * 
 */
package uk.co.quidos.gdsap.framework.authority.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;


/**
 * @author peng.shi
 */
public enum ACLGroup implements BaseEnum<String>
{
	Content("Content ACLs"), Auditor("Auditor ACLs"), Other("Other ACLs");

	private ACLGroup(String desc)
	{
		this.desc = desc;
	}

	private String desc;

	@Override
	public String getCode()
	{
		return this.toString();
	}

	@Override
	public String getDesc()
	{
		return this.desc;
	}

}
