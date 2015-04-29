package uk.co.quidos.gdsap.framework.authority.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class RoleAclRelDOKey extends AbstractDO
{
	private static final long serialVersionUID = -8594306766878061138L;
	private String roleId;
	private String aclId;

	public String getRoleId()
	{
		return roleId;
	}

	public void setRoleId(String roleId)
	{
		this.roleId = roleId;
	}

	public String getAclId()
	{
		return aclId;
	}

	public void setAclId(String aclId)
	{
		this.aclId = aclId;
	}
}