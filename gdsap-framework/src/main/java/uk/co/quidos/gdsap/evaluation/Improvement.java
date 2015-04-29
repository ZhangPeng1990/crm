/**
 * 
 */
package uk.co.quidos.gdsap.evaluation;

import uk.co.quidos.gdsap.evaluation.enums.GreenDealCategoryCode;
import uk.co.quidos.gdsap.evaluation.enums.ImprovementScope;
import uk.co.quidos.gdsap.evaluation.enums.RecommendationCategoryCode;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 *
 */
public class Improvement extends AbsBusinessObject
{
	
	private static final long serialVersionUID = 4921405066338298332L;

	private Long id;
    private RecommendationCategoryCode improvementCategory;
    private String improvementType;
    private float typicalSaving;
    private float energyPerformanceRating;
    private float environmentalImpactRating;
    private Recommendation recommendationCode;
    private String indicativeCost;
    private GreenDealCategoryCode greenDealCategory;
    private ImprovementScope scope;
    private Report report;
    
	public Long getId()
	{
		return id;
	}
	public RecommendationCategoryCode getImprovementCategory()
	{
		return improvementCategory;
	}
	public String getImprovementType()
	{
		return improvementType;
	}
	public float getTypicalSaving()
	{
		return typicalSaving;
	}
	public float getEnergyPerformanceRating()
	{
		return energyPerformanceRating;
	}
	public float getEnvironmentalImpactRating()
	{
		return environmentalImpactRating;
	}
	public Recommendation getRecommendationCode()
	{
		return recommendationCode;
	}
	public String getIndicativeCost()
	{
		return indicativeCost;
	}
	public GreenDealCategoryCode getGreenDealCategory()
	{
		return greenDealCategory;
	}
	public ImprovementScope getScope()
	{
		return scope;
	}
	public Report getReport()
	{
		return report;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public void setImprovementCategory(RecommendationCategoryCode improvementCategory)
	{
		this.improvementCategory = improvementCategory;
	}
	public void setImprovementType(String improvementType)
	{
		this.improvementType = improvementType;
	}
	public void setTypicalSaving(float typicalSaving)
	{
		this.typicalSaving = typicalSaving;
	}
	public void setEnergyPerformanceRating(float energyPerformanceRating)
	{
		this.energyPerformanceRating = energyPerformanceRating;
	}
	public void setEnvironmentalImpactRating(float environmentalImpactRating)
	{
		this.environmentalImpactRating = environmentalImpactRating;
	}
	public void setRecommendationCode(Recommendation recommendationCode)
	{
		this.recommendationCode = recommendationCode;
	}
	public void setIndicativeCost(String indicativeCost)
	{
		this.indicativeCost = indicativeCost;
	}
	public void setGreenDealCategory(GreenDealCategoryCode greenDealCategory)
	{
		this.greenDealCategory = greenDealCategory;
	}
	public void setScope(ImprovementScope scope)
	{
		this.scope = scope;
	}
	public void setReport(Report report)
	{
		this.report = report;
	}
    
}
