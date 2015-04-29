package uk.co.quidos.gdsap.framework.authority.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class RoleDO extends AbstractDO
{
	private static final long serialVersionUID = 2957633327634259540L;
	private String id;
	private String rolename;
	private String des;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getRolename()
	{
		return rolename;
	}

	public void setRolename(String rolename)
	{
		this.rolename = rolename;
	}

	public String getDes()
	{
		return des;
	}

	public void setDes(String des)
	{
		this.des = des;
	}
}