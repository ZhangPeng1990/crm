package uk.co.quidos.gdsap.framework.authority.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import uk.co.quidos.gdsap.framework.authority.persistence.object.RoleDO;
@Repository
public interface RoleDOMapper
{
	int deleteByPrimaryKey(String id);

	int insert(RoleDO record);

	int insertSelective(RoleDO record);

	RoleDO selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(RoleDO record);

	int updateByPrimaryKeyWithBLOBs(RoleDO record);

	int updateByPrimaryKey(RoleDO record);
	
	List<String> findAllIds();
}