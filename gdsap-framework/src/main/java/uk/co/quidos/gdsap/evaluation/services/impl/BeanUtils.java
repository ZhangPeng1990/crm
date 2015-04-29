package uk.co.quidos.gdsap.evaluation.services.impl;


import net.sf.cglib.beans.BeanCopier;

import org.springframework.util.Assert;

import uk.co.quidos.gdsap.evaluation.AppCooking;
import uk.co.quidos.gdsap.evaluation.BillDataComm;
import uk.co.quidos.gdsap.evaluation.BillDataEle;
import uk.co.quidos.gdsap.evaluation.BillDataLPG;
import uk.co.quidos.gdsap.evaluation.BillDataMg;
import uk.co.quidos.gdsap.evaluation.CalResult;
import uk.co.quidos.gdsap.evaluation.Company;
import uk.co.quidos.gdsap.evaluation.EnergyUse;
import uk.co.quidos.gdsap.evaluation.EpcEADetail;
import uk.co.quidos.gdsap.evaluation.EpcImprovementCalResult;
import uk.co.quidos.gdsap.evaluation.FuelCalResult;
import uk.co.quidos.gdsap.evaluation.HeatProportion;
import uk.co.quidos.gdsap.evaluation.HeatingPattern;
import uk.co.quidos.gdsap.evaluation.HeatingSystem;
import uk.co.quidos.gdsap.evaluation.Improvement;
import uk.co.quidos.gdsap.evaluation.Occupants;
import uk.co.quidos.gdsap.evaluation.OtherFuel;
import uk.co.quidos.gdsap.evaluation.Report;
import uk.co.quidos.gdsap.evaluation.ReportEpcImprovementCalResult;
import uk.co.quidos.gdsap.evaluation.Solution;
import uk.co.quidos.gdsap.evaluation.SolutionIssue;
import uk.co.quidos.gdsap.evaluation.StandardRecommendationCalResult;
import uk.co.quidos.gdsap.evaluation.enums.Language;
import uk.co.quidos.gdsap.evaluation.enums.OffPeakType;
import uk.co.quidos.gdsap.evaluation.enums.PropertyType;
import uk.co.quidos.gdsap.evaluation.enums.ReportFileStatus;
import uk.co.quidos.gdsap.evaluation.enums.ReportLocation;
import uk.co.quidos.gdsap.evaluation.enums.ReportStatus;
import uk.co.quidos.gdsap.evaluation.enums.ReportType;
import uk.co.quidos.gdsap.evaluation.enums.RoomType;
import uk.co.quidos.gdsap.evaluation.enums.SolutionType;
import uk.co.quidos.gdsap.evaluation.enums.TransactionType;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalAppCooking;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalBillDataComm;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalBillDataEle;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalBillDataLpg;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalBillDataMg;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalCompany;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalEnergyUse;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalEpcEaDetail;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalEpcImprovementResult;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalHeatProportion;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalHeatingPattern;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalHeatingSystem;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalImprovement;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalOccupants;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalOtherFuel;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalReport;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalReportEpcImprovementResult;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalSolution;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalSolutionCalResult;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalSolutionFuelCalResult;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalSolutionIssue;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalSolutionRecommendationRel;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapGdpuserReport;
import uk.co.quidos.gdsap.evaluation.services.DictServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.ReportServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.SolutionServiceMgr;
import uk.co.quidos.gdsap.framework.enums.EpcVersion;
import uk.co.quidos.gdsap.framework.enums.UploadWay;
import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.sys.business.BusinessFactory;
import uk.co.quidos.gdsap.framework.sys.lang.enums.EnumUtils;
import uk.co.quidos.gdsap.framework.user.User;

/**
 * 业务对象以及数据对象转换
 * 1. 完成持久化中基本类型与Enum转换等操作，类似于quidos-framework-1.0 中接口赋值
 * 2. 
 * @author peng.shi
 */
public class BeanUtils
{	

	public static Company toCompany(GdsapEvalCompany gdsapEvalCompany)
	{
		Company company = new Company();
		company.setAddress1(gdsapEvalCompany.getAddress1());
		company.setAddress2(gdsapEvalCompany.getAddress2());
		company.setAddress3(gdsapEvalCompany.getAddress3());
		company.setCompanyId(gdsapEvalCompany.getCompanyId());
		company.setEmail(gdsapEvalCompany.getEmail());
		company.setFax(gdsapEvalCompany.getFax());
		company.setInsertTime(gdsapEvalCompany.getInserTtime());
		company.setName(gdsapEvalCompany.getName());
		company.setPostcode(gdsapEvalCompany.getPostcode());
		company.setPosttown(gdsapEvalCompany.getPosttown());
		company.setTel(gdsapEvalCompany.getTel());
		company.setWebsite(gdsapEvalCompany.getWebsite());
		return company;
	}
	
	public static GdsapEvalCompany toGdsapEvalCompany(Company company)
	{
		GdsapEvalCompany gdsapEvalCompany = new GdsapEvalCompany();
		gdsapEvalCompany.setAddress1(company.getAddress1());
		gdsapEvalCompany.setAddress2(company.getAddress2());
		gdsapEvalCompany.setAddress3(company.getAddress3());
		gdsapEvalCompany.setCompanyId(company.getCompanyId());
		gdsapEvalCompany.setEmail(company.getEmail());
		gdsapEvalCompany.setFax(company.getFax());
		gdsapEvalCompany.setInserTtime(company.getInsertTime());
		gdsapEvalCompany.setName(company.getName());
		gdsapEvalCompany.setPostcode(company.getPostcode());
		gdsapEvalCompany.setPosttown(company.getPosttown());
		gdsapEvalCompany.setTel(company.getTel());
		gdsapEvalCompany.setWebsite(company.getWebsite());
		return gdsapEvalCompany;
	}
	
	public static StandardRecommendationCalResult toStandardRecommendationCalResult(GdsapEvalSolutionRecommendationRel gdsapEvalSolutionRecommendationRel)
	{
		StandardRecommendationCalResult standardRecommendationCalResult = new StandardRecommendationCalResult();
		standardRecommendationCalResult.setCheckedKey0(gdsapEvalSolutionRecommendationRel.getCheckedKey0());
		standardRecommendationCalResult.setCheckedKey1(gdsapEvalSolutionRecommendationRel.getCheckedKey1());
		standardRecommendationCalResult.setCheckedKey2(gdsapEvalSolutionRecommendationRel.getCheckedKey2());
		standardRecommendationCalResult.setCheckedKey3(gdsapEvalSolutionRecommendationRel.getCheckedKey3());
		standardRecommendationCalResult.setCheckedKey4(gdsapEvalSolutionRecommendationRel.getCheckedKey4());
		standardRecommendationCalResult.setCheckedKey5(gdsapEvalSolutionRecommendationRel.getCheckedKey5());
		standardRecommendationCalResult.setCheckedValue0(gdsapEvalSolutionRecommendationRel.getCheckedValue0());
		standardRecommendationCalResult.setCheckedValue1(gdsapEvalSolutionRecommendationRel.getCheckedValue1());
		standardRecommendationCalResult.setCheckedValue2(gdsapEvalSolutionRecommendationRel.getCheckedValue2());
		standardRecommendationCalResult.setCheckedValue3(gdsapEvalSolutionRecommendationRel.getCheckedValue3());
		standardRecommendationCalResult.setCheckedValue4(gdsapEvalSolutionRecommendationRel.getCheckedValue4());
		standardRecommendationCalResult.setCheckedValue5(gdsapEvalSolutionRecommendationRel.getCheckedValue5());
		standardRecommendationCalResult.setEstimatedAnnualSavings(gdsapEvalSolutionRecommendationRel.getEstimatedAnnualSavings());
		standardRecommendationCalResult.setEstimatedCostsEnd(gdsapEvalSolutionRecommendationRel.getEstimatedCostsEnd());
		standardRecommendationCalResult.setEstimatedCostsStart(gdsapEvalSolutionRecommendationRel.getEstimatedCostsStart());
		standardRecommendationCalResult.setGreenDealCategory(gdsapEvalSolutionRecommendationRel.getGreenDealCategory());
		standardRecommendationCalResult.setInUseFactor(gdsapEvalSolutionRecommendationRel.getInUseFactor());
		standardRecommendationCalResult.setInUseFactor2(gdsapEvalSolutionRecommendationRel.getInUseFactor2());
		standardRecommendationCalResult.setInputKey0(gdsapEvalSolutionRecommendationRel.getInputKey0());
		standardRecommendationCalResult.setInputKey1(gdsapEvalSolutionRecommendationRel.getInputKey1());
		standardRecommendationCalResult.setInputKey2(gdsapEvalSolutionRecommendationRel.getInputKey2());
		standardRecommendationCalResult.setInputKey3(gdsapEvalSolutionRecommendationRel.getInputKey3());
		standardRecommendationCalResult.setInputKey4(gdsapEvalSolutionRecommendationRel.getInputKey4());
		standardRecommendationCalResult.setInputKey5(gdsapEvalSolutionRecommendationRel.getInputKey5());
		standardRecommendationCalResult.setInputValue0(gdsapEvalSolutionRecommendationRel.getInputValue0());
		standardRecommendationCalResult.setInputValue1(gdsapEvalSolutionRecommendationRel.getInputValue1());
		standardRecommendationCalResult.setInputValue2(gdsapEvalSolutionRecommendationRel.getInputValue2());
		standardRecommendationCalResult.setInputValue3(gdsapEvalSolutionRecommendationRel.getInputValue3());
		standardRecommendationCalResult.setInputValue4(gdsapEvalSolutionRecommendationRel.getInputValue4());
		standardRecommendationCalResult.setInputValue5(gdsapEvalSolutionRecommendationRel.getInputValue5());
		standardRecommendationCalResult.setKey0(gdsapEvalSolutionRecommendationRel.getKey0());
		standardRecommendationCalResult.setKey1(gdsapEvalSolutionRecommendationRel.getKey1());
		standardRecommendationCalResult.setKey2(gdsapEvalSolutionRecommendationRel.getKey2());
		standardRecommendationCalResult.setKey3(gdsapEvalSolutionRecommendationRel.getKey3());
		standardRecommendationCalResult.setKey4(gdsapEvalSolutionRecommendationRel.getKey4());
		standardRecommendationCalResult.setKey5(gdsapEvalSolutionRecommendationRel.getKey5());
		standardRecommendationCalResult.setKey6(gdsapEvalSolutionRecommendationRel.getKey6());
		standardRecommendationCalResult.setKey7(gdsapEvalSolutionRecommendationRel.getKey7());
		standardRecommendationCalResult.setKey8(gdsapEvalSolutionRecommendationRel.getKey8());
		standardRecommendationCalResult.setKey9(gdsapEvalSolutionRecommendationRel.getKey9());
		standardRecommendationCalResult.setTypicalAnnualSavings(gdsapEvalSolutionRecommendationRel.getTypicalAnnualSavings());
		standardRecommendationCalResult.setValue0(gdsapEvalSolutionRecommendationRel.getValue0());
		standardRecommendationCalResult.setValue1(gdsapEvalSolutionRecommendationRel.getValue1());
		standardRecommendationCalResult.setValue2(gdsapEvalSolutionRecommendationRel.getValue2());
		standardRecommendationCalResult.setValue3(gdsapEvalSolutionRecommendationRel.getValue3());
		standardRecommendationCalResult.setValue4(gdsapEvalSolutionRecommendationRel.getValue4());
		standardRecommendationCalResult.setValue5(gdsapEvalSolutionRecommendationRel.getValue5());
		standardRecommendationCalResult.setValue6(gdsapEvalSolutionRecommendationRel.getValue6());
		standardRecommendationCalResult.setValue7(gdsapEvalSolutionRecommendationRel.getValue7());
		standardRecommendationCalResult.setValue8(gdsapEvalSolutionRecommendationRel.getValue8());
		standardRecommendationCalResult.setValue9(gdsapEvalSolutionRecommendationRel.getValue9());
		standardRecommendationCalResult.setWallConstruction1(gdsapEvalSolutionRecommendationRel.getWallConstruction1());
		standardRecommendationCalResult.setWallConstruction2(gdsapEvalSolutionRecommendationRel.getWallConstruction2());
		standardRecommendationCalResult.setWallConstruction3(gdsapEvalSolutionRecommendationRel.getWallConstruction3());
		standardRecommendationCalResult.setWallConstruction4(gdsapEvalSolutionRecommendationRel.getWallConstruction4());
		
		return standardRecommendationCalResult;
	}
	
	public static GdsapEvalSolutionRecommendationRel toGdsapEvalSolutionRecommendationRel(StandardRecommendationCalResult standardRecommendationCalResult)
	{
		GdsapEvalSolutionRecommendationRel gdsapEvalSolutionRecommendationRel = new GdsapEvalSolutionRecommendationRel();
		gdsapEvalSolutionRecommendationRel.setCheckedKey0(standardRecommendationCalResult.getCheckedKey0());
		gdsapEvalSolutionRecommendationRel.setCheckedKey1(standardRecommendationCalResult.getCheckedKey1());
		gdsapEvalSolutionRecommendationRel.setCheckedKey2(standardRecommendationCalResult.getCheckedKey2());
		gdsapEvalSolutionRecommendationRel.setCheckedKey3(standardRecommendationCalResult.getCheckedKey3());
		gdsapEvalSolutionRecommendationRel.setCheckedKey4(standardRecommendationCalResult.getCheckedKey4());
		gdsapEvalSolutionRecommendationRel.setCheckedKey5(standardRecommendationCalResult.getCheckedKey5());
		gdsapEvalSolutionRecommendationRel.setCheckedValue0(standardRecommendationCalResult.getCheckedValue0());
		gdsapEvalSolutionRecommendationRel.setCheckedValue1(standardRecommendationCalResult.getCheckedValue1());
		gdsapEvalSolutionRecommendationRel.setCheckedValue2(standardRecommendationCalResult.getCheckedValue2());
		gdsapEvalSolutionRecommendationRel.setCheckedValue3(standardRecommendationCalResult.getCheckedValue3());
		gdsapEvalSolutionRecommendationRel.setCheckedValue4(standardRecommendationCalResult.getCheckedValue4());
		gdsapEvalSolutionRecommendationRel.setCheckedValue5(standardRecommendationCalResult.getCheckedValue5());
		gdsapEvalSolutionRecommendationRel.setEstimatedAnnualSavings(standardRecommendationCalResult.getEstimatedAnnualSavings());
		gdsapEvalSolutionRecommendationRel.setEstimatedCostsEnd(standardRecommendationCalResult.getEstimatedCostsEnd());
		gdsapEvalSolutionRecommendationRel.setEstimatedCostsStart(standardRecommendationCalResult.getEstimatedCostsStart());
		gdsapEvalSolutionRecommendationRel.setGreenDealCategory(standardRecommendationCalResult.getGreenDealCategory());
		gdsapEvalSolutionRecommendationRel.setInUseFactor(standardRecommendationCalResult.getInUseFactor());
		gdsapEvalSolutionRecommendationRel.setInUseFactor2(standardRecommendationCalResult.getInUseFactor2());
		gdsapEvalSolutionRecommendationRel.setInputKey0(standardRecommendationCalResult.getInputKey0());
		gdsapEvalSolutionRecommendationRel.setInputKey1(standardRecommendationCalResult.getInputKey1());
		gdsapEvalSolutionRecommendationRel.setInputKey2(standardRecommendationCalResult.getInputKey2());
		gdsapEvalSolutionRecommendationRel.setInputKey3(standardRecommendationCalResult.getInputKey3());
		gdsapEvalSolutionRecommendationRel.setInputKey4(standardRecommendationCalResult.getInputKey4());
		gdsapEvalSolutionRecommendationRel.setInputKey5(standardRecommendationCalResult.getInputKey5());
		gdsapEvalSolutionRecommendationRel.setInputValue0(standardRecommendationCalResult.getInputValue0());
		gdsapEvalSolutionRecommendationRel.setInputValue1(standardRecommendationCalResult.getInputValue1());
		gdsapEvalSolutionRecommendationRel.setInputValue2(standardRecommendationCalResult.getInputValue2());
		gdsapEvalSolutionRecommendationRel.setInputValue3(standardRecommendationCalResult.getInputValue3());
		gdsapEvalSolutionRecommendationRel.setInputValue4(standardRecommendationCalResult.getInputValue4());
		gdsapEvalSolutionRecommendationRel.setInputValue5(standardRecommendationCalResult.getInputValue5());
		gdsapEvalSolutionRecommendationRel.setKey0(standardRecommendationCalResult.getKey0());
		gdsapEvalSolutionRecommendationRel.setKey1(standardRecommendationCalResult.getKey1());
		gdsapEvalSolutionRecommendationRel.setKey2(standardRecommendationCalResult.getKey2());
		gdsapEvalSolutionRecommendationRel.setKey3(standardRecommendationCalResult.getKey3());
		gdsapEvalSolutionRecommendationRel.setKey4(standardRecommendationCalResult.getKey4());
		gdsapEvalSolutionRecommendationRel.setKey5(standardRecommendationCalResult.getKey5());
		gdsapEvalSolutionRecommendationRel.setKey6(standardRecommendationCalResult.getKey6());
		gdsapEvalSolutionRecommendationRel.setKey7(standardRecommendationCalResult.getKey7());
		gdsapEvalSolutionRecommendationRel.setKey8(standardRecommendationCalResult.getKey8());
		gdsapEvalSolutionRecommendationRel.setKey9(standardRecommendationCalResult.getKey9());
		gdsapEvalSolutionRecommendationRel.setTypicalAnnualSavings(standardRecommendationCalResult.getTypicalAnnualSavings());
		gdsapEvalSolutionRecommendationRel.setValue0(standardRecommendationCalResult.getValue0());
		gdsapEvalSolutionRecommendationRel.setValue1(standardRecommendationCalResult.getValue1());
		gdsapEvalSolutionRecommendationRel.setValue2(standardRecommendationCalResult.getValue2());
		gdsapEvalSolutionRecommendationRel.setValue3(standardRecommendationCalResult.getValue3());
		gdsapEvalSolutionRecommendationRel.setValue4(standardRecommendationCalResult.getValue4());
		gdsapEvalSolutionRecommendationRel.setValue5(standardRecommendationCalResult.getValue5());
		gdsapEvalSolutionRecommendationRel.setValue6(standardRecommendationCalResult.getValue6());
		gdsapEvalSolutionRecommendationRel.setValue7(standardRecommendationCalResult.getValue7());
		gdsapEvalSolutionRecommendationRel.setValue8(standardRecommendationCalResult.getValue8());
		gdsapEvalSolutionRecommendationRel.setValue9(standardRecommendationCalResult.getValue9());
		gdsapEvalSolutionRecommendationRel.setWallConstruction1(standardRecommendationCalResult.getWallConstruction1());
		gdsapEvalSolutionRecommendationRel.setWallConstruction2(standardRecommendationCalResult.getWallConstruction2());
		gdsapEvalSolutionRecommendationRel.setWallConstruction3(standardRecommendationCalResult.getWallConstruction3());
		gdsapEvalSolutionRecommendationRel.setWallConstruction4(standardRecommendationCalResult.getWallConstruction4());
		return gdsapEvalSolutionRecommendationRel;
	}
	
