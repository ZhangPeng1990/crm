package uk.co.quidos.gdsap.framework.mail.utils;

import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import uk.co.quidos.common.util.FreeMarkerUtil;
import uk.co.quidos.gdsap.framework.mail.MailHost;
import uk.co.quidos.gdsap.framework.mail.MailTemplate;
import uk.co.quidos.gdsap.framework.mail.enums.MailTemplates;
import uk.co.quidos.gdsap.framework.sysconf.MailConfig;

/**
 * 发送邮件，包含simple或者html类型
 * 
 * @author peng.shi
 */
public class MailUtil {

	private static Logger logger = LoggerFactory.getLogger(MailUtil.class);

	private static final String MAIL_SMTP_AUTH = "true";

	private static final String MAIL_SMTP_TIMEOUT = "25000";
	
	private static Document mailDoc = MailConfig.getInstance().getMailDoc();
	private static XPath xpathSelector;
	
	public static MailHost getMailHost(){
		MailHost host = new MailHost();
		
		xpathSelector = DocumentHelper.createXPath("//mailhosts/mailhost/id");
		Element id = (Element)xpathSelector.selectSingleNode(mailDoc);
		host.setId(id != null ? id.getTextTrim() : null);
		
		xpathSelector = DocumentHelper.createXPath("//mailhosts/mailhost/smtp");
		Element smtp = (Element)xpathSelector.selectSingleNode(mailDoc);
		host.setSmtp(smtp != null ? smtp.getTextTrim() : null);
		
		xpathSelector = DocumentHelper.createXPath("//mailhosts/mailhost/smtp-port");
		Element smtp_port = (Element)xpathSelector.selectSingleNode(mailDoc);
		host.setSmtpPort(smtp_port != null ? Integer.parseInt(smtp_port.getTextTrim()) : null);
		
		xpathSelector = DocumentHelper.createXPath("//mailhosts/mailhost/from-mail-address");
		Element from_mail_address = (Element)xpathSelector.selectSingleNode(mailDoc);
		host.setFromMailAddress(from_mail_address != null ? from_mail_address.getTextTrim() : null);
		
		xpathSelector = DocumentHelper.createXPath("//mailhosts/mailhost/username");
		Element username = (Element)xpathSelector.selectSingleNode(mailDoc);
		host.setUsername(username != null ? username.getTextTrim() : null);
		
		xpathSelector = DocumentHelper.createXPath("//mailhosts/mailhost/password");
		Element password = (Element)xpathSelector.selectSingleNode(mailDoc);
		host.setPassword(password != null ? password.getTextTrim() : null);
		
		return host;
	}
	
	public static MailTemplate getMailTemplate(MailTemplates t, Map<String, Object> map, boolean isT){
		MailTemplate template = new MailTemplate();
		template.setId(t.name());
		
		xpathSelector = DocumentHelper.createXPath("//mailTemplates/mailTemplate[id=" + "'" + t.name() + "'" + "]/subject");
		Element subject = (Element)xpathSelector.selectSingleNode(mailDoc);
		template.setSubject(subject != null ? subject.getTextTrim() : null);
		
		xpathSelector = DocumentHelper.createXPath("//mailTemplates/mailTemplate[id=" + "'" + t.name() + "'" + "]/content");
		Element content = (Element)xpathSelector.selectSingleNode(mailDoc);
		template.setContent(content != null ? content.getTextTrim() : null);
		
		String emailContent = FreeMarkerUtil.template2String(template.getContent(),map,isT);
		template.setContent(emailContent);
		
		return template;
	}
	
	public static void sendSimpleEmail(String smtpHost, int port, String fromAddress, String username, String password,
			String[] toAddresses, String subject, String emailContent) throws MailException
	{
		logger.debug("spring javamail start simple email.");
		
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		
		senderImpl.setHost(smtpHost);
		senderImpl.setPort(port);
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		mailMessage.setTo(toAddresses);
		mailMessage.setFrom(fromAddress);
		mailMessage.setSubject(subject);
		mailMessage.setText(emailContent);
		
		senderImpl.setUsername(username);
		senderImpl.setPassword(password);
		
		Properties prop = new Properties();
		
		// 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.auth", MAIL_SMTP_AUTH);
		prop.put("mail.smtp.timeout", MAIL_SMTP_TIMEOUT);
		senderImpl.setJavaMailProperties(prop);
		
		senderImpl.send(mailMessage);
		logger.debug("spring javamail end simple email.");
	}
	
	public static void sendHtmlEmail(String smtpHost, int port, String fromAddress, String username, String password, String[] toAddresses,
			String subject, String emailContent) throws MessagingException, MailException
	{
		logger.debug("smtphost : " + smtpHost);
		logger.debug("port : " + port);
		logger.debug("fromAddress : " + fromAddress);
		logger.debug("username : " + username);
		logger.debug("password : " + password);
		logger.debug("toAddresses : " + toAddresses);
		logger.debug("subject : " + subject);
		logger.debug("emailContent : " + emailContent);
		logger.debug("spring javamail start html email.");
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		
		senderImpl.setHost(smtpHost);
		
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
		
		messageHelper.setTo(toAddresses);
		messageHelper.setFrom(fromAddress);
		messageHelper.setSubject(subject);
		
		// true 表示启动HTML格式的邮件
		messageHelper.setText(emailContent, true);
		
		senderImpl.setUsername(username); // 根据自己的情况,设置username
		senderImpl.setPassword(password); // 根据自己的情况, 设置password
		Properties prop = new Properties();
		
		// 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.auth", MAIL_SMTP_AUTH);
		prop.put("mail.smtp.timeout", MAIL_SMTP_TIMEOUT);
		
		senderImpl.setJavaMailProperties(prop);
		senderImpl.send(mailMessage);
		
		logger.debug("spring javamail end html email.");
	}
	
	public static void main(String[] args) {
		MailUtil.sendSimpleEmail("smtp.yooglebuy.com",
				25, "admin@yooglebuy.com",
				"admin@yooglebuy.com", "1234qwer",
				new String[]{"shipengpipi@126.com"}, UUID.randomUUID().toString(), "sdfsdf");
	}
	
}
