/**
 * 
 */
package uk.co.quidos.gdsap.framework.authority;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.util.Assert;

import uk.co.quidos.gdsap.framework.authority.enums.PagetagGroup;
import uk.co.quidos.gdsap.framework.authority.services.AdminServiceMgr;
import uk.co.quidos.gdsap.framework.authority.services.PageTagServiceMgr;
import uk.co.quidos.gdsap.framework.sys.business.BusinessFactory;
import uk.co.quidos.gdsap.framework.user.enums.AccessLevel;
import uk.co.quidos.gdsap.framework.user.enums.UserStatus;

/**
 * @author peng.shi
 */
public class Admin
{
	private Integer id;
	private String userName;
	private String userPwd;
	private Date insertTime;
	private Date updateTime;
	private UserStatus userStatus;
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

	public Admin()
	{
	}
	
	private Set<Role> roles;
	private Set<ACL> acls;
	private Map<PagetagGroup, Set<PageTag>> pageTagsMap;
	private boolean isInitial;
	
	/**
	 * 是否为Root用户
	 * @return
	 */
	public boolean isRoot()
	{
		if (AdminServiceMgr.DEFAULT_ROOT_NAME.equals(this.getUserName()))
		{
			return true;
		}
		return false;
	}

	/**
	 * @return the roles
	 */
	public Set<Role> getRoles()
	{
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles)
	{
		this.isInitial = false;
		this.roles = roles;
	}

	/**
	 * ACLs以及PageTags初始化
	 */
	private void _init()
	{
		if (this.getUserName().equals(AdminServiceMgr.DEFAULT_ROOT_NAME))
		{
			//ROOT 用户
			//如果为Root用户，则显示全部页签
			PageTagServiceMgr serviceMgr = (PageTagServiceMgr)BusinessFactory.getInstance().getService(PageTagServiceMgr.SERVICE_NAME);
			this.pageTagsMap = serviceMgr.getPageTags();
		}
		else
		{
			Assert.notEmpty(this.roles);
			//非Root用户
			// 重新计算ACLs
			Set<ACL> acls = new LinkedHashSet<ACL>();
			for (Role role : this.roles)
			{
				Set<ACL> tmpAcls = role.getAcls();
				acls.addAll(tmpAcls);
			}
			// 赋值属性
			this.acls = acls;
			// true
			// 重新计算 PageTags
			Set<PageTag> allPageTags = new LinkedHashSet<PageTag>();
			for (Role role : this.roles)
			{
				Set<PageTag> pageTags = role.getPageTags();
				allPageTags.addAll(pageTags);
			}
			//如果为普通管理员，则显示指定的页签 
			Map<PagetagGroup, Set<PageTag>> datas = new LinkedHashMap<PagetagGroup, Set<PageTag>>();
			for (PageTag tag : allPageTags)
			{
				if (datas.containsKey(tag.getPagetagGroup()))
				{
					datas.get(tag.getPagetagGroup()).add(tag);
				}
				else
				{
					Set<PageTag> tags = new LinkedHashSet<PageTag>();
					tags.add(tag);
					datas.put(tag.getPagetagGroup(), tags);
				}
			}
			this.pageTagsMap = datas;
		}
		this.isInitial = true;
	}
	/**
	 * @return the acls
	 */
	public Set<ACL> getAcls()
	{
		if (!this.isInitial)
		{
			this._init();
		}
		return acls;
	}

	/**
	 * @return the pageTagsMap
	 */
	public Map<PagetagGroup, Set<PageTag>> getPageTagsMap()
	{
		if (!this.isInitial)
		{
			this._init();
		}
		return pageTagsMap;
	}


	
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

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
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

	public AccessLevel getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(AccessLevel accessLevel) {
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

	public boolean isInitial() {
		return isInitial;
	}

	public void setInitial(boolean isInitial) {
		this.isInitial = isInitial;
	}

	public void setAcls(Set<ACL> acls) {
		this.acls = acls;
	}

	public void setPageTagsMap(Map<PagetagGroup, Set<PageTag>> pageTagsMap) {
		this.pageTagsMap = pageTagsMap;
	}
}