	public static GdsapGdpuserReport toGdsapGdpuserReport(User user,Report report)
	{
		GdsapGdpuserReport gdsapGdpuserReport = new GdsapGdpuserReport(user.getId(), report.getId());
		return gdsapGdpuserReport;
	}
	
	public static EpcEADetail gdsapEvalEpcEaDetail2EpcEADetail(GdsapEvalEpcEaDetail gdsapEvalEpcEaDetail){
		EpcEADetail epcEADetail = new EpcEADetail();
		epcEADetail.setCertificateNumber(gdsapEvalEpcEaDetail.getCertificateNumber());
		epcEADetail.setCompanyName(gdsapEvalEpcEaDetail.getCompanyName());
		epcEADetail.setContactAddress1(gdsapEvalEpcEaDetail.getContactAddress1());
		epcEADetail.setContactAddress2(gdsapEvalEpcEaDetail.getContactAddress2());
		epcEADetail.setContactAddress3(gdsapEvalEpcEaDetail.getContactAddress3());
		epcEADetail.setEffectiveDate(gdsapEvalEpcEaDetail.getEffectiveDate());
		epcEADetail.setEmail(gdsapEvalEpcEaDetail.getEmail());
		epcEADetail.setExpiryDate(gdsapEvalEpcEaDetail.getExpiryDate());
		epcEADetail.setFax(gdsapEvalEpcEaDetail.getFax());
		epcEADetail.setFullName(gdsapEvalEpcEaDetail.getFullName());
		epcEADetail.setInsurer(gdsapEvalEpcEaDetail.getInsurer());
		epcEADetail.setNotifyLodgement(gdsapEvalEpcEaDetail.getNotifyLodgement());
		epcEADetail.setPiLimit(gdsapEvalEpcEaDetail.getPiLimit());
		epcEADetail.setPolicyNo(gdsapEvalEpcEaDetail.getPolicyNo());
		epcEADetail.setPostcode(gdsapEvalEpcEaDetail.getPostcode());
		epcEADetail.setPosttown(gdsapEvalEpcEaDetail.getPosttown());
		epcEADetail.setSchemeName(gdsapEvalEpcEaDetail.getSchemeName());
		epcEADetail.setSchemeWebSite(gdsapEvalEpcEaDetail.getSchemeWebSite());
		epcEADetail.setTelephone(gdsapEvalEpcEaDetail.getTelephone());
		epcEADetail.setWebsite(gdsapEvalEpcEaDetail.getWebsite());
		
		return epcEADetail;
	}
	
	public static GdsapEvalEpcEaDetail epcEADetail2GdsapEvalEpcEaDetail(EpcEADetail epcEADetail){
		GdsapEvalEpcEaDetail gdsapEvalEpcEaDetail = new GdsapEvalEpcEaDetail();
		gdsapEvalEpcEaDetail.setCertificateNumber(epcEADetail.getCertificateNumber());
		gdsapEvalEpcEaDetail.setCompanyName(epcEADetail.getCompanyName());
		gdsapEvalEpcEaDetail.setContactAddress1(epcEADetail.getContactAddress1());
		gdsapEvalEpcEaDetail.setContactAddress2(epcEADetail.getContactAddress2());
		gdsapEvalEpcEaDetail.setContactAddress3(epcEADetail.getContactAddress3());
		gdsapEvalEpcEaDetail.setEffectiveDate(epcEADetail.getEffectiveDate());
		gdsapEvalEpcEaDetail.setEmail(epcEADetail.getEmail());
		gdsapEvalEpcEaDetail.setExpiryDate(epcEADetail.getExpiryDate());
		gdsapEvalEpcEaDetail.setFax(epcEADetail.getFax());
		gdsapEvalEpcEaDetail.setFullName(epcEADetail.getFullName());
		gdsapEvalEpcEaDetail.setInsurer(epcEADetail.getInsurer());
		gdsapEvalEpcEaDetail.setNotifyLodgement(epcEADetail.getNotifyLodgement());
		gdsapEvalEpcEaDetail.setPiLimit(epcEADetail.getPiLimit());
		gdsapEvalEpcEaDetail.setPolicyNo(epcEADetail.getPolicyNo());
		gdsapEvalEpcEaDetail.setPostcode(epcEADetail.getPostcode());
		gdsapEvalEpcEaDetail.setPosttown(epcEADetail.getPosttown());
		gdsapEvalEpcEaDetail.setReportId(epcEADetail.getReport() != null ? epcEADetail.getReport().getId() : null);
		gdsapEvalEpcEaDetail.setSchemeName(epcEADetail.getSchemeName());
		gdsapEvalEpcEaDetail.setSchemeWebSite(epcEADetail.getSchemeWebSite());
		gdsapEvalEpcEaDetail.setTelephone(epcEADetail.getTelephone());
		gdsapEvalEpcEaDetail.setWebsite(epcEADetail.getWebsite());
		return gdsapEvalEpcEaDetail;
	}
	
	/**
	 * GdsapAltType -> LogType
	 * @param model
	 * @return
	 */
	public static GdsapEvalReport user2gdsapUsrUser(Report report)
	{
		if(report != null){
			GdsapEvalReport gdsapEvalReport = new GdsapEvalReport();
			gdsapEvalReport.setId(report.getId());
			gdsapEvalReport.setRrn(report.getRrn());
			gdsapEvalReport.setInspectionDate(report.getInspectionDate() != null ? report.getInspectionDate() : null);
			gdsapEvalReport.setReportType(report.getReportType() != null ? report.getReportType().getCode() : null);
			gdsapEvalReport.setCompletionDate(report.getCompletionDate() != null ? report.getCompletionDate() : null);
			gdsapEvalReport.setRegistrationDate(report.getRegistrationDate() != null ? report.getRegistrationDate() : null);
			gdsapEvalReport.setReportXmlStatus(report.getReportXmlStatus() != null ? report.getReportXmlStatus().getCode() : null);
			gdsapEvalReport.setLanguageCode(report.getLanguageCode() != null ? report.getLanguageCode().getCode() : null);
			gdsapEvalReport.setTransactionType(report.getTransactionType() != null ? report.getTransactionType().getCode() : null);
			gdsapEvalReport.setEaFullName(report.getEaFullName());
			gdsapEvalReport.setEaCertificateName(report.getEaCertificateName());
			gdsapEvalReport.setAddress1(report.getAddress1());
			gdsapEvalReport.setAddress2(report.getAddress2());
			gdsapEvalReport.setAddress3(report.getAddress3());
			gdsapEvalReport.setPostcode(report.getPostcode());
			gdsapEvalReport.setPosttown(report.getPosttown());
			gdsapEvalReport.setUprn(report.getUprn());
			gdsapEvalReport.setInsertTime(report.getInsertTime());
			gdsapEvalReport.setUpdateTime(report.getUpdateTime());
			gdsapEvalReport.setPropertyType(report.getPropertyType() != null ? report.getPropertyType().getCode() : null);
			gdsapEvalReport.setRdsapMhsFuel(report.getRdsapMhsFuel() != null ? report.getRdsapMhsFuel() : null);
			gdsapEvalReport.setRdsapSmhsFuel(report.getRdsapSmhsFuel() != null ? report.getRdsapSmhsFuel() : null);
			gdsapEvalReport.setRdsapShsFuel(report.getRdsapShsFuel() != null ? report.getRdsapShsFuel() : null);
			gdsapEvalReport.setHabitableRoomCount(report.getHabitableRoomCount());
			gdsapEvalReport.setRdsapWhsFuel(report.getRdsapWhsFuel() != null ? report.getRdsapWhsFuel() : null);
			gdsapEvalReport.setReportXmlFile(report.getReportXmlFile().toString());
			gdsapEvalReport.setRdsapMainGasAvailable(report.getRdsapMainGasAvailable().getCode());
			gdsapEvalReport.setUserId(report.getUser() != null ? report.getUser().getId() : null);
			gdsapEvalReport.setOaRrn(report.getOaRrn());
			gdsapEvalReport.setGdipRrn(report.getGdipRrn());
			gdsapEvalReport.setReportLocation(report.getReportLocation().getCode());
			gdsapEvalReport.setUnoccupiedPropertyable(report.getUnoccupiedPropertyable() != null ? report.getUnoccupiedPropertyable().getCode() : null);
			gdsapEvalReport.setLodgeDate(report.getLodgeDate());
			gdsapEvalReport.setGdipLodgeDate(report.getGdipLodgeDate());
			gdsapEvalReport.setReportStatus(report.getReportStatus().getCode());
			gdsapEvalReport.setUploadWay(report.getUploadWay() != null ? report.getUploadWay().getCode() : null);
			gdsapEvalReport.setEpcVersion(report.getEpcVersion() != null ? report.getEpcVersion().getCode() : null);
			
			if (report.getRelatedPartyDisclosure() != null)
			{
				gdsapEvalReport.setRelatedPartyDisclosure(report.getRelatedPartyDisclosure().getId());
			}
			gdsapEvalReport.setCompanyId(report.getCompany() != null ? report.getCompany().getCompanyId() : null);
			gdsapEvalReport.setCompanyJobRef(report.getCompanyJobRef());
			gdsapEvalReport.setGdipLigXmlFile(report.getLigXmlFile());
			gdsapEvalReport.setLodgedGdipLigXmlFile(report.getLodgedGdipLigXmlFile());
			gdsapEvalReport.setLodgedGdipRrn(report.getLodgedGdipRrn());
			gdsapEvalReport.setRequestXmlFile(report.getRequestXmlFile());
			return gdsapEvalReport;
		}
		return null;
	}
	
	
	/**
	 * GdsapAltType -> LogType
	 * @param model
	 * @return
	 */
	public static Report gdsapUsrUser2user(GdsapEvalReport gdsapEvalReport)
	{
		if(gdsapEvalReport != null){
			Report report = new Report();
			report.setId(gdsapEvalReport.getId());
			report.setRrn(gdsapEvalReport.getRrn());
			report.setInspectionDate(gdsapEvalReport.getInspectionDate());
			report.setReportType((ReportType)EnumUtils.getByCode(gdsapEvalReport.getReportType(),ReportType.class));
			report.setCompletionDate(gdsapEvalReport.getCompletionDate());
			report.setRegistrationDate(gdsapEvalReport.getRegistrationDate());
			report.setReportXmlStatus((ReportFileStatus)EnumUtils.getByCode(gdsapEvalReport.getReportXmlStatus(), ReportFileStatus.class));
			report.setLanguageCode(gdsapEvalReport.getLanguageCode() != null ? (Language)EnumUtils.getByCode(gdsapEvalReport.getLanguageCode(), Language.class) : null);
			report.setTransactionType((TransactionType)EnumUtils.getByCode(gdsapEvalReport.getTransactionType(), TransactionType.class));
			report.setEaFullName(gdsapEvalReport.getEaFullName());
			report.setEaCertificateName(gdsapEvalReport.getEaCertificateName());
			report.setAddress1(gdsapEvalReport.getAddress1());
			report.setAddress2(gdsapEvalReport.getAddress2());
			report.setAddress3(gdsapEvalReport.getAddress3());
			report.setPostcode(gdsapEvalReport.getPostcode());
			report.setPosttown(gdsapEvalReport.getPosttown());
			report.setUprn(gdsapEvalReport.getUprn());
			report.setInsertTime(gdsapEvalReport.getInsertTime());
			report.setUpdateTime(gdsapEvalReport.getUpdateTime());
			report.setPropertyType((PropertyType)EnumUtils.getByCode(gdsapEvalReport.getPropertyType(), PropertyType.class));
			report.setRdsapMhsFuel(gdsapEvalReport.getRdsapMhsFuel());
			report.setRdsapSmhsFuel(gdsapEvalReport.getRdsapSmhsFuel());
			report.setRdsapShsFuel(gdsapEvalReport.getRdsapShsFuel());
			report.setRdsapWhsFuel(gdsapEvalReport.getRdsapWhsFuel());
			report.setHabitableRoomCount(gdsapEvalReport.getHabitableRoomCount());
			report.setReportXmlFile(gdsapEvalReport.getReportXmlFile());
			report.setOldReportCalXmlFile(gdsapEvalReport.getOldReportCalXmlFile());
			report.setRdsapMainGasAvailable((YesNo)EnumUtils.getByCode(gdsapEvalReport.getRdsapMainGasAvailable(), YesNo.class));
			report.setReportLocation((ReportLocation)EnumUtils.getByCode(gdsapEvalReport.getReportLocation(), ReportLocation.class));
			report.setOaRrn(gdsapEvalReport.getOaRrn());
			report.setGdipRrn(gdsapEvalReport.getGdipRrn());
			report.setUnoccupiedPropertyable((YesNo)EnumUtils.getByCode(gdsapEvalReport.getUnoccupiedPropertyable(), YesNo.class));
			report.setLodgeDate(gdsapEvalReport.getLodgeDate());
			report.setGdipLodgeDate(gdsapEvalReport.getGdipLodgeDate());
			report.setReportStatus((ReportStatus)EnumUtils.getByCode(gdsapEvalReport.getReportStatus(), ReportStatus.class));
			report.setUploadWay((UploadWay)EnumUtils.getByCode(gdsapEvalReport.getUploadWay(),UploadWay.class));
			report.setEpcVersion((EpcVersion)EnumUtils.getByCode(gdsapEvalReport.getEpcVersion(), EpcVersion.class));
			report.setCompanyJobRef(gdsapEvalReport.getCompanyJobRef());
			report.setLigXmlFile(gdsapEvalReport.getGdipLigXmlFile());
			report.setLodgedGdipLigXmlFile(gdsapEvalReport.getLodgedGdipLigXmlFile());
			report.setLodgedGdipRrn(gdsapEvalReport.getLodgedGdipRrn());
			report.setRequestXmlFile(gdsapEvalReport.getRequestXmlFile());
			return report;
		}
		return null;
	}
	
	private static BeanCopier GdsapEvalImprovement = BeanCopier.create(Improvement.class,GdsapEvalImprovement.class,false);
	
	/**
	    private Report report; 
	 */
	public static GdsapEvalImprovement improvementToGdsImprovement(Improvement improvement){
		if(improvement != null){
			GdsapEvalImprovement gdsapEvalImprovement = new GdsapEvalImprovement();
			GdsapEvalImprovement.copy(improvement, gdsapEvalImprovement, null);
			
			gdsapEvalImprovement.setId(improvement.getId());
			gdsapEvalImprovement.setImprovementCategory(improvement.getImprovementCategory() != null ? improvement.getImprovementCategory().getCode() : null);
			gdsapEvalImprovement.setImprovementType(improvement.getImprovementType());
			gdsapEvalImprovement.setTypicalSaving(improvement.getTypicalSaving());
			gdsapEvalImprovement.setEnergyPerformanceRating(improvement.getEnergyPerformanceRating());
			gdsapEvalImprovement.setEnvironmentalImpactRating(improvement.getEnvironmentalImpactRating());
			//gdsapEvalImprovement.setRecommendationCode(improvement.getRecommendationCode().getId());
			gdsapEvalImprovement.setIndicativeCost(improvement.getIndicativeCost());
			gdsapEvalImprovement.setScope(improvement.getScope() != null ? improvement.getScope().getCode() : null);
			gdsapEvalImprovement.setGreenDealCategory(improvement.getGreenDealCategory() != null ? improvement.getGreenDealCategory().getSaveToDBcode() : null);
			gdsapEvalImprovement.setReportId(improvement.getReport().getId());
			return gdsapEvalImprovement;
		}
			return null;
	}
	
	
	private static BeanCopier energyUseCopier = BeanCopier.create(EnergyUse.class,GdsapEvalEnergyUse.class,false);
	
