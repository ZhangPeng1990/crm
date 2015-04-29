package uk.co.quidos.gdsap.evaluation.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalSolutionRecommendationRel;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalSolutionRecommendationRelKey;
import uk.co.quidos.gdsap.evaluation.persistence.object.QueryRecommendationSolutionRecommendationRelResultMap;

@Repository
public interface GdsapEvalSolutionRecommendationRelMapper extends BaseMapper<GdsapEvalSolutionRecommendationRel, GdsapEvalSolutionRecommendationRelKey>
{
	void deleteBySolution(Long solutionId);
	
	List<QueryRecommendationSolutionRecommendationRelResultMap> findRelBySolutionId(Long solutionId);
	
}