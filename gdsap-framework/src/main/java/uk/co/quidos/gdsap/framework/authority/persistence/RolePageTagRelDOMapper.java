package uk.co.quidos.gdsap.framework.authority.persistence;

import org.springframework.stereotype.Repository;

import uk.co.quidos.gdsap.framework.authority.persistence.object.RolePageTagRelDOKey;

@Repository
public interface RolePageTagRelDOMapper
{
	int deleteByPrimaryKey(RolePageTagRelDOKey key);

	int insert(RolePageTagRelDOKey record);

	int insertSelective(RolePageTagRelDOKey record);
	
	int deleteByRoleId(String roleId);
	
	int deleteByPageTagId(String pageTagId);
}