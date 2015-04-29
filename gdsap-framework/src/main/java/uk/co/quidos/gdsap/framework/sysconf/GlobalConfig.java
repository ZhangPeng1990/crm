/**
 * 
 */
package uk.co.quidos.gdsap.framework.sysconf;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.co.quidos.common.util.MessageUtil;
import uk.co.quidos.gdsap.evaluation.enums.ReportLocation;

/**
 * @author peng.shi
 *
 */
public class GlobalConfig
{
	private static Log LOG = LogFactory.getLog(GlobalConfig.class);
	
	/**
	 * 全局配置文件路径
	 */
	public static final String GLOBAL_CONFIG = "/global-config.properties";
	
	private static Properties globalProperties;
	
	private static GlobalConfig gc;
	
	private static Object lock = new Object();
	
	private GlobalConfig()
	{
		if (globalProperties == null)
		{
			//初始化
			synchronized (lock)
			{
				globalProperties = new Properties();
				try
				{
					globalProperties.load(GlobalConfig.class.getResourceAsStream(GLOBAL_CONFIG));
				} catch (IOException e)
				{
					LOG.error("Global Initial Error,May be file not found or file other error.");
					e.printStackTrace();
					throw new ExceptionInInitializerError();
				}
			}
		}
	}
	
	/**
	 * 获取单例 
	 * @return
	 */
	public static GlobalConfig getInstance()
	{
		if (gc == null)
		{
			gc = new GlobalConfig();
		}
		return gc;
	}
	
	/**
	 * 不以" / "为结尾
	 * @return
	 */
	public String getFSDir()
	{
		String tmp = globalProperties.getProperty("gdsap.fs.dir");
		if (StringUtils.isEmpty(tmp ) || StringUtils.isBlank(tmp))
		{
			throw new ExceptionInInitializerError();
		}
		if (tmp.endsWith("/"))
		{
			tmp = tmp.substring(0,tmp.length()-1);
		}
		return tmp;
	}
	
	/**
	 * 不以" / "为结尾
	 * @return
	 */
	public String getFrontendFSDir()
	{
		String tmp = globalProperties.getProperty("gdsap.fs.dir.frontend");
		if (StringUtils.isEmpty(tmp ) || StringUtils.isBlank(tmp))
		{
			throw new ExceptionInInitializerError();
		}
		if (tmp.endsWith("/"))
		{
			tmp = tmp.substring(0,tmp.length()-1);
		}
		return tmp;
	}
	
	/**
	 * 不以" / "为结尾
	 * @return
	 */
	public String getFSrestfulDir()
	{
		String tmp = globalProperties.getProperty("gdsap.fs.dir.restful");
		if (StringUtils.isEmpty(tmp ) || StringUtils.isBlank(tmp))
		{
			throw new ExceptionInInitializerError();
		}
		if (tmp.endsWith("/"))
		{
			tmp = tmp.substring(0,tmp.length()-1);
		}
		return tmp;
	}
	
	/**
	 * 返回计算配置地址
	 * 对应属性文件中的Key为 ： gdsap.cal.webservice.addr
	 * 结果例如：gdsap.cal.webservice.addr
	 * @return
	 */
	public String getCalWebserviceAddr()
	{
		String tmp = globalProperties.getProperty("gdsap.cal.webservice.addr");
		if (StringUtils.isEmpty(tmp) || StringUtils.isBlank(tmp))
		{
			throw new ExceptionInInitializerError();
		}
		return tmp;
	}
	

	/**
	 * 调用C# webservice ，传过去oa计算文件，返回计算结果的xml
	 * gdsap.rdsapLIG2CalXml.webservice.addr
	 * @return
	 */
	public String getCalResultXmlWebserviceAddr()
	{
		String tmp = globalProperties.getProperty("gdsap.rdsapLIG2CalXml.webservice.addr");
		if (StringUtils.isEmpty(tmp) || StringUtils.isBlank(tmp))
		{
			throw new ExceptionInInitializerError();
		}
		return tmp;
	}
	
