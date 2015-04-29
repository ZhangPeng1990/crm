package uk.co.quidos.gdsap.framework.authority.services.impl;

import uk.co.quidos.gdsap.framework.authority.Admin;
import uk.co.quidos.gdsap.framework.authority.persistence.object.AdminDO;
import uk.co.quidos.gdsap.framework.sys.lang.enums.EnumUtils;
import uk.co.quidos.gdsap.framework.user.enums.AccessLevel;
import uk.co.quidos.gdsap.framework.user.enums.UserStatus;


/**
 * @author peng.shi
 */
public class BeanUtils
{
	public static Admin getAdmin(AdminDO adminDO)
	{
		Admin admin = new Admin();
		admin.setId(adminDO.getId());
		admin.setUserName(adminDO.getUserName());
		admin.setUserPwd(adminDO.getUserPwd());
		admin.setInsertTime(adminDO.getInsertTime());
		admin.setUpdateTime(adminDO.getUpdateTime());
		admin.setUserStatus((UserStatus)EnumUtils.getByCode(adminDO.getUserStatus(), UserStatus.class));
		admin.setFirstName(adminDO.getFirstName());
		admin.setSurName(adminDO.getSurName());
		admin.setEmail(adminDO.getEmail());
		admin.setRegisterCode(adminDO.getRegisterCode());
		admin.setAccessLevel((AccessLevel)EnumUtils.getByCode(adminDO.getAccessLevel(), AccessLevel.class));
		admin.setOrganisation(adminDO.getOrganisation());
		admin.setOrganisationWebSite(adminDO.getOrganisationWebSite());
		admin.setOrganisationCertificationNumber(adminDO.getOrganisationCertificationNumber());
		admin.setAddress1(adminDO.getAddress1());
		admin.setAddress2(adminDO.getAddress2());
		admin.setAddress3(adminDO.getAddress3());
		admin.setPostcode(adminDO.getPostcode());
		admin.setPosttown(adminDO.getPosttown());
		admin.setWebsite(adminDO.getWebsite());
		admin.setFax(adminDO.getFax());
		admin.setTel(adminDO.getTel());
		admin.setCompanyName(adminDO.getCompanyName());
		return admin;
	}
	
	public static AdminDO getAdminDO(Admin admin)
	{
		AdminDO adminDO = new AdminDO();
		adminDO.setId(admin.getId());
		adminDO.setUserName(admin.getUserName());
		adminDO.setUserPwd(admin.getUserPwd());
		adminDO.setInsertTime(admin.getInsertTime());
		adminDO.setUpdateTime(admin.getUpdateTime());
		adminDO.setUserStatus(admin.getUserStatus() != null ? admin.getUserStatus().getCode() : null);
		adminDO.setFirstName(admin.getFirstName());
		adminDO.setSurName(admin.getSurName());
		adminDO.setEmail(admin.getEmail());
		adminDO.setRegisterCode(admin.getRegisterCode());
		adminDO.setAccessLevel(admin.getAccessLevel() != null ? admin.getAccessLevel().getCode() : null);
		adminDO.setOrganisation(admin.getOrganisation());
		adminDO.setOrganisationWebSite(admin.getOrganisationWebSite());
		adminDO.setOrganisationCertificationNumber(admin.getOrganisationCertificationNumber());
		adminDO.setAddress1(admin.getAddress1());
		adminDO.setAddress2(admin.getAddress2());
		adminDO.setAddress3(admin.getAddress3());
		adminDO.setPostcode(admin.getPostcode());
		adminDO.setPosttown(admin.getPosttown());
		adminDO.setWebsite(admin.getWebsite());
		adminDO.setFax(admin.getFax());
		adminDO.setTel(admin.getTel());
		adminDO.setCompanyName(admin.getCompanyName());
		return adminDO;
	}
}
