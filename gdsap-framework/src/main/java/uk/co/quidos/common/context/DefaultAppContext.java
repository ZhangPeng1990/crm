package uk.co.quidos.common.context;

import uk.co.quidos.gdsap.framework.authority.Admin;



public class DefaultAppContext implements AppContext {

	private Admin user = null;

//	private Website website = null;

	public DefaultAppContext() {
	}

//	public DefaultAppContext(BaseUser user) {
//		this.user = user;
//	}

//	public DefaultAppContext(Website website) {
//		this.website = website;
//	}

	public Admin getUser() {
		return this.user;
	}

	public void setUser(Admin user) {
		this.user = user;
	}

//	public Website getWebsite() {
//		return this.website;
//	}
//
//	public void setWebsite(Website website) {
//		this.website = website;
//	}

}
