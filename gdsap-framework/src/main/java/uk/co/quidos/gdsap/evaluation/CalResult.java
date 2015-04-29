/**
 * 
 */
package uk.co.quidos.gdsap.evaluation;

import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 */
public class CalResult extends AbsBusinessObject
{
	private static final long serialVersionUID = 3623580992722228789L;
	private Solution solution;
	private Float actualHousehold;
	private Float typicalHousehold;
	private Float electricitySavings;
	private Float gasSavings;
	private Float otherFuelSavings;
	private Float percentHeating;
	private Float percentHotWater;
	private Float costReduction;
	private Float commEnergyCost;
	private Float commEnergyUse;
	private Float commEnergyScInput;
	private Float commEnergyScTable;
	private Float commEnergyUpInput;
	private Float commEnergyUpTable;
	
	private Float commChpEnergyCost;
	private Float commChpEnergyUse;
	private Float commChpEnergyScInput;
	private Float commChpEnergyScTable;
	private Float commChpEnergyUpInput;
	private Float commChpEnergyUpTable;
	private Integer commChpUnitInput;
	private Integer commChpUnitTable;
	private Integer commChpFuelCode;
	
	private Float eleSdCost;
	private Float eleSdUse;
	private Float eleSdScInput;
	private Float eleSdScTable;
	private Float eleSdUpInput;
	private Float eleSdUpTable;
	private Float eleOpCostHighrate;
	private Float eleOpUseHighrate;
	private Float eleOpScInputHighrate;
	private Float eleOpScTableHighrate;
	private Float eleOpUpInputHighrate;
	private Float eleOpUpTableHighrate;
	private Float eleCostLowrate;
	private Float eleUseLowrate;
	private Float eleScInputLowrate;
	private Float eleScTableLowrate;
	private Float eleUpInputLowrate;
	private Float eleUpTableLowrate;
	private Float mainsGasCost;
	private Float mainsGasUse;
	private Float mainsGasScInput;
	private Float mainsGasScTable;
	private Float mainsGasUpInput;
	private Float mainsGasUpTable;
	private Float spaceHeating;
	private Float waterHeating;
	private Float energyTotal;
	private Integer stOccupants;
	private String stAverageHours;
	private Integer stThermostatSetting;
	private Integer stUnheatedRooms;
	private YesNo stRecommendShowersable;
	private YesNo stRecommendOutsideable;
	private Integer acOccupants;
	private String acAverageHours;
	private Integer acThermostatSetting;
	private Integer acUnheatedRooms;
	private YesNo acRecommendShowersable;
	private YesNo acRecommendOutsideable;
	private Integer commFuelCode;
    private Integer mgFuelCode;
    private Integer eleStFuelCode;
    private Integer eleHFuelCode;
    private Integer eleLFuelCode;
    private Float totalEleSaving;
    private Float totalGasSaving;
    private Float totalOtherFuelSaving;
    private Integer impBoilerFuel;
    private Integer impSingleGlazedPercentage;
    
    private Integer commUnitTable;
	private Integer mgUnitTable;
	private Integer eleSdUnitTable;
	private Integer eleSd24UnitTable;
	private Integer eleHUnitTable;
	private Integer eleLUnitTable;
	private Integer commUnitInput;
	private Integer mgUnitInput;
	private Integer eleSdUnitInput;
	private Integer eleSd24UnitInput;
	private Integer eleHUnitInput;
	private Integer eleLUnitInput;
	
	private Float eleSd24Cost;
	private Float eleSd24Use;
	private Float eleSd24ScInput;
	private Float eleSd24ScTable;
	private Float eleSd24UpInput;
	private Float eleSd24UpTable;
	private Integer eleSt24FuelCode;

	public Float getCommChpEnergyCost()
	{
		return commChpEnergyCost;
	}

	public void setCommChpEnergyCost(Float commChpEnergyCost)
	{
		this.commChpEnergyCost = commChpEnergyCost;
	}

	public Float getCommChpEnergyUse()
	{
		return commChpEnergyUse;
	}