	/**
	 * 调用C# webservice ，传过去oa计算文件，返回计算结果的xml
	 * gdsap.rdsap.gdipLIG2CalXml.webservice.addr
	 * @return
	 */
	public String getGDIPCalResultXmlWebserviceAddr()
	{
		String tmp = globalProperties.getProperty("gdsap.rdsap.gdipLIG2CalXml.webservice.addr");
		if (StringUtils.isEmpty(tmp) || StringUtils.isBlank(tmp))
		{
			throw new ExceptionInInitializerError();
		}
		return tmp;
	}
	
	/**
	 * 调用C# webservice ，传过去oa计算文件，返回GDAR LIG中间部分
	 * gdsap.rdsapLIG2CalXml.webservice.addr
	 * @return
	 */
	public String getGdarLigXmlWebserviceAddr()
	{
		String tmp = globalProperties.getProperty("gdsap.oacalxml2oaLIG.webservice.addr");
		if (StringUtils.isEmpty(tmp) || StringUtils.isBlank(tmp))
		{
			throw new ExceptionInInitializerError();
		}
		return tmp;
	}
	
	/**
	 * 调用C# webservice ，传过去oa计算文件，返回GDAR GDIP中间部分
	 * gdsap.rdsapLIG2CalXml.webservice.addr
	 * @return
	 */
	public String getGdipLigXmlWebserviceAddr()
	{
		String tmp = globalProperties.getProperty("gdsap.oacalxml2GdipLIG.webservice.addr");
		if (StringUtils.isEmpty(tmp) || StringUtils.isBlank(tmp))
		{
			throw new ExceptionInInitializerError();
		}
		return tmp;
	}
	
	public String getLoginUrl()
	{
		String tmp = globalProperties.getProperty("gdsap.login.url");
		if (StringUtils.isEmpty(tmp) || StringUtils.isBlank(tmp))
		{
			throw new ExceptionInInitializerError();
		}
		return tmp;
	}
	
	/**
	 * 生成PDF地址
	 * @param solutionId
	 * @return
	 */
	public String getPdfAddress(String solutionId) {
		return globalProperties.getProperty("gdsap.pdfaddress") + solutionId;
	}
	
	/**
	 * 导出Excel文件的页大小
	 */
	public int getExcelPageSize()
	{
		String temp = globalProperties.getProperty("gdsap.excel.pazeSize");
		return Integer.parseInt(temp);
	}
	
	/**
	 * 获取从后台登陆qube的主机地址
	 * @return
	 */
	public String getHost(){
		String host = globalProperties.getProperty("gdsap.host.address");
		return host;
	}
	
	/**
	 * 获取寻址的webservice地址
	 * @return
	 */
	public String getFindAddressListUrl(){
		String url = globalProperties.getProperty("gdsap.findAddress.url");
		return url;
	}
	/**
	 * 获取寻址的webservice地址
	 * @return
	 */
	public String getFindAddressUrl(){
		String url = globalProperties.getProperty("gdsap.singleAddress.url");
		return url;
	}
	
	/**
	 * 获取当前环境
	 * @return
	 */
	public WebserviceEnvironment getCurrentWebserviceEnvironment() {
		return WebserviceEnvironment.getWebserviceEnvironment(globalProperties.getProperty("gdsap.ws.env"));
	}
	
