package uk.co.quidos.gdsap.framework.sysconf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

public class MailConfig {
	
	private static Log LOG = LogFactory.getLog(MailConfig.class);

	/**
	 * 全局配置文件路径
	 */
	public static final String MAIL_CONFIG = "/quidos-mailhost.xml";
	
	private Document mailDoc;
	
	private static MailConfig mc;
	
	private static Object lock = new Object();
	
	private MailConfig(){
		if(mc == null){
			//初始化
			synchronized (lock){
				try {
					mailDoc = getDocumentByURI();
				} catch (Exception e) {
					LOG.error("May be file not found or file other error.");
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 获取单例 
	 * @return
	 */
	public static MailConfig getInstance()
	{
		if (mc == null)
		{
			mc = new MailConfig();
		}
		return mc;
	}
	
	public Document getDocumentByURI() throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read(this.getClass().getResourceAsStream(MAIL_CONFIG));
		if (document == null)
		{
			throw new ExceptionInInitializerError();
		}
		return document;
	}

	public Document getMailDoc() {
		return mailDoc;
	}
}
