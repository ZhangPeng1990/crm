package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapEvalSolutionRecommendationRelKey extends AbstractDO
{
	private static final long serialVersionUID = -2316521048170750611L;
	
	private Long recommendationId;
	private Long solutionId;

	public Long getRecommendationId()
	{
		return recommendationId;
	}

	public void setRecommendationId(Long recommendationId)
	{
		this.recommendationId = recommendationId;
	}

	public Long getSolutionId()
	{
		return solutionId;
	}

	public void setSolutionId(Long solutionId)
	{
		this.solutionId = solutionId;
	}
}