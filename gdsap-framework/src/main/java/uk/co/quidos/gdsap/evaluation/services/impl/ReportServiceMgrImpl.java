/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.ibatis.session.RowBounds;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import pw.itcircle.javaBeanFactory.factory.JavaBeanFactory;
import uk.co.argylesoftware.webservice.client.WebserviceClient;
import uk.co.quidos.common.util.DateUtil;
import uk.co.quidos.common.util.F;
import uk.co.quidos.common.util.StringUtils;
import uk.co.quidos.common.util.TemplateUtil;
import uk.co.quidos.common.util.time.DateUtils;
import uk.co.quidos.gdsap.evaluation.AppCooking;
import uk.co.quidos.gdsap.evaluation.BillDataComm;
import uk.co.quidos.gdsap.evaluation.BillDataEle;
import uk.co.quidos.gdsap.evaluation.BillDataLPG;
import uk.co.quidos.gdsap.evaluation.BillDataMg;
import uk.co.quidos.gdsap.evaluation.Company;
import uk.co.quidos.gdsap.evaluation.DictItem;
import uk.co.quidos.gdsap.evaluation.EnergyUse;
import uk.co.quidos.gdsap.evaluation.HeatProportion;
import uk.co.quidos.gdsap.evaluation.HeatingPattern;
import uk.co.quidos.gdsap.evaluation.HeatingSystem;
import uk.co.quidos.gdsap.evaluation.Improvement;
import uk.co.quidos.gdsap.evaluation.Occupants;
import uk.co.quidos.gdsap.evaluation.OtherFuel;
import uk.co.quidos.gdsap.evaluation.RelatedPartyDisclosure;
import uk.co.quidos.gdsap.evaluation.Report;
import uk.co.quidos.gdsap.evaluation.Solution;
import uk.co.quidos.gdsap.evaluation.calculate.input.RDSAP;
import uk.co.quidos.gdsap.evaluation.enums.DictType;
import uk.co.quidos.gdsap.evaluation.enums.FuelXmlCode;
import uk.co.quidos.gdsap.evaluation.enums.GreenDealCategoryCode;
import uk.co.quidos.gdsap.evaluation.enums.ImprovementScope;
import uk.co.quidos.gdsap.evaluation.enums.Language;
import uk.co.quidos.gdsap.evaluation.enums.OffPeakType;
import uk.co.quidos.gdsap.evaluation.enums.PropertyType;
import uk.co.quidos.gdsap.evaluation.enums.RecommendationCategoryCode;
import uk.co.quidos.gdsap.evaluation.enums.ReportFileStatus;
import uk.co.quidos.gdsap.evaluation.enums.ReportLocation;
import uk.co.quidos.gdsap.evaluation.enums.ReportStatus;
import uk.co.quidos.gdsap.evaluation.enums.ReportType;
import uk.co.quidos.gdsap.evaluation.enums.RoomType;
import uk.co.quidos.gdsap.evaluation.enums.SolutionType;
import uk.co.quidos.gdsap.evaluation.enums.TariffType;
import uk.co.quidos.gdsap.evaluation.enums.TransactionType;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalAppCookingMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalBillDataCommMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalBillDataEleMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalBillDataLpgMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalBillDataMgMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalEnergyUseMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalEpcEaDetailMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalHeatProportionMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalHeatingPatternMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalHeatingSystemMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalImprovementMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalOccupantsMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalOtherFuelMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalRecommendationMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalReportEpcImprovementResultMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalReportMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapGdpuserReportMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalEnergyUse;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalEpcEaDetail;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalImprovement;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalReport;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapGdpuserReport;
import uk.co.quidos.gdsap.evaluation.services.AppCookingServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.BillDataServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.CalServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.CompanyServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.DictServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.HeatProportionServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.HeatingPatternServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.HeatingSystemServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.LandmarkDefineServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.OccupantsServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.OtherFuelServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.ReportServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.SolutionSeqServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.SolutionServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.object.ConditionVO;
import uk.co.quidos.gdsap.evaluation.services.object.ReportConfig;
import uk.co.quidos.gdsap.evaluation.utils.ClgRestClientUtil;
import uk.co.quidos.gdsap.evaluation.utils.DocumentUtil;
import uk.co.quidos.gdsap.evaluation.utils.NumberUtils;
import uk.co.quidos.gdsap.evaluation.utils.RRNBuilder;
import uk.co.quidos.gdsap.evaluation.utils.UPRNPatcher;
import uk.co.quidos.gdsap.evaluation.wsclient.ResponseObject;
import uk.co.quidos.gdsap.evaluation.wsclient.ResponseStatus;
import uk.co.quidos.gdsap.framework.common.Preference;
import uk.co.quidos.gdsap.framework.common.enums.PreferenceRelId;
import uk.co.quidos.gdsap.framework.common.services.PreferenceServiceMgr;
import uk.co.quidos.gdsap.framework.enums.EpcVersion;
import uk.co.quidos.gdsap.framework.enums.UploadWay;
import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.exception.CalculateException;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;
import uk.co.quidos.gdsap.framework.sys.exception.ServiceException;
import uk.co.quidos.gdsap.framework.sys.lang.enums.EnumUtils;
import uk.co.quidos.gdsap.framework.sysconf.GlobalConfig;
import uk.co.quidos.gdsap.framework.sysconf.GlobalUtils;
import uk.co.quidos.gdsap.framework.user.User;
import uk.co.quidos.gdsap.framework.user.services.UserServiceMgr;
import uk.co.quidos.gdsap.framework.webservice.ServiceRequest;

/**
 * @author peng.shi
 *
 */
@Service("reportServiceMgr")
@Transactional
public class ReportServiceMgrImpl extends AbsBusinessObjectServiceMgr implements ReportServiceMgr
{
	
	@Autowired
	private GdsapEvalReportMapper gdsapEvalReportMapper;
	
	@Autowired
	private GdsapGdpuserReportMapper gdsapGdpuserReportMapper;
	
	@Autowired
	private LandmarkDefineServiceMgr landmarkDefineServiceMgr;
	
	@Autowired
	private GdsapEvalEnergyUseMapper gdsapEvalEnergyUseMapper;
	
	@Autowired
	private GdsapEvalImprovementMapper gdsapEvalImprovementMapper;
	
	@Autowired
	private GdsapEvalHeatProportionMapper gdsapEvalHeatProportionMapper; 
	
	@Autowired
	private UserServiceMgr userServiceMgr;
	
	@Autowired
	private HeatingSystemServiceMgr heatingSystemServiceMgr;
	
	@Autowired
	private AppCookingServiceMgr appCookingServiceMgr;
	
	@Autowired
	private OccupantsServiceMgr occupantsServiceMgr;
	
	@Autowired
	private HeatProportionServiceMgr heatProportionServiceMgr;
	
	@Autowired
	private HeatingPatternServiceMgr heatingPatternServiceMgr;
	
	@Autowired
	private BillDataServiceMgr billDataServiceMgr;
	@Autowired
	private GdsapEvalEpcEaDetailMapper gdsapEvalEpcEaDetailMapper;
	@Autowired
	private SolutionServiceMgr solutionServiceMgr;
	@Autowired
	private CompanyServiceMgr companyServiceMgr;
	
	@Autowired
	private GdsapEvalAppCookingMapper gdsapEvalAppCookingMapper;
	@Autowired
	private GdsapEvalBillDataCommMapper gdsapEvalBillDataCommMapper;
	@Autowired
	private GdsapEvalBillDataMgMapper gdsapEvalBillDataMgMapper;
	@Autowired
	private GdsapEvalBillDataEleMapper gdsapEvalBillDataEleMapper;
	@Autowired
	private GdsapEvalBillDataLpgMapper gdsapEvalBillDataLpgMapper;
	@Autowired
	private GdsapEvalHeatingSystemMapper gdsapEvalHeatingSystemMapper;
	@Autowired
	private GdsapEvalOccupantsMapper gdsapEvalOccupantsMapper;
	@Autowired
	private GdsapEvalOtherFuelMapper gdsapEvalOtherFuelMapper;
	@Autowired
	private GdsapEvalHeatingPatternMapper gdsapEvalHeatingPatternMapper;
	@Autowired
	private CalServiceMgr calServiceMgr;
	@Autowired
	private DictServiceMgr dictServiceMgr;
	@Autowired
	private OccupantsServiceMgr occupantsMgr;
	@Autowired
	private HeatingSystemServiceMgr heatingSystemMgr;
	@Autowired
	private OtherFuelServiceMgr otherFuelServiceMgr;
	@Autowired
	private GdsapEvalReportEpcImprovementResultMapper gdsapEvalReportEpcImprovementResultMapper;
	@Autowired
	private PreferenceServiceMgr preferenceServiceMgr;
	
	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.ReportServiceMgr#addReport(org.dom4j.Document)
	 */
	@Override
	public Report addReport(Document doc,String dbfileName,User user, Report temReport)  throws ObjectDuplicateException
	{
		    Element rootElt = doc.getRootElement();//获取根节点
		    //Report开始
		    temReport.setEpcVersion(EpcVersion.EPC_OLD);
		    if(ReportConfig.isExist(ReportConfig.EAWRootElements, rootElt.getName()))//英格兰 991
		    {
		    	return saveEAW_v991(doc,dbfileName,user,temReport);
		    }
		    else if(ReportConfig.isExist(ReportConfig.SCTRootElements, rootElt.getName()))//苏格兰 991
		    {
		    	return saveSCT_v991(doc,dbfileName,user,temReport);
		    }
		    else if(ReportConfig.isExist(ReportConfig.NewEpcRootElements, rootElt.getName()))//针对EPC 17.0新格式解析
		    {
		    	temReport.setEpcVersion(EpcVersion.EPC_NEW);
    	    	if(rootElt.element("Report-Header") != null && rootElt.element("Report-Header").element("Country-Code") != null)
    	    	{
    	    		if(rootElt.element("SAP-Version") != null && rootElt.element("SAP-Version").getTextTrim().equals("9.91"))
    	    		{
    	    			temReport.setEpcVersion(EpcVersion.EPC_OLD);
    	    		}
    	    		
    	    		if("SCT".equals(rootElt.element("Report-Header").element("Country-Code").getTextTrim()))
    	    		{
    	    			return saveSCT_v992(doc,dbfileName,user,temReport);
    	    		}
    	    		else
    	    		{
    	    			return saveEAW_v992(doc,dbfileName,user,temReport);
    	    		}
    	    	}
	    	    throw new IllegalArgumentException();
		    }
		    throw new IllegalArgumentException();
	}
	
	@Override
	public Report addReport(User user,Report report) throws ObjectDuplicateException
	{
		GdsapGdpuserReport model = BeanUtils.toGdsapGdpuserReport(user, report);
		try {
			this.gdsapGdpuserReportMapper.insert(model);
		} catch (DuplicateKeyException e) {
			throw new ObjectDuplicateException();
		}
		return report;
	}
	
	@Override
	public boolean lodgeReport(ServiceRequest serviceRequest, UploadWay uploadWay) throws ServiceException 
	{
		if(addReport(serviceRequest, uploadWay))
		{
			GdsapEvalReport gdsapReport = gdsapEvalReportMapper.findByCompany_companyJobRef(serviceRequest.getCompanyID(), serviceRequest.getCompanyJobRef());
			if(gdsapReport == null)
			{
				throw new ServiceException("System Error.");
			}
			Report report = this.getReport(gdsapReport.getId());
		
			ResponseObject ro = null;
			try {
				String xmlContent = "";
				String path = GlobalConfig.getInstance().getFSDir() + report.getLigXmlFile();
				xmlContent = FileUtils.readFileToString(new File(path),"utf-8");
				
				ro = ClgRestClientUtil.post(GlobalConfig.getInstance().getEAWLodgementAddress()	,
						report.getUser().getAuthUsername() + ":" + report.getUser().getAuthPassword(),
						GlobalConfig.getInstance().getEAWCert(),GlobalConfig.getInstance().getEAWCertPassword(),xmlContent);
			} catch (Exception e) {
				e.printStackTrace();
				this.deleteReport(gdsapReport.getId());
				throw new ServiceException(e.getMessage());
			}
			
			if (ro.getResponseStatus().equals(ResponseStatus.Success)) 
			{
				report.setLodgeDate(new Date());
				this.updateReportStatus(report, ReportStatus.Lodged);
				System.out.println("******************************** updateReportStatus finish " + DateUtil.date2String(new Date(), "yyyy-MM-dd hh:mm:ss"));
			}
			else
			{
				this.deleteReport(gdsapReport.getId());
				throw new ServiceException(ro.getResponseMessage().getMessage());
			}
			
			return true;
		}
		return false;
	}
	
	@Override
	public boolean addReport(ServiceRequest serviceRequest, UploadWay uploadWay) throws ServiceException 
	{
		boolean success = false;
		if(UploadWay.GDP_Retrieval_From_Repo.equals(uploadWay))
		{
			Report report = null;
			try {
				Report tempReport = new Report();
				report = this.addReport(tempReport, serviceRequest.getUser(), uploadWay, serviceRequest.getEpcLig().asXML(), serviceRequest.getOaLig().asXML(), null);
				report.setBuilderOaRrn(false);
				report.setOaRrn(serviceRequest.getOaRrn());
				//GDP用户通过OA_RRN检索报告，让oaRrn不重新生成
				this.updateBaseReportInfo(report);
				this.updateGdipRrn(report);
				this.updateReportStatus(report, ReportStatus.GDIP_In_Process);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException(e.getMessage());
			}
			if(report != null)
			{
				return true;
			}
		}
		else if(UploadWay.Third_Party_Web_Service_pushOAToQuidos.equals(uploadWay))
		{
			String companyId = serviceRequest.getCompanyID();
			String companyJobRef = serviceRequest.getCompanyJobRef();
			String assessorID = serviceRequest.getAssessorID();
			String epcRrn = serviceRequest.getEpcRrn();
			if(epcRrn == null || epcRrn.trim().equals(""))
			{
				throw new ServiceException("EPCRRN is required");
			}
			Document oaLig = serviceRequest.getOaLig();
			if(oaLig == null)
			{
				throw new ServiceException("OA_XML is required");
			}
			User asserssor = this.userServiceMgr.getUserByUsername(assessorID);
			if(asserssor == null)
			{
				throw new ServiceException("Assessor_ID is invalid");
			}
			Company company = companyServiceMgr.getCompany(companyId);
			if(company == null)
			{
				throw new ServiceException("Company_ID is invalid");
			}
			GdsapEvalReport gdsapReport = gdsapEvalReportMapper.findByCompany_companyJobRef(company.getCompanyId(), companyJobRef);
			if(gdsapReport != null)
			{
				throw new ServiceException("A job with this Job reference has already been created.");
			}
			String epcXml = null;
			try {
				epcXml = WebserviceClient.getInstance().getEPCXml(epcRrn,asserssor.getAuthUsername(),asserssor.getAuthPassword());
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("can not find the EPC xml which RRN is " + epcRrn);
			}
			Report report = null;
			try {
				Report tempReport = new Report();
				tempReport.setCompany(company);
				tempReport.setCompanyJobRef(companyJobRef);
				report = this.addReport(tempReport, asserssor, uploadWay, epcXml, oaLig.asXML(), null);
				this.updateBaseReportInfo(report);
			} catch (Exception e) {
				throw new ServiceException(e.getMessage());
			}
			
			if(report != null)
			{
				return true;
			}
		}
		else if(UploadWay.Third_Party_Web_Service_pushGDIPToQuidos.equals(uploadWay))
		{

			String companyId = serviceRequest.getCompanyID();
			String companyJobRef = serviceRequest.getCompanyJobRef();
			String assessorID = serviceRequest.getAssessorID();
			String oaRrn = serviceRequest.getOaRrn();
			if(oaRrn == null || oaRrn.trim().equals(""))
			{
				throw new ServiceException("OARRN is required");
			}
			Document gdipLig = serviceRequest.getGdipLig();
			if(gdipLig == null)
			{
				throw new ServiceException("GDIP XML is required");
			}
			User asserssor = this.userServiceMgr.getUserByUsername(assessorID);
			if(asserssor == null)
			{
				throw new ServiceException("Assessor_ID is invalid");
			}
			Company company = companyServiceMgr.getCompany(companyId);
			if(company == null)
			{
				throw new ServiceException("Company_ID is invalid");
			}
			GdsapEvalReport gdsapReport = gdsapEvalReportMapper.findByCompany_companyJobRef(company.getCompanyId(), companyJobRef);
			if(gdsapReport != null)
			{
				throw new ServiceException("A job with this Job reference has already been created.");
			}
			String epcXml = null;
			try {
				epcXml = WebserviceClient.getInstance().getEPCXml(serviceRequest.getEpcRrn(),asserssor.getAuthUsername(),asserssor.getAuthPassword());
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("can not find the EPC xml which RRN is " + serviceRequest.getEpcRrn());
			}
			
			String oaXml = null;
			try {
				oaXml = WebserviceClient.getInstance().getOAXml(oaRrn,asserssor.getAuthUsername(),asserssor.getAuthPassword());
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("can not find the OA Lig xml which OA RRN is " + oaRrn);
			}
			Report report = null;
			try {
				Report tempReport = new Report();
				tempReport.setCompany(company);
				tempReport.setCompanyJobRef(companyJobRef);
				report = this.addReport(tempReport, asserssor, uploadWay, epcXml, oaXml, gdipLig.asXML());
				this.updateBaseReportInfo(report);
			} catch (Exception e) {
				throw new ServiceException(e.getMessage());
			}
			
			if(report != null)
			{
				return true;
			}
		
		}
		else if(UploadWay.Third_Party_Web_Service_lodgeOADirectlyToLandmark.equals(uploadWay))
		{
			try {
				serviceRequest = reWriteOaLig(serviceRequest);
			} catch (DocumentException e1) {
				e1.printStackTrace();
				throw new ServiceException("System Error.");
			}
			
			String companyId = serviceRequest.getCompanyID();
			String companyJobRef = serviceRequest.getCompanyJobRef();
			String assessorID = serviceRequest.getAssessorID();
			String epcRrn = serviceRequest.getEpcRrn();
			if(epcRrn == null || epcRrn.trim().equals(""))
			{
				throw new ServiceException("EPCRRN is required");
			}
			Document oaLig = serviceRequest.getOaLig();
			if(oaLig == null)
			{
				throw new ServiceException("OA_XML is required");
			}
			
			User asserssor = this.userServiceMgr.getUserByUsername(assessorID);
			if(asserssor == null)
			{
				throw new ServiceException("Assessor_ID is invalid");
			}
			Company company = companyServiceMgr.getCompany(companyId);
			if(company == null)
			{
				throw new ServiceException("Company_ID is invalid");
			}
			GdsapEvalReport gdsapReport = gdsapEvalReportMapper.findByCompany_companyJobRef(company.getCompanyId(), companyJobRef);
			if(gdsapReport != null)
			{
				throw new ServiceException("A job with this Job reference has already been created.");
			}
			String epcXml = null;
			try {
				epcXml = WebserviceClient.getInstance().getEPCXml(epcRrn,asserssor.getAuthUsername(),asserssor.getAuthPassword());
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("can not find the EPC xml which RRN is " + epcRrn);
			}
			Report report = null;
			try {
				Report tempReport = new Report();
				tempReport.setCompany(company);
				tempReport.setCompanyJobRef(companyJobRef);
				report = this.addReport(tempReport, asserssor, uploadWay, epcXml, oaLig.asXML(), null);
				report.setBuilderOaRrn(false);//不重新生成OA RRN
				report.setOaRrn(serviceRequest.getOaRrn());
				this.updateOARrn(report);
				this.updateBaseReportInfo(report);
			} catch (Exception e) {
				throw new ServiceException(e.getMessage());
			}
			
			if(report != null)
			{
				return true;
			}
		}
		return success;
	}
	
