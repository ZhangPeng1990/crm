package uk.co.quidos.gdsap.framework.authority.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class RolePageTagRelDOKey extends AbstractDO
{
	private static final long serialVersionUID = 1139274246954904176L;
	private String roleId;
	private String pagetagId;

	public String getRoleId()
	{
		return roleId;
	}

	public void setRoleId(String roleId)
	{
		this.roleId = roleId;
	}

	public String getPagetagId()
	{
		return pagetagId;
	}

	public void setPagetagId(String pagetagId)
	{
		this.pagetagId = pagetagId;
	}
}