/**
 * 
 */
package uk.co.quidos.gdsap.framework.user;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import uk.co.quidos.gdsap.framework.enums.UserType;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;
import uk.co.quidos.gdsap.framework.user.enums.AccessLevel;
import uk.co.quidos.gdsap.framework.user.enums.UserStatus;

/**
 * @author peng.shi
 * 
 */
public class User extends AbsBusinessObject
{
	private static final long serialVersionUID = 3671792629782535385L;

	private Long id;
	private String userName;
	private String assessorID;
	private String userPwd;
	private Date insertTime;
	private Date updateTime;
	private UserStatus userStatus;
	private UserType userType;
	private String firstName;
	private String surName;
	private String email;
	private String registerCode;
	private AccessLevel accessLevel;
	private String organisation;
	private String organisationWebSite;
	private String organisationCertificationNumber;
	private String address1;
	private String address2;
	private String address3;
	private String postcode;
	private String posttown;
	private String website;
	private String fax;
	private String tel;
	private String companyName;
	
	private String authUsername;
	private String authPassword;
	
	private String insurer;
	private String policyNo;
	private Date effectiveDate;
	private Date expiryDate;
	private Float piLimit;
	
	private String sctAuthorizationUsername;
	private String sctAuthorizationPassword;
	private String sctAssessorOrganisationid;
	private String sctAdviserId;
	
	public String getAuthUsername() {
		return authUsername;
	}

	public void setAuthUsername(String authUsername) {
		this.authUsername = authUsername;
	}
	
	public String getAssessorID() {
		return assessorID;
	}

	public void setAssessorID(String assessorID) {
		this.assessorID = assessorID;
	}

	public String getAuthPassword() {
		return authPassword;
	}

	public void setAuthPassword(String authPassword) {
		this.authPassword = authPassword;
	}

	public String getOrganisation()
	{
		return organisation;
	}

	public void setOrganisation(String organisation)
	{
		this.organisation = organisation;
	}

	public String getOrganisationWebSite()
	{
		return organisationWebSite;
	}

	public void setOrganisationWebSite(String organisationWebSite)
	{
		this.organisationWebSite = organisationWebSite;
	}

	public String getOrganisationCertificationNumber()
	{
		return organisationCertificationNumber;
	}

	public void setOrganisationCertificationNumber(String organisationCertificationNumber)
	{
		this.organisationCertificationNumber = organisationCertificationNumber;
	}
	
	public String getOrganisationCertificationNumberSuffix() {
		if (!StringUtils.isEmpty(this.organisationCertificationNumber) && !StringUtils.isBlank(organisationCertificationNumber)) {
			return this.organisationCertificationNumber.substring(4);
		}
		return null;
	}
	public String getAddress1()
	{
		return address1;
	}

	public void setAddress1(String address1)
	{
		this.address1 = address1;
	}

	public String getAddress2()
	{
		return address2;
	}

	public void setAddress2(String address2)
	{
		this.address2 = address2;
	}

	public String getAddress3()
	{
		return address3;
	}

	public void setAddress3(String address3)
	{
		this.address3 = address3;
	}

	public String getPostcode()
	{
		return postcode;
	}

	public void setPostcode(String postcode)
	{
		this.postcode = postcode;
	}

	public String getPosttown()
	{
		return posttown;
	}

	public void setPosttown(String posttown)
	{
		this.posttown = posttown;
	}

	public String getWebsite()
	{
		return website;
	}

	public void setWebsite(String website)
	{
		this.website = website;
	}

	public String getFax()
	{
		return fax;
	}

	public void setFax(String fax)
	{
		this.fax = fax;
	}

	public String getTel()
	{
		return tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public String getCompanyName()
	{
		return companyName;
	}

	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}

	@Override
	public Long getId()
	{
		return this.id;
	}

	/**
	 * @return the userName
	 */
	public String getUserName()
	{
		return userName;
	}

	/**
	 * @return the userPwd
	 */
	public String getUserPwd()
	{
		return userPwd;
	}

	/**
	 * @return the insertTime
	 */
	public Date getInsertTime()
	{
		return insertTime;
	}

	/**
	 * @return the userStatus
	 */
	public UserStatus getUserStatus()
	{
		return userStatus;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @return the surName
	 */
	public String getSurName()
	{
		return surName;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @return the registerCode
	 */
	public String getRegisterCode()
	{
		return registerCode;
	}

	/**
	 * @return the accessLevel
	 */
	public AccessLevel getAccessLevel()
	{
		return accessLevel;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	/**
	 * @param userPwd
	 *            the userPwd to set
	 */
	public void setUserPwd(String userPwd)
	{
		this.userPwd = userPwd;
	}

	/**
	 * @param insertTime
	 *            the insertTime to set
	 */
	public void setInsertTime(Date insertTime)
	{
		this.insertTime = insertTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime()
	{
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	/**
	 * @param userStatus
	 *            the userStatus to set
	 */
	public void setUserStatus(UserStatus userStatus)
	{
		this.userStatus = userStatus;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @param surName
	 *            the surName to set
	 */
	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @param registerCode
	 *            the registerCode to set
	 */
	public void setRegisterCode(String registerCode)
	{
		this.registerCode = registerCode;
	}

	/**
	 * @param accessLevel
	 *            the accessLevel to set
	 */
	public void setAccessLevel(AccessLevel accessLevel)
	{
		this.accessLevel = accessLevel;
	}

	public String getInsurer() {
		return insurer;
	}

	public void setInsurer(String insurer) {
		this.insurer = insurer;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Float getPiLimit() {
		return piLimit;
	}

	public void setPiLimit(Float piLimit) {
		this.piLimit = piLimit;
	}

	public String getSctAuthorizationUsername() {
		return sctAuthorizationUsername;
	}

	public void setSctAuthorizationUsername(String sctAuthorizationUsername) {
		this.sctAuthorizationUsername = sctAuthorizationUsername;
	}

	public String getSctAuthorizationPassword() {
		return sctAuthorizationPassword;
	}

	public void setSctAuthorizationPassword(String sctAuthorizationPassword) {
		this.sctAuthorizationPassword = sctAuthorizationPassword;
	}

	public String getSctAssessorOrganisationid() {
		return sctAssessorOrganisationid;
	}

	public void setSctAssessorOrganisationid(String sctAssessorOrganisationid) {
		this.sctAssessorOrganisationid = sctAssessorOrganisationid;
	}

	public String getSctAdviserId() {
		return sctAdviserId;
	}

	public void setSctAdviserId(String sctAdviserId) {
		this.sctAdviserId = sctAdviserId;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
}
