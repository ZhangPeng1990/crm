package uk.co.quidos.gdsap.evaluation.object;

/**
 * @author peng.shi
 */
public class TotalEpcImprovementCalResult
{
	private float totalEstimatedSaving;
	private float totalTypicalSaving;
	private float totalStartTotalIndicativeCost;
	private float totalEndIndicativeCost;
	
	public float getTotalEstimatedSaving()
	{
		return totalEstimatedSaving;
	}
	public void setTotalEstimatedSaving(float totalEstimatedSaving)
	{
		this.totalEstimatedSaving = totalEstimatedSaving;
	}
	public float getTotalTypicalSaving()
	{
		return totalTypicalSaving;
	}
	public void setTotalTypicalSaving(float totalTypicalSaving)
	{
		this.totalTypicalSaving = totalTypicalSaving;
	}
	public float getTotalStartTotalIndicativeCost()
	{
		return totalStartTotalIndicativeCost;
	}
	public void setTotalStartTotalIndicativeCost(float totalStartTotalIndicativeCost)
	{
		this.totalStartTotalIndicativeCost = totalStartTotalIndicativeCost;
	}
	public float getTotalEndIndicativeCost()
	{
		return totalEndIndicativeCost;
	}
	public void setTotalEndIndicativeCost(float totalEndIndicativeCost)
	{
		this.totalEndIndicativeCost = totalEndIndicativeCost;
	}
}