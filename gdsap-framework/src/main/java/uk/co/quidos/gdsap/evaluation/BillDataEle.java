package uk.co.quidos.gdsap.evaluation;

import uk.co.quidos.gdsap.evaluation.enums.OffPeakType;
import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 */
public class BillDataEle extends AbsBusinessObject
{
	private static final long serialVersionUID = -4455084140764681364L;

	private DictItem etElectricityTariff;
	private DictItem etStReliablityLevel;
	private Float etStElectricityUsed;
	private Float etStPeriod;
	private DictItem etStPeriodSelect;
	private YesNo etStVatable;
	private DictItem etStChargingBasis;
	private Float etStStandingChargeAmount;
	private DictItem etStStandingChargeAmountSelect;
	private Float etStUnitPrice;
	private Float etStInitialUnitPrice;
	private Float etStUnitsAtThisPrice;
	private DictItem etStUnitsAtThisPriceSelect;
	private Float etStFollowonUnitPrice;	
	private YesNo etStUnusualEnergyUsingable;	
    private String etStUnusualEnergyUsingableDes;
    private DictItem et24ReliablityLevel;
    private Float et24ElectricityUsed;
    private Float et24Period;
    private DictItem et24PeriodSelect;
    private YesNo et24Vatable;
    private DictItem et24ChargingBasis;
    private Float et24StandingChargeAmount;
    private DictItem et24StandingChargeAmountSelect;
    private Float et24UnitPrice;
    private Float et24InitialUnitPrice;
    private Float et24UnitsAtThisPrice;
    private DictItem et24UnitsAtThisPriceSelect;
    private Float et24FollowonUnitPrice;
    private YesNo et24UnusualEnergyUsingable;
    private String et24UnusualEnergyUsingableDes;   
	private DictItem etOffHReliablityLevel;
	private Float etOffHElectricityUsed;
	private Float etOffHPeriod;
	private DictItem etOffHPeriodSelect;
	private YesNo etOffHVatable;
	private DictItem etOffHChargingBasis;
	private Float etOffHStandingChargeAmount;
	private DictItem etOffHStandingChargeAmountSelect;
	private Float etOffHUnitPrice;
	private Float etOffHInitialUnitAmount;
	private Float etOffHUnitsAtThisPrice;
	private DictItem etOffHUnitsAtThisPriceSelect;
	private Float etOffHFollow;
	private DictItem etOffLReliablityLevel;
	private Float etOffLElectricityUsed;
	private Float etOffLPeriod;
	private DictItem etOffLPeriodSelect;
	private YesNo etOffLVatable;
	private DictItem etOffLChargingBasis;
	private Float etOffLStandingChargeAmount;
	private DictItem etOffLStandingChargeAmountSelect;
	private Float etOffLUnitPrice;
	private Float etOffLInitialUnitAmount;
	private Float etOffLUnitsAtThisPrice;
	private DictItem etOffLUnitsAtThisPriceSelect;
	private Float etOffLFollow;
	private YesNo etPvable;
	private Float etPvAmount;
	private Float etPvPeriod;
	private DictItem etPvPeriodSelect;
	private YesNo etWindable;
	private Float etWindAmount;
	private Float etWindPeriod;
	private DictItem etWindPeriodSelect;
	private YesNo etMicroable;
	private Float etMicroableAmount;
	private Float etMicroablePeriod;
	private DictItem etMicroablePeriodSelect;
    private YesNo etOffHUnusualEnergyUsingable;
    private YesNo etOffLUnusualEnergyUsingable;
    private String etOffHUnusualEnergyUsingableDes;
    private String etOffLUnusualEnergyUsingableDes;
	private Report report;
	private OffPeakType offPeakType;
	
	public OffPeakType getOffPeakType()
	{
		return offPeakType;
	}

	public void setOffPeakType(OffPeakType offPeakType)
	{
		this.offPeakType = offPeakType;
	}

	public DictItem getEt24StandingChargeAmountSelect()
	{
		return et24StandingChargeAmountSelect;
	}

	public void setEt24StandingChargeAmountSelect(DictItem et24StandingChargeAmountSelect)
	{
		this.et24StandingChargeAmountSelect = et24StandingChargeAmountSelect;
	}

	public DictItem getEt24ReliablityLevel()
	{
		return et24ReliablityLevel;
	}

