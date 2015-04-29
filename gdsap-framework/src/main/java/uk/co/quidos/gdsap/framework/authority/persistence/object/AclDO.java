package uk.co.quidos.gdsap.framework.authority.persistence.object;


import uk.co.quidos.dal.object.AbstractDO;

public class AclDO extends AbstractDO
{
	private static final long serialVersionUID = 4280331366969622885L;
	private String id;
	private String title;
	private String aclGroup;
	private String des;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getAclGroup()
	{
		return aclGroup;
	}

	public void setAclGroup(String aclGroup)
	{
		this.aclGroup = aclGroup;
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