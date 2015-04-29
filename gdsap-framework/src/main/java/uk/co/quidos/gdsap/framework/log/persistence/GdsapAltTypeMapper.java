package uk.co.quidos.gdsap.framework.log.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.framework.log.persistence.object.GdsapAltType;

/**
 * @author peng.shi
 *
 */
@Repository
public interface GdsapAltTypeMapper extends BaseMapper<GdsapAltType,String>
{
	List<String> findPageBreakAllIdsByTitle(@Param("title")String title,RowBounds rb);
	
	int findNumberByTitle(@Param("title")String title);
	
}