package uk.co.quidos.gdsap.framework.user.persistence;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.framework.user.persistence.object.GdsapUsrUser;

@Repository
public interface GdsapUsrUserMapper extends BaseMapper<GdsapUsrUser, Long>
{
	// 通过用户名查找用户
	GdsapUsrUser findUserByName(@Param("userName") String userName);
	
	GdsapUsrUser findUserByEmail(@Param("email") String email);
	
	//登录时根据用户名密码查找用户
	List<GdsapUsrUser> findUserList(@Param("userName") String userName,@Param("userPwd") String userPwd);

	// 查询Users,根据CondtionVo 内设置的条件
	List<GdsapUsrUser> findPageBreakByCondition(@Param("userName") String userName,@Param("assessorID") String assessorID,
			@Param("startInsertTime") Date startInsertTime, @Param("endInsertTime") Date endInsertTime,
			@Param("userStatus") Integer userStatus, @Param("userType") Integer userType, @Param("firstName") String firstName,
			@Param("surName") String surName, @Param("email") String email, Integer accessLevel, 
			@Param("orderField")String orderField, @Param("orderDirection")String orderDirection, RowBounds rb);

	Integer findNumberByCondition(@Param("userName") String userName,@Param("assessorID") String assessorID,
			@Param("startInsertTime") Date startInsertTime, @Param("endInsertTime") Date endInsertTime,
			@Param("userStatus") Integer userStatus, @Param("userType") Integer userType, @Param("firstName") String firstName,
			@Param("surName") String surName, @Param("email") String email, Integer accessLevel);
}