/**
 * 
 */
package uk.co.quidos.gdsap.framework.mail.services.impl;

import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import uk.co.quidos.gdsap.framework.mail.Mail;
import uk.co.quidos.gdsap.framework.mail.enums.MailType;
import uk.co.quidos.gdsap.framework.mail.services.MailServiceMgr;
import uk.co.quidos.gdsap.framework.mail.utils.MailUtil;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;

/**
 * @author shipeng
 *
 */
@Service("mailServiceMgr")
public class MailServiceMgrImpl extends AbsBusinessObjectServiceMgr implements MailServiceMgr
{
	private Log log = LogFactory.getLog(MailServiceMgrImpl.class);
	
	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.mail.services.MailServiceMgr#asynSend(uk.co.quidos.gdsap.framework.mail.Mail)
	 */
	@Override
	public void asynSend(Mail mailInfo)
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.mail.services.MailServiceMgr#syncSendSync(uk.co.quidos.gdsap.framework.mail.Mail)
	 */
	@Override
	public boolean syncSendSync(Mail mailInfo)
	{
		Assert.notNull(mailInfo);
		if (mailInfo.getMailType().equals(MailType.Html))
		{
			log.info("MailType -> " + MailType.Html);
			try
			{
				MailUtil.sendHtmlEmail(mailInfo.getMailHost().getSmtp(), mailInfo.getMailHost().getSmtpPort(), mailInfo.getMailHost().getFromMailAddress(), mailInfo.getMailHost().getUsername(), mailInfo.getMailHost().getPassword(),
						new String[]
						{ mailInfo.getToAddress().get(0) }, mailInfo.getSubject(), mailInfo.getEmailContent());
				return true;
			}
			catch (MessagingException e)
			{
				e.printStackTrace();
				return false;
			}
			catch (MailException e)
			{
				e.printStackTrace();
				return false;
			}
		}
		if (mailInfo.getMailType().equals(MailType.Simple))
		{
			log.info("MailType -> " + MailType.Html);
			try
			{
				MailUtil.sendSimpleEmail(mailInfo.getMailHost().getSmtp(), mailInfo.getMailHost().getSmtpPort(), mailInfo
						.getMailHost().getFromMailAddress(), mailInfo.getMailHost().getUsername(), mailInfo.getMailHost()
						.getPassword(), new String[]
				{ mailInfo.getToAddress().get(0) }, mailInfo.getSubject(), mailInfo.getEmailContent());
				return true;
			}
			catch (MailException e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	public boolean sendMail(Mail mailInfo) throws Exception  
    {  
        Email email = null;
        switch (mailInfo.getMailType()) {
		case Html:
			email = new HtmlEmail();
			break;

		case Multi:
			email = new MultiPartEmail();
			break;
		
		case Simple:
			email = new SimpleEmail();
			break;
			
		default:
			email = new SimpleEmail();
			break;
		}
        
        email.setCharset("UTF-8");  
        email.setHostName(mailInfo.getMailHost().getSmtp());  
        email.setSmtpPort(mailInfo.getMailHost().getSmtpPort());
        email.setAuthentication(mailInfo.getMailHost().getUsername(), mailInfo.getMailHost().getPassword());  
        for(String toEmail : mailInfo.getToAddress())  
        {  
            email.addTo(toEmail);  
        }
        
        if(mailInfo.getCcAddress() != null && mailInfo.getCcAddress().size() > 0){
        	for(String ccEmail : mailInfo.getCcAddress())  
            {  
                email.addTo(ccEmail);  
            }
        }
        
        email.addHeader("X-Mailer", "WEB MAILER 1.0.1");
        email.setSSL(true);
        email.setFrom(mailInfo.getMailHost().getFromMailAddress());  
        email.setSubject(mailInfo.getSubject());  
        email.setMsg(mailInfo.getEmailContent());  
        String result = email.send();
        System.out.println(result);
        
        return true;
    }  

}
