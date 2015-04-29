/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import pw.itcircle.javaBeanFactory.factory.JavaBeanFactory;
import pw.itcircle.javaBeanFactory.tools.StringUtil;
import uk.co.quidos.common.util.DateUtil;
import uk.co.quidos.common.util.StringUtils;
import uk.co.quidos.common.util.TemplateUtil;
import uk.co.quidos.common.util.io.FileStoreUtils;
import uk.co.quidos.gdsap.evaluation.AppCooking;
import uk.co.quidos.gdsap.evaluation.BillDataComm;
import uk.co.quidos.gdsap.evaluation.BillDataEle;
import uk.co.quidos.gdsap.evaluation.BillDataLPG;
import uk.co.quidos.gdsap.evaluation.BillDataMg;
import uk.co.quidos.gdsap.evaluation.EpcEADetail;
import uk.co.quidos.gdsap.evaluation.EpcImprovementCalResult;
import uk.co.quidos.gdsap.evaluation.HeatProportion;
import uk.co.quidos.gdsap.evaluation.HeatingPattern;
import uk.co.quidos.gdsap.evaluation.HeatingSystem;
import uk.co.quidos.gdsap.evaluation.Occupants;
import uk.co.quidos.gdsap.evaluation.OtherFuel;
import uk.co.quidos.gdsap.evaluation.PerFuelCalResult;
import uk.co.quidos.gdsap.evaluation.Report;
import uk.co.quidos.gdsap.evaluation.Solution;
import uk.co.quidos.gdsap.evaluation.StandardRecommendationCalResult;
import uk.co.quidos.gdsap.evaluation.StandardRecommendationWrap;
import uk.co.quidos.gdsap.evaluation.calculate.CalOutError;
import uk.co.quidos.gdsap.evaluation.calculate.output.OATrunk;
import uk.co.quidos.gdsap.evaluation.enums.ReportLocation;
import uk.co.quidos.gdsap.evaluation.enums.ReportStatus;
import uk.co.quidos.gdsap.evaluation.enums.SolutionType;
import uk.co.quidos.gdsap.evaluation.object.TotalEpcImprovementCalResult;
import uk.co.quidos.gdsap.evaluation.object.TotalEstimatedCosts;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalSolutionMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalSolution;
import uk.co.quidos.gdsap.evaluation.services.AppCookingServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.BillDataServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.CalResultServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.CalServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.EpcEADetailServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.HeatProportionServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.HeatingPatternServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.HeatingSystemServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.OccupantsServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.OtherFuelServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.ReportCommonServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.ReportServiceMgr;
import uk.co.quidos.gdsap.evaluation.utils.ClgRestClientUtil;
import uk.co.quidos.gdsap.evaluation.utils.RestClientUtil;
import uk.co.quidos.gdsap.evaluation.utils.SCTWSResponse;
import uk.co.quidos.gdsap.evaluation.utils.SCTWebserviceUtil;
import uk.co.quidos.gdsap.evaluation.wsclient.ResponseMessage;
import uk.co.quidos.gdsap.evaluation.wsclient.ResponseObject;
import uk.co.quidos.gdsap.evaluation.wsclient.ResponseStatus;
import uk.co.quidos.gdsap.framework.common.Preference;
import uk.co.quidos.gdsap.framework.common.enums.PreferenceRelId;
import uk.co.quidos.gdsap.framework.common.services.PreferenceServiceMgr;
import uk.co.quidos.gdsap.framework.enums.UserType;
import uk.co.quidos.gdsap.framework.exception.CalculateException;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;
import uk.co.quidos.gdsap.framework.sysconf.GlobalConfig;
import uk.co.quidos.gdsap.framework.sysconf.GlobalUtils;
import uk.co.quidos.gdsap.framework.user.User;

/**
 * @author peng.shi
 */
