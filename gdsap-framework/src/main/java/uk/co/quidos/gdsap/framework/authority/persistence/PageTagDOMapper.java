package uk.co.quidos.gdsap.framework.authority.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import uk.co.quidos.gdsap.framework.authority.persistence.object.PageTagDO;

@Repository
public interface PageTagDOMapper
{
	int deleteByPrimaryKey(String id);

	int insert(PageTagDO record);

	int insertSelective(PageTagDO record);

	PageTagDO selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(PageTagDO record);

	int updateByPrimaryKey(PageTagDO record);
	
	List<PageTagDO> findByGroup(String pagetagGroup);
	
	List<PageTagDO> findByRoleId(String roleId);
}