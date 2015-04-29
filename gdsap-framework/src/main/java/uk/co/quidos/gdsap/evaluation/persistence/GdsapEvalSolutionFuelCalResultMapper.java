package uk.co.quidos.gdsap.evaluation.persistence;


import java.util.List;

import org.springframework.stereotype.Repository;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalSolutionFuelCalResult;

@Repository
public interface GdsapEvalSolutionFuelCalResultMapper extends BaseMapper<GdsapEvalSolutionFuelCalResult, Long>
{
	void deleteBySolution(Long solutionId);
	
	List<GdsapEvalSolutionFuelCalResult> findGdsapEvalSolutionFuelCalResults(Long solutionId);
	
}