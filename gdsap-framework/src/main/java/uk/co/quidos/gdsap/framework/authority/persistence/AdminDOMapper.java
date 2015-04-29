package uk.co.quidos.gdsap.framework.authority.persistence;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.framework.authority.persistence.object.AdminDO;

public interface AdminDOMapper  extends BaseMapper<AdminDO, Long>{
	
		// 通过用户名查找用户
		AdminDO findUserByName(@Param("userName") String userName);
		
		AdminDO findUserByEmail(@Param("email") String email);
		
		//登录时根据用户名密码查找用户
		AdminDO findByUsernameAndPwd(@Param("username")String username,@Param("userPwd")String userPwd);

		// 查询Users,根据CondtionVo 内设置的条件
		List<AdminDO> findPageBreakByCondition(@Param("userName") String userName,
				@Param("startInsertTime") Date startInsertTime, @Param("endInsertTime") Date endInsertTime,
				@Param("userStatus") Integer userStatus, @Param("firstName") String firstName,
				@Param("surName") String surName, @Param("email") String email, Integer accessLevel, RowBounds rb);

		Integer findNumberByCondition(@Param("userName") String userName,
				@Param("startInsertTime") Date startInsertTime, @Param("endInsertTime") Date endInsertTime,
				@Param("userStatus") Integer userStatus, @Param("firstName") String firstName,
				@Param("surName") String surName, @Param("email") String email, Integer accessLevel);
}
