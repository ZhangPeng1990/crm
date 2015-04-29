package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapEvalEnergyUse extends AbstractDO
{
	private static final long serialVersionUID = -1636177073706147806L;
	
	private Long reportId;
    private Integer energyRatingCurrent;
    private Integer energyRatingPotential;
    private Integer energyRatingAverage;
    private Integer environmentalImpactCurrent;
    private Integer environmentalImpactPotential;
    private Float energyConsumptionCurrent;
    private Float energyConsumptionPotential;
    private Float co2EmissionsCurrent;
    private Float co2EmissionsCurrentPerFloorArea;
    private Float co2EmissionsPotential;
    private Float lightingCostCurrent;
    private Float lightingCostPotential;
    private Float heatingCostCurrent;
    private Float heatingCostPotential;
    private Float hotWaterCostCurrent;
    private Float hotWaterCostPotential;

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Integer getEnergyRatingCurrent() {
        return energyRatingCurrent;
    }

    public void setEnergyRatingCurrent(Integer energyRatingCurrent) {
        this.energyRatingCurrent = energyRatingCurrent;
    }

    public Integer getEnergyRatingPotential() {
        return energyRatingPotential;
    }

    public void setEnergyRatingPotential(Integer energyRatingPotential) {
        this.energyRatingPotential = energyRatingPotential;
    }

    public Integer getEnergyRatingAverage() {
        return energyRatingAverage;
    }

    public void setEnergyRatingAverage(Integer energyRatingAverage) {
        this.energyRatingAverage = energyRatingAverage;
    }

    public Integer getEnvironmentalImpactCurrent() {
        return environmentalImpactCurrent;
    }

    public void setEnvironmentalImpactCurrent(Integer environmentalImpactCurrent) {
        this.environmentalImpactCurrent = environmentalImpactCurrent;
    }

    public Integer getEnvironmentalImpactPotential() {
        return environmentalImpactPotential;
    }

    public void setEnvironmentalImpactPotential(Integer environmentalImpactPotential) {
        this.environmentalImpactPotential = environmentalImpactPotential;
    }

    public Float getEnergyConsumptionCurrent() {
        return energyConsumptionCurrent;
    }

    public void setEnergyConsumptionCurrent(Float energyConsumptionCurrent) {
        this.energyConsumptionCurrent = energyConsumptionCurrent;
    }

    public Float getEnergyConsumptionPotential() {
        return energyConsumptionPotential;
    }

    public void setEnergyConsumptionPotential(Float energyConsumptionPotential) {
        this.energyConsumptionPotential = energyConsumptionPotential;
    }

    public Float getCo2EmissionsCurrent() {
        return co2EmissionsCurrent;
    }

    public void setCo2EmissionsCurrent(Float co2EmissionsCurrent) {
        this.co2EmissionsCurrent = co2EmissionsCurrent;
    }

    public Float getCo2EmissionsCurrentPerFloorArea() {
        return co2EmissionsCurrentPerFloorArea;
    }

    public void setCo2EmissionsCurrentPerFloorArea(Float co2EmissionsCurrentPerFloorArea) {
        this.co2EmissionsCurrentPerFloorArea = co2EmissionsCurrentPerFloorArea;
    }

    public Float getCo2EmissionsPotential() {
        return co2EmissionsPotential;
    }

    public void setCo2EmissionsPotential(Float co2EmissionsPotential) {
        this.co2EmissionsPotential = co2EmissionsPotential;
    }

    public Float getLightingCostCurrent() {
        return lightingCostCurrent;
    }

    public void setLightingCostCurrent(Float lightingCostCurrent) {
        this.lightingCostCurrent = lightingCostCurrent;
    }

    public Float getLightingCostPotential() {
        return lightingCostPotential;
    }

    public void setLightingCostPotential(Float lightingCostPotential) {
        this.lightingCostPotential = lightingCostPotential;
    }

    public Float getHeatingCostCurrent() {
        return heatingCostCurrent;
    }

    public void setHeatingCostCurrent(Float heatingCostCurrent) {
        this.heatingCostCurrent = heatingCostCurrent;
    }

    public Float getHeatingCostPotential() {
        return heatingCostPotential;
    }

    public void setHeatingCostPotential(Float heatingCostPotential) {
        this.heatingCostPotential = heatingCostPotential;
    }

    public Float getHotWaterCostCurrent() {
        return hotWaterCostCurrent;
    }

    public void setHotWaterCostCurrent(Float hotWaterCostCurrent) {
        this.hotWaterCostCurrent = hotWaterCostCurrent;
    }

    public Float getHotWaterCostPotential() {
        return hotWaterCostPotential;
    }

    public void setHotWaterCostPotential(Float hotWaterCostPotential) {
        this.hotWaterCostPotential = hotWaterCostPotential;
    }
}