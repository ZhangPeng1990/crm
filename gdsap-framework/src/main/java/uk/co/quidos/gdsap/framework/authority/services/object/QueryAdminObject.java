package uk.co.quidos.gdsap.framework.authority.services.object;


import uk.co.quidos.dal.BaseConditionVO;
import uk.co.quidos.gdsap.framework.enums.WebsiteUserStatus;
import uk.co.quidos.gdsap.framework.user.enums.UserStatus;

public class QueryAdminObject extends BaseConditionVO{

	private WebsiteUserStatus websiteUserStatus;
	private UserStatus userStatus;
	
	public QueryAdminObject()
	{
		
	}
	
	public WebsiteUserStatus getWebsiteUserStatus() {
		return websiteUserStatus;
	}
	public void setWebsiteUserStatus(WebsiteUserStatus websiteUserStatus) {
		this.websiteUserStatus = websiteUserStatus;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
	
}
