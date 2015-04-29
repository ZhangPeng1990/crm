package uk.co.quidos.gdsap.framework.authority.persistence.object;

import java.util.Date;

import uk.co.quidos.dal.object.AbstractDO;


public class AdminDO extends AbstractDO{
	private static final long serialVersionUID = 629316181396707981L;
	private Integer id;
	private String userName;
	private String userPwd;
	private Date insertTime;
	private Date updateTime;
	private Integer userStatus;
	private String firstName;
	private String surName;
	private String email;
	private String registerCode;
	private Integer accessLevel;
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
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
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
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegisterCode() {
		return registerCode;
	}
	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}
	public Integer getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(Integer accessLevel) {
		this.accessLevel = accessLevel;
	}
	public String getOrganisation() {
		return organisation;
	}
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	public String getOrganisationWebSite() {
		return organisationWebSite;
	}
	public void setOrganisationWebSite(String organisationWebSite) {
		this.organisationWebSite = organisationWebSite;
	}
	public String getOrganisationCertificationNumber() {
		return organisationCertificationNumber;
	}
	public void setOrganisationCertificationNumber(
			String organisationCertificationNumber) {
		this.organisationCertificationNumber = organisationCertificationNumber;
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
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
