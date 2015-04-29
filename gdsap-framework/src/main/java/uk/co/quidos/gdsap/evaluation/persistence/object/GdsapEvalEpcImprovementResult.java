package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

/**
 * @author peng.shi
 */
public class GdsapEvalEpcImprovementResult extends AbstractDO
{
	private static final long serialVersionUID = 8839409227602296233L;

	private Long id;

	private String improvementType;

	private Integer improvementNumber;

	private String greenDealCategory;

	private Float estimatedSaving;

	private Float typicalSaving;

	private Float indicativeCostStart;

	private Float indicativeCostEnd;

	private Float inUseFactor;

	private Long solutionId;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getImprovementType()
	{
		return improvementType;
	}

	public void setImprovementType(String improvementType)
	{
		this.improvementType = improvementType;
	}

	public Integer getImprovementNumber()
	{
		return improvementNumber;
	}

	public void setImprovementNumber(Integer improvementNumber)
	{
		this.improvementNumber = improvementNumber;
	}

	public String getGreenDealCategory()
	{
		return greenDealCategory;
	}

	public void setGreenDealCategory(String greenDealCategory)
	{
		this.greenDealCategory = greenDealCategory;
	}

	public Float getEstimatedSaving()
	{
		return estimatedSaving;
	}

	public void setEstimatedSaving(Float estimatedSaving)
	{
		this.estimatedSaving = estimatedSaving;
	}

	public Float getTypicalSaving()
	{
		return typicalSaving;
	}

	public void setTypicalSaving(Float typicalSaving)
	{
		this.typicalSaving = typicalSaving;
	}

	public Float getIndicativeCostStart()
	{
		return indicativeCostStart;
	}

	public void setIndicativeCostStart(Float indicativeCostStart)
	{
		this.indicativeCostStart = indicativeCostStart;
	}

	public Float getIndicativeCostEnd()
	{
		return indicativeCostEnd;
	}

	public void setIndicativeCostEnd(Float indicativeCostEnd)
	{
		this.indicativeCostEnd = indicativeCostEnd;
	}

	public Float getInUseFactor()
	{
		return inUseFactor;
	}

	public void setInUseFactor(Float inUseFactor)
	{
		this.inUseFactor = inUseFactor;
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