package uk.co.quidos.gdsap.evaluation.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalImprovement;

/**
 * @author peng.shi
 *
 */
@Repository
public interface GdsapEvalImprovementMapper extends BaseMapper<GdsapEvalImprovement,Long>
{
	List<GdsapEvalImprovement> findByReportIdAndScope(@Param("reportId") Long reportId,@Param("scope") Integer scope);
	
	List<GdsapEvalImprovement> findBySolutionId(Long solutionId);
	
}