/**
 * 
 */
package uk.co.quidos.gdsap.evaluation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import uk.co.quidos.gdsap.evaluation.enums.SolutionType;

/**
 * 用于StandardRecommendation
 * @author peng.shi
 */
public class StandardRecommendationWrap
{
	private StandardRecommendation standardRecommendation;
	private Map<StandardOption, Integer> selectedOptionItems;
	private Map<StandardValue,String> selectedInputValues;
	private SolutionType solutionType;
	
	private long id;
	private Report report;
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public Report getReport()
	{
		return report;
	}
	public void setReport(Report report)
	{
		this.report = report;
	}
	public StandardRecommendation getStandardRecommendation()
	{
		return standardRecommendation;
	}
	public void setStandardRecommendation(StandardRecommendation standardRecommendation)
	{
		this.standardRecommendation = standardRecommendation;
	}	
	public Map<StandardOption, Integer> getSelectedOptionItems() {
		return selectedOptionItems;
	}
	public void setSelectedOptionItems(Map<StandardOption, Integer> selectedOptionItems) 
	{
		this.selectedOptionItems = selectedOptionItems;
	}
	public Map<String, String> getSelectedOptionPairs()
	{
		if (!CollectionUtils.isEmpty(this.selectedOptionItems))
		{
			Map<String, String> datas = new LinkedHashMap<String, String>();
			Set<StandardOption> keys = this.selectedOptionItems.keySet();
			for (StandardOption key : keys)
			{
				datas.put(key.getCode(), key.getValue(this.selectedOptionItems.get(key)));
				//datas.put(key.getCode(), String.valueOf(this.selectedOptionItems.get(key)));
			}
			return datas;
		}
		return null;
	}
	public Map<StandardValue, String> getSelectedInputValues() {
		return selectedInputValues;
	}
	public void setSelectedInputValues(
			Map<StandardValue, String> selectedInputValues) {
		this.selectedInputValues = selectedInputValues;
	}
	public SolutionType getSolutionType() {
		return solutionType;
	}
	public void setSolutionType(SolutionType solutionType) {
		this.solutionType = solutionType;
	}
}