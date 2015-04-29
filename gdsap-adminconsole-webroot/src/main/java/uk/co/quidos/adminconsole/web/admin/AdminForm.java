package uk.co.quidos.adminconsole.web.admin;

import uk.co.quidos.gdsap.framework.authority.Admin;



public class AdminForm {

	private Admin admin;
	
	private String roleIds;

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
}