	public void setCommChpEnergyUse(Float commChpEnergyUse)
	{
		this.commChpEnergyUse = commChpEnergyUse;
	}

	public Float getCommChpEnergyScInput()
	{
		return commChpEnergyScInput;
	}

	public void setCommChpEnergyScInput(Float commChpEnergyScInput)
	{
		this.commChpEnergyScInput = commChpEnergyScInput;
	}

	public Float getCommChpEnergyScTable()
	{
		return commChpEnergyScTable;
	}

	public void setCommChpEnergyScTable(Float commChpEnergyScTable)
	{
		this.commChpEnergyScTable = commChpEnergyScTable;
	}

	public Float getCommChpEnergyUpInput()
	{
		return commChpEnergyUpInput;
	}

	public void setCommChpEnergyUpInput(Float commChpEnergyUpInput)
	{
		this.commChpEnergyUpInput = commChpEnergyUpInput;
	}

	public Float getCommChpEnergyUpTable()
	{
		return commChpEnergyUpTable;
	}

	public void setCommChpEnergyUpTable(Float commChpEnergyUpTable)
	{
		this.commChpEnergyUpTable = commChpEnergyUpTable;
	}

	public Integer getCommChpUnitInput()
	{
		return commChpUnitInput;
	}

	public void setCommChpUnitInput(Integer commChpUnitInput)
	{
		this.commChpUnitInput = commChpUnitInput;
	}

	public Integer getCommChpUnitTable()
	{
		return commChpUnitTable;
	}

	public void setCommChpUnitTable(Integer commChpUnitTable)
	{
		this.commChpUnitTable = commChpUnitTable;
	}

	public Integer getCommChpFuelCode()
	{
		return commChpFuelCode;
	}

	public void setCommChpFuelCode(Integer commChpFuelCode)
	{
		this.commChpFuelCode = commChpFuelCode;
	}

	public Integer getEleSt24FuelCode()
	{
		return eleSt24FuelCode;
	}

	public void setEleSt24FuelCode(Integer eleSt24FuelCode)
	{
		this.eleSt24FuelCode = eleSt24FuelCode;
	}

	public Integer getCommUnitTable()
	{
		return commUnitTable;
	}

	public void setCommUnitTable(Integer commUnitTable)
	{
		this.commUnitTable = commUnitTable;
	}

	public Integer getMgUnitTable()
	{
		return mgUnitTable;
	}

	public void setMgUnitTable(Integer mgUnitTable)
	{
		this.mgUnitTable = mgUnitTable;
	}

	public Integer getEleSdUnitTable()
	{
		return eleSdUnitTable;
	}

	public void setEleSdUnitTable(Integer eleSdUnitTable)
	{
		this.eleSdUnitTable = eleSdUnitTable;
	}

	public Integer getEleSd24UnitTable()
	{
		return eleSd24UnitTable;
	}

	public void setEleSd24UnitTable(Integer eleSd24UnitTable)
	{
		this.eleSd24UnitTable = eleSd24UnitTable;
	}

	public Integer getEleHUnitTable()
	{
		return eleHUnitTable;
	}

	public void setEleHUnitTable(Integer eleHUnitTable)
	{
		this.eleHUnitTable = eleHUnitTable;
	}

	public Integer getEleLUnitTable()
	{
		return eleLUnitTable;
	}

	public void setEleLUnitTable(Integer eleLUnitTable)
	{
		this.eleLUnitTable = eleLUnitTable;
	}

	public Integer getCommUnitInput()
	{
		return commUnitInput;
	}

	public void setCommUnitInput(Integer commUnitInput)
	{
		this.commUnitInput = commUnitInput;
	}

	public Integer getMgUnitInput()
	{
		return mgUnitInput;
	}

	public void setMgUnitInput(Integer mgUnitInput)
	{
		this.mgUnitInput = mgUnitInput;
	}

	public Integer getEleSdUnitInput()
	{
		return eleSdUnitInput;
	}

	public void setEleSdUnitInput(Integer eleSdUnitInput)
	{
		this.eleSdUnitInput = eleSdUnitInput;
	}

