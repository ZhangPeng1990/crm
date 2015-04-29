/**
 * 
 */
package uk.co.quidos.gdsap.framework.mail.services;

import uk.co.quidos.gdsap.framework.mail.Mail;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * @author shipeng
 *
 */
public interface MailServiceMgr extends BusinessObjectServiceMgr
{
	/**
	 * 同步发送邮件，相对速度较慢，
	 * @param mailInfo
	 * @return
	 */
	public boolean syncSendSync(Mail mailInfo);
	
	/**
	 * 异步发送，需要使用mq 服务器配合,本版本暂不实现
	 * @param mailInfo
	 */
	public void asynSend(Mail mailInfo);
	
	public boolean sendMail(Mail mailInfo) throws Exception;
}