	public static GdsapEvalEnergyUse energyUsetoGdsapEvalEnergyUse(EnergyUse energyUse){
		if(energyUse != null){
			GdsapEvalEnergyUse gdsapEvalEnergyUse = new GdsapEvalEnergyUse();
			energyUseCopier.copy(energyUse, gdsapEvalEnergyUse, null);
			gdsapEvalEnergyUse.setReportId(energyUse.getId().getId());
			gdsapEvalEnergyUse.setEnergyRatingCurrent(energyUse.getEnergyRatingCurrent());
			gdsapEvalEnergyUse.setEnergyRatingPotential(energyUse.getEnergyRatingPotential());
			gdsapEvalEnergyUse.setEnergyRatingAverage(energyUse.getEnergyRatingAverage());
			gdsapEvalEnergyUse.setEnvironmentalImpactCurrent(energyUse.getEnvironmentalImpactCurrent());
			gdsapEvalEnergyUse.setEnvironmentalImpactPotential(energyUse.getEnvironmentalImpactPotential());
			gdsapEvalEnergyUse.setEnergyConsumptionCurrent(energyUse.getEnergyConsumptionCurrent());
			gdsapEvalEnergyUse.setEnergyConsumptionPotential(energyUse.getEnergyConsumptionPotential());
			gdsapEvalEnergyUse.setCo2EmissionsCurrent(energyUse.getCo2EmissionsCurrent());
			gdsapEvalEnergyUse.setCo2EmissionsCurrentPerFloorArea(energyUse.getCo2EmissionsCurrentPerFloorArea());
			gdsapEvalEnergyUse.setCo2EmissionsPotential(energyUse.getCo2EmissionsPotential());
			gdsapEvalEnergyUse.setLightingCostCurrent(energyUse.getLightingCostCurrent());
			gdsapEvalEnergyUse.setLightingCostPotential(energyUse.getLightingCostPotential());
			gdsapEvalEnergyUse.setHeatingCostCurrent(energyUse.getHeatingCostCurrent());
			gdsapEvalEnergyUse.setHeatingCostPotential(energyUse.getHeatingCostPotential());
			gdsapEvalEnergyUse.setHotWaterCostCurrent(energyUse.getHotWaterCostCurrent());
			gdsapEvalEnergyUse.setHotWaterCostPotential(energyUse.getHotWaterCostPotential());
			
			return gdsapEvalEnergyUse;
		}
			return null;
	}
	
    private static BeanCopier useCopier = BeanCopier.create(GdsapEvalEnergyUse.class,EnergyUse.class,false);
	
	public static EnergyUse GdsapEvalEnergyUsetoenergyUse(GdsapEvalEnergyUse gdsapEvalEnergyUse){
		if(gdsapEvalEnergyUse != null){
			EnergyUse energyUse = new EnergyUse();
			useCopier.copy(gdsapEvalEnergyUse,energyUse,null);
			
			energyUse.setEnergyRatingCurrent(gdsapEvalEnergyUse.getEnergyRatingCurrent());
			energyUse.setEnergyRatingPotential(gdsapEvalEnergyUse.getEnergyRatingPotential());
			energyUse.setEnergyRatingAverage(gdsapEvalEnergyUse.getEnergyRatingAverage());
			energyUse.setEnvironmentalImpactCurrent(gdsapEvalEnergyUse.getEnvironmentalImpactCurrent());
			energyUse.setEnvironmentalImpactPotential(gdsapEvalEnergyUse.getEnvironmentalImpactPotential());
			energyUse.setEnergyConsumptionCurrent(gdsapEvalEnergyUse.getEnergyConsumptionCurrent());
			energyUse.setEnergyConsumptionPotential(gdsapEvalEnergyUse.getEnergyConsumptionPotential());
			energyUse.setCo2EmissionsCurrent(gdsapEvalEnergyUse.getCo2EmissionsCurrent());
			energyUse.setCo2EmissionsCurrentPerFloorArea(gdsapEvalEnergyUse.getCo2EmissionsCurrentPerFloorArea());
			energyUse.setCo2EmissionsPotential(gdsapEvalEnergyUse.getCo2EmissionsPotential());
			energyUse.setLightingCostCurrent(gdsapEvalEnergyUse.getLightingCostCurrent());
			energyUse.setLightingCostPotential(gdsapEvalEnergyUse.getLightingCostPotential());
			energyUse.setHeatingCostCurrent(gdsapEvalEnergyUse.getHeatingCostCurrent());
			energyUse.setHeatingCostPotential(gdsapEvalEnergyUse.getHeatingCostPotential());
			energyUse.setHotWaterCostCurrent(gdsapEvalEnergyUse.getHotWaterCostCurrent());
			energyUse.setHotWaterCostPotential(gdsapEvalEnergyUse.getHotWaterCostPotential());
			
			return energyUse;
		}
			return null;
	}
	
	/**
	 * solution -> gdsap eval solution
	 * @param solution
	 * @return
	 */
	public static GdsapEvalSolution bo2do(Solution solution)
	{
		GdsapEvalSolution model = new GdsapEvalSolution();
		model.setDes(solution.getDes());
		model.setId(solution.getId());
		model.setInsertTime(solution.getInsertTime());
		model.setUpdateTime(solution.getUpdateTime());
		model.setReportId(solution.getReport().getId());
		model.setTitle(solution.getTitle());
		model.setSelected(solution.getSelected() != null ? solution.getSelected().getCode() : null);
		model.setSolutionType(solution.getSolutionType().getCode());
		model.setSolutionLodgeXmlPath(solution.getSolutionLodgeXmlPath());
		model.setSolutionPartLodgeXmlPath(solution.getSolutionPartLodgeXmlPath());
		model.setSolutionPdfPath(solution.getSolutionPdfPath());
		return model;
	}
	
	/**
	 * 内置 Report 不能转换，需要代码中手动继续转换
	 * @param model
	 * @return
	 */
	public static Solution do2bo(GdsapEvalSolution model)
	{
		Solution s = new Solution();
		s.setDes(model.getDes());
		s.setSolutionType((SolutionType)EnumUtils.getByCode(model.getSolutionType(), SolutionType.class));
		s.setId(model.getId());
		s.setInsertTime(model.getInsertTime());
		s.setTitle(model.getTitle());
		s.setUpdateTime(model.getUpdateTime());
		s.setSelected((YesNo)EnumUtils.getByCode(model.getSelected(), YesNo.class));
		s.setSolutionLodgeXmlPath(model.getSolutionLodgeXmlPath());
		s.setSolutionPartLodgeXmlPath(model.getSolutionPartLodgeXmlPath());
		return s;
	}
	
	/**
	 * HeatingPattern
	 */
	public static GdsapEvalHeatingPattern heatingParToGdsap(HeatingPattern heatingPattern)
	{
		if(heatingPattern != null){
			GdsapEvalHeatingPattern gdsapEvalHeatingPattern = new GdsapEvalHeatingPattern();
			gdsapEvalHeatingPattern.setReportId(heatingPattern.getReport().getId());
			gdsapEvalHeatingPattern.setN1On(heatingPattern.getN1On());
			gdsapEvalHeatingPattern.setN1Off(heatingPattern.getN1Off());
			gdsapEvalHeatingPattern.setA1On(heatingPattern.getA1On());
			gdsapEvalHeatingPattern.setA1Off(heatingPattern.getA1Off());
			gdsapEvalHeatingPattern.setN2On(heatingPattern.getN2On());
			gdsapEvalHeatingPattern.setN2Off(heatingPattern.getN2Off());
			gdsapEvalHeatingPattern.setA2On(heatingPattern.getA2On());
			gdsapEvalHeatingPattern.setA2Off(heatingPattern.getA2Off());
			gdsapEvalHeatingPattern.setN3On(heatingPattern.getN3On());
			gdsapEvalHeatingPattern.setN3Off(heatingPattern.getN3Off());
			gdsapEvalHeatingPattern.setA3On(heatingPattern.getA3On());
			gdsapEvalHeatingPattern.setA3Off(heatingPattern.getA3Off());
			gdsapEvalHeatingPattern.setN4On(heatingPattern.getN4On());
			gdsapEvalHeatingPattern.setN4Off(heatingPattern.getN4Off());
			gdsapEvalHeatingPattern.setA4On(heatingPattern.getA4On());
			gdsapEvalHeatingPattern.setA4Off(heatingPattern.getA4Off());
			gdsapEvalHeatingPattern.setDays(heatingPattern.getDays());
			return gdsapEvalHeatingPattern;
		}
			return null;
	}
	
	public static HeatingPattern GdsapToheatingPar(GdsapEvalHeatingPattern gdsapEvalHeatingPattern)
	{
		if(gdsapEvalHeatingPattern != null){
			HeatingPattern heatingPattern = new HeatingPattern();
			heatingPattern.setN1On(gdsapEvalHeatingPattern.getN1On());
			heatingPattern.setN1Off(gdsapEvalHeatingPattern.getN1Off());
			heatingPattern.setA1On(gdsapEvalHeatingPattern.getA1On());
			heatingPattern.setA1Off(gdsapEvalHeatingPattern.getA1Off());
			heatingPattern.setN2On(gdsapEvalHeatingPattern.getN2On());
			heatingPattern.setN2Off(gdsapEvalHeatingPattern.getN2Off());
			heatingPattern.setA2On(gdsapEvalHeatingPattern.getA2On());
			heatingPattern.setA2Off(gdsapEvalHeatingPattern.getA2Off());
			heatingPattern.setN3On(gdsapEvalHeatingPattern.getN3On());
			heatingPattern.setN3Off(gdsapEvalHeatingPattern.getN3Off());
			heatingPattern.setA3On(gdsapEvalHeatingPattern.getA3On());
			heatingPattern.setA3Off(gdsapEvalHeatingPattern.getA3Off());
			heatingPattern.setN4On(gdsapEvalHeatingPattern.getN4On());
			heatingPattern.setN4Off(gdsapEvalHeatingPattern.getN4Off());
			heatingPattern.setA4On(gdsapEvalHeatingPattern.getA4On());
			heatingPattern.setA4Off(gdsapEvalHeatingPattern.getA4Off());
			heatingPattern.setDays(gdsapEvalHeatingPattern.getDays());
			return heatingPattern;
		}
			return null;
	}
	
	/**
	 * GdsapAltType -> LogType
	 * @param model
	 * @return
	 */
	public static GdsapEvalHeatProportion heatProportionToGdsap(HeatProportion heatProportion)
	{
		if(heatProportion != null){
			GdsapEvalHeatProportion gdsapEvalHeatProportion = new GdsapEvalHeatProportion();
			gdsapEvalHeatProportion.setId(heatProportion.getId());
			gdsapEvalHeatProportion.setRoomScope(heatProportion.getRoomScope().getCode());
			gdsapEvalHeatProportion.setMain1(heatProportion.getMain1().getCode());
			gdsapEvalHeatProportion.setMain2(heatProportion.getMain2().getCode());
			gdsapEvalHeatProportion.setSecondary(heatProportion.getSecondary().getCode());
			gdsapEvalHeatProportion.setHeatedPartially(heatProportion.getHeatedPartially().getCode());
			gdsapEvalHeatProportion.setNotable(heatProportion.getNotable().getCode());
			gdsapEvalHeatProportion.setReportId(heatProportion.getReportId().getId());
			return gdsapEvalHeatProportion;
		}
			return null;
	}
	
	//private static BeanCopier heatProportionCopier2 = BeanCopier.create(GdsapEvalHeatProportion.class,HeatProportion.class,false);
	
	public static HeatProportion gdsapToHeatProportion(GdsapEvalHeatProportion gdsapEvalHeatProportion)
	{
		if(gdsapEvalHeatProportion != null){
			HeatProportion heatProportion = new HeatProportion();
			//heatProportionCopier2.copy(gdsapEvalHeatProportion,heatProportion,null);
			heatProportion.setId(gdsapEvalHeatProportion.getId());
			heatProportion.setRoomScope((RoomType)EnumUtils.getByCode(gdsapEvalHeatProportion.getRoomScope(), RoomType.class));
			heatProportion.setMain1((YesNo)EnumUtils.getByCode(gdsapEvalHeatProportion.getMain1(), YesNo.class));
			heatProportion.setMain2((YesNo)EnumUtils.getByCode(gdsapEvalHeatProportion.getMain2(), YesNo.class));
			heatProportion.setSecondary((YesNo)EnumUtils.getByCode(gdsapEvalHeatProportion.getSecondary(), YesNo.class));
			heatProportion.setHeatedPartially((YesNo)EnumUtils.getByCode(gdsapEvalHeatProportion.getHeatedPartially(), YesNo.class));
			heatProportion.setNotable((YesNo)EnumUtils.getByCode(gdsapEvalHeatProportion.getNotable(), YesNo.class));
			return heatProportion;
		}
			return null;
	}
	
	//GdsapEvalAppCooking
	//private static BeanCopier appCookingCopier = BeanCopier.create(AppCooking.class,GdsapEvalAppCooking.class,false);
	
	public static GdsapEvalAppCooking gAppCookingToAppCooking(AppCooking appCooking)
	{
		if(appCooking != null){
			GdsapEvalAppCooking gdsapEvalAppCooking = new GdsapEvalAppCooking();
			//appCookingCopier.copy(appCooking, gdsapEvalAppCooking, null);
			gdsapEvalAppCooking.setReportId(appCooking.getReport().getId());
			gdsapEvalAppCooking.setDryProportion(appCooking.getDryProportion());
			gdsapEvalAppCooking.setFridgeFreezersNumber(appCooking.getFridgeFreezersNumber());
			gdsapEvalAppCooking.setFridgesNumber(appCooking.getFridgesNumber());
			gdsapEvalAppCooking.setFreezersNumber(appCooking.getFreezersNumber());
			gdsapEvalAppCooking.setCookerType(appCooking.getCookerType().getId());
			gdsapEvalAppCooking.setCookingFuel(appCooking.getCookingFuel().getId());
			gdsapEvalAppCooking.setDryProportion(appCooking.getDryProportion());
			gdsapEvalAppCooking.setDryingClothesSpacable(appCooking.getDryingClothesSpacable().getCode());
			return gdsapEvalAppCooking;
		}
			return null;
	}
	
	//private static BeanCopier appCookingCopier2 = BeanCopier.create(GdsapEvalAppCooking.class,AppCooking.class,false); 
	
	public static AppCooking appCookingToGAppCooking(GdsapEvalAppCooking gdsapEvalAppCooking)
	{
		if(gdsapEvalAppCooking != null){
			AppCooking appCooking = new AppCooking();
			//appCookingCopier2.copy(gdsapEvalAppCooking,appCooking,null);
			appCooking.setDryProportion(gdsapEvalAppCooking.getDryProportion());
			appCooking.setDryingClothesSpacable((YesNo)EnumUtils.getByCode(gdsapEvalAppCooking.getDryingClothesSpacable(), YesNo.class));
			appCooking.setFridgeFreezersNumber(gdsapEvalAppCooking.getFridgeFreezersNumber());
			appCooking.setFridgesNumber(gdsapEvalAppCooking.getFridgesNumber());
			appCooking.setFreezersNumber(gdsapEvalAppCooking.getFreezersNumber());
			return appCooking;
		}
			return null;
	}
	
	//GdsapEvalOtherFuel
	public static GdsapEvalOtherFuel gOtherFuelToOtherFuel(OtherFuel otherFuel){
		if(otherFuel != null){
			GdsapEvalOtherFuel gdsapEvalOtherFuel = new GdsapEvalOtherFuel();
			gdsapEvalOtherFuel.setId(otherFuel.getId());
			gdsapEvalOtherFuel.setReliablityLevel(otherFuel.getReliablityLevel() != null ? otherFuel.getReliablityLevel().getId() : null);
			gdsapEvalOtherFuel.setFixedCost(otherFuel.getFixedCost());
			gdsapEvalOtherFuel.setFixedCostSelected(otherFuel.getFixedCostSelected() != null ? otherFuel.getFixedCostSelected().getId() : null);
			gdsapEvalOtherFuel.setUnitOfSale(otherFuel.getUnitOfSale() != null ? otherFuel.getUnitOfSale().getId() : null);
			gdsapEvalOtherFuel.setUnitPrice(otherFuel.getUnitPrice());
			gdsapEvalOtherFuel.setUnitsPurchasedNumber(otherFuel.getUnitsPurchasedNumber());
			gdsapEvalOtherFuel.setTotalCost(otherFuel.getTotalCost());
			gdsapEvalOtherFuel.setPeriodSelect(otherFuel.getPeriodSelect() != null ? otherFuel.getPeriodSelect().getId() : null);
			gdsapEvalOtherFuel.setPeriod(otherFuel.getPeriod());
			gdsapEvalOtherFuel.setVatable(otherFuel.getVatable() != null ? otherFuel.getVatable().getCode() : null);
			gdsapEvalOtherFuel.setFuelCode(otherFuel.getFuelCode() != null ? otherFuel.getFuelCode() : null);
			gdsapEvalOtherFuel.setUnusualEnergyUsingable(otherFuel.getUnusualEnergyUsingable() != null ? otherFuel.getUnusualEnergyUsingable().getCode() : null);
			gdsapEvalOtherFuel.setUnusualEnergyUsingableDes(otherFuel.getUnusualEnergyUsingableDes() != null ? otherFuel.getUnusualEnergyUsingableDes() : null);
			gdsapEvalOtherFuel.setReportId(otherFuel.getReport().getId());
			return gdsapEvalOtherFuel;
		}
			return null;
	}
	