@Service("reportCommonServiceMgr")
@Transactional
public class ReportCommonServiceMgrImpl extends AbsBusinessObjectServiceMgr implements ReportCommonServiceMgr
{
	@Autowired
	private AppCookingServiceMgr appCookingServiceMgr;
	@Autowired
	private OccupantsServiceMgr occupantsServiceMgr;
	@Autowired
	private EpcEADetailServiceMgr epcEADetailServiceMgr;
	@Autowired
	private HeatingPatternServiceMgr heatingPatternServiceMgr;
	@Autowired
	private HeatingSystemServiceMgr heatingSystemServiceMgr;
	@Autowired
	private HeatProportionServiceMgr heatProportionServiceMgr;
	@Autowired
	private OtherFuelServiceMgr otherFuelServiceMgr;
	@Autowired
	private BillDataServiceMgr billDataServiceMgr;
	@Autowired
	private PreferenceServiceMgr preferenceServiceMgr;
	@Autowired
	private CalResultServiceMgr calResultServiceMgr;
	@Autowired
	private ReportServiceMgr reportServiceMgr;
	@Autowired
	private CalServiceMgr calServiceMgr;

	/*
	 * (non-Javadoc)
	 * 
	 * @seeuk.co.quidos.gdsap.evaluation.services.ReportCommonServiceMgr#
	 * solutionCalResultXml(uk.co.quidos.gdsap.evaluation.Solution)
	 */
	@Override
	public String solutionCalResultXml(Solution solution, List<StandardRecommendationWrap> srsw) throws CalculateException
	{
		Assert.notNull(solution);
		Report report = solution.getReport();
		
		EpcEADetail epcEaDetail = this.epcEADetailServiceMgr.getEpcEADetail(report.getId());
		Assert.notNull(epcEaDetail);
		AppCooking appCooking = this.appCookingServiceMgr.getAppCooking(report.getId());
		Assert.notNull(appCooking);
		Occupants occupants = this.occupantsServiceMgr.getOccupants(report.getId());
		Assert.notNull(occupants);
		HeatingSystem heatingSystem = this.heatingSystemServiceMgr.getHeatingSystem(report.getId());
		Assert.notNull(heatingSystem);
		HeatingPattern heatingPattern = this.heatingPatternServiceMgr.getHeatingPattern(report.getId());
		Assert.notNull(heatingPattern);
		List<HeatProportion> heatProportions = this.heatProportionServiceMgr.getHeatProportions(report.getId());
		Assert.notEmpty(heatProportions);
		BillDataEle billDataEle = this.billDataServiceMgr.getBillDataEle(report.getId());
		BillDataMg billDataMg = this.billDataServiceMgr.getBillDataMg(report.getId());
		BillDataComm billDataComm = this.billDataServiceMgr.getBillDataComm(report.getId());
		BillDataLPG billDataLPG = this.billDataServiceMgr.getBillDataLPG(report.getId());
//		Assert.notNull(billDataEle);
		List<OtherFuel> otherlist = this.otherFuelServiceMgr.getOtherFuelsByReportId(report.getId());
		
//		List<PerFuelCalResult> perFuelCalResults = this.calResultServiceMgr.getPerFuelCalResults(solution);
		
		String organisationCertificationNumber = null;
//		if (report.getReportLocation().equals(ReportLocation.EAW)) {
//			organisationCertificationNumber = GlobalConfig.getInstance().getEAWOrganisationCertificationnumber();
//		}else if (report.getReportLocation().equals(ReportLocation.SCT)) {
//			organisationCertificationNumber = GlobalConfig.getInstance().getSCTOrganisationCertificationnumber();
//		}
		organisationCertificationNumber = report.getUser().getOrganisationCertificationNumber();
		
		String organisation = null;
		if (report.getReportLocation().equals(ReportLocation.EAW)) {
			organisation = GlobalConfig.getInstance().getEAWOrganisation();
		}else if (report.getReportLocation().equals(ReportLocation.SCT)) {
			organisation = GlobalConfig.getInstance().getSCTOrganisation();
		}
		
		organisation = report.getUser().getOrganisation();
		
		String organisationWebsite = null;
		if (report.getReportLocation().equals(ReportLocation.EAW) || report.getReportLocation().equals(ReportLocation.NIR)) {
			organisationWebsite = GlobalConfig.getInstance().getEAWOrganisationWebsite();
		}else if (report.getReportLocation().equals(ReportLocation.SCT)) {
			organisationWebsite = GlobalConfig.getInstance().getSCTOrganisationWebsite();
		}
		organisationWebsite = report.getUser().getOrganisationWebSite();
		
		// 对Perfuel列表继续进行排序
//		if(perFuelCalResults != null && perFuelCalResults.size() > 0){
//			Collections.sort(perFuelCalResults, new PerfuelFuelCodeComparator());
//		}
//		
		User user = solution.getReport().getUser();

//		SolutionIssue solutionIssue = this.calResultServiceMgr.getSolutionIssue(solution);
//		List<EpcImprovementCalResult> epcImprovementCalResults = this.calResultServiceMgr.getEpcImprovementCalResults(solution);
//		TotalEpcImprovementCalResult totalEpcImprovementCalResult = this.getTotalEpcImprovementCalResult(epcImprovementCalResults);
		
		// CalResult
//		CalResult calResult = this.calResultServiceMgr.getCalResult(solution.getId());
//		Assert.notNull(calResult);
//		List<FuelCalResult> fuelCalResults = this.calResultServiceMgr.getFuelCalResults(solution.getId());
//		List<StandardRecommendationCalResult> srCalResults = this.calResultServiceMgr.getStandardRecommendationCalResults(solution.getId());
//		Assert.notEmpty(srCalResults);

//		TotalEstimatedCosts totalSrCosts = this.getTotalEstimatedCosts(srCalResults);

		Map<String, Object> datas = new LinkedHashMap<String, Object>();
		if(billDataLPG != null)
		{
			datas.put("billDataLPG", billDataLPG);
		}
		
		datas.put("report", report);
		datas.put("epcEaDetail", epcEaDetail);
		datas.put("appCooking", appCooking);
		datas.put("occupants", occupants);
		datas.put("heatingSystem", heatingSystem);
		datas.put("heatingPattern", heatingPattern);
		datas.put("heatProportions", heatProportions);
		datas.put("billDataEle", billDataEle);
		datas.put("organisationCertificationNumber", organisationCertificationNumber);
		datas.put("organisation", organisation);
		datas.put("organisationWebSite", organisationWebsite);
		
		if (billDataMg != null)
		{
			datas.put("billDataMg", billDataMg);
		}
		if (billDataComm != null)
		{
			datas.put("billDataComm", billDataComm);
		}
		datas.put("otherFuel", otherlist);
//		datas.put("calResult", calResult);
//		datas.put("fuelCalResults", fuelCalResults);
//		datas.put("srCalResults", srCalResults);
//		datas.put("totalSRCosts", totalSrCosts);
//		datas.put("solutionIssue", solutionIssue);
//		datas.put("epcImprovementCalResults", epcImprovementCalResults);
//		datas.put("totalEpcImprovementCalResult", totalEpcImprovementCalResult);
//		datas.put("perFuelCalResults", perFuelCalResults);
		datas.put("registrationDate", new Date());
		datas.put("user", user);
		
		Preference preference = null;
		if(SolutionType.GDAR.equals(solution.getSolutionType()))
		{
			preference = this.preferenceServiceMgr.getPreference(PreferenceRelId.GDAR_LODGE_XML_TEMPLATE);
			Assert.notNull(preference);
			String template = preference.getContent();
			Assert.hasText(template);
			
			String partGdarLig = TemplateUtil.template2String(template, datas);
			if(srsw != null)
			{
				Document calDocument = calServiceMgr.prepareCal(solution.getReport(), srsw);
				return returnLigContent(partGdarLig, solution, calDocument);
			}
			else
			{
				return returnLigContent(partGdarLig, solution, null);
			}
		}
		else if(SolutionType.GDIP.equals(solution.getSolutionType()))
		{
			preference = this.preferenceServiceMgr.getPreference(PreferenceRelId.GDIP_LODGE_XML_TEMPLATE);
			Assert.notNull(preference);
			String template = preference.getContent();
			Assert.hasText(template);
			
			String partGdarLig = TemplateUtil.template2String(template, datas);
			if(srsw != null)
			{
				Document calDocument = calServiceMgr.prepareCal(solution.getReport(), srsw);
				return returnLigContent(partGdarLig, solution, calDocument);
			}
			else
			{
				return returnLigContent(partGdarLig, solution, null);
			}
		}
		return null;
	}
	