	/**
	 * 获取EAW lodgement address
	 * @return
	 */
	public String getEAWLodgementAddress() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.ws.lodgementaddr.eaw." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取给第三方公司提供的lodge GDIP的Auth Username
	 * @return
	 */
	public String getGDIPLodgementAuthUsername(ReportLocation reportLocation) {
		if(reportLocation.equals(ReportLocation.NIR))
		{
			reportLocation = ReportLocation.EAW;
		}
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty(reportLocation.getCode() + ".gdsap.ws.gdip.lodgement.username." + currentWebserviceEnvironment.getValue());
	}
	/**
	 * 获取给第三方公司提供的lodge GDIP的Auth Password
	 * @return
	 */
	public String getGDIPLodgementAuthPassword(ReportLocation reportLocation) {
		if(reportLocation.equals(ReportLocation.NIR))
		{
			reportLocation = ReportLocation.EAW;
		}
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty(reportLocation.getCode() + ".gdsap.ws.gdip.lodgement.password." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取EAW GDIP lodgement address
	 * @return
	 */
	public String getEAWGdipLodgementAddress() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.ws.gdip.lodgementaddr.eaw." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取EAW Cert 证书位置
	 * @return
	 */
	public String getEAWCert() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.ws.cert.eaw." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取EAW GDIP Cert 证书位置
	 * @return
	 */
	public String getEAWGdipCert() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.ws.gdip.cert.eaw." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取EAW Cert 证书密码
	 * @return
	 */
	public String getEAWCertPassword() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.ws.certpassword.eaw." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取EAW GDIP Cert 证书密码
	 * @return
	 */
	public String getEAWGdipCertPassword() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.ws.gdip.certpassword.eaw." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取EAW Cert 证书用户名
	 * @return
	 */
	public String getEAWHttpAuthUsername() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.ws.httpauth.username.eaw." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取EAW GDIP Cert 证书用户名
	 * @return
	 */
	public String getEAWGdipHttpAuthUsername() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.ws.gdip.httpauth.username.eaw." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取EAW Cert 证书密码
	 * @return
	 */
	public String getEAWHttpAuthPassword() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.ws.httpauth.password.eaw." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取EAW GDIP Cert 证书密码
	 * @return
	 */
	public String getEAWGdipHttpAuthPassword() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.ws.gdip.httpauth.password.eaw." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取Eaw 下载地址
	 * @return
	 */
	public String getEAWDownloadAddress() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.downloadpdf.address.eaw." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取Sct lodgement address
	 * @param rrn
	 * @return
	 */
	public String getSCTLodgementaddr(String rrn) {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.ws.lodgementaddr.sct." + currentWebserviceEnvironment.getValue()) + rrn;
	}
	
	/**
	 * 获取SCT request pdf address
	 * @param rrn
	 * @return
	 */
	public String getSCTRequestPdfAddr(String rrn) {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.ws.requestpdfaddr.sct." + currentWebserviceEnvironment.getValue()) + rrn;
	}
	
	/**
	 * 获取SCT request xml address
	 * @param rrn
	 * @return
	 */
	public String getSCTRequestXmlAddr(String rrn) {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.ws.requestoaxmladdr.sct." + currentWebserviceEnvironment.getValue()) + rrn;
	}
	
	/**
	 * 获取授权auth username
	 * @return
	 */
	public String getSCTHttpAuthUsername() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.ws.httpauth.username.sct." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取sct 授权 http password
	 * @return
	 */
	public String getSCTHttpAuthPassword() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.ws.httpauth.password.sct." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取 assessor organisationid
	 * @return
	 */
	public String getSCTAssessorOrganisationId() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.ws.assessororganisationId.sct." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取EPC xml 获取地址
	 */
	public String getSCTRequestEPCXmlAddr(String epcRrn) {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.ws.requestepcxmladdr.sct." + currentWebserviceEnvironment.getValue()) + epcRrn;
	}
	
	/**
	 * 获取SCT Organisation Certification Number
	 * @return
	 */
	public String getSCTOrganisationCertificationnumber() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.organisation.certificationnumber.sct." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取EAW Organisation Certification Number
	 * @return
	 */
	public String getEAWOrganisationCertificationnumber() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.organisation.certificationnumber.eaw." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取EAW organisation
	 * @return
	 */
	public String getEAWOrganisation() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.organisation.eaw." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取EAW Organisation website
	 * @return
	 */
	public String getEAWOrganisationWebsite() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
											 
		return globalProperties.getProperty("gdsap.organisation.website.eaw." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取SCT Organisation
	 * @return
	 */
	public String getSCTOrganisation() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.organisation.sct." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取SCT Organisation Website
	 * @return
	 */
	public String getSCTOrganisationWebsite() {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		return globalProperties.getProperty("gdsap.organisation.website.sct." + currentWebserviceEnvironment.getValue());
	}
	
	/**
	 * 获取 苏格兰 get oa xml 地址
	 * @param oarrn
	 * @return
	 */
	public String getGetOAXmlWebserviceAddressOfSCT(String oarrn) {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		String suffix = null;
		if (currentWebserviceEnvironment.equals(WebserviceEnvironment.Live)) {
			suffix = WebserviceEnvironment.Live.getValue();
		}else{
			suffix = WebserviceEnvironment.Ote.getValue();
		}
		return MessageUtil.pattern2String(globalProperties.getProperty("gdsap.ws.getoaxml.address." + suffix), new String[]{oarrn});
	}
	
	/**
	 * 获取苏格兰 get oa pdf 地址
	 * @param oarrn
	 * @return
	 */
	public String getGetOAPDFWebserviceAddressOfSCT(String oarrn) {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		String suffix = null;
		if (currentWebserviceEnvironment.equals(WebserviceEnvironment.Live)) {
			suffix = WebserviceEnvironment.Live.getValue();
		}else{
			suffix = WebserviceEnvironment.Ote.getValue();
		}
		return MessageUtil.pattern2String(globalProperties.getProperty("gdsap.ws.getoapdf.address." + suffix), new String[]{oarrn});
	}
	
	/**
	 * 获取苏格兰 get gdip pdf 地址
	 * @param oarrn
	 * @return
	 */
	public String getGetGdipPDFWebserviceAddressOfSCT(String gdipRrn) {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		String suffix = null;
		if (currentWebserviceEnvironment.equals(WebserviceEnvironment.Live)) {
			suffix = WebserviceEnvironment.Live.getValue();
		}else{
			suffix = WebserviceEnvironment.Ote.getValue();
		}
		return MessageUtil.pattern2String(globalProperties.getProperty("gdsap.ws.getgdippdf.address." + suffix), new String[]{gdipRrn});
	}
	
	/**
	 * 获取苏格兰 lodge oa 地址
	 * @param oarrn
	 * @return
	 */
	public String getLodgeOAXmlWebserviceAddressOfSCT(String oarrn) {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		String suffix = null;
		if (currentWebserviceEnvironment.equals(WebserviceEnvironment.Live)) {
			suffix = WebserviceEnvironment.Live.getValue();
		}else if(currentWebserviceEnvironment.equals(WebserviceEnvironment.Uat)){
			suffix = WebserviceEnvironment.Uat.getValue();
		}
		else{
			suffix = WebserviceEnvironment.Ote.getValue();
		}
		return MessageUtil.pattern2String(globalProperties.getProperty("gdsap.ws.lodgeoa.address." + suffix), new String[]{oarrn});
	}
	
	/**
	 * 获取苏格兰 lodge GDIP 地址
	 * @param oarrn
	 * @return
	 */
	public String getLodgeGDIPXmlWebserviceAddressOfSCT(String gdipRrn) {
		WebserviceEnvironment currentWebserviceEnvironment = this.getCurrentWebserviceEnvironment();
		String suffix = null;
		if (currentWebserviceEnvironment.equals(WebserviceEnvironment.Live)) {
			suffix = WebserviceEnvironment.Live.getValue();
		}else if(currentWebserviceEnvironment.equals(WebserviceEnvironment.Uat)){
			suffix = WebserviceEnvironment.Uat.getValue();
		}
		else{
			suffix = WebserviceEnvironment.Ote.getValue();
		}
		return MessageUtil.pattern2String(globalProperties.getProperty("gdsap.ws.lodgegdip.address." + suffix), new String[]{gdipRrn});
	}
	
	/**
	 * 获取苏格兰密钥文件
	 * @return
	 */
	public String getCertOfSCT() {
		return globalProperties.getProperty("gdsap.ws.cert");
	}
	
	/**
	 * 获取苏格兰证书密码
	 * @return
	 */
	public String getCertPasswordOfSCT() {
		return globalProperties.getProperty("gdsap.ws.certpassword");
	}
}
