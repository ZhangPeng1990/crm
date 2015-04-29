/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.object;

import java.util.List;

import uk.co.quidos.gdsap.evaluation.Solution;
import uk.co.quidos.gdsap.evaluation.SolutionIssue;
import uk.co.quidos.gdsap.evaluation.StandardRecommendationCalResult;

/**
 * @author peng.shi
 *
 */
public class SolutionResult
{
	private Solution solution;
	private float totalEstimatedAnnualSavings;
    private float totalTypicalAnnualSavings;
    private float totalEstimatedCostsStart;
    private float totalEstimatedCostsEnd;
    private List<StandardRecommendationCalResult> standardRecommendationCalResults;
    private SolutionIssue solutionIssue;
    
	public SolutionIssue getSolutionIssue()
	{
		return solutionIssue;
	}
	public void setSolutionIssue(SolutionIssue solutionIssue)
	{
		this.solutionIssue = solutionIssue;
	}
	public Solution getSolution()
	{
		return solution;
	}
	public void setSolution(Solution solution)
	{
		this.solution = solution;
	}
	public float getTotalEstimatedAnnualSavings()
	{
		return totalEstimatedAnnualSavings;
	}
	public void setTotalEstimatedAnnualSavings(float totalEstimatedAnnualSavings)
	{
		this.totalEstimatedAnnualSavings = totalEstimatedAnnualSavings;
	}
	public float getTotalTypicalAnnualSavings()
	{
		return totalTypicalAnnualSavings;
	}
	public void setTotalTypicalAnnualSavings(float totalTypicalAnnualSavings)
	{
		this.totalTypicalAnnualSavings = totalTypicalAnnualSavings;
	}
	public float getTotalEstimatedCostsStart()
	{
		return totalEstimatedCostsStart;
	}
	public void setTotalEstimatedCostsStart(float totalEstimatedCostsStart)
	{
		this.totalEstimatedCostsStart = totalEstimatedCostsStart;
	}
	public float getTotalEstimatedCostsEnd()
	{
		return totalEstimatedCostsEnd;
	}
	public void setTotalEstimatedCostsEnd(float totalEstimatedCostsEnd)
	{
		this.totalEstimatedCostsEnd = totalEstimatedCostsEnd;
	}
	public List<StandardRecommendationCalResult> getStandardRecommendationCalResults()
	{
		return standardRecommendationCalResults;
	}
	public void setStandardRecommendationCalResults(List<StandardRecommendationCalResult> standardRecommendationCalResults)
	{
		this.standardRecommendationCalResults = standardRecommendationCalResults;
	}
    
}