	/**
	 * 第三方公司通过webservice lodge OA lig, 需要对传过来的 oa Lig部分节点做处理
	 * @param serviceRequest
	 * @throws DocumentException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ServiceRequest reWriteOaLig(ServiceRequest serviceRequest) throws DocumentException
	{
		Document originalLig = serviceRequest.getOaLig();
		
		Map<String, Object> datas = new LinkedHashMap<String, Object>();
		if(serviceRequest.getUser() != null)
		{
			datas.put("user", serviceRequest.getUser());
		}
		else
		{
			datas.put("user", this.userServiceMgr.getUserByUsername(serviceRequest.getAssessorID()));
		}
		
		Preference preference = preferenceServiceMgr.getPreference(PreferenceRelId.Replace_Part_GDAR_LODGE_XML_TEMP);
		Assert.notNull(preference);
		String template = preference.getContent();
		Assert.hasText(template);
		String partGdarLig = TemplateUtil.template2String(template, datas);
		Document partDoc = DocumentUtil.stringToDocument(partGdarLig);
		//替换Green-Deal-Advisor节点的内容
		Element ogdaE = originalLig.getRootElement().element("Report-Header").element("Green-Deal-Advisor");
		Element rgdaE = partDoc.getRootElement().element("Green-Deal-Advisor");
		List<Element> ogdaContent = ogdaE.getParent().content();
		ogdaContent.set(ogdaContent.indexOf(ogdaE),rgdaE);  
		//替换Green-Deal-Advisor节点的内容
		Element oidE = originalLig.getRootElement().element("Insurance-Details");
		Element ridE = partDoc.getRootElement().element("Insurance-Details");
		List oidContent = oidE.getParent().content();
		oidContent.set(oidContent.indexOf(oidE),ridE);  
		
		String originalLigXml = originalLig.asXML().replaceAll("Green-Deal-Advisor xmlns=\"\"", "Green-Deal-Advisor").replaceAll("Insurance-Details xmlns=\"\"", "Insurance-Details");
		serviceRequest.setOaLig(DocumentUtil.stringToDocument(originalLigXml));
		return serviceRequest;
	}
	
	private Report addReport(Report tempReport, User user, UploadWay way, String rdsapLig, String oaLig, String gdipLig) throws Exception
	{
		String newFilename = UUID.randomUUID().toString().replace("-", "") + ".xml";
		String datedir = GlobalUtils.dateDir(new Date());
		String newfilePath = GlobalConfig.getInstance().getFSDir() + datedir;
		if (!new File(newfilePath).exists())
		{
			new File(newfilePath).mkdirs();
		}
		String newFullFilepath = newfilePath + newFilename;
		String newdbFilename = datedir + newFilename;
		
		Document doc = DocumentUtil.readDocument(rdsapLig);
        DocumentUtil.writeDocToXMLFile(doc, newFullFilepath, true);//写入文件
        
        Document oaLigDoc = DocumentUtil.readDocument(oaLig);
        Report temReport = this.getReportByOaLig(oaLigDoc);
        temReport.setUploadWay(way);
        temReport.setCompany(tempReport.getCompany());
        temReport.setCompanyJobRef(tempReport.getCompanyJobRef());
        
        Report report = null;
        report = this.addReport(doc, newdbFilename, user, temReport);
        if(report != null)
        {
        	report = this.fillDateForReport(report, doc, oaLigDoc, null);
        }
        
        //保存检索到的oa Lig
        String oaLigNewFilename = UUID.randomUUID().toString().replace("-", "") + ".xml";
		String datedir2 = GlobalUtils.dateDir(new Date());
		String newfilePath2 = GlobalConfig.getInstance().getFSDir() + datedir2;
		if (!new File(newfilePath2).exists())
		{
			new File(newfilePath2).mkdirs();
		}
		String newFullFilepath2 = newfilePath2 + oaLigNewFilename;
		String newdbFilename2 = datedir + oaLigNewFilename;
        DocumentUtil.writeDocToXMLFile(oaLigDoc, newFullFilepath2, true);//写入文件
        report.setLigXmlFile(newdbFilename2);
		return report;
	}
	
	private Report fillDateForReport(Report report,Document rdsapLigDoc, Document oaLigDoc, Document gdipLigDoc) throws CalculateException, Exception
	{
		Assert.notNull(report);
		Assert.notNull(oaLigDoc);
		
		Element rootElt = oaLigDoc.getRootElement();
		String defNamespace = rootElt.getNamespaceURI();
		XPath xpathSelector;
		Map<String, String> nameSpaceMap = new HashMap<String, String>();
		if(defNamespace != null)
		{
			nameSpaceMap.put("defu", defNamespace);
		}
		
		xpathSelector = DocumentHelper.createXPath("//defu:Unoccupied-Property");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element unoccupied_Property = (Element)xpathSelector.selectSingleNode(oaLigDoc);
		if(unoccupied_Property != null && unoccupied_Property.getTextTrim().equals("true"))
		{
			report.setUnoccupiedPropertyable(YesNo.Yes);
			this.gdsapEvalReportMapper.updateUnoccupiedPropertyable(YesNo.Yes.getCode(), report.getId());
		}
		
		if(report.getUnoccupiedPropertyable() != null && report.getUnoccupiedPropertyable().equals(YesNo.Yes))//空置房产的情况
		{
			EpcVersion version = report.getEpcVersion();
			report.setPrepareCalunoccupiedProperty(true);
			String xml = calServiceMgr.cal_ReturnResult(report, null, null);
			report.setPrepareCalunoccupiedProperty(false);
			SAXReader reader = new SAXReader();
			Document document;
			Map<String,Object> mapTem = getTems(rdsapLigDoc, version);
			
			try
			{
				document = reader.read(new StringReader(xml));
				
				//Occupants
				RDSAP rdsap = null;
				try {
					rdsap = (RDSAP)JavaBeanFactory.newInstance().createObject(document, RDSAP.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				double TFA = rdsap.getStandNumOfOcc();
				
				Occupants occupants = new Occupants();
				occupants.setReport(report);
				occupants.setOccupantsNumber(NumberUtils.doubleHalfUp(TFA));
				occupants.setShowerType(dictServiceMgr.getDictItem(1006));
				occupants.setBathsPerable(YesNo.No);
				occupants.setShowersPerable(YesNo.No);
				occupantsMgr.addOccupants(occupants);
				
				//HeatingSystem
				HeatingSystem heatSystem = new HeatingSystem();
				heatSystem.setReport(report);
				heatSystem.setTemperature(0);
				heatSystem.setKnownable(YesNo.No);
				heatSystem.setmHs(dictServiceMgr.getDictItem(1008));
				heatSystem.setSmHs(dictServiceMgr.getDictItem(1056));
				Integer[] temInteger = (Integer[]) mapTem.get("s1");
				Integer heatedRoomCount = temInteger[0] != null ? temInteger[0] : 0 , HabitableRoomCount = temInteger[1] != null ? temInteger[1] : 0, sAPMainHeatingCode = temInteger[2] != null ? temInteger[2] : 0;
				String[] getTem = (String[]) mapTem.get("s");
				if(getTem[1] == null || getTem[1].equals(""))
				{
					if((heatedRoomCount < HabitableRoomCount) || (sAPMainHeatingCode == 401) || (sAPMainHeatingCode == 402) || (sAPMainHeatingCode == 404) || (sAPMainHeatingCode == 409) || (sAPMainHeatingCode == 421))
					{
						heatSystem.setsHs(dictServiceMgr.getDictItem(1060));
						heatSystem.setsHf(dictServiceMgr.getDictItem(136));
						heatSystem.setsHt(dictServiceMgr.getDictItem(238));
					}
					else
					{
						heatSystem.setsHs(dictServiceMgr.getDictItem(1059));
					}
				}
				else
				{
					heatSystem.setsHs(dictServiceMgr.getDictItem(1059));
				}
				heatingSystemMgr.addHeatingSystem(heatSystem);
				
				//HeatProportion
				List<HeatProportion> listHp = heatProportionServiceMgr.getHeatProportions(report.getId());
				if(!CollectionUtils.isEmpty(listHp))
				{
					String[] tem = (String[]) mapTem.get("s");
					String[] tem2 = (String[]) mapTem.get("s2");
					if(!tem[0].equals("1"))
					{
						if(tem2[0].equals("0") && tem2[1].equals("902"))
						{
							for(int i = 0 ; i < listHp.size() ; i++)
							{
								HeatProportion hp = listHp.get(i);
								hp.setMain1(YesNo.Yes);
								hp.setMain2(YesNo.No);
								hp.setSecondary(YesNo.No);
								hp.setHeatedPartially(YesNo.No);
								hp.setNotable(YesNo.No);
								while(i == 0)
								{
									if(!tem[1].equals("") && tem[1] != null)
									{
										hp.setSecondary(YesNo.Yes);
									}
									break;
								}
								heatProportionServiceMgr.updateHeatProportion(hp);
							}
						}
						else
						{
							for(int i = 0 ; i < listHp.size() ; i++)
							{
								HeatProportion hp = listHp.get(i);
								if(i % 2==0)
								{
									hp.setMain1(YesNo.Yes);
									hp.setMain2(YesNo.No);
									hp.setSecondary(YesNo.No);
									hp.setHeatedPartially(YesNo.No);
									hp.setNotable(YesNo.No);
								}
								else
								{
									hp.setMain1(YesNo.No);
									hp.setMain2(YesNo.Yes);
									hp.setSecondary(YesNo.No);
									hp.setHeatedPartially(YesNo.No);
									hp.setNotable(YesNo.No);
								}
								while(i == 0)
								{
									if((!tem[1].equals("") && tem[1] != null) && (heatedRoomCount == HabitableRoomCount) && (sAPMainHeatingCode != 401) && (sAPMainHeatingCode != 402) && (sAPMainHeatingCode != 404) && (sAPMainHeatingCode != 421))
									{
										hp.setSecondary(YesNo.Yes);
									}
									break;
								}
								heatProportionServiceMgr.updateHeatProportion(hp);
							}
						}
						
					}
					else
					{
						for(int i = 0 ; i < listHp.size() ; i++)
						{
							HeatProportion hp = listHp.get(i);
							hp.setMain1(YesNo.Yes);
							hp.setMain2(YesNo.No);
							hp.setSecondary(YesNo.No);
							hp.setHeatedPartially(YesNo.No);
							hp.setNotable(YesNo.No);
							while(i == 0)
							{
								if(!tem[1].equals("") && tem[1] != null)
								{
									hp.setSecondary(YesNo.Yes);
								}
								break;
							}
							heatProportionServiceMgr.updateHeatProportion(hp);
						}
					}
					
				}
				
				//HeatingPattern
				HeatingPattern heatingPatterns = new HeatingPattern();
				heatingPatterns.setReport(report);
				heatingPatterns.setA1Off("23:00");
				heatingPatterns.setA1On("07:00");
				heatingPatterns.setA2Off("");
				heatingPatterns.setA2On("");
				heatingPatterns.setA3Off("");
				heatingPatterns.setA3On("");
				heatingPatterns.setA4Off("");
				heatingPatterns.setA4On("");
				heatingPatterns.setN1Off("09:00");
				heatingPatterns.setN1On("07:00");
				heatingPatterns.setN2Off("23:00");
				heatingPatterns.setN2On("16:00");
				heatingPatterns.setN3Off("");
				heatingPatterns.setN3On("");
				heatingPatterns.setN4Off("");
				heatingPatterns.setN4On("");
				heatingPatterns.setDays(2);
				heatingPatternServiceMgr.addHeatingPattern(heatingPatterns);
				
				//AppCooking
				AppCooking ac = new AppCooking();
				ac.setReport(report);
				ac.setDryProportion(25);
				ac.setFridgeFreezersNumber(0);
				ac.setFridgesNumber(1);
				ac.setFreezersNumber(1);
				ac.setCookingFuel(dictServiceMgr.getDictItem(1038));
//				boolean tem = rdsap.getMaingasAvilable();
//				if(tem)
//				{
//					ac.setCookingFuel(dictServiceMgr.getDictItem(1037));
//				}
//				else
//				{
//					ac.setCookingFuel(dictServiceMgr.getDictItem(1038));
//				}
				ac.setCookerType(dictServiceMgr.getDictItem(1015));
				ac.setDryingClothesSpacable(YesNo.No);
				appCookingServiceMgr.addAppCooking(ac);
				
				//BillData
				TariffType tariffType = calServiceMgr.EleTariffType(report);//判断高低电价或24小时电价
				BillDataEle bd = new BillDataEle();
				bd.setReport(report);
				if(tariffType.name().equals("Standard"))
				{
					bd.setEtElectricityTariff(dictServiceMgr.getDictItem(1027));
					bd.setEtStReliablityLevel(dictServiceMgr.getDictItem(1031));
				}
				if(tariffType.name().equals("Hour_24"))
				{
					bd.setEtElectricityTariff(dictServiceMgr.getDictItem(1068));
					bd.setEt24ReliablityLevel(dictServiceMgr.getDictItem(1031));
				}
				if(tariffType.name().equals("Off_peak_7") || tariffType.name().equals("Off_peak_10"))
				{
					bd.setEtElectricityTariff(dictServiceMgr.getDictItem(1028));
					bd.setEtOffHReliablityLevel(dictServiceMgr.getDictItem(1031));
					bd.setEtOffLReliablityLevel(dictServiceMgr.getDictItem(1031));
				}
				
				billDataServiceMgr.addBillDataEle(bd);
				
				HeatingSystem heatingSystem = heatingSystemMgr.getHeatingSystem(report.getId());
				AppCooking appCooking = appCookingServiceMgr.getAppCooking(report.getId());
	        	int[] fuelCodes = new int[5]; //燃料的xmlCode数组,用于判断是否为其他燃料
	        	if(heatingSystem != null){
					int mHs = (int)heatingSystem.getmHs().getCalCode();
		        	int smHs = (int)heatingSystem.getSmHs().getCalCode();
		        	int sHs = (int)heatingSystem.getsHs().getCalCode();
		        	if(mHs == 1){//表示从xml里取值
		        		fuelCodes[0] = (report.getRdsapMhsFuel() != null ? report.getRdsapMhsFuel() : 0);
		        	}else{
		        		fuelCodes[0] = (heatingSystem.getmHf() != null ? (int)heatingSystem.getmHf().getCalCode() : 0);
		        	}
		        	
		        	if(smHs == 1 || smHs == 0){
		        		fuelCodes[1] = (report.getRdsapSmhsFuel() != null ? report.getRdsapSmhsFuel() : 0);
		        	}else{
		        		fuelCodes[1] = (heatingSystem.getSmHf() != null ? (int)heatingSystem.getSmHf().getCalCode() : 0);
		        	}
		        	
		        	if(sHs == 1 || sHs == 0){
		        		fuelCodes[2] = (report.getRdsapShsFuel() != null ? report.getRdsapShsFuel() : 0);
		        	}else{
		        		fuelCodes[2] = (heatingSystem.getsHf() != null ? (int)heatingSystem.getsHf().getCalCode() : 0);
		        	}
				}else{
					fuelCodes[0] = 0;fuelCodes[1] = 0;fuelCodes[2] = 0;
				}
	        	fuelCodes[3] = (report.getRdsapWhsFuel() != null ? report.getRdsapWhsFuel() : 0);
	        	if(appCooking != null && heatingSystem != null){
	        		fuelCodes[4] = FuelXmlCode.getAppCookFuelType(report,heatingSystem,(int)appCooking.getCookingFuel().getCalCode());
	        	}else{
	        		fuelCodes[4] = 0;
	        	}
	        	
	        	
	        	int[] removed = FuelXmlCode.removeRepeat(fuelCodes);//判断数组重复数据fuelCodes,removed为删除重复后的数组
	        	
	        	Integer[] arryOtherFuel = FuelXmlCode.getOtherFuels(removed);//arryOtherFuel为筛选出的OtherFuel的code数组
	        	if(arryOtherFuel != null && arryOtherFuel.length > 0){
	        		if (ArrayUtils.contains(arryOtherFuel, 9) && !ArrayUtils.contains(arryOtherFuel, 6))
	        		{
	        			List<Integer> tempList = FuelXmlCode.changeInteger(arryOtherFuel);
	        			tempList.add(6);
	        			arryOtherFuel = FuelXmlCode.changeList(tempList);
	        			for(int i = 0 ; i < arryOtherFuel.length ; i++){
	        				OtherFuel otherFuel = new OtherFuel();
	        				FuelXmlCode fuelXmlCode = (FuelXmlCode)EnumUtils.getByCode(arryOtherFuel[i], FuelXmlCode.class);
	        				otherFuel.setReport(report);
	        				otherFuel.setFuelCode(fuelXmlCode.getCode());
	        				otherFuel.setReliablityLevel(dictServiceMgr.getDictItem(1067));
	        				otherFuelServiceMgr.addOtherFuel(otherFuel);
	        			}
	        		}
	        		if(arryOtherFuel != null && arryOtherFuel.length > 0){
	        			for(int i = 0 ; i < arryOtherFuel.length ; i++){
	        				OtherFuel otherFuel = new OtherFuel();
	        				FuelXmlCode fuelXmlCode = (FuelXmlCode)EnumUtils.getByCode(arryOtherFuel[i], FuelXmlCode.class);
	        				otherFuel.setReport(report);
	        				otherFuel.setFuelCode(fuelXmlCode.getCode());
	        				otherFuel.setReliablityLevel(dictServiceMgr.getDictItem(1067));
	        				otherFuelServiceMgr.addOtherFuel(otherFuel);
	        			}
	        	}
	        	
	        	Integer[] arryMains_Gas = FuelXmlCode.getMains_Gas(removed);//arryMains_Gas为筛选出的Mains_Gas的code数组
	        	if(arryMains_Gas != null && arryMains_Gas.length > 0)
	        	{
	        		for(int i = 0 ; i < arryMains_Gas.length ; i++)
	        		{
	        			BillDataMg bdMG = new BillDataMg();
	        			bdMG.setReport(report);
	        			bdMG.setMgReliablityLevel(dictServiceMgr.getDictItem(1031));
	        			billDataServiceMgr.addBillDataMg(bdMG);
	        		}
	        	}
	        	
	        	Integer[] arryCommunity_heating = FuelXmlCode.getCommunity_heating(removed);//arryCommunity_heating为筛选出的Community_heating的code数组
	        	if(arryCommunity_heating != null && arryCommunity_heating.length > 0){
	        		for(int i = 0 ; i < arryCommunity_heating.length ; i++){
	        			FuelXmlCode fuelXmlCode = (FuelXmlCode)EnumUtils.getByCode(arryCommunity_heating[i], FuelXmlCode.class);
	        			BillDataComm bdComm = new BillDataComm();
	        			bdComm.setReport(report);
	        			bdComm.setChFuelCode(fuelXmlCode.getCode());
	        			bdComm.setChReliablityLevel(dictServiceMgr.getDictItem(1065));
	        			billDataServiceMgr.addBillDataComm(bdComm);
	            	}
	        	}
	        	
	        	Integer[] arryLPG_subject = FuelXmlCode.getLPGsubject(removed);
	        	if(arryLPG_subject != null && arryLPG_subject.length > 0){
	        		for(int i = 0 ; i < arryLPG_subject.length ; i++){
	        			BillDataLPG bdLPG = new BillDataLPG();
	        			bdLPG.setReport(report);
	        			bdLPG.setLpgReliablityLevel(dictServiceMgr.getDictItem(1065));
	        			billDataServiceMgr.addBillDataLPG(bdLPG);
	            	}
	        	}
	        	
	        	}
			}
			catch (DocumentException e)
			{
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		else
		{
			// Occupants
			xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Number-Of-Occupants");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element number_Of_Occupants = (Element)xpathSelector.selectSingleNode(oaLigDoc);
			if(number_Of_Occupants != null)
			{
				Occupants occupants = new Occupants();
				occupants.setReport(report);
				occupants.setOccupantsNumber(Integer.parseInt(number_Of_Occupants.getTextTrim()));
				
				xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Baths-And-Showers//defu:Shower-Type");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element shower_Type = (Element)xpathSelector.selectSingleNode(oaLigDoc);
				if(shower_Type != null)
				{
					occupants.setShowerType(dictServiceMgr.getDictItem(DictType.OSB_SHOWER_TYPE, shower_Type.getTextTrim()));
				}
				
				
				if(occupants.getShowerType().getLodgeCode().equals("1"))
				{
					occupants.setShowersPerable(YesNo.No);
					occupants.setShowersPerDay(0);
				}
				else
				{
					xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Baths-And-Showers//defu:Showers-Per-Day");
					xpathSelector.setNamespaceURIs(nameSpaceMap);
					Element showers_Per_Day = (Element)xpathSelector.selectSingleNode(oaLigDoc);
					if(showers_Per_Day != null)
					{
						if(showers_Per_Day.getTextTrim().equals("-1"))
						{
							occupants.setShowersPerable(YesNo.No);
							occupants.setShowersPerDay(0);
						}
						else
						{
							occupants.setShowersPerable(YesNo.Yes);
							occupants.setShowersPerDay(Float.parseFloat(showers_Per_Day.getTextTrim()));
						}
					}
				}
				
				xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Baths-And-Showers//defu:Baths-Per-Day");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element baths_Per_Day = (Element)xpathSelector.selectSingleNode(oaLigDoc);
				if(baths_Per_Day != null)
				{
					if(baths_Per_Day.getTextTrim().equals("-1"))
					{
						occupants.setBathsPerable(YesNo.No);
						occupants.setBathsPerDay(0);
					}
					else
					{
						occupants.setBathsPerable(YesNo.Yes);
						occupants.setBathsPerDay(Float.parseFloat(baths_Per_Day.getTextTrim()));
					}
				}
				occupantsMgr.addOccupants(occupants);
			}
			
			//HeatingSystem
			HeatingSystem heatSystem = new HeatingSystem();
			heatSystem.setReport(report);
			xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Living-Room-Temperature");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element living_Room_Temperature = (Element)xpathSelector.selectSingleNode(oaLigDoc);
			if(living_Room_Temperature != null)
			{
				heatSystem.setKnownable(YesNo.Yes);
				heatSystem.setTemperature(Float.parseFloat(living_Room_Temperature.getTextTrim()));
			}
			
			xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Main-Heating-1//defu:Heater-Selection");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element heater_Selection = (Element)xpathSelector.selectSingleNode(oaLigDoc);
			if(heater_Selection != null)
			{
				if(heater_Selection.getTextTrim().equals("0"))
				{
					heatSystem.setmHs(dictServiceMgr.getDictItem(DictType.MAIHS_DEF, "0"));
				}
				else
				{
					heatSystem.setmHs(dictServiceMgr.getDictItem(DictType.MAIHS_DEF, "1"));
				}
			}
			
			xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Main-Heating-2//defu:Heater-Selection");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element heater_Selection2 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
			if(heater_Selection2 != null)
			{
				if(heater_Selection2.getTextTrim().equals("0"))
				{
					heatSystem.setSmHs(dictServiceMgr.getDictItem(DictType.SMHS_TYPE, "1"));
				}
				else if(heater_Selection2.getTextTrim().equals("1"))
				{
					heatSystem.setSmHs(dictServiceMgr.getDictItem(DictType.SMHS_TYPE, "0"));
				}
				else
				{
					heatSystem.setSmHs(dictServiceMgr.getDictItem(DictType.SMHS_TYPE, "2"));
				}
			}

			xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Secondary-Heating//defu:Heater-Selection");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element heater_Selection3 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
			if(heater_Selection3 != null)
			{
				if(heater_Selection3.getTextTrim().equals("0"))
				{
					heatSystem.setsHs(dictServiceMgr.getDictItem(DictType.SHS_TYPE, "1"));
				}
				else if(heater_Selection3.getTextTrim().equals("1"))
				{
					heatSystem.setsHs(dictServiceMgr.getDictItem(DictType.SHS_TYPE, "0"));
				}
				else
				{
					heatSystem.setsHs(dictServiceMgr.getDictItem(DictType.SHS_TYPE, "3"));
				}
			}
			
			xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Main-Heating-1//defu:Heating-Fuel");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element heating_Fuel = (Element)xpathSelector.selectSingleNode(oaLigDoc);
			if(heating_Fuel != null && !heating_Fuel.getTextTrim().equals("0"))
			{
				heatSystem.setmHf(dictServiceMgr.getDictItem(DictType.MAIHS_HT_2, heating_Fuel.getTextTrim()));
			}
			
			xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Main-Heating-1//defu:Heating-Type");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element heating_Type = (Element)xpathSelector.selectSingleNode(oaLigDoc);
            if (heating_Type != null && !heating_Type.getTextTrim().equals("0") && heatSystem.getmHf() != null)
            {
                if (heatSystem.getmHf().getCalCode() == 26 || heatSystem.getmHf().getCalCode() == 27 || heatSystem.getmHf().getCalCode() == 3 || heatSystem.getmHf().getCalCode() == 17)
                {
                    heatSystem.setmHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type.getTextTrim()));
                }

                if (heatSystem.getmHf().getCalCode() == 28)
                {
                    heatSystem.setmHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type.getTextTrim()));
                }

                if (heatSystem.getmHf().getCalCode() == 33 || heatSystem.getmHf().getCalCode() == 15 || heatSystem.getmHf().getCalCode() == 6 || heatSystem.getmHf().getCalCode() == 9 || heatSystem.getmHf().getCalCode() == 8)
                {
                	 heatSystem.setmHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type.getTextTrim()));
                }

                if (heatSystem.getmHf().getCalCode() == 29)
                {
                	 heatSystem.setmHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type.getTextTrim()));
                }

                if (heatSystem.getmHf().getCalCode() == 19)
                {
                	 heatSystem.setmHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type.getTextTrim()));
                }

                if (heatSystem.getmHf().getCalCode() == 16)
                {
                	 heatSystem.setmHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type.getTextTrim()));
                }

                if (heatSystem.getmHf().getCalCode() == 5)
                {
                	 heatSystem.setmHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type.getTextTrim()));
                }
            }

            xpathSelector = DocumentHelper.createXPath("//defu:OA-Data/defu:Main-Heating-2/defu:Heating-Fuel");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element heating_Fuel22 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
            if (heating_Fuel22 != null && Integer.parseInt(heating_Fuel22.getTextTrim()) != 0)
            {
                heatSystem.setSmHf(dictServiceMgr.getDictItem(DictType.MAIHS_HT_2, heating_Fuel22.getTextTrim()));
            }

            xpathSelector = DocumentHelper.createXPath("//defu:OA-Data/defu:Main-Heating-2/defu:Heating-Type");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element heating_Type2 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
            if (heating_Type2 != null && !heating_Type2.getTextTrim().equals("0") && heatSystem.getSmHf() != null)
            {
                if (heatSystem.getSmHf().getCalCode() == 26 || heatSystem.getSmHf().getCalCode() == 27 || heatSystem.getSmHf().getCalCode() == 3 || heatSystem.getSmHf().getCalCode() == 17)
                {
                    heatSystem.setSmHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type2.getTextTrim()));
                }

                if (heatSystem.getSmHf().getCalCode() == 28)
                {
                	heatSystem.setSmHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type2.getTextTrim()));
                }

                if (heatSystem.getSmHf().getCalCode() == 33 || heatSystem.getSmHf().getCalCode() == 15 || heatSystem.getSmHf().getCalCode() == 6 || heatSystem.getSmHf().getCalCode() == 9 || heatSystem.getSmHf().getCalCode() == 8)
                {
                	heatSystem.setSmHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type2.getTextTrim()));
                }

                if (heatSystem.getSmHf().getCalCode() == 29)
                {
                	heatSystem.setSmHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type2.getTextTrim()));
                }

                if (heatSystem.getSmHf().getCalCode() == 19)
                {
                	heatSystem.setSmHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type2.getTextTrim()));
                }

                if (heatSystem.getSmHf().getCalCode() == 16)
                {
                	heatSystem.setSmHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type2.getTextTrim()));
                }

                if (heatSystem.getSmHf().getCalCode() == 5)
                {
                	heatSystem.setSmHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type2.getTextTrim()));
                }
            }

            xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Secondary-Heating//defu:Heating-Fuel");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element heating_Fuel2 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
            if (heating_Fuel2 != null && !heating_Fuel2.getTextTrim().equals("0"))
            {
                heatSystem.setsHf(dictServiceMgr.getDictItem(DictType.MAIHS_HT_2, heating_Fuel2.getTextTrim()));
            }

            xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Secondary-Heating//defu:Heating-Type");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element heating_Type22 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
            if (heating_Type22 != null && !heating_Type22.getTextTrim().equals("0") && heatSystem.getsHf() != null)
            {
                if (heatSystem.getsHf().getCalCode() == 26 || heatSystem.getsHf().getCalCode() == 27 || heatSystem.getsHf().getCalCode() == 3 || heatSystem.getsHf().getCalCode() == 17)
                {
                    heatSystem.setsHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type22.getTextTrim()));
                }

                if (heatSystem.getsHf().getCalCode() == 28)
                {
                	heatSystem.setsHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type22.getTextTrim()));
                }

                if (heatSystem.getsHf().getCalCode() == 33 || heatSystem.getsHf().getCalCode() == 15 || heatSystem.getsHf().getCalCode() == 6 || heatSystem.getsHf().getCalCode() == 9 || heatSystem.getsHf().getCalCode() == 8)
                {
                	heatSystem.setsHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type22.getTextTrim()));
                }

                if (heatSystem.getsHf().getCalCode() == 29)
                {
                	heatSystem.setsHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type22.getTextTrim()));
                }

                if (heatSystem.getsHf().getCalCode() == 19)
                {
                	heatSystem.setsHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type22.getTextTrim()));
                }

                if (heatSystem.getsHf().getCalCode() == 16)
                {
                	heatSystem.setsHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type22.getTextTrim()));
                }

                if (heatSystem.getsHf().getCalCode() == 5)
                {
                	heatSystem.setsHt(dictServiceMgr.getDictItem(DictType.MAIHS_SAP_2, heating_Type22.getTextTrim()));
                }
            }
			heatingSystemMgr.addHeatingSystem(heatSystem);
			
			xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Room-Heating");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			@SuppressWarnings("unchecked")
			List<Element> room_Heating = (List<Element>)xpathSelector.selectNodes(oaLigDoc);
			List<HeatProportion> listHp = heatProportionServiceMgr.getHeatProportions(report.getId());
            if (room_Heating != null && room_Heating.size() == report.getHabitableRoomCount())
            {
                for (int i = 0; i < room_Heating.size(); i++)
                {
                    HeatProportion heatProportion = listHp.get(i);
                    heatProportion.setReportId(report);
                    int num = i + 1;
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Room-Heating[" + num + "]//defu:Main-Heating-1");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element main_Heating_1 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                    if (main_Heating_1 != null && main_Heating_1.getTextTrim().equalsIgnoreCase("true"))
                    {
                        heatProportion.setMain1(YesNo.Yes);
                    }
                    else
                    {
                        heatProportion.setMain1(YesNo.No);
                    }

                    xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Room-Heating[" + num + "]//defu:Main-Heating-2");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element main_Heating_2 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                    if (main_Heating_2 != null && main_Heating_2.getTextTrim().equalsIgnoreCase("true"))
                    {
                    	heatProportion.setMain2(YesNo.Yes);
                    }
                    else
                    {
                    	heatProportion.setMain2(YesNo.No);
                    }

                    xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Room-Heating[" + num + "]//defu:Secondary-Heating");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element secondary_Heating = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                    if (secondary_Heating != null && secondary_Heating.getTextTrim().equalsIgnoreCase("true"))
                    {
                        heatProportion.setSecondary(YesNo.Yes);
                    }
                    else
                    {
                    	heatProportion.setSecondary(YesNo.No);
                    }

                    xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Room-Heating[" + num + "]//defu:Partially-Heated");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element prtially_Heated = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                    if (prtially_Heated != null && prtially_Heated.getTextTrim().equalsIgnoreCase("true"))
                    {
                        heatProportion.setHeatedPartially(YesNo.Yes);
                    }
                    else
                    {
                        heatProportion.setHeatedPartially(YesNo.No);
                    }

                    xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Room-Heating[" + num + "]//defu:Not-Heated");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element not_Heated = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                    if (not_Heated != null && not_Heated.getTextTrim().equalsIgnoreCase("true"))
                    {
                        heatProportion.setNotable(YesNo.Yes);
                    }
                    else
                    {
                    	heatProportion.setNotable(YesNo.No);
                    }
                    heatProportionServiceMgr.updateHeatProportion(heatProportion);
                }
            }

            //HeatingPattern
            xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Heating-Pattern//defu:Normal-Heating-Day");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			@SuppressWarnings("unchecked")
			List<Element> listNormalHeatingPattern = (List<Element>)xpathSelector.selectNodes(oaLigDoc);
			HeatingPattern heatingPattern = new HeatingPattern();
			heatingPattern.setN1On("");
            heatingPattern.setN1Off("");
            heatingPattern.setN2On("");
            heatingPattern.setN2Off("");
            heatingPattern.setN3On("");
            heatingPattern.setN3Off("");
            heatingPattern.setN4On("");
            heatingPattern.setN4Off("");
            heatingPattern.setA1On("");
        	heatingPattern.setA1Off("");
            heatingPattern.setA2On("");
            heatingPattern.setA2Off("");
            heatingPattern.setA3On("");
            heatingPattern.setA3Off("");
            heatingPattern.setA4On("");
            heatingPattern.setA4Off("");
                
        	heatingPattern.setReport(report);
            if (listNormalHeatingPattern != null && listNormalHeatingPattern.size() > 0)
            {
                xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Heating-Pattern//defu:Normal-Heating-Day[1]//defu:Heating-On");
    			xpathSelector.setNamespaceURIs(nameSpaceMap);
    			Element heating_On = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                heatingPattern.setN1On(heating_On != null ? heating_On.getTextTrim() : "");
                
                xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Heating-Pattern//defu:Normal-Heating-Day[1]//defu:Heating-Off");
    			xpathSelector.setNamespaceURIs(nameSpaceMap);
    			Element heating_Off = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                heatingPattern.setN1Off(heating_Off != null ? heating_Off.getTextTrim() : "");

                if (listNormalHeatingPattern.size() >= 2)
                {
                    xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Heating-Pattern//defu:Normal-Heating-Day[2]//defu:Heating-On");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element heating_On2 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                    heatingPattern.setN2On(heating_On2 != null ? heating_On2.getTextTrim() : "");
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Heating-Pattern//defu:Normal-Heating-Day[2]//defu:Heating-Off");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element heating_Off2 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                    heatingPattern.setN2Off(heating_Off2 != null ? heating_Off2.getTextTrim() : "");
                }

                if (listNormalHeatingPattern.size() >= 3)
                {
                    xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Heating-Pattern//defu:Normal-Heating-Day[3]//defu:Heating-On");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element heating_On3 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                    heatingPattern.setN3On(heating_On3 != null ? heating_On3.getTextTrim() : "");
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Heating-Pattern//defu:Normal-Heating-Day[3]//defu:Heating-Off");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element heating_Off3 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                    heatingPattern.setN3Off(heating_Off3 != null ? heating_Off3.getTextTrim() : "");
                }

                if (listNormalHeatingPattern.size() >= 4)
                {
                    xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Heating-Pattern//defu:Normal-Heating-Day[4]//defu:Heating-On");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element heating_On4 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                    heatingPattern.setN4On(heating_On4 != null ? heating_On4.getTextTrim() : "");
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Heating-Pattern//defu:Normal-Heating-Day[4]//defu:Heating-Off");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element heating_Off4 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                    heatingPattern.setN4Off(heating_Off4 != null ? heating_Off4.getTextTrim() : "");
                }
            }

            xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Heating-Pattern//defu:Alternative-Heating-Day");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			@SuppressWarnings("unchecked")
			List<Element> listAlternativeHeatingPattern = (List<Element>)xpathSelector.selectNodes(oaLigDoc);
            if (listAlternativeHeatingPattern != null && listAlternativeHeatingPattern.size() > 0)
            {
                xpathSelector = DocumentHelper.createXPath("//defu:OA-Data/defu:Heating-Pattern/defu:Alternative-Heating-Day[1]/defu:Heating-On");
    			xpathSelector.setNamespaceURIs(nameSpaceMap);
    			Element heating_On = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                heatingPattern.setA1On(heating_On != null ? heating_On.getTextTrim() : "");
                
                xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Heating-Pattern//defu:Alternative-Heating-Day[1]//defu:Heating-Off");
    			xpathSelector.setNamespaceURIs(nameSpaceMap);
    			Element heating_Off = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                heatingPattern.setA1Off(heating_Off != null ? heating_Off.getTextTrim() : "");

                if (listAlternativeHeatingPattern.size() >= 2)
                {
                    xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Heating-Pattern//defu:Alternative-Heating-Day[2]//defu:Heating-On");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element heating_On2 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                    heatingPattern.setA2On(heating_On2 != null ? heating_On2.getTextTrim() : "");
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Heating-Pattern//defu:Alternative-Heating-Day[2]//defu:Heating-Off");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element heating_Off2 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                    heatingPattern.setA2Off(heating_Off2 != null ? heating_Off2.getTextTrim() : "");
                }

                if (listAlternativeHeatingPattern.size() >= 3)
                {
                    xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Heating-Pattern//defu:Alternative-Heating-Day[3]//defu:Heating-On");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element heating_On3 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                    heatingPattern.setA3On(heating_On3 != null ? heating_On3.getTextTrim() : "");
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Heating-Pattern//defu:Alternative-Heating-Day[3]//defu:Heating-Off");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element heating_Off3 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                    heatingPattern.setA3Off(heating_Off3 != null ? heating_Off3.getTextTrim() : "");
                }

                if (listAlternativeHeatingPattern.size() >= 4)
                {
                    xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Heating-Pattern//defu:Alternative-Heating-Day[4]//defu:Heating-On");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element heating_On4 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                    heatingPattern.setA4On(heating_On4 != null ? heating_On4.getTextTrim() : "");
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Heating-Pattern//defu:Alternative-Heating-Day[4]//defu:Heating-Off");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element heating_Off4 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                    heatingPattern.setA4Off(heating_Off4 != null ? heating_Off4.getTextTrim() : "");
                }
            }

            xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Heating-Pattern//defu:Alternative-Days-Per-Week");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element alternative_Days_Per_Week = (Element)xpathSelector.selectSingleNode(oaLigDoc);
            heatingPattern.setDays(alternative_Days_Per_Week != null ? Integer.parseInt(alternative_Days_Per_Week.getTextTrim()) : 0);

            this.heatingPatternServiceMgr.addHeatingPattern(heatingPattern);
            
            //appCooking
            AppCooking appCooking = new AppCooking();
            appCooking.setReport(report);
            
            xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Electrical-Appliances//defu:Number-Of-Refrigerators");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element number_Of_Refrigerators = (Element)xpathSelector.selectSingleNode(oaLigDoc);
            if (number_Of_Refrigerators != null)
            {
                appCooking.setFridgesNumber(Integer.parseInt(number_Of_Refrigerators.getTextTrim()));
            }

            xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Electrical-Appliances//defu:Number-Of-Freezers");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element number_Of_Freezers = (Element)xpathSelector.selectSingleNode(oaLigDoc);
            if (number_Of_Freezers != null)
            {
                appCooking.setFreezersNumber(Integer.parseInt(number_Of_Freezers.getTextTrim()));
            }

            xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Electrical-Appliances//defu:Number-Of-Fridge-Freezers");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element number_Of_Fridge_Freezers = (Element)xpathSelector.selectSingleNode(oaLigDoc);
            if (number_Of_Fridge_Freezers != null)
            {
                appCooking.setFridgeFreezersNumber(note2Int(number_Of_Fridge_Freezers));
            }

            appCooking.setDryProportion(0);
            xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Electrical-Appliances//defu:Percent-Drying-In-Tumble-Dryer");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element percent_Drying_In_Tumble_Dryer = (Element)xpathSelector.selectSingleNode(oaLigDoc);
            if (percent_Drying_In_Tumble_Dryer != null)
            {
                appCooking.setDryProportion(Float.parseFloat(percent_Drying_In_Tumble_Dryer.getTextTrim()));
            }

            Element xmlNode = null;
            appCooking.setDryProportion(0);
            xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Cooking-Appliance//defu:Cooker-Type-Normal//defu:Fuel");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			xmlNode = (Element)xpathSelector.selectSingleNode(oaLigDoc);
            if (xmlNode != null)
            {
                appCooking.setCookerType(dictServiceMgr.getDictItem(DictType.AC_COOKER_TYPE, 1.0));
                appCooking.setCookingFuel(dictServiceMgr.getDictItem(DictType.AC_COOKING_FUEL, xmlNode.getTextTrim()));
            }

            xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Cooking-Appliance//defu:Cooker-Type-Large//defu:Fuel");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			xmlNode = (Element)xpathSelector.selectSingleNode(oaLigDoc);
            if (xmlNode != null)
            {
            	appCooking.setCookerType(dictServiceMgr.getDictItem(DictType.AC_COOKER_TYPE, 2.0));
            	appCooking.setCookingFuel(dictServiceMgr.getDictItem(DictType.AC_COOKING_FUEL, xmlNode.getTextTrim()));
            }

            xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Cooking-Appliance//defu:Cooker-Type-Range-All-Year//defu:Fuel");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			xmlNode = (Element)xpathSelector.selectSingleNode(oaLigDoc);
            if (xmlNode != null)
            {
            	appCooking.setCookerType(dictServiceMgr.getDictItem(DictType.AC_COOKER_TYPE, 3.0));
            	appCooking.setCookingFuel(dictServiceMgr.getDictItem(DictType.AC_COOKING_FUEL, xmlNode.getTextTrim()));
            }

            xpathSelector = DocumentHelper.createXPath("//defu:OA-Data//defu:Cooking-Appliance//defu:Cooker-Type-Range-Winter//defu:Fuel");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			xmlNode = (Element)xpathSelector.selectSingleNode(oaLigDoc);
            if (xmlNode != null)
            {
            	appCooking.setCookerType(dictServiceMgr.getDictItem(DictType.AC_COOKER_TYPE, 4.0));
            	appCooking.setCookingFuel(dictServiceMgr.getDictItem(DictType.AC_COOKING_FUEL, xmlNode.getTextTrim()));
            }
            appCooking.setDryingClothesSpacable(YesNo.No);

            this.appCookingServiceMgr.addAppCooking(appCooking);
            
            //BillData --> BillDataComm
            xpathSelector = DocumentHelper.createXPath("//defu:Occupancy-Assessment//defu:OA-Data//defu:Fuel-Bill-Data//defu:Fuel-Bill");
            xpathSelector.setNamespaceURIs(nameSpaceMap);
			List<Element> _FuelBillNodes = (List<Element>)xpathSelector.selectNodes(oaLigDoc);
			
			BillDataEle billDataEle = null;
	    	BillDataMg billDataMg = null;
	    	BillDataComm billDataComm = null;
	    	BillDataLPG billDataLPG = null;
			int billDataEleFuelNumbers[] = new int[]{39, 29, 42, 44, 40, 41, 45, 46, 47};
			for(Element _FuelBillNode : _FuelBillNodes)
			{
				xpathSelector = DocumentHelper.createXPath("//defu:Fuel");
            	xpathSelector.setNamespaceURIs(nameSpaceMap);
    			xmlNode = (Element)xpathSelector.selectSingleNode(oaLigDoc);
                int fuel = note2Int(xmlNode);
                if (F.in(fuel, billDataEleFuelNumbers))
                {
                	billDataEle = new BillDataEle();
                	billDataEle.setReport(report);
                	break;
                }
			}
			
            for(Element _FuelBillNode : _FuelBillNodes)
            {
            	xpathSelector = DocumentHelper.createXPath("//defu:Fuel");
            	xpathSelector.setNamespaceURIs(nameSpaceMap);
    			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                int fuel = note2Int(xmlNode);
                
                if (F.in(fuel, 39, 29))
                {
                    billDataEle.setEtElectricityTariff(dictServiceMgr.getDictItem(DictType.BILL_ELECTRICITY_TARIFF, 1.0));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Data-Source");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataEle.setEtStReliablityLevel(note2Ditem(xmlNode, DictType.BILL_RELIABLITY_LEVEL));

                    xpathSelector = DocumentHelper.createXPath("//defu:Units-Supplied");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataEle.setEtStElectricityUsed(note2Float(xmlNode));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Billing-Period");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataEle.setEtStPeriod(note2Float(xmlNode));
                    if (F.include(billDataEle.getEtStPeriod(), 11f, 12f, 13f))
                    {
                        billDataEle.setEtStPeriodSelect(dictServiceMgr.getDictItem(DictType.BILL_PERIOD, 1.0));
                    }
                    else
                    {
                        billDataEle.setEtStPeriodSelect(dictServiceMgr.getDictItem(DictType.BILL_PERIOD, 2.0));
                    }

                    xpathSelector = DocumentHelper.createXPath("//defu:Prices-Include-VAT");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataEle.setEtStVatable(note2YesNo(xmlNode));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Standing-Charge-And-Unit-Price");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element _StandingChargeAndUnitPriceNote = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (_StandingChargeAndUnitPriceNote != null)
                    {
                        billDataEle.setEtStChargingBasis(dictServiceMgr.getDictItem(DictType.BILL_CHARGING_BASIS, 1.0));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Standing-Charge");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        billDataEle.setEtStStandingChargeAmount(note2Float(xmlNode));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Unit-For-Standing-Charge");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        billDataEle.setEtStStandingChargeAmountSelect(note2Ditem(xmlNode, DictType.BILL_STANDING_CHARGE_UNIT));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Unit-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        billDataEle.setEtStUnitPrice(note2Float(xmlNode));
                    }
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Two-Unit-Prices");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			_StandingChargeAndUnitPriceNote = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (_StandingChargeAndUnitPriceNote != null)
                    {
                        billDataEle.setEtStChargingBasis(dictServiceMgr.getDictItem(DictType.BILL_CHARGING_BASIS, 2.0));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Initial-Unit-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        billDataEle.setEtStInitialUnitPrice(note2Float(xmlNode));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Units-At-Initial-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        billDataEle.setEtStUnitsAtThisPrice(note2Float(xmlNode));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:SPeriod-For-Initial-Units");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        billDataEle.setEtStUnitsAtThisPriceSelect(note2Ditem(xmlNode, DictType.BILL_UNITS_AT_THIS_PRICE));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Follow-On-Unit-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        billDataEle.setEtStFollowonUnitPrice(note2Float(xmlNode));
                    }
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Unusual-Energy-Using-Item-Description");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataEle.setEtStUnusualEnergyUsingableDes(xmlNode != null ? xmlNode.getTextTrim() : null);
                    
                    if (billDataEle.getEtStUnusualEnergyUsingableDes() == null || billDataEle.getEtStUnusualEnergyUsingableDes().equals(""))
                        billDataEle.setEtStUnusualEnergyUsingable(YesNo.No);
                    else
                        billDataEle.setEtStUnusualEnergyUsingable(YesNo.Yes);

                    xpathSelector = DocumentHelper.createXPath("//defu:Electricity-Generation-PVs");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (xmlNode != null)
                    {
                        billDataEle.setEtPvable(YesNo.Yes);
                        billDataEle.setEtPvAmount(note2Float(xmlNode));
                        billDataEle.setEtPvPeriod(billDataEle.getEtStPeriod());
                        billDataEle.setEtPvPeriodSelect(billDataEle.getEtStPeriodSelect());
                    }
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Electricity-Generation-Wind");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (xmlNode != null)
                    {
                        billDataEle.setEtWindable(YesNo.Yes);
                        billDataEle.setEtWindAmount(note2Float(xmlNode));
                        billDataEle.setEtWindPeriod(billDataEle.getEtStPeriod());
                        billDataEle.setEtWindPeriodSelect(billDataEle.getEtStPeriodSelect());
                    }
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Electricity-Generation-Micro-CHP");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (xmlNode != null)
                    {
                        billDataEle.setEtMicroable(YesNo.Yes);
                        billDataEle.setEtMicroableAmount(note2Float(xmlNode));
                        billDataEle.setEtMicroablePeriod(billDataEle.getEtStPeriod());
                        billDataEle.setEtMicroablePeriodSelect(billDataEle.getEtStPeriodSelect());
                    }
                }
                else if (fuel == 42)
                {
                    billDataEle.setEtElectricityTariff(dictServiceMgr.getDictItem(DictType.BILL_ELECTRICITY_TARIFF, 3.0));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Data-Source");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataEle.setEt24ReliablityLevel(note2Ditem(xmlNode, DictType.BILL_RELIABLITY_LEVEL));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Units-Supplied");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataEle.setEt24ElectricityUsed(note2Float(xmlNode));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Billing-Period");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataEle.setEt24Period(note2Float(xmlNode));

                    if (F.include(billDataEle.getEt24Period(), 11f, 12f, 13f))
                    {
                        billDataEle.setEt24PeriodSelect(dictServiceMgr.getDictItem(DictType.BILL_PERIOD, 1.0));
                    }
                    else
                    {
                        billDataEle.setEt24PeriodSelect(dictServiceMgr.getDictItem(DictType.BILL_PERIOD, 2.0));
                    }

                    xpathSelector = DocumentHelper.createXPath("//defu:Standing-Charge-And-Unit-Price");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element _StandingChargeAndUnitPriceNote = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (_StandingChargeAndUnitPriceNote != null)
                    {
                        billDataEle.setEt24ChargingBasis(dictServiceMgr.getDictItem(DictType.BILL_CHARGING_BASIS, 1.0));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Standing-Charge");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        billDataEle.setEt24StandingChargeAmount(note2Float(xmlNode));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Unit-For-Standing-Charge");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        billDataEle.setEt24StandingChargeAmountSelect(note2Ditem(xmlNode, DictType.BILL_STANDING_CHARGE_UNIT));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Unit-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        billDataEle.setEt24UnitPrice(note2Float(xmlNode));
                    }
                    xpathSelector = DocumentHelper.createXPath("//defu:Unit-Price");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			_StandingChargeAndUnitPriceNote = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (_StandingChargeAndUnitPriceNote != null)
                    {
                        billDataEle.setEt24ChargingBasis(dictServiceMgr.getDictItem(DictType.BILL_CHARGING_BASIS, 2.0));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Initial-Unit-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        billDataEle.setEt24InitialUnitPrice(note2Float(xmlNode));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Units-At-Initial-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        billDataEle.setEt24UnitsAtThisPrice(note2Float(xmlNode));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:SPeriod-For-Initial-Units");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        billDataEle.setEt24UnitsAtThisPriceSelect(note2Ditem(xmlNode, DictType.BILL_UNITS_AT_THIS_PRICE));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Follow-On-Unit-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        billDataEle.setEt24FollowonUnitPrice(note2Float(xmlNode));
                    }

                    xpathSelector = DocumentHelper.createXPath("//defu:Prices-Include-VAT");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataEle.setEt24Vatable(note2YesNo(xmlNode));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Electricity-Generation-PVs");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (xmlNode != null)
                    {
                        billDataEle.setEtPvable(YesNo.Yes);
                        billDataEle.setEtPvAmount(note2Float(xmlNode));
                    }
                    xpathSelector = DocumentHelper.createXPath("//defu:Electricity-Generation-Wind");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (xmlNode != null)
                    {
                        billDataEle.setEtWindable(YesNo.Yes);
                        billDataEle.setEtWindAmount(note2Float(xmlNode));
                    }
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Electricity-Generation-Micro-CHP");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (xmlNode != null)
                    {
                        billDataEle.setEtMicroable(YesNo.Yes);
                        billDataEle.setEtMicroableAmount(note2Float(xmlNode));
                    }
                    xpathSelector = DocumentHelper.createXPath("//defu:Unusual-Energy-Using-Item-Description");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (xmlNode != null)
                    {
                        billDataEle.setEt24UnusualEnergyUsingable(YesNo.Yes);
                        billDataEle.setEt24UnusualEnergyUsingableDes(xmlNode.getTextTrim());
                    }
                }
                else if (F.in(fuel, 44, 40, 41, 45, 46, 47))
                {
                    //40.H 41.L
                    billDataEle.setEtElectricityTariff(dictServiceMgr.getDictItem(DictType.BILL_ELECTRICITY_TARIFF, 2.0));

                    if (F.in(fuel, 40, 45))
                    {
                        billDataEle.setOffPeakType(OffPeakType.OffPeak_7);
                    }
                    else if (F.in(fuel, 46, 47))
                    {
                        billDataEle.setOffPeakType(OffPeakType.OffPeak_18);
                    }
                    else
                    {
                        billDataEle.setOffPeakType(OffPeakType.OffPeak_10);
                    }

                    xpathSelector = DocumentHelper.createXPath("//defu:Data-Source");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
        			String HLFlag = heightOrLow(fuel);
                    if (HLFlag.equals("H"))
                        billDataEle.setEtOffHReliablityLevel(note2Ditem(xmlNode, DictType.BILL_RELIABLITY_LEVEL));
                    else if(HLFlag.equals("L"))
                        billDataEle.setEtOffLReliablityLevel(note2Ditem(xmlNode, DictType.BILL_RELIABLITY_LEVEL));

                    xpathSelector = DocumentHelper.createXPath("//defu:Units-Supplied");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (HLFlag.equals("H"))
                        billDataEle.setEtOffHElectricityUsed(note2Float(xmlNode));
                    else if(HLFlag.equals("L"))
                        billDataEle.setEtOffLElectricityUsed(note2Float(xmlNode));

                    xpathSelector = DocumentHelper.createXPath("//defu:Billing-Period");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (HLFlag.equals("H"))
                        billDataEle.setEtOffHPeriod(note2Float(xmlNode));
                    else if(HLFlag.equals("L"))
                        billDataEle.setEtOffLPeriod(note2Float(xmlNode));

                    if (F.include(billDataEle.getEtMicroablePeriod(), 11f, 12f, 13f))
                    {
                        billDataEle.setEtMicroablePeriodSelect(dictServiceMgr.getDictItem(DictType.BILL_PERIOD, 1.0));
                    }
                    else
                    {
                        billDataEle.setEtMicroablePeriodSelect(dictServiceMgr.getDictItem(DictType.BILL_PERIOD, 2.0));
                    }


                    xpathSelector = DocumentHelper.createXPath("//defu:Standing-Charge-And-Unit-Price");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			Element _StandingChargeAndUnitPriceNote = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (_StandingChargeAndUnitPriceNote != null)
                    {
                        if (HLFlag.equals("H"))
                            billDataEle.setEtOffHChargingBasis(dictServiceMgr.getDictItem(DictType.BILL_CHARGING_BASIS, 1.0));
                        else if (HLFlag.equals("L"))
                            billDataEle.setEtOffLChargingBasis(dictServiceMgr.getDictItem(DictType.BILL_CHARGING_BASIS, 1.0));

                        xpathSelector = DocumentHelper.createXPath("//defu:Standing-Charge");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        if (HLFlag.equals("H"))
                            billDataEle.setEtOffHStandingChargeAmount(note2Float(xmlNode));
                        else if (HLFlag.equals("L"))
                            billDataEle.setEtOffLStandingChargeAmount(note2Float(xmlNode));

                        xpathSelector = DocumentHelper.createXPath("//defu:Unit-For-Standing-Charge");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        if (HLFlag.equals("H"))
                            billDataEle.setEtOffHStandingChargeAmountSelect(note2Ditem(xmlNode, DictType.BILL_STANDING_CHARGE_UNIT));
                        else if (HLFlag.equals("L"))
                            billDataEle.setEtOffLStandingChargeAmountSelect(note2Ditem(xmlNode, DictType.BILL_STANDING_CHARGE_UNIT));

                        xpathSelector = DocumentHelper.createXPath("//defu:Unit-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        if (HLFlag.equals("H"))
                            billDataEle.setEtOffHUnitPrice(note2Float(xmlNode));
                        else if (HLFlag.equals("L"))
                            billDataEle.setEtOffLUnitPrice(note2Float(xmlNode));
                    }
                    xpathSelector = DocumentHelper.createXPath("//defu:Two-Unit-Prices");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (_StandingChargeAndUnitPriceNote != null)
                    {
                        billDataEle.setEtOffHChargingBasis(dictServiceMgr.getDictItem(DictType.BILL_CHARGING_BASIS, 2.0));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Initial-Unit-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        billDataEle.setEtOffHInitialUnitAmount(note2Float(xmlNode));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Units-At-Initial-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        billDataEle.setEtOffHUnitsAtThisPrice(note2Float(xmlNode));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:SPeriod-For-Initial-Units");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        billDataEle.setEtOffHUnitsAtThisPriceSelect(note2Ditem(xmlNode, DictType.BILL_UNITS_AT_THIS_PRICE));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Follow-On-Unit-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_StandingChargeAndUnitPriceNote);
                        billDataEle.setEtOffHFollow(note2Float(xmlNode));
                    }
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Prices-Include-VAT");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (HLFlag.equals("H"))
                        billDataEle.setEtOffHVatable(note2YesNo(xmlNode));
                    else if (HLFlag.equals("L"))
                        billDataEle.setEtOffLVatable(note2YesNo(xmlNode));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Electricity-Generation-PVs");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (xmlNode != null)
                    {
                        billDataEle.setEtPvable(YesNo.Yes);
                        billDataEle.setEtPvAmount(note2Float(xmlNode));
                    }
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Electricity-Generation-Wind");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (xmlNode != null)
                    {
                        billDataEle.setEtPvable(YesNo.Yes);
                        billDataEle.setEtPvAmount(note2Float(xmlNode));
                    }
                    xpathSelector = DocumentHelper.createXPath("//defu:Electricity-Generation-Micro-CHP");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (xmlNode != null)
                    {
                        billDataEle.setEtWindable(YesNo.Yes);
                        billDataEle.setEtWindAmount(note2Float(xmlNode));
                    }
                    xpathSelector = DocumentHelper.createXPath("//defu:Unusual-Energy-Using-Item-Description");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (xmlNode != null)
                    {
                        billDataEle.setEtStUnusualEnergyUsingable(YesNo.Yes);
                        billDataEle.setEtStUnusualEnergyUsingableDes(xmlNode.getTextTrim());
                    }
                    xpathSelector = DocumentHelper.createXPath("//defu:Unusual-Energy-Using-Item-Description");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (xmlNode != null)
                    {
                        if (F.include(fuel, 41, 45))
                        {
                            billDataEle.setEtOffLUnusualEnergyUsingable(YesNo.Yes);
                            billDataEle.setEtOffLUnusualEnergyUsingableDes(xmlNode.getTextTrim());
                        }
                        else
                        {
                            billDataEle.setEtOffHUnusualEnergyUsingable(YesNo.Yes);
                            billDataEle.setEtOffHUnusualEnergyUsingableDes(xmlNode.getTextTrim());
                        }
                    }
                }
                else if (F.include(fuel, 38, 43, 51))
                {
                    //BillData-->billDataComm
                	billDataComm = new BillDataComm();
                	xpathSelector = DocumentHelper.createXPath("//defu:Data-Source");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataComm.setChReliablityLevel(note2Ditem(xmlNode, DictType.BILL_Community_Heating_LEVEL));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Units-Supplied");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataComm.setChEnergyUsed(note2Float(xmlNode));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Billing-Period");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataComm.setChPeriod(note2Float(xmlNode));
                    if (F.include(billDataComm.getChPeriod(), 11f, 12f, 13f))
                    {
                        billDataComm.setChPeriodSelect(dictServiceMgr.getDictItem(DictType.BILL_PERIOD, 1.0));
                    }
                    else
                    {
                        billDataComm.setChPeriodSelect(dictServiceMgr.getDictItem(DictType.BILL_PERIOD, 2.0));
                    }
                    xpathSelector = DocumentHelper.createXPath("//defu:Cost-Per-Unit/defu:Fixed-Charge");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataComm.setChFixedCost(note2Float(xmlNode));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Cost-Per-Unit/defu:Unit-Price");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataComm.setChUnitPrice(note2Float(xmlNode));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Prices-Include-VAT");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataComm.setVatable(note2YesNo(xmlNode));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Unusual-Energy-Using-Item-Description");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
                    if (xmlNode != null)
                    {
                        billDataComm.setChUnusualEnergyUsingable(YesNo.Yes);
                        billDataComm.setChUnusualEnergyUsingableDes(xmlNode.getTextTrim());
                    }

                    billDataServiceMgr.addBillDataComm(billDataComm);
                }

                else if (F.include(fuel, 26))
                {
                	billDataMg = new BillDataMg();
                	xpathSelector = DocumentHelper.createXPath("//defu:Data-Source");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataMg.setMgReliablityLevel(note2Ditem(xmlNode, DictType.BILL_RELIABLITY_LEVEL));
                    
                	xpathSelector = DocumentHelper.createXPath("//defu:Units-Supplied");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataMg.setMgGasUsed(note2Float(xmlNode));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Billing-Period");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataMg.setMgPeriod(note2Float(xmlNode));
                    if (F.include(billDataMg.getMgPeriod(), 11f, 12f, 13f))
                    {
                        billDataMg.setMgPeriodSelect(dictServiceMgr.getDictItem(DictType.BILL_PERIOD, 1.0));
                    }
                    else
                    {
                        billDataMg.setMgPeriodSelect(dictServiceMgr.getDictItem(DictType.BILL_PERIOD, 2.0));
                    }

                    xpathSelector = DocumentHelper.createXPath("//defu:Standing-Charge-And-Unit-Price");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (xmlNode != null)
                    {
                        billDataMg.setMgChargingBasis(dictServiceMgr.getDictItem(DictType.BILL_CHARGING_BASIS, 1.0));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Standing-Charge-And-Unit-Price//defu:Standing-Charge");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                        billDataMg.setMgStAmount(note2Float(xmlNode));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Standing-Charge-And-Unit-Price//defu:Unit-For-Standing-Charge");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                        billDataMg.setMgStAmountSelect(note2Ditem(xmlNode, DictType.BILL_STANDING_CHARGE_UNIT));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Standing-Charge-And-Unit-Price//defu:Unit-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                        billDataMg.setMgStUnitPrice(note2Float(xmlNode));
                    }
                    xpathSelector = DocumentHelper.createXPath("//defu:Two-Unit-Prices");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (xmlNode != null)
                    {
                        billDataMg.setMgChargingBasis(dictServiceMgr.getDictItem(DictType.BILL_CHARGING_BASIS, 2.0));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Two-Unit-Prices//defu:Initial-Unit-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                        billDataMg.setMgTwInitialUnit(note2Float(xmlNode));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Two-Unit-Prices//defu:Units-At-Initial-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                        billDataMg.setMgTwUnits(note2Float(xmlNode));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Two-Unit-Prices//defu:Period-For-Initial-Units");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                        billDataMg.setMgTwUnitsSelected(note2Ditem(xmlNode, DictType.BILL_UNITS_AT_THIS_PRICE));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Two-Unit-Prices//defu:Follow-On-Unit-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                        billDataMg.setMgTwFollowOn(note2Float(xmlNode));
                    }
                    xpathSelector = DocumentHelper.createXPath("//defu:Prices-Include-VAT");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataMg.setMgVatAble(note2YesNo(xmlNode));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Unusual-Energy-Using-Item-Description");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (xmlNode != null)
                    {
                        billDataMg.setMgUnusualEnergyUsingable(YesNo.Yes);
                        billDataMg.setMgUnusualEnergyUsingableDes(xmlNode.getTextTrim());
                    }
                    this.billDataServiceMgr.addBillDataMg(billDataMg);
                }
                //BillData-->billDataLPG
                else if (F.include(fuel, 17))
                {
                	billDataLPG = new BillDataLPG();
                	xpathSelector = DocumentHelper.createXPath("//defu:Data-Source");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataLPG.setLpgReliablityLevel(note2Ditem(xmlNode, DictType.BILL_RELIABLITY_LEVEL));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Units-Supplied");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataLPG.setLpgGasUsed(note2Float(xmlNode));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Billing-Period");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataLPG.setLpgPeriod(note2Float(xmlNode));
                    
                    if (F.include(billDataLPG.getLpgPeriod(), 11f, 12f, 13f))
                    {
                        billDataLPG.setLpgPeriodSelect(dictServiceMgr.getDictItem(DictType.BILL_PERIOD, 1.0));
                    }
                    else
                    {
                        billDataLPG.setLpgPeriodSelect(dictServiceMgr.getDictItem(DictType.BILL_PERIOD, 2.0));
                    }

                    xpathSelector = DocumentHelper.createXPath("//defu:Standing-Charge-And-Unit-Price");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (xmlNode != null)
                    {
                        billDataLPG.setLpgChargingBasis(dictServiceMgr.getDictItem(DictType.BILL_CHARGING_BASIS, 1.0));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Standing-Charge-And-Unit-Price//defu:Standing-Charge");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                        billDataLPG.setLpgStAmount(note2Float(xmlNode));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Standing-Charge-And-Unit-Price//defu:Unit-For-Standing-Charge");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                        billDataLPG.setLpgStAmountSelect(note2Ditem(xmlNode, DictType.BILL_STANDING_CHARGE_UNIT));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Standing-Charge-And-Unit-Price//defu:Unit-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                        billDataLPG.setLpgStUnitPrice(note2Float(xmlNode));
                    }
                    xpathSelector = DocumentHelper.createXPath("//defu:Two-Unit-Prices");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (xmlNode != null)
                    {
                        billDataLPG.setLpgChargingBasis(dictServiceMgr.getDictItem(DictType.BILL_CHARGING_BASIS, 2.0));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Two-Unit-Prices//defu:Initial-Unit-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                        billDataLPG.setLpgTwInitialUnit(note2Float(xmlNode));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Two-Unit-Prices//defu:Units-At-Initial-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                        billDataLPG.setLpgTwUnits(note2Float(xmlNode));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Two-Unit-Prices//defu:Period-For-Initial-Units");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                        billDataLPG.setLpgTwUnitsSelected(note2Ditem(xmlNode, DictType.BILL_UNITS_AT_THIS_PRICE));
                        
                        xpathSelector = DocumentHelper.createXPath("//defu:Two-Unit-Prices/defu:Follow-On-Unit-Price");
            			xpathSelector.setNamespaceURIs(nameSpaceMap);
            			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                        billDataLPG.setLpgTwFollowOn(note2Float(xmlNode));
                    }
                    xpathSelector = DocumentHelper.createXPath("//defu:Prices-Include-VAT");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    billDataLPG.setLpgVatAble(note2YesNo(xmlNode));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Unusual-Energy-Using-Item-Description");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    if (xmlNode != null)
                    {
                        billDataLPG.setLpgUnusualEnergyUsingable(YesNo.Yes);
                        billDataLPG.setLpgUnusualEnergyUsingableDes(xmlNode.getTextTrim());
                    }

                    this.billDataServiceMgr.addBillDataLPG(billDataLPG);
                }
                else if (F.include(fuel, 3, 5, 6, 7, 8, 9, 15, 16, 18, 19, 27, 28, 33, 34, 35, 36, 37))
                {
                    OtherFuel otherFuel = new OtherFuel();
                    otherFuel.setFuelCode(fuel);
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Data-Source");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    otherFuel.setReliablityLevel(note2Ditem(xmlNode, DictType.BILL_Community_Heating_LEVEL));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Billing-Unit");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    otherFuel.setUnitOfSale(note2Ditem(xmlNode, DictType.BILL_UNIT_OF_SALE));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Units-Supplied");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    otherFuel.setUnitsPurchasedNumber(note2Float(xmlNode));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Billing-Period");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    otherFuel.setPeriod(note2Float(xmlNode));
                    
                    if (F.include(otherFuel.getPeriod(), 11f, 12f, 13f))
                    {
                        otherFuel.setPeriodSelect(dictServiceMgr.getDictItem(DictType.BILL_PERIOD, 1.0));
                    }
                    else
                    {
                        otherFuel.setPeriodSelect(dictServiceMgr.getDictItem(DictType.BILL_PERIOD, 2.0));
                    }
                    xpathSelector = DocumentHelper.createXPath("//defu:Cost-Per-Unit//defu:Fixed-Charge");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    otherFuel.setFixedCost(note2Float(xmlNode));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Cost-Per-Unit//defu:Unit-Price");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    otherFuel.setUnitPrice(note2Float(xmlNode));
                    otherFuel.setFixedCostSelected(dictServiceMgr.getDictItem(DictType.BILL_UNITS_AT_THIS_PRICE, 1.0));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Total-Cost//defu:Fixed-Charge");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    otherFuel.setFixedCost(note2Float(xmlNode));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Total-Cost//defu:Total-Cost");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    otherFuel.setTotalCost(note2Float(xmlNode));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Prices-Include-VAT");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    otherFuel.setVatable(note2YesNo(xmlNode));
                    
                    xpathSelector = DocumentHelper.createXPath("//defu:Unusual-Energy-Using-Item-Description");
        			xpathSelector.setNamespaceURIs(nameSpaceMap);
        			xmlNode = (Element)xpathSelector.selectSingleNode(_FuelBillNode);
                    otherFuel.setUnusualEnergyUsingable(note2YesNo(xmlNode));

                    otherFuelServiceMgr.addOtherFuel(otherFuel);
                }

            }
            if(billDataEle != null)
            {
            	billDataServiceMgr.addBillDataEle(billDataEle);
            }
		}
		
		
		if(gdipLigDoc != null)
		{
			// TODO 第三方 web service 创建report, 存在GDIP的情况 imp...
		}
		
		return report;
	}
	
	private String heightOrLow(int fuel)
    {
        if (fuel == 40 || fuel == 44)
        {
            return "H";
        }
        else if (fuel == 41 || fuel == 45)
        {
            return "L";
        }
        else
        {
            return "H";
        }
        
    }
	
	private YesNo note2YesNo(Element node)
	{
		if(node == null)
		{
			return YesNo.No;
		}
		if(node.getTextTrim().equals("true"))
		{
			return YesNo.Yes;
		}
		return YesNo.No;
	}
	
	private Integer note2Int(Element node)
	{
		if(node == null)
		{
			return null;
		}
		return Integer.parseInt(node.getTextTrim());
	}
	private Float note2Float(Element node)
	{
		if(node == null)
		{
			return null;
		}
		return Float.parseFloat(node.getTextTrim());
	}
	private Double note2Double(Element node)
	{
		if(node == null)
		{
			return null;
		}
		return Double.parseDouble(node.getTextTrim());
	}
	private DictItem note2Ditem(Element node, DictType dictType)
	{
		if(node == null)
		{
			return null;
		}
		return dictServiceMgr.getDictItem(dictType, node.getTextTrim());
	}
	
	private Report getReportByOaLig(Document oaLigDoc)
	{
		Assert.notNull(oaLigDoc);
		Report report = new Report();
		Element rootElt = oaLigDoc.getRootElement();
		String defNamespace = rootElt.getNamespaceURI();
		XPath xpathSelector;
		Map<String, String> nameSpaceMap = new HashMap<String, String>();
		if(defNamespace != null)
		{
			nameSpaceMap.put("defu", defNamespace);
		}
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Inspection-Date");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element inspection_Date = (Element)xpathSelector.selectSingleNode(oaLigDoc);
		if(inspection_Date != null)
		{
			report.setInspectionDate(DateUtil.string2Date(inspection_Date.getTextTrim(), DateUtil.PATTERN_DATE));
		}
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Completion-Date");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element completion_Date = (Element)xpathSelector.selectSingleNode(oaLigDoc);
		if(completion_Date != null)
		{
			report.setCompletionDate(DateUtil.string2Date(completion_Date.getTextTrim(), DateUtil.PATTERN_DATE));
		}
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Registration-Date");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element registration_Date = (Element)xpathSelector.selectSingleNode(oaLigDoc);
		if(registration_Date != null)
		{
			report.setRegistrationDate(DateUtil.string2Date(registration_Date.getTextTrim(), DateUtil.PATTERN_DATE));
		}
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Related-Party-Disclosure-Number");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element related_Party_Disclosure_Number = (Element)xpathSelector.selectSingleNode(oaLigDoc);
		if(related_Party_Disclosure_Number != null)
		{
			report.setRelatedPartyDisclosure(this.landmarkDefineServiceMgr.getRelatedPartyDisclosure(Integer.parseInt(related_Party_Disclosure_Number.getTextTrim()), Language.EN));
		}

		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Address-Line-1");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element address_Line_1 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
		if(address_Line_1 != null)
		{
			report.setAddress1(address_Line_1.getTextTrim());
		}
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Address-Line-2");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element address_Line_2 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
		if(address_Line_2 != null)
		{
			report.setAddress2(address_Line_2.getTextTrim());
		}
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Address-Line-3");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element address_Line_3 = (Element)xpathSelector.selectSingleNode(oaLigDoc);
		if(address_Line_3 != null)
		{
			report.setAddress3(address_Line_3.getTextTrim());
		}
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Post-Town");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element post_Town = (Element)xpathSelector.selectSingleNode(oaLigDoc);
		if(post_Town != null)
		{
			report.setPosttown(post_Town.getTextTrim());
		}
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Postcode");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element postcode = (Element)xpathSelector.selectSingleNode(oaLigDoc);
		if(postcode != null)
		{
			report.setPostcode(postcode.getTextTrim());
		}
		return report;
	}
	
	//解析EAW的报告
	@SuppressWarnings("unchecked")
	private Report saveEAW_v992(Document doc,String dbfileName,User user,Report temReport)  throws ObjectDuplicateException
	{
		Report report = new Report();
		report.setReportStatus(ReportStatus.In_Process);
		report.setReportXmlFile(dbfileName);
		report.setRelatedPartyDisclosure(temReport.getRelatedPartyDisclosure());
		report.setInspectionDate(temReport.getInspectionDate());
		report.setCompletionDate(temReport.getCompletionDate());
		report.setRegistrationDate(temReport.getRegistrationDate());
		report.setUploadWay(temReport.getUploadWay());
		report.setEpcVersion(temReport.getEpcVersion());
		report.setCompany(temReport.getCompany());
		report.setCompanyJobRef(temReport.getCompanyJobRef());
		Element rootElt = doc.getRootElement();//获取根节点
		String defNamespace = rootElt.getNamespaceURI();
		XPath xpathSelector;
		Map<String, String> nameSpaceMap = new HashMap<String, String>();
		//处理名称空间
		if(defNamespace != null)
		{
			nameSpaceMap.put("defu", defNamespace);
		}
		for(String prefix : ReportConfig.prefixs){
			String namespace = null;
			namespace = rootElt.getNamespaceForPrefix(prefix) != null ? rootElt.getNamespaceForPrefix(prefix).getURI() : null;
			if(namespace != null){
				nameSpaceMap.put(prefix, namespace);
			}
		}
		//处理名称空间
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Country-Code");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element countryCode = (Element)xpathSelector.selectSingleNode(doc);
		ReportLocation reportLocation = ReportLocation.EAW;
		if(countryCode != null && !countryCode.getTextTrim().equals("")){
			reportLocation = ReportLocation.valueOf(countryCode.getTextTrim());
		}
		report.setReportLocation(reportLocation);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:RRN");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element rrn = (Element)xpathSelector.selectSingleNode(doc);
		report.setRrn(rrn != null ? rrn.getTextTrim() : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Report-Type");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element reportType = (Element)xpathSelector.selectSingleNode(doc);
		ReportType re = reportType != null ? (ReportType)EnumUtils.getByCode(Integer.parseInt(reportType.getTextTrim()), ReportType.class) : null;
		report.setReportType(reportType != null ? re : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Status");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element reportXmlStatus = (Element)xpathSelector.selectSingleNode(doc);
		ReportFileStatus rep = (ReportFileStatus)EnumUtils.getByCode(reportXmlStatus != null ?reportXmlStatus.getTextTrim():null, ReportFileStatus.class);
		report.setReportXmlStatus(reportXmlStatus != null ? rep : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Language-Code");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element languageCode = (Element)xpathSelector.selectSingleNode(doc);
		report.setLanguageCode(languageCode != null ? (Language)EnumUtils.getByCode(languageCode.getTextTrim(), Language.class) : null);
		
		if(user != null){
			report.setUser(user);
		}
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Transaction-Type");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element transactionType = (Element)xpathSelector.selectSingleNode(doc);
		TransactionType tr = transactionType != null ? (TransactionType)EnumUtils.getByCode(Integer.parseInt(transactionType.getTextTrim()), TransactionType.class) : null;
		report.setTransactionType(transactionType != null ? tr : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property-Type");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element propertyType = (Element)xpathSelector.selectSingleNode(doc);
		PropertyType pr = propertyType != null ? (PropertyType)EnumUtils.getByCode(Integer.parseInt(propertyType.getTextTrim()), PropertyType.class) : null;
		report.setPropertyType(propertyType != null ? pr : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Identification-Number/defu:Certificate-Number");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element eaFullName = (Element)xpathSelector.selectSingleNode(doc);
		report.setEaFullName(eaFullName != null ? eaFullName.getTextTrim() : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Name");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element eaCertificateName = (Element)xpathSelector.selectSingleNode(doc);
		report.setEaCertificateName(eaCertificateName != null ? eaCertificateName.getTextTrim() : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Address-Line-1");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element address1 = (Element)xpathSelector.selectSingleNode(doc);
		report.setAddress1(address1 != null ? address1.getTextTrim() : temReport.getAddress1());
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Address-Line-2");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element address2 = (Element)xpathSelector.selectSingleNode(doc);
		report.setAddress2(address2 != null ? address2.getTextTrim() : temReport.getAddress2());
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Address-Line-3");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element address3 = (Element)xpathSelector.selectSingleNode(doc);
		report.setAddress3(address3 != null ? address3.getTextTrim() : temReport.getAddress3());
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Post-Town");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element posttown = (Element)xpathSelector.selectSingleNode(doc);
		report.setPosttown(posttown != null ? posttown.getTextTrim() : temReport.getPosttown());
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Postcode");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element postcode = (Element)xpathSelector.selectSingleNode(doc);
		report.setPostcode(postcode != null ? postcode.getTextTrim() : temReport.getPostcode());
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:UPRN");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element uprn = (Element)xpathSelector.selectSingleNode(doc);
		report.setUprn(uprn != null ? UPRNPatcher.getFullUPRN(uprn.getTextTrim()) : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Energy-Source/defu:Mains-Gas");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element mainGas = (Element)xpathSelector.selectSingleNode(doc);
		if(mainGas != null && mainGas.getTextTrim() != null && mainGas.getTextTrim().equals("Y")){
			report.setRdsapMainGasAvailable(YesNo.Yes);
		}else{
			report.setRdsapMainGasAvailable(YesNo.No);
		}
		
		//新加开始
		xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Main-Heating-Details/defu:Main-Heating");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		List<Element> listElement = xpathSelector.selectNodes(doc);
		if(listElement.size() == 1){
			xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Main-Heating-Details/defu:Main-Heating/defu:Main-Fuel-Type");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element rdsapMhsFuel = (Element)xpathSelector.selectSingleNode(doc);
			report.setRdsapMhsFuel(rdsapMhsFuel != null ? Integer.parseInt(rdsapMhsFuel.getTextTrim()) : null);
			
			report.setRdsapSmhsFuel(null);
		}else if(listElement.size() == 2){
			xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Main-Heating-Details/defu:Main-Heating[1]/defu:Main-Fuel-Type");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element rdsapMhsFuel = (Element)xpathSelector.selectSingleNode(doc);
			report.setRdsapMhsFuel(rdsapMhsFuel != null ? Integer.parseInt(rdsapMhsFuel.getTextTrim()) : null);
			
			xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Main-Heating-Details/defu:Main-Heating[2]/defu:Main-Fuel-Type");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element rdsapSmhsFuel = (Element)xpathSelector.selectSingleNode(doc);
			report.setRdsapSmhsFuel(rdsapSmhsFuel != null ? Integer.parseInt(rdsapSmhsFuel.getTextTrim()) : null);
		}
		else{
			report.setRdsapMhsFuel(null);
			
			report.setRdsapSmhsFuel(null);
		}
		xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Secondary-Fuel-Type");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element rdsapShsFuel = (Element)xpathSelector.selectSingleNode(doc);
		report.setRdsapShsFuel(rdsapShsFuel != null ? Integer.parseInt(rdsapShsFuel.getTextTrim()) : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Water-Heating-Fuel");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element rdsapWhsFuel = (Element)xpathSelector.selectSingleNode(doc);
		report.setRdsapWhsFuel(rdsapWhsFuel != null ? Integer.parseInt(rdsapWhsFuel.getTextTrim()) : null);
		//新加结束
		
		xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:Habitable-Room-Count");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element habitableRoomCountE = (Element)xpathSelector.selectSingleNode(doc);
		int habitableRoomCount = Integer.parseInt(habitableRoomCountE.getTextTrim());
		report.setHabitableRoomCount(habitableRoomCount);
		
		report.setInsertTime(new Date());
		report.setUpdateTime(new Date());
		report.setOaRrn(RRNBuilder.buildDRrn(RRNBuilder.DReportType.GDOA_TYPE, report.getUprn(), report.getInspectionDate()));
		GdsapEvalReport gdsapEvalReport = BeanUtils.user2gdsapUsrUser(report);
		gdsapEvalReportMapper.insert(gdsapEvalReport);
		
		//Improvement开始(Suggested-Improvement)
		Improvement improvement =new Improvement();
		
		xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		List<Element> listHeat = xpathSelector.selectNodes(doc);
		for(int i=1;i<listHeat.size()+1;i++){
			xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Improvement-Category");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element improvementCategory = (Element)xpathSelector.selectSingleNode(doc);
			RecommendationCategoryCode r = improvementCategory != null ? (RecommendationCategoryCode)EnumUtils.getByCode(Integer.parseInt(improvementCategory.getTextTrim()), RecommendationCategoryCode.class) : null;
			improvement.setImprovementCategory(improvementCategory != null ? r : null);
			
			xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Green-Deal-Category");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element greenDealCategory = (Element)xpathSelector.selectSingleNode(doc);
			GreenDealCategoryCode g = greenDealCategory != null ? (GreenDealCategoryCode)EnumUtils.getByCode(greenDealCategory.getTextTrim(), GreenDealCategoryCode.class) : null;
			improvement.setGreenDealCategory(greenDealCategory != null ? g : null);
			
			improvement.setScope(ImprovementScope.Suggested);
			
			xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Typical-Saving");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element typicalSaving = (Element)xpathSelector.selectSingleNode(doc);
			improvement.setTypicalSaving(typicalSaving != null ? Float.parseFloat(typicalSaving.getTextTrim()) : null);
			
			xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Indicative-Cost");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element indicativeCost = (Element)xpathSelector.selectSingleNode(doc);
			improvement.setIndicativeCost(indicativeCost != null ? indicativeCost.getTextTrim() : null);
			
			xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Energy-Performance-Rating");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element energyPerformanceRating = (Element)xpathSelector.selectSingleNode(doc);
			improvement.setEnergyPerformanceRating(energyPerformanceRating != null ? Float.parseFloat(energyPerformanceRating.getTextTrim()) : null);
			
			xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Environmental-Impact-Rating");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element environmentalImpactRating = (Element)xpathSelector.selectSingleNode(doc);
			improvement.setEnvironmentalImpactRating(environmentalImpactRating != null ? Float.parseFloat(environmentalImpactRating.getTextTrim()) : null);
			
			if(gdsapEvalReport != null){
				report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
				this._boTodo(gdsapEvalReport,report);
				improvement.setReport(report);
			}
			GdsapEvalImprovement gdsapEvalImprovement = BeanUtils.improvementToGdsImprovement(improvement);
			gdsapEvalImprovementMapper.insert(gdsapEvalImprovement);
		}
		
		//Improvement开始(Alternative-Improvements)<Alternative-Improvements>
		xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		List<Element> listElements = xpathSelector.selectNodes(doc);
		if(!listElements.isEmpty()){
			for(int i=1;i<listElements.size()+1;i++){
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Improvement-Category");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element improvementCategory = (Element)xpathSelector.selectSingleNode(doc);
				RecommendationCategoryCode r = improvementCategory != null ? (RecommendationCategoryCode)EnumUtils.getByCode(improvementCategory.getTextTrim(), RecommendationCategoryCode.class) : null;
				improvement.setImprovementCategory(improvementCategory != null ? r : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Green-Deal-Category");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element greenDealCategory = (Element)xpathSelector.selectSingleNode(doc);
				GreenDealCategoryCode g = greenDealCategory != null ? (GreenDealCategoryCode)EnumUtils.getByCode(greenDealCategory.getTextTrim(), GreenDealCategoryCode.class) : null;
				improvement.setGreenDealCategory(greenDealCategory != null ? g : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Improvement-Type");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element improvementType = (Element)xpathSelector.selectSingleNode(doc);
				GreenDealCategoryCode g1 = improvementType != null ? (GreenDealCategoryCode)EnumUtils.getByCode(improvementType.getTextTrim(), GreenDealCategoryCode.class) : null;
				improvement.setGreenDealCategory(improvementType != null ? g1 : null);
				
				improvement.setScope(ImprovementScope.Alternative);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Typical-Saving");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element typicalSaving = (Element)xpathSelector.selectSingleNode(doc);
				improvement.setTypicalSaving(typicalSaving != null ? Float.parseFloat(typicalSaving.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Indicative-Cost");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element indicativeCost = (Element)xpathSelector.selectSingleNode(doc);
				improvement.setIndicativeCost(indicativeCost != null ? indicativeCost.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Energy-Performance-Rating");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyPerformanceRating = (Element)xpathSelector.selectSingleNode(doc);
				improvement.setEnergyPerformanceRating(energyPerformanceRating != null ? Float.parseFloat(energyPerformanceRating.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Environmental-Impact-Rating");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element environmentalImpactRating = (Element)xpathSelector.selectSingleNode(doc);
				improvement.setEnvironmentalImpactRating(environmentalImpactRating != null ? Float.parseFloat(environmentalImpactRating.getTextTrim()) : null);
				
				if(gdsapEvalReport != null){
					report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
					this._boTodo(gdsapEvalReport,report);
					improvement.setReport(report);
				}
				GdsapEvalImprovement gdsapEvalImprovement = BeanUtils.improvementToGdsImprovement(improvement);
				gdsapEvalImprovementMapper.insert(gdsapEvalImprovement);
			}
		}
		        //EpcEADetail(start)
		        GdsapEvalEpcEaDetail epc = new GdsapEvalEpcEaDetail();
		        epc.setReportId(gdsapEvalReport.getId());
		        
		        xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Scheme-Name");
	        	xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element schemeNameElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setSchemeName(schemeNameElement != null ? schemeNameElement.getTextTrim() : null);
		        
		        xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Scheme-Web-Site");
	        	xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element schemeWebSiteElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setSchemeWebSite(schemeWebSiteElement != null ? schemeWebSiteElement.getTextTrim() : null);
		        
	        	xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Identification-Number/defu:Certificate-Number");
        		xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element certificateNumberElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setCertificateNumber(certificateNumberElement != null ? certificateNumberElement.getTextTrim() : null);
		        
		        xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Name");
	        	xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element fullNameElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setFullName(fullNameElement != null ? fullNameElement.getTextTrim() : null);
		        
		        xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Notify-Lodgement");
	        	xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element notifyLodgementElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setNotifyLodgement(notifyLodgementElement != null ? notifyLodgementElement.getTextTrim() : null);
		        
	        	xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Contact-Address/defu:Address-Line-1");
        		xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element contactAddress1Element = (Element)xpathSelector.selectSingleNode(doc);
				epc.setContactAddress1(contactAddress1Element != null ? contactAddress1Element.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Contact-Address/defu:Address-Line-2");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element contactAddress2Element = (Element)xpathSelector.selectSingleNode(doc);
				epc.setContactAddress2(contactAddress2Element != null ? contactAddress2Element.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Contact-Address/defu:Address-Line-3");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element contactAddress3Element = (Element)xpathSelector.selectSingleNode(doc);
				epc.setContactAddress3(contactAddress3Element != null ? contactAddress3Element.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Contact-Address/defu:Post-Town");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element posttownElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setPosttown(posttownElement != null ? posttownElement.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Contact-Address/defu:Postcode");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element postcodeElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setPostcode(postcodeElement != null ? postcodeElement.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Web-Site");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element websiteElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setWebsite(websiteElement != null ? websiteElement.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:E-Mail");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element emailElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setEmail(emailElement != null ? emailElement.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Fax");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element faxElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setFax(faxElement != null ? faxElement.getTextTrim() : null);

				xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Telephone");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element telephoneElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setTelephone(telephoneElement != null ? telephoneElement.getTextTrim() : null);

				xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Company-Name");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element companyNameElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setCompanyName(companyNameElement != null ? companyNameElement.getTextTrim() : null);
				// this line -----------------------------------------------------------------------------------------------------
				xpathSelector = DocumentHelper.createXPath("//defu:Insurance-Details/defu:Insurer");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element insurerElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setInsurer(insurerElement != null ? insurerElement.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Insurance-Details/defu:Policy-No");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element policyNoElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setPolicyNo(policyNoElement != null ? policyNoElement.getTextTrim() : null);
					
				xpathSelector = DocumentHelper.createXPath("//defu:Insurance-Details/defu:Effective-Date");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element effectiveDateElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setEffectiveDate(effectiveDateElement != null ? DateUtils.convert(effectiveDateElement.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Insurance-Details/defu:Expiry-Date");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element expiryDateElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setExpiryDate(expiryDateElement != null ? DateUtils.convert(expiryDateElement.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Insurance-Details/defu:PI-Limit");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element piLimitElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setPiLimit(piLimitElement != null ? piLimitElement.getTextTrim() : null);
				
				gdsapEvalEpcEaDetailMapper.insert(epc);
				
    			//EpcEADetail(start)
		
		        //EnergyUse(HIP:Energy-Use)开始
		        EnergyUse energyUse = new EnergyUse();
		        
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Energy-Rating-Average");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyRatingAverage = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyRatingAverage(energyRatingAverage != null ? Integer.parseInt(energyRatingAverage.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Energy-Rating-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyRatingCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyRatingCurrent(energyRatingCurrent != null ? Integer.parseInt(energyRatingCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Energy-Rating-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyRatingPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyRatingPotential(energyRatingPotential != null ? Integer.parseInt(energyRatingPotential.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Environmental-Impact-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element environmentalImpactCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnvironmentalImpactCurrent(environmentalImpactCurrent != null ? Integer.parseInt(environmentalImpactCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Environmental-Impact-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element environmentalImpactPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnvironmentalImpactPotential(environmentalImpactPotential != null ? Integer.parseInt(environmentalImpactPotential.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Energy-Consumption-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyConsumptionCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyConsumptionCurrent(energyConsumptionCurrent != null ? Float.parseFloat(energyConsumptionCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Energy-Consumption-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyConsumptionPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyConsumptionPotential(energyConsumptionPotential != null ? Float.parseFloat(energyConsumptionPotential.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:CO2-Emissions-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element co2EmissionsCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setCo2EmissionsCurrent(co2EmissionsCurrent != null ? Float.parseFloat(co2EmissionsCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:CO2-Emissions-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element co2EmissionsPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setCo2EmissionsPotential(co2EmissionsPotential != null ? Float.parseFloat(co2EmissionsPotential.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:CO2-Emissions-Current-Per-Floor-Area");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element co2EmissionsCurrentPerFloorArea = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setCo2EmissionsCurrentPerFloorArea(co2EmissionsCurrentPerFloorArea != null ? Float.parseFloat(co2EmissionsCurrentPerFloorArea.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Lighting-Cost-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element lightingCostCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setLightingCostCurrent(lightingCostCurrent != null ? Float.parseFloat(lightingCostCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Lighting-Cost-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element lightingCostPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setLightingCostPotential(lightingCostPotential != null ? Float.parseFloat(lightingCostPotential.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Heating-Cost-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element heatingCostCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setHeatingCostCurrent(heatingCostCurrent != null ? Float.parseFloat(heatingCostCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Heating-Cost-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element heatingCostPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setHeatingCostPotential(heatingCostPotential != null ? Float.parseFloat(heatingCostPotential.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Hot-Water-Cost-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element hotWaterCostCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setHotWaterCostCurrent(hotWaterCostCurrent != null ? Float.parseFloat(hotWaterCostCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Hot-Water-Cost-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element hotWaterCostPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setHotWaterCostPotential(hotWaterCostPotential != null ? Float.parseFloat(hotWaterCostPotential.getTextTrim()) : null);
				
				if(gdsapEvalReport != null){
					report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
					this._boTodo(gdsapEvalReport,report);
					energyUse.setId(report);
				}
				gdsapEvalEnergyUseMapper.insert(BeanUtils.energyUsetoGdsapEvalEnergyUse(energyUse));
				
				//GdsapEvalHeatProportion开始
				HeatProportion heatProportion = new HeatProportion();
				if(habitableRoomCount > 25)
				{
					habitableRoomCount = 25;
				}
				if(habitableRoomCount > 1){
					RoomType roomType = (RoomType)EnumUtils.getByCode(0, RoomType.class);
					RoomType roomType1 = (RoomType)EnumUtils.getByCode(1, RoomType.class);
					YesNo main1 = YesNo.No;
					YesNo main2 = YesNo.No;
					YesNo secondary = YesNo.No;
					YesNo heatedP = YesNo.No;
					YesNo notable = YesNo.No;
					heatProportion.setMain1(main1);
					heatProportion.setMain2(main2);
					heatProportion.setSecondary(secondary);
					heatProportion.setHeatedPartially(heatedP);
					heatProportion.setNotable(notable);
					heatProportion.setRoomScope(roomType);
					if(gdsapEvalReport != null){
						report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
						this._boTodo(gdsapEvalReport,report);
						heatProportion.setReportId(report);
					}
					gdsapEvalHeatProportionMapper.insert(BeanUtils.heatProportionToGdsap(heatProportion));
					for(int j = 0 ; j < habitableRoomCount-1 ; j++){
						heatProportion.setRoomScope(roomType1);
						heatProportion.setMain1(main1);
						heatProportion.setMain2(main2);
						heatProportion.setSecondary(secondary);
						heatProportion.setHeatedPartially(heatedP);
						heatProportion.setNotable(notable);
						if(gdsapEvalReport != null){
							report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
							this._boTodo(gdsapEvalReport,report);
							heatProportion.setReportId(report);
						}
						gdsapEvalHeatProportionMapper.insert(BeanUtils.heatProportionToGdsap(heatProportion));
					}
				}else{
					RoomType roomType = (RoomType)EnumUtils.getByCode(0, RoomType.class);
					heatProportion.setRoomScope(roomType);
					YesNo main1 = YesNo.No;
					YesNo main2 = YesNo.No;
					YesNo secondary = YesNo.No;
					YesNo heatedP = YesNo.No;
					YesNo notable = YesNo.No;
					heatProportion.setMain1(main1);
					heatProportion.setMain2(main2);
					heatProportion.setSecondary(secondary);
					heatProportion.setHeatedPartially(heatedP);
					heatProportion.setNotable(notable);
					if(gdsapEvalReport != null){
						report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
						this._boTodo(gdsapEvalReport,report);
						heatProportion.setReportId(report);
					}
					gdsapEvalHeatProportionMapper.insert(BeanUtils.heatProportionToGdsap(heatProportion));
				}
				return report;
	}
	
	//解析EAW的报告
	@SuppressWarnings("unchecked")
	private Report saveEAW_v991(Document doc,String dbfileName,User user,Report temReport)  throws ObjectDuplicateException
	{
		Report report = new Report();
		report.setReportStatus(ReportStatus.In_Process);
		report.setReportXmlFile(dbfileName);
		report.setReportLocation(ReportLocation.EAW);
		report.setRelatedPartyDisclosure(temReport.getRelatedPartyDisclosure());
		report.setInspectionDate(temReport.getInspectionDate());
		report.setCompletionDate(temReport.getCompletionDate());
		report.setRegistrationDate(temReport.getRegistrationDate());
		report.setUploadWay(temReport.getUploadWay());
		report.setEpcVersion(temReport.getEpcVersion());
		report.setCompany(temReport.getCompany());
		report.setCompanyJobRef(temReport.getCompanyJobRef());
		Element rootElt = doc.getRootElement();//获取根节点
		String defNamespace = rootElt.getNamespaceURI();
		XPath xpathSelector;
		Map<String, String> nameSpaceMap = new HashMap<String, String>();
		//处理名称空间
		if(defNamespace != null)
		{
			nameSpaceMap.put("defu", defNamespace);
		}
		for(String prefix : ReportConfig.prefixs){
			String namespace = null;
			namespace = rootElt.getNamespaceForPrefix(prefix) != null ? rootElt.getNamespaceForPrefix(prefix).getURI() : null;
			if(namespace != null){
				nameSpaceMap.put(prefix, namespace);
			}
		}
				
		//针对前缀为SAP和HIP的名称空间定义到子节点上的情况处理
		if(nameSpaceMap.get("SAP") == null || nameSpaceMap.get("HIP") == null){
			nameSpaceMap = null;
			nameSpaceMap = getNameSpaceMap(doc);
		}
		//处理名称空间
		
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:RRN");//将装有prefix和URI映射关系的HashMap放入到xpath选择器中
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element rrn = (Element)xpathSelector.selectSingleNode(doc);
		report.setRrn(rrn != null ? rrn.getTextTrim() : null);
		
//		GdsapEvalReport tmpModel = this.gdsapEvalReportMapper.findByRRN(rrn.getTextTrim());
//		if (tmpModel != null)
//		{
//			throw new ObjectDuplicateException();
//		}
		
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Report-Type");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element reportType = (Element)xpathSelector.selectSingleNode(doc);
		ReportType re = reportType != null ? (ReportType)EnumUtils.getByCode(Integer.parseInt(reportType.getTextTrim()), ReportType.class) : null;
		report.setReportType(reportType != null ? re : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Status");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element reportXmlStatus = (Element)xpathSelector.selectSingleNode(doc);
		ReportFileStatus rep = (ReportFileStatus)EnumUtils.getByCode(reportXmlStatus != null ?reportXmlStatus.getTextTrim():null, ReportFileStatus.class);
		report.setReportXmlStatus(reportXmlStatus != null ? rep : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Language-Code");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element languageCode = (Element)xpathSelector.selectSingleNode(doc);
		report.setLanguageCode(languageCode != null ? (Language)EnumUtils.getByCode(languageCode.getTextTrim(), Language.class) : null);
		
		if(user != null){
			report.setUser(user);
		}
		
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Transaction-Type");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element transactionType = (Element)xpathSelector.selectSingleNode(doc);
		TransactionType tr = transactionType != null ? (TransactionType)EnumUtils.getByCode(Integer.parseInt(transactionType.getTextTrim()), TransactionType.class) : null;
		report.setTransactionType(transactionType != null ? tr : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Property-Type");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element propertyType = (Element)xpathSelector.selectSingleNode(doc);
		PropertyType pr = propertyType != null ? (PropertyType)EnumUtils.getByCode(Integer.parseInt(propertyType.getTextTrim()), PropertyType.class) : null;
		report.setPropertyType(propertyType != null ? pr : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Home-Inspector/SAP:Identification-Number/SAP:Certificate-Number");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element eaFullName = (Element)xpathSelector.selectSingleNode(doc);
		report.setEaFullName(eaFullName != null ? eaFullName.getTextTrim() : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Home-Inspector/SAP:Name");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element eaCertificateName = (Element)xpathSelector.selectSingleNode(doc);
		report.setEaCertificateName(eaCertificateName != null ? eaCertificateName.getTextTrim() : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Property/HIP:Address/HIP:Address-Line-1");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element address1 = (Element)xpathSelector.selectSingleNode(doc);
		report.setAddress1(address1 != null ? address1.getTextTrim() : temReport.getAddress1());
		
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Property/HIP:Address/HIP:Address-Line-2");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element address2 = (Element)xpathSelector.selectSingleNode(doc);
		report.setAddress2(address2 != null ? address2.getTextTrim() : temReport.getAddress2());
		
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Property/HIP:Address/HIP:Address-Line-3");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element address3 = (Element)xpathSelector.selectSingleNode(doc);
		report.setAddress3(address3 != null ? address3.getTextTrim() : temReport.getAddress3());
		
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Property/HIP:Address/HIP:Post-Town");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element posttown = (Element)xpathSelector.selectSingleNode(doc);
		report.setPosttown(posttown != null ? posttown.getTextTrim() : temReport.getPosttown());
		
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Property/HIP:Address/HIP:Postcode");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element postcode = (Element)xpathSelector.selectSingleNode(doc);
		report.setPostcode(postcode != null ? postcode.getTextTrim() : temReport.getPostcode());
		
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Property/HIP:UPRN");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element uprn = (Element)xpathSelector.selectSingleNode(doc);
		report.setUprn(uprn != null ? UPRNPatcher.getFullUPRN(uprn.getTextTrim()) : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Energy-Source/HIP:Main-Gas");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element mainGas = (Element)xpathSelector.selectSingleNode(doc);
		if(mainGas != null && mainGas.getTextTrim() != null && mainGas.getTextTrim().equals("Y")){
			report.setRdsapMainGasAvailable(YesNo.Yes);
		}else{
			report.setRdsapMainGasAvailable(YesNo.No);
		}
		
		//新加开始
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		List<Element> listElement = xpathSelector.selectNodes(doc);
		if(listElement.size() == 1){
			xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating/HIP:Main-Fuel-Type");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element rdsapMhsFuel = (Element)xpathSelector.selectSingleNode(doc);
			report.setRdsapMhsFuel(rdsapMhsFuel != null ? Integer.parseInt(rdsapMhsFuel.getTextTrim()) : null);
			
			report.setRdsapSmhsFuel(null);
		}else if(listElement.size() == 2){
			xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating[1]/HIP:Main-Fuel-Type");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element rdsapMhsFuel = (Element)xpathSelector.selectSingleNode(doc);
			report.setRdsapMhsFuel(rdsapMhsFuel != null ? Integer.parseInt(rdsapMhsFuel.getTextTrim()) : null);
			
			xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating[2]/HIP:Main-Fuel-Type");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element rdsapSmhsFuel = (Element)xpathSelector.selectSingleNode(doc);
			report.setRdsapSmhsFuel(rdsapSmhsFuel != null ? Integer.parseInt(rdsapSmhsFuel.getTextTrim()) : null);
		}
		else{
			report.setRdsapMhsFuel(null);
			
			report.setRdsapSmhsFuel(null);
		}
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Secondary-Fuel-Type");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element rdsapShsFuel = (Element)xpathSelector.selectSingleNode(doc);
		report.setRdsapShsFuel(rdsapShsFuel != null ? Integer.parseInt(rdsapShsFuel.getTextTrim()) : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Water-Heating-Fuel");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element rdsapWhsFuel = (Element)xpathSelector.selectSingleNode(doc);
		report.setRdsapWhsFuel(rdsapWhsFuel != null ? Integer.parseInt(rdsapWhsFuel.getTextTrim()) : null);

		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:SAP-Data/HIP:SAP-Property-Details/HIP:Habitable-Room-Count");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element habitableRoomCountE = (Element)xpathSelector.selectSingleNode(doc);
		int habitableRoomCount = Integer.parseInt(habitableRoomCountE.getTextTrim());
		report.setHabitableRoomCount(habitableRoomCount);
		
		report.setInsertTime(new Date());
		report.setUpdateTime(new Date());
		report.setOaRrn(RRNBuilder.buildDRrn(RRNBuilder.DReportType.GDOA_TYPE, report.getUprn(), report.getInspectionDate()));
		GdsapEvalReport gdsapEvalReport = BeanUtils.user2gdsapUsrUser(report);
		gdsapEvalReportMapper.insert(gdsapEvalReport);
		
		//Improvement开始(Suggested-Improvement)
		Improvement improvement = new Improvement();
		
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		List<Element> listHeat = xpathSelector.selectNodes(doc);
		for(int i=1;i<listHeat.size()+1;i++){
			xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Improvement-Category");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element improvementCategory = (Element)xpathSelector.selectSingleNode(doc);
			RecommendationCategoryCode r = improvementCategory != null ? (RecommendationCategoryCode)EnumUtils.getByCode(Integer.parseInt(improvementCategory.getTextTrim()), RecommendationCategoryCode.class) : null;
			improvement.setImprovementCategory(improvementCategory != null ? r : null);
			
			xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Green-Deal-Category");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element greenDealCategory = (Element)xpathSelector.selectSingleNode(doc);
			GreenDealCategoryCode g = greenDealCategory != null ? (GreenDealCategoryCode)EnumUtils.getByCode(greenDealCategory.getTextTrim(), GreenDealCategoryCode.class) : null;
			improvement.setGreenDealCategory(greenDealCategory != null ? g : null);
			
			improvement.setScope(ImprovementScope.Suggested);
			
			xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Typical-Saving");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element typicalSaving = (Element)xpathSelector.selectSingleNode(doc);
			improvement.setTypicalSaving(typicalSaving != null ? Float.parseFloat(typicalSaving.getTextTrim()) : null);
			
			xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Indicative-Cost");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element indicativeCost = (Element)xpathSelector.selectSingleNode(doc);
			improvement.setIndicativeCost(indicativeCost != null ? indicativeCost.getTextTrim() : null);
			
			xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Energy-Performance-Rating");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element energyPerformanceRating = (Element)xpathSelector.selectSingleNode(doc);
			improvement.setEnergyPerformanceRating(energyPerformanceRating != null ? Float.parseFloat(energyPerformanceRating.getTextTrim()) : null);
			
			xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Environmental-Impact-Rating");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element environmentalImpactRating = (Element)xpathSelector.selectSingleNode(doc);
			improvement.setEnvironmentalImpactRating(environmentalImpactRating != null ? Float.parseFloat(environmentalImpactRating.getTextTrim()) : null);
			
			if(gdsapEvalReport != null){
				report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
				this._boTodo(gdsapEvalReport,report);
				improvement.setReport(report);
			}
			GdsapEvalImprovement gdsapEvalImprovement = BeanUtils.improvementToGdsImprovement(improvement);
			gdsapEvalImprovementMapper.insert(gdsapEvalImprovement);
		}
		
		//Improvement开始(Alternative-Improvements)HIP:Alternative-Improvements
		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		List<Element> listElements = xpathSelector.selectNodes(doc);
		if(!listElements.isEmpty()){
			for(int i=1;i<listElements.size()+1;i++){
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Improvement-Category");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element improvementCategory = (Element)xpathSelector.selectSingleNode(doc);
				RecommendationCategoryCode r = improvementCategory != null ? (RecommendationCategoryCode)EnumUtils.getByCode(improvementCategory.getTextTrim(), RecommendationCategoryCode.class) : null;
				improvement.setImprovementCategory(improvementCategory != null ? r : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Green-Deal-Category");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element greenDealCategory = (Element)xpathSelector.selectSingleNode(doc);
				GreenDealCategoryCode g = greenDealCategory != null ? (GreenDealCategoryCode)EnumUtils.getByCode(greenDealCategory.getTextTrim(), GreenDealCategoryCode.class) : null;
				improvement.setGreenDealCategory(greenDealCategory != null ? g : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Improvement-Type");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element improvementType = (Element)xpathSelector.selectSingleNode(doc);
				GreenDealCategoryCode g1 = improvementType != null ? (GreenDealCategoryCode)EnumUtils.getByCode(improvementType.getTextTrim(), GreenDealCategoryCode.class) : null;
				improvement.setGreenDealCategory(improvementType != null ? g1 : null);
				
				improvement.setScope(ImprovementScope.Alternative);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Typical-Saving");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element typicalSaving = (Element)xpathSelector.selectSingleNode(doc);
				improvement.setTypicalSaving(typicalSaving != null ? Float.parseFloat(typicalSaving.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Indicative-Cost");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element indicativeCost = (Element)xpathSelector.selectSingleNode(doc);
				improvement.setIndicativeCost(indicativeCost != null ? indicativeCost.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Energy-Performance-Rating");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyPerformanceRating = (Element)xpathSelector.selectSingleNode(doc);
				improvement.setEnergyPerformanceRating(energyPerformanceRating != null ? Float.parseFloat(energyPerformanceRating.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Environmental-Impact-Rating");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element environmentalImpactRating = (Element)xpathSelector.selectSingleNode(doc);
				improvement.setEnvironmentalImpactRating(environmentalImpactRating != null ? Float.parseFloat(environmentalImpactRating.getTextTrim()) : null);
				
				if(gdsapEvalReport != null){
					report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
					this._boTodo(gdsapEvalReport,report);
					improvement.setReport(report);
				}
				GdsapEvalImprovement gdsapEvalImprovement = BeanUtils.improvementToGdsImprovement(improvement);
				gdsapEvalImprovementMapper.insert(gdsapEvalImprovement);
			}
		}
		        //EpcEADetail(start)
		        GdsapEvalEpcEaDetail epc = new GdsapEvalEpcEaDetail();
		        epc.setReportId(gdsapEvalReport.getId());
		        
		        xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Home-Inspector/SAP:Scheme-Name");
	        	xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element schemeNameElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setSchemeName(schemeNameElement != null ? schemeNameElement.getTextTrim() : null);
		        
		        xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Home-Inspector/SAP:Scheme-Web-Site");
	        	xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element schemeWebSiteElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setSchemeWebSite(schemeWebSiteElement != null ? schemeWebSiteElement.getTextTrim() : null);
		        
	        	xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Home-Inspector/SAP:Identification-Number/SAP:Certificate-Number");
        		xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element certificateNumberElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setCertificateNumber(certificateNumberElement != null ? certificateNumberElement.getTextTrim() : null);
		        
		        xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Home-Inspector/SAP:Name");
	        	xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element fullNameElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setFullName(fullNameElement != null ? fullNameElement.getTextTrim() : null);
		        
		        xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Home-Inspector/SAP:Notify-Lodgement");
	        	xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element notifyLodgementElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setNotifyLodgement(notifyLodgementElement != null ? notifyLodgementElement.getTextTrim() : null);
		        
	        	xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Home-Inspector/SAP:Contact-Address/HIP:Address-Line-1");
        		xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element contactAddress1Element = (Element)xpathSelector.selectSingleNode(doc);
				epc.setContactAddress1(contactAddress1Element != null ? contactAddress1Element.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Home-Inspector/SAP:Contact-Address/HIP:Address-Line-2");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element contactAddress2Element = (Element)xpathSelector.selectSingleNode(doc);
				epc.setContactAddress2(contactAddress2Element != null ? contactAddress2Element.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Home-Inspector/SAP:Contact-Address/HIP:Address-Line-3");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element contactAddress3Element = (Element)xpathSelector.selectSingleNode(doc);
				epc.setContactAddress3(contactAddress3Element != null ? contactAddress3Element.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Home-Inspector/SAP:Contact-Address/HIP:Post-Town");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element posttownElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setPosttown(posttownElement != null ? posttownElement.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Home-Inspector/SAP:Contact-Address/HIP:Postcode");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element postcodeElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setPostcode(postcodeElement != null ? postcodeElement.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Home-Inspector/SAP:Web-Site");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element websiteElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setWebsite(websiteElement != null ? websiteElement.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Home-Inspector/SAP:E-Mail");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element emailElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setEmail(emailElement != null ? emailElement.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Home-Inspector/SAP:Fax");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element faxElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setFax(faxElement != null ? faxElement.getTextTrim() : null);

				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Home-Inspector/SAP:Telephone");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element telephoneElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setTelephone(telephoneElement != null ? telephoneElement.getTextTrim() : null);

				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Report-Header/SAP:Home-Inspector/SAP:Company-Name");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element companyNameElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setCompanyName(companyNameElement != null ? companyNameElement.getTextTrim() : null);

				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Insurance-Details/SAP:Insurer");
					xpathSelector.setNamespaceURIs(nameSpaceMap);
					Element insurerElement = (Element)xpathSelector.selectSingleNode(doc);
					epc.setInsurer(insurerElement != null ? insurerElement.getTextTrim() : null);
					
					xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Insurance-Details/SAP:Policy-No");
					xpathSelector.setNamespaceURIs(nameSpaceMap);
					Element policyNoElement = (Element)xpathSelector.selectSingleNode(doc);
					epc.setPolicyNo(policyNoElement != null ? policyNoElement.getTextTrim() : null);
						
					xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Insurance-Details/SAP:Effective-Date");
					xpathSelector.setNamespaceURIs(nameSpaceMap);
					Element effectiveDateElement = (Element)xpathSelector.selectSingleNode(doc);
					epc.setEffectiveDate(effectiveDateElement != null ? DateUtils.convert(effectiveDateElement.getTextTrim()) : null);
					
					xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Insurance-Details/SAP:Expiry-Date");
					xpathSelector.setNamespaceURIs(nameSpaceMap);
					Element expiryDateElement = (Element)xpathSelector.selectSingleNode(doc);
					epc.setExpiryDate(expiryDateElement != null ? DateUtils.convert(expiryDateElement.getTextTrim()) : null);
					
					xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Insurance-Details/SAP:PI-Limit");
					xpathSelector.setNamespaceURIs(nameSpaceMap);
					Element piLimitElement = (Element)xpathSelector.selectSingleNode(doc);
					epc.setPiLimit(piLimitElement != null ? piLimitElement.getTextTrim() : null);
					
					gdsapEvalEpcEaDetailMapper.insert(epc);
				
    			//EpcEADetail(start)
		
		        //EnergyUse(HIP:Energy-Use)开始
		        EnergyUse energyUse = new EnergyUse();
		        
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Energy-Use/HIP:Energy-Rating-Average");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyRatingAverage = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyRatingAverage(energyRatingAverage != null ? Integer.parseInt(energyRatingAverage.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Energy-Use/HIP:Energy-Rating-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyRatingCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyRatingCurrent(energyRatingCurrent != null ? Integer.parseInt(energyRatingCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Energy-Use/HIP:Energy-Rating-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyRatingPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyRatingPotential(energyRatingPotential != null ? Integer.parseInt(energyRatingPotential.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Energy-Use/HIP:Environmental-Impact-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element environmentalImpactCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnvironmentalImpactCurrent(environmentalImpactCurrent != null ? Integer.parseInt(environmentalImpactCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Energy-Use/HIP:Environmental-Impact-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element environmentalImpactPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnvironmentalImpactPotential(environmentalImpactPotential != null ? Integer.parseInt(environmentalImpactPotential.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Energy-Use/HIP:Energy-Consumption-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyConsumptionCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyConsumptionCurrent(energyConsumptionCurrent != null ? Float.parseFloat(energyConsumptionCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Energy-Use/HIP:Energy-Consumption-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyConsumptionPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyConsumptionPotential(energyConsumptionPotential != null ? Float.parseFloat(energyConsumptionPotential.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Energy-Use/HIP:CO2-Emissions-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element co2EmissionsCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setCo2EmissionsCurrent(co2EmissionsCurrent != null ? Float.parseFloat(co2EmissionsCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Energy-Use/HIP:CO2-Emissions-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element co2EmissionsPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setCo2EmissionsPotential(co2EmissionsPotential != null ? Float.parseFloat(co2EmissionsPotential.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Energy-Use/HIP:CO2-Emissions-Current-Per-Floor-Area");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element co2EmissionsCurrentPerFloorArea = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setCo2EmissionsCurrentPerFloorArea(co2EmissionsCurrentPerFloorArea != null ? Float.parseFloat(co2EmissionsCurrentPerFloorArea.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Energy-Use/HIP:Lighting-Cost-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element lightingCostCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setLightingCostCurrent(lightingCostCurrent != null ? Float.parseFloat(lightingCostCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Energy-Use/HIP:Lighting-Cost-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element lightingCostPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setLightingCostPotential(lightingCostPotential != null ? Float.parseFloat(lightingCostPotential.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Energy-Use/HIP:Heating-Cost-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element heatingCostCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setHeatingCostCurrent(heatingCostCurrent != null ? Float.parseFloat(heatingCostCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Energy-Use/HIP:Heating-Cost-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element heatingCostPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setHeatingCostPotential(heatingCostPotential != null ? Float.parseFloat(heatingCostPotential.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Energy-Use/HIP:Hot-Water-Cost-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element hotWaterCostCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setHotWaterCostCurrent(hotWaterCostCurrent != null ? Float.parseFloat(hotWaterCostCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Energy-Use/HIP:Hot-Water-Cost-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element hotWaterCostPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setHotWaterCostPotential(hotWaterCostPotential != null ? Float.parseFloat(hotWaterCostPotential.getTextTrim()) : null);
				
				if(gdsapEvalReport != null){
					report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
					this._boTodo(gdsapEvalReport,report);
					energyUse.setId(report);
				}
				gdsapEvalEnergyUseMapper.insert(BeanUtils.energyUsetoGdsapEvalEnergyUse(energyUse));
				
				//GdsapEvalHeatProportion开始
				HeatProportion heatProportion = new HeatProportion();
				if(habitableRoomCount > 25)
				{
					habitableRoomCount = 25;
				}
				if(habitableRoomCount > 1){
					RoomType roomType = (RoomType)EnumUtils.getByCode(0, RoomType.class);
					RoomType roomType1 = (RoomType)EnumUtils.getByCode(1, RoomType.class);
					YesNo main1 = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					YesNo main2 = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					YesNo secondary = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					YesNo heatedP = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					YesNo notable = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					heatProportion.setMain1(main1);
					heatProportion.setMain2(main2);
					heatProportion.setSecondary(secondary);
					heatProportion.setHeatedPartially(heatedP);
					heatProportion.setNotable(notable);
					heatProportion.setRoomScope(roomType);
					if(gdsapEvalReport != null){
						report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
						this._boTodo(gdsapEvalReport,report);
						heatProportion.setReportId(report);
					}
					gdsapEvalHeatProportionMapper.insert(BeanUtils.heatProportionToGdsap(heatProportion));
					for(int j = 0 ; j < habitableRoomCount-1 ; j++){
						heatProportion.setRoomScope(roomType1);
						heatProportion.setMain1(main1);
						heatProportion.setMain2(main2);
						heatProportion.setSecondary(secondary);
						heatProportion.setHeatedPartially(heatedP);
						heatProportion.setNotable(notable);
						if(gdsapEvalReport != null){
							report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
							this._boTodo(gdsapEvalReport,report);
							heatProportion.setReportId(report);
						}
						gdsapEvalHeatProportionMapper.insert(BeanUtils.heatProportionToGdsap(heatProportion));
					}
				}else{
					RoomType roomType = (RoomType)EnumUtils.getByCode(0, RoomType.class);
					heatProportion.setRoomScope(roomType);
					YesNo main1 = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					YesNo main2 = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					YesNo secondary = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					YesNo heatedP = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					YesNo notable = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					heatProportion.setMain1(main1);
					heatProportion.setMain2(main2);
					heatProportion.setSecondary(secondary);
					heatProportion.setHeatedPartially(heatedP);
					heatProportion.setNotable(notable);
					if(gdsapEvalReport != null){
						report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
						this._boTodo(gdsapEvalReport,report);
						heatProportion.setReportId(report);
					}
					gdsapEvalHeatProportionMapper.insert(BeanUtils.heatProportionToGdsap(heatProportion));
				}
				return report;
	}
	
	//解析SCT的报告
	@SuppressWarnings("unchecked")
	private Report saveSCT_v991(Document doc,String dbfileName,User user,Report temReport)  throws ObjectDuplicateException
	{
		Report report = new Report();
		//In_Process
		report.setReportStatus(ReportStatus.In_Process);
		report.setReportXmlFile(dbfileName);
		report.setReportLocation(ReportLocation.SCT);
		report.setRelatedPartyDisclosure(temReport.getRelatedPartyDisclosure());
		report.setInspectionDate(temReport.getInspectionDate());
		report.setCompletionDate(temReport.getCompletionDate());
		report.setRegistrationDate(temReport.getRegistrationDate());
		report.setUploadWay(temReport.getUploadWay());
		report.setEpcVersion(temReport.getEpcVersion());
		report.setCompany(temReport.getCompany());
		report.setCompanyJobRef(temReport.getCompanyJobRef());
		
		Element rootElt = doc.getRootElement();//获取根节点
		String defNamespace = rootElt.getNamespaceURI();
		String xsiNamespace = rootElt.getNamespaceForPrefix("xsi") != null ? rootElt.getNamespaceForPrefix("xsi").getURI() : null;
		String hipNamespace = rootElt.getNamespaceForPrefix("HIP") != null ? rootElt.getNamespaceForPrefix("HIP").getURI() : null;
		String sapNamespace = rootElt.getNamespaceForPrefix("SAP") != null ? rootElt.getNamespaceForPrefix("SAP").getURI() : null;
		String csNamespace = rootElt.getNamespaceForPrefix("CS") != null ? rootElt.getNamespaceForPrefix("CS").getURI() : null;
		
		XPath xpathSelector;
		Map<String, String> nameSpaceMap = new HashMap<String, String>();
		
		if(xsiNamespace != null)
		{
			nameSpaceMap.put("xsi", xsiNamespace);
		}
		if(defNamespace != null)
		{
			nameSpaceMap.put("defu", defNamespace);
		}
		if(sapNamespace != null)
		{
			nameSpaceMap.put("SAP", sapNamespace);
		}
		if(hipNamespace != null)
		{
			nameSpaceMap.put("HIP", hipNamespace);
		}
		if(csNamespace != null)
		{
			nameSpaceMap.put("CS", csNamespace);
		}
		
		if(sapNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:RRN");//将装有prefix和URI映射关系的HashMap放入到xpath选择器中
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:RRN");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element rrn = (Element)xpathSelector.selectSingleNode(doc);
		report.setRrn(rrn != null ? rrn.getTextTrim() : null);
		
//		GdsapEvalReport tmpModel = this.gdsapEvalReportMapper.findByRRN(rrn.getTextTrim());
//		if (tmpModel != null)
//		{
//			throw new ObjectDuplicateException();
//		}
		
		if(sapNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Report-Type");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Report-Type");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element reportType = (Element)xpathSelector.selectSingleNode(doc);
		ReportType re = (ReportType)EnumUtils.getByCode(Integer.parseInt(reportType.getTextTrim()), ReportType.class);
		report.setReportType(reportType != null ? re : null);
		
		if(sapNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Status");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Status");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element reportXmlStatus = (Element)xpathSelector.selectSingleNode(doc);
		ReportFileStatus rep = (ReportFileStatus)EnumUtils.getByCode(reportXmlStatus.getTextTrim(), ReportFileStatus.class);
		report.setReportXmlStatus(reportXmlStatus != null ? rep : null);
		
		if(sapNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Language-Code");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Language-Code");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element languageCode = (Element)xpathSelector.selectSingleNode(doc);
		report.setLanguageCode(languageCode != null ? (Language)EnumUtils.getByCode(languageCode.getTextTrim(), Language.class) : null);
		
		if(user != null){
			report.setUser(user);
		}
		
		if(sapNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Transaction-Type");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Transaction-Type");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element transactionType = (Element)xpathSelector.selectSingleNode(doc);
		TransactionType tr = transactionType != null ? (TransactionType)EnumUtils.getByCode(Integer.parseInt(transactionType.getTextTrim()), TransactionType.class) : null;
		report.setTransactionType(transactionType != null ? tr : null);
		
		if(sapNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Property-Type");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property-Type");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element propertyType = (Element)xpathSelector.selectSingleNode(doc);
		PropertyType pr = propertyType != null ? (PropertyType)EnumUtils.getByCode(Integer.parseInt(propertyType.getTextTrim()), PropertyType.class) : null;
		report.setPropertyType(propertyType != null ? pr : null);
		
		if(sapNamespace != null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/HIP:Identification-Number/HIP:Membership-Number");
		}else if(sapNamespace != null && hipNamespace == null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/defu:Identification-Number/defu:Membership-Number");
		}else if(sapNamespace == null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/HIP:Identification-Number/HIP:Membership-Number");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/defu:Identification-Number/defu:Membership-Number");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element eaFullName = (Element)xpathSelector.selectSingleNode(doc);
		report.setEaFullName(eaFullName != null ? eaFullName.getTextTrim() : null);
		
		if(sapNamespace != null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/HIP:Certification-Scheme/HIP:Scheme-Name");
		}else if(sapNamespace != null && hipNamespace == null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/defu:Certification-Scheme/defu:Scheme-Name");
		}else if(sapNamespace == null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/HIP:Certification-Scheme/HIP:Scheme-Name");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/defu:Certification-Scheme/defu:Scheme-Name");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element eaCertificateName = (Element)xpathSelector.selectSingleNode(doc);
		report.setEaCertificateName(eaCertificateName != null ? eaCertificateName.getTextTrim() : null);
		
		if(sapNamespace != null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Property/HIP:Address/HIP:Address-Line-1");
		}else if(sapNamespace != null && hipNamespace == null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Property/defu:Address/defu:Address-Line-1");
		}else if(sapNamespace == null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/HIP:Address/HIP:Address-Line-1");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Address-Line-1");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element address1 = (Element)xpathSelector.selectSingleNode(doc);
		report.setAddress1(address1 != null ? address1.getTextTrim() : temReport.getAddress1());
		
		if(sapNamespace != null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Property/HIP:Address/HIP:Address-Line-2");
		}else if(sapNamespace != null && hipNamespace == null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Property/defu:Address/defu:Address-Line-2");
		}else if(sapNamespace == null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/HIP:Address/HIP:Address-Line-2");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Address-Line-2");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element address2 = (Element)xpathSelector.selectSingleNode(doc);
		report.setAddress2(address2 != null ? address2.getTextTrim() : temReport.getAddress2());
		
		if(sapNamespace != null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Property/HIP:Address/HIP:Address-Line-3");
		}else if(sapNamespace != null && hipNamespace == null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Property/defu:Address/defu:Address-Line-3");
		}else if(sapNamespace == null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/HIP:Address/HIP:Address-Line-3");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Address-Line-3");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element address3 = (Element)xpathSelector.selectSingleNode(doc);
		report.setAddress3(address3 != null ? address3.getTextTrim() : temReport.getAddress3());
		
		if(sapNamespace != null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Property/HIP:Address/HIP:Post-Town");
		}else if(sapNamespace != null && hipNamespace == null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Property/defu:Address/defu:Post-Town");
		}else if(sapNamespace == null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/HIP:Address/HIP:Post-Town");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Post-Town");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element posttown = (Element)xpathSelector.selectSingleNode(doc);
		report.setPosttown(posttown != null ? posttown.getTextTrim() : temReport.getPosttown());
		
		if(sapNamespace != null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Property/HIP:Address/HIP:Postcode");
		}else if(sapNamespace != null && hipNamespace == null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Property/defu:Address/defu:Postcode");
		}else if(sapNamespace == null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/HIP:Address/HIP:Postcode");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Postcode");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element postcode = (Element)xpathSelector.selectSingleNode(doc);
		report.setPostcode(postcode != null ? postcode.getTextTrim() : temReport.getPostcode());
		
		if(sapNamespace != null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Property/HIP:UPRN");
		}else if(sapNamespace != null && hipNamespace == null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Property/defu:UPRN");
		}else if(sapNamespace == null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/HIP:UPRN");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:UPRN");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element uprn = (Element)xpathSelector.selectSingleNode(doc);
		report.setUprn(uprn != null ? UPRNPatcher.getFullUPRN(uprn.getTextTrim()) : null);
		
		if(sapNamespace != null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Energy-Source/HIP:Main-Gas");
		}else if(sapNamespace != null && hipNamespace == null){
			xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/defu:SAP-Property-Details/defu:SAP-Energy-Source/defu:Main-Gas");
		}else if(sapNamespace == null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Energy-Source/HIP:Main-Gas");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Energy-Source/defu:Main-Gas");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element mainGas = (Element)xpathSelector.selectSingleNode(doc);
		if(mainGas != null && mainGas.getTextTrim() != null && mainGas.getTextTrim().equals("Y")){
			report.setRdsapMainGasAvailable(YesNo.Yes);
		}else{
			report.setRdsapMainGasAvailable(YesNo.No);
		}
		
		//新加开始
		if(sapNamespace != null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating");
		}else if(sapNamespace != null && hipNamespace == null){
			xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Main-Heating-Details/defu:Main-Heating");
		}else if(sapNamespace == null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/HIP:Main-Heating-Details/defu:Main-Heating");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		List<Element> listElement = xpathSelector.selectNodes(doc);
		if(listElement.size() == 1){
			
			if(sapNamespace != null && hipNamespace != null){
				xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating/HIP:Main-Fuel-Type");
			}else if(sapNamespace != null && hipNamespace == null){
				xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Main-Heating-Details/defu:Main-Heating/defu:Main-Fuel-Type");
			}else if(sapNamespace == null && hipNamespace != null){
				xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating/HIP:Main-Fuel-Type");
			}else{
				xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Main-Heating-Details/defu:Main-Heating/HIP:Main-Fuel-Type");
			}
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element rdsapMhsFuel = (Element)xpathSelector.selectSingleNode(doc);
			report.setRdsapMhsFuel(rdsapMhsFuel != null ? Integer.parseInt(rdsapMhsFuel.getTextTrim()) : null);
			
			report.setRdsapSmhsFuel(null);
		}else if(listElement.size() == 2){
			
			if(sapNamespace != null && hipNamespace != null){
				xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating[1]/HIP:Main-Fuel-Type");
			}else if(sapNamespace != null && hipNamespace == null){
				xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Main-Heating-Details/defu:Main-Heating[1]/defu:Main-Fuel-Type");
			}else if(sapNamespace == null && hipNamespace != null){
				xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating[1]/HIP:Main-Fuel-Type");
			}else{
				xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Main-Heating-Details/defu:Main-Heating[1]/defu:Main-Fuel-Type");
			}
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element rdsapMhsFuel = (Element)xpathSelector.selectSingleNode(doc);
			report.setRdsapMhsFuel(rdsapMhsFuel != null ? Integer.parseInt(rdsapMhsFuel.getTextTrim()) : null);
			
			if(sapNamespace != null && hipNamespace != null){
				xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating[2]/HIP:Main-Fuel-Type");
			}else if(sapNamespace != null && hipNamespace == null){
				xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Main-Heating-Details/defu:Main-Heating[2]/defu:Main-Fuel-Type");
			}else if(sapNamespace == null && hipNamespace != null){
				xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating[2]/HIP:Main-Fuel-Type");
			}else{
				xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Main-Heating-Details/defu:Main-Heating[2]/defu:Main-Fuel-Type");
			}
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element rdsapSmhsFuel = (Element)xpathSelector.selectSingleNode(doc);
			report.setRdsapSmhsFuel(rdsapSmhsFuel != null ? Integer.parseInt(rdsapSmhsFuel.getTextTrim()) : null);
		}
		else{
			report.setRdsapMhsFuel(null);
			
			report.setRdsapSmhsFuel(null);
		}
		
		if(sapNamespace != null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Secondary-Fuel-Type");
		}else if(sapNamespace != null && hipNamespace == null){
			xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Secondary-Fuel-Type");
		}else if(sapNamespace == null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Secondary-Fuel-Type");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Secondary-Fuel-Type");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element rdsapShsFuel = (Element)xpathSelector.selectSingleNode(doc);
		report.setRdsapShsFuel(rdsapShsFuel != null ? Integer.parseInt(rdsapShsFuel.getTextTrim()) : null);
		
		if(sapNamespace != null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Water-Heating-Fuel");
		}else if(sapNamespace != null && hipNamespace == null){
			xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Water-Heating-Fuel");
		}else if(sapNamespace == null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Water-Heating-Fuel");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Water-Heating-Fuel");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element rdsapWhsFuel = (Element)xpathSelector.selectSingleNode(doc);
		report.setRdsapWhsFuel(rdsapWhsFuel != null ? Integer.parseInt(rdsapWhsFuel.getTextTrim()) : null);

		if(sapNamespace != null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/HIP:SAP-Property-Details/HIP:Habitable-Room-Count");
		}else if(sapNamespace != null && hipNamespace == null){
			xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/defu:SAP-Property-Details/defu:Habitable-Room-Count");
		}else if(sapNamespace == null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/HIP:SAP-Property-Details/HIP:Habitable-Room-Count");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:Habitable-Room-Count");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element habitableRoomCountE = (Element)xpathSelector.selectSingleNode(doc);
		int habitableRoomCount = Integer.parseInt(habitableRoomCountE.getTextTrim());
		report.setHabitableRoomCount(habitableRoomCount);
		
		report.setInsertTime(new Date());
		report.setUpdateTime(new Date());
		report.setOaRrn(RRNBuilder.buildDRrn(RRNBuilder.DReportType.GDOA_TYPE, report.getUprn(), report.getInspectionDate()));
		GdsapEvalReport gdsapEvalReport = BeanUtils.user2gdsapUsrUser(report);
		gdsapEvalReportMapper.insert(gdsapEvalReport);
		
		//Improvement开始(Suggested-Improvement)
		Improvement improvement =new Improvement();
//		xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement/HIP:Sequence");
//		xpathSelector.setNamespaceURIs(nameSpaceMap);
//		Element improvementCategory = (Element)xpathSelector.selectSingleNode(doc);
//		improvement.setImprovementCategory(improvementCategory != null ? RecommendationCategoryCode.valueOf(improvementCategory.getTextTrim()) : null);
		
		if(sapNamespace != null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement");
		}else if(sapNamespace != null && hipNamespace == null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement");
		}else if(sapNamespace == null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		List<Element> listHeat = xpathSelector.selectNodes(doc);
		for(int i=1;i<listHeat.size()+1;i++){
			if(sapNamespace != null && hipNamespace != null){
				xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Improvement-Category");
			}else if(sapNamespace != null && hipNamespace == null){
				xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Improvement-Category");
			}else if(sapNamespace == null && hipNamespace != null){
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Improvement-Category");
			}else{
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Improvement-Category");
			}
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element improvementCategory = (Element)xpathSelector.selectSingleNode(doc);
			RecommendationCategoryCode r = (RecommendationCategoryCode)EnumUtils.getByCode(Integer.parseInt(improvementCategory.getTextTrim()), RecommendationCategoryCode.class);
			improvement.setImprovementCategory(improvementCategory != null ? r : null);
			
			if(sapNamespace != null && hipNamespace != null){
				xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Green-Deal-Category");
			}else if(sapNamespace != null && hipNamespace == null){
				xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Green-Deal-Category");
			}else if(sapNamespace == null && hipNamespace != null){
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Green-Deal-Category");
			}else{
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Green-Deal-Category");
			}
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element greenDealCategory = (Element)xpathSelector.selectSingleNode(doc);
			GreenDealCategoryCode g = (GreenDealCategoryCode)EnumUtils.getByCode(greenDealCategory.getTextTrim(), GreenDealCategoryCode.class);
			improvement.setGreenDealCategory(greenDealCategory != null ? g : null);
			
			improvement.setScope(ImprovementScope.Suggested);
			
			if(sapNamespace != null && hipNamespace != null){
				xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Typical-Saving");
			}else if(sapNamespace != null && hipNamespace == null){
				xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Typical-Saving");
			}else if(sapNamespace == null && hipNamespace != null){
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Typical-Saving");
			}else{
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Typical-Saving");
			}
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element typicalSaving = (Element)xpathSelector.selectSingleNode(doc);
			improvement.setTypicalSaving(typicalSaving != null ? Float.parseFloat(typicalSaving.getTextTrim()) : null);
			
			if(sapNamespace != null && hipNamespace != null){
				xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Indicative-Cost");
			}else if(sapNamespace != null && hipNamespace == null){
				xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Indicative-Cost");
			}else if(sapNamespace == null && hipNamespace != null){
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Indicative-Cost");
			}else{
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Indicative-Cost");
			}
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element indicativeCost = (Element)xpathSelector.selectSingleNode(doc);
			improvement.setIndicativeCost(indicativeCost != null ? indicativeCost.getTextTrim() : null);
			
			if(sapNamespace != null && hipNamespace != null){
				xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Energy-Performance-Rating");
			}else if(sapNamespace != null && hipNamespace == null){
				xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Energy-Performance-Rating");
			}else if(sapNamespace == null && hipNamespace != null){
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Energy-Performance-Rating");
			}else{
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Energy-Performance-Rating");
			}
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element energyPerformanceRating = (Element)xpathSelector.selectSingleNode(doc);
			improvement.setEnergyPerformanceRating(energyPerformanceRating != null ? Float.parseFloat(energyPerformanceRating.getTextTrim()) : null);
			
			if(sapNamespace != null && hipNamespace != null){
				xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Environmental-Impact-Rating");
			}else if(sapNamespace != null && hipNamespace == null){
				xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Environmental-Impact-Rating");
			}else if(sapNamespace == null && hipNamespace != null){
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Suggested-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Environmental-Impact-Rating");
			}else{
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Environmental-Impact-Rating");
			}
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element environmentalImpactRating = (Element)xpathSelector.selectSingleNode(doc);
			improvement.setEnvironmentalImpactRating(environmentalImpactRating != null ? Float.parseFloat(environmentalImpactRating.getTextTrim()) : null);
			
			if(gdsapEvalReport != null){
				report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
				this._boTodo(gdsapEvalReport,report);
				improvement.setReport(report);
			}
			GdsapEvalImprovement gdsapEvalImprovement = BeanUtils.improvementToGdsImprovement(improvement);
			gdsapEvalImprovementMapper.insert(gdsapEvalImprovement);
		}
		
		//Improvement开始(Alternative-Improvements)HIP:Alternative-Improvements
		if(sapNamespace != null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement");
		}else if(sapNamespace != null && hipNamespace == null){
			xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement");
		}else if(sapNamespace == null && hipNamespace != null){
			xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement");
		}else{
			xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement");
		}
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		List<Element> listElements = xpathSelector.selectNodes(doc);
		if(!listElements.isEmpty()){
			for(int i=1;i<listElements.size()+1;i++){
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Improvement-Category");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Improvement-Category");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Improvement-Category");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Improvement-Category");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element improvementCategory = (Element)xpathSelector.selectSingleNode(doc);
				RecommendationCategoryCode r = improvementCategory != null ? (RecommendationCategoryCode)EnumUtils.getByCode(improvementCategory.getTextTrim(), RecommendationCategoryCode.class) : null;
				improvement.setImprovementCategory(improvementCategory != null ? r : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Green-Deal-Category");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Green-Deal-Category");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Green-Deal-Category");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Green-Deal-Category");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element greenDealCategory = (Element)xpathSelector.selectSingleNode(doc);
				GreenDealCategoryCode g = greenDealCategory != null ? (GreenDealCategoryCode)EnumUtils.getByCode(greenDealCategory.getTextTrim(), GreenDealCategoryCode.class) : null;
				improvement.setGreenDealCategory(greenDealCategory != null ? g : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Improvement-Type");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Improvement-Type");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Improvement-Type");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Improvement-Type");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element improvementType = (Element)xpathSelector.selectSingleNode(doc);
				GreenDealCategoryCode g1 = improvementType != null ? (GreenDealCategoryCode)EnumUtils.getByCode(improvementType.getTextTrim(), GreenDealCategoryCode.class) : null;
				improvement.setGreenDealCategory(improvementType != null ? g1 : null);
				
				improvement.setScope(ImprovementScope.Alternative);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Typical-Saving");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Typical-Saving");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Typical-Saving");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Typical-Saving");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element typicalSaving = (Element)xpathSelector.selectSingleNode(doc);
				improvement.setTypicalSaving(typicalSaving != null ? Float.parseFloat(typicalSaving.getTextTrim()) : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Indicative-Cost");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Indicative-Cost");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Indicative-Cost");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Indicative-Cost");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element indicativeCost = (Element)xpathSelector.selectSingleNode(doc);
				improvement.setIndicativeCost(indicativeCost != null ? indicativeCost.getTextTrim() : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Energy-Performance-Rating");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Energy-Performance-Rating");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Energy-Performance-Rating");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Energy-Performance-Rating");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyPerformanceRating = (Element)xpathSelector.selectSingleNode(doc);
				improvement.setEnergyPerformanceRating(energyPerformanceRating != null ? Float.parseFloat(energyPerformanceRating.getTextTrim()) : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Environmental-Impact-Rating");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Environmental-Impact-Rating");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Alternative-Improvements/HIP:Improvement"+"["+i+"]"+"/HIP:Environmental-Impact-Rating");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Environmental-Impact-Rating");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element environmentalImpactRating = (Element)xpathSelector.selectSingleNode(doc);
				improvement.setEnvironmentalImpactRating(environmentalImpactRating != null ? Float.parseFloat(environmentalImpactRating.getTextTrim()) : null);
				
				if(gdsapEvalReport != null){
					report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
					this._boTodo(gdsapEvalReport,report);
					improvement.setReport(report);
				}
				GdsapEvalImprovement gdsapEvalImprovement = BeanUtils.improvementToGdsImprovement(improvement);
				gdsapEvalImprovementMapper.insert(gdsapEvalImprovement);
			}
		}
		        //EpcEADetail(start)
		        GdsapEvalEpcEaDetail epc = new GdsapEvalEpcEaDetail();
		        epc.setReportId(gdsapEvalReport.getId());
		        
		    	if(sapNamespace != null && hipNamespace != null){
		    		xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/HIP:Certification-Scheme/HIP:Scheme-Name");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/defu:Certification-Scheme/defu:Scheme-Name");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/HIP:Certification-Scheme/HIP:Scheme-Name");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/defu:Certification-Scheme/defu:Scheme-Name");
				}
	        	xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element schemeNameElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setSchemeName(schemeNameElement != null ? schemeNameElement.getTextTrim() : null);
		        
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/HIP:Certification-Scheme/HIP:Web-Site");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/defu:Certification-Scheme/defu:Web-Site");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/HIP:Certification-Scheme/HIP:Web-Site");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/defu:Certification-Scheme/defu:Web-Site");
				}
	        	xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element schemeWebSiteElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setSchemeWebSite(schemeWebSiteElement != null ? schemeWebSiteElement.getTextTrim() : null);
		        
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/HIP:Identification-Number/HIP:Membership-Number");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/defu:Identification-Number/defu:Membership-Number");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/HIP:Identification-Number/HIP:Membership-Number");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/defu:Identification-Number/defu:Membership-Number");
				}
        		xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element certificateNumberElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setCertificateNumber(certificateNumberElement != null ? certificateNumberElement.getTextTrim() : null);
		        
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/HIP:Name/HIP:First-Name");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/defu:Name/defu:First-Name");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/HIP:Name/HIP:First-Name");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/defu:Name/defu:First-Name");
				}
	        	xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element firstNameElement = (Element)xpathSelector.selectSingleNode(doc);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/HIP:Name/HIP:Surname");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/defu:Name/defu:Surname");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/HIP:Name/HIP:Surname");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/defu:Name/defu:Surname");
				}
	        	xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element surNameElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setFullName(firstNameElement != null && surNameElement != null ? firstNameElement.getTextTrim() +" "+ surNameElement.getTextTrim() : null);
		        
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/HIP:Notify-Lodgement");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/defu:Notify-Lodgement");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/HIP:Notify-Lodgement");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/defu:Notify-Lodgement");
				}
	        	xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element notifyLodgementElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setNotifyLodgement(notifyLodgementElement != null ? notifyLodgementElement.getTextTrim() : null);
		        
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/HIP:Contact-Address/HIP:Address-Line-1");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/defu:Contact-Address/defu:Address-Line-1");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/HIP:Contact-Address/HIP:Address-Line-1");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/defu:Contact-Address/defu:Address-Line-1");
				}
        		xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element contactAddress1Element = (Element)xpathSelector.selectSingleNode(doc);
				epc.setContactAddress1(contactAddress1Element != null ? contactAddress1Element.getTextTrim() : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/HIP:Contact-Address/HIP:Address-Line-2");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/defu:Contact-Address/defu:Address-Line-2");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/HIP:Contact-Address/HIP:Address-Line-2");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/defu:Contact-Address/defu:Address-Line-2");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element contactAddress2Element = (Element)xpathSelector.selectSingleNode(doc);
				epc.setContactAddress2(contactAddress2Element != null ? contactAddress2Element.getTextTrim() : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/HIP:Contact-Address/HIP:Address-Line-3");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/defu:Contact-Address/defu:Address-Line-3");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/HIP:Contact-Address/HIP:Address-Line-3");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/defu:Contact-Address/defu:Address-Line-3");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element contactAddress3Element = (Element)xpathSelector.selectSingleNode(doc);
				epc.setContactAddress3(contactAddress3Element != null ? contactAddress3Element.getTextTrim() : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/HIP:Contact-Address/HIP:Post-Town");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/defu:Contact-Address/defu:Post-Town");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/HIP:Contact-Address/HIP:Post-Town");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/defu:Contact-Address/defu:Post-Town");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element posttownElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setPosttown(posttownElement != null ? posttownElement.getTextTrim() : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/HIP:Contact-Address/HIP:Postcode");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/defu:Contact-Address/defu:Postcode");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/HIP:Contact-Address/HIP:Postcode");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/defu:Contact-Address/defu:Postcode");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element postcodeElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setPostcode(postcodeElement != null ? postcodeElement.getTextTrim() : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/HIP:Web-Site");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/defu:Web-Site");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/HIP:Web-Site");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/defu:Web-Site");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element websiteElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setWebsite(websiteElement != null ? websiteElement.getTextTrim() : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/HIP:E-Mail");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/defu:E-Mail");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/HIP:E-Mail");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/defu:E-Mail");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element emailElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setEmail(emailElement != null ? emailElement.getTextTrim() : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/HIP:Fax");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/defu:Fax");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/HIP:Fax");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/defu:Fax");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element faxElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setFax(faxElement != null ? faxElement.getTextTrim() : null);

				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/HIP:Telephone");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/defu:Telephone");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/HIP:Telephone");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/defu:Telephone");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element telephoneElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setTelephone(telephoneElement != null ? telephoneElement.getTextTrim() : null);

				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/HIP:Company-Name");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Report-Header/SAP:Home-Inspector/defu:Company-Name");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/HIP:Company-Name");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Home-Inspector/defu:Company-Name");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element companyNameElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setCompanyName(companyNameElement != null ? companyNameElement.getTextTrim() : null);

				if(sapNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Insurance-Details/SAP:Insurer");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Insurance-Details/defu:Insurer");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element insurerElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setInsurer(insurerElement != null ? insurerElement.getTextTrim() : null);
				

				if(sapNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Insurance-Details/SAP:Policy-No");	
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Insurance-Details/defu:Policy-No");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element policyNoElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setPolicyNo(policyNoElement != null ? policyNoElement.getTextTrim() : null);
					
				if(sapNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Insurance-Details/SAP:Effective-Date");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Insurance-Details/defu:Effective-Date");	
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element effectiveDateElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setEffectiveDate(effectiveDateElement != null ? DateUtils.convert(effectiveDateElement.getTextTrim()) : null);
				
				if(sapNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Insurance-Details/SAP:Expiry-Date");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Insurance-Details/defu:Expiry-Date");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element expiryDateElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setExpiryDate(expiryDateElement != null ? DateUtils.convert(expiryDateElement.getTextTrim()) : null);
				
				if(sapNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Insurance-Details/SAP:PI-Limit");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Insurance-Details/defu:PI-Limit");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element piLimitElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setPiLimit(piLimitElement != null ? piLimitElement.getTextTrim() : null);
				
				gdsapEvalEpcEaDetailMapper.insert(epc);
    			//EpcEADetail(end)
		
		        //EnergyUse(HIP:Energy-Use)开始
		        EnergyUse energyUse = new EnergyUse();
		        
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Energy-Use/HIP:Energy-Rating-Average");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Energy-Use/defu:Energy-Rating-Average");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Energy-Use/HIP:Energy-Rating-Average");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Energy-Rating-Average");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyRatingAverage = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyRatingAverage(energyRatingAverage != null ? Integer.parseInt(energyRatingAverage.getTextTrim()) : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Energy-Use/HIP:Energy-Rating-Current");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Energy-Use/defu:Energy-Rating-Current");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Energy-Use/HIP:Energy-Rating-Current");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Energy-Rating-Current");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyRatingCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyRatingCurrent(energyRatingCurrent != null ? Integer.parseInt(energyRatingCurrent.getTextTrim()) : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Energy-Use/HIP:Energy-Rating-Potential");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Energy-Use/defu:Energy-Rating-Potential");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Energy-Use/HIP:Energy-Rating-Potential");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Energy-Rating-Potential");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyRatingPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyRatingPotential(energyRatingPotential != null ? Integer.parseInt(energyRatingPotential.getTextTrim()) : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Energy-Use/HIP:Environmental-Impact-Current");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Energy-Use/defu:Environmental-Impact-Current");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Energy-Use/HIP:Environmental-Impact-Current");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Environmental-Impact-Current");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element environmentalImpactCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnvironmentalImpactCurrent(environmentalImpactCurrent != null ? Integer.parseInt(environmentalImpactCurrent.getTextTrim()) : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Energy-Use/HIP:Environmental-Impact-Potential");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Energy-Use/defu:Environmental-Impact-Potential");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Energy-Use/HIP:Environmental-Impact-Potential");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Environmental-Impact-Potential");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element environmentalImpactPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnvironmentalImpactPotential(environmentalImpactPotential != null ? Integer.parseInt(environmentalImpactPotential.getTextTrim()) : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Energy-Use/HIP:Energy-Consumption-Current");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Energy-Use/defu:Energy-Consumption-Current");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Energy-Use/HIP:Energy-Consumption-Current");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Energy-Consumption-Current");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyConsumptionCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyConsumptionCurrent(energyConsumptionCurrent != null ? Float.parseFloat(energyConsumptionCurrent.getTextTrim()) : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Energy-Use/HIP:Energy-Consumption-Potential");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Energy-Use/defu:Energy-Consumption-Potential");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Energy-Use/HIP:Energy-Consumption-Potential");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Energy-Consumption-Potential");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyConsumptionPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyConsumptionPotential(energyConsumptionPotential != null ? Float.parseFloat(energyConsumptionPotential.getTextTrim()) : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Energy-Use/HIP:CO2-Emissions-Current");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Energy-Use/defu:CO2-Emissions-Current");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Energy-Use/HIP:CO2-Emissions-Current");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:CO2-Emissions-Current");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element co2EmissionsCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setCo2EmissionsCurrent(co2EmissionsCurrent != null ? Float.parseFloat(co2EmissionsCurrent.getTextTrim()) : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Energy-Use/HIP:CO2-Emissions-Potential");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Energy-Use/defu:CO2-Emissions-Potential");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Energy-Use/HIP:CO2-Emissions-Potential");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:CO2-Emissions-Potential");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element co2EmissionsPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setCo2EmissionsPotential(co2EmissionsPotential != null ? Float.parseFloat(co2EmissionsPotential.getTextTrim()) : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Energy-Use/HIP:CO2-Emissions-Current-Per-Floor-Area");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Energy-Use/defu:CO2-Emissions-Current-Per-Floor-Area");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Energy-Use/HIP:CO2-Emissions-Current-Per-Floor-Area");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:CO2-Emissions-Current-Per-Floor-Area");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element co2EmissionsCurrentPerFloorArea = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setCo2EmissionsCurrentPerFloorArea(co2EmissionsCurrentPerFloorArea != null ? Float.parseFloat(co2EmissionsCurrentPerFloorArea.getTextTrim()) : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Energy-Use/HIP:Lighting-Cost-Current");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Energy-Use/defu:Lighting-Cost-Current");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Energy-Use/HIP:Lighting-Cost-Current");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Lighting-Cost-Current");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element lightingCostCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setLightingCostCurrent(lightingCostCurrent != null ? Float.parseFloat(lightingCostCurrent.getTextTrim()) : null);
				
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Energy-Use/HIP:Lighting-Cost-Potential");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Energy-Use/defu:Lighting-Cost-Potential");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Energy-Use/HIP:Lighting-Cost-Potential");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Lighting-Cost-Potential");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element lightingCostPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setLightingCostPotential(lightingCostPotential != null ? Float.parseFloat(lightingCostPotential.getTextTrim()) : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Energy-Use/HIP:Heating-Cost-Current");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Energy-Use/defu:Heating-Cost-Current");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Energy-Use/HIP:Heating-Cost-Current");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Heating-Cost-Current");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element heatingCostCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setHeatingCostCurrent(heatingCostCurrent != null ? Float.parseFloat(heatingCostCurrent.getTextTrim()) : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Energy-Use/HIP:Heating-Cost-Potential");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Energy-Use/defu:Heating-Cost-Potential");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Energy-Use/HIP:Heating-Cost-Potential");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Heating-Cost-Potential");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element heatingCostPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setHeatingCostPotential(heatingCostPotential != null ? Float.parseFloat(heatingCostPotential.getTextTrim()) : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Energy-Use/HIP:Hot-Water-Cost-Current");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Energy-Use/defu:Hot-Water-Cost-Current");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Energy-Use/HIP:Hot-Water-Cost-Current");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Hot-Water-Cost-Current");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element hotWaterCostCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setHotWaterCostCurrent(hotWaterCostCurrent != null ? Float.parseFloat(hotWaterCostCurrent.getTextTrim()) : null);
				
				if(sapNamespace != null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/HIP:Energy-Use/HIP:Hot-Water-Cost-Potential");
				}else if(sapNamespace != null && hipNamespace == null){
					xpathSelector = DocumentHelper.createXPath("//SAP:Energy-Assessment/defu:Energy-Use/defu:Hot-Water-Cost-Potential");
				}else if(sapNamespace == null && hipNamespace != null){
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/HIP:Energy-Use/HIP:Hot-Water-Cost-Potential");
				}else{
					xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Hot-Water-Cost-Potential");
				}
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element hotWaterCostPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setHotWaterCostPotential(hotWaterCostPotential != null ? Float.parseFloat(hotWaterCostPotential.getTextTrim()) : null);
				
				if(gdsapEvalReport != null){
					report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
					this._boTodo(gdsapEvalReport,report);
					energyUse.setId(report);
				}
				gdsapEvalEnergyUseMapper.insert(BeanUtils.energyUsetoGdsapEvalEnergyUse(energyUse));
				
				//GdsapEvalHeatProportion开始
				HeatProportion heatProportion = new HeatProportion();
				if(habitableRoomCount > 25)
				{
					habitableRoomCount = 25;
				}
				if(habitableRoomCount > 1){
					RoomType roomType = (RoomType)EnumUtils.getByCode(0, RoomType.class);
					RoomType roomType1 = (RoomType)EnumUtils.getByCode(1, RoomType.class);
					YesNo main1 = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					YesNo main2 = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					YesNo secondary = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					YesNo heatedP = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					YesNo notable = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					heatProportion.setMain1(main1);
					heatProportion.setMain2(main2);
					heatProportion.setSecondary(secondary);
					heatProportion.setHeatedPartially(heatedP);
					heatProportion.setNotable(notable);
					heatProportion.setRoomScope(roomType);
					if(gdsapEvalReport != null){
						report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
						this._boTodo(gdsapEvalReport,report);
						heatProportion.setReportId(report);
					}
					gdsapEvalHeatProportionMapper.insert(BeanUtils.heatProportionToGdsap(heatProportion));
					for(int j = 0 ; j < habitableRoomCount-1 ; j++){
						heatProportion.setRoomScope(roomType1);
						heatProportion.setMain1(main1);
						heatProportion.setMain2(main2);
						heatProportion.setSecondary(secondary);
						heatProportion.setHeatedPartially(heatedP);
						heatProportion.setNotable(notable);
						if(gdsapEvalReport != null){
							report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
							this._boTodo(gdsapEvalReport,report);
							heatProportion.setReportId(report);
						}
						gdsapEvalHeatProportionMapper.insert(BeanUtils.heatProportionToGdsap(heatProportion));
					}
				}else{
					RoomType roomType = RoomType.LiveRoom;
					heatProportion.setRoomScope(roomType);
					YesNo main1 = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					YesNo main2 = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					YesNo secondary = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					YesNo heatedP = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					YesNo notable = (YesNo)EnumUtils.getByCode(0, YesNo.class);
					heatProportion.setMain1(main1);
					heatProportion.setMain2(main2);
					heatProportion.setSecondary(secondary);
					heatProportion.setHeatedPartially(heatedP);
					heatProportion.setNotable(notable);
					if(gdsapEvalReport != null){
						report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
						this._boTodo(gdsapEvalReport,report);
						heatProportion.setReportId(report);
					}
					gdsapEvalHeatProportionMapper.insert(BeanUtils.heatProportionToGdsap(heatProportion));
				}
				return report;
	}
	
	//解析SCT的报告
	@SuppressWarnings("unchecked")
	private Report saveSCT_v992(Document doc,String dbfileName,User user,Report temReport)  throws ObjectDuplicateException
	{
		Report report = new Report();
		report.setReportStatus(ReportStatus.In_Process);
		report.setReportXmlFile(dbfileName);
		report.setRelatedPartyDisclosure(temReport.getRelatedPartyDisclosure());
		report.setInspectionDate(temReport.getInspectionDate());
		report.setCompletionDate(temReport.getCompletionDate());
		report.setRegistrationDate(temReport.getRegistrationDate());
		report.setUploadWay(temReport.getUploadWay());
		report.setEpcVersion(temReport.getEpcVersion());
		report.setCompany(temReport.getCompany());
		report.setCompanyJobRef(temReport.getCompanyJobRef());
		Element rootElt = doc.getRootElement();//获取根节点
		String defNamespace = rootElt.getNamespaceURI();
		XPath xpathSelector;
		Map<String, String> nameSpaceMap = new HashMap<String, String>();
		//处理名称空间
		if(defNamespace != null)
		{
			nameSpaceMap.put("defu", defNamespace);
		}
		for(String prefix : ReportConfig.prefixs){
			String namespace = null;
			namespace = rootElt.getNamespaceForPrefix(prefix) != null ? rootElt.getNamespaceForPrefix(prefix).getURI() : null;
			if(namespace != null){
				nameSpaceMap.put(prefix, namespace);
			}
		}
		//处理名称空间
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Country-Code");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element countryCode = (Element)xpathSelector.selectSingleNode(doc);
		ReportLocation reportLocation = ReportLocation.EAW;
		if(countryCode != null && !countryCode.getTextTrim().equals("")){
			reportLocation = ReportLocation.valueOf(countryCode.getTextTrim());
		}
		report.setReportLocation(reportLocation);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:RRN");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element rrn = (Element)xpathSelector.selectSingleNode(doc);
		report.setRrn(rrn != null ? rrn.getTextTrim() : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Report-Type");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element reportType = (Element)xpathSelector.selectSingleNode(doc);
		ReportType re = reportType != null ? (ReportType)EnumUtils.getByCode(Integer.parseInt(reportType.getTextTrim()), ReportType.class) : null;
		report.setReportType(reportType != null ? re : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Status");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element reportXmlStatus = (Element)xpathSelector.selectSingleNode(doc);
		ReportFileStatus rep = (ReportFileStatus)EnumUtils.getByCode(reportXmlStatus != null ?reportXmlStatus.getTextTrim():null, ReportFileStatus.class);
		report.setReportXmlStatus(reportXmlStatus != null ? rep : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Language-Code");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element languageCode = (Element)xpathSelector.selectSingleNode(doc);
		report.setLanguageCode(languageCode != null ? (Language)EnumUtils.getByCode(languageCode.getTextTrim(), Language.class) : null);
		
		if(user != null){
			report.setUser(user);
		}
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Transaction-Type");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element transactionType = (Element)xpathSelector.selectSingleNode(doc);
		TransactionType tr = transactionType != null ? (TransactionType)EnumUtils.getByCode(Integer.parseInt(transactionType.getTextTrim()), TransactionType.class) : null;
		report.setTransactionType(transactionType != null ? tr : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property-Type");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element propertyType = (Element)xpathSelector.selectSingleNode(doc);
		PropertyType pr = propertyType != null ? (PropertyType)EnumUtils.getByCode(Integer.parseInt(propertyType.getTextTrim()), PropertyType.class) : null;
		report.setPropertyType(propertyType != null ? pr : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Identification-Number/defu:Membership-Number");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element eaFullName = (Element)xpathSelector.selectSingleNode(doc);
		report.setEaFullName(eaFullName != null ? eaFullName.getTextTrim() : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Name");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element eaCertificateName = (Element)xpathSelector.selectSingleNode(doc);
		report.setEaCertificateName(eaCertificateName != null ? eaCertificateName.getTextTrim() : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Address-Line-1");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element address1 = (Element)xpathSelector.selectSingleNode(doc);
		report.setAddress1(address1 != null ? address1.getTextTrim() : temReport.getAddress1());
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Address-Line-2");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element address2 = (Element)xpathSelector.selectSingleNode(doc);
		report.setAddress2(address2 != null ? address2.getTextTrim() : temReport.getAddress2());
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Address-Line-3");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element address3 = (Element)xpathSelector.selectSingleNode(doc);
		report.setAddress3(address3 != null ? address3.getTextTrim() : temReport.getAddress3());
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Post-Town");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element posttown = (Element)xpathSelector.selectSingleNode(doc);
		report.setPosttown(posttown != null ? posttown.getTextTrim() : temReport.getPosttown());
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:Address/defu:Postcode");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element postcode = (Element)xpathSelector.selectSingleNode(doc);
		report.setPostcode(postcode != null ? postcode.getTextTrim() : temReport.getPostcode());
		
		xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Property/defu:UPRN");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element uprn = (Element)xpathSelector.selectSingleNode(doc);
		report.setUprn(uprn != null ? UPRNPatcher.getFullUPRN(uprn.getTextTrim()) : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Energy-Source/defu:Mains-Gas");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element mainGas = (Element)xpathSelector.selectSingleNode(doc);
		if(mainGas != null && mainGas.getTextTrim() != null && mainGas.getTextTrim().equals("Y")){
			report.setRdsapMainGasAvailable(YesNo.Yes);
		}else{
			report.setRdsapMainGasAvailable(YesNo.No);
		}
		
		//新加开始
		xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Main-Heating-Details/defu:Main-Heating");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		List<Element> listElement = xpathSelector.selectNodes(doc);
		if(listElement.size() == 1){
			xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Main-Heating-Details/defu:Main-Heating/defu:Main-Fuel-Type");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element rdsapMhsFuel = (Element)xpathSelector.selectSingleNode(doc);
			report.setRdsapMhsFuel(rdsapMhsFuel != null ? Integer.parseInt(rdsapMhsFuel.getTextTrim()) : null);
			
			report.setRdsapSmhsFuel(null);
		}else if(listElement.size() == 2){
			xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Main-Heating-Details/defu:Main-Heating[1]/defu:Main-Fuel-Type");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element rdsapMhsFuel = (Element)xpathSelector.selectSingleNode(doc);
			report.setRdsapMhsFuel(rdsapMhsFuel != null ? Integer.parseInt(rdsapMhsFuel.getTextTrim()) : null);
			
			xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Main-Heating-Details/defu:Main-Heating[2]/defu:Main-Fuel-Type");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element rdsapSmhsFuel = (Element)xpathSelector.selectSingleNode(doc);
			report.setRdsapSmhsFuel(rdsapSmhsFuel != null ? Integer.parseInt(rdsapSmhsFuel.getTextTrim()) : null);
		}
		else{
			report.setRdsapMhsFuel(null);
			
			report.setRdsapSmhsFuel(null);
		}
		xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Secondary-Fuel-Type");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element rdsapShsFuel = (Element)xpathSelector.selectSingleNode(doc);
		report.setRdsapShsFuel(rdsapShsFuel != null ? Integer.parseInt(rdsapShsFuel.getTextTrim()) : null);
		
		xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Water-Heating-Fuel");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element rdsapWhsFuel = (Element)xpathSelector.selectSingleNode(doc);
		report.setRdsapWhsFuel(rdsapWhsFuel != null ? Integer.parseInt(rdsapWhsFuel.getTextTrim()) : null);
		//新加结束
		
		xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:Habitable-Room-Count");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element habitableRoomCountE = (Element)xpathSelector.selectSingleNode(doc);
		int habitableRoomCount = Integer.parseInt(habitableRoomCountE.getTextTrim());
		report.setHabitableRoomCount(habitableRoomCount);
		
		report.setInsertTime(new Date());
		report.setUpdateTime(new Date());
		report.setOaRrn(RRNBuilder.buildDRrn(RRNBuilder.DReportType.GDOA_TYPE, report.getUprn(), report.getInspectionDate()));
		GdsapEvalReport gdsapEvalReport = BeanUtils.user2gdsapUsrUser(report);
		gdsapEvalReportMapper.insert(gdsapEvalReport);
		
		//Improvement开始(Suggested-Improvement)
		Improvement improvement =new Improvement();
		
		xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		List<Element> listHeat = xpathSelector.selectNodes(doc);
		for(int i=1;i<listHeat.size()+1;i++){
			xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Improvement-Category");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element improvementCategory = (Element)xpathSelector.selectSingleNode(doc);
			RecommendationCategoryCode r = improvementCategory != null ? (RecommendationCategoryCode)EnumUtils.getByCode(Integer.parseInt(improvementCategory.getTextTrim()), RecommendationCategoryCode.class) : null;
			improvement.setImprovementCategory(improvementCategory != null ? r : null);
			
			xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Green-Deal-Category");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element greenDealCategory = (Element)xpathSelector.selectSingleNode(doc);
			GreenDealCategoryCode g = greenDealCategory != null ? (GreenDealCategoryCode)EnumUtils.getByCode(greenDealCategory.getTextTrim(), GreenDealCategoryCode.class) : null;
			improvement.setGreenDealCategory(greenDealCategory != null ? g : null);
			
			improvement.setScope(ImprovementScope.Suggested);
			
			xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Typical-Saving");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element typicalSaving = (Element)xpathSelector.selectSingleNode(doc);
			improvement.setTypicalSaving(typicalSaving != null ? Float.parseFloat(typicalSaving.getTextTrim()) : null);
			
			xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Indicative-Cost");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element indicativeCost = (Element)xpathSelector.selectSingleNode(doc);
			improvement.setIndicativeCost(indicativeCost != null ? indicativeCost.getTextTrim() : null);
			
			xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Energy-Performance-Rating");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element energyPerformanceRating = (Element)xpathSelector.selectSingleNode(doc);
			improvement.setEnergyPerformanceRating(energyPerformanceRating != null ? Float.parseFloat(energyPerformanceRating.getTextTrim()) : null);
			
			xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Suggested-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Environmental-Impact-Rating");
			xpathSelector.setNamespaceURIs(nameSpaceMap);
			Element environmentalImpactRating = (Element)xpathSelector.selectSingleNode(doc);
			improvement.setEnvironmentalImpactRating(environmentalImpactRating != null ? Float.parseFloat(environmentalImpactRating.getTextTrim()) : null);
			
			if(gdsapEvalReport != null){
				report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
				this._boTodo(gdsapEvalReport,report);
				improvement.setReport(report);
			}
			GdsapEvalImprovement gdsapEvalImprovement = BeanUtils.improvementToGdsImprovement(improvement);
			gdsapEvalImprovementMapper.insert(gdsapEvalImprovement);
		}
		
		//Improvement开始(Alternative-Improvements)<Alternative-Improvements>
		xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		List<Element> listElements = xpathSelector.selectNodes(doc);
		if(!listElements.isEmpty()){
			for(int i=1;i<listElements.size()+1;i++){
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Improvement-Category");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element improvementCategory = (Element)xpathSelector.selectSingleNode(doc);
				RecommendationCategoryCode r = improvementCategory != null ? (RecommendationCategoryCode)EnumUtils.getByCode(improvementCategory.getTextTrim(), RecommendationCategoryCode.class) : null;
				improvement.setImprovementCategory(improvementCategory != null ? r : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Green-Deal-Category");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element greenDealCategory = (Element)xpathSelector.selectSingleNode(doc);
				GreenDealCategoryCode g = greenDealCategory != null ? (GreenDealCategoryCode)EnumUtils.getByCode(greenDealCategory.getTextTrim(), GreenDealCategoryCode.class) : null;
				improvement.setGreenDealCategory(greenDealCategory != null ? g : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Improvement-Type");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element improvementType = (Element)xpathSelector.selectSingleNode(doc);
				GreenDealCategoryCode g1 = improvementType != null ? (GreenDealCategoryCode)EnumUtils.getByCode(improvementType.getTextTrim(), GreenDealCategoryCode.class) : null;
				improvement.setGreenDealCategory(improvementType != null ? g1 : null);
				
				improvement.setScope(ImprovementScope.Alternative);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Typical-Saving");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element typicalSaving = (Element)xpathSelector.selectSingleNode(doc);
				improvement.setTypicalSaving(typicalSaving != null ? Float.parseFloat(typicalSaving.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Indicative-Cost");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element indicativeCost = (Element)xpathSelector.selectSingleNode(doc);
				improvement.setIndicativeCost(indicativeCost != null ? indicativeCost.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Energy-Performance-Rating");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyPerformanceRating = (Element)xpathSelector.selectSingleNode(doc);
				improvement.setEnergyPerformanceRating(energyPerformanceRating != null ? Float.parseFloat(energyPerformanceRating.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Alternative-Improvements/defu:Improvement"+"["+i+"]"+"/defu:Environmental-Impact-Rating");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element environmentalImpactRating = (Element)xpathSelector.selectSingleNode(doc);
				improvement.setEnvironmentalImpactRating(environmentalImpactRating != null ? Float.parseFloat(environmentalImpactRating.getTextTrim()) : null);
				
				if(gdsapEvalReport != null){
					report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
					this._boTodo(gdsapEvalReport,report);
					improvement.setReport(report);
				}
				GdsapEvalImprovement gdsapEvalImprovement = BeanUtils.improvementToGdsImprovement(improvement);
				gdsapEvalImprovementMapper.insert(gdsapEvalImprovement);
			}
		}
		        //EpcEADetail(start)
		        GdsapEvalEpcEaDetail epc = new GdsapEvalEpcEaDetail();
		        epc.setReportId(gdsapEvalReport.getId());
		        
		        xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Scheme-Name");
	        	xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element schemeNameElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setSchemeName(schemeNameElement != null ? schemeNameElement.getTextTrim() : null);
		        
		        xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Scheme-Web-Site");
	        	xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element schemeWebSiteElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setSchemeWebSite(schemeWebSiteElement != null ? schemeWebSiteElement.getTextTrim() : null);
		        
	        	xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Identification-Number/defu:Membership-Number");
        		xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element certificateNumberElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setCertificateNumber(certificateNumberElement != null ? certificateNumberElement.getTextTrim() : null);
		        
		        xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Name");
	        	xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element fullNameElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setFullName(fullNameElement != null ? fullNameElement.getTextTrim() : null);
		        
		        xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Notify-Lodgement");
	        	xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element notifyLodgementElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setNotifyLodgement(notifyLodgementElement != null ? notifyLodgementElement.getTextTrim() : null);
		        
	        	xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Contact-Address/defu:Address-Line-1");
        		xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element contactAddress1Element = (Element)xpathSelector.selectSingleNode(doc);
				epc.setContactAddress1(contactAddress1Element != null ? contactAddress1Element.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Contact-Address/defu:Address-Line-2");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element contactAddress2Element = (Element)xpathSelector.selectSingleNode(doc);
				epc.setContactAddress2(contactAddress2Element != null ? contactAddress2Element.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Contact-Address/defu:Address-Line-3");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element contactAddress3Element = (Element)xpathSelector.selectSingleNode(doc);
				epc.setContactAddress3(contactAddress3Element != null ? contactAddress3Element.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Contact-Address/defu:Post-Town");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element posttownElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setPosttown(posttownElement != null ? posttownElement.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Contact-Address/defu:Postcode");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element postcodeElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setPostcode(postcodeElement != null ? postcodeElement.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Web-Site");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element websiteElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setWebsite(websiteElement != null ? websiteElement.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:E-Mail");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element emailElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setEmail(emailElement != null ? emailElement.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Fax");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element faxElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setFax(faxElement != null ? faxElement.getTextTrim() : null);

				xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Telephone");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element telephoneElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setTelephone(telephoneElement != null ? telephoneElement.getTextTrim() : null);

				xpathSelector = DocumentHelper.createXPath("//defu:Report-Header/defu:Energy-Assessor/defu:Company-Name");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element companyNameElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setCompanyName(companyNameElement != null ? companyNameElement.getTextTrim() : null);
				// this line -----------------------------------------------------------------------------------------------------
				xpathSelector = DocumentHelper.createXPath("//defu:Insurance-Details/defu:Insurer");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element insurerElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setInsurer(insurerElement != null ? insurerElement.getTextTrim() : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Insurance-Details/defu:Policy-No");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element policyNoElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setPolicyNo(policyNoElement != null ? policyNoElement.getTextTrim() : null);
					
				xpathSelector = DocumentHelper.createXPath("//defu:Insurance-Details/defu:Effective-Date");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element effectiveDateElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setEffectiveDate(effectiveDateElement != null ? DateUtils.convert(effectiveDateElement.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Insurance-Details/defu:Expiry-Date");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element expiryDateElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setExpiryDate(expiryDateElement != null ? DateUtils.convert(expiryDateElement.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Insurance-Details/defu:PI-Limit");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element piLimitElement = (Element)xpathSelector.selectSingleNode(doc);
				epc.setPiLimit(piLimitElement != null ? piLimitElement.getTextTrim() : null);
				
				gdsapEvalEpcEaDetailMapper.insert(epc);
				
    			//EpcEADetail(start)
		
		        //EnergyUse(HIP:Energy-Use)开始
		        EnergyUse energyUse = new EnergyUse();
		        
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Energy-Rating-Average");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyRatingAverage = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyRatingAverage(energyRatingAverage != null ? Integer.parseInt(energyRatingAverage.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Energy-Rating-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyRatingCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyRatingCurrent(energyRatingCurrent != null ? Integer.parseInt(energyRatingCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Energy-Rating-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyRatingPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyRatingPotential(energyRatingPotential != null ? Integer.parseInt(energyRatingPotential.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Environmental-Impact-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element environmentalImpactCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnvironmentalImpactCurrent(environmentalImpactCurrent != null ? Integer.parseInt(environmentalImpactCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Environmental-Impact-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element environmentalImpactPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnvironmentalImpactPotential(environmentalImpactPotential != null ? Integer.parseInt(environmentalImpactPotential.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Energy-Consumption-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyConsumptionCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyConsumptionCurrent(energyConsumptionCurrent != null ? Float.parseFloat(energyConsumptionCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Energy-Consumption-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element energyConsumptionPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setEnergyConsumptionPotential(energyConsumptionPotential != null ? Float.parseFloat(energyConsumptionPotential.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:CO2-Emissions-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element co2EmissionsCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setCo2EmissionsCurrent(co2EmissionsCurrent != null ? Float.parseFloat(co2EmissionsCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:CO2-Emissions-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element co2EmissionsPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setCo2EmissionsPotential(co2EmissionsPotential != null ? Float.parseFloat(co2EmissionsPotential.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:CO2-Emissions-Current-Per-Floor-Area");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element co2EmissionsCurrentPerFloorArea = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setCo2EmissionsCurrentPerFloorArea(co2EmissionsCurrentPerFloorArea != null ? Float.parseFloat(co2EmissionsCurrentPerFloorArea.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Lighting-Cost-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element lightingCostCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setLightingCostCurrent(lightingCostCurrent != null ? Float.parseFloat(lightingCostCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Lighting-Cost-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element lightingCostPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setLightingCostPotential(lightingCostPotential != null ? Float.parseFloat(lightingCostPotential.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Heating-Cost-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element heatingCostCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setHeatingCostCurrent(heatingCostCurrent != null ? Float.parseFloat(heatingCostCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Heating-Cost-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element heatingCostPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setHeatingCostPotential(heatingCostPotential != null ? Float.parseFloat(heatingCostPotential.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Hot-Water-Cost-Current");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element hotWaterCostCurrent = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setHotWaterCostCurrent(hotWaterCostCurrent != null ? Float.parseFloat(hotWaterCostCurrent.getTextTrim()) : null);
				
				xpathSelector = DocumentHelper.createXPath("//defu:Energy-Assessment/defu:Energy-Use/defu:Hot-Water-Cost-Potential");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element hotWaterCostPotential = (Element)xpathSelector.selectSingleNode(doc);
				energyUse.setHotWaterCostPotential(hotWaterCostPotential != null ? Float.parseFloat(hotWaterCostPotential.getTextTrim()) : null);
				
				if(gdsapEvalReport != null){
					report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
					this._boTodo(gdsapEvalReport,report);
					energyUse.setId(report);
				}
				gdsapEvalEnergyUseMapper.insert(BeanUtils.energyUsetoGdsapEvalEnergyUse(energyUse));
				
				//GdsapEvalHeatProportion开始
				HeatProportion heatProportion = new HeatProportion();
				if(habitableRoomCount > 25)
				{
					habitableRoomCount = 25;
				}
				if(habitableRoomCount > 1){
					RoomType roomType = (RoomType)EnumUtils.getByCode(0, RoomType.class);
					RoomType roomType1 = (RoomType)EnumUtils.getByCode(1, RoomType.class);
					YesNo main1 = YesNo.No;
					YesNo main2 = YesNo.No;
					YesNo secondary = YesNo.No;
					YesNo heatedP = YesNo.No;
					YesNo notable = YesNo.No;
					heatProportion.setMain1(main1);
					heatProportion.setMain2(main2);
					heatProportion.setSecondary(secondary);
					heatProportion.setHeatedPartially(heatedP);
					heatProportion.setNotable(notable);
					heatProportion.setRoomScope(roomType);
					if(gdsapEvalReport != null){
						report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
						this._boTodo(gdsapEvalReport,report);
						heatProportion.setReportId(report);
					}
					gdsapEvalHeatProportionMapper.insert(BeanUtils.heatProportionToGdsap(heatProportion));
					for(int j = 0 ; j < habitableRoomCount-1 ; j++){
						heatProportion.setRoomScope(roomType1);
						heatProportion.setMain1(main1);
						heatProportion.setMain2(main2);
						heatProportion.setSecondary(secondary);
						heatProportion.setHeatedPartially(heatedP);
						heatProportion.setNotable(notable);
						if(gdsapEvalReport != null){
							report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
							this._boTodo(gdsapEvalReport,report);
							heatProportion.setReportId(report);
						}
						gdsapEvalHeatProportionMapper.insert(BeanUtils.heatProportionToGdsap(heatProportion));
					}
				}else{
					RoomType roomType = (RoomType)EnumUtils.getByCode(0, RoomType.class);
					heatProportion.setRoomScope(roomType);
					YesNo main1 = YesNo.No;
					YesNo main2 = YesNo.No;
					YesNo secondary = YesNo.No;
					YesNo heatedP = YesNo.No;
					YesNo notable = YesNo.No;
					heatProportion.setMain1(main1);
					heatProportion.setMain2(main2);
					heatProportion.setSecondary(secondary);
					heatProportion.setHeatedPartially(heatedP);
					heatProportion.setNotable(notable);
					if(gdsapEvalReport != null){
						report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
						this._boTodo(gdsapEvalReport,report);
						heatProportion.setReportId(report);
					}
					gdsapEvalHeatProportionMapper.insert(BeanUtils.heatProportionToGdsap(heatProportion));
				}
				return report;
	}
		
	Map<String, String> nameSpacesMap = null;
	private Map<String, String> getNameSpaceMap(Document doc){
		nameSpacesMap = new HashMap<String, String>();
		Element rootElt = doc.getRootElement();
		for(String prefix : ReportConfig.prefixs){
			String namespace = null;
			namespace = rootElt.getNamespaceForPrefix(prefix) != null ? rootElt.getNamespaceForPrefix(prefix).getURI() : null;
			if(namespace != null){
				nameSpacesMap.put(prefix, namespace);
			}
		}
		getNameSpaceMap(rootElt);
		return nameSpacesMap;
	}
	
	private void getNameSpaceMap(Element e){
		for(Iterator<Element> it = e.elementIterator(); it.hasNext();){
			Element element = (Element) it.next();
			Namespace namespace = element.getNamespace();
			if(isValidNamespace(namespace)){
				if(!namespace.getPrefix().trim().equals("")){
					nameSpacesMap.put(namespace.getPrefix(),namespace.getURI());
				}else{
					nameSpacesMap.put("defu",namespace.getURI());
				}
			}
			getNameSpaceMap(element);
		}
	}
	
	private boolean isValidNamespace(Namespace namespace){
		Assert.notNull(namespace);
		boolean valid = false;
		if(!namespace.getPrefix().trim().equals("") || !namespace.getURI().trim().equals("")){
			valid = true;
		}
		return valid;
	}
	
	/**
	 * 对report里面的个别属性对象进行转换
	 * @param gdsapEvalReport
	 * @param report
	 */
	public void _boTodo(GdsapEvalReport gdsapEvalReport,Report report){
		RelatedPartyDisclosure re = landmarkDefineServiceMgr.getRelatedPartyDisclosure(gdsapEvalReport.getRelatedPartyDisclosure(), (Language)EnumUtils.getByCode(gdsapEvalReport.getLanguageCode(), Language.class));
		report.setRelatedPartyDisclosure(re);
		report.setUser(userServiceMgr.getUser(gdsapEvalReport.getUserId()));
		//report.setReportXmlFile(gdsapEvalReport.getReportXmlFile());
	}