	public Integer getEleSd24UnitInput()
	{
		return eleSd24UnitInput;
	}

	public void setEleSd24UnitInput(Integer eleSd24UnitInput)
	{
		this.eleSd24UnitInput = eleSd24UnitInput;
	}

	public Integer getEleHUnitInput()
	{
		return eleHUnitInput;
	}

	public void setEleHUnitInput(Integer eleHUnitInput)
	{
		this.eleHUnitInput = eleHUnitInput;
	}

	public Integer getEleLUnitInput()
	{
		return eleLUnitInput;
	}

	public void setEleLUnitInput(Integer eleLUnitInput)
	{
		this.eleLUnitInput = eleLUnitInput;
	}

	public Float getEleSd24Cost()
	{
		return eleSd24Cost;
	}

	public void setEleSd24Cost(Float eleSd24Cost)
	{
		this.eleSd24Cost = eleSd24Cost;
	}

	public Float getEleSd24Use()
	{
		return eleSd24Use;
	}

	public void setEleSd24Use(Float eleSd24Use)
	{
		this.eleSd24Use = eleSd24Use;
	}

	public Float getEleSd24ScInput()
	{
		return eleSd24ScInput;
	}

	public void setEleSd24ScInput(Float eleSd24ScInput)
	{
		this.eleSd24ScInput = eleSd24ScInput;
	}

	public Float getEleSd24ScTable()
	{
		return eleSd24ScTable;
	}

	public void setEleSd24ScTable(Float eleSd24ScTable)
	{
		this.eleSd24ScTable = eleSd24ScTable;
	}

	public Float getEleSd24UpInput()
	{
		return eleSd24UpInput;
	}

	public void setEleSd24UpInput(Float eleSd24UpInput)
	{
		this.eleSd24UpInput = eleSd24UpInput;
	}

	public Float getEleSd24UpTable()
	{
		return eleSd24UpTable;
	}

	public void setEleSd24UpTable(Float eleSd24UpTable)
	{
		this.eleSd24UpTable = eleSd24UpTable;
	}

	public Integer getImpBoilerFuel()
	{
		return impBoilerFuel;
	}

	public void setImpBoilerFuel(Integer impBoilerFuel)
	{
		this.impBoilerFuel = impBoilerFuel;
	}

	public Integer getImpSingleGlazedPercentage()
	{
		return impSingleGlazedPercentage;
	}

	public void setImpSingleGlazedPercentage(Integer impSingleGlazedPercentage)
	{
		this.impSingleGlazedPercentage = impSingleGlazedPercentage;
	}

	public Float getTotalEleSaving()
	{
		return totalEleSaving;
	}

	public void setTotalEleSaving(Float totalEleSaving)
	{
		this.totalEleSaving = totalEleSaving;
	}

	public Float getTotalGasSaving()
	{
		return totalGasSaving;
	}

	public void setTotalGasSaving(Float totalGasSaving)
	{
		this.totalGasSaving = totalGasSaving;
	}

	public Float getTotalOtherFuelSaving()
	{
		return totalOtherFuelSaving;
	}

	public void setTotalOtherFuelSaving(Float totalOtherFuelSaving)
	{
		this.totalOtherFuelSaving = totalOtherFuelSaving;
	}

	public Integer getCommFuelCode()
	{
		return commFuelCode;
	}

	public void setCommFuelCode(Integer commFuelCode)
	{
		this.commFuelCode = commFuelCode;
	}

	public Integer getMgFuelCode()
	{
		return mgFuelCode;
	}

	public void setMgFuelCode(Integer mgFuelCode)
	{
		this.mgFuelCode = mgFuelCode;
	}

	public Integer getEleStFuelCode()
	{
		return eleStFuelCode;
	}

	public void setEleStFuelCode(Integer eleStFuelCode)
	{
		this.eleStFuelCode = eleStFuelCode;
	}

	public Integer getEleHFuelCode()
	{
		return eleHFuelCode;
	}

