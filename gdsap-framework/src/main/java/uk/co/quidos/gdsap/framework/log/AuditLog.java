/**
 * 
 */
package uk.co.quidos.gdsap.framework.log;

import java.util.Date;

import uk.co.quidos.gdsap.framework.enums.UserType;
import uk.co.quidos.gdsap.framework.log.enums.LogLevel;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 *
 */
public class AuditLog extends AbsBusinessObject
{
	private static final long serialVersionUID = -3528788774303162544L;
	
	private String id;
    private Date insertTime;
    private LogLevel level;
    private String ipAddress;
    private UserType userType;
    private Long userId;
    private LogType logType;
    private String content;
    
	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * @return the insertTime
	 */
	public Date getInsertTime()
	{
		return insertTime;
	}
	/**
	 * @return the level
	 */
	public LogLevel getLevel()
	{
		return level;
	}
	/**
	 * @return the ipAddress
	 */
	public String getIpAddress()
	{
		return ipAddress;
	}
	/**
	 * @return the userType
	 */
	public UserType getUserType()
	{
		return userType;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId()
	{
		return userId;
	}
	/**
	 * @return the logType
	 */
	public LogType getLogType()
	{
		return logType;
	}
	/**
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}
	/**
	 * @param insertTime the insertTime to set
	 */
	public void setInsertTime(Date insertTime)
	{
		this.insertTime = insertTime;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(LogLevel level)
	{
		this.level = level;
	}
	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(UserType userType)
	{
		this.userType = userType;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}
	/**
	 * @param logType the logType to set
	 */
	public void setLogType(LogType logType)
	{
		this.logType = logType;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content)
	{
		this.content = content;
	}
	
}
