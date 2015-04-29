/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.object;

/**
 * @author peng.shi
 *
 */
public class TotalEstimatedCosts
{
	private float totalEstimatedCostsStart = 0f;
	private float totalEstimatedCostsEnd = 0f;
	private float totalActual = 0f;
	private float totalTypical = 0f;
	
	public TotalEstimatedCosts(float totalEstimatedCostsStart,float totalEstimatedCostsEnd,float totalActual,float totalTypical)
	{
		this.totalActual = totalActual;
		this.totalEstimatedCostsEnd = totalEstimatedCostsEnd;
		this.totalEstimatedCostsStart = totalEstimatedCostsStart;
		this.totalTypical = totalTypical;
	}
	
	public float getTotalEstimatedCostsStart()
	{
		return totalEstimatedCostsStart;
	}
	public float getTotalEstimatedCostsEnd()
	{
		return totalEstimatedCostsEnd;
	}
	public float getTotalActual()
	{
		return totalActual;
	}
	public float getTotalTypical()
	{
		return totalTypical;
	}
	
}