	@Override
	public EnergyUse getEnergyUser(long reportId)
	{
		GdsapEvalEnergyUse gdsapEvalEnergyUse = gdsapEvalEnergyUseMapper.load(reportId);
		if (gdsapEvalEnergyUse != null)
		{
			return BeanUtils.GdsapEvalEnergyUsetoenergyUse(gdsapEvalEnergyUse);
		}
		return null;
	}

	@Override
	public Report getReport(long id)
	{
		GdsapEvalReport gdsapEvalReport = gdsapEvalReportMapper.load(id);
		if(gdsapEvalReport != null){
			Report report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
			report.setUser(userServiceMgr.getUser(gdsapEvalReport.getUserId()));
			RelatedPartyDisclosure re = landmarkDefineServiceMgr.getRelatedPartyDisclosure(gdsapEvalReport.getRelatedPartyDisclosure(), (Language)EnumUtils.getByCode(gdsapEvalReport.getLanguageCode(), Language.class));
			report.setRelatedPartyDisclosure(re);
			report.setCompany(companyServiceMgr.getCompany(gdsapEvalReport.getCompanyId()));
			return report;
		}
		return null;
	}

	@Override
	public Report getReportByRRN(String rrn, ReportStatus reportStatus)
	{
		List<GdsapEvalReport> gdsapReports = gdsapEvalReportMapper.findByRRN_reportStatus(rrn, reportStatus.getCode());
		if(!CollectionUtils.isEmpty(gdsapReports))
		{
			GdsapEvalReport gdsapEvalReport = gdsapReports.get(0);
			Report report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
			report.setUser(userServiceMgr.getUser(gdsapEvalReport.getUserId()));
			RelatedPartyDisclosure re = landmarkDefineServiceMgr.getRelatedPartyDisclosure(gdsapEvalReport.getRelatedPartyDisclosure(), (Language)EnumUtils.getByCode(gdsapEvalReport.getLanguageCode(), Language.class));
			report.setRelatedPartyDisclosure(re);
			report.setCompany(companyServiceMgr.getCompany(gdsapEvalReport.getCompanyId()));
			return report;
		}
		return null;
	}
	
