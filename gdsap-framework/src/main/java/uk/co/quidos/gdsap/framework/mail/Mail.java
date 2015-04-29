package uk.co.quidos.gdsap.framework.mail;

import java.util.List;

import org.springframework.util.Assert;

import uk.co.quidos.gdsap.framework.mail.enums.MailType;

/**
 * @author shipeng
 *
 */
public class Mail
{
	private MailHost mailHost;
	private List<String> toAddress;
	private List<String> ccAddress;
	private String subject;
	private String emailContent;
	private MailType mailType;
	
	public Mail(MailHost mailHost, List<String> toAddress, List<String> ccAddress, 
			String subject, String emailContent, MailType mailType) {
		Assert.notNull(mailHost);
		Assert.notEmpty(toAddress);
		Assert.hasText(subject);
		Assert.hasText(emailContent);
		Assert.notNull(mailType);
		
		this.mailHost = mailHost;
		this.toAddress = toAddress;
		this.ccAddress = ccAddress;
		this.subject = subject;
		this.emailContent = emailContent;
		this.mailType = mailType;
	}

	/**
	 * @return the mailHost
	 */
	public MailHost getMailHost()
	{
		return mailHost;
	}

	
	public List<String> getCcAddress() {
		return ccAddress;
	}

	/**
	 * @return Returns the toAddress.
	 */
	public List<String> getToAddress()
	{
		return toAddress;
	}

	/**
	 * @return Returns the subject.
	 */
	public String getSubject()
	{
		return subject;
	}

	/**
	 * @return Returns the emailContent.
	 */
	public String getEmailContent()
	{
		return emailContent;
	}

	/**
	 * @return Returns the mailType.
	 */
	public MailType getMailType()
	{
		return mailType;
	}
	
}
