/**
 * 
 */
package uk.co.quidos.gdsap.framework.authority.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 */
public enum PagetagGroup implements BaseEnum<String>
{
	
	User_Management("User Management"),
	
	Admin_Management("Admin Management"),
	
	Company_Management("Company Management"),
	/**
	 * 系统配置
	 */
	SystemConfig("SystemConfig"),
	
	/**
	 * 权限菜单
	 */
	Authority("Authority"),

	/**
	 * 报告管理
	 */
	Report_Management("Report Management");
	
	private String desc;
	private PagetagGroup(String desc)
	{
		this.desc = desc;
	}
	
	/* (non-Javadoc)
	 * @see uk.co.argylesoftware.sys.lang.enums.BaseEnum#getCode()
	 */
	@Override
	public String getCode()
	{
		return this.toString();
	}

	/* (non-Javadoc)
	 * @see uk.co.argylesoftware.sys.lang.enums.BaseEnum#getDesc()
	 */
	@Override
	public String getDesc()
	{
		return this.desc;
	}
}
