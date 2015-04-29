/**
 * 
 */
package uk.co.quidos.gdsap.evaluation;

import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 *
 */
public class EnergyUse extends AbsBusinessObject
{

	private static final long serialVersionUID = -6007210073633041987L;

	private Report report;
    private int energyRatingCurrent;
    private int energyRatingPotential;
    private int energyRatingAverage;
    private int environmentalImpactCurrent;
    private int environmentalImpactPotential;
    private float energyConsumptionCurrent;
    private float energyConsumptionPotential;
    private float co2EmissionsCurrent;
    private float co2EmissionsCurrentPerFloorArea;
    private float co2EmissionsPotential;
    private float lightingCostCurrent;
    private float lightingCostPotential;
    private float heatingCostCurrent;
    private float heatingCostPotential;
    private float hotWaterCostCurrent;
    private float hotWaterCostPotential;
    
	public Report getId() {
		return report;
	}
	public void setId(Report report) {
		this.report = report;
	}
	public int getEnergyRatingCurrent() {
		return energyRatingCurrent;
	}
	public void setEnergyRatingCurrent(int energyRatingCurrent) {
		this.energyRatingCurrent = energyRatingCurrent;
	}
	public int getEnergyRatingPotential() {
		return energyRatingPotential;
	}
	public void setEnergyRatingPotential(int energyRatingPotential) {
		this.energyRatingPotential = energyRatingPotential;
	}
	public int getEnergyRatingAverage() {
		return energyRatingAverage;
	}
	public void setEnergyRatingAverage(int energyRatingAverage) {
		this.energyRatingAverage = energyRatingAverage;
	}
	public int getEnvironmentalImpactCurrent() {
		return environmentalImpactCurrent;
	}
	public void setEnvironmentalImpactCurrent(int environmentalImpactCurrent) {
		this.environmentalImpactCurrent = environmentalImpactCurrent;
	}
	public int getEnvironmentalImpactPotential() {
		return environmentalImpactPotential;
	}
	public void setEnvironmentalImpactPotential(int environmentalImpactPotential) {
		this.environmentalImpactPotential = environmentalImpactPotential;
	}
	public float getEnergyConsumptionCurrent() {
		return energyConsumptionCurrent;
	}
	public void setEnergyConsumptionCurrent(float energyConsumptionCurrent) {
		this.energyConsumptionCurrent = energyConsumptionCurrent;
	}
	public float getEnergyConsumptionPotential() {
		return energyConsumptionPotential;
	}
	public void setEnergyConsumptionPotential(float energyConsumptionPotential) {
		this.energyConsumptionPotential = energyConsumptionPotential;
	}
	public float getCo2EmissionsCurrent() {
		return co2EmissionsCurrent;
	}
	public void setCo2EmissionsCurrent(float co2EmissionsCurrent) {
		this.co2EmissionsCurrent = co2EmissionsCurrent;
	}
	public float getCo2EmissionsCurrentPerFloorArea() {
		return co2EmissionsCurrentPerFloorArea;
	}
	public void setCo2EmissionsCurrentPerFloorArea(
			float co2EmissionsCurrentPerFloorArea) {
		this.co2EmissionsCurrentPerFloorArea = co2EmissionsCurrentPerFloorArea;
	}
	public float getCo2EmissionsPotential() {
		return co2EmissionsPotential;
	}
	public void setCo2EmissionsPotential(float co2EmissionsPotential) {
		this.co2EmissionsPotential = co2EmissionsPotential;
	}
	public float getLightingCostCurrent() {
		return lightingCostCurrent;
	}
	public void setLightingCostCurrent(float lightingCostCurrent) {
		this.lightingCostCurrent = lightingCostCurrent;
	}
	public float getLightingCostPotential() {
		return lightingCostPotential;
	}
	public void setLightingCostPotential(float lightingCostPotential) {
		this.lightingCostPotential = lightingCostPotential;
	}
	public float getHeatingCostCurrent() {
		return heatingCostCurrent;
	}
	public void setHeatingCostCurrent(float heatingCostCurrent) {
		this.heatingCostCurrent = heatingCostCurrent;
	}
	public float getHeatingCostPotential() {
		return heatingCostPotential;
	}
	public void setHeatingCostPotential(float heatingCostPotential) {
		this.heatingCostPotential = heatingCostPotential;
	}
	public float getHotWaterCostCurrent() {
		return hotWaterCostCurrent;
	}
	public void setHotWaterCostCurrent(float hotWaterCostCurrent) {
		this.hotWaterCostCurrent = hotWaterCostCurrent;
	}
	public float getHotWaterCostPotential() {
		return hotWaterCostPotential;
	}
	public void setHotWaterCostPotential(float hotWaterCostPotential) {
		this.hotWaterCostPotential = hotWaterCostPotential;
	}
    
    
}