	//OtherFuel
	public static OtherFuel otherFuelToGOtherFuel(GdsapEvalOtherFuel gdsapEvalOtherFuel){
		if(gdsapEvalOtherFuel != null){
			OtherFuel otherFuel = new OtherFuel();
			otherFuel.setId(gdsapEvalOtherFuel.getId());
			otherFuel.setFixedCost(gdsapEvalOtherFuel.getFixedCost());
			otherFuel.setUnitPrice(gdsapEvalOtherFuel.getUnitPrice());
			otherFuel.setUnitsPurchasedNumber(gdsapEvalOtherFuel.getUnitsPurchasedNumber());
			otherFuel.setTotalCost(gdsapEvalOtherFuel.getTotalCost());
			otherFuel.setPeriod(gdsapEvalOtherFuel.getPeriod());
			otherFuel.setVatable((YesNo)EnumUtils.getByCode(gdsapEvalOtherFuel.getVatable(), YesNo.class));
			otherFuel.setFuelCode(gdsapEvalOtherFuel.getFuelCode());
			otherFuel.setUnusualEnergyUsingable(gdsapEvalOtherFuel.getUnusualEnergyUsingable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalOtherFuel.getUnusualEnergyUsingable(), YesNo.class) : null);
			otherFuel.setUnusualEnergyUsingableDes(gdsapEvalOtherFuel.getUnusualEnergyUsingableDes() != null ? gdsapEvalOtherFuel.getUnusualEnergyUsingableDes() : null);
			
			return otherFuel;
		}
			return null;
	}
	
	//GdsapEvalHeatingSystem
	public static GdsapEvalHeatingSystem heatingSystemToGheatingSystem(HeatingSystem heatingSystem)
	{
		if(heatingSystem != null){
			GdsapEvalHeatingSystem gdsapEvalHeatingSystem = new GdsapEvalHeatingSystem();
			gdsapEvalHeatingSystem.setKnownable(heatingSystem.getKnownable().getCode());
			gdsapEvalHeatingSystem.setReportId(heatingSystem.getReport().getId());
			gdsapEvalHeatingSystem.setTemperature(heatingSystem.getTemperature());
			gdsapEvalHeatingSystem.setmHs(heatingSystem.getmHs() != null ? heatingSystem.getmHs().getId() : null);
			gdsapEvalHeatingSystem.setmHf(heatingSystem.getmHf() != null ? heatingSystem.getmHf().getId() : null);
			gdsapEvalHeatingSystem.setmHt(heatingSystem.getmHt() != null ? heatingSystem.getmHt().getId() : null);
			gdsapEvalHeatingSystem.setSmHs(heatingSystem.getSmHs() != null ? heatingSystem.getSmHs().getId() : null);
			gdsapEvalHeatingSystem.setSmHf(heatingSystem.getSmHf() != null ? heatingSystem.getSmHf().getId() : null);
			gdsapEvalHeatingSystem.setSmHt(heatingSystem.getSmHt() != null ? heatingSystem.getSmHt().getId() : null);
			gdsapEvalHeatingSystem.setsHs(heatingSystem.getsHs() != null ? heatingSystem.getsHs().getId() : null);
			gdsapEvalHeatingSystem.setsHf(heatingSystem.getsHf() != null ?  heatingSystem.getsHf().getId() : null);
			gdsapEvalHeatingSystem.setsHt(heatingSystem.getsHt() != null ? heatingSystem.getsHt().getId() : null);
			return gdsapEvalHeatingSystem;
		}
			return null;
	}
	
	public static HeatingSystem gheatingSystemToHeatingSystem(GdsapEvalHeatingSystem gdsapEvalHeatingSystem)
	{
		if(gdsapEvalHeatingSystem != null){
			HeatingSystem heatingSystem = new HeatingSystem();
			heatingSystem.setKnownable((YesNo)EnumUtils.getByCode(gdsapEvalHeatingSystem.getKnownable(), YesNo.class));
			heatingSystem.setTemperature(gdsapEvalHeatingSystem.getTemperature());
			return heatingSystem;
		}
			return null;
	}
	
	//GdsapEvalOccupants
	public static GdsapEvalOccupants gdsapToOccupants(Occupants occupants){
		if(occupants != null){
			GdsapEvalOccupants gdsapEvalOccupants = new GdsapEvalOccupants();
			gdsapEvalOccupants.setReportId(occupants.getReport().getId());
			gdsapEvalOccupants.setOccupantsNumber(occupants.getOccupantsNumber());
			gdsapEvalOccupants.setShowersPerable(occupants.getShowersPerable() != null ? occupants.getShowersPerable().getCode() : null);
			gdsapEvalOccupants.setBathsPerDay(occupants.getBathsPerDay());
			gdsapEvalOccupants.setShowersPerable(occupants.getShowersPerable() != null ? occupants.getShowersPerable().getCode() : null);
			gdsapEvalOccupants.setShowerType(occupants.getShowerType().getId());
			gdsapEvalOccupants.setShowersPerDay(occupants.getShowersPerDay());
			gdsapEvalOccupants.setBathsPerable(occupants.getBathsPerable() != null ? occupants.getBathsPerable().getCode() : null);
			return gdsapEvalOccupants;
		}
			return null;
	}
	
	//private static BeanCopier gOccupantsCopier = BeanCopier.create(GdsapEvalOccupants.class, Occupants.class, false);
	
	public static Occupants occupantsToGdsap(GdsapEvalOccupants gdsapEvalOccupants){
		if(gdsapEvalOccupants != null){
			Occupants occupants = new Occupants();
		    //gOccupantsCopier.copy(gdsapEvalOccupants, occupants, null);
		    occupants.setOccupantsNumber(gdsapEvalOccupants.getOccupantsNumber());
		    occupants.setShowersPerable((YesNo)EnumUtils.getByCode(gdsapEvalOccupants.getShowersPerable(), YesNo.class));
		    occupants.setShowersPerDay(gdsapEvalOccupants.getShowersPerDay());
		    occupants.setBathsPerable((YesNo)EnumUtils.getByCode(gdsapEvalOccupants.getBathsPerable(),YesNo.class));
		    occupants.setBathsPerDay(gdsapEvalOccupants.getBathsPerDay());
			return occupants;
		}
			return null;
	}
	
	//GdsapEvalBillData
//	public static GdsapEvalBillData billDataToGBillData(BillData billData){
//		if(billData != null){
//			GdsapEvalBillData gdsapEvalBillData = new GdsapEvalBillData();
//			gdsapEvalBillData.setReportId(billData.getReport().getId());
//			gdsapEvalBillData.setChFuelCode(billData.getChFuelCode() != null ? billData.getChFuelCode():null);
//			gdsapEvalBillData.setChReliablityLevel(billData.getChReliablityLevel() != null ? billData.getChReliablityLevel().getId() : null);
//			gdsapEvalBillData.setChEnergyUsed(billData.getChEnergyUsed());
//			gdsapEvalBillData.setChPeriodSelect(billData.getChPeriodSelect() != null ? billData.getChPeriodSelect().getId() : null);
//			gdsapEvalBillData.setVatable(billData.getVatable().getCode());
//			gdsapEvalBillData.setChFixedCost(billData.getChFixedCost());
//			gdsapEvalBillData.setChFixedCostSelected(billData.getChFixedCostSelected() != null ? billData.getChFixedCostSelected().getId() : null);
//			gdsapEvalBillData.setChUnitPrice(billData.getChUnitPrice());
//			gdsapEvalBillData.setEtElectricityTariff(billData.getEtElectricityTariff() != null ? billData.getEtElectricityTariff().getId() : null);
//			gdsapEvalBillData.setMgReliablityLevel(billData.getMgReliablityLevel() != null ? billData.getMgReliablityLevel().getId() : null);
//			gdsapEvalBillData.setMgGasUsed(billData.getMgGasUsed());
//			gdsapEvalBillData.setMgPeriodSelect(billData.getMgPeriodSelect() != null ? billData.getMgPeriodSelect().getId() : null);
//			gdsapEvalBillData.setMgChargingBasis(billData.getMgChargingBasis() != null ? billData.getMgChargingBasis().getId() : null);
//			gdsapEvalBillData.setMgPeriod(billData.getMgPeriod());
//			gdsapEvalBillData.setMgStAmount(billData.getMgStAmount());
//			gdsapEvalBillData.setMgStUnitPrice(billData.getMgStUnitPrice());
//			gdsapEvalBillData.setMgTwInitialUnit(billData.getMgTwInitialUnit());
//			gdsapEvalBillData.setMgTwUnits(billData.getMgTwUnits());
//			gdsapEvalBillData.setMgTwFollowOn(billData.getMgTwFollowOn());
//			gdsapEvalBillData.setMgVatAble(billData.getMgVatAble().getCode());
//			gdsapEvalBillData.setMgStAmountSelect(billData.getMgStAmountSelect() != null ? billData.getMgStAmountSelect().getId() : null);
//			gdsapEvalBillData.setChPeriod(billData.getChPeriod());
//			gdsapEvalBillData.setMgTwUnitsSelected(billData.getMgTwUnitsSelected() != null ? billData.getMgTwUnitsSelected().getId() : null);
//			gdsapEvalBillData.setEtStReliablityLevel(billData.getEtStReliablityLevel() != null ? billData.getEtStReliablityLevel().getId() : null);
//			gdsapEvalBillData.setEtStElectricityUsed(billData.getEtStElectricityUsed() != null ? billData.getEtStElectricityUsed() : null);
//			gdsapEvalBillData.setEtStPeriod(billData.getEtStPeriod() != null ? billData.getEtStPeriod() : null);
//			gdsapEvalBillData.setEtStPeriodSelect(billData.getEtStPeriodSelect() != null ? billData.getEtStPeriodSelect().getId() : null);
//			gdsapEvalBillData.setEtStVatable(billData.getEtStVatable() != null ? billData.getEtStVatable().getCode() : null);
//			gdsapEvalBillData.setEtStChargingBasis(billData.getEtStChargingBasis() != null ? billData.getEtStChargingBasis().getId() : null);
//			gdsapEvalBillData.setEtStStandingChargeAmount(billData.getEtStStandingChargeAmount() != null ? billData.getEtStStandingChargeAmount() : null);
//			gdsapEvalBillData.setEtStStandingChargeAmountSelect(billData.getEtStStandingChargeAmountSelect() != null ? billData.getEtStStandingChargeAmountSelect().getId() : null);
//			gdsapEvalBillData.setEtStUnitPrice(billData.getEtStUnitPrice() != null ? billData.getEtStUnitPrice() : null);
//			gdsapEvalBillData.setEtStInitialUnitPrice(billData.getEtStInitialUnitPrice() != null ? billData.getEtStInitialUnitPrice() : null);
//			gdsapEvalBillData.setEtStUnitsAtThisPrice(billData.getEtStUnitsAtThisPrice() != null ? billData.getEtStUnitsAtThisPrice() : null);
//			gdsapEvalBillData.setEtStUnitsAtThisPriceSelect(billData.getEtStUnitsAtThisPriceSelect() != null ? billData.getEtStUnitsAtThisPriceSelect().getId() : null);
//			gdsapEvalBillData.setEtStFollowonUnitPrice(billData.getEtStFollowonUnitPrice() != null ? billData.getEtStFollowonUnitPrice() : null);
//			gdsapEvalBillData.setEtOffHReliablityLevel(billData.getEtOffHReliablityLevel() != null ? billData.getEtOffHReliablityLevel().getId() : null);
//			gdsapEvalBillData.setEtOffHElectricityUsed(billData.getEtOffHElectricityUsed() != null ? billData.getEtOffHElectricityUsed() : null);
//			gdsapEvalBillData.setEtOffHPeriod(billData.getEtOffHPeriod() != null ? billData.getEtOffHPeriod() : null);
//			gdsapEvalBillData.setEtOffHPeriodSelect(billData.getEtOffHPeriodSelect() != null ? billData.getEtOffHPeriodSelect().getId() : null);
//			gdsapEvalBillData.setEtOffHVatable(billData.getEtOffHVatable() != null ? billData.getEtOffHVatable().getCode() : null);
//			gdsapEvalBillData.setEtOffHChargingBasis(billData.getEtOffHChargingBasis() != null ? billData.getEtOffHChargingBasis().getId() : null);
//			gdsapEvalBillData.setEtOffHStandingChargeAmount(billData.getEtOffHStandingChargeAmount() != null ? billData.getEtOffHStandingChargeAmount() : null);
//			gdsapEvalBillData.setEtOffHStandingChargeAmountSelect(billData.getEtOffHStandingChargeAmountSelect() != null ? billData.getEtOffHStandingChargeAmountSelect().getId() : null);
//			gdsapEvalBillData.setEtOffHUnitPrice(billData.getEtOffHUnitPrice() != null ? billData.getEtOffHUnitPrice() : null);
//			gdsapEvalBillData.setEtOffHInitialUnitAmount(billData.getEtOffHInitialUnitAmount() != null ? billData.getEtOffHInitialUnitAmount() : null);
//			gdsapEvalBillData.setEtOffHUnitsAtThisPrice(billData.getEtOffHUnitsAtThisPrice() != null ? billData.getEtOffHUnitsAtThisPrice() : null);
//			gdsapEvalBillData.setEtOffHUnitsAtThisPriceSelect(billData.getEtOffHUnitsAtThisPriceSelect() != null ? billData.getEtOffHUnitsAtThisPriceSelect().getId() : null);
//			gdsapEvalBillData.setEtOffHFollow(billData.getEtOffHFollow() != null ? billData.getEtOffHFollow() : null);
//			gdsapEvalBillData.setEtOffLReliablityLevel(billData.getEtOffLReliablityLevel() != null ? billData.getEtOffLReliablityLevel().getId() : null);
//			gdsapEvalBillData.setEtOffLElectricityUsed(billData.getEtOffLElectricityUsed() != null ? billData.getEtOffLElectricityUsed() : null);
//			gdsapEvalBillData.setEtOffLPeriod(billData.getEtOffLPeriod() != null ? billData.getEtOffLPeriod() : null);
//			gdsapEvalBillData.setEtOffLPeriodSelect(billData.getEtOffLPeriodSelect() != null ? billData.getEtOffLPeriodSelect().getId() : null);
//			gdsapEvalBillData.setEtOffLVatable(billData.getEtOffLVatable() != null ? billData.getEtOffLVatable().getCode() : null);
//			gdsapEvalBillData.setEtOffLChargingBasis(billData.getEtOffLChargingBasis() != null ? billData.getEtOffLChargingBasis().getId() : null);
//			gdsapEvalBillData.setEtOffLStandingChargeAmount(billData.getEtOffLStandingChargeAmount() != null ? billData.getEtOffLStandingChargeAmount() : null);
//			gdsapEvalBillData.setEtOffLStandingChargeAmountSelect(billData.getEtOffLStandingChargeAmountSelect()!= null ?  billData.getEtOffLStandingChargeAmountSelect().getId() : null);
//			gdsapEvalBillData.setEtOffLUnitPrice(billData.getEtOffLUnitPrice() != null ? billData.getEtOffLUnitPrice() : null);
//			gdsapEvalBillData.setEtOffLInitialUnitAmount(billData.getEtOffLInitialUnitAmount() != null ? billData.getEtOffLInitialUnitAmount() : null);
//			gdsapEvalBillData.setEtOffLUnitsAtThisPrice(billData.getEtOffLUnitsAtThisPrice() != null ? billData.getEtOffLUnitsAtThisPrice() : null);
//			gdsapEvalBillData.setEtOffLUnitsAtThisPriceSelect(billData.getEtOffLUnitsAtThisPriceSelect() != null ? billData.getEtOffLUnitsAtThisPriceSelect().getId() : null);
//			gdsapEvalBillData.setEtOffLFollow(billData.getEtOffLFollow() != null ? billData.getEtOffLFollow() : null);
//			gdsapEvalBillData.setEtPvable(billData.getEtPvable() != null ? billData.getEtPvable().getCode() : null);
//			gdsapEvalBillData.setEtPvAmount(billData.getEtPvAmount() != null ? billData.getEtPvAmount() : null);
//			gdsapEvalBillData.setEtPvPeriod(billData.getEtPvPeriod() != null ? billData.getEtPvPeriod() : null);
//			gdsapEvalBillData.setEtPvPeriodSelect(billData.getEtPvPeriodSelect() != null ? billData.getEtPvPeriodSelect().getId() : null);
//			gdsapEvalBillData.setEtWindable(billData.getEtWindable() != null ? billData.getEtWindable().getCode() : null);
//			gdsapEvalBillData.setEtWindAmount(billData.getEtWindAmount() != null ? billData.getEtWindAmount() : null);
//			gdsapEvalBillData.setEtWindPeriod(billData.getEtWindPeriod() != null ? billData.getEtWindPeriod() : null);
//			gdsapEvalBillData.setEtWindPeriodSelect(billData.getEtWindPeriodSelect() != null ? billData.getEtWindPeriodSelect().getId() : null);
//			gdsapEvalBillData.setEtMicroable(billData.getEtMicroable() != null ? billData.getEtMicroable().getCode() : null);
//			gdsapEvalBillData.setEtMicroableAmount(billData.getEtMicroableAmount() != null ? billData.getEtMicroableAmount() : null);
//			gdsapEvalBillData.setEtMicroablePeriod(billData.getEtMicroablePeriod() != null ? billData.getEtMicroablePeriod() : null);
//			gdsapEvalBillData.setEtMicroablePeriodSelect(billData.getEtMicroablePeriodSelect() != null ? billData.getEtMicroablePeriodSelect().getId() : null);
//			gdsapEvalBillData.setChUnusualEnergyUsingable(billData.getChUnusualEnergyUsingable() != null ? billData.getChUnusualEnergyUsingable().getCode() : null);
//			gdsapEvalBillData.setChUnusualEnergyUsingableDes(billData.getChUnusualEnergyUsingableDes() != null ? billData.getChUnusualEnergyUsingableDes() : null);
//			gdsapEvalBillData.setMgUnusualEnergyUsingable(billData.getMgUnusualEnergyUsingable() != null ? billData.getMgUnusualEnergyUsingable().getCode() : null);
//			gdsapEvalBillData.setMgUnusualEnergyUsingableDes(billData.getMgUnusualEnergyUsingableDes() != null ? billData.getMgUnusualEnergyUsingableDes() : null);
//			gdsapEvalBillData.setEtStUnusualEnergyUsingable(billData.getEtStUnusualEnergyUsingable() != null ? billData.getEtStUnusualEnergyUsingable().getCode() : null);
//			gdsapEvalBillData.setEtStUnusualEnergyUsingableDes(billData.getEtStUnusualEnergyUsingableDes() != null ? billData.getEtStUnusualEnergyUsingableDes() : null);
//			gdsapEvalBillData.setEtOffHUnusualEnergyUsingable(billData.getEtOffHUnusualEnergyUsingable() != null ? billData.getEtOffHUnusualEnergyUsingable().getCode() : null);
//			gdsapEvalBillData.setEtOffHUnusualEnergyUsingableDes(billData.getEtOffHUnusualEnergyUsingableDes() != null ? billData.getEtOffHUnusualEnergyUsingableDes() : null);
//			gdsapEvalBillData.setEtOffLUnusualEnergyUsingable(billData.getEtOffLUnusualEnergyUsingable() != null ? billData.getEtOffLUnusualEnergyUsingable().getCode() : null);
//			gdsapEvalBillData.setEtOffLUnusualEnergyUsingableDes(billData.getEtOffLUnusualEnergyUsingableDes() != null ? billData.getEtOffLUnusualEnergyUsingableDes() : null);
//			return gdsapEvalBillData;
//		}
//			return null;
//	}
	
