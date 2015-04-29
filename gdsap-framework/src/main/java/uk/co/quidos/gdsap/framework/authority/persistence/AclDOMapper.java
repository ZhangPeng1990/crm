package uk.co.quidos.gdsap.framework.authority.persistence;


import java.util.List;

import org.springframework.stereotype.Repository;

import uk.co.quidos.gdsap.framework.authority.persistence.object.AclDO;

@Repository
public interface AclDOMapper
{
	int deleteByPrimaryKey(String id);

	int insert(AclDO record);

	int insertSelective(AclDO record);

	AclDO selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(AclDO record);

	int updateByPrimaryKeyWithBLOBs(AclDO record);

	int updateByPrimaryKey(AclDO record);
	
	List<AclDO> findAll();
	
	List<AclDO> findByRoleId(String roleId);
	
	List<AclDO> findByAdminId(Integer adminId);
	
}