	@Override
	public Report getReport(Company company, String companyJobRef)
	{
		GdsapEvalReport gdsapReport = gdsapEvalReportMapper.findByCompany_companyJobRef(company.getCompanyId(), companyJobRef);
		if(gdsapReport != null)
		{
			Report report = BeanUtils.gdsapUsrUser2user(gdsapReport);
			report.setUser(userServiceMgr.getUser(gdsapReport.getUserId()));
			RelatedPartyDisclosure re = landmarkDefineServiceMgr.getRelatedPartyDisclosure(gdsapReport.getRelatedPartyDisclosure(), (Language)EnumUtils.getByCode(gdsapReport.getLanguageCode(), Language.class));
			report.setRelatedPartyDisclosure(re);
			report.setCompany(companyServiceMgr.getCompany(gdsapReport.getCompanyId()));
			return report;
		}
		return null;
	}
	
	public Report getReportByOARRN(String oaRrn, ReportStatus reportStatus)
	{
		List<GdsapEvalReport> gdsapReports = gdsapEvalReportMapper.findByOARRN_reportStatus(oaRrn, reportStatus.getCode());
		if(!CollectionUtils.isEmpty(gdsapReports))
		{
			GdsapEvalReport gdsapEvalReport = gdsapReports.get(0);
			Report report = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
			report.setUser(userServiceMgr.getUser(gdsapEvalReport.getUserId()));
			RelatedPartyDisclosure re = landmarkDefineServiceMgr.getRelatedPartyDisclosure(gdsapEvalReport.getRelatedPartyDisclosure(), (Language)EnumUtils.getByCode(gdsapEvalReport.getLanguageCode(), Language.class));
			report.setRelatedPartyDisclosure(re);
			report.setCompany(companyServiceMgr.getCompany(gdsapEvalReport.getCompanyId()));
			return report;
		}
		return null;
	}
	