//	public static BillData gBillDataToBillData(GdsapEvalBillData gdsapEvalBillData){
//		if(gdsapEvalBillData != null){
//			BillData billData = new BillData();
//			billData.setReportId(gdsapEvalBillData.getReportId());
//			billData.setChEnergyUsed(gdsapEvalBillData.getChEnergyUsed());
//			billData.setChFixedCost(gdsapEvalBillData.getChFixedCost());
//			billData.setChUnitPrice(gdsapEvalBillData.getChUnitPrice());
//			billData.setMgGasUsed(gdsapEvalBillData.getMgGasUsed());
//			billData.setChPeriod(gdsapEvalBillData.getChPeriod());
//			billData.setVatable((YesNo)EnumUtils.getByCode(gdsapEvalBillData.getVatable(), YesNo.class));
//			billData.setMgPeriod(gdsapEvalBillData.getMgPeriod());
//			billData.setMgStAmount(gdsapEvalBillData.getMgStAmount());
//			billData.setMgStUnitPrice(gdsapEvalBillData.getMgStUnitPrice());
//			billData.setMgTwInitialUnit(gdsapEvalBillData.getMgTwInitialUnit());
//			billData.setMgTwUnits(gdsapEvalBillData.getMgTwUnits());
//			billData.setMgTwFollowOn(gdsapEvalBillData.getMgTwFollowOn());
//			billData.setMgVatAble((YesNo)EnumUtils.getByCode(gdsapEvalBillData.getMgVatAble(),YesNo.class));
//			billData.setChPeriod(gdsapEvalBillData.getChPeriod());
//			billData.setChFuelCode(gdsapEvalBillData.getChFuelCode() != null ? gdsapEvalBillData.getChFuelCode() : null);
//			billData.setEtStElectricityUsed(gdsapEvalBillData.getEtStElectricityUsed() != null ? gdsapEvalBillData.getEtStElectricityUsed() : null);
//			billData.setEtStPeriod(gdsapEvalBillData.getEtStPeriod() != null ? gdsapEvalBillData.getEtStPeriod() : null);
//			billData.setEtStVatable(gdsapEvalBillData.getEtStVatable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillData.getEtStVatable(), YesNo.class) : null);
//			billData.setEtStStandingChargeAmount(gdsapEvalBillData.getEtStStandingChargeAmount() != null ? gdsapEvalBillData.getEtStStandingChargeAmount() : null);
//			billData.setEtStUnitPrice(gdsapEvalBillData.getEtStUnitPrice() != null ? gdsapEvalBillData.getEtStUnitPrice() : null);
//			billData.setEtStInitialUnitPrice(gdsapEvalBillData.getEtStInitialUnitPrice() != null ? gdsapEvalBillData.getEtStInitialUnitPrice() : null);
//			billData.setEtStUnitsAtThisPrice(gdsapEvalBillData.getEtStUnitsAtThisPrice() != null ? gdsapEvalBillData.getEtStUnitsAtThisPrice() : null);
//			billData.setEtStFollowonUnitPrice(gdsapEvalBillData.getEtStFollowonUnitPrice() != null ? gdsapEvalBillData.getEtStFollowonUnitPrice() : null);
//			billData.setEtOffHElectricityUsed(gdsapEvalBillData.getEtOffHElectricityUsed() != null ? gdsapEvalBillData.getEtOffHElectricityUsed() : null);
//			billData.setEtOffHPeriod(gdsapEvalBillData.getEtOffHPeriod() != null ? gdsapEvalBillData.getEtOffHPeriod() : null);
//			billData.setEtOffHVatable((YesNo)EnumUtils.getByCode(gdsapEvalBillData.getEtOffHVatable(), YesNo.class));
//			billData.setEtOffHStandingChargeAmount(gdsapEvalBillData.getEtOffHStandingChargeAmount() != null ? gdsapEvalBillData.getEtOffHStandingChargeAmount() : null);
//			billData.setEtOffHUnitPrice(gdsapEvalBillData.getEtOffHUnitPrice() != null ? gdsapEvalBillData.getEtOffHUnitPrice() : null);
//			billData.setEtOffHInitialUnitAmount(gdsapEvalBillData.getEtOffHInitialUnitAmount() != null ? gdsapEvalBillData.getEtOffHInitialUnitAmount() : null);
//			billData.setEtOffHUnitsAtThisPrice(gdsapEvalBillData.getEtOffHUnitsAtThisPrice() != null ? gdsapEvalBillData.getEtOffHUnitsAtThisPrice() : null);
//			billData.setEtOffHFollow(gdsapEvalBillData.getEtOffHFollow() != null ? gdsapEvalBillData.getEtOffHFollow() : null);
//			billData.setEtOffLElectricityUsed(gdsapEvalBillData.getEtOffLElectricityUsed() != null ? gdsapEvalBillData.getEtOffLElectricityUsed() : null);
//			billData.setEtOffLPeriod(gdsapEvalBillData.getEtOffLPeriod() != null ? gdsapEvalBillData.getEtOffLPeriod() : null);
//			billData.setEtOffLVatable(gdsapEvalBillData.getEtOffLVatable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillData.getEtOffLVatable(), YesNo.class) : null);
//			billData.setEtOffLStandingChargeAmount(gdsapEvalBillData.getEtOffLStandingChargeAmount() != null ? gdsapEvalBillData.getEtOffLStandingChargeAmount() : null);
//			billData.setEtOffLUnitPrice(gdsapEvalBillData.getEtOffLUnitPrice() != null ? gdsapEvalBillData.getEtOffLUnitPrice() : null);
//			billData.setEtOffLInitialUnitAmount(gdsapEvalBillData.getEtOffLInitialUnitAmount() != null ? gdsapEvalBillData.getEtOffLInitialUnitAmount() : null);
//			billData.setEtOffLUnitsAtThisPrice(gdsapEvalBillData.getEtOffLUnitsAtThisPrice() != null ? gdsapEvalBillData.getEtOffLUnitsAtThisPrice() : null);
//			billData.setEtOffLFollow(gdsapEvalBillData.getEtOffLFollow() != null ? gdsapEvalBillData.getEtOffLFollow() : null);
//			billData.setEtPvable(gdsapEvalBillData.getEtPvable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillData.getEtPvable(), YesNo.class) : null);
//			billData.setEtPvAmount(gdsapEvalBillData.getEtPvAmount() != null ? gdsapEvalBillData.getEtPvAmount() : null);
//			billData.setEtPvPeriod(gdsapEvalBillData.getEtPvPeriod() != null ? gdsapEvalBillData.getEtPvPeriod() : null);
//			billData.setEtWindable(gdsapEvalBillData.getEtWindable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillData.getEtWindable(), YesNo.class) : null);
//			billData.setEtWindAmount(gdsapEvalBillData.getEtWindAmount() != null ? gdsapEvalBillData.getEtWindAmount() : null);
//			billData.setEtWindPeriod(gdsapEvalBillData.getEtWindPeriod() != null ? gdsapEvalBillData.getEtWindPeriod() : null);
//			billData.setEtMicroable(gdsapEvalBillData.getEtMicroable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillData.getEtMicroable(), YesNo.class) : null);
//			billData.setEtMicroableAmount(gdsapEvalBillData.getEtMicroableAmount() != null ? gdsapEvalBillData.getEtMicroableAmount() : null);
//			billData.setEtMicroablePeriod(gdsapEvalBillData.getEtMicroablePeriod() != null ? gdsapEvalBillData.getEtMicroablePeriod() : null);
//			billData.setChUnusualEnergyUsingable(gdsapEvalBillData.getChUnusualEnergyUsingable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillData.getChUnusualEnergyUsingable(), YesNo.class) : null);
//			billData.setChUnusualEnergyUsingableDes(gdsapEvalBillData.getChUnusualEnergyUsingableDes() != null ? gdsapEvalBillData.getChUnusualEnergyUsingableDes() : null);
//			billData.setMgUnusualEnergyUsingable(gdsapEvalBillData.getMgUnusualEnergyUsingable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillData.getMgUnusualEnergyUsingable(), YesNo.class) : null);
//			billData.setMgUnusualEnergyUsingableDes(gdsapEvalBillData.getMgUnusualEnergyUsingableDes() != null ? gdsapEvalBillData.getMgUnusualEnergyUsingableDes() : null);
//			billData.setEtStUnusualEnergyUsingable(gdsapEvalBillData.getEtStUnusualEnergyUsingable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillData.getEtStUnusualEnergyUsingable(), YesNo.class) : null);
//			billData.setEtStUnusualEnergyUsingableDes(gdsapEvalBillData.getEtStUnusualEnergyUsingableDes() != null ? gdsapEvalBillData.getEtStUnusualEnergyUsingableDes() : null);
//			billData.setEtOffHUnusualEnergyUsingable(gdsapEvalBillData.getEtOffHUnusualEnergyUsingable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillData.getEtOffHUnusualEnergyUsingable(), YesNo.class) : null);
//			billData.setEtOffHUnusualEnergyUsingableDes(gdsapEvalBillData.getEtOffHUnusualEnergyUsingableDes() != null ? gdsapEvalBillData.getEtOffHUnusualEnergyUsingableDes() : null);
//			billData.setEtOffLUnusualEnergyUsingable(gdsapEvalBillData.getEtOffLUnusualEnergyUsingable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillData.getEtOffLUnusualEnergyUsingable(), YesNo.class) : null);
//			billData.setEtOffLUnusualEnergyUsingableDes(gdsapEvalBillData.getEtOffLUnusualEnergyUsingableDes() != null ? gdsapEvalBillData.getEtOffLUnusualEnergyUsingableDes() : null);
//			return billData;
//		}
//			return null;
//	}
	
	/**
	 * Solution ---> GdsapEvalSolution
	 * @param solution
	 * @return
	 */
	public static GdsapEvalSolution SolutionToGSolution(Solution solution){
		if(solution != null){
			GdsapEvalSolution gdsapEvalSolution = new GdsapEvalSolution();
			gdsapEvalSolution.setId(solution.getId());
			gdsapEvalSolution.setTitle(solution.getTitle() != null ? solution.getTitle() : null);
			gdsapEvalSolution.setInsertTime(solution.getInsertTime() != null ? solution.getInsertTime() : null);
			gdsapEvalSolution.setUpdateTime(solution.getUpdateTime() != null ? solution.getUpdateTime() : null);
			gdsapEvalSolution.setReportId(solution.getReport().getId());
			gdsapEvalSolution.setDes(solution.getDes() != null ? solution.getDes() : null);
			gdsapEvalSolution.setSolutionPartLodgeXmlPath(solution.getSolutionPartLodgeXmlPath());
			gdsapEvalSolution.setSolutionPdfPath(solution.getSolutionPdfPath());
			gdsapEvalSolution.setSolutionType(solution.getSolutionType() != null ? solution.getSolutionType().getCode() : null);
			gdsapEvalSolution.setSelected(solution.getSelected() != null ? solution.getSelected().getCode() : null);
			return gdsapEvalSolution;
		}
		return null;
	}
	
	/**
	 * GdsapEvalSolution ---> Solution
	 * @param gdsapEvalSolution
	 * @return
	 */
	public static Solution GSolutionToSolution(GdsapEvalSolution gdsapEvalSolution){
		if(gdsapEvalSolution != null){
			Solution solution = new Solution();
			solution.setId(gdsapEvalSolution.getId());
			solution.setTitle(gdsapEvalSolution.getTitle());
			solution.setInsertTime(gdsapEvalSolution.getInsertTime());
			solution.setUpdateTime(gdsapEvalSolution.getUpdateTime());
			solution.setDes(gdsapEvalSolution.getDes());
			solution.setSelected((YesNo)EnumUtils.getByCode(gdsapEvalSolution.getSelected(), YesNo.class));
			solution.setSolutionLodgeXmlPath(gdsapEvalSolution.getSolutionLodgeXmlPath());
			solution.setSolutionPartLodgeXmlPath(gdsapEvalSolution.getSolutionPartLodgeXmlPath());
			solution.setSolutionPdfPath(gdsapEvalSolution.getSolutionPdfPath());
			solution.setSolutionType((SolutionType)EnumUtils.getByCode(gdsapEvalSolution.getSolutionType(), SolutionType.class));
			return solution;
		}
		return null;
	}
	
	/**
	 * FuelCalResult ---> GdsapEvalSolutionFuelCalResult
	 * @param fuelCalResult
	 * @return
	 */
	public static GdsapEvalSolutionFuelCalResult FuelCalResultToGFuelCalResult(FuelCalResult fuelCalResult){
		if(fuelCalResult != null){
			GdsapEvalSolutionFuelCalResult gdsapEvalSolutionFuelCalResult = new GdsapEvalSolutionFuelCalResult();
			gdsapEvalSolutionFuelCalResult.setId(fuelCalResult.getId());
			gdsapEvalSolutionFuelCalResult.setSolutionId(fuelCalResult.getSolution().getId());
			gdsapEvalSolutionFuelCalResult.setFuelCode(fuelCalResult.getFuelCode() != null ? fuelCalResult.getFuelCode() : null);
			gdsapEvalSolutionFuelCalResult.setOtherFuelCost(fuelCalResult.getOtherFuelCost() != null ? fuelCalResult.getOtherFuelCost() : null);
			gdsapEvalSolutionFuelCalResult.setOtherFuelUse(fuelCalResult.getOtherFuelUse() != null ? fuelCalResult.getOtherFuelUse() : null);
			return gdsapEvalSolutionFuelCalResult;
			
		}
		return null;
	}
	
	/**
	 * GdsapEvalSolutionFuelCalResult ---> FuelCalResult
	 * @param gdsapEvalSolutionFuelCalResult
	 * @return
	 */
	public static FuelCalResult GFuelCalResultToFuelCalResult(GdsapEvalSolutionFuelCalResult gdsapEvalSolutionFuelCalResult){
		if(gdsapEvalSolutionFuelCalResult != null){
			FuelCalResult fuelCalResult = new FuelCalResult();
			fuelCalResult.setId(gdsapEvalSolutionFuelCalResult.getId());
			fuelCalResult.setFuelCode(gdsapEvalSolutionFuelCalResult.getFuelCode() != null ? gdsapEvalSolutionFuelCalResult.getFuelCode() : null);
			fuelCalResult.setOtherFuelCost(gdsapEvalSolutionFuelCalResult.getOtherFuelCost() != null ? gdsapEvalSolutionFuelCalResult.getOtherFuelCost() : null);
			fuelCalResult.setOtherFuelUse(gdsapEvalSolutionFuelCalResult.getOtherFuelUse() != null ? gdsapEvalSolutionFuelCalResult.getOtherFuelUse() : null);
			return fuelCalResult;
		}
		return null;
	}
	
	/**
	 * CalResult ---> GdsapEvalSolutionCalResult
	 * @param calResult
	 * @return
	 */
	public static GdsapEvalSolutionCalResult GCalResultToCalResult(CalResult calResult){
		if(calResult != null){
			GdsapEvalSolutionCalResult gdsapEvalSolutionCalResult = new GdsapEvalSolutionCalResult();
			gdsapEvalSolutionCalResult.setCalResultId(calResult.getSolution().getId());
			gdsapEvalSolutionCalResult.setActualHousehold(calResult.getActualHousehold() != null ? calResult.getActualHousehold() : null);
			gdsapEvalSolutionCalResult.setTypicalHousehold(calResult.getTypicalHousehold() != null ? calResult.getTypicalHousehold() : null);
			gdsapEvalSolutionCalResult.setElectricitySavings(calResult.getElectricitySavings() != null ? calResult.getElectricitySavings() : null);
			gdsapEvalSolutionCalResult.setGasSavings(calResult.getGasSavings() != null ? calResult.getGasSavings() : null);
			gdsapEvalSolutionCalResult.setOtherFuelSavings(calResult.getOtherFuelSavings() != null ? calResult.getOtherFuelSavings() : null);
			gdsapEvalSolutionCalResult.setPercentHeating(calResult.getPercentHeating() != null ? calResult.getPercentHeating() : null);
			gdsapEvalSolutionCalResult.setPercentHotWater(calResult.getPercentHotWater() != null ? calResult.getPercentHotWater() : null);
			gdsapEvalSolutionCalResult.setCostReduction(calResult.getCostReduction() != null ? calResult.getCostReduction() : null);
			gdsapEvalSolutionCalResult.setCommEnergyCost(calResult.getCommEnergyCost() != null ? calResult.getCommEnergyCost() : null);
			gdsapEvalSolutionCalResult.setCommEnergyUse(calResult.getCommEnergyUse() != null ? calResult.getCommEnergyUse() : null);
			gdsapEvalSolutionCalResult.setEleSdCost(calResult.getEleSdCost() != null ? calResult.getEleSdCost() : null);
			gdsapEvalSolutionCalResult.setEleSdUse(calResult.getEleSdUse() != null ? calResult.getEleSdUse() : null);
			gdsapEvalSolutionCalResult.setEleOpCostHighrate(calResult.getEleOpCostHighrate() != null ? calResult.getEleOpCostHighrate() : null);
			gdsapEvalSolutionCalResult.setEleOpUseHighrate(calResult.getEleOpUseHighrate() != null ? calResult.getEleOpUseHighrate() : null);
			gdsapEvalSolutionCalResult.setEleCostLowrate(calResult.getEleCostLowrate() != null ? calResult.getEleCostLowrate() : null);
			gdsapEvalSolutionCalResult.setEleUseLowrate(calResult.getEleUseLowrate() != null ? calResult.getEleUseLowrate() : null);
			gdsapEvalSolutionCalResult.setMainsGasCost(calResult.getMainsGasCost() != null ? calResult.getMainsGasCost() : null);
			gdsapEvalSolutionCalResult.setMainsGasUse(calResult.getMainsGasUse() != null ? calResult.getMainsGasUse() : null);
			return gdsapEvalSolutionCalResult;
		}
		return null;
	}
	
	/**
	 * GdsapEvalSolutionCalResult ---> CalResult
	 * @param gdsapEvalSolutionCalResult
	 * @return
	 */
	public static CalResult CalResultToGCalResult(GdsapEvalSolutionCalResult gdsapEvalSolutionCalResult){
		if(gdsapEvalSolutionCalResult != null){
			CalResult calResult = new CalResult();
			calResult.setActualHousehold(gdsapEvalSolutionCalResult.getActualHousehold() != null ? gdsapEvalSolutionCalResult.getActualHousehold() : null);
			calResult.setTypicalHousehold(gdsapEvalSolutionCalResult.getTypicalHousehold() != null ? gdsapEvalSolutionCalResult.getTypicalHousehold() : null);
			calResult.setElectricitySavings(gdsapEvalSolutionCalResult.getElectricitySavings() != null ? gdsapEvalSolutionCalResult.getElectricitySavings() : null);
			calResult.setGasSavings(gdsapEvalSolutionCalResult.getGasSavings() != null ? gdsapEvalSolutionCalResult.getGasSavings() : null);
			calResult.setOtherFuelSavings(gdsapEvalSolutionCalResult.getOtherFuelSavings() != null ? gdsapEvalSolutionCalResult.getOtherFuelSavings() : null);
			calResult.setPercentHeating(gdsapEvalSolutionCalResult.getPercentHeating() != null ? gdsapEvalSolutionCalResult.getPercentHeating() : null);
			calResult.setPercentHotWater(gdsapEvalSolutionCalResult.getPercentHotWater() != null ? gdsapEvalSolutionCalResult.getPercentHotWater() : null);
			calResult.setCostReduction(gdsapEvalSolutionCalResult.getCostReduction() != null ? gdsapEvalSolutionCalResult.getCostReduction() : null);
			calResult.setCommEnergyCost(gdsapEvalSolutionCalResult.getCommEnergyCost() != null ? gdsapEvalSolutionCalResult.getCommEnergyCost() : null);
			calResult.setCommEnergyUse(gdsapEvalSolutionCalResult.getCommEnergyUse() != null ? gdsapEvalSolutionCalResult.getCommEnergyUse() : null);
			calResult.setEleSdCost(gdsapEvalSolutionCalResult.getEleSdCost() != null ? gdsapEvalSolutionCalResult.getEleSdCost() : null);
			calResult.setEleSdUse(gdsapEvalSolutionCalResult.getEleSdUse() != null ? gdsapEvalSolutionCalResult.getEleSdUse() : null);
			calResult.setEleOpCostHighrate(gdsapEvalSolutionCalResult.getEleOpCostHighrate() != null ? gdsapEvalSolutionCalResult.getEleOpCostHighrate() : null);
			calResult.setEleOpUseHighrate(gdsapEvalSolutionCalResult.getEleOpUseHighrate() != null ? gdsapEvalSolutionCalResult.getEleOpUseHighrate() : null);
			calResult.setEleCostLowrate(gdsapEvalSolutionCalResult.getEleCostLowrate() != null ? gdsapEvalSolutionCalResult.getEleCostLowrate() : null);
			calResult.setEleUseLowrate(gdsapEvalSolutionCalResult.getEleUseLowrate() != null ? gdsapEvalSolutionCalResult.getEleUseLowrate() : null);
			calResult.setMainsGasCost(gdsapEvalSolutionCalResult.getMainsGasCost() != null ? gdsapEvalSolutionCalResult.getMainsGasCost() : null);
			calResult.setMainsGasUse(gdsapEvalSolutionCalResult.getMainsGasUse() != null ? gdsapEvalSolutionCalResult.getMainsGasUse() : null);
			return calResult;
		}
		return null;
	}
	
	/**
	 * 没有进行初始化的字段
	 * Solution 
	 * @param gdsapEvalEpcImprovementResult
	 * @return
	 */
	public static EpcImprovementCalResult toEpcImprovementCalResult(GdsapEvalEpcImprovementResult gdsapEvalEpcImprovementResult)
	{
		EpcImprovementCalResult epcImprovementCalResult = new EpcImprovementCalResult();
		epcImprovementCalResult.setId(gdsapEvalEpcImprovementResult.getId());
		epcImprovementCalResult.setImprovementType(gdsapEvalEpcImprovementResult.getImprovementType());
		epcImprovementCalResult.setImprovementNumber(gdsapEvalEpcImprovementResult.getImprovementNumber());
		epcImprovementCalResult.setGreenDealCategory(gdsapEvalEpcImprovementResult.getGreenDealCategory());
		epcImprovementCalResult.setEstimatedSaving(gdsapEvalEpcImprovementResult.getEstimatedSaving());
		epcImprovementCalResult.setTypicalSaving(gdsapEvalEpcImprovementResult.getTypicalSaving());
		epcImprovementCalResult.setIndicativeCostStart(gdsapEvalEpcImprovementResult.getIndicativeCostStart());
		epcImprovementCalResult.setIndicativeCostEnd(gdsapEvalEpcImprovementResult.getIndicativeCostEnd());
		epcImprovementCalResult.setInUseFactor(gdsapEvalEpcImprovementResult.getInUseFactor());
		return epcImprovementCalResult;
	}
	
	/**
	 * 全部进行转换
	 * @param epcImprovementCalResult
	 * @return
	 */
	public static GdsapEvalEpcImprovementResult toGdsapEvalEpcImprovementResult(EpcImprovementCalResult epcImprovementCalResult)
	{
		GdsapEvalEpcImprovementResult gdsapEvalEpcImprovementResult = new GdsapEvalEpcImprovementResult();
		gdsapEvalEpcImprovementResult.setId(epcImprovementCalResult.getId());
		gdsapEvalEpcImprovementResult.setImprovementType(epcImprovementCalResult.getImprovementType());
		gdsapEvalEpcImprovementResult.setImprovementNumber(epcImprovementCalResult.getImprovementNumber());
		gdsapEvalEpcImprovementResult.setGreenDealCategory(epcImprovementCalResult.getGreenDealCategory());
		gdsapEvalEpcImprovementResult.setEstimatedSaving(epcImprovementCalResult.getEstimatedSaving());
		gdsapEvalEpcImprovementResult.setTypicalSaving(epcImprovementCalResult.getTypicalSaving());
		gdsapEvalEpcImprovementResult.setIndicativeCostStart(epcImprovementCalResult.getIndicativeCostStart());
		gdsapEvalEpcImprovementResult.setIndicativeCostEnd(epcImprovementCalResult.getIndicativeCostEnd());
		gdsapEvalEpcImprovementResult.setInUseFactor(epcImprovementCalResult.getInUseFactor());
		if (epcImprovementCalResult.getSolution() != null)
		{
			gdsapEvalEpcImprovementResult.setSolutionId(epcImprovementCalResult.getSolution().getId());
		}
		return gdsapEvalEpcImprovementResult;
	}
	
	public static ReportEpcImprovementCalResult toReportEpcImprovementCalResult(GdsapEvalReportEpcImprovementResult gdsapEvalReportEpcImprovementResult)
	{
		ReportEpcImprovementCalResult reportEpcImprovementCalResult = new ReportEpcImprovementCalResult();
		reportEpcImprovementCalResult.setId(gdsapEvalReportEpcImprovementResult.getId());
		reportEpcImprovementCalResult.setImprovementType(gdsapEvalReportEpcImprovementResult.getImprovementType());
		reportEpcImprovementCalResult.setImprovementNumber(gdsapEvalReportEpcImprovementResult.getImprovementNumber());
		reportEpcImprovementCalResult.setGreenDealCategory(gdsapEvalReportEpcImprovementResult.getGreenDealCategory());
		reportEpcImprovementCalResult.setEstimatedSaving(gdsapEvalReportEpcImprovementResult.getEstimatedSaving());
		reportEpcImprovementCalResult.setTypicalSaving(gdsapEvalReportEpcImprovementResult.getTypicalSaving());
		reportEpcImprovementCalResult.setIndicativeCostStart(gdsapEvalReportEpcImprovementResult.getIndicativeCostStart());
		reportEpcImprovementCalResult.setIndicativeCostEnd(gdsapEvalReportEpcImprovementResult.getIndicativeCostEnd());
		reportEpcImprovementCalResult.setInUseFactor(gdsapEvalReportEpcImprovementResult.getInUseFactor());
		return reportEpcImprovementCalResult;
	}
	
	public static GdsapEvalReportEpcImprovementResult toGdsapEvalReportEpcImprovementResult(ReportEpcImprovementCalResult reportEpcImprovementCalResult)
	{
		GdsapEvalReportEpcImprovementResult gdsapEvalReportEpcImprovementResult = new GdsapEvalReportEpcImprovementResult();
		gdsapEvalReportEpcImprovementResult.setId(reportEpcImprovementCalResult.getId());
		gdsapEvalReportEpcImprovementResult.setImprovementType(reportEpcImprovementCalResult.getImprovementType());
		gdsapEvalReportEpcImprovementResult.setImprovementNumber(reportEpcImprovementCalResult.getImprovementNumber());
		gdsapEvalReportEpcImprovementResult.setGreenDealCategory(reportEpcImprovementCalResult.getGreenDealCategory());
		gdsapEvalReportEpcImprovementResult.setEstimatedSaving(reportEpcImprovementCalResult.getEstimatedSaving());
		gdsapEvalReportEpcImprovementResult.setTypicalSaving(reportEpcImprovementCalResult.getTypicalSaving());
		gdsapEvalReportEpcImprovementResult.setIndicativeCostStart(reportEpcImprovementCalResult.getIndicativeCostStart());
		gdsapEvalReportEpcImprovementResult.setIndicativeCostEnd(reportEpcImprovementCalResult.getIndicativeCostEnd());
		gdsapEvalReportEpcImprovementResult.setInUseFactor(reportEpcImprovementCalResult.getInUseFactor());
		if (reportEpcImprovementCalResult.getReport() != null)
		{
			gdsapEvalReportEpcImprovementResult.setReportId(reportEpcImprovementCalResult.getReport().getId());
		}
		return gdsapEvalReportEpcImprovementResult;
	}
	
	/**
	 * 
	 * @param billDataMg
	 * @return
	 */
	public static GdsapEvalBillDataMg toGdsapEvalBillDataMg(BillDataMg billDataMg)
	{
		GdsapEvalBillDataMg gdsapEvalBillDataMg = new GdsapEvalBillDataMg();
		
		gdsapEvalBillDataMg.setReportId(billDataMg.getReport().getId());
		gdsapEvalBillDataMg.setMgReliablityLevel(billDataMg.getMgReliablityLevel() != null ? billDataMg.getMgReliablityLevel().getId():null);
		gdsapEvalBillDataMg.setMgGasUsed(billDataMg.getMgGasUsed());
		gdsapEvalBillDataMg.setMgPeriodSelect(billDataMg.getMgPeriodSelect() != null ? billDataMg.getMgPeriodSelect().getId():null);
		gdsapEvalBillDataMg.setMgPeriod(billDataMg.getMgPeriod());
		gdsapEvalBillDataMg.setMgChargingBasis(billDataMg.getMgChargingBasis() != null ? billDataMg.getMgChargingBasis().getId() : null);
		gdsapEvalBillDataMg.setMgStAmountSelect(billDataMg.getMgStAmountSelect() != null ? billDataMg.getMgStAmountSelect().getId() : null);
		gdsapEvalBillDataMg.setMgStAmount(billDataMg.getMgStAmount());
		gdsapEvalBillDataMg.setMgStUnitPrice(billDataMg.getMgStUnitPrice());
		gdsapEvalBillDataMg.setMgTwInitialUnit(billDataMg.getMgTwInitialUnit());
		gdsapEvalBillDataMg.setMgTwUnits(billDataMg.getMgTwUnits());
		gdsapEvalBillDataMg.setMgTwUnitsSelected(billDataMg.getMgTwUnitsSelected() != null ? billDataMg.getMgTwUnitsSelected().getId() : null);
		gdsapEvalBillDataMg.setMgTwFollowOn(billDataMg.getMgTwFollowOn());
		gdsapEvalBillDataMg.setMgVatAble(billDataMg.getMgVatAble() != null ? billDataMg.getMgVatAble().getCode() : null);
		gdsapEvalBillDataMg.setMgUnusualEnergyUsingable(billDataMg.getMgUnusualEnergyUsingable() != null ? billDataMg.getMgUnusualEnergyUsingable().getCode() : null);
		gdsapEvalBillDataMg.setMgUnusualEnergyUsingableDes(billDataMg.getMgUnusualEnergyUsingableDes());
		return gdsapEvalBillDataMg;
	}
	
	public static BillDataMg toBillDataMg(GdsapEvalBillDataMg gdsapEvalBillDataMg)
	{
		BillDataMg billDataMg = new BillDataMg();
		DictServiceMgr dictServiceMgr = BusinessFactory.getInstance().getService(DictServiceMgr.SERVICE_NAME);
		ReportServiceMgr reportServiceMgr = BusinessFactory.getInstance().getService(ReportServiceMgr.SERVICE_NAME);
		Assert.notNull(dictServiceMgr);
		Assert.notNull(reportServiceMgr);
		
		billDataMg.setMgReliablityLevel(gdsapEvalBillDataMg.getMgReliablityLevel() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataMg.getMgReliablityLevel()) : null);
		billDataMg.setMgGasUsed(gdsapEvalBillDataMg.getMgGasUsed());
		billDataMg.setMgPeriodSelect(gdsapEvalBillDataMg.getMgPeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataMg.getMgPeriodSelect()) : null);
		billDataMg.setMgPeriod(gdsapEvalBillDataMg.getMgPeriod());
		billDataMg.setMgChargingBasis(gdsapEvalBillDataMg.getMgChargingBasis() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataMg.getMgChargingBasis()) : null);
		billDataMg.setMgStAmountSelect(gdsapEvalBillDataMg.getMgStAmountSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataMg.getMgStAmountSelect()) : null);
		billDataMg.setMgStAmount(gdsapEvalBillDataMg.getMgStAmount());
		billDataMg.setMgStUnitPrice(gdsapEvalBillDataMg.getMgStUnitPrice());
		billDataMg.setMgTwInitialUnit(gdsapEvalBillDataMg.getMgTwInitialUnit());
		billDataMg.setMgTwUnits(gdsapEvalBillDataMg.getMgTwUnits());
		billDataMg.setMgTwUnitsSelected(gdsapEvalBillDataMg.getMgTwUnitsSelected() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataMg.getMgTwUnitsSelected()) : null);
		billDataMg.setMgTwFollowOn(gdsapEvalBillDataMg.getMgTwFollowOn());
		billDataMg.setMgVatAble(gdsapEvalBillDataMg.getMgVatAble() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillDataMg.getMgVatAble(), YesNo.class) : null);
		billDataMg.setMgUnusualEnergyUsingable(gdsapEvalBillDataMg.getMgUnusualEnergyUsingable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillDataMg.getMgUnusualEnergyUsingable(), YesNo.class) : null);
		billDataMg.setMgUnusualEnergyUsingableDes(gdsapEvalBillDataMg.getMgUnusualEnergyUsingableDes());
		billDataMg.setReport(reportServiceMgr.getReport(gdsapEvalBillDataMg.getReportId()));
		return billDataMg;
	}
	
	/**
	 * 获取GdsapEvalBillDataComm
	 * @param billDataComm
	 * @return
	 */
	public static GdsapEvalBillDataComm toGdsapEvalBillDataComm(BillDataComm billDataComm)
	{
		GdsapEvalBillDataComm gdsapEvalBillDataComm = new GdsapEvalBillDataComm();
		
		gdsapEvalBillDataComm.setChFuelCode(billDataComm.getChFuelCode());
		gdsapEvalBillDataComm.setChReliablityLevel(billDataComm.getChReliablityLevel() != null ? billDataComm.getChReliablityLevel().getId() : null);
		gdsapEvalBillDataComm.setChEnergyUsed(billDataComm.getChEnergyUsed());
		gdsapEvalBillDataComm.setChPeriodSelect(billDataComm.getChPeriodSelect() != null ? billDataComm.getChPeriodSelect().getId() : null);
		gdsapEvalBillDataComm.setChPeriod(billDataComm.getChPeriod());
		gdsapEvalBillDataComm.setChFixedCost(billDataComm.getChFixedCost());
		gdsapEvalBillDataComm.setChFixedCostSelected(billDataComm.getChFixedCostSelected() != null ? billDataComm.getChFixedCostSelected().getId() : null);
		gdsapEvalBillDataComm.setChUnitPrice(billDataComm.getChUnitPrice());
		gdsapEvalBillDataComm.setVatable(billDataComm.getVatable() != null ? billDataComm.getVatable().getCode() : null);
		gdsapEvalBillDataComm.setReportId(billDataComm.getReport().getId());
		gdsapEvalBillDataComm.setChUnusualEnergyUsingable(billDataComm.getChUnusualEnergyUsingable() != null ? billDataComm.getChUnusualEnergyUsingable().getCode() : null);
		gdsapEvalBillDataComm.setChUnusualEnergyUsingableDes(billDataComm.getChUnusualEnergyUsingableDes());
		
		return gdsapEvalBillDataComm;
	}
	
	public static BillDataComm toBillDataComm(GdsapEvalBillDataComm gdsapEvalBillDataComm)
	{
		DictServiceMgr dictServiceMgr = BusinessFactory.getInstance().getService(DictServiceMgr.SERVICE_NAME);
		ReportServiceMgr reportServiceMgr = BusinessFactory.getInstance().getService(ReportServiceMgr.SERVICE_NAME);
		Assert.notNull(dictServiceMgr);
		Assert.notNull(reportServiceMgr);
		
		BillDataComm billDataComm = new BillDataComm();
		
		billDataComm.setChFuelCode(gdsapEvalBillDataComm.getChFuelCode());
		billDataComm.setChReliablityLevel(gdsapEvalBillDataComm.getChReliablityLevel() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataComm.getChReliablityLevel()) : null);
		billDataComm.setChEnergyUsed(gdsapEvalBillDataComm.getChEnergyUsed());
		billDataComm.setChPeriodSelect(gdsapEvalBillDataComm.getChPeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataComm.getChPeriodSelect()) : null);
		billDataComm.setChPeriod(gdsapEvalBillDataComm.getChPeriod());
		billDataComm.setChFixedCost(gdsapEvalBillDataComm.getChFixedCost());
		billDataComm.setChFixedCostSelected(gdsapEvalBillDataComm.getChFixedCostSelected() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataComm.getChFixedCostSelected()) : null);
		billDataComm.setChUnitPrice(gdsapEvalBillDataComm.getChUnitPrice());
		billDataComm.setVatable(gdsapEvalBillDataComm.getVatable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillDataComm.getVatable(), YesNo.class) : null);
		billDataComm.setReport(reportServiceMgr.getReport(gdsapEvalBillDataComm.getReportId()));
		
		billDataComm.setChUnusualEnergyUsingable(gdsapEvalBillDataComm.getChUnusualEnergyUsingable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillDataComm.getChUnusualEnergyUsingable(), YesNo.class) : null);
		billDataComm.setChUnusualEnergyUsingableDes(gdsapEvalBillDataComm.getChUnusualEnergyUsingableDes());
		
		return billDataComm;
	}
	
	public static GdsapEvalBillDataEle toGdsapEvalBillDataEle(BillDataEle billDataEle)
	{
		GdsapEvalBillDataEle gdsapEvalBillDataEle = new GdsapEvalBillDataEle();
		gdsapEvalBillDataEle.setEtElectricityTariff(billDataEle.getEtElectricityTariff() != null ? billDataEle.getEtElectricityTariff().getId():null);
		gdsapEvalBillDataEle.setEtStReliablityLevel(billDataEle.getEtStReliablityLevel() != null ? billDataEle.getEtStReliablityLevel().getId():null);
		gdsapEvalBillDataEle.setEtStElectricityUsed(billDataEle.getEtStElectricityUsed());
		gdsapEvalBillDataEle.setEtStPeriod(billDataEle.getEtStPeriod());
		gdsapEvalBillDataEle.setEtStPeriodSelect(billDataEle.getEtStPeriodSelect() != null ? billDataEle.getEtStPeriodSelect().getId() : null);
		gdsapEvalBillDataEle.setEtStVatable(billDataEle.getEtStVatable() != null ? billDataEle.getEtStVatable().getCode() : null);
		gdsapEvalBillDataEle.setEtStChargingBasis(billDataEle.getEtStChargingBasis() != null ? billDataEle.getEtStChargingBasis().getId() : null);
		gdsapEvalBillDataEle.setEtStStandingChargeAmount(billDataEle.getEtStStandingChargeAmount());
		gdsapEvalBillDataEle.setEtStStandingChargeAmountSelect(billDataEle.getEtStStandingChargeAmountSelect() != null ? billDataEle.getEtStStandingChargeAmountSelect().getId() : null);
		gdsapEvalBillDataEle.setEtStUnitPrice(billDataEle.getEtStUnitPrice());
		gdsapEvalBillDataEle.setEtStInitialUnitPrice(billDataEle.getEtStInitialUnitPrice());
		gdsapEvalBillDataEle.setEtStUnitsAtThisPrice(billDataEle.getEtStUnitsAtThisPrice());
		gdsapEvalBillDataEle.setEtStUnitsAtThisPriceSelect(billDataEle.getEtStUnitsAtThisPriceSelect() != null ? billDataEle.getEtStUnitsAtThisPriceSelect().getId() : null);
		gdsapEvalBillDataEle.setEtStFollowonUnitPrice(billDataEle.getEtStFollowonUnitPrice());
		gdsapEvalBillDataEle.setEtStUnusualEnergyUsingable(billDataEle.getEtStUnusualEnergyUsingable() != null ? billDataEle.getEtStUnusualEnergyUsingable().getCode() : null);
		gdsapEvalBillDataEle.setEt24ReliablityLevel(billDataEle.getEt24ReliablityLevel() != null ?billDataEle.getEt24ReliablityLevel().getId() : null);
		gdsapEvalBillDataEle.setEt24ElectricityUsed(billDataEle.getEt24ElectricityUsed());
		gdsapEvalBillDataEle.setEt24Period(billDataEle.getEt24Period());
		gdsapEvalBillDataEle.setEt24PeriodSelect(billDataEle.getEt24PeriodSelect() != null ? billDataEle.getEt24PeriodSelect().getId() : null);
		gdsapEvalBillDataEle.setEt24Vatable(billDataEle.getEt24Vatable() != null ? billDataEle.getEt24Vatable().getCode() : null);
		gdsapEvalBillDataEle.setEt24ChargingBasis(billDataEle.getEt24ChargingBasis() != null ? billDataEle.getEt24ChargingBasis().getId() : null);
		gdsapEvalBillDataEle.setEt24StandingChargeAmount(billDataEle.getEt24StandingChargeAmount());
		gdsapEvalBillDataEle.setEt24UnitPrice(billDataEle.getEt24UnitPrice());
		gdsapEvalBillDataEle.setEt24InitialUnitPrice(billDataEle.getEt24InitialUnitPrice());
		gdsapEvalBillDataEle.setEt24UnitsAtThisPrice(billDataEle.getEt24UnitsAtThisPrice());
		gdsapEvalBillDataEle.setEt24StandingChargeAmountSelect(billDataEle.getEt24StandingChargeAmountSelect() != null ? billDataEle.getEt24StandingChargeAmountSelect().getId() : null);
		gdsapEvalBillDataEle.setEt24UnitsAtThisPriceSelect(billDataEle.getEt24UnitsAtThisPriceSelect() != null ? billDataEle.getEt24UnitsAtThisPriceSelect().getId() : null);
		gdsapEvalBillDataEle.setEt24FollowonUnitPrice(billDataEle.getEt24FollowonUnitPrice());
		gdsapEvalBillDataEle.setEt24UnusualEnergyUsingable(billDataEle.getEt24UnusualEnergyUsingable() != null ? billDataEle.getEt24UnusualEnergyUsingable().getCode() : null);
		gdsapEvalBillDataEle.setEtOffHReliablityLevel(billDataEle.getEtOffHReliablityLevel() != null ? billDataEle.getEtOffHReliablityLevel().getId() : null);
		gdsapEvalBillDataEle.setEtOffHElectricityUsed(billDataEle.getEtOffHElectricityUsed());
		gdsapEvalBillDataEle.setEtOffHPeriod(billDataEle.getEtOffHPeriod());
		gdsapEvalBillDataEle.setEtOffHPeriodSelect(billDataEle.getEtOffHPeriodSelect() != null ? billDataEle.getEtOffHPeriodSelect().getId() : null);
		gdsapEvalBillDataEle.setEtOffHVatable(billDataEle.getEtOffHVatable() != null ? billDataEle.getEtOffHVatable().getCode() : null);
		gdsapEvalBillDataEle.setEtOffHChargingBasis(billDataEle.getEtOffHChargingBasis() != null ? billDataEle.getEtOffHChargingBasis().getId() : null);
		gdsapEvalBillDataEle.setEtOffHStandingChargeAmount(billDataEle.getEtOffHStandingChargeAmount());
		gdsapEvalBillDataEle.setEtOffHStandingChargeAmountSelect(billDataEle.getEtOffHStandingChargeAmountSelect() != null ? billDataEle.getEtOffHStandingChargeAmountSelect().getId():null);
		gdsapEvalBillDataEle.setEtOffHUnitPrice(billDataEle.getEtOffHUnitPrice());
		gdsapEvalBillDataEle.setEtOffHInitialUnitAmount(billDataEle.getEtOffHInitialUnitAmount());
		gdsapEvalBillDataEle.setEtOffHUnitsAtThisPrice(billDataEle.getEtOffHUnitsAtThisPrice());
		gdsapEvalBillDataEle.setEtOffHUnitsAtThisPriceSelect(billDataEle.getEtOffHUnitsAtThisPriceSelect() != null ? billDataEle.getEtOffHUnitsAtThisPriceSelect().getId() : null);
		gdsapEvalBillDataEle.setEtOffHFollow(billDataEle.getEtOffHFollow());
		gdsapEvalBillDataEle.setEtOffHUnusualEnergyUsingable(billDataEle.getEtOffHUnusualEnergyUsingable() != null ? billDataEle.getEtOffHUnusualEnergyUsingable().getCode() : null);
		gdsapEvalBillDataEle.setEtOffLReliablityLevel(billDataEle.getEtOffLReliablityLevel() != null ? billDataEle.getEtOffLReliablityLevel().getId() : null);
		gdsapEvalBillDataEle.setEtOffLElectricityUsed(billDataEle.getEtOffLElectricityUsed());
		gdsapEvalBillDataEle.setEtOffLPeriod(billDataEle.getEtOffLPeriod());
		gdsapEvalBillDataEle.setEtOffLPeriodSelect(billDataEle.getEtOffLPeriodSelect() != null ? billDataEle.getEtOffLPeriodSelect().getId() : null);
		gdsapEvalBillDataEle.setEtOffLVatable(billDataEle.getEtOffLVatable() != null ? billDataEle.getEtOffLVatable().getCode() : null);
		gdsapEvalBillDataEle.setEtOffLChargingBasis(billDataEle.getEtOffLChargingBasis() != null ? billDataEle.getEtOffLChargingBasis().getId() : null);
		gdsapEvalBillDataEle.setEtOffLStandingChargeAmount(billDataEle.getEtOffLStandingChargeAmount());
		gdsapEvalBillDataEle.setEtOffLStandingChargeAmountSelect(billDataEle.getEtOffLStandingChargeAmountSelect() != null ? billDataEle.getEtOffLStandingChargeAmountSelect().getId() : null);
		gdsapEvalBillDataEle.setEtOffLUnitPrice(billDataEle.getEtOffLUnitPrice());
		gdsapEvalBillDataEle.setEtOffLInitialUnitAmount(billDataEle.getEtOffLInitialUnitAmount());
		gdsapEvalBillDataEle.setEtOffLUnitsAtThisPrice(billDataEle.getEtOffLUnitsAtThisPrice());
		gdsapEvalBillDataEle.setEtOffLUnitsAtThisPriceSelect(billDataEle.getEtOffLUnitsAtThisPriceSelect() != null ? billDataEle.getEtOffLUnitsAtThisPriceSelect().getId() : null);
		gdsapEvalBillDataEle.setEtOffLFollow(billDataEle.getEtOffLFollow());
		gdsapEvalBillDataEle.setEtOffLUnusualEnergyUsingable(billDataEle.getEtOffLUnusualEnergyUsingable() != null ? billDataEle.getEtOffLUnusualEnergyUsingable().getCode() : null);
		gdsapEvalBillDataEle.setEtPvable(billDataEle.getEtPvable() != null ? billDataEle.getEtPvable().getCode() : null);
		gdsapEvalBillDataEle.setEtPvAmount(billDataEle.getEtPvAmount());
		gdsapEvalBillDataEle.setEtPvPeriod(billDataEle.getEtPvPeriod());
		gdsapEvalBillDataEle.setEtPvPeriodSelect(billDataEle.getEtPvPeriodSelect() != null ? billDataEle.getEtPvPeriodSelect().getId() : null);
		gdsapEvalBillDataEle.setEtWindable(billDataEle.getEtWindable() != null ? billDataEle.getEtWindable().getCode() : null);
		gdsapEvalBillDataEle.setEtWindAmount(billDataEle.getEtWindAmount());
		gdsapEvalBillDataEle.setEtWindPeriod(billDataEle.getEtWindPeriod());
		gdsapEvalBillDataEle.setEtWindPeriodSelect(billDataEle.getEtWindPeriodSelect() != null ? billDataEle.getEtWindPeriodSelect().getId() : null);
		gdsapEvalBillDataEle.setEtMicroable(billDataEle.getEtMicroable() != null ? billDataEle.getEtMicroable().getCode() : null);
		gdsapEvalBillDataEle.setEtMicroableAmount(billDataEle.getEtMicroableAmount());
		gdsapEvalBillDataEle.setEtMicroablePeriod(billDataEle.getEtMicroablePeriod());
		gdsapEvalBillDataEle.setEtMicroablePeriodSelect(billDataEle.getEtMicroablePeriodSelect() != null ? billDataEle.getEtMicroablePeriodSelect().getId() : null);
		gdsapEvalBillDataEle.setEtStUnusualEnergyUsingableDes(billDataEle.getEtStUnusualEnergyUsingableDes());
		gdsapEvalBillDataEle.setEt24UnusualEnergyUsingableDes(billDataEle.getEt24UnusualEnergyUsingableDes());
		gdsapEvalBillDataEle.setEtOffHUnusualEnergyUsingableDes(billDataEle.getEtOffHUnusualEnergyUsingableDes());
		gdsapEvalBillDataEle.setEtOffLUnusualEnergyUsingableDes(billDataEle.getEtOffLUnusualEnergyUsingableDes());
		gdsapEvalBillDataEle.setEtOffType(billDataEle.getOffPeakType() != null ? billDataEle.getOffPeakType().getCode() : null);
		gdsapEvalBillDataEle.setReportId(billDataEle.getId());
		return gdsapEvalBillDataEle;
	}
	
	public static BillDataEle toBillDataEle(GdsapEvalBillDataEle gdsapEvalBillDataEle)
	{
		DictServiceMgr dictServiceMgr = BusinessFactory.getInstance().getService(DictServiceMgr.SERVICE_NAME);
		ReportServiceMgr reportServiceMgr = BusinessFactory.getInstance().getService(ReportServiceMgr.SERVICE_NAME);
		Assert.notNull(dictServiceMgr);
		Assert.notNull(reportServiceMgr);
		
		Report report = reportServiceMgr.getReport(gdsapEvalBillDataEle.getReportId());
		Assert.notNull(report);
		
		BillDataEle billDataEle = new BillDataEle();
		
		billDataEle.setReport(report);
		billDataEle.setEtElectricityTariff(gdsapEvalBillDataEle.getEtElectricityTariff() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEtElectricityTariff()) : null);
		billDataEle.setEtStReliablityLevel(gdsapEvalBillDataEle.getEtStReliablityLevel() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEtStReliablityLevel()) : null);
		billDataEle.setEtStElectricityUsed(gdsapEvalBillDataEle.getEtStElectricityUsed());
		billDataEle.setEtStPeriod(gdsapEvalBillDataEle.getEtStPeriod());
		billDataEle.setEtStPeriodSelect(gdsapEvalBillDataEle.getEtStPeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEtStPeriodSelect()) : null);
		billDataEle.setEtStVatable(gdsapEvalBillDataEle.getEtStVatable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillDataEle.getEtStVatable(), YesNo.class) : null);
		billDataEle.setEtStChargingBasis(gdsapEvalBillDataEle.getEtStChargingBasis() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEtStChargingBasis()) : null);
		billDataEle.setEtStStandingChargeAmount(gdsapEvalBillDataEle.getEtStStandingChargeAmount());
		billDataEle.setEtStStandingChargeAmountSelect(gdsapEvalBillDataEle.getEtStStandingChargeAmountSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEtStStandingChargeAmountSelect()) : null);
		billDataEle.setEtStUnitPrice(gdsapEvalBillDataEle.getEtStUnitPrice());
		billDataEle.setEtStInitialUnitPrice(gdsapEvalBillDataEle.getEtStInitialUnitPrice());
		billDataEle.setEtStUnitsAtThisPrice(gdsapEvalBillDataEle.getEtStUnitsAtThisPrice());
		billDataEle.setEtStUnitsAtThisPriceSelect(gdsapEvalBillDataEle.getEtStUnitsAtThisPriceSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEtStUnitsAtThisPriceSelect()) : null);
		billDataEle.setEtStFollowonUnitPrice(gdsapEvalBillDataEle.getEtStFollowonUnitPrice());
		billDataEle.setEtStUnusualEnergyUsingable(gdsapEvalBillDataEle.getEtStUnusualEnergyUsingable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillDataEle.getEtStUnusualEnergyUsingable(), YesNo.class) : null);
		billDataEle.setEtStUnusualEnergyUsingableDes(gdsapEvalBillDataEle.getEtStUnusualEnergyUsingableDes());
		billDataEle.setEt24ReliablityLevel(gdsapEvalBillDataEle.getEt24ReliablityLevel() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEt24ReliablityLevel()) : null);
		billDataEle.setEt24ElectricityUsed(gdsapEvalBillDataEle.getEt24ElectricityUsed());
		billDataEle.setEt24Period(gdsapEvalBillDataEle.getEt24Period());
		billDataEle.setEt24PeriodSelect(gdsapEvalBillDataEle.getEt24PeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEt24PeriodSelect()) : null);
		billDataEle.setEt24Vatable(gdsapEvalBillDataEle.getEt24Vatable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillDataEle.getEt24Vatable(), YesNo.class) : null);
		billDataEle.setEt24ChargingBasis(gdsapEvalBillDataEle.getEt24ChargingBasis() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEt24ChargingBasis()) : null);
		billDataEle.setEt24StandingChargeAmount(gdsapEvalBillDataEle.getEt24StandingChargeAmount());
		billDataEle.setEt24StandingChargeAmountSelect(gdsapEvalBillDataEle.getEt24StandingChargeAmountSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEt24StandingChargeAmountSelect()) : null);
		billDataEle.setEt24UnitPrice(gdsapEvalBillDataEle.getEt24UnitPrice());
		billDataEle.setEt24InitialUnitPrice(gdsapEvalBillDataEle.getEt24InitialUnitPrice());
		billDataEle.setEt24UnitsAtThisPrice(gdsapEvalBillDataEle.getEt24UnitsAtThisPrice());
		billDataEle.setEt24UnitsAtThisPriceSelect(gdsapEvalBillDataEle.getEt24UnitsAtThisPriceSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEt24UnitsAtThisPriceSelect()) : null);
		billDataEle.setEt24FollowonUnitPrice(gdsapEvalBillDataEle.getEt24FollowonUnitPrice());
		billDataEle.setEt24UnusualEnergyUsingable(gdsapEvalBillDataEle.getEt24UnusualEnergyUsingable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillDataEle.getEt24UnusualEnergyUsingable(), YesNo.class) : null);
		billDataEle.setEt24UnusualEnergyUsingableDes(gdsapEvalBillDataEle.getEt24UnusualEnergyUsingableDes());
		
		billDataEle.setEtOffHReliablityLevel(gdsapEvalBillDataEle.getEtOffHReliablityLevel() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEtOffHReliablityLevel()) : null);
		billDataEle.setEtOffHElectricityUsed(gdsapEvalBillDataEle.getEtOffHElectricityUsed());
		billDataEle.setEtOffHPeriod(gdsapEvalBillDataEle.getEtOffHPeriod());
		billDataEle.setEtOffHPeriodSelect(gdsapEvalBillDataEle.getEtOffHPeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEtOffHPeriodSelect()) : null);
		billDataEle.setEtOffHVatable(gdsapEvalBillDataEle.getEtOffHVatable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillDataEle.getEtOffHVatable(), YesNo.class) : null);
		billDataEle.setEtOffHChargingBasis(gdsapEvalBillDataEle.getEtOffHChargingBasis() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEtOffHChargingBasis()) : null);
		billDataEle.setEtOffHStandingChargeAmount(gdsapEvalBillDataEle.getEtOffHStandingChargeAmount());
		billDataEle.setEtOffHStandingChargeAmountSelect(gdsapEvalBillDataEle.getEtOffHStandingChargeAmountSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEtOffHStandingChargeAmountSelect()) : null);
		billDataEle.setEtOffHUnitPrice(gdsapEvalBillDataEle.getEtOffHUnitPrice());
		billDataEle.setEtOffHInitialUnitAmount(gdsapEvalBillDataEle.getEtOffHInitialUnitAmount());
		billDataEle.setEtOffHUnitsAtThisPrice(gdsapEvalBillDataEle.getEtOffHUnitsAtThisPrice());
		billDataEle.setEtOffHUnitsAtThisPriceSelect(gdsapEvalBillDataEle.getEtOffHUnitsAtThisPriceSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEtOffHUnitsAtThisPriceSelect()) : null);
		billDataEle.setEtOffHFollow(gdsapEvalBillDataEle.getEtOffHFollow());
		billDataEle.setEtOffHUnusualEnergyUsingable(gdsapEvalBillDataEle.getEtOffHUnusualEnergyUsingable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillDataEle.getEtOffHUnusualEnergyUsingable(), YesNo.class) : null);
		billDataEle.setEtOffHUnusualEnergyUsingableDes(gdsapEvalBillDataEle.getEtOffHUnusualEnergyUsingableDes());
		
		billDataEle.setEtOffLReliablityLevel(gdsapEvalBillDataEle.getEtOffLReliablityLevel() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEtOffLReliablityLevel()) : null);
		billDataEle.setEtOffLElectricityUsed(gdsapEvalBillDataEle.getEtOffLElectricityUsed());
		billDataEle.setEtOffLPeriod(gdsapEvalBillDataEle.getEtOffLPeriod());
		billDataEle.setEtOffLPeriodSelect(gdsapEvalBillDataEle.getEtOffLPeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEtOffLPeriodSelect()) : null);
		billDataEle.setEtOffLVatable(gdsapEvalBillDataEle.getEtOffLVatable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillDataEle.getEtOffLVatable(), YesNo.class) : null);
		billDataEle.setEtOffLChargingBasis(gdsapEvalBillDataEle.getEtOffLChargingBasis() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEtOffLChargingBasis()) : null);
		billDataEle.setEtOffLStandingChargeAmount(gdsapEvalBillDataEle.getEtOffLStandingChargeAmount());
		billDataEle.setEtOffLStandingChargeAmountSelect(gdsapEvalBillDataEle.getEtOffLStandingChargeAmountSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEtOffLStandingChargeAmountSelect()) : null);
		billDataEle.setEtOffLUnitPrice(gdsapEvalBillDataEle.getEtOffLUnitPrice());
		billDataEle.setEtOffLInitialUnitAmount(gdsapEvalBillDataEle.getEtOffLInitialUnitAmount());
		billDataEle.setEtOffLUnitsAtThisPrice(gdsapEvalBillDataEle.getEtOffLUnitsAtThisPrice());
		billDataEle.setEtOffLUnitsAtThisPriceSelect(gdsapEvalBillDataEle.getEtOffLUnitsAtThisPriceSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEtOffLUnitsAtThisPriceSelect()) : null);
		billDataEle.setEtOffLFollow(gdsapEvalBillDataEle.getEtOffLFollow());
		billDataEle.setEtOffLUnusualEnergyUsingable(gdsapEvalBillDataEle.getEtOffLUnusualEnergyUsingable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillDataEle.getEtOffLUnusualEnergyUsingable(), YesNo.class) : null);
		billDataEle.setEtOffLUnusualEnergyUsingableDes(gdsapEvalBillDataEle.getEtOffLUnusualEnergyUsingableDes());
		
		billDataEle.setEtPvable(gdsapEvalBillDataEle.getEtPvable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillDataEle.getEtPvable(), YesNo.class) : null);
		billDataEle.setEtPvAmount(gdsapEvalBillDataEle.getEtPvAmount());
		billDataEle.setEtPvPeriod(gdsapEvalBillDataEle.getEtPvPeriod());
		billDataEle.setEtPvPeriodSelect(gdsapEvalBillDataEle.getEtPvPeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEtPvPeriodSelect()) : null);
		billDataEle.setEtWindable(gdsapEvalBillDataEle.getEtWindable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillDataEle.getEtWindable(),YesNo.class) : null);
		billDataEle.setEtWindAmount(gdsapEvalBillDataEle.getEtWindAmount());
		billDataEle.setEtWindPeriod(gdsapEvalBillDataEle.getEtWindPeriod());
		billDataEle.setEtWindPeriodSelect(gdsapEvalBillDataEle.getEtWindPeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEtWindPeriodSelect()) : null);
		billDataEle.setEtMicroable(gdsapEvalBillDataEle.getEtMicroable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillDataEle.getEtMicroable(), YesNo.class) : null);
		billDataEle.setEtMicroableAmount(gdsapEvalBillDataEle.getEtMicroableAmount());
		billDataEle.setEtMicroablePeriod(gdsapEvalBillDataEle.getEtMicroablePeriod());
		billDataEle.setEtMicroablePeriodSelect(gdsapEvalBillDataEle.getEtMicroablePeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillDataEle.getEtMicroablePeriodSelect()) : null);
		billDataEle.setOffPeakType(gdsapEvalBillDataEle.getEtOffType() != null ? (OffPeakType)EnumUtils.getByCode(gdsapEvalBillDataEle.getEtOffType(), OffPeakType.class) : null);

		return billDataEle;
	}
	
	/**
	 * @param gdsapEvalSolutionIssue
	 * @return
	 */
	public static SolutionIssue toSolutionIssue(GdsapEvalSolutionIssue gdsapEvalSolutionIssue)
	{
		SolutionIssue issue = new SolutionIssue();
		issue.setStoneWalls((YesNo)EnumUtils.getByCode(gdsapEvalSolutionIssue.getStoneWalls(), YesNo.class));
		issue.setSystemBuild((YesNo)EnumUtils.getByCode(gdsapEvalSolutionIssue.getSystemBuild(), YesNo.class));
		issue.setAccessIssues((YesNo)EnumUtils.getByCode(gdsapEvalSolutionIssue.getAccessIssues(), YesNo.class));
		issue.setHighExposure((YesNo)EnumUtils.getByCode(gdsapEvalSolutionIssue.getHighExposure(), YesNo.class));
		issue.setNarrowCavities((YesNo)EnumUtils.getByCode(gdsapEvalSolutionIssue.getNarrowCavities(), YesNo.class));
		issue.setCavityFillUnknown((YesNo)EnumUtils.getByCode(gdsapEvalSolutionIssue.getCavityFillUnknown(), YesNo.class));
		issue.setSolidWallInsulationUnknown((YesNo)EnumUtils.getByCode(gdsapEvalSolutionIssue.getSolidWallInsulationUnknown(), YesNo.class));
		issue.setNoLoftAccess((YesNo)EnumUtils.getByCode(gdsapEvalSolutionIssue.getNoLoftAccess(), YesNo.class));
		issue.setFlatRoofInsulationUnknown((YesNo)EnumUtils.getByCode(gdsapEvalSolutionIssue.getFlatRoofInsulationUnknown(), YesNo.class));
		issue.setRoofRoomInsulationUnknown((YesNo)EnumUtils.getByCode(gdsapEvalSolutionIssue.getRoofRoomInsulationUnknown(), YesNo.class));
		issue.setFloorInsulationUnknown((YesNo)EnumUtils.getByCode(gdsapEvalSolutionIssue.getFloorInsulationUnknown(), YesNo.class));
		issue.setNoCylinderAccess((YesNo)EnumUtils.getByCode(gdsapEvalSolutionIssue.getNoCylinderAccess(), YesNo.class));
		issue.setMainsGasNeeded((YesNo)EnumUtils.getByCode(gdsapEvalSolutionIssue.getMainsGasNeeded(), YesNo.class));
		
		SolutionServiceMgr serviceMgr = (SolutionServiceMgr)BusinessFactory.getInstance().getService(SolutionServiceMgr.SERVICE_NAME);
		Solution solution = serviceMgr.getSolution(gdsapEvalSolutionIssue.getSolutionId());
		issue.setSolution(solution);
		return issue;
	}
	
	/**
	 * @param solutionIssue
	 * @return
	 */
	public static GdsapEvalSolutionIssue toGdsapEvalSolutionIssue(SolutionIssue issue)
	{
		GdsapEvalSolutionIssue gdsapEvalSolutionIssue = new GdsapEvalSolutionIssue();
		gdsapEvalSolutionIssue.setStoneWalls(issue.getStoneWalls().getCode());
		gdsapEvalSolutionIssue.setSystemBuild(issue.getSystemBuild().getCode());
		gdsapEvalSolutionIssue.setAccessIssues(issue.getAccessIssues().getCode());
		gdsapEvalSolutionIssue.setHighExposure(issue.getHighExposure().getCode());
		gdsapEvalSolutionIssue.setNarrowCavities(issue.getNarrowCavities().getCode());
		gdsapEvalSolutionIssue.setCavityFillUnknown(issue.getCavityFillUnknown().getCode());
		gdsapEvalSolutionIssue.setSolidWallInsulationUnknown(issue.getSolidWallInsulationUnknown().getCode());
		gdsapEvalSolutionIssue.setNoLoftAccess(issue.getNoLoftAccess().getCode());
		gdsapEvalSolutionIssue.setFlatRoofInsulationUnknown(issue.getFlatRoofInsulationUnknown().getCode());
		gdsapEvalSolutionIssue.setRoofRoomInsulationUnknown(issue.getRoofRoomInsulationUnknown().getCode());
		gdsapEvalSolutionIssue.setFloorInsulationUnknown(issue.getFloorInsulationUnknown().getCode());
		gdsapEvalSolutionIssue.setNoCylinderAccess(issue.getNoCylinderAccess().getCode());
		gdsapEvalSolutionIssue.setMainsGasNeeded(issue.getMainsGasNeeded().getCode());
		gdsapEvalSolutionIssue.setSolutionId(issue.getSolution().getId());
		return gdsapEvalSolutionIssue;
	}
	
	/**
	 * GdsapEvalBillDataLpg-->BillDataLPG
	 * @param gdsapEvalBillDataLpg
	 * @return
	 */
	public static BillDataLPG getBillDataLPG(GdsapEvalBillDataLpg gdsapEvalBillDataLpg)
	{
		BillDataLPG billDataLPG = new BillDataLPG();
		billDataLPG.setLpgGasUsed(gdsapEvalBillDataLpg.getLpgGasUsed());
		billDataLPG.setLpgPeriod(gdsapEvalBillDataLpg.getLpgPeriod());
		billDataLPG.setLpgStAmount(gdsapEvalBillDataLpg.getLpgStAmount());
		billDataLPG.setLpgStUnitPrice(gdsapEvalBillDataLpg.getLpgStUnitPrice());
		billDataLPG.setLpgTwInitialUnit(gdsapEvalBillDataLpg.getLpgTwInitialUnit());
		billDataLPG.setLpgTwUnits(gdsapEvalBillDataLpg.getLpgTwUnits());
		billDataLPG.setLpgTwFollowOn(gdsapEvalBillDataLpg.getLpgTwFollowOn());
		billDataLPG.setLpgVatAble(gdsapEvalBillDataLpg.getLpgVatAble() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillDataLpg.getLpgVatAble(), YesNo.class) : null);
		billDataLPG.setLpgUnusualEnergyUsingable(gdsapEvalBillDataLpg.getLpgUnusualEnergyUsingable() != null ? (YesNo)EnumUtils.getByCode(gdsapEvalBillDataLpg.getLpgUnusualEnergyUsingable(), YesNo.class) : null);
		billDataLPG.setLpgUnusualEnergyUsingableDes(gdsapEvalBillDataLpg.getLpgUnusualEnergyUsingableDes());

		return billDataLPG;
	}
	
	/**
	 * BillDataLPG-->GdsapEvalBillDataLpg
	 * @param billDataLPG
	 * @return
	 */
	public static GdsapEvalBillDataLpg getGdsapEvalBillDataLpg(BillDataLPG billDataLPG)
	{
		GdsapEvalBillDataLpg gdsapEvalBillDataLpg = new GdsapEvalBillDataLpg();
		gdsapEvalBillDataLpg.setReportId(billDataLPG.getReport().getId());
		gdsapEvalBillDataLpg.setLpgReliablityLevel(billDataLPG.getLpgReliablityLevel() != null ? billDataLPG.getLpgReliablityLevel().getId() : null);
		gdsapEvalBillDataLpg.setLpgGasUsed(billDataLPG.getLpgGasUsed());
		gdsapEvalBillDataLpg.setLpgPeriodSelect(billDataLPG.getLpgPeriodSelect() != null ? billDataLPG.getLpgPeriodSelect().getId() : null);
		gdsapEvalBillDataLpg.setLpgPeriod(billDataLPG.getLpgPeriod());
		gdsapEvalBillDataLpg.setLpgChargingBasis(billDataLPG.getLpgChargingBasis() != null ? billDataLPG.getLpgChargingBasis().getId() : null);
		gdsapEvalBillDataLpg.setLpgStAmountSelect(billDataLPG.getLpgStAmountSelect() != null ? billDataLPG.getLpgStAmountSelect().getId() : null);
		gdsapEvalBillDataLpg.setLpgStAmount(billDataLPG.getLpgStAmount());
		gdsapEvalBillDataLpg.setLpgStUnitPrice(billDataLPG.getLpgStUnitPrice());
		gdsapEvalBillDataLpg.setLpgTwInitialUnit(billDataLPG.getLpgTwInitialUnit());
		gdsapEvalBillDataLpg.setLpgTwUnits(billDataLPG.getLpgTwUnits());
		gdsapEvalBillDataLpg.setLpgTwUnitsSelected(billDataLPG.getLpgTwUnitsSelected() != null ? billDataLPG.getLpgTwUnitsSelected().getId() : null);
		gdsapEvalBillDataLpg.setLpgTwFollowOn(billDataLPG.getLpgTwFollowOn());
		gdsapEvalBillDataLpg.setLpgVatAble(billDataLPG.getLpgVatAble() != null ? billDataLPG.getLpgVatAble().getCode() : null);
		gdsapEvalBillDataLpg.setLpgUnusualEnergyUsingable(billDataLPG.getLpgUnusualEnergyUsingable() != null ? billDataLPG.getLpgUnusualEnergyUsingable().getCode() : null);
		gdsapEvalBillDataLpg.setLpgUnusualEnergyUsingableDes(billDataLPG.getLpgUnusualEnergyUsingableDes());
		return gdsapEvalBillDataLpg;
	}
}
