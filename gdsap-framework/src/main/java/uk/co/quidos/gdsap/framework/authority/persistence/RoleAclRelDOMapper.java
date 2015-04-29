package uk.co.quidos.gdsap.framework.authority.persistence;

import org.springframework.stereotype.Repository;

import uk.co.quidos.gdsap.framework.authority.persistence.object.RoleAclRelDOKey;

@Repository
public interface RoleAclRelDOMapper
{
	int deleteByPrimaryKey(RoleAclRelDOKey key);

	int insert(RoleAclRelDOKey record);

	int insertSelective(RoleAclRelDOKey record);
	
	int deleteByAclId(String aclId);
	
	int deleteByRoleId(String roleId);
}