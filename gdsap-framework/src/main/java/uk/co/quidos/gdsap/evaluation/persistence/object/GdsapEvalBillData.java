package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapEvalBillData extends AbstractDO
{
	
	private static final long serialVersionUID = 3639081243107967771L;

	private Long reportId;
	
	private Integer chFuelCode;
	
    private Integer chReliablityLevel;

    private Float chEnergyUsed;

    private Integer chPeriodSelect;

    private Float chPeriod;

    private Float chFixedCost;

    private Float chUnitPrice;

    private Integer vatable;

    private Integer mgReliablityLevel;

    private Float mgGasUsed;

    private Integer mgPeriodSelect;

    private Float mgPeriod;

    private Integer mgChargingBasis;

    private Integer mgStAmountSelect;

    private Float mgStAmount;

    private Float mgStUnitPrice;

    private Float mgTwInitialUnit;

    private Float mgTwUnits;

    private Integer mgTwUnitsSelected;

    private Float mgTwFollowOn;

    private Integer mgVatAble;

    private Integer etElectricityTariff;

    private Integer etStReliablityLevel;

    private Float etStElectricityUsed;

    private Float etStPeriod;

    private Integer etStPeriodSelect;

    private Integer etStVatable;

    private Integer etStChargingBasis;

    private Float etStStandingChargeAmount;

    private Integer etStStandingChargeAmountSelect;

    private Float etStUnitPrice;

    private Float etStInitialUnitPrice;

    private Float etStUnitsAtThisPrice;

    private Integer etStUnitsAtThisPriceSelect;

    private Float etStFollowonUnitPrice;

    private Integer etOffHReliablityLevel;

    private Float etOffHElectricityUsed;

    private Float etOffHPeriod;

    private Integer etOffHPeriodSelect;

    private Integer etOffHVatable;

    private Integer etOffHChargingBasis;

    private Float etOffHStandingChargeAmount;

    private Integer etOffHStandingChargeAmountSelect;

    private Float etOffHUnitPrice;

    private Float etOffHInitialUnitAmount;

    private Float etOffHUnitsAtThisPrice;

    private Integer etOffHUnitsAtThisPriceSelect;

    private Float etOffHFollow;

    private Integer etOffLReliablityLevel;

    private Float etOffLElectricityUsed;

    private Float etOffLPeriod;

    private Integer etOffLPeriodSelect;

    private Integer etOffLVatable;

    private Integer etOffLChargingBasis;

    private Float etOffLStandingChargeAmount;

    private Integer etOffLStandingChargeAmountSelect;

    private Float etOffLUnitPrice;

    private Float etOffLInitialUnitAmount;

    private Float etOffLUnitsAtThisPrice;

    private Integer etOffLUnitsAtThisPriceSelect;

    private Float etOffLFollow;

    private Integer etPvable;

    private Float etPvAmount;

    private Float etPvPeriod;

    private Integer etPvPeriodSelect;

    private Integer etWindable;

    private Float etWindAmount;

    private Float etWindPeriod;

    private Integer etWindPeriodSelect;

    private Integer etMicroable;

    private Float etMicroableAmount;

    private Float etMicroablePeriod;

    private Integer etMicroablePeriodSelect;

    private Integer chFixedCostSelected;

    private Integer chUnusualEnergyUsingable;

    private Integer mgUnusualEnergyUsingable;

    private Integer etStUnusualEnergyUsingable;

    private Integer etOffHUnusualEnergyUsingable;

    private Integer etOffLUnusualEnergyUsingable;

    private String chUnusualEnergyUsingableDes;

    private String mgUnusualEnergyUsingableDes;

    private String etStUnusualEnergyUsingableDes;

    private String etOffHUnusualEnergyUsingableDes;

    private String etOffLUnusualEnergyUsingableDes;
    
    public Integer getChFuelCode()
	{
		return chFuelCode;
	}

	public void setChFuelCode(Integer chFuelCode)
	{
		this.chFuelCode = chFuelCode;
	}

	public String getChUnusualEnergyUsingableDes()
	{
		return chUnusualEnergyUsingableDes;
	}

	public void setChUnusualEnergyUsingableDes(String chUnusualEnergyUsingableDes)
	{
		this.chUnusualEnergyUsingableDes = chUnusualEnergyUsingableDes;
	}

	public String getMgUnusualEnergyUsingableDes()
	{
		return mgUnusualEnergyUsingableDes;
	}

	public void setMgUnusualEnergyUsingableDes(String mgUnusualEnergyUsingableDes)
	{
		this.mgUnusualEnergyUsingableDes = mgUnusualEnergyUsingableDes;
	}

	public String getEtStUnusualEnergyUsingableDes()
	{
		return etStUnusualEnergyUsingableDes;
	}

	public void setEtStUnusualEnergyUsingableDes(String etStUnusualEnergyUsingableDes)
	{
		this.etStUnusualEnergyUsingableDes = etStUnusualEnergyUsingableDes;
	}

	public String getEtOffHUnusualEnergyUsingableDes()
	{
		return etOffHUnusualEnergyUsingableDes;
	}

	public void setEtOffHUnusualEnergyUsingableDes(String etOffHUnusualEnergyUsingableDes)
	{
		this.etOffHUnusualEnergyUsingableDes = etOffHUnusualEnergyUsingableDes;
	}

	public String getEtOffLUnusualEnergyUsingableDes()
	{
		return etOffLUnusualEnergyUsingableDes;
	}

	public void setEtOffLUnusualEnergyUsingableDes(String etOffLUnusualEnergyUsingableDes)
	{
		this.etOffLUnusualEnergyUsingableDes = etOffLUnusualEnergyUsingableDes;
	}

	public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Integer getChReliablityLevel() {
        return chReliablityLevel;
    }

    public void setChReliablityLevel(Integer chReliablityLevel) {
        this.chReliablityLevel = chReliablityLevel;
    }

    public Float getChEnergyUsed() {
        return chEnergyUsed;
    }

    public void setChEnergyUsed(Float chEnergyUsed) {
        this.chEnergyUsed = chEnergyUsed;
    }

    public Integer getChPeriodSelect() {
        return chPeriodSelect;
    }

    public void setChPeriodSelect(Integer chPeriodSelect) {
        this.chPeriodSelect = chPeriodSelect;
    }

    public Float getChPeriod() {
        return chPeriod;
    }

    public void setChPeriod(Float chPeriod) {
        this.chPeriod = chPeriod;
    }

    public Float getChFixedCost() {
        return chFixedCost;
    }

    public void setChFixedCost(Float chFixedCost) {
        this.chFixedCost = chFixedCost;
    }

    public Float getChUnitPrice() {
        return chUnitPrice;
    }

    public void setChUnitPrice(Float chUnitPrice) {
        this.chUnitPrice = chUnitPrice;
    }

    public Integer getVatable() {
        return vatable;
    }

    public void setVatable(Integer vatable) {
        this.vatable = vatable;
    }

    public Integer getMgReliablityLevel() {
        return mgReliablityLevel;
    }

    public void setMgReliablityLevel(Integer mgReliablityLevel) {
        this.mgReliablityLevel = mgReliablityLevel;
    }

    public Float getMgGasUsed() {
        return mgGasUsed;
    }

    public void setMgGasUsed(Float mgGasUsed) {
        this.mgGasUsed = mgGasUsed;
    }

    public Integer getMgPeriodSelect() {
        return mgPeriodSelect;
    }

    public void setMgPeriodSelect(Integer mgPeriodSelect) {
        this.mgPeriodSelect = mgPeriodSelect;
    }

    public Float getMgPeriod() {
        return mgPeriod;
    }

    public void setMgPeriod(Float mgPeriod) {
        this.mgPeriod = mgPeriod;
    }

    public Integer getMgChargingBasis() {
        return mgChargingBasis;
    }

    public void setMgChargingBasis(Integer mgChargingBasis) {
        this.mgChargingBasis = mgChargingBasis;
    }

    public Integer getMgStAmountSelect() {
        return mgStAmountSelect;
    }

    public void setMgStAmountSelect(Integer mgStAmountSelect) {
        this.mgStAmountSelect = mgStAmountSelect;
    }

    public Float getMgStAmount() {
        return mgStAmount;
    }

    public void setMgStAmount(Float mgStAmount) {
        this.mgStAmount = mgStAmount;
    }

    public Float getMgStUnitPrice() {
        return mgStUnitPrice;
    }

    public void setMgStUnitPrice(Float mgStUnitPrice) {
        this.mgStUnitPrice = mgStUnitPrice;
    }

    public Float getMgTwInitialUnit() {
        return mgTwInitialUnit;
    }

    public void setMgTwInitialUnit(Float mgTwInitialUnit) {
        this.mgTwInitialUnit = mgTwInitialUnit;
    }

    public Float getMgTwUnits() {
        return mgTwUnits;
    }

    public void setMgTwUnits(Float mgTwUnits) {
        this.mgTwUnits = mgTwUnits;
    }

    public Integer getMgTwUnitsSelected() {
        return mgTwUnitsSelected;
    }

    public void setMgTwUnitsSelected(Integer mgTwUnitsSelected) {
        this.mgTwUnitsSelected = mgTwUnitsSelected;
    }

    public Float getMgTwFollowOn() {
        return mgTwFollowOn;
    }

    public void setMgTwFollowOn(Float mgTwFollowOn) {
        this.mgTwFollowOn = mgTwFollowOn;
    }

    public Integer getMgVatAble() {
        return mgVatAble;
    }

    public void setMgVatAble(Integer mgVatAble) {
        this.mgVatAble = mgVatAble;
    }

    public Integer getEtElectricityTariff() {
        return etElectricityTariff;
    }

    public void setEtElectricityTariff(Integer etElectricityTariff) {
        this.etElectricityTariff = etElectricityTariff;
    }

    public Integer getEtStReliablityLevel() {
        return etStReliablityLevel;
    }

    public void setEtStReliablityLevel(Integer etStReliablityLevel) {
        this.etStReliablityLevel = etStReliablityLevel;
    }

    public Float getEtStElectricityUsed() {
        return etStElectricityUsed;
    }

    public void setEtStElectricityUsed(Float etStElectricityUsed) {
        this.etStElectricityUsed = etStElectricityUsed;
    }

    public Float getEtStPeriod() {
        return etStPeriod;
    }

    public void setEtStPeriod(Float etStPeriod) {
        this.etStPeriod = etStPeriod;
    }

    public Integer getEtStPeriodSelect() {
        return etStPeriodSelect;
    }

    public void setEtStPeriodSelect(Integer etStPeriodSelect) {
        this.etStPeriodSelect = etStPeriodSelect;
    }

    public Integer getEtStVatable() {
        return etStVatable;
    }

    public void setEtStVatable(Integer etStVatable) {
        this.etStVatable = etStVatable;
    }

    public Integer getEtStChargingBasis() {
        return etStChargingBasis;
    }

    public void setEtStChargingBasis(Integer etStChargingBasis) {
        this.etStChargingBasis = etStChargingBasis;
    }

    public Float getEtStStandingChargeAmount() {
        return etStStandingChargeAmount;
    }

    public void setEtStStandingChargeAmount(Float etStStandingChargeAmount) {
        this.etStStandingChargeAmount = etStStandingChargeAmount;
    }

    public Integer getEtStStandingChargeAmountSelect() {
        return etStStandingChargeAmountSelect;
    }

    public void setEtStStandingChargeAmountSelect(Integer etStStandingChargeAmountSelect) {
        this.etStStandingChargeAmountSelect = etStStandingChargeAmountSelect;
    }

    public Float getEtStUnitPrice() {
        return etStUnitPrice;
    }

    public void setEtStUnitPrice(Float etStUnitPrice) {
        this.etStUnitPrice = etStUnitPrice;
    }

    public Float getEtStInitialUnitPrice() {
        return etStInitialUnitPrice;
    }

    public void setEtStInitialUnitPrice(Float etStInitialUnitPrice) {
        this.etStInitialUnitPrice = etStInitialUnitPrice;
    }

    public Float getEtStUnitsAtThisPrice() {
        return etStUnitsAtThisPrice;
    }

    public void setEtStUnitsAtThisPrice(Float etStUnitsAtThisPrice) {
        this.etStUnitsAtThisPrice = etStUnitsAtThisPrice;
    }

    public Integer getEtStUnitsAtThisPriceSelect() {
        return etStUnitsAtThisPriceSelect;
    }

    public void setEtStUnitsAtThisPriceSelect(Integer etStUnitsAtThisPriceSelect) {
        this.etStUnitsAtThisPriceSelect = etStUnitsAtThisPriceSelect;
    }

    public Float getEtStFollowonUnitPrice() {
        return etStFollowonUnitPrice;
    }

    public void setEtStFollowonUnitPrice(Float etStFollowonUnitPrice) {
        this.etStFollowonUnitPrice = etStFollowonUnitPrice;
    }

    public Integer getEtOffHReliablityLevel() {
        return etOffHReliablityLevel;
    }

    public void setEtOffHReliablityLevel(Integer etOffHReliablityLevel) {
        this.etOffHReliablityLevel = etOffHReliablityLevel;
    }

    public Float getEtOffHElectricityUsed() {
        return etOffHElectricityUsed;
    }

    public void setEtOffHElectricityUsed(Float etOffHElectricityUsed) {
        this.etOffHElectricityUsed = etOffHElectricityUsed;
    }

    public Float getEtOffHPeriod() {
        return etOffHPeriod;
    }

    public void setEtOffHPeriod(Float etOffHPeriod) {
        this.etOffHPeriod = etOffHPeriod;
    }

    public Integer getEtOffHPeriodSelect() {
        return etOffHPeriodSelect;
    }

    public void setEtOffHPeriodSelect(Integer etOffHPeriodSelect) {
        this.etOffHPeriodSelect = etOffHPeriodSelect;
    }

    public Integer getEtOffHVatable() {
        return etOffHVatable;
    }

    public void setEtOffHVatable(Integer etOffHVatable) {
        this.etOffHVatable = etOffHVatable;
    }

    public Integer getEtOffHChargingBasis() {
        return etOffHChargingBasis;
    }

    public void setEtOffHChargingBasis(Integer etOffHChargingBasis) {
        this.etOffHChargingBasis = etOffHChargingBasis;
    }

    public Float getEtOffHStandingChargeAmount() {
        return etOffHStandingChargeAmount;
    }

    public void setEtOffHStandingChargeAmount(Float etOffHStandingChargeAmount) {
        this.etOffHStandingChargeAmount = etOffHStandingChargeAmount;
    }

    public Integer getEtOffHStandingChargeAmountSelect() {
        return etOffHStandingChargeAmountSelect;
    }

    public void setEtOffHStandingChargeAmountSelect(Integer etOffHStandingChargeAmountSelect) {
        this.etOffHStandingChargeAmountSelect = etOffHStandingChargeAmountSelect;
    }

    public Float getEtOffHUnitPrice() {
        return etOffHUnitPrice;
    }

    public void setEtOffHUnitPrice(Float etOffHUnitPrice) {
        this.etOffHUnitPrice = etOffHUnitPrice;
    }

    public Float getEtOffHInitialUnitAmount() {
        return etOffHInitialUnitAmount;
    }

    public void setEtOffHInitialUnitAmount(Float etOffHInitialUnitAmount) {
        this.etOffHInitialUnitAmount = etOffHInitialUnitAmount;
    }

    public Float getEtOffHUnitsAtThisPrice() {
        return etOffHUnitsAtThisPrice;
    }

    public void setEtOffHUnitsAtThisPrice(Float etOffHUnitsAtThisPrice) {
        this.etOffHUnitsAtThisPrice = etOffHUnitsAtThisPrice;
    }

    public Integer getEtOffHUnitsAtThisPriceSelect() {
        return etOffHUnitsAtThisPriceSelect;
    }

    public void setEtOffHUnitsAtThisPriceSelect(Integer etOffHUnitsAtThisPriceSelect) {
        this.etOffHUnitsAtThisPriceSelect = etOffHUnitsAtThisPriceSelect;
    }

    public Float getEtOffHFollow() {
        return etOffHFollow;
    }

    public void setEtOffHFollow(Float etOffHFollow) {
        this.etOffHFollow = etOffHFollow;
    }

    public Integer getEtOffLReliablityLevel() {
        return etOffLReliablityLevel;
    }

    public void setEtOffLReliablityLevel(Integer etOffLReliablityLevel) {
        this.etOffLReliablityLevel = etOffLReliablityLevel;
    }

    public Float getEtOffLElectricityUsed() {
        return etOffLElectricityUsed;
    }

    public void setEtOffLElectricityUsed(Float etOffLElectricityUsed) {
        this.etOffLElectricityUsed = etOffLElectricityUsed;
    }

    public Float getEtOffLPeriod() {
        return etOffLPeriod;
    }

    public void setEtOffLPeriod(Float etOffLPeriod) {
        this.etOffLPeriod = etOffLPeriod;
    }

    public Integer getEtOffLPeriodSelect() {
        return etOffLPeriodSelect;
    }

    public void setEtOffLPeriodSelect(Integer etOffLPeriodSelect) {
        this.etOffLPeriodSelect = etOffLPeriodSelect;
    }

    public Integer getEtOffLVatable() {
        return etOffLVatable;
    }

    public void setEtOffLVatable(Integer etOffLVatable) {
        this.etOffLVatable = etOffLVatable;
    }

    public Integer getEtOffLChargingBasis() {
        return etOffLChargingBasis;
    }

    public void setEtOffLChargingBasis(Integer etOffLChargingBasis) {
        this.etOffLChargingBasis = etOffLChargingBasis;
    }

    public Float getEtOffLStandingChargeAmount() {
        return etOffLStandingChargeAmount;
    }

    public void setEtOffLStandingChargeAmount(Float etOffLStandingChargeAmount) {
        this.etOffLStandingChargeAmount = etOffLStandingChargeAmount;
    }

    public Integer getEtOffLStandingChargeAmountSelect() {
        return etOffLStandingChargeAmountSelect;
    }

    public void setEtOffLStandingChargeAmountSelect(Integer etOffLStandingChargeAmountSelect) {
        this.etOffLStandingChargeAmountSelect = etOffLStandingChargeAmountSelect;
    }

    public Float getEtOffLUnitPrice() {
        return etOffLUnitPrice;
    }

    public void setEtOffLUnitPrice(Float etOffLUnitPrice) {
        this.etOffLUnitPrice = etOffLUnitPrice;
    }

    public Float getEtOffLInitialUnitAmount() {
        return etOffLInitialUnitAmount;
    }

    public void setEtOffLInitialUnitAmount(Float etOffLInitialUnitAmount) {
        this.etOffLInitialUnitAmount = etOffLInitialUnitAmount;
    }

    public Float getEtOffLUnitsAtThisPrice() {
        return etOffLUnitsAtThisPrice;
    }

    public void setEtOffLUnitsAtThisPrice(Float etOffLUnitsAtThisPrice) {
        this.etOffLUnitsAtThisPrice = etOffLUnitsAtThisPrice;
    }

    public Integer getEtOffLUnitsAtThisPriceSelect() {
        return etOffLUnitsAtThisPriceSelect;
    }

    public void setEtOffLUnitsAtThisPriceSelect(Integer etOffLUnitsAtThisPriceSelect) {
        this.etOffLUnitsAtThisPriceSelect = etOffLUnitsAtThisPriceSelect;
    }

    public Float getEtOffLFollow() {
        return etOffLFollow;
    }

    public void setEtOffLFollow(Float etOffLFollow) {
        this.etOffLFollow = etOffLFollow;
    }

    public Integer getEtPvable() {
        return etPvable;
    }

    public void setEtPvable(Integer etPvable) {
        this.etPvable = etPvable;
    }

    public Float getEtPvAmount() {
        return etPvAmount;
    }

    public void setEtPvAmount(Float etPvAmount) {
        this.etPvAmount = etPvAmount;
    }

    public Float getEtPvPeriod() {
        return etPvPeriod;
    }

    public void setEtPvPeriod(Float etPvPeriod) {
        this.etPvPeriod = etPvPeriod;
    }

    public Integer getEtPvPeriodSelect() {
        return etPvPeriodSelect;
    }

    public void setEtPvPeriodSelect(Integer etPvPeriodSelect) {
        this.etPvPeriodSelect = etPvPeriodSelect;
    }

    public Integer getEtWindable() {
        return etWindable;
    }

    public void setEtWindable(Integer etWindable) {
        this.etWindable = etWindable;
    }

    public Float getEtWindAmount() {
        return etWindAmount;
    }

    public void setEtWindAmount(Float etWindAmount) {
        this.etWindAmount = etWindAmount;
    }

    public Float getEtWindPeriod() {
        return etWindPeriod;
    }

    public void setEtWindPeriod(Float etWindPeriod) {
        this.etWindPeriod = etWindPeriod;
    }

    public Integer getEtWindPeriodSelect() {
        return etWindPeriodSelect;
    }

    public void setEtWindPeriodSelect(Integer etWindPeriodSelect) {
        this.etWindPeriodSelect = etWindPeriodSelect;
    }

    public Integer getEtMicroable() {
        return etMicroable;
    }

    public void setEtMicroable(Integer etMicroable) {
        this.etMicroable = etMicroable;
    }

    public Float getEtMicroableAmount() {
        return etMicroableAmount;
    }

    public void setEtMicroableAmount(Float etMicroableAmount) {
        this.etMicroableAmount = etMicroableAmount;
    }

    public Float getEtMicroablePeriod() {
        return etMicroablePeriod;
    }

    public void setEtMicroablePeriod(Float etMicroablePeriod) {
        this.etMicroablePeriod = etMicroablePeriod;
    }

    public Integer getEtMicroablePeriodSelect() {
        return etMicroablePeriodSelect;
    }

    public void setEtMicroablePeriodSelect(Integer etMicroablePeriodSelect) {
        this.etMicroablePeriodSelect = etMicroablePeriodSelect;
    }

    public Integer getChFixedCostSelected() {
        return chFixedCostSelected;
    }

    public void setChFixedCostSelected(Integer chFixedCostSelected) {
        this.chFixedCostSelected = chFixedCostSelected;
    }

	public Integer getChUnusualEnergyUsingable() {
		return chUnusualEnergyUsingable;
	}

	public void setChUnusualEnergyUsingable(Integer chUnusualEnergyUsingable) {
		this.chUnusualEnergyUsingable = chUnusualEnergyUsingable;
	}

	public Integer getMgUnusualEnergyUsingable() {
		return mgUnusualEnergyUsingable;
	}

	public void setMgUnusualEnergyUsingable(Integer mgUnusualEnergyUsingable) {
		this.mgUnusualEnergyUsingable = mgUnusualEnergyUsingable;
	}

	public Integer getEtStUnusualEnergyUsingable() {
		return etStUnusualEnergyUsingable;
	}

	public void setEtStUnusualEnergyUsingable(Integer etStUnusualEnergyUsingable) {
		this.etStUnusualEnergyUsingable = etStUnusualEnergyUsingable;
	}

	public Integer getEtOffHUnusualEnergyUsingable() {
		return etOffHUnusualEnergyUsingable;
	}

	public void setEtOffHUnusualEnergyUsingable(Integer etOffHUnusualEnergyUsingable) {
		this.etOffHUnusualEnergyUsingable = etOffHUnusualEnergyUsingable;
	}

	public Integer getEtOffLUnusualEnergyUsingable() {
		return etOffLUnusualEnergyUsingable;
	}

	public void setEtOffLUnusualEnergyUsingable(Integer etOffLUnusualEnergyUsingable) {
		this.etOffLUnusualEnergyUsingable = etOffLUnusualEnergyUsingable;
	}

}