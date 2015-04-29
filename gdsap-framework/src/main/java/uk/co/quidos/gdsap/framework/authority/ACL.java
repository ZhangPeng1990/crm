/**
 * 
 */
package uk.co.quidos.gdsap.framework.authority;

import uk.co.quidos.gdsap.framework.authority.enums.ACLGroup;
import uk.co.quidos.gdsap.framework.sys.business.AbstractBusinessObject;

/**
 * @author peng.shi
 */
public class ACL extends AbstractBusinessObject
{

	private static final long serialVersionUID = 9096478016362859475L;
	private String id;
	private String title;
	private ACLGroup aclGroup;
	private String des;
	@Override
	public String getId()
	{
		return this.id;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public ACLGroup getAclGroup()
	{
		return aclGroup;
	}
	public void setAclGroup(ACLGroup aclGroup)
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
	public void setId(String id)
	{
		this.id = id;
	}
	
}