	@Override
	public List<Report> getReportsByConditionVO(ConditionVO vo)
	{
		Long userId = null;
		Date startInspectionDate = null;
		Date endInspectionDate = null;
		Integer transactionType = null;
		Integer propertyType = null;
		String keywords = null;
		RowBounds rb = null;
		Integer reportStatus = null;
		
		if(vo != null){
			userId = vo.getUser() != null ? vo.getUser().getId() : null;
			startInspectionDate = vo.getStartInspectionDate();
			endInspectionDate = vo.getEndInspectionDate();
			transactionType = vo.getTransactionType() != null ? vo.getTransactionType().getCode() : null;
			propertyType = vo.getPropertyType() != null ? vo.getPropertyType().getCode() : null;
			reportStatus = vo.getReportStatus() != null ? vo.getReportStatus().getCode() : null;
			keywords = vo.getKeywords() != null ? vo.getKeywords().trim() : null;
			rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
		}
		
		List<GdsapEvalReport> listg = null;
//		if(vo != null && vo.getUser() != null && vo.getUser().getUserType().equals(UserType.GDP))
//		{
//			reportStatus = ReportStatus.GDIP_Lodged_By_Assessor.getCode();
//			listg = this.gdsapGdpuserReportMapper.findPageBreakByCondition(userId, rrn, startInspectionDate, endInspectionDate, urpn, transactionType, propertyType,reportStatus, rb);
//		}
//		else
//		{
			listg = this.gdsapEvalReportMapper.findPageBreakByCondition(userId, startInspectionDate, endInspectionDate, reportStatus, keywords, transactionType, propertyType, rb);
//		}
		if (!CollectionUtils.isEmpty(listg))
		{
			List<Report> reports = new ArrayList<Report>();
			for (GdsapEvalReport gdsapEvalReport : listg)
			{
				reports.add(BeanUtils.gdsapUsrUser2user(gdsapEvalReport));
			}
			return reports;
		}
		return null;
	}

