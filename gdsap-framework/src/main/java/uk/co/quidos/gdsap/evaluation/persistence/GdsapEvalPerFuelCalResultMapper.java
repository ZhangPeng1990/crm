package uk.co.quidos.gdsap.evaluation.persistence;

import java.util.List;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalPerFuelCalResult;

public interface GdsapEvalPerFuelCalResultMapper extends BaseMapper<GdsapEvalPerFuelCalResult, Long>
{
	List<GdsapEvalPerFuelCalResult> findBySolutionId(Long solutionId);
	
	int deleteBySolutionId(Long solutionId);
}