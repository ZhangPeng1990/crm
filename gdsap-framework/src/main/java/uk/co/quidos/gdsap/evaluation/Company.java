package uk.co.quidos.gdsap.evaluation;

import java.io.Serializable;
import java.util.Date;

import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

public class Company extends AbsBusinessObject
{
	private static final long serialVersionUID = 8780304617164532175L;
	private String companyId;
	private String name;
	private Date insertTime;
	private Date updateTime;
	private String email;
	private String address1;
	private String address2;
	private String address3;
	private String postcode;
	private String posttown;
	private String website;
	private String fax;
	private String tel;
	
	public String getCompanyId() {
		return companyId;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
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


	@Override
	public Serializable getId() {
		return null;
	}

}
