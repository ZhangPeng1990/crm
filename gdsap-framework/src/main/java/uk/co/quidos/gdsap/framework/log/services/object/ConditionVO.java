/**
 * 
 */
package uk.co.quidos.gdsap.framework.log.services.object;

import java.util.Date;

import uk.co.quidos.gdsap.framework.enums.UserType;
import uk.co.quidos.gdsap.framework.log.enums.LogLevel;

/**
 * @author peng.shi
 * 
 */
public class ConditionVO
{
	private Date startInsertTime;
	private Date endInsertTime;
	private LogLevel logLevel;
	private UserType userType;
	private String ip;

	public Date getStartInsertTime()
	{
		return startInsertTime;
	}

	public void setStartInsertTime(Date startInsertTime)
	{
		this.startInsertTime = startInsertTime;
	}

	public Date getEndInsertTime()
	{
		return endInsertTime;
	}

	public void setEndInsertTime(Date endInsertTime)
	{
		this.endInsertTime = endInsertTime;
	}

	public LogLevel getLogLevel()
	{
		return logLevel;
	}

	public void setLogLevel(LogLevel logLevel)
	{
		this.logLevel = logLevel;
	}

	public UserType getUserType()
	{
		return userType;
	}

	public void setUserType(UserType userType)
	{
		this.userType = userType;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

}
