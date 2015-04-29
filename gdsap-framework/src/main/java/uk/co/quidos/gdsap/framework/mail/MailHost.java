/**
 * 
 */
package uk.co.quidos.gdsap.framework.mail;


/**
 * @author shipeng
 *
 */
public class MailHost
{
	/**
	 * 引用id
	 */
	private String id;
	
	/**
	 * smtp 地址
	 */
	private String smtp;
	/**
	 * smtp 端口
	 */
	private int smtpPort;
	/**
	 * 发送地址
	 */
	private String fromMailAddress;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	
	public String getId() 
	{
		return this.id;
	}

	public String getFromMailAddress()
	{
		return fromMailAddress;
	}

	/**
	 * @return the smtp
	 */
	public String getSmtp()
	{
		return smtp;
	}

	/**
	 * @param smtp the smtp to set
	 */
	public void setSmtp(String smtp)
	{
		this.smtp = smtp;
	}

	/**
	 * @return the smtpPort
	 */
	public int getSmtpPort()
	{
		return smtpPort;
	}

	/**
	 * @param smtpPort the smtpPort to set
	 */
	public void setSmtpPort(int smtpPort)
	{
		this.smtpPort = smtpPort;
	}



	public void setFromMailAddress(String fromMailAddress)
	{
		this.fromMailAddress = fromMailAddress;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setId(String id)
	{
		this.id = id;
	}
	
}
