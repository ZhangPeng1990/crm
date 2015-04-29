/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;

import uk.co.quidos.gdsap.framework.mail.utils.MailUtil;

/**
 * @author peng.shi@argylesoftware.co.uk
 */
public class SendMailMainApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String smtp = "mail.noris.net";
		int smtpPort = 25;
		String fromMailAddress = "mail@noris.net";
		String username = "";
		String password = "";
		String toMailAddress = "peng.shi@argylesoftware.co.uk";
		String subject = "test";
		String mailContent = "test content";
		
		try {
			MailUtil.sendHtmlEmail(smtp, smtpPort, fromMailAddress, username, password,new String[] { toMailAddress }, subject,mailContent);
		} catch (MailException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
