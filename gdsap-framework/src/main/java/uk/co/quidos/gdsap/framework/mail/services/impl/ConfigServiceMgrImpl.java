/**
 * 
 */
package uk.co.quidos.gdsap.framework.mail.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.mail.MailHost;
import uk.co.quidos.gdsap.framework.mail.services.ConfigServiceMgr;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;
import uk.co.quidos.gdsap.framework.sys.exception.QuidosRuntimeException;

/**
 * @author shipeng
 *
 */
@Service("configServiceMgr")
public class ConfigServiceMgrImpl extends AbsBusinessObjectServiceMgr implements ConfigServiceMgr
{
	private Log log = LogFactory.getLog(ConfigServiceMgrImpl.class);
	
	/**
	 * host 配置文件地址
	 */
	private static String HOST_CONFIG = "/quidos-mailhost.xml";

	private Map<String,MailHost> cache = new LinkedHashMap<String, MailHost>();
	
	public ConfigServiceMgrImpl()
	{
		this._initial();
	}
	
	private static Object lock = new Object();
	
	/**
	 * 对配置进行初始化工作,
	 * 将数据初始化到缓冲中.
	 */
	private void _initial()
	{
		synchronized (lock)
		{
			try
			{
				SAXReader reader = new SAXReader();
				Document document = reader.read(this.getClass().getResourceAsStream(HOST_CONFIG));
				if (document == null)
				{
					throw new ExceptionInInitializerError();
				}
				Element mailConfigsElement = document.getRootElement();
				/*
				<mail-configs>
					<mailhost>
						<id>AIRS-HOST</id>
						<smtp>smtp.emailsrvr.com</smtp>
						<smtp-port>25</smtp-port>
						<from-mail-address>AIRS@quidos.co.uk</from-mail-address>
						<username>AIRS@quidos.co.uk</username>
						<password>Password1</password>
					</mailhost>
				</mail-configs>
				*/
				Element mailHost = mailConfigsElement.element("mailhosts");
				List<Element> mailhostElements = mailHost.elements("mailhost");
				if (CollectionUtils.isEmpty(mailhostElements))
				{
					throw new ExceptionInInitializerError();
				}
				
				for (Element mailhostElement : mailhostElements)
				{
					String id = mailhostElement.element("id").getTextTrim();
					String smtp = mailhostElement.element("smtp").getTextTrim();
					String smtpPort = mailhostElement.element("smtp-port").getTextTrim();
					String fromMailAddress = mailhostElement.element("from-mail-address").getTextTrim();
					String username = mailhostElement.element("username").getTextTrim();
					String password = mailhostElement.element("password").getTextTrim();
					
					Assert.hasText(id);
					Assert.hasText(smtp);
					Assert.hasText(smtpPort);
					Assert.hasText(fromMailAddress);
					Assert.hasText(username);
					
					MailHost host = new MailHost();
					host.setId(id);
					host.setSmtp(smtp);
					host.setSmtpPort(Integer.parseInt(smtpPort));
					host.setUsername(username);
					host.setPassword(password);
					host.setFromMailAddress(fromMailAddress);
					
					cache.put(id, host);
				}
				log.info("=============================> Mail host config finish.");
				
			}
			catch (DocumentException e)
			{
				e.printStackTrace();
				throw new ExceptionInInitializerError();
			}
		}
		
	}
	@Override
	public MailHost addMailHost(MailHost config) throws ObjectDuplicateException
	{
		Assert.notNull(config);
		if (!_validateMailConfig(config))
		{
			throw new IllegalArgumentException();
		}
		
		if (this.getMailHost(config.getId()) != null)
		{
			throw new ObjectDuplicateException();
		}
		
		//写入文件中
		synchronized (lock)
		{
			SAXReader reader = new SAXReader();
			Document document;
			try
			{
				document = reader.read(this.getClass().getResourceAsStream(HOST_CONFIG));
				Element mailConfigsElement = document.getRootElement();
				/*
				<mailhost>
					<id>AIRS-HOST</id>
					<smtp>smtp.emailsrvr.com</smtp>
					<smtp-port>25</smtp-port>
					<from-mail-address>AIRS@quidos.co.uk</from-mail-address>
					<username>AIRS@quidos.co.uk</username>
					<password>Password1</password>
				</mailhost>
				 */
				Element mailHostElement = mailConfigsElement.addElement("mailhost");
				Element idElement = mailHostElement.addElement("id");
				idElement.setText(config.getId());
				Element smtpElement = mailHostElement.addElement("smtp");
				smtpElement.setText(config.getSmtp());
				Element smtpPortElement = mailHostElement.addElement("smtp-port");
				smtpPortElement.setText(String.valueOf(config.getSmtpPort()));
				Element usernameElement = mailHostElement.addElement("username");
				usernameElement.setText(config.getUsername());
				Element passwordElement = mailHostElement.addElement("password");
				passwordElement.setText(config.getPassword());
				Element fromMailAddressElement = mailHostElement.addElement("from-mail-address");
				fromMailAddressElement.setText(config.getFromMailAddress());
				
				File file = new File(this.getClass().getResource(HOST_CONFIG).toURI());
				OutputFormat format = OutputFormat.createPrettyPrint();
				XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
				writer.write(document);
				writer.close();
			} catch (DocumentException e)
			{
				e.printStackTrace();
				throw new QuidosRuntimeException();
			} 
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
				throw new QuidosRuntimeException();
			} 
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
				throw new QuidosRuntimeException();
			} catch (IOException e)
			{
				e.printStackTrace();
				throw new QuidosRuntimeException();
			} catch (URISyntaxException e)
			{
				e.printStackTrace();
				throw new QuidosRuntimeException();
			}
			cache.put(config.getId(), config);
		}
		return config;
	}

	@Override
	public MailHost getMailHost(String id)
	{
		return cache.get(id);
	}

	@Override
	public List<MailHost> getMailHosts()
	{
		List<MailHost> list = new ArrayList<MailHost>();
		list.addAll(cache.values());
		return list;
	}

	@Override
	public MailHost updateMailHost(MailHost config)
	{
		Assert.notNull(config);
		if (!_validateMailConfig(config))
		{
			throw new IllegalArgumentException();
		}
		
		MailHost loadMailConfig = this.getMailHost(config.getId());
		if (loadMailConfig == null)
		{
			throw new IllegalArgumentException();
		}
		
		//写入文件中
		synchronized (lock)
		{
			SAXReader reader = new SAXReader();
			Document document;
			try
			{
				document = reader.read(this.getClass().getResourceAsStream(HOST_CONFIG));
				Element mailConfigsElement = document.getRootElement();
				/*
				<mailhost>
					<id>AIRS-HOST</id>
					<smtp>smtp.emailsrvr.com</smtp>
					<smtp-port>25</smtp-port>
					<from-mail-address>AIRS@quidos.co.uk</from-mail-address>
					<username>AIRS@quidos.co.uk</username>
					<password>Password1</password>
				</mailhost>
				 */
				List<Element> mailhosts = mailConfigsElement.elements("mailhost");
				for (Element temp : mailhosts)
				{
					Element idElement = temp.element("id");
					if (idElement.getText().equals(config.getId()))
					{
						Element smtpElement = temp.element("smtp");
						smtpElement.setText(config.getSmtp());
						Element smtpPortElement = temp.element("smtp-port");
						smtpPortElement.setText(String.valueOf(config.getSmtpPort()));
						Element usernameElement = temp.element("username");
						usernameElement.setText(config.getUsername());
						Element passwordElement = temp.element("password");
						passwordElement.setText(config.getPassword());
						Element fromMailAddressElement = temp.element("from-mail-address");
						fromMailAddressElement.setText(config.getFromMailAddress());
						break;
					}
				}
				
				
				File file = new File(this.getClass().getResource(HOST_CONFIG).toURI());
				OutputFormat format = OutputFormat.createPrettyPrint();
				XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
				writer.write(document);
				writer.close();
			} catch (DocumentException e)
			{
				e.printStackTrace();
				throw new QuidosRuntimeException();
			} 
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
				throw new QuidosRuntimeException();
			} 
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
				throw new QuidosRuntimeException();
			} catch (IOException e)
			{
				e.printStackTrace();
				throw new QuidosRuntimeException();
			} catch (URISyntaxException e)
			{
				e.printStackTrace();
				throw new QuidosRuntimeException();
			}
			cache.put(config.getId(), config);
		}
		return config;
	}
	
	@Override
	public void deleteMailHost(String id)
	{
		MailHost mailhost = this.getMailHost(id);
		if (mailhost == null)
		{
			throw new IllegalArgumentException();
		}
		
		SAXReader reader = new SAXReader();
		Document document;
		
		try
		{
			document = reader.read(this.getClass().getResourceAsStream(HOST_CONFIG));
			Element mailConfigsElement = document.getRootElement();
			/*
			<mailhost>
				<id>AIRS-HOST</id>
				<smtp>smtp.emailsrvr.com</smtp>
				<smtp-port>25</smtp-port>
				<from-mail-address>AIRS@quidos.co.uk</from-mail-address>
				<username>AIRS@quidos.co.uk</username>
				<password>Password1</password>
			</mailhost>
			 */
			
			List<Element> mailhosts = mailConfigsElement.elements("mailhost");
			for (Element temp : mailhosts)
			{
				Element idElement = temp.element("id");
				if (idElement.getText().equals(id))
				{
					mailConfigsElement.remove(temp);
					break;
				}
			}
			
			File file = new File(this.getClass().getResource(HOST_CONFIG).toURI());
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
			writer.write(document);
			writer.close();
		} catch (DocumentException e)
		{
			e.printStackTrace();
			throw new QuidosRuntimeException();
		} 
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			throw new QuidosRuntimeException();
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			throw new QuidosRuntimeException();
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new QuidosRuntimeException();
		} catch (URISyntaxException e)
		{
			e.printStackTrace();
			throw new QuidosRuntimeException();
		}
		cache.remove(id);
	}
	
	@Override
	public boolean exist(String id)
	{
		return cache.containsKey(id);
	}
	
	private boolean _validateMailConfig(MailHost host)
	{
		if (StringUtils.isEmpty(host.getId()))
		{
			return false;
		}
		if(StringUtils.isEmpty(host.getSmtp()))
		{
			return false;
		}
		if (StringUtils.isEmpty(host.getUsername()))
		{
			return false;
		}
		if (StringUtils.isEmpty(host.getFromMailAddress()))
		{
			return false;
		}
		return true;
	}
}
