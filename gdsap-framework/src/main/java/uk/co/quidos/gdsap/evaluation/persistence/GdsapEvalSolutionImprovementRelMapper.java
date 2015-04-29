package uk.co.quidos.gdsap.evaluation.persistence;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalSolutionImprovementRelKey;

public interface GdsapEvalSolutionImprovementRelMapper extends BaseMapper<GdsapEvalSolutionImprovementRelKey,GdsapEvalSolutionImprovementRelKey>
{
	void deleteBySolutionId(Long solutionId);
}