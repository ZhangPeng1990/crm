package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapEvalBillDataEle extends AbstractDO
{
	private static final long serialVersionUID = 1559780061943618857L;

	private Long reportId;

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

	private Integer etStUnusualEnergyUsingable;

	private Integer et24ReliablityLevel;

	private Float et24ElectricityUsed;

	private Float et24Period;

	private Integer et24PeriodSelect;

	private Integer et24Vatable;

	private Integer et24ChargingBasis;

	private Float et24StandingChargeAmount;

	private Integer et24StandingChargeAmountSelect;

	private Float et24UnitPrice;

	private Float et24InitialUnitPrice;

	private Float et24UnitsAtThisPrice;

	private Integer et24UnitsAtThisPriceSelect;

	private Float et24FollowonUnitPrice;

	private Integer et24UnusualEnergyUsingable;

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

	private Integer etOffHUnusualEnergyUsingable;

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

	private Integer etOffLUnusualEnergyUsingable;

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

	private String etStUnusualEnergyUsingableDes;

	private String et24UnusualEnergyUsingableDes;

	private String etOffHUnusualEnergyUsingableDes;

	private String etOffLUnusualEnergyUsingableDes;
	
	private Integer etOffType;
	
	public Integer getEtOffType()
	{
		return etOffType;
	}

	public void setEtOffType(Integer etOffType)
	{
		this.etOffType = etOffType;
	}

	public String getEtStUnusualEnergyUsingableDes()
	{
		return etStUnusualEnergyUsingableDes;
	}

	public void setEtStUnusualEnergyUsingableDes(String etStUnusualEnergyUsingableDes)
	{
		this.etStUnusualEnergyUsingableDes = etStUnusualEnergyUsingableDes;
	}

	public String getEt24UnusualEnergyUsingableDes()
	{
		return et24UnusualEnergyUsingableDes;
	}

	public void setEt24UnusualEnergyUsingableDes(String et24UnusualEnergyUsingableDes)
	{
		this.et24UnusualEnergyUsingableDes = et24UnusualEnergyUsingableDes;
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

	public Long getReportId()
	{
		return reportId;
	}

	public void setReportId(Long reportId)
	{
		this.reportId = reportId;
	}

	public Integer getEtElectricityTariff()
	{
		return etElectricityTariff;
	}

	public void setEtElectricityTariff(Integer etElectricityTariff)
	{
		this.etElectricityTariff = etElectricityTariff;
	}

	public Integer getEtStReliablityLevel()
	{
		return etStReliablityLevel;
	}

	public void setEtStReliablityLevel(Integer etStReliablityLevel)
	{
		this.etStReliablityLevel = etStReliablityLevel;
	}

	public Float getEtStElectricityUsed()
	{
		return etStElectricityUsed;
	}

	public void setEtStElectricityUsed(Float etStElectricityUsed)
	{
		this.etStElectricityUsed = etStElectricityUsed;
	}

	public Float getEtStPeriod()
	{
		return etStPeriod;
	}

	public void setEtStPeriod(Float etStPeriod)
	{
		this.etStPeriod = etStPeriod;
	}

	public Integer getEtStPeriodSelect()
	{
		return etStPeriodSelect;
	}

	public void setEtStPeriodSelect(Integer etStPeriodSelect)
	{
		this.etStPeriodSelect = etStPeriodSelect;
	}

	public Integer getEtStVatable()
	{
		return etStVatable;
	}

	public void setEtStVatable(Integer etStVatable)
	{
		this.etStVatable = etStVatable;
	}

	public Integer getEtStChargingBasis()
	{
		return etStChargingBasis;
	}

	public void setEtStChargingBasis(Integer etStChargingBasis)
	{
		this.etStChargingBasis = etStChargingBasis;
	}

	public Float getEtStStandingChargeAmount()
	{
		return etStStandingChargeAmount;
	}

	public void setEtStStandingChargeAmount(Float etStStandingChargeAmount)
	{
		this.etStStandingChargeAmount = etStStandingChargeAmount;
	}

	public Integer getEtStStandingChargeAmountSelect()
	{
		return etStStandingChargeAmountSelect;
	}

	public void setEtStStandingChargeAmountSelect(Integer etStStandingChargeAmountSelect)
	{
		this.etStStandingChargeAmountSelect = etStStandingChargeAmountSelect;
	}

	public Float getEtStUnitPrice()
	{
		return etStUnitPrice;
	}

	public void setEtStUnitPrice(Float etStUnitPrice)
	{
		this.etStUnitPrice = etStUnitPrice;
	}

	public Float getEtStInitialUnitPrice()
	{
		return etStInitialUnitPrice;
	}

	public void setEtStInitialUnitPrice(Float etStInitialUnitPrice)
	{
		this.etStInitialUnitPrice = etStInitialUnitPrice;
	}

	public Float getEtStUnitsAtThisPrice()
	{
		return etStUnitsAtThisPrice;
	}

	public void setEtStUnitsAtThisPrice(Float etStUnitsAtThisPrice)
	{
		this.etStUnitsAtThisPrice = etStUnitsAtThisPrice;
	}

	public Integer getEtStUnitsAtThisPriceSelect()
	{
		return etStUnitsAtThisPriceSelect;
	}

	public void setEtStUnitsAtThisPriceSelect(Integer etStUnitsAtThisPriceSelect)
	{
		this.etStUnitsAtThisPriceSelect = etStUnitsAtThisPriceSelect;
	}

	public Float getEtStFollowonUnitPrice()
	{
		return etStFollowonUnitPrice;
	}

	public void setEtStFollowonUnitPrice(Float etStFollowonUnitPrice)
	{
		this.etStFollowonUnitPrice = etStFollowonUnitPrice;
	}

	public Integer getEtStUnusualEnergyUsingable()
	{
		return etStUnusualEnergyUsingable;
	}

	public void setEtStUnusualEnergyUsingable(Integer etStUnusualEnergyUsingable)
	{
		this.etStUnusualEnergyUsingable = etStUnusualEnergyUsingable;
	}

	public Integer getEt24ReliablityLevel()
	{
		return et24ReliablityLevel;
	}

	public void setEt24ReliablityLevel(Integer et24ReliablityLevel)
	{
		this.et24ReliablityLevel = et24ReliablityLevel;
	}

	public Float getEt24ElectricityUsed()
	{
		return et24ElectricityUsed;
	}

	public void setEt24ElectricityUsed(Float et24ElectricityUsed)
	{
		this.et24ElectricityUsed = et24ElectricityUsed;
	}

	public Float getEt24Period()
	{
		return et24Period;
	}

	public void setEt24Period(Float et24Period)
	{
		this.et24Period = et24Period;
	}

	public Integer getEt24PeriodSelect()
	{
		return et24PeriodSelect;
	}

	public void setEt24PeriodSelect(Integer et24PeriodSelect)
	{
		this.et24PeriodSelect = et24PeriodSelect;
	}

	public Integer getEt24Vatable()
	{
		return et24Vatable;
	}

	public void setEt24Vatable(Integer et24Vatable)
	{
		this.et24Vatable = et24Vatable;
	}

	public Integer getEt24ChargingBasis()
	{
		return et24ChargingBasis;
	}

	public void setEt24ChargingBasis(Integer et24ChargingBasis)
	{
		this.et24ChargingBasis = et24ChargingBasis;
	}

	public Float getEt24StandingChargeAmount()
	{
		return et24StandingChargeAmount;
	}

	public void setEt24StandingChargeAmount(Float et24StandingChargeAmount)
	{
		this.et24StandingChargeAmount = et24StandingChargeAmount;
	}

	public Integer getEt24StandingChargeAmountSelect()
	{
		return et24StandingChargeAmountSelect;
	}

	public void setEt24StandingChargeAmountSelect(Integer et24StandingChargeAmountSelect)
	{
		this.et24StandingChargeAmountSelect = et24StandingChargeAmountSelect;
	}

	public Float getEt24UnitPrice()
	{
		return et24UnitPrice;
	}

	public void setEt24UnitPrice(Float et24UnitPrice)
	{
		this.et24UnitPrice = et24UnitPrice;
	}

	public Float getEt24InitialUnitPrice()
	{
		return et24InitialUnitPrice;
	}

	public void setEt24InitialUnitPrice(Float et24InitialUnitPrice)
	{
		this.et24InitialUnitPrice = et24InitialUnitPrice;
	}

	public Float getEt24UnitsAtThisPrice()
	{
		return et24UnitsAtThisPrice;
	}

	public void setEt24UnitsAtThisPrice(Float et24UnitsAtThisPrice)
	{
		this.et24UnitsAtThisPrice = et24UnitsAtThisPrice;
	}

	public Integer getEt24UnitsAtThisPriceSelect()
	{
		return et24UnitsAtThisPriceSelect;
	}

	public void setEt24UnitsAtThisPriceSelect(Integer et24UnitsAtThisPriceSelect)
	{
		this.et24UnitsAtThisPriceSelect = et24UnitsAtThisPriceSelect;
	}

	public Float getEt24FollowonUnitPrice()
	{
		return et24FollowonUnitPrice;
	}

	public void setEt24FollowonUnitPrice(Float et24FollowonUnitPrice)
	{
		this.et24FollowonUnitPrice = et24FollowonUnitPrice;
	}

	public Integer getEt24UnusualEnergyUsingable()
	{
		return et24UnusualEnergyUsingable;
	}

	public void setEt24UnusualEnergyUsingable(Integer et24UnusualEnergyUsingable)
	{
		this.et24UnusualEnergyUsingable = et24UnusualEnergyUsingable;
	}

	public Integer getEtOffHReliablityLevel()
	{
		return etOffHReliablityLevel;
	}

	public void setEtOffHReliablityLevel(Integer etOffHReliablityLevel)
	{
		this.etOffHReliablityLevel = etOffHReliablityLevel;
	}

	public Float getEtOffHElectricityUsed()
	{
		return etOffHElectricityUsed;
	}

	public void setEtOffHElectricityUsed(Float etOffHElectricityUsed)
	{
		this.etOffHElectricityUsed = etOffHElectricityUsed;
	}

	public Float getEtOffHPeriod()
	{
		return etOffHPeriod;
	}

	public void setEtOffHPeriod(Float etOffHPeriod)
	{
		this.etOffHPeriod = etOffHPeriod;
	}

	public Integer getEtOffHPeriodSelect()
	{
		return etOffHPeriodSelect;
	}

	public void setEtOffHPeriodSelect(Integer etOffHPeriodSelect)
	{
		this.etOffHPeriodSelect = etOffHPeriodSelect;
	}

	public Integer getEtOffHVatable()
	{
		return etOffHVatable;
	}

	public void setEtOffHVatable(Integer etOffHVatable)
	{
		this.etOffHVatable = etOffHVatable;
	}

	public Integer getEtOffHChargingBasis()
	{
		return etOffHChargingBasis;
	}

	public void setEtOffHChargingBasis(Integer etOffHChargingBasis)
	{
		this.etOffHChargingBasis = etOffHChargingBasis;
	}

	public Float getEtOffHStandingChargeAmount()
	{
		return etOffHStandingChargeAmount;
	}

	public void setEtOffHStandingChargeAmount(Float etOffHStandingChargeAmount)
	{
		this.etOffHStandingChargeAmount = etOffHStandingChargeAmount;
	}

	public Integer getEtOffHStandingChargeAmountSelect()
	{
		return etOffHStandingChargeAmountSelect;
	}

	public void setEtOffHStandingChargeAmountSelect(Integer etOffHStandingChargeAmountSelect)
	{
		this.etOffHStandingChargeAmountSelect = etOffHStandingChargeAmountSelect;
	}

	public Float getEtOffHUnitPrice()
	{
		return etOffHUnitPrice;
	}

	public void setEtOffHUnitPrice(Float etOffHUnitPrice)
	{
		this.etOffHUnitPrice = etOffHUnitPrice;
	}

	public Float getEtOffHInitialUnitAmount()
	{
		return etOffHInitialUnitAmount;
	}

	public void setEtOffHInitialUnitAmount(Float etOffHInitialUnitAmount)
	{
		this.etOffHInitialUnitAmount = etOffHInitialUnitAmount;
	}

	public Float getEtOffHUnitsAtThisPrice()
	{
		return etOffHUnitsAtThisPrice;
	}

	public void setEtOffHUnitsAtThisPrice(Float etOffHUnitsAtThisPrice)
	{
		this.etOffHUnitsAtThisPrice = etOffHUnitsAtThisPrice;
	}

	public Integer getEtOffHUnitsAtThisPriceSelect()
	{
		return etOffHUnitsAtThisPriceSelect;
	}

	public void setEtOffHUnitsAtThisPriceSelect(Integer etOffHUnitsAtThisPriceSelect)
	{
		this.etOffHUnitsAtThisPriceSelect = etOffHUnitsAtThisPriceSelect;
	}

	public Float getEtOffHFollow()
	{
		return etOffHFollow;
	}

	public void setEtOffHFollow(Float etOffHFollow)
	{
		this.etOffHFollow = etOffHFollow;
	}

	public Integer getEtOffHUnusualEnergyUsingable()
	{
		return etOffHUnusualEnergyUsingable;
	}

	public void setEtOffHUnusualEnergyUsingable(Integer etOffHUnusualEnergyUsingable)
	{
		this.etOffHUnusualEnergyUsingable = etOffHUnusualEnergyUsingable;
	}

	public Integer getEtOffLReliablityLevel()
	{
		return etOffLReliablityLevel;
	}

	public void setEtOffLReliablityLevel(Integer etOffLReliablityLevel)
	{
		this.etOffLReliablityLevel = etOffLReliablityLevel;
	}

	public Float getEtOffLElectricityUsed()
	{
		return etOffLElectricityUsed;
	}

	public void setEtOffLElectricityUsed(Float etOffLElectricityUsed)
	{
		this.etOffLElectricityUsed = etOffLElectricityUsed;
	}

	public Float getEtOffLPeriod()
	{
		return etOffLPeriod;
	}

	public void setEtOffLPeriod(Float etOffLPeriod)
	{
		this.etOffLPeriod = etOffLPeriod;
	}

	public Integer getEtOffLPeriodSelect()
	{
		return etOffLPeriodSelect;
	}

	public void setEtOffLPeriodSelect(Integer etOffLPeriodSelect)
	{
		this.etOffLPeriodSelect = etOffLPeriodSelect;
	}

	public Integer getEtOffLVatable()
	{
		return etOffLVatable;
	}

	public void setEtOffLVatable(Integer etOffLVatable)
	{
		this.etOffLVatable = etOffLVatable;
	}

	public Integer getEtOffLChargingBasis()
	{
		return etOffLChargingBasis;
	}

	public void setEtOffLChargingBasis(Integer etOffLChargingBasis)
	{
		this.etOffLChargingBasis = etOffLChargingBasis;
	}

	public Float getEtOffLStandingChargeAmount()
	{
		return etOffLStandingChargeAmount;
	}

	public void setEtOffLStandingChargeAmount(Float etOffLStandingChargeAmount)
	{
		this.etOffLStandingChargeAmount = etOffLStandingChargeAmount;
	}

	public Integer getEtOffLStandingChargeAmountSelect()
	{
		return etOffLStandingChargeAmountSelect;
	}

	public void setEtOffLStandingChargeAmountSelect(Integer etOffLStandingChargeAmountSelect)
	{
		this.etOffLStandingChargeAmountSelect = etOffLStandingChargeAmountSelect;
	}

	public Float getEtOffLUnitPrice()
	{
		return etOffLUnitPrice;
	}

	public void setEtOffLUnitPrice(Float etOffLUnitPrice)
	{
		this.etOffLUnitPrice = etOffLUnitPrice;
	}

	public Float getEtOffLInitialUnitAmount()
	{
		return etOffLInitialUnitAmount;
	}

	public void setEtOffLInitialUnitAmount(Float etOffLInitialUnitAmount)
	{
		this.etOffLInitialUnitAmount = etOffLInitialUnitAmount;
	}

	public Float getEtOffLUnitsAtThisPrice()
	{
		return etOffLUnitsAtThisPrice;
	}

	public void setEtOffLUnitsAtThisPrice(Float etOffLUnitsAtThisPrice)
	{
		this.etOffLUnitsAtThisPrice = etOffLUnitsAtThisPrice;
	}

	public Integer getEtOffLUnitsAtThisPriceSelect()
	{
		return etOffLUnitsAtThisPriceSelect;
	}

	public void setEtOffLUnitsAtThisPriceSelect(Integer etOffLUnitsAtThisPriceSelect)
	{
		this.etOffLUnitsAtThisPriceSelect = etOffLUnitsAtThisPriceSelect;
	}

	public Float getEtOffLFollow()
	{
		return etOffLFollow;
	}

	public void setEtOffLFollow(Float etOffLFollow)
	{
		this.etOffLFollow = etOffLFollow;
	}

	public Integer getEtOffLUnusualEnergyUsingable()
	{
		return etOffLUnusualEnergyUsingable;
	}

	public void setEtOffLUnusualEnergyUsingable(Integer etOffLUnusualEnergyUsingable)
	{
		this.etOffLUnusualEnergyUsingable = etOffLUnusualEnergyUsingable;
	}

	public Integer getEtPvable()
	{
		return etPvable;
	}

	public void setEtPvable(Integer etPvable)
	{
		this.etPvable = etPvable;
	}

	public Float getEtPvAmount()
	{
		return etPvAmount;
	}

	public void setEtPvAmount(Float etPvAmount)
	{
		this.etPvAmount = etPvAmount;
	}

	public Float getEtPvPeriod()
	{
		return etPvPeriod;
	}

	public void setEtPvPeriod(Float etPvPeriod)
	{
		this.etPvPeriod = etPvPeriod;
	}

	public Integer getEtPvPeriodSelect()
	{
		return etPvPeriodSelect;
	}

	public void setEtPvPeriodSelect(Integer etPvPeriodSelect)
	{
		this.etPvPeriodSelect = etPvPeriodSelect;
	}

	public Integer getEtWindable()
	{
		return etWindable;
	}

	public void setEtWindable(Integer etWindable)
	{
		this.etWindable = etWindable;
	}

	public Float getEtWindAmount()
	{
		return etWindAmount;
	}

	public void setEtWindAmount(Float etWindAmount)
	{
		this.etWindAmount = etWindAmount;
	}

	public Float getEtWindPeriod()
	{
		return etWindPeriod;
	}

	public void setEtWindPeriod(Float etWindPeriod)
	{
		this.etWindPeriod = etWindPeriod;
	}

	public Integer getEtWindPeriodSelect()
	{
		return etWindPeriodSelect;
	}

	public void setEtWindPeriodSelect(Integer etWindPeriodSelect)
	{
		this.etWindPeriodSelect = etWindPeriodSelect;
	}

	public Integer getEtMicroable()
	{
		return etMicroable;
	}

	public void setEtMicroable(Integer etMicroable)
	{
		this.etMicroable = etMicroable;
	}

	public Float getEtMicroableAmount()
	{
		return etMicroableAmount;
	}

	public void setEtMicroableAmount(Float etMicroableAmount)
	{
		this.etMicroableAmount = etMicroableAmount;
	}

	public Float getEtMicroablePeriod()
	{
		return etMicroablePeriod;
	}

	public void setEtMicroablePeriod(Float etMicroablePeriod)
	{
		this.etMicroablePeriod = etMicroablePeriod;
	}

	public Integer getEtMicroablePeriodSelect()
	{
		return etMicroablePeriodSelect;
	}

	public void setEtMicroablePeriodSelect(Integer etMicroablePeriodSelect)
	{
		this.etMicroablePeriodSelect = etMicroablePeriodSelect;
	}
}