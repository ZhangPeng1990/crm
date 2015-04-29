/**
 * 
 */
package uk.co.quidos.gdsap.evaluation;

import java.util.Date;

import pw.itcircle.javaBeanFactory.tools.StringUtil;

import uk.co.quidos.gdsap.evaluation.enums.Language;
import uk.co.quidos.gdsap.evaluation.enums.PropertyType;
import uk.co.quidos.gdsap.evaluation.enums.ReportFileStatus;
import uk.co.quidos.gdsap.evaluation.enums.ReportLocation;
import uk.co.quidos.gdsap.evaluation.enums.ReportStatus;
import uk.co.quidos.gdsap.evaluation.enums.ReportType;
import uk.co.quidos.gdsap.evaluation.enums.TransactionType;
import uk.co.quidos.gdsap.evaluation.utils.RRNBuilder;
import uk.co.quidos.gdsap.framework.enums.EpcVersion;
import uk.co.quidos.gdsap.framework.enums.UploadWay;
import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;
import uk.co.quidos.gdsap.framework.user.User;
import uk.co.quidos.gdsap.framework.webservice.ServiceRequest;

/**
 * @author peng.shi
 *
 */
public class Report extends AbsBusinessObject
{
	private static final long serialVersionUID = 2383991438301732836L;

	private Long id;
    private String rrn;
    private String oaRrn;
    private String gdipRrn;
    private Date inspectionDate;
    private ReportType reportType;
    private Date completionDate;
    private Date registrationDate;
    private ReportFileStatus reportXmlStatus;
    private Language languageCode;
    private TransactionType transactionType;
    private String eaFullName;
    private String eaCertificateName;
    private String address1;
    private String address2;
    private String address3;
    private String postcode;
    private String posttown;
    private String uprn;
    private RelatedPartyDisclosure relatedPartyDisclosure;
    private User user;
    private Date insertTime;
    private Date updateTime;
    private String reportXmlFile;
    private String oldReportCalXmlFile;
    private PropertyType propertyType;
    private Integer rdsapMhsFuel;
    private Integer rdsapSmhsFuel;
    private Integer rdsapShsFuel;
    private Integer rdsapWhsFuel;
    private Integer habitableRoomCount;
    private YesNo rdsapMainGasAvailable;
    private ReportLocation reportLocation;
    private YesNo unoccupiedPropertyable;
    private Date lodgeDate;
    private Date gdipLodgeDate;
    private UploadWay uploadWay;
    private EpcVersion epcVersion;
    
    private ReportStatus reportStatus;
    
    private Company company;
	private String companyJobRef;
	
	//准备计算空置房产默认值时设为true
	private boolean prepareCalunoccupiedProperty = false;
	private boolean builderOaRrn = true;
	
	//第三方公司通过web service直接lodge的report,要求不出现在系统中，只做记录
	private String lodgedGdipRrn;
    private String requestXmlFile;
    private String ligXmlFile;
    private String lodgedGdipLigXmlFile;
    
    private Double dValue;// 为了统计Qube-228系列问题临时添加的，可以删除
    
    public Report()
    {}
    
    public Report(ServiceRequest request)
    {
    	this.rrn = request.getEpcRrn();
    	this.oaRrn = request.getOaRrn();
    	this.gdipRrn = request.getGdipRrn();
    	this.uploadWay = UploadWay.Third_Party_Web_Service_pushGDIPToQuidos_AndLodge;
    	this.insertTime = new Date();
    	String gdipRRn = RRNBuilder.buildDRrn(RRNBuilder.DReportType.GDIP_TYPE, null, new Date());
    	this.lodgedGdipRrn = gdipRRn;
    	this.rdsapMainGasAvailable = YesNo.No;
    }
    