	@Override
	public int getTotalReportsByConditionVO(ConditionVO vo)
	{
		Long userId = null;
		Date startInspectionDate = null;
		Date endInspectionDate = null;
		Integer transactionType = null;
		Integer propertyType = null;
		Integer reportStatus = null;
		String keywords = null;
		if(vo != null){
			userId = vo.getUser() != null ? vo.getUser().getId() : null;
			startInspectionDate = vo.getStartInspectionDate();
			endInspectionDate = vo.getEndInspectionDate();
			transactionType = vo.getTransactionType() != null ? vo.getTransactionType().getCode() : null;
			propertyType = vo.getPropertyType() != null ? vo.getPropertyType().getCode() : null;
			reportStatus = vo.getReportStatus() != null ? vo.getReportStatus().getCode() : null;
			keywords = vo.getKeywords() != null ? vo.getKeywords().trim() : null;
		}
		
//		if(vo != null && vo.getUser() != null && vo.getUser().getUserType().equals(UserType.GDP))
//		{
//			reportStatus = ReportStatus.GDIP_Lodged_By_Assessor.getCode();
//			return this.gdsapGdpuserReportMapper.findNumberByCondition(userId, rrn, startInspectionDate, endInspectionDate, urpn, transactionType, propertyType, reportStatus);
//		}
		return this.gdsapEvalReportMapper.findNumberByCondition(userId, startInspectionDate, endInspectionDate, reportStatus, keywords, transactionType, propertyType);
	}

