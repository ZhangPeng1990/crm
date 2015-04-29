/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.persistence.object;

/**
 * @author peng.shi
 *
 */
public class QueryRecommendationSolutionRecommendationRelResultMap extends GdsapEvalSolutionRecommendationRel
{
	private static final long serialVersionUID = 105101966188728303L;
	
	private int recommendationCode;

	public int getRecommendationCode()
	{
		return recommendationCode;
	}

	public void setRecommendationCode(int recommendationCode)
	{
		this.recommendationCode = recommendationCode;
	}
	
}