	public ReportStatus getReportStatus()
	{
		return reportStatus;
	}
	public void setReportStatus(ReportStatus reportStatus)
	{
		this.reportStatus = reportStatus;
	}
	public YesNo getUnoccupiedPropertyable()
	{
		return unoccupiedPropertyable;
	}
	public void setUnoccupiedPropertyable(YesNo unoccupiedPropertyable)
	{
		this.unoccupiedPropertyable = unoccupiedPropertyable;
	}
	public ReportLocation getReportLocation()
	{
		return reportLocation;
	}
	public void setReportLocation(ReportLocation reportLocation)
	{
		this.reportLocation = reportLocation;
	}
	public String getOaRrn()
	{
		return oaRrn;
	}
	public void setOaRrn(String oaRrn)
	{
		this.oaRrn = oaRrn;
	}
	public String getOldReportCalXmlFile()
	{
		return oldReportCalXmlFile;
	}
	public void setOldReportCalXmlFile(String oldReportCalXmlFile)
	{
		this.oldReportCalXmlFile = oldReportCalXmlFile;
	}
	public YesNo getRdsapMainGasAvailable()
	{
		return rdsapMainGasAvailable;
	}
	public void setRdsapMainGasAvailable(YesNo rdsapMainGasAvailable)
	{
		this.rdsapMainGasAvailable = rdsapMainGasAvailable;
	}
	public Integer getRdsapMhsFuel()
	{
		return rdsapMhsFuel;
	}
	public Integer getRdsapSmhsFuel()
	{
		return rdsapSmhsFuel;
	}
	public Integer getRdsapShsFuel()
	{
		return rdsapShsFuel;
	}
	public Integer getRdsapWhsFuel()
	{
		return rdsapWhsFuel;
	}
	public void setRdsapMhsFuel(Integer rdsapMhsFuel)
	{
		this.rdsapMhsFuel = rdsapMhsFuel;
	}
	public void setRdsapSmhsFuel(Integer rdsapSmhsFuel)
	{
		this.rdsapSmhsFuel = rdsapSmhsFuel;
	}
	public void setRdsapShsFuel(Integer rdsapShsFuel)
	{
		this.rdsapShsFuel = rdsapShsFuel;
	}
	public void setRdsapWhsFuel(Integer rdsapWhsFuel)
	{
		this.rdsapWhsFuel = rdsapWhsFuel;
	}
	public PropertyType getPropertyType()
	{
		return propertyType;
	}
	public void setPropertyType(PropertyType propertyType)
	{
		this.propertyType = propertyType;
	}
	public Long getId()
	{
		return id;
	}
	public String getRrn()
	{
		return rrn;
	}
	public Date getInspectionDate()
	{
		return inspectionDate;
	}
	public ReportType getReportType()
	{
		return reportType;
	}
	public Date getCompletionDate()
	{
		return completionDate;
	}
	public Date getRegistrationDate()
	{
		return registrationDate;
	}
	public TransactionType getTransactionType()
	{
		return transactionType;
	}
	public String getEaFullName()
	{
		return eaFullName;
	}
	public String getEaCertificateName()
	{
		return eaCertificateName;
	}
	public String getAddress1()
	{
		if(StringUtil.haveContent(address1))
		{
			return address1;
		}
		return null;
	}
	public String getAddress2()
	{
		if(StringUtil.haveContent(address2))
		{
			return address2;
		}
		return null;
	}
	public String getAddress3()
	{
		if(StringUtil.haveContent(address3))
		{
			return address3;
		}
		return null;
	}
	public String getPostcode()
	{
		if(StringUtil.haveContent(postcode))
		{
			return postcode;
		}
		return null;
	}
	public String getPosttown()
	{
		if(StringUtil.haveContent(posttown))
		{
			return posttown;
		}
		return null;
	}
	public String getUprn()
	{
		return uprn;
	}
	public RelatedPartyDisclosure getRelatedPartyDisclosure()
	{
		return relatedPartyDisclosure;
	}
	public User getUser()
	{
		return user;
	}
	public Date getInsertTime()
	{
		return insertTime;
	}
	public Date getUpdateTime()
	{
		return updateTime;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public void setRrn(String rrn)
	{
		this.rrn = rrn;
	}
	public void setInspectionDate(Date inspectionDate)
	{
		this.inspectionDate = inspectionDate;
	}
	public void setReportType(ReportType reportType)
	{
		this.reportType = reportType;
	}
	public void setCompletionDate(Date completionDate)
	{
		this.completionDate = completionDate;
	}
	public void setRegistrationDate(Date registrationDate)
	{
		this.registrationDate = registrationDate;
	}
	public void setTransactionType(TransactionType transactionType)
	{
		this.transactionType = transactionType;
	}
	public void setEaFullName(String eaFullName)
	{
		this.eaFullName = eaFullName;
	}
	public void setEaCertificateName(String eaCertificateName)
	{
		this.eaCertificateName = eaCertificateName;
	}
	public void setAddress1(String address1)
	{
		this.address1 = address1;
	}
	public void setAddress2(String address2)
	{
		this.address2 = address2;
	}
	public void setAddress3(String address3)
	{
		this.address3 = address3;
	}
	public void setPostcode(String postcode)
	{
		this.postcode = postcode;
	}
	public void setPosttown(String posttown)
	{
		this.posttown = posttown;
	}
	public void setUprn(String uprn)
	{
		this.uprn = uprn;
	}
	public void setRelatedPartyDisclosure(RelatedPartyDisclosure relatedPartyDisclosure)
	{
		this.relatedPartyDisclosure = relatedPartyDisclosure;
	}
	public void setUser(User user)
	{
		this.user = user;
	}
	public void setInsertTime(Date insertTime)
	{
		this.insertTime = insertTime;
	}
	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}
	public ReportFileStatus getReportXmlStatus() {
		return reportXmlStatus;
	}
	public void setReportXmlStatus(ReportFileStatus reportXmlStatus) {
		this.reportXmlStatus = reportXmlStatus;
	}
	public String getReportXmlFile()
	{
		return reportXmlFile;
	}
	public void setReportXmlFile(String reportXmlFile)
	{
		this.reportXmlFile = reportXmlFile;
	}
	public Language getLanguageCode()
	{
		return languageCode;
	}
	public void setLanguageCode(Language languageCode)
	{
		this.languageCode = languageCode;
	}
	public Date getLodgeDate() {
		return lodgeDate;
	}
	public void setLodgeDate(Date lodgeDate) {
		this.lodgeDate = lodgeDate;
	}
	public UploadWay getUploadWay() {
		return uploadWay;
	}
	public void setUploadWay(UploadWay uploadWay) {
		this.uploadWay = uploadWay;
	}
	public EpcVersion getEpcVersion() {
		return epcVersion;
	}
	public void setEpcVersion(EpcVersion epcVersion) {
		this.epcVersion = epcVersion;
	}
	public Date getGdipLodgeDate() {
		return gdipLodgeDate;
	}
	public void setGdipLodgeDate(Date gdipLodgeDate) {
		this.gdipLodgeDate = gdipLodgeDate;
	}
	public String getGdipRrn() {
		return gdipRrn;
	}
	public void setGdipRrn(String gdipRrn) {
		this.gdipRrn = gdipRrn;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getCompanyJobRef() {
		return companyJobRef;
	}
	public void setCompanyJobRef(String companyJobRef) {
		this.companyJobRef = companyJobRef;
	}
	public Integer getHabitableRoomCount() {
		return habitableRoomCount;
	}
	public void setHabitableRoomCount(Integer habitableRoomCount) {
		this.habitableRoomCount = habitableRoomCount;
	}
	public boolean isPrepareCalunoccupiedProperty() {
		return prepareCalunoccupiedProperty;
	}
	public void setPrepareCalunoccupiedProperty(boolean prepareCalunoccupiedProperty) {
		this.prepareCalunoccupiedProperty = prepareCalunoccupiedProperty;
	}
	public boolean isBuilderOaRrn() {
		return builderOaRrn;
	}
	public void setBuilderOaRrn(boolean builderOaRrn) {
		this.builderOaRrn = builderOaRrn;
	}

	public String getLodgedGdipRrn() {
		return lodgedGdipRrn;
	}

	public void setLodgedGdipRrn(String lodgedGdipRrn) {
		this.lodgedGdipRrn = lodgedGdipRrn;
	}

	public String getRequestXmlFile() {
		return requestXmlFile;
	}

	public void setRequestXmlFile(String requestXmlFile) {
		this.requestXmlFile = requestXmlFile;
	}

	public String getLigXmlFile() {
		return ligXmlFile;
	}

	public void setLigXmlFile(String ligXmlFile) {
		this.ligXmlFile = ligXmlFile;
	}

	public String getLodgedGdipLigXmlFile() {
		return lodgedGdipLigXmlFile;
	}

	public void setLodgedGdipLigXmlFile(String lodgedGdipLigXmlFile) {
		this.lodgedGdipLigXmlFile = lodgedGdipLigXmlFile;
	}

	public Double getdValue() {
		return dValue;
	}

	public void setdValue(Double dValue) {
		this.dValue = dValue;
	}
}
