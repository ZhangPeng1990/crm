package uk.co.quidos.gdsap.evaluation.calculate.input;

import java.util.List;
import java.util.Map;

public class CalOutput {

	private CalRecommendation recommendation;
	private CalRecommendation_EPC recommendation_EPC;
	private Double actualHousehold;
	private Double typicalHousehold;
	private Double electricityRcost;
	private Double gasRcost;
	private Double otherRcost;
	private Double percentOfEnergyByHeating;
	private Double percentOfEnergyByHotWater;
	private Float spaceHeating;
	private Float waterHeating;
	private Float totalEnergyUse;
	private Float livingAreaTemperature;
	private Integer boilerFuel;
	private Integer existingSingleGlazedPercentage;
	
	private OccupancyParameters standardOccupancyParameters;
	private OccupancyParameters actOccupancyParameters;
	private CalFuelBillData calFuelPriceList;
	private Map<ImprovementIssue, Boolean> improvementIssue;
	private List<PerFuelSaving> perFuelSavingList;
	
	public CalRecommendation getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(CalRecommendation recommendation) {
		this.recommendation = recommendation;
	}

	public CalRecommendation_EPC getRecommendation_EPC() {
		return recommendation_EPC;
	}

	public void setRecommendation_EPC(CalRecommendation_EPC recommendation_EPC) {
		this.recommendation_EPC = recommendation_EPC;
	}

	public Double getActualHousehold() {
		return actualHousehold;
	}

	public void setActualHousehold(Double actualHousehold) {
		this.actualHousehold = actualHousehold;
	}

	public Double getTypicalHousehold() {
		return typicalHousehold;
	}

	public void setTypicalHousehold(Double typicalHousehold) {
		this.typicalHousehold = typicalHousehold;
	}

	public Double getElectricityRcost() {
		return electricityRcost;
	}

	public void setElectricityRcost(Double electricityRcost) {
		this.electricityRcost = electricityRcost;
	}

	public Double getGasRcost() {
		return gasRcost;
	}

	public void setGasRcost(Double gasRcost) {
		this.gasRcost = gasRcost;
	}

	public Double getOtherRcost() {
		return otherRcost;
	}

	public void setOtherRcost(Double otherRcost) {
		this.otherRcost = otherRcost;
	}

	public Double getPercentOfEnergyByHeating() {
		return percentOfEnergyByHeating;
	}

	public void setPercentOfEnergyByHeating(Double percentOfEnergyByHeating) {
		this.percentOfEnergyByHeating = percentOfEnergyByHeating;
	}

	public Double getPercentOfEnergyByHotWater() {
		return percentOfEnergyByHotWater;
	}

	public void setPercentOfEnergyByHotWater(Double percentOfEnergyByHotWater) {
		this.percentOfEnergyByHotWater = percentOfEnergyByHotWater;
	}

	public Float getSpaceHeating() {
		return spaceHeating;
	}

	public void setSpaceHeating(Float spaceHeating) {
		this.spaceHeating = spaceHeating;
	}

	public Float getWaterHeating() {
		return waterHeating;
	}

	public void setWaterHeating(Float waterHeating) {
		this.waterHeating = waterHeating;
	}

	public Float getTotalEnergyUse() {
		return totalEnergyUse;
	}

	public void setTotalEnergyUse(Float totalEnergyUse) {
		this.totalEnergyUse = totalEnergyUse;
	}

	public Float getLivingAreaTemperature() {
		return livingAreaTemperature;
	}

	public void setLivingAreaTemperature(Float livingAreaTemperature) {
		this.livingAreaTemperature = livingAreaTemperature;
	}

	public Integer getBoilerFuel() {
		return boilerFuel;
	}

	public void setBoilerFuel(Integer boilerFuel) {
		this.boilerFuel = boilerFuel;
	}

	public Integer getExistingSingleGlazedPercentage() {
		return existingSingleGlazedPercentage;
	}

	public void setExistingSingleGlazedPercentage(
			Integer existingSingleGlazedPercentage) {
		this.existingSingleGlazedPercentage = existingSingleGlazedPercentage;
	}

	public OccupancyParameters getStandardOccupancyParameters() {
		return standardOccupancyParameters;
	}

	public void setStandardOccupancyParameters(
			OccupancyParameters standardOccupancyParameters) {
		this.standardOccupancyParameters = standardOccupancyParameters;
	}

	public OccupancyParameters getActOccupancyParameters() {
		return actOccupancyParameters;
	}

	public void setActOccupancyParameters(OccupancyParameters actOccupancyParameters) {
		this.actOccupancyParameters = actOccupancyParameters;
	}

	public CalFuelBillData getCalFuelPriceList() {
		return calFuelPriceList;
	}

	public void setCalFuelPriceList(CalFuelBillData calFuelPriceList) {
		this.calFuelPriceList = calFuelPriceList;
	}

	public Map<ImprovementIssue, Boolean> getImprovementIssue() {
		return improvementIssue;
	}

	public void setImprovementIssue(Map<ImprovementIssue, Boolean> improvementIssue) {
		this.improvementIssue = improvementIssue;
	}

	public List<PerFuelSaving> getPerFuelSavingList() {
		return perFuelSavingList;
	}

	public void setPerFuelSavingList(List<PerFuelSaving> perFuelSavingList) {
		this.perFuelSavingList = perFuelSavingList;
	}
}
