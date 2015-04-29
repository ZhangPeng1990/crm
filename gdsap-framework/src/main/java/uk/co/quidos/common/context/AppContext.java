package uk.co.quidos.common.context;

import uk.co.quidos.gdsap.framework.authority.Admin;



public interface AppContext {

	public Admin getUser();

	public void setUser(Admin user);

}
