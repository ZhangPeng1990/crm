package uk.co.quidos.gdsap.framework.authority.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import uk.co.quidos.gdsap.framework.authority.persistence.object.AdminRoleRelDOKey;

public interface AdminRoleRelDOMapper
{
	int deleteByPrimaryKey(AdminRoleRelDOKey key);

	int insert(AdminRoleRelDOKey record);

	int insertSelective(AdminRoleRelDOKey record);
	
	int deleteByAdminId(Integer adminId);
	
	List<String> findRoleIdsByAdminId(Integer adminId);
	
	
	List<Integer> findAdminIdByRoleId(@Param("roleId")String roleId,@Param("userName")String userName,
			@Param("firstName")String firstName,@Param("surName")String surName,
			@Param("userStatus")Integer userStatus,RowBounds rb);
	
	int getAdminNumByRoleId(@Param("roleId")String roleId,@Param("userName")String userName,
			@Param("firstName")String firstName,@Param("surName")String surName,
			@Param("userStatus")Integer userStatus);
}