/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.object;

import java.util.Date;

import uk.co.quidos.dal.BaseConditionVO;
import uk.co.quidos.gdsap.evaluation.enums.PropertyType;
import uk.co.quidos.gdsap.evaluation.enums.ReportStatus;
import uk.co.quidos.gdsap.evaluation.enums.TransactionType;
import uk.co.quidos.gdsap.framework.user.User;

/**
 * @author shipeng
 *
 */
public class ConditionVO extends BaseConditionVO
{
	private User user;
	private String rrn;
	private Date startInspectionDate;
	private Date endInspectionDate;
	private String urpn;
	private TransactionType transactionType;
	private PropertyType propertyType;
	private ReportStatus reportStatus;
	private String oaRrn;
	private String organisation;
	private String organisationCertificationNumber;
	private String userName;
	
	private String companyId;
	private String companyName;
	
	public User getUser()
	{
		return user;
	}
	public void setUser(User user)
	{
		this.user = user;
	}
	public String getRrn()
	{
		return rrn;
	}
	public void setRrn(String rrn)
	{
		this.rrn = rrn;
	}
	public Date getStartInspectionDate()
	{
		return startInspectionDate;
	}
	public void setStartInspectionDate(Date startInspectionDate)
	{
		this.startInspectionDate = startInspectionDate;
	}
	public Date getEndInspectionDate()
	{
		return endInspectionDate;
	}
	public void setEndInspectionDate(Date endInspectionDate)
	{
		this.endInspectionDate = endInspectionDate;
	}
	public String getUrpn()
	{
		return urpn;
	}
	public void setUrpn(String urpn)
	{
		this.urpn = urpn;
	}
	public TransactionType getTransactionType()
	{
		return transactionType;
	}
	public void setTransactionType(TransactionType transactionType)
	{
		this.transactionType = transactionType;
	}
	public PropertyType getPropertyType()
	{
		return propertyType;
	}
	public void setPropertyType(PropertyType propertyType)
	{
		this.propertyType = propertyType;
	}
	public ReportStatus getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(ReportStatus reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String getOaRrn() {
		return oaRrn;
	}
	public void setOaRrn(String oaRrn) {
		this.oaRrn = oaRrn;
	}
	public String getOrganisation() {
		return organisation;
	}
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	public String getOrganisationCertificationNumber() {
		return organisationCertificationNumber;
	}
	public void setOrganisationCertificationNumber(
			String organisationCertificationNumber) {
		this.organisationCertificationNumber = organisationCertificationNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
