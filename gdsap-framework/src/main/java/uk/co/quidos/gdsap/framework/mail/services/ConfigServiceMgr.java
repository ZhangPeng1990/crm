/**
 * 
 */
package uk.co.quidos.gdsap.framework.mail.services;

import java.util.List;

import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.mail.MailHost;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * @author shipeng
 * 对Host config 进行管理　<br />
 * 数据源为本地 quidos-mailhost.xml配置文件
 */
public interface ConfigServiceMgr extends BusinessObjectServiceMgr
{
	public static final String SERVICE_NAME = "configServiceMgr";
	
	/**
	 * 添加MailHost 到配置文件中
	 * @param config
	 * @return
	 */
	MailHost addMailHost(MailHost config) throws ObjectDuplicateException;
	
	/**
	 * 更新邮件主机配置
	 * @param config
	 * @return
	 */
	MailHost updateMailHost(MailHost config);
	
	/**
	 * 获取邮件主机配置
	 * @param id
	 * @return
	 */
	MailHost getMailHost(String id);
	
	/**
	 * 获取邮件主机列表
	 * @return
	 */
	List<MailHost> getMailHosts();
	
	/**
	 * 是否存在
	 * @param id
	 * @return
	 */
	boolean exist(String id);
	
	/**
	 * 删除MailHost
	 * @param id
	 */
	void deleteMailHost(String id);
	
}
