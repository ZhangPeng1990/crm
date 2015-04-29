package uk.co.quidos.gdsap.evaluation.persistence.object;

import java.util.Date;

import uk.co.quidos.dal.object.AbstractDO;

/**
 * @author peng.shi
 */
public class GdsapEvalReport extends AbstractDO {

	private static final long serialVersionUID = 3787561438666822039L;

	private Long id;

    private String rrn;
    
    private String oaRrn;
    
    private String gdipRrn;
    
    private Date inspectionDate;

    private Integer reportType;

    private Date completionDate;

    private Date registrationDate;

    private String reportXmlStatus;

    private String languageCode;

    private Integer transactionType;

    private String eaFullName;

    private String eaCertificateName;

    private String address1;

    private String address2;

    private String address3;

    private String postcode;

    private String posttown;

    private String uprn;

    private Integer relatedPartyDisclosure;

    private Long userId;

    private Date insertTime;

    private Date updateTime;
    
    private String reportXmlFile;
    
    private String oldReportCalXmlFile;
    
    private Integer propertyType;
    
    private Integer rdsapMhsFuel;
    private Integer rdsapSmhsFuel;
    private Integer rdsapShsFuel;
    private Integer rdsapWhsFuel;
    private Integer habitableRoomCount;
    private Integer rdsapMainGasAvailable;
    private String reportLocation;
    private Integer unoccupiedPropertyable;
    private Date lodgeDate;
    private Date gdipLodgeDate;
    private Integer reportStatus;
    private Integer uploadWay;
    private Integer epcVersion;
    private String companyId;
	private String companyJobRef;
	private String lodgedGdipRrn;
    private String requestXmlFile;
    private String gdipLigXmlFile;
    private String lodgedGdipLigXmlFile;
    
	public Integer getReportStatus()
	{
		return reportStatus;
	}

	public void setReportStatus(Integer reportStatus)
	{
		this.reportStatus = reportStatus;
	}

	public Integer getUnoccupiedPropertyable()
	{
		return unoccupiedPropertyable;
	}

	public void setUnoccupiedPropertyable(Integer unoccupiedPropertyable)
	{
		this.unoccupiedPropertyable = unoccupiedPropertyable;
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

	public Integer getRdsapMainGasAvailable()
	{
		return rdsapMainGasAvailable;
	}

	public void setRdsapMainGasAvailable(Integer rdsapMainGasAvailable)
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

	public Integer getPropertyType()
	{
		return propertyType;
	}

	public void setPropertyType(Integer propertyType)
	{
		this.propertyType = propertyType;
	}

	public String getReportXmlFile()
	{
		return reportXmlFile;
	}

	public void setReportXmlFile(String reportXmlFile)
	{
		this.reportXmlFile = reportXmlFile;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getReportXmlStatus() {
        return reportXmlStatus;
    }

    public void setReportXmlStatus(String reportXmlStatus) {
        this.reportXmlStatus = reportXmlStatus;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public String getEaFullName() {
        return eaFullName;
    }

    public void setEaFullName(String eaFullName) {
        this.eaFullName = eaFullName;
    }

    public String getEaCertificateName() {
        return eaCertificateName;
    }

    public void setEaCertificateName(String eaCertificateName) {
        this.eaCertificateName = eaCertificateName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPosttown() {
        return posttown;
    }

    public void setPosttown(String posttown) {
        this.posttown = posttown;
    }

    public String getUprn() {
        return uprn;
    }

    public void setUprn(String uprn) {
        this.uprn = uprn;
    }

    public Integer getRelatedPartyDisclosure() {
        return relatedPartyDisclosure;
    }

    public void setRelatedPartyDisclosure(Integer relatedPartyDisclosure) {
        this.relatedPartyDisclosure = relatedPartyDisclosure;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getReportLocation() {
		return reportLocation;
	}

	public void setReportLocation(String reportLocation) {
		this.reportLocation = reportLocation;
	}

	public Date getLodgeDate() {
		return lodgeDate;
	}

	public void setLodgeDate(Date lodgeDate) {
		this.lodgeDate = lodgeDate;
	}

	public Integer getUploadWay() {
		return uploadWay;
	}

	public void setUploadWay(Integer uploadWay) {
		this.uploadWay = uploadWay;
	}

	public Integer getEpcVersion() {
		return epcVersion;
	}

	public void setEpcVersion(Integer epcVersion) {
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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

	public String getGdipLigXmlFile() {
		return gdipLigXmlFile;
	}

	public void setGdipLigXmlFile(String gdipLigXmlFile) {
		this.gdipLigXmlFile = gdipLigXmlFile;
	}

	public String getLodgedGdipLigXmlFile() {
		return lodgedGdipLigXmlFile;
	}

	public void setLodgedGdipLigXmlFile(String lodgedGdipLigXmlFile) {
		this.lodgedGdipLigXmlFile = lodgedGdipLigXmlFile;
	}
}