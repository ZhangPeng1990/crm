package uk.co.quidos.gdsap.evaluation.persistence;

import java.util.List;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalEpcImprovementResult;

/**
 * @author peng.shi
 */
public interface GdsapEvalEpcImprovementResultMapper extends BaseMapper<GdsapEvalEpcImprovementResult, Long>
{
	List<GdsapEvalEpcImprovementResult> findBySolutionId(Long solutionId);
	
	void deleteBySolutionId(Long solutionId);
	
}