	public void setEleHFuelCode(Integer eleHFuelCode)
	{
		this.eleHFuelCode = eleHFuelCode;
	}

	public Integer getEleLFuelCode()
	{
		return eleLFuelCode;
	}

	public void setEleLFuelCode(Integer eleLFuelCode)
	{
		this.eleLFuelCode = eleLFuelCode;
	}

	public Float getCommEnergyScInput()
	{
		return commEnergyScInput;
	}

	public void setCommEnergyScInput(Float commEnergyScInput)
	{
		this.commEnergyScInput = commEnergyScInput;
	}

	public Float getCommEnergyScTable()
	{
		return commEnergyScTable;
	}

	public void setCommEnergyScTable(Float commEnergyScTable)
	{
		this.commEnergyScTable = commEnergyScTable;
	}

	public Float getCommEnergyUpInput()
	{
		return commEnergyUpInput;
	}

	public void setCommEnergyUpInput(Float commEnergyUpInput)
	{
		this.commEnergyUpInput = commEnergyUpInput;
	}

	public Float getCommEnergyUpTable()
	{
		return commEnergyUpTable;
	}

	public void setCommEnergyUpTable(Float commEnergyUpTable)
	{
		this.commEnergyUpTable = commEnergyUpTable;
	}

	public Float getEleSdScInput()
	{
		return eleSdScInput;
	}

	public void setEleSdScInput(Float eleSdScInput)
	{
		this.eleSdScInput = eleSdScInput;
	}

	public Float getEleSdScTable()
	{
		return eleSdScTable;
	}

	public void setEleSdScTable(Float eleSdScTable)
	{
		this.eleSdScTable = eleSdScTable;
	}

	public Float getEleSdUpInput()
	{
		return eleSdUpInput;
	}

	public void setEleSdUpInput(Float eleSdUpInput)
	{
		this.eleSdUpInput = eleSdUpInput;
	}

	public Float getEleSdUpTable()
	{
		return eleSdUpTable;
	}

	public void setEleSdUpTable(Float eleSdUpTable)
	{
		this.eleSdUpTable = eleSdUpTable;
	}

	public Float getEleOpScInputHighrate()
	{
		return eleOpScInputHighrate;
	}

	public void setEleOpScInputHighrate(Float eleOpScInputHighrate)
	{
		this.eleOpScInputHighrate = eleOpScInputHighrate;
	}

	public Float getEleOpScTableHighrate()
	{
		return eleOpScTableHighrate;
	}

	public void setEleOpScTableHighrate(Float eleOpScTableHighrate)
	{
		this.eleOpScTableHighrate = eleOpScTableHighrate;
	}

	public Float getEleOpUpInputHighrate()
	{
		return eleOpUpInputHighrate;
	}

	public void setEleOpUpInputHighrate(Float eleOpUpInputHighrate)
	{
		this.eleOpUpInputHighrate = eleOpUpInputHighrate;
	}

	public Float getEleOpUpTableHighrate()
	{
		return eleOpUpTableHighrate;
	}

	public void setEleOpUpTableHighrate(Float eleOpUpTableHighrate)
	{
		this.eleOpUpTableHighrate = eleOpUpTableHighrate;
	}

	public Float getEleScInputLowrate()
	{
		return eleScInputLowrate;
	}

	public void setEleScInputLowrate(Float eleScInputLowrate)
	{
		this.eleScInputLowrate = eleScInputLowrate;
	}

	public Float getEleScTableLowrate()
	{
		return eleScTableLowrate;
	}

	public void setEleScTableLowrate(Float eleScTableLowrate)
	{
		this.eleScTableLowrate = eleScTableLowrate;
	}

	public Float getEleUpInputLowrate()
	{
		return eleUpInputLowrate;
	}

	public void setEleUpInputLowrate(Float eleUpInputLowrate)
	{
		this.eleUpInputLowrate = eleUpInputLowrate;
	}

	public Float getEleUpTableLowrate()
	{
		return eleUpTableLowrate;
	}

	public void setEleUpTableLowrate(Float eleUpTableLowrate)
	{
		this.eleUpTableLowrate = eleUpTableLowrate;
	}