	private String returnLigContent(String partLig, Solution solution, Document calDocument) throws CalculateException
	{
		if(calDocument != null)
		{
			if(SolutionType.GDAR.equals(solution.getSolutionType()))
			{
				OATrunk oaTrunk = new OATrunk(null, calDocument.asXML(), null, solution.getReport().getReportLocation().toString(), solution.getReport().getEpcVersion().getVersionNum());
				String result = RestClientUtil.post(GlobalConfig.getInstance().getGdarLigXmlWebserviceAddr(), oaTrunk.toJosn());
				CalOutError error = null;
				try {
					error = (CalOutError)JavaBeanFactory.newInstance().createObject(result, CalOutError.class);
				} catch (Exception e) {
					//e.printStackTrace();
				}
				if(error != null){
					throw new CalculateException(error.getMessage());
				}
				
				String rep = "<WaitingReplacement></WaitingReplacement>";
				partLig = partLig.replaceAll(rep, result.trim());
				savePartLig(solution, result.trim());
				return partLig;
			}
			else if(SolutionType.GDIP.equals(solution.getSolutionType()))
			{
				OATrunk oaTrunk = new OATrunk(null, calDocument.asXML(), null, solution.getReport().getReportLocation().toString(), solution.getReport().getEpcVersion().getVersionNum());
				String result = RestClientUtil.post(GlobalConfig.getInstance().getGdipLigXmlWebserviceAddr(), oaTrunk.toJosn());
				CalOutError error = null;
				try {
					error = (CalOutError)JavaBeanFactory.newInstance().createObject(result, CalOutError.class);
				} catch (Exception e) {
					//e.printStackTrace();
				}
				if(error != null){
					System.out.println(error.getStack());
					throw new CalculateException(error.getMessage());
				}
				
				result = result.replaceAll("<GDIPRoot>", "").replaceAll("</GDIPRoot>", "");
				String rep = "<WaitingReplacement></WaitingReplacement>";
				partLig = partLig.replaceAll(rep, result);
				savePartLig(solution, result.trim());
				return partLig;
			}
		}
		else
		{
			String path = GlobalConfig.getInstance().getFSDir() + solution.getSolutionPartLodgeXmlPath();
			if(StringUtils.isEmpty(path))
			{
				throw new IllegalArgumentException();
			}
			String xmlContent = null;
			try {
				xmlContent = FileUtils.readFileToString(new File(path),"utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(StringUtils.isEmpty(xmlContent))
			{
				throw new IllegalArgumentException();
			}
			if(SolutionType.GDAR.equals(solution.getSolutionType()))
			{
				String rep = "<WaitingReplacement></WaitingReplacement>";
				partLig = partLig.replaceAll(rep, xmlContent);
				return partLig;
			}
			else if(SolutionType.GDIP.equals(solution.getSolutionType()))
			{
				xmlContent = xmlContent.replaceAll("<GDIPRoot>", "").replaceAll("</GDIPRoot>", "");
				String rep = "<WaitingReplacement></WaitingReplacement>";
				partLig = partLig.replaceAll(rep, xmlContent);
				return partLig;
			}
		}
		
		return null;
	}

	private Solution savePartLig(Solution solution, String result)
	{
		FileStoreUtils.deleteFile(GlobalConfig.getInstance().getFSDir() + gdsapEvalSolutionMapper.load(solution.getId()).getSolutionPartLodgeXmlPath());
		
		String filename = UUID.randomUUID().toString();
		String datedir = GlobalUtils.dateDir(new Date());

		String parentDir = GlobalConfig.getInstance().getFSDir();
		if (!new File(parentDir + datedir).exists())
		{
			new File(parentDir + datedir).mkdirs();
		}
		File partLodgeFile = new File(parentDir + datedir + filename + ".txt");
		try
		{
			FileUtils.writeStringToFile(partLodgeFile, result, "utf-8");
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		solution.setSolutionPartLodgeXmlPath(datedir + filename + ".txt");
		GdsapEvalSolution solutionModel = BeanUtils.bo2do(solution);
		this.gdsapEvalSolutionMapper.update(solutionModel);
		return solution;
	}
	
	@Override
	public void prettyXmlFormat(File lodgeFile, ReportLocation reportLocation)
	{
		SAXReader reader = new SAXReader();
		try
		{
			Document document = reader.read(lodgeFile);
			OutputFormat format = OutputFormat.createPrettyPrint();
			if (reportLocation.equals(ReportLocation.EAW) || reportLocation.equals(ReportLocation.NIR))
			{
				format.setEncoding("utf-8");
			} else if (reportLocation.equals(ReportLocation.SCT))
			{
				format.setEncoding("utf-8");
			}
			try
			{
				XMLWriter writer = new XMLWriter(new FileOutputStream(lodgeFile), format);
				writer.write(document);
				writer.close();
			} catch (IOException e)
			{
				e.printStackTrace();
				throw new IllegalArgumentException();
			}
		} catch (DocumentException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException();
		}

	}

	public TotalEpcImprovementCalResult getTotalEpcImprovementCalResult(List<EpcImprovementCalResult> epcImprovementCalResults)
	{
		TotalEpcImprovementCalResult total = new TotalEpcImprovementCalResult();
		float totalEstimatedSaving = 0f;
		float totalTypicalSaving = 0f;
		float totalStartTotalIndicativeCost = 0f;
		float totalEndIndicativeCost = 0f;
		if (!CollectionUtils.isEmpty(epcImprovementCalResults))
		{
			for (EpcImprovementCalResult calResult : epcImprovementCalResults)
			{
				totalEstimatedSaving = totalEstimatedSaving + calResult.getEstimatedSaving();
				totalTypicalSaving = totalTypicalSaving + calResult.getTypicalSaving();
				totalStartTotalIndicativeCost = totalStartTotalIndicativeCost + calResult.getIndicativeCostStart();
				totalEndIndicativeCost = totalEndIndicativeCost + calResult.getIndicativeCostEnd();
			}
		}
		total.setTotalEndIndicativeCost(totalEndIndicativeCost);
		total.setTotalEstimatedSaving(totalEstimatedSaving);
		total.setTotalStartTotalIndicativeCost(totalStartTotalIndicativeCost);
		total.setTotalTypicalSaving(totalTypicalSaving);
		return total;
	}

	public TotalEstimatedCosts getTotalEstimatedCosts(List<StandardRecommendationCalResult> srCalResuls)
	{
		TotalEstimatedCosts total = new TotalEstimatedCosts(0f, 0f, 0f, 0f);
		float totalStart = 0f;
		float totalEnd = 0f;
		float totalActual = 0f;
		float totalTypical = 0f;
		if (!CollectionUtils.isEmpty(srCalResuls))
		{
			for (StandardRecommendationCalResult sr : srCalResuls)
			{
				totalStart = totalStart + sr.getEstimatedCostsStart();
				totalEnd = totalEnd + sr.getEstimatedCostsEnd();
				totalActual = totalActual + sr.getEstimatedAnnualSavings();
				totalTypical = totalTypical + sr.getTypicalAnnualSavings();
			}
			total = new TotalEstimatedCosts(totalStart, totalEnd, totalActual, totalTypical);
		}
		return total;
	}

	@Override
	public ResponseObject findAddressList(String postCode){
		ResponseObject ro = null;
		
		String[] temp = postCode.split("\\s");
		if(temp != null && temp.length > 0){
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < temp.length; i++){
				sb.append(temp[i].trim());
				if(i != temp.length - 1){
					sb.append("%20");
				}
			}
			ro = ClgRestClientUtil.get(GlobalConfig.getInstance().getFindAddressListUrl() + sb.toString());
		}
		
		return ro;
	}
	
	/**
	 * 根据邮编寻址
	 * @param postCode
	 * @return
	 */
	public ResponseObject findAddress(String  country, String id){
		ResponseObject ro = null;
		ro = ClgRestClientUtil.get(GlobalConfig.getInstance().getFindAddressUrl() + country + "/" + id);
		return ro;
	}
	
	@Override
	public ResponseObject lodgeReport(Solution solution)
	{
		Report report = solution.getReport();
		SolutionType solutionType = solution.getSolutionType();
		ReportStatus originalStatus = report.getReportStatus();
		
		if(SolutionType.GDAR.equals(solutionType))
		{
			if (report.getReportStatus().equals(ReportStatus.Lodged) || report.getReportStatus().equals(ReportStatus.Canceled)) {
				throw new IllegalArgumentException();
			}
			
			if (report.getReportLocation().equals(ReportLocation.EAW) || report.getReportLocation().equals(ReportLocation.NIR)) {
				//上传到EAW中或者nir
				String path = GlobalConfig.getInstance().getFSDir() + solution.getSolutionLodgeXmlPath();
				try {
					String xmlContent = FileUtils.readFileToString(new File(path),"utf-8");
					report.setLodgeDate(null);
					reportServiceMgr.updateReportStatus(report, ReportStatus.Lodging);
					ResponseObject ro = null;
					try {
						ro = ClgRestClientUtil.post(GlobalConfig.getInstance().getEAWLodgementAddress()	,
								solution.getReport().getUser().getAuthUsername() + ":" + solution.getReport().getUser().getAuthPassword(),
								GlobalConfig.getInstance().getEAWCert(),GlobalConfig.getInstance().getEAWCertPassword(),xmlContent);
					} catch (Exception e) {
						e.printStackTrace();
						report.setLodgeDate(null);
						reportServiceMgr.updateReportStatus(report, originalStatus);
					}
					System.out.println("***************************************** " + ro.getResponseStatus().getCode() + " -- rrn " + report.getRrn() + " -- oarrn " + report.getOaRrn());
					
					if (ro.getResponseStatus().equals(ResponseStatus.Success)) {
						report.setLodgeDate(new Date());
						reportServiceMgr.updateReportStatus(report, ReportStatus.Lodged);
						System.out.println("******************************** updateReportStatus finish " + DateUtil.date2String(new Date(), "yyyy-MM-dd hh:mm:ss"));
					}else{
						report.setLodgeDate(null);
						reportServiceMgr.updateReportStatus(report, originalStatus);
					}
					return ro;
				} catch (IOException e) {
					e.printStackTrace();
					throw new IllegalArgumentException();
				}
			}else {
				String path = GlobalConfig.getInstance().getFSDir() + solution.getSolutionLodgeXmlPath();
				String xmlContent;
				try {
					xmlContent = FileUtils.readFileToString(new File(path), "utf-8");
				} catch (IOException e) {
					e.printStackTrace();
					throw new IllegalArgumentException();
				}
				report.setLodgeDate(null);
				reportServiceMgr.updateReportStatus(report, ReportStatus.Lodging);
				SCTWSResponse sctWSResponse = null;
				try {
					sctWSResponse = SCTWebserviceUtil.sctLodgeOAxml(GlobalConfig.getInstance().getLodgeOAXmlWebserviceAddressOfSCT(report.getOaRrn()), 
							GlobalConfig.getInstance().getCertOfSCT(), 
							GlobalConfig.getInstance().getCertPasswordOfSCT(), 
							report.getUser().getSctAuthorizationUsername(), 
							report.getUser().getSctAuthorizationPassword(), 
							report.getUser().getSctAssessorOrganisationid(), 
							report.getUser().getSctAdviserId(), xmlContent);
				} catch (Exception e) {
					e.printStackTrace();
					report.setLodgeDate(null);
					reportServiceMgr.updateReportStatus(report, originalStatus);
				}
				
				if (sctWSResponse.isResponseStatus()) {
					//成功 --> 获取pdf
					String pdfFilePath = DateUtil.date2String(new Date(), "/yyyy/MM/dd/");
					if (!new File(GlobalConfig.getInstance().getFSDir() + pdfFilePath).exists()) {
						new File(GlobalConfig.getInstance().getFSDir() + pdfFilePath).mkdirs();
					}
					
					pdfFilePath = pdfFilePath + UUID.randomUUID().toString() + ".pdf";
					SCTWebserviceUtil.getOAPdf(GlobalConfig.getInstance().getGetOAPDFWebserviceAddressOfSCT(report.getOaRrn()), GlobalConfig.getInstance().getCertOfSCT(), GlobalConfig.getInstance().getCertPasswordOfSCT(), 
							report.getUser().getSctAuthorizationUsername(), report.getUser().getSctAuthorizationPassword(), report.getUser().getSctAssessorOrganisationid(), report.getUser().getSctAdviserId(), 
							GlobalConfig.getInstance().getFSDir() + pdfFilePath);
					report.setLodgeDate(new Date());
					reportServiceMgr.updateReportStatus(report, ReportStatus.Lodged);
					gdsapEvalSolutionMapper.updateSolutionPdfPathById(solution.getId(), pdfFilePath, new Date());
					
					ResponseObject responseObject = new ResponseObject(ResponseStatus.Success);
					return responseObject;
				}else{
					report.setLodgeDate(null);
					reportServiceMgr.updateReportStatus(report, ReportStatus.In_Process);
					ResponseObject responseObject = new ResponseObject(ResponseStatus.Fail_SCT);
					ResponseMessage message = new ResponseMessage();
					String messageContent = null;
					if (sctWSResponse.getResponseCode() == 401) {
						messageContent = sctWSResponse.getResponseMessage();
					}else{
						messageContent = sctWSResponse.getErrorMessage();
						if(StringUtil.haveContent(sctWSResponse.getComment()))
						{
							messageContent += ":" + sctWSResponse.getComment();
						}
					}
					message.setMessage(messageContent);
					responseObject.setResponseMessage(message);
					return responseObject;
				}
			}
		}
		else if(SolutionType.GDIP.equals(solutionType))
		{
			if (report.getReportStatus().getCode() < ReportStatus.GDIP_In_Process.getCode()) {
				throw new IllegalArgumentException();
			}
			
			if (report.getReportLocation().equals(ReportLocation.EAW) || report.getReportLocation().equals(ReportLocation.NIR)) {
				//上传到EAW中或者nir
				String path = GlobalConfig.getInstance().getFSDir() + solution.getSolutionLodgeXmlPath();
				try {
					String xmlContent = FileUtils.readFileToString(new File(path),"utf-8");
					report.setGdipLodgeDate(null);
					reportServiceMgr.updateReportStatus(report, ReportStatus.GDIP_Lodging);
					ResponseObject ro = null;
					try {
						ro = ClgRestClientUtil.post(GlobalConfig.getInstance().getEAWGdipLodgementAddress()	,
								solution.getReport().getUser().getAuthUsername() + ":" + solution.getReport().getUser().getAuthPassword(),
								GlobalConfig.getInstance().getEAWGdipCert(),GlobalConfig.getInstance().getEAWGdipCertPassword(),xmlContent);
					} catch (Exception e) {
						e.printStackTrace();
						report.setGdipLodgeDate(null);
						reportServiceMgr.updateReportStatus(report, ReportStatus.GDIP_In_Process);
					}
					System.out.println("***************************************** " + ro.getResponseStatus().getCode() + " -- rrn " + report.getRrn() + " -- oarrn " + report.getOaRrn());
					
					if (ro.getResponseStatus().equals(ResponseStatus.Success)) {
						report.setGdipLodgeDate(new Date());
						if(originalStatus.equals(ReportStatus.GDIP_Lodged_By_Assessor))
						{
							reportServiceMgr.updateReportStatus(report, ReportStatus.GDIP_reLodged_By_Assessor);
						}
						else if(originalStatus.equals(ReportStatus.GDIP_Lodged_By_Provider))
						{
							reportServiceMgr.updateReportStatus(report, ReportStatus.GDIP_reLodged_By_Provider);
						}
						else
						{
							if(report.getUser().getUserType().equals(UserType.GDA))
							{
								reportServiceMgr.updateReportStatus(report, ReportStatus.GDIP_Lodged_By_Assessor);
							}
							else
							{
								reportServiceMgr.updateReportStatus(report, ReportStatus.GDIP_Lodged_By_Provider);
							}
						}
						
						System.out.println("******************************** updateReportStatus finish " + DateUtil.date2String(new Date(), "yyyy-MM-dd hh:mm:ss"));
					}else{
						report.setGdipLodgeDate(null);
						reportServiceMgr.updateReportStatus(report, originalStatus);
					}
					return ro;
				} catch (IOException e) {
					e.printStackTrace();
					throw new IllegalArgumentException();
				}
			}else {
				String path = GlobalConfig.getInstance().getFSDir() + solution.getSolutionLodgeXmlPath();
				String xmlContent;
				try {
					xmlContent = FileUtils.readFileToString(new File(path), "utf-8");
				} catch (IOException e) {
					e.printStackTrace();
					throw new IllegalArgumentException();
				}
				report.setLodgeDate(null);
				reportServiceMgr.updateReportStatus(report, ReportStatus.GDIP_Lodging);
				SCTWSResponse sctWSResponse = null;
				try {
					sctWSResponse = SCTWebserviceUtil.sctLodgeOAxml(GlobalConfig.getInstance().getLodgeGDIPXmlWebserviceAddressOfSCT(report.getOaRrn()), 
							GlobalConfig.getInstance().getCertOfSCT(), 
							GlobalConfig.getInstance().getCertPasswordOfSCT(), 
							report.getUser().getSctAuthorizationUsername(), 
							report.getUser().getSctAuthorizationPassword(), 
							report.getUser().getSctAssessorOrganisationid(), 
							report.getUser().getSctAdviserId(), xmlContent);
				} catch (Exception e) {
					e.printStackTrace();
					report.setGdipLodgeDate(null);
					reportServiceMgr.updateReportStatus(report, ReportStatus.GDIP_In_Process);
				}
				
				if (sctWSResponse.isResponseStatus()) {
					//成功 --> 获取pdf
					String pdfFilePath = DateUtil.date2String(new Date(), "/yyyy/MM/dd/");
					if (!new File(GlobalConfig.getInstance().getFSDir() + pdfFilePath).exists()) {
						new File(GlobalConfig.getInstance().getFSDir() + pdfFilePath).mkdirs();
					}
					
					pdfFilePath = pdfFilePath + UUID.randomUUID().toString() + ".pdf";
					SCTWebserviceUtil.getOAPdf(GlobalConfig.getInstance().getGetGdipPDFWebserviceAddressOfSCT(report.getGdipRrn()), GlobalConfig.getInstance().getCertOfSCT(), GlobalConfig.getInstance().getCertPasswordOfSCT(), 
							report.getUser().getSctAuthorizationUsername(), report.getUser().getSctAuthorizationPassword(), report.getUser().getSctAssessorOrganisationid(), report.getUser().getSctAdviserId(), 
							GlobalConfig.getInstance().getFSDir() + pdfFilePath);
					report.setGdipLodgeDate(new Date());
					reportServiceMgr.updateReportStatus(report, ReportStatus.GDIP_Lodged_By_Assessor);
					gdsapEvalSolutionMapper.updateSolutionPdfPathById(solution.getId(), pdfFilePath, new Date());
					
					ResponseObject responseObject = new ResponseObject(ResponseStatus.Success);
					return responseObject;
				}else{
					report.setGdipLodgeDate(null);
					reportServiceMgr.updateReportStatus(report, ReportStatus.GDIP_In_Process);
					ResponseObject responseObject = new ResponseObject(ResponseStatus.Fail_SCT);
					ResponseMessage message = new ResponseMessage();
					String messageContent = null;
					if (sctWSResponse.getResponseCode() == 401) {
						messageContent = sctWSResponse.getResponseMessage();
					}else{
						messageContent = sctWSResponse.getErrorMessage();
					}
					message.setMessage(messageContent);
					responseObject.setResponseMessage(message);
					return responseObject;
				}
			}
		
		}
		
		return null;
	}

	@Autowired
	private GdsapEvalSolutionMapper gdsapEvalSolutionMapper;
	
	
	public void getPdf(Solution solution){
		Report report = solution.getReport();
		//成功 --> 获取pdf
		String pdfFilePath = DateUtil.date2String(new Date(), "/yyyy/MM/dd/");
		if (!new File(GlobalConfig.getInstance().getFSDir() + pdfFilePath).exists()) {
			new File(GlobalConfig.getInstance().getFSDir() + pdfFilePath).mkdirs();
		}
		
		pdfFilePath = pdfFilePath + UUID.randomUUID().toString() + ".pdf";
		SCTWebserviceUtil.getOAPdf(GlobalConfig.getInstance().getGetOAPDFWebserviceAddressOfSCT(report.getOaRrn()), GlobalConfig.getInstance().getCertOfSCT(), GlobalConfig.getInstance().getCertPasswordOfSCT(), 
				report.getUser().getSctAuthorizationUsername(), report.getUser().getSctAuthorizationPassword(), report.getUser().getSctAssessorOrganisationid(), report.getUser().getSctAdviserId(), 
				GlobalConfig.getInstance().getFSDir() + pdfFilePath);
		
		gdsapEvalSolutionMapper.updateSolutionPdfPathById(solution.getId(), pdfFilePath, new Date());
	}
	
}


class PerfuelFuelCodeComparator implements Comparator
{
	@Override
	public int compare(Object o1, Object o2)
	{
		PerFuelCalResult calResult1 = (PerFuelCalResult) o1;
		PerFuelCalResult calResult2 = (PerFuelCalResult) o2;
		return calResult1.getFuelCode().compareTo(calResult2.getFuelCode());
	}

}