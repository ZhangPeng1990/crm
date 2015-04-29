/**
 * 
 */
package uk.co.quidos.gdsap.framework.user.services.object;

import java.util.Date;

import uk.co.quidos.dal.BaseConditionVO;
import uk.co.quidos.gdsap.framework.enums.UserType;
import uk.co.quidos.gdsap.framework.user.enums.AccessLevel;
import uk.co.quidos.gdsap.framework.user.enums.UserStatus;

/**
 * 查询条件<br/>
 * 其中 username,firstname,surname,email 作为Like 进行查询<br/>
 * userstatus,accesslevel入为null，则不作为条件查询<br/>
 * @author peng.shi
 */
public class ConditionVO extends BaseConditionVO
{
    private String userName;
    private Date startInsertTime;
    private Date endInsertTime;
    private UserStatus userStatus;
    private UserType userType;
    private String firstName;
    private String surName;
    private String email;
    private AccessLevel accessLevel;
    
	public String getUserName()
	{
		return userName;
	}
	public Date getStartInsertTime()
	{
		return startInsertTime;
	}
	public Date getEndInsertTime()
	{
		return endInsertTime;
	}
	public UserStatus getUserStatus()
	{
		return userStatus;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public String getSurName()
	{
		return surName;
	}
	public String getEmail()
	{
		return email;
	}
	public AccessLevel getAccessLevel()
	{
		return accessLevel;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	public void setStartInsertTime(Date startInsertTime)
	{
		this.startInsertTime = startInsertTime;
	}
	public void setEndInsertTime(Date endInsertTime)
	{
		this.endInsertTime = endInsertTime;
	}
	public void setUserStatus(UserStatus userStatus)
	{
		this.userStatus = userStatus;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public void setSurName(String surName)
	{
		this.surName = surName;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public void setAccessLevel(AccessLevel accessLevel)
	{
		this.accessLevel = accessLevel;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
}
