package uk.co.quidos.gdsap.framework.authority.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class AdminRoleRelDOKey extends AbstractDO
{
	private static final long serialVersionUID = 6087822620959805832L;
	private Integer adminId;
	private String roleId;

	public Integer getAdminId()
	{
		return adminId;
	}

	public void setAdminId(Integer adminId)
	{
		this.adminId = adminId;
	}

	public String getRoleId()
	{
		return roleId;
	}

	public void setRoleId(String roleId)
	{
		this.roleId = roleId;
	}
}