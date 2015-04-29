package uk.co.quidos.adminconsole.web.authority;

import uk.co.quidos.gdsap.framework.authority.Role;


public class RoleForm {

	private Role role;
	
	private String pageTags;
	
	private String acls;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPageTags() {
		return pageTags;
	}

	public void setPageTags(String pageTags) {
		this.pageTags = pageTags;
	}

	public String getAcls() {
		return acls;
	}

	public void setAcls(String acls) {
		this.acls = acls;
	}
}
