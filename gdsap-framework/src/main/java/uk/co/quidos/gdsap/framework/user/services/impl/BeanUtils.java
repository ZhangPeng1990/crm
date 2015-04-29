package uk.co.quidos.gdsap.framework.user.services.impl;

import uk.co.quidos.gdsap.framework.enums.UserType;
import uk.co.quidos.gdsap.framework.sys.lang.enums.EnumUtils;
import uk.co.quidos.gdsap.framework.user.User;
import uk.co.quidos.gdsap.framework.user.enums.AccessLevel;
import uk.co.quidos.gdsap.framework.user.enums.UserStatus;
import uk.co.quidos.gdsap.framework.user.persistence.object.GdsapUsrUser;

/**
 * 业务对象以及数据对象转换
 * 1. 完成持久化中基本类型与Enum转换等操作，类似于quidos-framework-1.0 中接口赋值
 * 2. 
 * @author peng.shi
 */
public class BeanUtils
{	
	/**
	 * GdsapAltType -> LogType
	 * @param model
	 * @return
	 */
	public static GdsapUsrUser user2gdsapUsr(User user)
	{
		GdsapUsrUser gdsapUsrUser = new GdsapUsrUser();
		gdsapUsrUser.setId(user.getId());
		gdsapUsrUser.setUserName(user.getUserName());
		gdsapUsrUser.setUserType(user.getUserType().getCode());
		gdsapUsrUser.setAssessorID(user.getAssessorID());
		gdsapUsrUser.setUserPwd(user.getUserPwd());
		gdsapUsrUser.setInsertTime(user.getInsertTime());
		gdsapUsrUser.setUpdateTime(user.getUpdateTime());
		gdsapUsrUser.setUserStatus(user.getUserStatus() != null ? user.getUserStatus().getCode() : null);
		gdsapUsrUser.setFirstName(user.getFirstName());
		gdsapUsrUser.setSurName(user.getSurName());
		gdsapUsrUser.setEmail(user.getEmail());
		gdsapUsrUser.setRegisterCode(user.getRegisterCode());
		gdsapUsrUser.setAccessLevel(user.getAccessLevel() != null ? user.getAccessLevel().getCode() : null);
		
		gdsapUsrUser.setOrganisation(user.getOrganisation());
		gdsapUsrUser.setOrganisationWebSite(user.getOrganisationWebSite());
		gdsapUsrUser.setOrganisationCertificationNumber(user.getOrganisationCertificationNumber());
		gdsapUsrUser.setAddress1(user.getAddress1());
		gdsapUsrUser.setAddress2(user.getAddress2());
		gdsapUsrUser.setAddress3(user.getAddress3());
		gdsapUsrUser.setPostcode(user.getPostcode());
		gdsapUsrUser.setPosttown(user.getPosttown());
		gdsapUsrUser.setWebsite(user.getWebsite());
		gdsapUsrUser.setFax(user.getFax());
		gdsapUsrUser.setTel(user.getTel());
		gdsapUsrUser.setCompanyName(user.getCompanyName());
		gdsapUsrUser.setInsurer(user.getInsurer());
		gdsapUsrUser.setPolicyNo(user.getPolicyNo());
		gdsapUsrUser.setEffectiveDate(user.getEffectiveDate());
		gdsapUsrUser.setExpiryDate(user.getExpiryDate());
		gdsapUsrUser.setPiLimit(user.getPiLimit());
		gdsapUsrUser.setAuthUsername(user.getAuthUsername());
		gdsapUsrUser.setAuthPassword(user.getAuthPassword());
		
		gdsapUsrUser.setSctAuthorizationUsername(user.getSctAuthorizationUsername());
		gdsapUsrUser.setSctAuthorizationPassword(user.getSctAuthorizationPassword());
		gdsapUsrUser.setSctAssessorOrganisationid(user.getSctAssessorOrganisationid());
		gdsapUsrUser.setSctAdviserId(user.getSctAdviserId());
		
		return gdsapUsrUser;
	}
	
	//private static BeanCopier gdsapuserCopier = BeanCopier.create(GdsapUsrUser.class,User.class,false);
	/**
	 * GdsapAltType -> LogType
	 * @param model+
	 * @return
	 */
	public static User gdsapUsr2User(GdsapUsrUser gdsapUsrUser)
	{
		User user = new User();
		//gdsapuserCopier.copy(gdsapUsrUser, user, null);
		user.setId(gdsapUsrUser.getId());
		user.setUserName(gdsapUsrUser.getUserName());
		user.setAssessorID(gdsapUsrUser.getAssessorID());
		user.setUserPwd(gdsapUsrUser.getUserPwd());
		user.setInsertTime(gdsapUsrUser.getInsertTime());
		user.setUpdateTime(gdsapUsrUser.getUpdateTime());
		user.setUserStatus((UserStatus)EnumUtils.getByCode(gdsapUsrUser.getUserStatus(), UserStatus.class));
		user.setUserType((UserType)EnumUtils.getByCode(gdsapUsrUser.getUserType(), UserType.class));
		user.setFirstName(gdsapUsrUser.getFirstName());
		user.setSurName(gdsapUsrUser.getSurName());
		user.setEmail(gdsapUsrUser.getEmail());
		user.setRegisterCode(gdsapUsrUser.getRegisterCode());
		user.setAccessLevel((AccessLevel)EnumUtils.getByCode(gdsapUsrUser.getAccessLevel(), AccessLevel.class));
		
		user.setOrganisation(gdsapUsrUser.getOrganisation());
		user.setOrganisationWebSite(gdsapUsrUser.getOrganisationWebSite());
		user.setOrganisationCertificationNumber(gdsapUsrUser.getOrganisationCertificationNumber());
		user.setAddress1(gdsapUsrUser.getAddress1());
		user.setAddress2(gdsapUsrUser.getAddress2());
		user.setAddress3(gdsapUsrUser.getAddress3());
		user.setPostcode(gdsapUsrUser.getPostcode());
		user.setPosttown(gdsapUsrUser.getPosttown());
		user.setWebsite(gdsapUsrUser.getWebsite());
		user.setFax(gdsapUsrUser.getFax());
		user.setTel(gdsapUsrUser.getTel());
		user.setCompanyName(gdsapUsrUser.getCompanyName());
		user.setInsurer(gdsapUsrUser.getInsurer());
		user.setPolicyNo(gdsapUsrUser.getPolicyNo());
		user.setEffectiveDate(gdsapUsrUser.getEffectiveDate());
		user.setExpiryDate(gdsapUsrUser.getExpiryDate());
		user.setPiLimit(gdsapUsrUser.getPiLimit());
		user.setAuthUsername(gdsapUsrUser.getAuthUsername());
		user.setAuthPassword(gdsapUsrUser.getAuthPassword());
		
		user.setSctAuthorizationUsername(gdsapUsrUser.getSctAuthorizationUsername());
		user.setSctAuthorizationPassword(gdsapUsrUser.getSctAuthorizationPassword());
		user.setSctAssessorOrganisationid(gdsapUsrUser.getSctAssessorOrganisationid());
		user.setSctAdviserId(gdsapUsrUser.getSctAdviserId());
		
		return user;
	}
	
}