	public Float getMainsGasScInput()
	{
		return mainsGasScInput;
	}

	public void setMainsGasScInput(Float mainsGasScInput)
	{
		this.mainsGasScInput = mainsGasScInput;
	}

	public Float getMainsGasScTable()
	{
		return mainsGasScTable;
	}

	public void setMainsGasScTable(Float mainsGasScTable)
	{
		this.mainsGasScTable = mainsGasScTable;
	}

	public Float getMainsGasUpInput()
	{
		return mainsGasUpInput;
	}

	public void setMainsGasUpInput(Float mainsGasUpInput)
	{
		this.mainsGasUpInput = mainsGasUpInput;
	}

	public Float getMainsGasUpTable()
	{
		return mainsGasUpTable;
	}

	public void setMainsGasUpTable(Float mainsGasUpTable)
	{
		this.mainsGasUpTable = mainsGasUpTable;
	}

	public Float getSpaceHeating()
	{
		return spaceHeating;
	}

	public void setSpaceHeating(Float spaceHeating)
	{
		this.spaceHeating = spaceHeating;
	}

	public Float getWaterHeating()
	{
		return waterHeating;
	}

	public void setWaterHeating(Float waterHeating)
	{
		this.waterHeating = waterHeating;
	}

	public Float getEnergyTotal()
	{
		return energyTotal;
	}

	public void setEnergyTotal(Float energyTotal)
	{
		this.energyTotal = energyTotal;
	}

	public Integer getStOccupants()
	{
		return stOccupants;
	}

	public void setStOccupants(Integer stOccupants)
	{
		this.stOccupants = stOccupants;
	}

	public String getStAverageHours()
	{
		return stAverageHours;
	}

	public void setStAverageHours(String stAverageHours)
	{
		this.stAverageHours = stAverageHours;
	}

	public Integer getStThermostatSetting()
	{
		return stThermostatSetting;
	}

	public void setStThermostatSetting(Integer stThermostatSetting)
	{
		this.stThermostatSetting = stThermostatSetting;
	}

	public Integer getStUnheatedRooms()
	{
		return stUnheatedRooms;
	}

	public void setStUnheatedRooms(Integer stUnheatedRooms)
	{
		this.stUnheatedRooms = stUnheatedRooms;
	}

	public YesNo getStRecommendShowersable()
	{
		return stRecommendShowersable;
	}

	public void setStRecommendShowersable(YesNo stRecommendShowersable)
	{
		this.stRecommendShowersable = stRecommendShowersable;
	}

	public YesNo getStRecommendOutsideable()
	{
		return stRecommendOutsideable;
	}

	public void setStRecommendOutsideable(YesNo stRecommendOutsideable)
	{
		this.stRecommendOutsideable = stRecommendOutsideable;
	}

	public Integer getAcOccupants()
	{
		return acOccupants;
	}

	public void setAcOccupants(Integer acOccupants)
	{
		this.acOccupants = acOccupants;
	}

	public String getAcAverageHours()
	{
		return acAverageHours;
	}

	public void setAcAverageHours(String acAverageHours)
	{
		this.acAverageHours = acAverageHours;
	}

	public Integer getAcThermostatSetting()
	{
		return acThermostatSetting;
	}

	public void setAcThermostatSetting(Integer acThermostatSetting)
	{
		this.acThermostatSetting = acThermostatSetting;
	}

	public Integer getAcUnheatedRooms()
	{
		return acUnheatedRooms;
	}

	public void setAcUnheatedRooms(Integer acUnheatedRooms)
	{
		this.acUnheatedRooms = acUnheatedRooms;
	}

	public YesNo getAcRecommendShowersable()
	{
		return acRecommendShowersable;
	}

	public void setAcRecommendShowersable(YesNo acRecommendShowersable)
	{
		this.acRecommendShowersable = acRecommendShowersable;
	}

	public YesNo getAcRecommendOutsideable()
	{
		return acRecommendOutsideable;
	}

