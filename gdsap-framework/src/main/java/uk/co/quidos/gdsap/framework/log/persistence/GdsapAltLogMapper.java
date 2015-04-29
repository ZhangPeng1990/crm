package uk.co.quidos.gdsap.framework.log.persistence;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.framework.log.persistence.object.GdsapAltLog;

/**
 * @author peng.shi
 *
 */
@Repository
public interface GdsapAltLogMapper extends BaseMapper<GdsapAltLog,String>
{
	void deleteByInsertTimeRange(@Param("startInsertTime") Date startInsertTime,@Param("endInsertTime") Date endInsertTime);
	
	List<GdsapAltLog> findPageBreakByCondition(@Param("startInsertTime") Date startInsertTime,
			@Param("endInsertTime") Date endInsertTime, @Param("logLevel") Integer logLevel,
			@Param("userType") Integer userType, @Param("ip") String ip,RowBounds rb);
	
	int findNumberByCondition(@Param("startInsertTime") Date startInsertTime,
			@Param("endInsertTime") Date endInsertTime, @Param("logLevel") Integer logLevel,
			@Param("userType") Integer userType, @Param("ip") String ip);
}