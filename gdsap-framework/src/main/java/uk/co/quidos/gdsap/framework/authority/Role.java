/**
 * 
 */
package uk.co.quidos.gdsap.framework.authority;

import java.util.Set;

import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 */
public class Role extends AbsBusinessObject
{
	private static final long serialVersionUID = 2508812659991752672L;
	private String id;
	private String roleName;
	private String des;
	private Set<PageTag> pageTags;
	private Set<ACL> acls;
	
	public Set<ACL> getAcls()
	{
		return acls;
	}

	public void setAcls(Set<ACL> acls)
	{
		this.acls = acls;
	}

	public String getRoleName()
	{
		return roleName;
	}

	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}

	public Set<PageTag> getPageTags()
	{
		return pageTags;
	}

	public void setPageTags(Set<PageTag> pageTags)
	{
		this.pageTags = pageTags;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
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
