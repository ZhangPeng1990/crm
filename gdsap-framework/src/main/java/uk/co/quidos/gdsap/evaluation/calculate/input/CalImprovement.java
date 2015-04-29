package uk.co.quidos.gdsap.evaluation.calculate.input;

import java.util.List;

public class CalImprovement {

	private int	improvementNo;
	private String categoryName;
	private int 		state;//状态	
	private double[]	indicativeCost;
	private double			typicalSavingsPerYear;
	private double			customSavingsPerYear;
	private int			energyEfficiency;
	private int			environmentalImpact;
	private int category;
	private double[] inUse;
	
	List<Integer> constructionLodgeCode;
	public int getImprovementNo() {
		return improvementNo;
	}
	public void setImprovementNo(int improvementNo) {
		this.improvementNo = improvementNo;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public double[] getIndicativeCost() {
		return indicativeCost;
	}
	public void setIndicativeCost(double[] indicativeCost) {
		this.indicativeCost = indicativeCost;
	}
	public double getTypicalSavingsPerYear() {
		return typicalSavingsPerYear;
	}
	public void setTypicalSavingsPerYear(double typicalSavingsPerYear) {
		this.typicalSavingsPerYear = typicalSavingsPerYear;
	}
	public double getCustomSavingsPerYear() {
		return this.customSavingsPerYear;
	}
	public void setCustomSavingsPerYear(double customSavingsPerYear) {
		this.customSavingsPerYear = customSavingsPerYear;
	}
	public int getEnergyEfficiency() {
		return energyEfficiency;
	}
	public void setEnergyEfficiency(int energyEfficiency) {
		this.energyEfficiency = energyEfficiency;
	}
	public int getEnvironmentalImpact() {
		return environmentalImpact;
	}
	public void setEnvironmentalImpact(int environmentalImpact) {
		this.environmentalImpact = environmentalImpact;
	}
	public double[] getInUse() {
		return inUse;
	}
	public void setInUse(double[] inUse) {
		this.inUse = inUse;
	}
	public List<Integer> getConstructionLodgeCode() {
		return constructionLodgeCode;
	}
	public void setConstructionLodgeCode(List<Integer> constructionLodgeCode) {
		this.constructionLodgeCode = constructionLodgeCode;
	}
	public int getCategory() {
		return category;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	
}
