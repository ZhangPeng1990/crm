package uk.co.quidos.gdsap.framework.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.framework.common.persistence.object.GdsapSysPreference;

@Repository
public interface GdsapSysPreferenceMapper extends BaseMapper<GdsapSysPreference, String>
{	
	List<String> findPageBreakIdsByPreType(@Param("preType") Integer preType,RowBounds rb);
	
	int findNumberByPreType(@Param("preType") Integer preType);
	
}