	public void setAcRecommendOutsideable(YesNo acRecommendOutsideable)
	{
		this.acRecommendOutsideable = acRecommendOutsideable;
	}

	public Solution getSolution()
	{
		return solution;
	}

	public Float getActualHousehold()
	{
		return actualHousehold;
	}

	public Float getTypicalHousehold()
	{
		return typicalHousehold;
	}

	public Float getElectricitySavings()
	{
		return electricitySavings;
	}

	public Float getGasSavings()
	{
		return gasSavings;
	}

	public Float getOtherFuelSavings()
	{
		return otherFuelSavings;
	}

	public Float getPercentHeating()
	{
		return percentHeating;
	}

	public Float getPercentHotWater()
	{
		return percentHotWater;
	}

	public Float getCostReduction()
	{
		return costReduction;
	}

	public Float getCommEnergyCost()
	{
		return commEnergyCost;
	}

	public Float getCommEnergyUse()
	{
		return commEnergyUse;
	}

	public Float getEleSdCost()
	{
		return eleSdCost;
	}

	public Float getEleSdUse()
	{
		return eleSdUse;
	}

	public Float getEleOpCostHighrate()
	{
		return eleOpCostHighrate;
	}

	public Float getEleOpUseHighrate()
	{
		return eleOpUseHighrate;
	}

	public Float getEleCostLowrate()
	{
		return eleCostLowrate;
	}

	public Float getEleUseLowrate()
	{
		return eleUseLowrate;
	}

	public Float getMainsGasCost()
	{
		return mainsGasCost;
	}

	public Float getMainsGasUse()
	{
		return mainsGasUse;
	}

	public void setSolution(Solution solution)
	{
		this.solution = solution;
	}

	public void setActualHousehold(Float actualHousehold)
	{
		this.actualHousehold = actualHousehold;
	}

	public void setTypicalHousehold(Float typicalHousehold)
	{
		this.typicalHousehold = typicalHousehold;
	}

	public void setElectricitySavings(Float electricitySavings)
	{
		this.electricitySavings = electricitySavings;
	}

	public void setGasSavings(Float gasSavings)
	{
		this.gasSavings = gasSavings;
	}

	public void setOtherFuelSavings(Float otherFuelSavings)
	{
		this.otherFuelSavings = otherFuelSavings;
	}

	public void setPercentHeating(Float percentHeating)
	{
		this.percentHeating = percentHeating;
	}

	public void setPercentHotWater(Float percentHotWater)
	{
		this.percentHotWater = percentHotWater;
	}

	public void setCostReduction(Float costReduction)
	{
		this.costReduction = costReduction;
	}

	public void setCommEnergyCost(Float commEnergyCost)
	{
		this.commEnergyCost = commEnergyCost;
	}

	public void setCommEnergyUse(Float commEnergyUse)
	{
		this.commEnergyUse = commEnergyUse;
	}

	public void setEleSdCost(Float eleSdCost)
	{
		this.eleSdCost = eleSdCost;
	}

	public void setEleSdUse(Float eleSdUse)
	{
		this.eleSdUse = eleSdUse;
	}

	public void setEleOpCostHighrate(Float eleOpCostHighrate)
	{
		this.eleOpCostHighrate = eleOpCostHighrate;
	}

	public void setEleOpUseHighrate(Float eleOpUseHighrate)
	{
		this.eleOpUseHighrate = eleOpUseHighrate;
	}

	public void setEleCostLowrate(Float eleCostLowrate)
	{
		this.eleCostLowrate = eleCostLowrate;
	}

	public void setEleUseLowrate(Float eleUseLowrate)
	{
		this.eleUseLowrate = eleUseLowrate;
	}

	public void setMainsGasCost(Float mainsGasCost)
	{
		this.mainsGasCost = mainsGasCost;
	}

	public void setMainsGasUse(Float mainsGasUse)
	{
		this.mainsGasUse = mainsGasUse;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject#getId()
	 */
	@Override
	public Long getId()
	{
		if (this.getSolution() != null)
		{
			return this.solution.getId();
		}
		return null;
	}

}