	@Override
	public List<Report> adminGetReportsByConditionVO(ConditionVO vo, RowBounds rb)
	{
		Date startInspectionDate = null;
		Date endInspectionDate = null;
		Integer reportStatus = null;
		String organisationCertificationNumber = null;
		String userName = null;
		String keywords = null;
		
		if(vo != null){
			startInspectionDate = vo.getStartInspectionDate();
			endInspectionDate = vo.getEndInspectionDate();
			if(endInspectionDate != null){
				Calendar cal = Calendar.getInstance();
				cal.setTime(endInspectionDate);
				cal.add(Calendar.HOUR, 24);
				endInspectionDate = cal.getTime();
			}
			reportStatus = vo.getReportStatus() != null ? vo.getReportStatus().getCode() : null;
			organisationCertificationNumber = vo.getOrganisationCertificationNumber() != null && !vo.getOrganisationCertificationNumber().equals("") ? vo.getOrganisationCertificationNumber() : null;
			userName = vo.getUserName() != null && !vo.getUserName().equals("") ? vo.getUserName() : null;
			keywords = vo.getKeywords() != null ? vo.getKeywords().trim() : null;
		}
		
		List<GdsapEvalReport> listg = this.gdsapEvalReportMapper.adminfindByCondition(startInspectionDate, endInspectionDate, organisationCertificationNumber, userName, reportStatus, keywords, rb);
		
		if (!CollectionUtils.isEmpty(listg))
		{
			List<Report> reports = new ArrayList<Report>();
			for (GdsapEvalReport gdsapEvalReport : listg)
			{
				Report tmpReport = BeanUtils.gdsapUsrUser2user(gdsapEvalReport);
				User user = userServiceMgr.getUser(gdsapEvalReport.getUserId());
				tmpReport.setUser(user);
				reports.add(tmpReport);
			}
			return reports;
		}
		
		return null;
	}

	@Override
	public int adminGetTotalReportsByConditionVO(ConditionVO vo)
	{
		Date startInspectionDate = null;
		Date endInspectionDate = null;
		Integer reportStatus = null;
		String organisationCertificationNumber = null;
		String userName = null;
		String keywords = null;
		
		if(vo != null){
			startInspectionDate = vo.getStartInspectionDate();
			endInspectionDate = vo.getEndInspectionDate();
			if(endInspectionDate != null){
				Calendar cal = Calendar.getInstance();
				cal.setTime(endInspectionDate);
				cal.add(Calendar.HOUR, 24);
				endInspectionDate = cal.getTime();
			}
			reportStatus = vo.getReportStatus() != null ? vo.getReportStatus().getCode() : null;
			organisationCertificationNumber = vo.getOrganisationCertificationNumber() != null && !vo.getOrganisationCertificationNumber().equals("") ? vo.getOrganisationCertificationNumber() : null;
			userName = vo.getUserName() != null && !vo.getUserName().equals("") ? vo.getUserName() : null;
			keywords = vo.getKeywords() != null ? vo.getKeywords().trim() : null;
		}
		
		return this.gdsapEvalReportMapper.adminfindNumberByCondition(startInspectionDate, endInspectionDate, organisationCertificationNumber, userName, reportStatus, keywords);
	}
	