	public void setEt24ReliablityLevel(DictItem et24ReliablityLevel)
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

	public DictItem getEt24PeriodSelect()
	{
		return et24PeriodSelect;
	}

	public void setEt24PeriodSelect(DictItem et24PeriodSelect)
	{
		this.et24PeriodSelect = et24PeriodSelect;
	}

	public YesNo getEt24Vatable()
	{
		return et24Vatable;
	}

	public void setEt24Vatable(YesNo et24Vatable)
	{
		this.et24Vatable = et24Vatable;
	}

	public DictItem getEt24ChargingBasis()
	{
		return et24ChargingBasis;
	}

	public void setEt24ChargingBasis(DictItem et24ChargingBasis)
	{
		this.et24ChargingBasis = et24ChargingBasis;
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

	public DictItem getEt24UnitsAtThisPriceSelect()
	{
		return et24UnitsAtThisPriceSelect;
	}

	public void setEt24UnitsAtThisPriceSelect(DictItem et24UnitsAtThisPriceSelect)
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

	public YesNo getEt24UnusualEnergyUsingable()
	{
		return et24UnusualEnergyUsingable;
	}

	public void setEt24UnusualEnergyUsingable(YesNo et24UnusualEnergyUsingable)
	{
		this.et24UnusualEnergyUsingable = et24UnusualEnergyUsingable;
	}

	public String getEt24UnusualEnergyUsingableDes()
	{
		return et24UnusualEnergyUsingableDes;
	}

	public void setEt24UnusualEnergyUsingableDes(String et24UnusualEnergyUsingableDes)
	{
		this.et24UnusualEnergyUsingableDes = et24UnusualEnergyUsingableDes;
	}

	public DictItem getEtElectricityTariff()
	{
		return etElectricityTariff;
	}

	public void setEtElectricityTariff(DictItem etElectricityTariff)
	{
		this.etElectricityTariff = etElectricityTariff;
	}

	public DictItem getEtStReliablityLevel()
	{
		return etStReliablityLevel;
	}

	public void setEtStReliablityLevel(DictItem etStReliablityLevel)
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

	public DictItem getEtStPeriodSelect()
	{
		return etStPeriodSelect;
	}

	public void setEtStPeriodSelect(DictItem etStPeriodSelect)
	{
		this.etStPeriodSelect = etStPeriodSelect;
	}

	public YesNo getEtStVatable()
	{
		return etStVatable;
	}

	public void setEtStVatable(YesNo etStVatable)
	{
		this.etStVatable = etStVatable;
	}

	public DictItem getEtStChargingBasis()
	{
		return etStChargingBasis;
	}

	public void setEtStChargingBasis(DictItem etStChargingBasis)
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

	public DictItem getEtStStandingChargeAmountSelect()
	{
		return etStStandingChargeAmountSelect;
	}

	public void setEtStStandingChargeAmountSelect(DictItem etStStandingChargeAmountSelect)
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

	public DictItem getEtStUnitsAtThisPriceSelect()
	{
		return etStUnitsAtThisPriceSelect;
	}

	public void setEtStUnitsAtThisPriceSelect(DictItem etStUnitsAtThisPriceSelect)
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

	public DictItem getEtOffHReliablityLevel()
	{
		return etOffHReliablityLevel;
	}

	public void setEtOffHReliablityLevel(DictItem etOffHReliablityLevel)
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

	public DictItem getEtOffHPeriodSelect()
	{
		return etOffHPeriodSelect;
	}

	public void setEtOffHPeriodSelect(DictItem etOffHPeriodSelect)
	{
		this.etOffHPeriodSelect = etOffHPeriodSelect;
	}

	public YesNo getEtOffHVatable()
	{
		return etOffHVatable;
	}

	public void setEtOffHVatable(YesNo etOffHVatable)
	{
		this.etOffHVatable = etOffHVatable;
	}

	public DictItem getEtOffHChargingBasis()
	{
		return etOffHChargingBasis;
	}

	public void setEtOffHChargingBasis(DictItem etOffHChargingBasis)
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

	public DictItem getEtOffHStandingChargeAmountSelect()
	{
		return etOffHStandingChargeAmountSelect;
	}

	public void setEtOffHStandingChargeAmountSelect(DictItem etOffHStandingChargeAmountSelect)
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

	public DictItem getEtOffHUnitsAtThisPriceSelect()
	{
		return etOffHUnitsAtThisPriceSelect;
	}

	public void setEtOffHUnitsAtThisPriceSelect(DictItem etOffHUnitsAtThisPriceSelect)
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

	public DictItem getEtOffLReliablityLevel()
	{
		return etOffLReliablityLevel;
	}

	public void setEtOffLReliablityLevel(DictItem etOffLReliablityLevel)
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

	public DictItem getEtOffLPeriodSelect()
	{
		return etOffLPeriodSelect;
	}

	public void setEtOffLPeriodSelect(DictItem etOffLPeriodSelect)
	{
		this.etOffLPeriodSelect = etOffLPeriodSelect;
	}

	public YesNo getEtOffLVatable()
	{
		return etOffLVatable;
	}

	public void setEtOffLVatable(YesNo etOffLVatable)
	{
		this.etOffLVatable = etOffLVatable;
	}

	public DictItem getEtOffLChargingBasis()
	{
		return etOffLChargingBasis;
	}

	public void setEtOffLChargingBasis(DictItem etOffLChargingBasis)
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

	public DictItem getEtOffLStandingChargeAmountSelect()
	{
		return etOffLStandingChargeAmountSelect;
	}

	public void setEtOffLStandingChargeAmountSelect(DictItem etOffLStandingChargeAmountSelect)
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

	public DictItem getEtOffLUnitsAtThisPriceSelect()
	{
		return etOffLUnitsAtThisPriceSelect;
	}

	public void setEtOffLUnitsAtThisPriceSelect(DictItem etOffLUnitsAtThisPriceSelect)
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

	public YesNo getEtPvable()
	{
		return etPvable;
	}

	public void setEtPvable(YesNo etPvable)
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

	public DictItem getEtPvPeriodSelect()
	{
		return etPvPeriodSelect;
	}

	public void setEtPvPeriodSelect(DictItem etPvPeriodSelect)
	{
		this.etPvPeriodSelect = etPvPeriodSelect;
	}

	public YesNo getEtWindable()
	{
		return etWindable;
	}

	public void setEtWindable(YesNo etWindable)
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

	public DictItem getEtWindPeriodSelect()
	{
		return etWindPeriodSelect;
	}

	public void setEtWindPeriodSelect(DictItem etWindPeriodSelect)
	{
		this.etWindPeriodSelect = etWindPeriodSelect;
	}

	public YesNo getEtMicroable()
	{
		return etMicroable;
	}

	public void setEtMicroable(YesNo etMicroable)
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

	public DictItem getEtMicroablePeriodSelect()
	{
		return etMicroablePeriodSelect;
	}

	public void setEtMicroablePeriodSelect(DictItem etMicroablePeriodSelect)
	{
		this.etMicroablePeriodSelect = etMicroablePeriodSelect;
	}

	public YesNo getEtStUnusualEnergyUsingable()
	{
		return etStUnusualEnergyUsingable;
	}

	public void setEtStUnusualEnergyUsingable(YesNo etStUnusualEnergyUsingable)
	{
		this.etStUnusualEnergyUsingable = etStUnusualEnergyUsingable;
	}

	public YesNo getEtOffHUnusualEnergyUsingable()
	{
		return etOffHUnusualEnergyUsingable;
	}

	public void setEtOffHUnusualEnergyUsingable(YesNo etOffHUnusualEnergyUsingable)
	{
		this.etOffHUnusualEnergyUsingable = etOffHUnusualEnergyUsingable;
	}

	public YesNo getEtOffLUnusualEnergyUsingable()
	{
		return etOffLUnusualEnergyUsingable;
	}

	public void setEtOffLUnusualEnergyUsingable(YesNo etOffLUnusualEnergyUsingable)
	{
		this.etOffLUnusualEnergyUsingable = etOffLUnusualEnergyUsingable;
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

	public Report getReport()
	{
		return report;
	}

	public void setReport(Report report)
	{
		this.report = report;
	}

	public Float getEt24StandingChargeAmount()
	{
		return et24StandingChargeAmount;
	}

	public void setEt24StandingChargeAmount(Float et24StandingChargeAmount)
	{
		this.et24StandingChargeAmount = et24StandingChargeAmount;
	}

	@Override
	public Long getId()
	{
		if (report != null)
		{
			return report.getId();
		}
		return null;
	}
	
}
