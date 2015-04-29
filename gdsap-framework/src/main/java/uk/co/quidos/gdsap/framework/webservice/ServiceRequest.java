package uk.co.quidos.gdsap.framework.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;

import uk.co.quidos.common.util.xml.XSDErrorVO;
import uk.co.quidos.common.util.xml.XmlUtil;
import uk.co.quidos.gdsap.evaluation.utils.Base64;
import uk.co.quidos.gdsap.framework.sysconf.GlobalConfig;
import uk.co.quidos.gdsap.framework.user.User;

public class ServiceRequest {

	public enum ServiceRequestType
	{
		GDAR,GDIP;
	}
	
	public ServiceRequest(){}
	
	private ServiceRequest(Document requestDoc) throws Exception 
	{
		XPath xpathSelector;
		
		xpathSelector = DocumentHelper.createXPath("//Company_ID");
		Element company_ID = (Element)xpathSelector.selectSingleNode(requestDoc);
		this.companyID = company_ID.getTextTrim();
		
		xpathSelector = DocumentHelper.createXPath("//Company_Job_Ref");
		Element company_Job_Ref = (Element)xpathSelector.selectSingleNode(requestDoc);
		this.companyJobRef = company_Job_Ref.getTextTrim();
		
		xpathSelector = DocumentHelper.createXPath("//Assessor_ID");
		Element assessor_ID = (Element)xpathSelector.selectSingleNode(requestDoc);
		if(assessor_ID != null)
		{
			this.assessorID = assessor_ID.getTextTrim();	
		}
		
		xpathSelector = DocumentHelper.createXPath("//EPCRRN");
		Element epcRRN = (Element)xpathSelector.selectSingleNode(requestDoc);
		if(epcRRN != null)
		{
			this.epcRrn = epcRRN != null ? epcRRN.getTextTrim() : null;	
		}
		
		xpathSelector = DocumentHelper.createXPath("//OA_RRN");
		Element oaRRN = (Element)xpathSelector.selectSingleNode(requestDoc);
		if(oaRrn != null)
		{
			this.oaRrn = oaRRN != null ? oaRRN.getTextTrim() : null;			
		}
		
		xpathSelector = DocumentHelper.createXPath("//OA_XML");
		Element oaXml = (Element)xpathSelector.selectSingleNode(requestDoc);
		if(oaXml != null)
		{
			this.oaLig = stringToDocument(Base64.base64ToStr(oaXml.getText()));
			if(this.oaLig != null)
			{
				List<XSDErrorVO> errorList = XmlUtil.validateXmlByXsd(GlobalConfig.getInstance().getFSrestfulDir() + "/OA-Schema_v2/OA-Report.xsd", this.oaLig);
				if (errorList != null && errorList.size() > 0){
					String message = "";
					for (XSDErrorVO error : errorList) {
						message += "Line " + error.getLine() + ":" + error.getErrorMsg();
					}
					throw new Exception(message);
				}
			}
			
			if(this.epcRrn == null)
			{
				this.epcRrn = getEpcRrn(this.oaLig);
			}
			if(this.oaRrn == null)
			{
				this.oaRrn = getOaRrn(this.oaLig);
			}
		}
		
		xpathSelector = DocumentHelper.createXPath("//GDIP_XML");
		Element gdipXml = (Element)xpathSelector.selectSingleNode(requestDoc);
		if(gdipXml != null)
		{
			this.gdipLig = stringToDocument(Base64.base64ToStr(gdipXml.getText())); 
			if(this.gdipLig != null)
			{
				List<XSDErrorVO> errorList = XmlUtil.validateXmlByXsd(GlobalConfig.getInstance().getFSrestfulDir() + "/IP-Schema_v2/IP-Report.xsd", this.gdipLig);
				if (errorList != null && errorList.size() > 0){
					String message = "";
					for (XSDErrorVO error : errorList) {
						message += "Line " + error.getLine() + ":" + error.getErrorMsg();
					}
					throw new Exception(message);
				}
			}
		}
	}
	
	public ServiceRequest(Document requestDoc, ServiceRequestType serviceRequestType) throws Exception 
	{
		this(requestDoc);
		if(ServiceRequestType.GDIP.equals(serviceRequestType))
		{
			Element rootElt = this.gdipLig.getRootElement();//获取根节点
			String defNamespace = rootElt.getNamespaceURI();
			XPath xpathSelector;
			Map<String, String> nameSpaceMap = new HashMap<String, String>();
			
			if(defNamespace != null)
			{
				nameSpaceMap.put("defu", defNamespace);
			}
			
			xpathSelector = DocumentHelper.createXPath("//defu:EPC-RRN");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element epc_RRN = (Element)xpathSelector.selectSingleNode(this.gdipLig);
			if(epc_RRN != null)
			{
				this.epcRrn = epc_RRN.getTextTrim();
			}
			
			xpathSelector = DocumentHelper.createXPath("//defu:OA-RRN");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element oa_RRN = (Element)xpathSelector.selectSingleNode(this.gdipLig);
			if(epc_RRN != null)
			{
				this.oaRrn = oa_RRN.getTextTrim();
			}
			
			xpathSelector = DocumentHelper.createXPath("//defu:GDIP-RRN");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element gdip_RRN = (Element)xpathSelector.selectSingleNode(this.gdipLig);
			if(gdip_RRN != null)
			{
				this.gdipRrn = gdip_RRN.getTextTrim();
			}
		}
	}
	
	public ServiceRequest(Document epcLig, Document oaLig)
	{
		this.epcLig = epcLig;
		this.oaLig = oaLig;
	}
	
	private String epcRrn;
	private String oaRrn;
	private String gdipRrn;
	
	private Document epcLig;
	private Document oaLig;
	private Document gdipLig;
	
	private String companyID;
	private String companyJobRef;
	private String assessorID;
	private User user;
	private String requestBody;
	
	public String getEpcRrn(Document oaLig) {
		if(oaLig != null)
		{
			return oaLig.getRootElement().element("Report-Header").element("EPC-RRN").getTextTrim();
		}
		return null;
	}
	public String getEpcRrn() {
		return epcRrn;
	}
	public void setEpcRrn(String epcRrn) {
		this.epcRrn = epcRrn;
	}
	public String getOaRrn(Document oaLig) 
	{
		if(oaLig != null)
		{
			return oaLig.getRootElement().element("Report-Header").element("OA-RRN").getTextTrim();
		}
		return null;
	}
	public String getOaRrn() {
		return oaRrn;
	}
	public void setOaRrn(String oaRrn) {
		this.oaRrn = oaRrn;
	}
	public String getGdipRrn() {
		return gdipRrn;
	}
	public void setGdipRrn(String gdipRrn) {
		this.gdipRrn = gdipRrn;
	}
	public Document getEpcLig() {
		return epcLig;
	}

	public void setEpcLig(Document epcLig) {
		this.epcLig = epcLig;
	}

	public Document getOaLig() {
		return oaLig;
	}

	public void setOaLig(Document oaLig) {
		this.oaLig = oaLig;
	}

	public Document getGdipLig() {
		return gdipLig;
	}

	public void setGdipLig(Document gdipLig) {
		this.gdipLig = gdipLig;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyJobRef() {
		return companyJobRef;
	}
	public void setCompanyJobRef(String companyJobRef) {
		this.companyJobRef = companyJobRef;
	}
	public String getAssessorID() {
		return assessorID;
	}
	public void setAssessorID(String assessorID) {
		this.assessorID = assessorID;
	}
	
	protected Document stringToDocument(String xml) throws DocumentException{
		Document document = null;
		document = DocumentHelper.parseText(xml);
		return document;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}
}