	@Override
	public List<Report> adminCheckReportsByConditionVO(ConditionVO vo, RowBounds rb)
	{
		Date startInspectionDate = null;
		Date endInspectionDate = null;
		Integer reportStatus = null;
		String organisationCertificationNumber = null;
		String userName = null;
		
		if(vo != null){
			reportStatus = vo.getReportStatus() != null ? vo.getReportStatus().getCode() : null;
			startInspectionDate = vo.getStartInspectionDate();
		}
		
		List<GdsapEvalReport> listg = this.gdsapEvalReportMapper.adminfindByCondition(startInspectionDate, endInspectionDate, organisationCertificationNumber, userName, reportStatus, null, rb);
		if (!CollectionUtils.isEmpty(listg))
		{
			int errorNum = 0;
			List<Report> reports = new ArrayList<Report>();
			for (GdsapEvalReport gr : listg) 
			{
				Solution soltion = null;
				List<Solution> soltions = solutionServiceMgr.getSolutions(gr.getId(), SolutionType.GDAR);
				if(soltions.size() == 1)
				{
					soltion = soltions.get(0);
				}
				if(soltions.size() > 1)
				{
					for(Solution s : soltions)
					{
						if(s.getSelected() != null && s.getSelected().equals(YesNo.Yes))
						{
							soltion = s;
							break;
						}
					}
				}
				
				String lodgeGdarXmlpath = GlobalConfig.getInstance().getFrontendFSDir();
				if(soltion != null)
				{
					lodgeGdarXmlpath = lodgeGdarXmlpath + soltion.getSolutionLodgeXmlPath();
				}
				
				String xmlContent = null;
				try {
						xmlContent = FileUtils.readFileToString(new File(lodgeGdarXmlpath),"utf-8");
						DocumentUtil.readDocument(xmlContent);
				} catch (Exception e) {
					try {
						xmlContent = FileUtils.readFileToString(new File(lodgeGdarXmlpath),"utf-16");
						DocumentUtil.readDocument(xmlContent);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
				if(xmlContent != null)
				{
					Document gdarLigDoc = null;
					try {
						gdarLigDoc = DocumentUtil.readDocument(xmlContent);
					} catch (Exception e) {
						System.out.println("report's lig can't find:" + gr.getOaRrn());
					}
					
					double result = getDvalueInLig(gdarLigDoc);
					if(result > 10 || result < -10)
					{
						Report tmpReport = BeanUtils.gdsapUsrUser2user(gr);
						User user = userServiceMgr.getUser(gr.getUserId());
						tmpReport.setUser(user);
						tmpReport.setdValue(result);
						reports.add(tmpReport);
						System.out.println("have error lig:" + tmpReport.getOaRrn() + "--------------------------------");
						errorNum++;
					}
				}
			}
			System.out.println("the count of error lig:" + errorNum);
			return reports;
		}
		
		return null;
	}
	
	@Override
	public List<Report> adminCheckReportsByConditionVO2(ConditionVO vo, RowBounds rb)
	{
		Date startInspectionDate = null;
		Date endInspectionDate = null;
		Integer reportStatus = null;
		String organisationCertificationNumber = null;
		String userName = null;
		
		if(vo != null){
			reportStatus = vo.getReportStatus() != null ? vo.getReportStatus().getCode() : null;
			startInspectionDate = vo.getStartInspectionDate();
		}
		
		List<GdsapEvalReport> listg = this.gdsapEvalReportMapper.adminfindByCondition(startInspectionDate, endInspectionDate, organisationCertificationNumber, userName, reportStatus, null, rb);
		if (!CollectionUtils.isEmpty(listg))
		{
			int errorNum = 0;
			List<Report> reports = new ArrayList<Report>();
			for (GdsapEvalReport gr : listg) 
			{
				Solution soltion = null;
				List<Solution> soltions = solutionServiceMgr.getSolutions(gr.getId(), SolutionType.GDAR);
				if(soltions.size() == 1)
				{
					soltion = soltions.get(0);
				}
				if(soltions.size() > 1)
				{
					for(Solution s : soltions)
					{
						if(s.getSelected() != null && s.getSelected().equals(YesNo.Yes))
						{
							soltion = s;
							break;
						}
					}
				}
				
				String lodgeGdarXmlpath = GlobalConfig.getInstance().getFrontendFSDir();
				if(soltion != null)
				{
					lodgeGdarXmlpath = lodgeGdarXmlpath + soltion.getSolutionLodgeXmlPath();
				}
				
				String xmlContent = null;
				try {
						xmlContent = FileUtils.readFileToString(new File(lodgeGdarXmlpath),"utf-8");
						DocumentUtil.readDocument(xmlContent);
				} catch (Exception e) {
					try {
						xmlContent = FileUtils.readFileToString(new File(lodgeGdarXmlpath),"utf-16");
						DocumentUtil.readDocument(xmlContent);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
				if(xmlContent != null)
				{
					Document gdarLigDoc = null;
					try {
						gdarLigDoc = DocumentUtil.readDocument(xmlContent);
					} catch (Exception e) {
						System.out.println("report's lig can't find:" + gr.getOaRrn());
					}
					
					if(haveError(gdarLigDoc) > 500000 || haveError(gdarLigDoc) == -100)
					{
						Report tmpReport = BeanUtils.gdsapUsrUser2user(gr);
						User user = userServiceMgr.getUser(gr.getUserId());
						tmpReport.setUser(user);
						tmpReport.setdValue(haveError(gdarLigDoc));
						reports.add(tmpReport);
						System.out.println("have error lig:" + tmpReport.getOaRrn() + "--------------------------------");
						errorNum++;
					}
				}
			}
			System.out.println("the count of error lig:" + errorNum);
			return reports;
		}
		
		return null;
	}
	
	/**
	 * 检查 
	 * @param gdarLig
	 * 查询的节点 
		<Occupancy-Assessment-Results> 
	  	<Energy-Use> 
	    	<Space-Heating>3950</Space-Heating> 
	    	<Water-Heating>1208</Water-Heating>
	    </Energy-Use> 
	 * @return
	 */
	private static double haveError(Document gdarLigDoc)
	{
		if(gdarLigDoc == null)
		{
			return 0;
		}
		
		Element rootElt = gdarLigDoc.getRootElement();
		String defNamespace = rootElt.getNamespaceURI();
		XPath xpathSelector;
		Map<String, String> nameSpaceMap = new HashMap<String, String>();
		if(defNamespace != null)
		{
			nameSpaceMap.put("defu", defNamespace);
		}
		
		xpathSelector = DocumentHelper.createXPath("//defu:Occupancy-Assessment/defu:Occupancy-Assessment-Results/defu:Energy-Use/defu:Space-Heating");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element shE = (Element)xpathSelector.selectSingleNode(gdarLigDoc);
		if(shE != null)
		{
			try {
				if(Double.valueOf(shE.getTextTrim()) > 500000)
				{
					return Double.valueOf(shE.getTextTrim());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		xpathSelector = DocumentHelper.createXPath("//defu:Occupancy-Assessment/defu:Occupancy-Assessment-Results/defu:Energy-Use/defu:Water-Heating");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element whE = (Element)xpathSelector.selectSingleNode(gdarLigDoc);
		if(whE != null)
		{
			try {
				if(Double.valueOf(whE.getTextTrim()) > 500000)
				{
					return Double.valueOf(whE.getTextTrim());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		xpathSelector = DocumentHelper.createXPath("//defu:Occupancy-Assessment-Results/defu:Selected-Improvements/defu:Improvement");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		@SuppressWarnings("unchecked")
		List<Element> selectedImp = xpathSelector.selectNodes(gdarLigDoc);
		if(!CollectionUtils.isEmpty(selectedImp))
		{
			for(int i = 1; i <= selectedImp.size(); i++)
			{
				xpathSelector = DocumentHelper.createXPath("//defu:Occupancy-Assessment-Results/defu:Selected-Improvements/defu:Improvement" + "["+i+"]" + "/defu:FIT-Revenue" + "/defu:Generation-Tariff");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element fitE = (Element)xpathSelector.selectSingleNode(gdarLigDoc);
				if(fitE != null && Double.valueOf(fitE.getTextTrim()) == 0)
				{
					return -100;
				}
			}
		}
		
		return 0;
	}
	
	/**
	 * 检查
	 * @param gdarLig
	 * @return
	 */
	private static double getDvalueInLig(Document gdarLigDoc)
	{
		if(gdarLigDoc == null)
		{
			return 0;
		}
		
		Element rootElt = gdarLigDoc.getRootElement();
		String defNamespace = rootElt.getNamespaceURI();
		XPath xpathSelector;
		Map<String, String> nameSpaceMap = new HashMap<String, String>();
		if(defNamespace != null)
		{
			nameSpaceMap.put("defu", defNamespace);
		}
		
		Double total = 0.0;
		Double es = 0.0, gs = 0.0, ofs = 0.0;
		
		xpathSelector = DocumentHelper.createXPath("//defu:Occupancy-Assessment/defu:Occupancy-Assessment-Results/defu:Selected-Improvements-Totals/defu:Typical-Saving");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element totalE = (Element)xpathSelector.selectSingleNode(gdarLigDoc);
		if(totalE != null)
		{
			total = Double.valueOf(totalE.getTextTrim());
		}
		
		xpathSelector = DocumentHelper.createXPath("//defu:Occupancy-Assessment/defu:Occupancy-Assessment-Results/defu:Selected-Improvements-Totals/defu:Typical-Saving-By-Fuel/defu:Electricity-Saving");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element esE = (Element)xpathSelector.selectSingleNode(gdarLigDoc);
		if(esE != null)
		{
			es = Double.valueOf(esE.getTextTrim());
		}

		xpathSelector = DocumentHelper.createXPath("//defu:Occupancy-Assessment/defu:Occupancy-Assessment-Results/defu:Selected-Improvements-Totals/defu:Typical-Saving-By-Fuel/defu:Gas-Saving");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element gsE = (Element)xpathSelector.selectSingleNode(gdarLigDoc);
		if(gsE != null)
		{
			gs = Double.valueOf(gsE.getTextTrim());
		}
		
		xpathSelector = DocumentHelper.createXPath("//defu:Occupancy-Assessment/defu:Occupancy-Assessment-Results/defu:Selected-Improvements-Totals/defu:Typical-Saving-By-Fuel/defu:Other-Fuel-Saving");
		xpathSelector.setNamespaceURIs(nameSpaceMap);
		Element ofsE = (Element)xpathSelector.selectSingleNode(gdarLigDoc);
		if(ofsE != null)
		{
			ofs = Double.valueOf(ofsE.getTextTrim());
		}
		
		double result = total - (es + gs + ofs);
		return result;
	}
	
	@Override
	public boolean enterBilldata(Report report)
	{
		if (report == null)
		{
			return false;
		}
		Occupants occupants = this.occupantsServiceMgr.getOccupants(report.getId());
		if (occupants == null)
		{
			return false;
		}
		HeatingSystem hsModel = this.heatingSystemServiceMgr.getHeatingSystem(report.getId());
		if (hsModel == null)
		{
			return false;
		}
		return true;
	}
	
	@Override
	public boolean viewBilldata(Report report)
	{
		if (report == null)
		{
			return false;
		}
		HeatingSystem hsModel = this.heatingSystemServiceMgr.getHeatingSystem(report.getId());
		if (hsModel == null)
		{
			return false;
		}
		AppCooking appCooking = this.appCookingServiceMgr.getAppCooking(report.getId());
		if (appCooking == null)
		{
			return false;
		}
		return true;
	}

	@Override
	public boolean viewCal(Report report)
	{
		if (report == null)
		{
			return false;
		}
		Occupants occupants = this.occupantsServiceMgr.getOccupants(report.getId());
		if (occupants == null)
		{
			return false;
		}
		HeatingSystem heatingSystem = this.heatingSystemServiceMgr.getHeatingSystem(report.getId());
		if (heatingSystem == null)
		{
			return false;
		}
		List<HeatProportion> heatProportions = this.heatProportionServiceMgr.getHeatProportions(report.getId());
		if (CollectionUtils.isEmpty(heatProportions))
		{
			return false;
		}
		HeatingPattern heatingPattern = this.heatingPatternServiceMgr.getHeatingPattern(report.getId());
		if (heatingPattern == null)
		{
			return false;
		}
		AppCooking appCooking = this.appCookingServiceMgr.getAppCooking(report.getId());
		if (appCooking == null)
		{
			return false;
		}
		BillDataEle billDataEle = this.billDataServiceMgr.getBillDataEle(report.getId());
		if (billDataEle == null && !report.getUploadWay().equals(UploadWay.GDP_Retrieval_From_Repo))
		{
			return false;
		}
		return true;
	}

	@Override
	public void deleteReport(long id)
	{
		Report report = this.getReport(id);
		Assert.notNull(report);
		this.gdsapEvalOtherFuelMapper.delteByReportId(id);
		this.gdsapEvalEnergyUseMapper.delete(id);
		this.gdsapEvalOccupantsMapper.delete(id);
		this.gdsapEvalHeatingSystemMapper.delete(id);
		this.gdsapEvalHeatingPatternMapper.delete(id);
		this.gdsapEvalHeatProportionMapper.deleteByReportId(id);
		this.gdsapEvalAppCookingMapper.delete(id);
		this.gdsapEvalBillDataCommMapper.delete(id);
		this.gdsapEvalBillDataMgMapper.delete(id);
		this.gdsapEvalBillDataEleMapper.delete(id);
		this.gdsapEvalBillDataLpgMapper.delete(id);
		this.deleteSolutionsWithRecommendations(report);
		this.gdsapEvalReportMapper.delete(id);
		this.gdsapEvalEpcEaDetailMapper.delete(id);
		this.solutionSeqServiceMgr.delete(id);
		this.gdsapEvalReportEpcImprovementResultMapper.deleteByReportId(id);
		//删除文件
		String filePath = GlobalConfig.getInstance().getFSDir() + report.getReportXmlFile();
		if (new File(filePath).exists())
		{
			new File(filePath).delete();
		}
		//删除文件
		String filePath2 = GlobalConfig.getInstance().getFSDir() + report.getLigXmlFile();
		if (new File(filePath2).exists())
		{
			new File(filePath2).delete();
		}
		//删除文件
		String filePath3 = GlobalConfig.getInstance().getFSDir() + report.getLodgedGdipLigXmlFile();
		if (new File(filePath3).exists())
		{
			new File(filePath3).delete();
		}
		//删除文件
		String filePath4 = GlobalConfig.getInstance().getFSDir() + report.getRequestXmlFile();
		if (new File(filePath4).exists())
		{
			new File(filePath4).delete();
		}
	}
	
	public void installerDeleteReport(Long reportId, Long userId)
	{
		this.gdsapGdpuserReportMapper.delete(userId, reportId);
	}
	
	@Override
	public List<Report> getReports()
	{
		List<Long> ids = this.gdsapEvalReportMapper.findAllIds();
		if (!CollectionUtils.isEmpty(ids))
		{
			List<Report> reports = new ArrayList<Report>();
			for (Long id : ids)
			{
				Report report = this.getReport(id);
				reports.add(report);
			}
			return reports;
		}
		return null;
	}

	@Override
	public void deleteSolutionsWithRecommendations(Report report)
	{
		Assert.notNull(report);
		SolutionType[] types = SolutionType.values();
		for(SolutionType t : types)
		{
			List<Solution> solutions = this.solutionServiceMgr.getSolutions(report.getId(), t);
			if (!CollectionUtils.isEmpty(solutions))
			{
				for (Solution solution : solutions )
				{
					this.solutionServiceMgr.deleteSolution(solution.getId());
				}
			}
			this.gdsapEvalRecommendationMapper.deleteByReportId(report.getId());
			this.solutionSeqServiceMgr.delete(report.getId());
		}
	}
	
	@Override
	public Report updateOARrn(Report report)
	{
		Assert.notNull(report);
		String oaRRn = null;
		if(report.isBuilderOaRrn())
		{
			oaRRn = RRNBuilder.buildDRrn(RRNBuilder.DReportType.GDOA_TYPE, report.getUprn(), report.getInspectionDate());
		}
		else
		{
			oaRRn = report.getOaRrn();
		}
		
		GdsapEvalReport model = new GdsapEvalReport();
		model.setId(report.getId());
		model.setOaRrn(report.getOaRrn());
		this.gdsapEvalReportMapper.updateSelective(model);
		report.setOaRrn(oaRRn);
		return report;
	}

	@Override
	public Report updateGdipRrn(Report report)
	{
		Assert.notNull(report);
		String gdipRRn = RRNBuilder.buildDRrn(RRNBuilder.DReportType.GDIP_TYPE, null, new Date());
		GdsapEvalReport model = new GdsapEvalReport();
		model.setId(report.getId());
		model.setGdipRrn(gdipRRn);
		this.gdsapEvalReportMapper.updateSelective(model);
		report.setGdipRrn(gdipRRn);
		return report;
	}
	
	@Autowired
	private GdsapEvalRecommendationMapper gdsapEvalRecommendationMapper;
	@Autowired
	private SolutionSeqServiceMgr solutionSeqServiceMgr;
	
	public GdsapEvalImprovementMapper getGdsapEvalImprovementMapper() {
		return gdsapEvalImprovementMapper;
	}

	public void setGdsapEvalImprovementMapper(
			GdsapEvalImprovementMapper gdsapEvalImprovementMapper) {
		this.gdsapEvalImprovementMapper = gdsapEvalImprovementMapper;
	}

	public LandmarkDefineServiceMgr getLandmarkDefineServiceMgr()
	{
		return landmarkDefineServiceMgr;
	}

	public void setLandmarkDefineServiceMgr(
			LandmarkDefineServiceMgr landmarkDefineServiceMgr)
	{
		this.landmarkDefineServiceMgr = landmarkDefineServiceMgr;
	}


	public GdsapEvalHeatProportionMapper getGdsapEvalHeatProportionMapper() {
		return gdsapEvalHeatProportionMapper;
	}

	public void setGdsapEvalHeatProportionMapper(
			GdsapEvalHeatProportionMapper gdsapEvalHeatProportionMapper) {
		this.gdsapEvalHeatProportionMapper = gdsapEvalHeatProportionMapper;
	}

	/**
	 * @return the gdsapEvalReportMapper
	 */
	public GdsapEvalReportMapper getGdsapEvalReportMapper()
	{
		return gdsapEvalReportMapper;
	}

	/**
	 * @param gdsapEvalReportMapper the gdsapEvalReportMapper to set
	 */
	public void setGdsapEvalReportMapper(GdsapEvalReportMapper gdsapEvalReportMapper)
	{
		this.gdsapEvalReportMapper = gdsapEvalReportMapper;
	}
	

	public GdsapEvalEnergyUseMapper getGdsapEvalEnergyUseMapper() {
		return gdsapEvalEnergyUseMapper;
	}

	public void setGdsapEvalEnergyUseMapper(GdsapEvalEnergyUseMapper gdsapEvalEnergyUseMapper) {
		this.gdsapEvalEnergyUseMapper = gdsapEvalEnergyUseMapper;
	}

	public UserServiceMgr getUserServiceMgr() {
		return userServiceMgr;
	}

	public void setUserServiceMgr(UserServiceMgr userServiceMgr) {
		this.userServiceMgr = userServiceMgr;
	}

	/**
	 * 从EPC中获取临时数据用作判断
	 */
	private Map<String,Object> getTems(Document doc, EpcVersion version)
	{
		Map<String,Object> mapTem = new HashMap<String,Object>();
		
		String[] s = new String[2];
		String secondMavailable = "";
		String secondaryHtype = "";
		
		Integer[] s1 = new Integer[3];
		Integer heatedRoomCount = null;
		Integer habitableRoomCount = null;
		Integer sAPMainHeatingCode = null;
		
		String[] s2 = new String[2];
		String mainHeatingFraction2 = "";//HIP:Main-Heating-Fraction(第2个)
		String waterHeatingCode = "";//HIP:Water-Heating-Code
		
		Element rootElt = doc.getRootElement();//获取根节点
		XPath xpathSelector;
		Map<String, String> nameSpaceMap = new HashMap<String, String>();
		if(EpcVersion.EPC_OLD.equals(version) && (rootElt.getName().equals("ConditionReportCreateRequest_1") || rootElt.getName().equals("Energy-Performance-Certificate")))
		{
			if(rootElt.getName().equals("ConditionReportCreateRequest_1"))//英格兰
		    {
		    	String defNamespace = rootElt.getNamespaceURI();
				String xsiNamespace = rootElt.getNamespaceForPrefix("xsi") != null ? rootElt.getNamespaceForPrefix("xsi").getURI() : null;
				String hipNamespace = rootElt.getNamespaceForPrefix("HIP") != null ? rootElt.getNamespaceForPrefix("HIP").getURI() : null;
				String sapNamespace = rootElt.getNamespaceForPrefix("SAP") != null ? rootElt.getNamespaceForPrefix("SAP").getURI() : null;
				String sap09Namespace = rootElt.getNamespaceForPrefix("SAP09") != null ? rootElt.getNamespaceForPrefix("SAP09").getURI() : null;
				String csNamespace = rootElt.getNamespaceForPrefix("CS") != null ? rootElt.getNamespaceForPrefix("CS").getURI() : null;
		    	
				//处理名称空间
				if(defNamespace != null)
				{
					nameSpaceMap.put("defu", defNamespace);
				}
			    if(xsiNamespace != null)
			    {
			    	nameSpaceMap.put("xsi", xsiNamespace);
			    }
				if(hipNamespace != null)
				{
					nameSpaceMap.put("HIP", hipNamespace);
				}
				if(sapNamespace != null)
				{
					nameSpaceMap.put("SAP", sapNamespace);
				}
		        if(sap09Namespace != null)
		        {
		        	nameSpaceMap.put("SAP09", sap09Namespace);
		        }
				if(csNamespace != null)
				{
					nameSpaceMap.put("CS", csNamespace);
				}
			    //处理名称空间end
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating/HIP:Main-Heating-Fraction");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element e1 = (Element)xpathSelector.selectSingleNode(doc);
				secondMavailable = e1 != null ? e1.getText() : "";
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Secondary-Heating-Type");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element e2 = (Element)xpathSelector.selectSingleNode(doc);
				secondaryHtype = e2 != null ? e2.getText() : "";
				
				s[0] = secondMavailable;
				s[1] = secondaryHtype;
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:SAP-Data/HIP:SAP-Property-Details/HIP:Heated-Room-Count");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element heatede1 = (Element)xpathSelector.selectSingleNode(doc);
				if(heatede1 != null)
				{
					String e11 = heatede1.getText().toString();
					heatedRoomCount = Integer.parseInt(e11);
				}
				
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:SAP-Data/HIP:SAP-Property-Details/HIP:Habitable-Room-Count");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element habitablee2 = (Element)xpathSelector.selectSingleNode(doc);
				if(habitablee2 != null)
				{
					String e22 = habitablee2.getText();
					habitableRoomCount = Integer.parseInt(e22);
				}
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating/HIP:SAP-Main-Heating-Code");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element sAPMaine3 = (Element)xpathSelector.selectSingleNode(doc);
				if(sAPMaine3 != null)
				{
					String e33 = sAPMaine3.getText();
					sAPMainHeatingCode = Integer.parseInt(e33);
				}
				
				s1[0] = heatedRoomCount;
				s1[1] = habitableRoomCount;
				s1[2] = sAPMainHeatingCode;
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				@SuppressWarnings("unchecked")
				List<Element> mainHeatingFraction = xpathSelector.selectNodes(doc);
				if(mainHeatingFraction.size() >= 2)
				{
					xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating[2]/HIP:Main-Heating-Fraction");
					xpathSelector.setNamespaceURIs(nameSpaceMap);
					Element mainHeatingFrac = (Element)xpathSelector.selectSingleNode(doc);
					mainHeatingFraction2 = mainHeatingFrac != null ? mainHeatingFrac.getText() : "";
				}
				
				xpathSelector = DocumentHelper.createXPath("//defu:Content/defu:EPC-Data/SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Water-Heating-Code");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element waterHeatingCodeEle = (Element)xpathSelector.selectSingleNode(doc);
				waterHeatingCode = waterHeatingCodeEle != null ? waterHeatingCodeEle.getText() : "";
				
				s2[0] = mainHeatingFraction2;
				s2[1] = waterHeatingCode;
		    }
		    if(rootElt.getName().equals("Energy-Performance-Certificate"))//苏格兰
		    {
		    	String defNamespace = rootElt.getNamespaceURI();
				String xsiNamespace = rootElt.getNamespaceForPrefix("xsi") != null ? rootElt.getNamespaceForPrefix("xsi").getURI() : null;
				String hipNamespace = rootElt.getNamespaceForPrefix("HIP") != null ? rootElt.getNamespaceForPrefix("HIP").getURI() : null;
				String sapNamespace = rootElt.getNamespaceForPrefix("SAP") != null ? rootElt.getNamespaceForPrefix("SAP").getURI() : null;
				String csNamespace = rootElt.getNamespaceForPrefix("CS") != null ? rootElt.getNamespaceForPrefix("CS").getURI() : null;
		    	
				if(xsiNamespace != null)
				{
					nameSpaceMap.put("xsi", xsiNamespace);
				}
				if(defNamespace != null)
				{
					nameSpaceMap.put("defu", defNamespace);
				}
				if(sapNamespace != null)
				{
					nameSpaceMap.put("SAP", sapNamespace);
				}
				if(hipNamespace != null)
				{
					nameSpaceMap.put("HIP", hipNamespace);
				}
				if(csNamespace != null)
				{
					nameSpaceMap.put("CS", csNamespace);
				}
				
				xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating/HIP:Main-Heating-Fraction");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element e1 = (Element)xpathSelector.selectSingleNode(doc);
				secondMavailable = e1 != null ? e1.getText() : ""; 
				
				xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Secondary-Heating-Type");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element e2 = (Element)xpathSelector.selectSingleNode(doc);
				secondaryHtype = e2 != null ? e2.getText() : "";
				
				s[0] = secondMavailable;
				s[1] = secondaryHtype;
				
				xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/HIP:SAP-Property-Details/HIP:Heated-Room-Count");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element heatede1 = (Element)xpathSelector.selectSingleNode(doc);
				if(heatede1 != null)
				{
					String e11 = heatede1.getText().toString();
					heatedRoomCount = Integer.parseInt(e11);
				}
				
				
				xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/HIP:SAP-Property-Details/HIP:Habitable-Room-Count");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element habitablee2 = (Element)xpathSelector.selectSingleNode(doc);
				if(habitablee2 != null)
				{
					String e22 = habitablee2.getText();
					habitableRoomCount = Integer.parseInt(e22);
				}
				
				xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating/HIP:SAP-Main-Heating-Code");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element sAPMaine3 = (Element)xpathSelector.selectSingleNode(doc);
				if(sAPMaine3 != null)
				{
					String e33 = sAPMaine3.getText();
					sAPMainHeatingCode = Integer.parseInt(e33);
				}
				
				s1[0] = heatedRoomCount;
				s1[1] = habitableRoomCount;
				s1[2] = sAPMainHeatingCode;
				
				xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				@SuppressWarnings("unchecked")
				List<Element> mainHeatingFraction = xpathSelector.selectNodes(doc);
				if(mainHeatingFraction.size() >= 2)
				{
					xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Main-Heating-Details/HIP:Main-Heating[2]/HIP:Main-Heating-Fraction");
					xpathSelector.setNamespaceURIs(nameSpaceMap);
					Element mainHeatingFrac = (Element)xpathSelector.selectSingleNode(doc);
					mainHeatingFraction2 = mainHeatingFrac != null ? mainHeatingFrac.getText() : "";
				}
				
				xpathSelector = DocumentHelper.createXPath("//SAP:SAP-Data/HIP:SAP-Property-Details/HIP:SAP-Heating/HIP:Water-Heating-Code");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element waterHeatingCodeEle = (Element)xpathSelector.selectSingleNode(doc);
				waterHeatingCode = waterHeatingCodeEle != null ? waterHeatingCodeEle.getText() : "";
				
				s2[0] = mainHeatingFraction2;
				s2[1] = waterHeatingCode;
		    }
		}
		else if(EpcVersion.EPC_NEW.equals(version) || EpcVersion.EPC_OLD.equals(version))
		{
			if(rootElt.getName().equals("RdSAP-Report"))//英格兰
		    {
		    	String defNamespace = rootElt.getNamespaceURI();
		    	
				//处理名称空间
				if(defNamespace != null)
				{
					nameSpaceMap.put("defu", defNamespace);
				}
			    //处理名称空间end
				
				xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Main-Heating-Details/defu:Main-Heating/defu:Main-Heating-Fraction");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element e1 = (Element)xpathSelector.selectSingleNode(doc);
				secondMavailable = e1 != null ? e1.getText() : "";
				
				xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Secondary-Heating-Type");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element e2 = (Element)xpathSelector.selectSingleNode(doc);
				secondaryHtype = e2 != null ? e2.getText() : "";
				
				s[0] = secondMavailable;
				s[1] = secondaryHtype;
				
				xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:Heated-Room-Count");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element heatede1 = (Element)xpathSelector.selectSingleNode(doc);
				if(heatede1 != null)
				{
					String e11 = heatede1.getText().toString();
					heatedRoomCount = Integer.parseInt(e11);
				}
				
				
				xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:Habitable-Room-Count");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element habitablee2 = (Element)xpathSelector.selectSingleNode(doc);
				if(habitablee2 != null)
				{
					String e22 = habitablee2.getText();
					habitableRoomCount = Integer.parseInt(e22);
				}
				
				xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Main-Heating-Details/defu:Main-Heating/defu:SAP-Main-Heating-Code");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element sAPMaine3 = (Element)xpathSelector.selectSingleNode(doc);
				if(sAPMaine3 != null)
				{
					String e33 = sAPMaine3.getText();
					sAPMainHeatingCode = Integer.parseInt(e33);
				}
				
				s1[0] = heatedRoomCount;
				s1[1] = habitableRoomCount;
				s1[2] = sAPMainHeatingCode;
				
				xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Main-Heating-Details/defu:Main-Heating");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				@SuppressWarnings("unchecked")
				List<Element> mainHeatingFraction = xpathSelector.selectNodes(doc);
				if(mainHeatingFraction.size() >= 2)
				{
					xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Main-Heating-Details/defu:Main-Heating[2]/defu:Main-Heating-Fraction");
					xpathSelector.setNamespaceURIs(nameSpaceMap);
					Element mainHeatingFrac = (Element)xpathSelector.selectSingleNode(doc);
					mainHeatingFraction2 = mainHeatingFrac != null ? mainHeatingFrac.getText() : "";
				}
				
				xpathSelector = DocumentHelper.createXPath("//defu:SAP-Data/defu:SAP-Property-Details/defu:SAP-Heating/defu:Water-Heating-Code");
				xpathSelector.setNamespaceURIs(nameSpaceMap);
				Element waterHeatingCodeEle = (Element)xpathSelector.selectSingleNode(doc);
				waterHeatingCode = waterHeatingCodeEle != null ? waterHeatingCodeEle.getText() : "";
				
				s2[0] = mainHeatingFraction2;
				s2[1] = waterHeatingCode;
		    }
		}
		
	    mapTem.put("s", s);
	    mapTem.put("s1", s1);
	    mapTem.put("s2", s2);
		
		return mapTem;
	}
	
	@Override
	public Report initialReport(Document doc, String fileName, User user, Report temReport) throws CalculateException , ObjectDuplicateException, Exception
	{
		Report report = addReport(doc, fileName, user, temReport);
		EpcVersion version = report.getEpcVersion();
		report.setUnoccupiedPropertyable(YesNo.Yes);
		this.gdsapEvalReportMapper.updateUnoccupiedPropertyable(YesNo.Yes.getCode(), report.getId());
		
		String xml = null;
		try {
			report.setPrepareCalunoccupiedProperty(true);
			xml = calServiceMgr.cal_ReturnResult(report, null, null);
			report.setPrepareCalunoccupiedProperty(false);
		} catch (CalculateException e) {
			this.deleteReport(report.getId());
			throw new CalculateException("calculate error");
		}
		SAXReader reader = new SAXReader();
		Document document;
		Map<String,Object> mapTem = getTems(doc, version);
		try
		{
			document = reader.read(new StringReader(xml));
			
			//Occupants
			RDSAP rdsap = null;
			try {
				rdsap = (RDSAP)JavaBeanFactory.newInstance().createObject(document, RDSAP.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			double TFA = rdsap.getStandNumOfOcc();
			
			Occupants occupants = new Occupants();
			occupants.setReport(report);
			occupants.setOccupantsNumber(NumberUtils.doubleHalfUp(TFA));
			occupants.setShowerType(dictServiceMgr.getDictItem(1006));
			occupants.setBathsPerable(YesNo.No);
			occupants.setShowersPerable(YesNo.No);
			occupantsMgr.addOccupants(occupants);
			
			//HeatingSystem
			HeatingSystem heatSystem = new HeatingSystem();
			heatSystem.setReport(report);
			heatSystem.setTemperature(0);
			heatSystem.setKnownable(YesNo.No);
			heatSystem.setmHs(dictServiceMgr.getDictItem(1008));
			heatSystem.setSmHs(dictServiceMgr.getDictItem(1056));
			Integer[] temInteger = (Integer[]) mapTem.get("s1");
			Integer heatedRoomCount = temInteger[0] != null ? temInteger[0] : 0 , HabitableRoomCount = temInteger[1] != null ? temInteger[1] : 0, sAPMainHeatingCode = temInteger[2] != null ? temInteger[2] : 0;
			String[] getTem = (String[]) mapTem.get("s");
			if(getTem[1] == null || getTem[1].equals(""))
			{
				if((heatedRoomCount < HabitableRoomCount) || (sAPMainHeatingCode == 401) || (sAPMainHeatingCode == 402) || (sAPMainHeatingCode == 404) || (sAPMainHeatingCode == 409) || (sAPMainHeatingCode == 421))
				{
					heatSystem.setsHs(dictServiceMgr.getDictItem(1060));
					heatSystem.setsHf(dictServiceMgr.getDictItem(136));
					heatSystem.setsHt(dictServiceMgr.getDictItem(238));
				}
				else
				{
					heatSystem.setsHs(dictServiceMgr.getDictItem(1059));
				}
			}
			else
			{
				heatSystem.setsHs(dictServiceMgr.getDictItem(1059));
			}
			heatingSystemMgr.addHeatingSystem(heatSystem);
			
			//HeatProportion
			List<HeatProportion> listHp = heatProportionServiceMgr.getHeatProportions(report.getId());
			if(!CollectionUtils.isEmpty(listHp))
			{
				String[] tem = (String[]) mapTem.get("s");
				String[] tem2 = (String[]) mapTem.get("s2");
				if(!tem[0].equals("1"))
				{
					if(tem2[0].equals("0") && tem2[1].equals("902"))
					{
						for(int i = 0 ; i < listHp.size() ; i++)
						{
							HeatProportion hp = listHp.get(i);
							hp.setMain1(YesNo.Yes);
							hp.setMain2(YesNo.No);
							hp.setSecondary(YesNo.No);
							hp.setHeatedPartially(YesNo.No);
							hp.setNotable(YesNo.No);
							while(i == 0)
							{
								if(!tem[1].equals("") && tem[1] != null)
								{
									hp.setSecondary(YesNo.Yes);
								}
								break;
							}
							heatProportionServiceMgr.updateHeatProportion(hp);
						}
					}
					else
					{
						for(int i = 0 ; i < listHp.size() ; i++)
						{
							HeatProportion hp = listHp.get(i);
							if(i % 2==0)
							{
								hp.setMain1(YesNo.Yes);
								hp.setMain2(YesNo.No);
								hp.setSecondary(YesNo.No);
								hp.setHeatedPartially(YesNo.No);
								hp.setNotable(YesNo.No);
							}
							else
							{
								hp.setMain1(YesNo.No);
								hp.setMain2(YesNo.Yes);
								hp.setSecondary(YesNo.No);
								hp.setHeatedPartially(YesNo.No);
								hp.setNotable(YesNo.No);
							}
							while(i == 0)
							{
								if((!tem[1].equals("") && tem[1] != null) && (heatedRoomCount == HabitableRoomCount) && (sAPMainHeatingCode != 401) && (sAPMainHeatingCode != 402) && (sAPMainHeatingCode != 404) && (sAPMainHeatingCode != 421))
								{
									hp.setSecondary(YesNo.Yes);
								}
								break;
							}
							heatProportionServiceMgr.updateHeatProportion(hp);
						}
					}
					
				}
				else
				{
					for(int i = 0 ; i < listHp.size() ; i++)
					{
						HeatProportion hp = listHp.get(i);
						hp.setMain1(YesNo.Yes);
						hp.setMain2(YesNo.No);
						hp.setSecondary(YesNo.No);
						hp.setHeatedPartially(YesNo.No);
						hp.setNotable(YesNo.No);
						while(i == 0)
						{
							if(!tem[1].equals("") && tem[1] != null)
							{
								hp.setSecondary(YesNo.Yes);
							}
							break;
						}
						heatProportionServiceMgr.updateHeatProportion(hp);
					}
				}
				
			}
			
			//HeatingPattern
			HeatingPattern heatingPatterns = new HeatingPattern();
			heatingPatterns.setReport(report);
			heatingPatterns.setA1Off("23:00");
			heatingPatterns.setA1On("07:00");
			heatingPatterns.setA2Off("");
			heatingPatterns.setA2On("");
			heatingPatterns.setA3Off("");
			heatingPatterns.setA3On("");
			heatingPatterns.setA4Off("");
			heatingPatterns.setA4On("");
			heatingPatterns.setN1Off("09:00");
			heatingPatterns.setN1On("07:00");
			heatingPatterns.setN2Off("23:00");
			heatingPatterns.setN2On("16:00");
			heatingPatterns.setN3Off("");
			heatingPatterns.setN3On("");
			heatingPatterns.setN4Off("");
			heatingPatterns.setN4On("");
			heatingPatterns.setDays(2);
			heatingPatternServiceMgr.addHeatingPattern(heatingPatterns);
			
			//AppCooking
			AppCooking ac = new AppCooking();
			ac.setReport(report);
			ac.setDryProportion(25);
			ac.setFridgeFreezersNumber(0);
			ac.setFridgesNumber(1);
			ac.setFreezersNumber(1);
			ac.setCookingFuel(dictServiceMgr.getDictItem(1038));
			YesNo maingasAvilable = report.getRdsapMainGasAvailable();
			if(maingasAvilable.getCode() == YesNo.Yes.getCode())
			{
				ac.setCookingFuel(dictServiceMgr.getDictItem(1037));
			}
			else
			{
				ac.setCookingFuel(dictServiceMgr.getDictItem(1038));
			}
			ac.setCookerType(dictServiceMgr.getDictItem(1015));
			ac.setDryingClothesSpacable(YesNo.No);
			appCookingServiceMgr.addAppCooking(ac);
			
			//BillData
			TariffType tariffType = calServiceMgr.EleTariffType(report);//判断高低电价或24小时电价
			BillDataEle bd = new BillDataEle();
			bd.setReport(report);
			if(tariffType.name().equals("Standard"))
			{
				bd.setEtElectricityTariff(dictServiceMgr.getDictItem(1027));
				bd.setEtStReliablityLevel(dictServiceMgr.getDictItem(1031));
			}
			if(tariffType.name().equals("Hour_24"))
			{
				bd.setEtElectricityTariff(dictServiceMgr.getDictItem(1068));
				bd.setEt24ReliablityLevel(dictServiceMgr.getDictItem(1031));
			}
			if(tariffType.name().equals("Off_peak_7") || tariffType.name().equals("Off_peak_10"))
			{
				bd.setEtElectricityTariff(dictServiceMgr.getDictItem(1028));
				bd.setEtOffHReliablityLevel(dictServiceMgr.getDictItem(1031));
				bd.setEtOffLReliablityLevel(dictServiceMgr.getDictItem(1031));
			}
			
			billDataServiceMgr.addBillDataEle(bd);
			
			HeatingSystem heatingSystem = heatingSystemMgr.getHeatingSystem(report.getId());
			AppCooking appCooking = appCookingServiceMgr.getAppCooking(report.getId());
        	int[] fuelCodes = new int[5]; //燃料的xmlCode数组,用于判断是否为其他燃料
        	if(heatingSystem != null){
				int mHs = (int)heatingSystem.getmHs().getCalCode();
	        	int smHs = (int)heatingSystem.getSmHs().getCalCode();
	        	int sHs = (int)heatingSystem.getsHs().getCalCode();
	        	if(mHs == 1){//表示从xml里取值
	        		fuelCodes[0] = (report.getRdsapMhsFuel() != null ? report.getRdsapMhsFuel() : 0);
	        	}else{
	        		fuelCodes[0] = (heatingSystem.getmHf() != null ? (int)heatingSystem.getmHf().getCalCode() : 0);
	        	}
	        	
	        	if(smHs == 1 || smHs == 0){
	        		fuelCodes[1] = (report.getRdsapSmhsFuel() != null ? report.getRdsapSmhsFuel() : 0);
	        	}else{
	        		fuelCodes[1] = (heatingSystem.getSmHf() != null ? (int)heatingSystem.getSmHf().getCalCode() : 0);
	        	}
	        	
	        	if(sHs == 1 || sHs == 0){
	        		fuelCodes[2] = (report.getRdsapShsFuel() != null ? report.getRdsapShsFuel() : 0);
	        	}else{
	        		fuelCodes[2] = (heatingSystem.getsHf() != null ? (int)heatingSystem.getsHf().getCalCode() : 0);
	        	}
			}else{
				fuelCodes[0] = 0;fuelCodes[1] = 0;fuelCodes[2] = 0;
			}
        	fuelCodes[3] = (report.getRdsapWhsFuel() != null ? report.getRdsapWhsFuel() : 0);
        	if(appCooking != null && heatingSystem != null){
        		fuelCodes[4] = FuelXmlCode.getAppCookFuelType(report,heatingSystem,(int)appCooking.getCookingFuel().getCalCode());
        	}else{
        		fuelCodes[4] = 0;
        	}
        	
        	
        	int[] removed = FuelXmlCode.removeRepeat(fuelCodes);//判断数组重复数据fuelCodes,removed为删除重复后的数组
        	
        	Integer[] arryOtherFuel = FuelXmlCode.getOtherFuels(removed);//arryOtherFuel为筛选出的OtherFuel的code数组
        	if(arryOtherFuel != null && arryOtherFuel.length > 0){
        		if (ArrayUtils.contains(arryOtherFuel, 9) && !ArrayUtils.contains(arryOtherFuel, 6))
        		{
        			List<Integer> tempList = FuelXmlCode.changeInteger(arryOtherFuel);
        			tempList.add(6);
        			arryOtherFuel = FuelXmlCode.changeList(tempList);
        			for(int i = 0 ; i < arryOtherFuel.length ; i++){
        				OtherFuel otherFuel = new OtherFuel();
        				FuelXmlCode fuelXmlCode = (FuelXmlCode)EnumUtils.getByCode(arryOtherFuel[i], FuelXmlCode.class);
        				otherFuel.setReport(report);
        				otherFuel.setFuelCode(fuelXmlCode.getCode());
        				otherFuel.setReliablityLevel(dictServiceMgr.getDictItem(1067));
        				otherFuelServiceMgr.addOtherFuel(otherFuel);
        			}
        		}
        		if(arryOtherFuel != null && arryOtherFuel.length > 0){
        			for(int i = 0 ; i < arryOtherFuel.length ; i++){
        				OtherFuel otherFuel = new OtherFuel();
        				FuelXmlCode fuelXmlCode = (FuelXmlCode)EnumUtils.getByCode(arryOtherFuel[i], FuelXmlCode.class);
        				otherFuel.setReport(report);
        				otherFuel.setFuelCode(fuelXmlCode.getCode());
        				otherFuel.setReliablityLevel(dictServiceMgr.getDictItem(1067));
        				otherFuelServiceMgr.addOtherFuel(otherFuel);
        			}
        	}
        	
        	Integer[] arryMains_Gas = FuelXmlCode.getMains_Gas(removed);//arryMains_Gas为筛选出的Mains_Gas的code数组
        	if(arryMains_Gas != null && arryMains_Gas.length > 0)
        	{
        		for(int i = 0 ; i < arryMains_Gas.length ; i++)
        		{
        			BillDataMg bdMG = new BillDataMg();
        			bdMG.setReport(report);
        			bdMG.setMgReliablityLevel(dictServiceMgr.getDictItem(1031));
        			billDataServiceMgr.addBillDataMg(bdMG);
        		}
        	}
        	
        	Integer[] arryCommunity_heating = FuelXmlCode.getCommunity_heating(removed);//arryCommunity_heating为筛选出的Community_heating的code数组
        	if(arryCommunity_heating != null && arryCommunity_heating.length > 0){
        		for(int i = 0 ; i < arryCommunity_heating.length ; i++){
        			FuelXmlCode fuelXmlCode = (FuelXmlCode)EnumUtils.getByCode(arryCommunity_heating[i], FuelXmlCode.class);
        			BillDataComm bdComm = new BillDataComm();
        			bdComm.setReport(report);
        			bdComm.setChFuelCode(fuelXmlCode.getCode());
        			bdComm.setChReliablityLevel(dictServiceMgr.getDictItem(1065));
        			billDataServiceMgr.addBillDataComm(bdComm);
            	}
        	}
        	
        	Integer[] arryLPG_subject = FuelXmlCode.getLPGsubject(removed);
        	if(arryLPG_subject != null && arryLPG_subject.length > 0){
        		for(int i = 0 ; i < arryLPG_subject.length ; i++){
        			BillDataLPG bdLPG = new BillDataLPG();
        			bdLPG.setReport(report);
        			bdLPG.setLpgReliablityLevel(dictServiceMgr.getDictItem(1065));
        			billDataServiceMgr.addBillDataLPG(bdLPG);
            	}
        	}
        	
        	}
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return report;
	}

	@Override
	public Report updateBaseReportInfo(Report report)
	{
		Assert.notNull(report);
		Assert.notNull(report.getId());
		Assert.notNull(report.getInspectionDate());
		Assert.notNull(report.getCompletionDate());
		//Assert.notNull(report.getRegistrationDate());
		Assert.notNull(report.getRelatedPartyDisclosure());
		
		GdsapEvalReport model = new GdsapEvalReport();
		model.setId(report.getId());
		model.setInspectionDate(report.getInspectionDate());
		//model.setRegistrationDate(report.getRegistrationDate());
		model.setCompletionDate(report.getCompletionDate());
		model.setRelatedPartyDisclosure(report.getRelatedPartyDisclosure().getId());
		model.setGdipLigXmlFile(report.getLigXmlFile());
		model.setUpdateTime(new Date());
		if(report.isBuilderOaRrn())
		{
			model.setOaRrn(RRNBuilder.buildDRrn(RRNBuilder.DReportType.GDOA_TYPE, report.getUprn(), report.getInspectionDate()));
		}
		else
		{
			model.setOaRrn(report.getOaRrn());
		}
		this.gdsapEvalReportMapper.updateSelective(model);
		return this.getReport(report.getId());
	}

	@Override
	public Report updateReportAddressInfo(Report report)
	{
		Assert.notNull(report);
		Assert.notNull(report.getId());
		
		GdsapEvalReport model = new GdsapEvalReport();
		model.setId(report.getId());
		model.setUpdateTime(new Date());
		model.setAddress1(report.getAddress1());
		model.setAddress2(report.getAddress2());
		model.setAddress3(report.getAddress3());
		model.setPostcode(report.getPostcode());
		model.setPosttown(report.getPosttown());
		this.gdsapEvalReportMapper.updateSelective(model);
		return this.getReport(report.getId());
	}
	
	@Override
	public Report updateReportStatus(Report report, ReportStatus reportStatus)
	{
		Assert.notNull(report);
		Assert.notNull(reportStatus);
		Assert.notNull(report.getId());
		GdsapEvalReport model = new GdsapEvalReport();
		model.setId(report.getId());
		model.setReportStatus(reportStatus.getCode());
		model.setUpdateTime(new Date());
		model.setLodgeDate(report.getLodgeDate());
		this.gdsapEvalReportMapper.updateSelective(model);
		System.out.println("************************** updateReportStatus method -> " + DateUtil.date2String(new Date(), "yyyy-MM-dd hh:mm:ss"));
		return report;
	}
	
	@Override
	public Report thirdPartyLodgeReport(ServiceRequest request) throws ServiceException
	{
		Assert.notNull(request);
		Assert.notNull(request.getEpcRrn());
		Assert.notNull(request.getOaRrn());
		Assert.notNull(request.getGdipRrn());
		Assert.notNull(request.getGdipLig());
		String companyId = request.getCompanyID();
		String companyJobRef = request.getCompanyJobRef();
		
		Company company = companyServiceMgr.getCompany(companyId);
		if(company == null)
		{
			throw new ServiceException("Company_ID is invalid");
		}
		GdsapEvalReport gdsapReport = gdsapEvalReportMapper.findByCompany_companyJobRef(company.getCompanyId(), companyJobRef);
		if(gdsapReport != null)
		{
			throw new ServiceException("A job with this Job reference has already been created.");
		}
		ReportLocation reportLocation = null;
		String epcXml = null;
		try {
			epcXml = WebserviceClient.getInstance().getEPCXml(request.getEpcRrn(),GlobalConfig.getInstance().getGDIPLodgementAuthUsername(ReportLocation.EAW), GlobalConfig.getInstance().getGDIPLodgementAuthPassword(ReportLocation.EAW));
			if(!StringUtils.isEmpty(epcXml))
			{
				reportLocation = ReportLocation.EAW;
			}
			else
			{
				epcXml = WebserviceClient.getInstance().getEPCXml(request.getEpcRrn(),GlobalConfig.getInstance().getGDIPLodgementAuthUsername(ReportLocation.SCT), GlobalConfig.getInstance().getGDIPLodgementAuthPassword(ReportLocation.SCT));
				if(!StringUtils.isEmpty(epcXml))
				{
					reportLocation = ReportLocation.SCT;
				}
				else
				{
					throw new ServiceException("the EPC RRN is invalid");
				}
			}
		} catch (Exception e) {
			try {
				epcXml = WebserviceClient.getInstance().getEPCXml(request.getEpcRrn(),GlobalConfig.getInstance().getGDIPLodgementAuthUsername(ReportLocation.SCT), GlobalConfig.getInstance().getGDIPLodgementAuthPassword(ReportLocation.SCT));
				if(!StringUtils.isEmpty(epcXml))
				{
					reportLocation = ReportLocation.SCT;
				}
				else
				{
					throw new ServiceException("the EPC RRN is invalid");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new ServiceException("the EPC RRN is invalid");
			} 
		}
		
		Report report = new Report(request);
		report.setCompany(company);
		report.setCompanyJobRef(companyJobRef);
		
		String originalXmlContent = request.getGdipLig().asXML();
		Document lodgeDoc = request.getGdipLig();
		lodgeDoc.getRootElement().element("GDIP-RRN").setText(report.getLodgedGdipRrn());
		String xmlContent = lodgeDoc.asXML();
		
		ResponseObject ro = null;
		if(reportLocation.equals(ReportLocation.EAW) || reportLocation.equals(ReportLocation.NIR))
		{
			try {
				ro = ClgRestClientUtil.post(GlobalConfig.getInstance().getEAWGdipLodgementAddress()	,
						GlobalConfig.getInstance().getGDIPLodgementAuthUsername(reportLocation) + ":" + GlobalConfig.getInstance().getGDIPLodgementAuthPassword(reportLocation),
						GlobalConfig.getInstance().getEAWGdipCert(),GlobalConfig.getInstance().getEAWGdipCertPassword(),xmlContent);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("***************************************** " + ro.getResponseStatus().getCode() + " -- rrn " + report.getRrn() + " -- oarrn " + report.getOaRrn());
			
			if (ro.getResponseStatus().equals(ResponseStatus.Success)) {
				report.setGdipLodgeDate(new Date());
				String requestSavePath = writeAndSave(request.getRequestBody(), "txt");
				report.setReportXmlFile(requestSavePath);
				report.setReportStatus(ReportStatus.GDIP_WebService_Lodged);
				report.setRequestXmlFile(requestSavePath);
				report.setLigXmlFile(writeAndSave(originalXmlContent, "xml"));
				report.setLodgedGdipLigXmlFile(writeAndSave(xmlContent, "xml"));
				report.setReportLocation(reportLocation);
				GdsapEvalReport dbReport = BeanUtils.user2gdsapUsrUser(report);
				this.gdsapEvalReportMapper.insert(dbReport);
			}
			else if (ro.getResponseStatus().equals(ResponseStatus.Fail_400))
			{
				throw new ServiceException(StringEscapeUtils.escapeHtml(ro.getResponseMessage().getMessage()));
			}
			else{
				throw new ServiceException("lodge failed");
			}
		}
		else
		{
//			try {
//				sctWSResponse = SCTWebserviceUtil.lodgeOAxml(GlobalConfig.getInstance().getLodgeOAXmlWebserviceAddressOfSCT(report.getOaRrn()), 
//						GlobalConfig.getInstance().getCertOfSCT(), 
//						GlobalConfig.getInstance().getCertPasswordOfSCT(), 
//						report.getUser().getSctAuthorizationUsername(), 
//						report.getUser().getSctAuthorizationPassword(), 
//						report.getUser().getSctAssessorOrganisationid(), 
//						report.getUser().getSctAdviserId(), xmlContent);
//			} catch (Exception e) {
//				e.printStackTrace();
//				report.setGdipLodgeDate(null);
//				reportServiceMgr.updateReportStatus(report, ReportStatus.GDIP_In_Process);
//			}
		}
		
		
		return report;
	}
	
	/**
	 * 写入内容到文本文件，并返回存储路径的相对地址
	 * @param content
	 * @param ext
	 * @return
	 */
	private String writeAndSave(String content, String ext)
	{
		String newFilename = UUID.randomUUID().toString().replace("-", "") + "." + ext;
		String datedir = GlobalUtils.dateDir(new Date());
		String newfilePath = GlobalConfig.getInstance().getFSDir() + datedir;
		if (!new File(newfilePath).exists())
		{
			new File(newfilePath).mkdirs();
		}
		String newFullFilepath = newfilePath + newFilename;
		String newdbFilename = datedir + newFilename;
        byte[] bytes;
		bytes = content.getBytes();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(newFullFilepath);
			fos.write(bytes);
		    fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fos != null)
			{
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
        return newdbFilename;
	}
	
	public static void main(String[] args) 
	{
		String folderPath = "C:\\Users\\tyr\\Desktop\\991OAInput";
		File file = new File(folderPath);
		File[] files = file.listFiles();
		
		int num = 0;
		for(File f : files)
		{
			Document ligDoc = DocumentUtil.readDocument(f);
			double result = getDvalueInLig(ligDoc);
			if(result > 10 || result < -10)
			{
				num++;
				System.out.println(f.getName());
			}
		}
		
		System.out.println("存在错误的数量" + num);
	}
}