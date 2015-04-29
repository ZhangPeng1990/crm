/**
 * 
 */
package uk.co.quidos.gdsap.evaluation;

import java.util.List;

import org.springframework.util.Assert;

import uk.co.quidos.gdsap.evaluation.enums.ItemUniqueGroup;
import uk.co.quidos.gdsap.evaluation.enums.SolutionType;
import uk.co.quidos.gdsap.evaluation.enums.StandardRecommendationGroup;
import uk.co.quidos.gdsap.evaluation.enums.StandardRecommendationType;


/**
 * 标准的改进建议
 * @author peng.shi
 */
public class StandardRecommendation
{	
	private int code;
	private String summary;
	private String item;
	private String heading;
	private String description;
	private StandardRecommendationType standardRecommendationType;
	private StandardRecommendationType gdip_standardRecommendationType;
	private String situation;
	private String conditions;
	private String situation_SCT;
	private String conditions_SCT;
	private StandardRecommendationGroup standardRecommendationGroup;
	private List<StandardValue> standardValues;
	private List<StandardValue> gdipInputValues;
	private List<StandardOption> standardOptions;
	private List<StandardOption> gdip_standardOptions;
	private SolutionType solutionType;
	private boolean selected = false;
	
	//用于页面显示的提示申明
	private String measureDeclarationEAW;
	private String measureDeclarationSCT;
	
	public StandardRecommendationGroup getStandardRecommendationGroup()
	{
		return standardRecommendationGroup;
	}
	public void setStandardRecommendationGroup(StandardRecommendationGroup standardRecommendationGroup)
	{
		this.standardRecommendationGroup = standardRecommendationGroup;
	}
	public String getItem()
	{
		return item;
	}
	public void setItem(String item)
	{
		this.item = item;
	}
	public List<StandardOption> getStandardOptions()
	{
		return standardOptions;
	}
	public void setStandardOptions(List<StandardOption> standardOptions)
	{
		this.standardOptions = standardOptions;
	}
	public List<StandardOption> getGdip_standardOptions() {
		return gdip_standardOptions;
	}
	public void setGdip_standardOptions(List<StandardOption> gdip_standardOptions) {
		this.gdip_standardOptions = gdip_standardOptions;
	}
	public int getCode()
	{
		return code;
	}
	public String getSummary()
	{
		return summary;
	}
	public String getHeading()
	{
		return heading;
	}
	public String getDescription()
	{
		return description;
	}
	public StandardRecommendationType getStandardRecommendationType()
	{
		return standardRecommendationType;
	}
	public String getConditions()
	{
		return conditions;
	}
	public void setCode(int code)
	{
		this.code = code;
	}
	public void setSummary(String summary)
	{
		this.summary = summary;
	}
	public void setHeading(String heading)
	{
		this.heading = heading;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public void setStandardRecommendationType(StandardRecommendationType standardRecommendationType)
	{
		this.standardRecommendationType = standardRecommendationType;
	}
	public StandardRecommendationType getGdip_standardRecommendationType() {
		return gdip_standardRecommendationType;
	}
	public void setGdip_standardRecommendationType(
			StandardRecommendationType gdip_standardRecommendationType) {
		this.gdip_standardRecommendationType = gdip_standardRecommendationType;
	}
	public void setConditions(String conditions)
	{
		this.conditions = conditions;
	}
	public String getSituation()
	{
		return situation;
	}
	public void setSituation(String situation)
	{
		this.situation = situation;
	}
	
	public List<StandardValue> getStandardValues() {
		return standardValues;
	}
	public void setStandardValues(List<StandardValue> standardValues) {
		this.standardValues = standardValues;
	}
	public List<StandardValue> getGdipInputValues() {
		return gdipInputValues;
	}
	public void setGdipInputValues(List<StandardValue> gdipInputValues) {
		this.gdipInputValues = gdipInputValues;
	}
	public String getItemUniqueGroupName()
	{
		Assert.hasText(this.getItem());
		ItemUniqueGroup[] itemUniqueGroups = ItemUniqueGroup.values();
		for (int i=0;i<itemUniqueGroups.length;i++)
		{
			String[] items = itemUniqueGroups[i].getItems();
			for (int n=0;n<items.length;n++)
			{
				if (items[n].equalsIgnoreCase(this.getItem()))
				{
					return itemUniqueGroups[i].getGroupName();
				}
			}
		}
		return null;
	}
	
	@Override
	public int hashCode()
	{
		return new Integer(this.code).hashCode();
	}
	@Override
	public boolean equals(Object o)
	{
		if (o == null) {
			return false;
		}
		if (this == o) {
			return true;
		}
		if (this.hashCode() == o.hashCode()) {
			return true;
		}

		if (o instanceof StandardRecommendation) {
			StandardRecommendation sr = (StandardRecommendation) o;
			if (this.getCode() == sr.getCode()) {
				return true;
			}
		}
		return false;
	}
	public SolutionType getSolutionType() {
		return solutionType;
	}
	public void setSolutionType(SolutionType solutionType) {
		this.solutionType = solutionType;
	}
	public String getMeasureDeclarationEAW() {
		return measureDeclarationEAW;
	}
	public void setMeasureDeclarationEAW(String measureDeclarationEAW) {
		this.measureDeclarationEAW = measureDeclarationEAW;
	}
	public String getMeasureDeclarationSCT() {
		return measureDeclarationSCT;
	}
	public void setMeasureDeclarationSCT(String measureDeclarationSCT) {
		this.measureDeclarationSCT = measureDeclarationSCT;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getSituation_SCT() {
		return situation_SCT;
	}
	public void setSituation_SCT(String situation_SCT) {
		this.situation_SCT = situation_SCT;
	}
	public String getConditions_SCT() {
		return conditions_SCT;
	}
	public void setConditions_SCT(String conditions_SCT) {
		this.conditions_SCT = conditions_SCT;
	}
}
