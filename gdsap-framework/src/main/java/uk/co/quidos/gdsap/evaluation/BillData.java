/**
 * 
 */
package uk.co.quidos.gdsap.evaluation;


/**
 * @author peng.shi
 */
public class BillData //extends AbsBusinessObject
{
	private static final long serialVersionUID = -6689573089936060639L;
	
//	private Long reportId;
//	
//	private Integer chFuelCode;
//	
//	private DictItem chReliablityLevel;
//
//	private Float chEnergyUsed;
//
//	private DictItem chPeriodSelect;
//
//	private Float chPeriod;
//
//	private Float chFixedCost;
//	
//	private DictItem chFixedCostSelected; 
//
//	private Float chUnitPrice;
//
//	private YesNo vatable;
//
//	private DictItem mgReliablityLevel;
//
//	private Float mgGasUsed;
//
//	private DictItem mgPeriodSelect;
//
//	private Float mgPeriod;
//
//	private DictItem mgChargingBasis;
//
//	private DictItem mgStAmountSelect;
//
//	private Float mgStAmount;
//
//	private Float mgStUnitPrice;
//
//	private Float mgTwInitialUnit;
//
//	private Float mgTwUnits;
//
//	private DictItem mgTwUnitsSelected;
//
//	private Float mgTwFollowOn;
//
//	private YesNo mgVatAble;
//
//	private DictItem etElectricityTariff;
//
//	private DictItem etStReliablityLevel;
//
//	private Float etStElectricityUsed;
//
//	private Float etStPeriod;
//
//	private DictItem etStPeriodSelect;
//
//	private YesNo etStVatable;
//
//	private DictItem etStChargingBasis;
//
//	private Float etStStandingChargeAmount;
//
//	private DictItem etStStandingChargeAmountSelect;
//
//	private Float etStUnitPrice;
//
//	private Float etStInitialUnitPrice;
//
//	private Float etStUnitsAtThisPrice;
//
//	private DictItem etStUnitsAtThisPriceSelect;
//
//	private Float etStFollowonUnitPrice;
//
//	private DictItem etOffHReliablityLevel;
//
//	private Float etOffHElectricityUsed;
//
//	private Float etOffHPeriod;
//
//	private DictItem etOffHPeriodSelect;
//
//	private YesNo etOffHVatable;
//
//	private DictItem etOffHChargingBasis;
//
//	private Float etOffHStandingChargeAmount;
//
//	private DictItem etOffHStandingChargeAmountSelect;
//
//	private Float etOffHUnitPrice;
//
//	private Float etOffHInitialUnitAmount;
//
//	private Float etOffHUnitsAtThisPrice;
//
//	private DictItem etOffHUnitsAtThisPriceSelect;
//
//	private Float etOffHFollow;
//
//	private DictItem etOffLReliablityLevel;
//
//	private Float etOffLElectricityUsed;
//
//	private Float etOffLPeriod;
//
//	private DictItem etOffLPeriodSelect;
//
//	private YesNo etOffLVatable;
//
//	private DictItem etOffLChargingBasis;
//
//	private Float etOffLStandingChargeAmount;
//
//	private DictItem etOffLStandingChargeAmountSelect;
//
//	private Float etOffLUnitPrice;
//
//	private Float etOffLInitialUnitAmount;
//
//	private Float etOffLUnitsAtThisPrice;
//
//	private DictItem etOffLUnitsAtThisPriceSelect;
//
//	private Float etOffLFollow;
//
//	private YesNo etPvable;
//
//	private Float etPvAmount;
//
//	private Float etPvPeriod;
//
//	private DictItem etPvPeriodSelect;
//
//	private YesNo etWindable;
//
//	private Float etWindAmount;
//
//	private Float etWindPeriod;
//
//	private DictItem etWindPeriodSelect;
//
//	private YesNo etMicroable;
//
//	private Float etMicroableAmount;
//
//	private Float etMicroablePeriod;
//
//	private DictItem etMicroablePeriodSelect;
//	
//	private YesNo chUnusualEnergyUsingable;
//
//    private YesNo mgUnusualEnergyUsingable;
//
//    private YesNo etStUnusualEnergyUsingable;
//
//    private YesNo etOffHUnusualEnergyUsingable;
//
//    private YesNo etOffLUnusualEnergyUsingable;
//
//    private String chUnusualEnergyUsingableDes;
//
//    private String mgUnusualEnergyUsingableDes;
//
//    private String etStUnusualEnergyUsingableDes;
//
//    private String etOffHUnusualEnergyUsingableDes;
//
//    private String etOffLUnusualEnergyUsingableDes;
//	
//	private Report report;
//	
//	public Integer getChFuelCode()
//	{
//		return chFuelCode;
//	}
//
//	public void setChFuelCode(Integer chFuelCode)
//	{
//		this.chFuelCode = chFuelCode;
//	}
//
//	public String getChUnusualEnergyUsingableDes()
//	{
//		return chUnusualEnergyUsingableDes;
//	}
//
//	public void setChUnusualEnergyUsingableDes(String chUnusualEnergyUsingableDes)
//	{
//		this.chUnusualEnergyUsingableDes = chUnusualEnergyUsingableDes;
//	}
//
//	public String getMgUnusualEnergyUsingableDes()
//	{
//		return mgUnusualEnergyUsingableDes;
//	}
//
//	public void setMgUnusualEnergyUsingableDes(String mgUnusualEnergyUsingableDes)
//	{
//		this.mgUnusualEnergyUsingableDes = mgUnusualEnergyUsingableDes;
//	}
//
//	public String getEtStUnusualEnergyUsingableDes()
//	{
//		return etStUnusualEnergyUsingableDes;
//	}
//
//	public void setEtStUnusualEnergyUsingableDes(String etStUnusualEnergyUsingableDes)
//	{
//		this.etStUnusualEnergyUsingableDes = etStUnusualEnergyUsingableDes;
//	}
//
//	public String getEtOffHUnusualEnergyUsingableDes()
//	{
//		return etOffHUnusualEnergyUsingableDes;
//	}
//
//	public void setEtOffHUnusualEnergyUsingableDes(String etOffHUnusualEnergyUsingableDes)
//	{
//		this.etOffHUnusualEnergyUsingableDes = etOffHUnusualEnergyUsingableDes;
//	}
//
//	public String getEtOffLUnusualEnergyUsingableDes()
//	{
//		return etOffLUnusualEnergyUsingableDes;
//	}
//
//	public void setEtOffLUnusualEnergyUsingableDes(String etOffLUnusualEnergyUsingableDes)
//	{
//		this.etOffLUnusualEnergyUsingableDes = etOffLUnusualEnergyUsingableDes;
//	}
//
//	public Long getReportId()
//	{
//		return reportId;
//	}
//
//	public void setReportId(Long reportId)
//	{
//		this.reportId = reportId;
//	}
//
//	public DictItem getChReliablityLevel()
//	{
//		return chReliablityLevel;
//	}
//
//	public void setChReliablityLevel(DictItem chReliablityLevel)
//	{
//		this.chReliablityLevel = chReliablityLevel;
//	}
//
//	public Float getChEnergyUsed()
//	{
//		return chEnergyUsed;
//	}
//
//	public void setChEnergyUsed(Float chEnergyUsed)
//	{
//		this.chEnergyUsed = chEnergyUsed;
//	}
//
//	public DictItem getChPeriodSelect()
//	{
//		return chPeriodSelect;
//	}
//
//	public void setChPeriodSelect(DictItem chPeriodSelect)
//	{
//		this.chPeriodSelect = chPeriodSelect;
//	}
//
//	public Float getChPeriod()
//	{
//		return chPeriod;
//	}
//
//	public void setChPeriod(Float chPeriod)
//	{
//		this.chPeriod = chPeriod;
//	}
//
//	public Float getChFixedCost()
//	{
//		return chFixedCost;
//	}
//
//	public void setChFixedCost(Float chFixedCost)
//	{
//		this.chFixedCost = chFixedCost;
//	}
//
//	public Float getChUnitPrice()
//	{
//		return chUnitPrice;
//	}
//
//	public void setChUnitPrice(Float chUnitPrice)
//	{
//		this.chUnitPrice = chUnitPrice;
//	}
//
//	public YesNo getVatable()
//	{
//		return vatable;
//	}
//
//	public void setVatable(YesNo vatable)
//	{
//		this.vatable = vatable;
//	}
//
//	public DictItem getMgReliablityLevel()
//	{
//		return mgReliablityLevel;
//	}
//
//	public void setMgReliablityLevel(DictItem mgReliablityLevel)
//	{
//		this.mgReliablityLevel = mgReliablityLevel;
//	}
//
//	public Float getMgGasUsed()
//	{
//		return mgGasUsed;
//	}
//
//	public void setMgGasUsed(Float mgGasUsed)
//	{
//		this.mgGasUsed = mgGasUsed;
//	}
//
//	public DictItem getMgPeriodSelect()
//	{
//		return mgPeriodSelect;
//	}
//
//	public void setMgPeriodSelect(DictItem mgPeriodSelect)
//	{
//		this.mgPeriodSelect = mgPeriodSelect;
//	}
//
//	public Float getMgPeriod()
//	{
//		return mgPeriod;
//	}
//
//	public void setMgPeriod(Float mgPeriod)
//	{
//		this.mgPeriod = mgPeriod;
//	}
//
//	public DictItem getMgChargingBasis()
//	{
//		return mgChargingBasis;
//	}
//
//	public void setMgChargingBasis(DictItem mgChargingBasis)
//	{
//		this.mgChargingBasis = mgChargingBasis;
//	}
//
//	public DictItem getMgStAmountSelect()
//	{
//		return mgStAmountSelect;
//	}
//
//	public void setMgStAmountSelect(DictItem mgStAmountSelect)
//	{
//		this.mgStAmountSelect = mgStAmountSelect;
//	}
//
//	public Float getMgStAmount()
//	{
//		return mgStAmount;
//	}
//
//	public void setMgStAmount(Float mgStAmount)
//	{
//		this.mgStAmount = mgStAmount;
//	}
//
//	public Float getMgStUnitPrice()
//	{
//		return mgStUnitPrice;
//	}
//
//	public void setMgStUnitPrice(Float mgStUnitPrice)
//	{
//		this.mgStUnitPrice = mgStUnitPrice;
//	}
//
//	public Float getMgTwInitialUnit()
//	{
//		return mgTwInitialUnit;
//	}
//
//	public void setMgTwInitialUnit(Float mgTwInitialUnit)
//	{
//		this.mgTwInitialUnit = mgTwInitialUnit;
//	}
//
//	public Float getMgTwUnits()
//	{
//		return mgTwUnits;
//	}
//
//	public void setMgTwUnits(Float mgTwUnits)
//	{
//		this.mgTwUnits = mgTwUnits;
//	}
//
//	public DictItem getMgTwUnitsSelected()
//	{
//		return mgTwUnitsSelected;
//	}
//
//	public void setMgTwUnitsSelected(DictItem mgTwUnitsSelected)
//	{
//		this.mgTwUnitsSelected = mgTwUnitsSelected;
//	}
//
//	public Float getMgTwFollowOn()
//	{
//		return mgTwFollowOn;
//	}
//
//	public void setMgTwFollowOn(Float mgTwFollowOn)
//	{
//		this.mgTwFollowOn = mgTwFollowOn;
//	}
//
//	public YesNo getMgVatAble()
//	{
//		return mgVatAble;
//	}
//
//	public void setMgVatAble(YesNo mgVatAble)
//	{
//		this.mgVatAble = mgVatAble;
//	}
//
//	public DictItem getEtElectricityTariff()
//	{
//		return etElectricityTariff;
//	}
//
//	public void setEtElectricityTariff(DictItem etElectricityTariff)
//	{
//		this.etElectricityTariff = etElectricityTariff;
//	}
//
//	public DictItem getEtStReliablityLevel()
//	{
//		return etStReliablityLevel;
//	}
//
//	public void setEtStReliablityLevel(DictItem etStReliablityLevel)
//	{
//		this.etStReliablityLevel = etStReliablityLevel;
//	}
//
//	public Float getEtStElectricityUsed()
//	{
//		return etStElectricityUsed;
//	}
//
//	public void setEtStElectricityUsed(Float etStElectricityUsed)
//	{
//		this.etStElectricityUsed = etStElectricityUsed;
//	}
//
//	public Float getEtStPeriod()
//	{
//		return etStPeriod;
//	}
//
//	public void setEtStPeriod(Float etStPeriod)
//	{
//		this.etStPeriod = etStPeriod;
//	}
//
//	public DictItem getEtStPeriodSelect()
//	{
//		return etStPeriodSelect;
//	}
//
//	public void setEtStPeriodSelect(DictItem etStPeriodSelect)
//	{
//		this.etStPeriodSelect = etStPeriodSelect;
//	}
//
//	public YesNo getEtStVatable()
//	{
//		return etStVatable;
//	}
//
//	public void setEtStVatable(YesNo etStVatable)
//	{
//		this.etStVatable = etStVatable;
//	}
//
//	public DictItem getEtStChargingBasis()
//	{
//		return etStChargingBasis;
//	}
//
//	public void setEtStChargingBasis(DictItem etStChargingBasis)
//	{
//		this.etStChargingBasis = etStChargingBasis;
//	}
//
//	public Float getEtStStandingChargeAmount()
//	{
//		return etStStandingChargeAmount;
//	}
//
//	public void setEtStStandingChargeAmount(Float etStStandingChargeAmount)
//	{
//		this.etStStandingChargeAmount = etStStandingChargeAmount;
//	}
//
//	public DictItem getEtStStandingChargeAmountSelect()
//	{
//		return etStStandingChargeAmountSelect;
//	}
//
//	public void setEtStStandingChargeAmountSelect(DictItem etStStandingChargeAmountSelect)
//	{
//		this.etStStandingChargeAmountSelect = etStStandingChargeAmountSelect;
//	}
//
//	public Float getEtStUnitPrice()
//	{
//		return etStUnitPrice;
//	}
//
//	public void setEtStUnitPrice(Float etStUnitPrice)
//	{
//		this.etStUnitPrice = etStUnitPrice;
//	}
//
//	public Float getEtStInitialUnitPrice()
//	{
//		return etStInitialUnitPrice;
//	}
//
//	public void setEtStInitialUnitPrice(Float etStInitialUnitPrice)
//	{
//		this.etStInitialUnitPrice = etStInitialUnitPrice;
//	}
//
//	public Float getEtStUnitsAtThisPrice()
//	{
//		return etStUnitsAtThisPrice;
//	}
//
//	public void setEtStUnitsAtThisPrice(Float etStUnitsAtThisPrice)
//	{
//		this.etStUnitsAtThisPrice = etStUnitsAtThisPrice;
//	}
//
//	public DictItem getEtStUnitsAtThisPriceSelect()
//	{
//		return etStUnitsAtThisPriceSelect;
//	}
//
//	public void setEtStUnitsAtThisPriceSelect(DictItem etStUnitsAtThisPriceSelect)
//	{
//		this.etStUnitsAtThisPriceSelect = etStUnitsAtThisPriceSelect;
//	}
//
//	public Float getEtStFollowonUnitPrice()
//	{
//		return etStFollowonUnitPrice;
//	}
//
//	public void setEtStFollowonUnitPrice(Float etStFollowonUnitPrice)
//	{
//		this.etStFollowonUnitPrice = etStFollowonUnitPrice;
//	}
//
//	public DictItem getEtOffHReliablityLevel()
//	{
//		return etOffHReliablityLevel;
//	}
//
//	public void setEtOffHReliablityLevel(DictItem etOffHReliablityLevel)
//	{
//		this.etOffHReliablityLevel = etOffHReliablityLevel;
//	}
//
//	public Float getEtOffHElectricityUsed()
//	{
//		return etOffHElectricityUsed;
//	}
//
//	public void setEtOffHElectricityUsed(Float etOffHElectricityUsed)
//	{
//		this.etOffHElectricityUsed = etOffHElectricityUsed;
//	}
//
//	public Float getEtOffHPeriod()
//	{
//		return etOffHPeriod;
//	}
//
//	public void setEtOffHPeriod(Float etOffHPeriod)
//	{
//		this.etOffHPeriod = etOffHPeriod;
//	}
//
//	public DictItem getEtOffHPeriodSelect()
//	{
//		return etOffHPeriodSelect;
//	}
//
//	public void setEtOffHPeriodSelect(DictItem etOffHPeriodSelect)
//	{
//		this.etOffHPeriodSelect = etOffHPeriodSelect;
//	}
//
//	public YesNo getEtOffHVatable()
//	{
//		return etOffHVatable;
//	}
//
//	public void setEtOffHVatable(YesNo etOffHVatable)
//	{
//		this.etOffHVatable = etOffHVatable;
//	}
//
//	public DictItem getEtOffHChargingBasis()
//	{
//		return etOffHChargingBasis;
//	}
//
//	public void setEtOffHChargingBasis(DictItem etOffHChargingBasis)
//	{
//		this.etOffHChargingBasis = etOffHChargingBasis;
//	}
//
//	public Float getEtOffHStandingChargeAmount()
//	{
//		return etOffHStandingChargeAmount;
//	}
//
//	public void setEtOffHStandingChargeAmount(Float etOffHStandingChargeAmount)
//	{
//		this.etOffHStandingChargeAmount = etOffHStandingChargeAmount;
//	}
//
//	public DictItem getEtOffHStandingChargeAmountSelect()
//	{
//		return etOffHStandingChargeAmountSelect;
//	}
//
//	public void setEtOffHStandingChargeAmountSelect(DictItem etOffHStandingChargeAmountSelect)
//	{
//		this.etOffHStandingChargeAmountSelect = etOffHStandingChargeAmountSelect;
//	}
//
//	public Float getEtOffHUnitPrice()
//	{
//		return etOffHUnitPrice;
//	}
//
//	public void setEtOffHUnitPrice(Float etOffHUnitPrice)
//	{
//		this.etOffHUnitPrice = etOffHUnitPrice;
//	}
//
//	public Float getEtOffHInitialUnitAmount()
//	{
//		return etOffHInitialUnitAmount;
//	}
//
//	public void setEtOffHInitialUnitAmount(Float etOffHInitialUnitAmount)
//	{
//		this.etOffHInitialUnitAmount = etOffHInitialUnitAmount;
//	}
//
//	public Float getEtOffHUnitsAtThisPrice()
//	{
//		return etOffHUnitsAtThisPrice;
//	}
//
//	public void setEtOffHUnitsAtThisPrice(Float etOffHUnitsAtThisPrice)
//	{
//		this.etOffHUnitsAtThisPrice = etOffHUnitsAtThisPrice;
//	}
//
//	public DictItem getEtOffHUnitsAtThisPriceSelect()
//	{
//		return etOffHUnitsAtThisPriceSelect;
//	}
//
//	public void setEtOffHUnitsAtThisPriceSelect(DictItem etOffHUnitsAtThisPriceSelect)
//	{
//		this.etOffHUnitsAtThisPriceSelect = etOffHUnitsAtThisPriceSelect;
//	}
//
//	public Float getEtOffHFollow()
//	{
//		return etOffHFollow;
//	}
//
//	public void setEtOffHFollow(Float etOffHFollow)
//	{
//		this.etOffHFollow = etOffHFollow;
//	}
//
//	public DictItem getEtOffLReliablityLevel()
//	{
//		return etOffLReliablityLevel;
//	}
//
//	public void setEtOffLReliablityLevel(DictItem etOffLReliablityLevel)
//	{
//		this.etOffLReliablityLevel = etOffLReliablityLevel;
//	}
//
//	public Float getEtOffLElectricityUsed()
//	{
//		return etOffLElectricityUsed;
//	}
//
//	public void setEtOffLElectricityUsed(Float etOffLElectricityUsed)
//	{
//		this.etOffLElectricityUsed = etOffLElectricityUsed;
//	}
//
//	public Float getEtOffLPeriod()
//	{
//		return etOffLPeriod;
//	}
//
//	public void setEtOffLPeriod(Float etOffLPeriod)
//	{
//		this.etOffLPeriod = etOffLPeriod;
//	}
//
//	public DictItem getEtOffLPeriodSelect()
//	{
//		return etOffLPeriodSelect;
//	}
//
//	public void setEtOffLPeriodSelect(DictItem etOffLPeriodSelect)
//	{
//		this.etOffLPeriodSelect = etOffLPeriodSelect;
//	}
//
//	public YesNo getEtOffLVatable()
//	{
//		return etOffLVatable;
//	}
//
//	public void setEtOffLVatable(YesNo etOffLVatable)
//	{
//		this.etOffLVatable = etOffLVatable;
//	}
//
//	public DictItem getEtOffLChargingBasis()
//	{
//		return etOffLChargingBasis;
//	}
//
//	public void setEtOffLChargingBasis(DictItem etOffLChargingBasis)
//	{
//		this.etOffLChargingBasis = etOffLChargingBasis;
//	}
//
//	public Float getEtOffLStandingChargeAmount()
//	{
//		return etOffLStandingChargeAmount;
//	}
//
//	public void setEtOffLStandingChargeAmount(Float etOffLStandingChargeAmount)
//	{
//		this.etOffLStandingChargeAmount = etOffLStandingChargeAmount;
//	}
//
//	public DictItem getEtOffLStandingChargeAmountSelect()
//	{
//		return etOffLStandingChargeAmountSelect;
//	}
//
//	public void setEtOffLStandingChargeAmountSelect(DictItem etOffLStandingChargeAmountSelect)
//	{
//		this.etOffLStandingChargeAmountSelect = etOffLStandingChargeAmountSelect;
//	}
//
//	public Float getEtOffLUnitPrice()
//	{
//		return etOffLUnitPrice;
//	}
//
//	public void setEtOffLUnitPrice(Float etOffLUnitPrice)
//	{
//		this.etOffLUnitPrice = etOffLUnitPrice;
//	}
//
//	public Float getEtOffLInitialUnitAmount()
//	{
//		return etOffLInitialUnitAmount;
//	}
//
//	public void setEtOffLInitialUnitAmount(Float etOffLInitialUnitAmount)
//	{
//		this.etOffLInitialUnitAmount = etOffLInitialUnitAmount;
//	}
//
//	public Float getEtOffLUnitsAtThisPrice()
//	{
//		return etOffLUnitsAtThisPrice;
//	}
//
//	public void setEtOffLUnitsAtThisPrice(Float etOffLUnitsAtThisPrice)
//	{
//		this.etOffLUnitsAtThisPrice = etOffLUnitsAtThisPrice;
//	}
//
//	public DictItem getEtOffLUnitsAtThisPriceSelect()
//	{
//		return etOffLUnitsAtThisPriceSelect;
//	}
//
//	public void setEtOffLUnitsAtThisPriceSelect(DictItem etOffLUnitsAtThisPriceSelect)
//	{
//		this.etOffLUnitsAtThisPriceSelect = etOffLUnitsAtThisPriceSelect;
//	}
//
//	public Float getEtOffLFollow()
//	{
//		return etOffLFollow;
//	}
//
//	public void setEtOffLFollow(Float etOffLFollow)
//	{
//		this.etOffLFollow = etOffLFollow;
//	}
//
//	public YesNo getEtPvable()
//	{
//		return etPvable;
//	}
//
//	public void setEtPvable(YesNo etPvable)
//	{
//		this.etPvable = etPvable;
//	}
//
//	public Float getEtPvAmount()
//	{
//		return etPvAmount;
//	}
//
//	public void setEtPvAmount(Float etPvAmount)
//	{
//		this.etPvAmount = etPvAmount;
//	}
//
//	public Float getEtPvPeriod()
//	{
//		return etPvPeriod;
//	}
//
//	public void setEtPvPeriod(Float etPvPeriod)
//	{
//		this.etPvPeriod = etPvPeriod;
//	}
//
//	public DictItem getEtPvPeriodSelect()
//	{
//		return etPvPeriodSelect;
//	}
//
//	public void setEtPvPeriodSelect(DictItem etPvPeriodSelect)
//	{
//		this.etPvPeriodSelect = etPvPeriodSelect;
//	}
//
//	public YesNo getEtWindable()
//	{
//		return etWindable;
//	}
//
//	public void setEtWindable(YesNo etWindable)
//	{
//		this.etWindable = etWindable;
//	}
//
//	public Float getEtWindAmount()
//	{
//		return etWindAmount;
//	}
//
//	public void setEtWindAmount(Float etWindAmount)
//	{
//		this.etWindAmount = etWindAmount;
//	}
//
//	public Float getEtWindPeriod()
//	{
//		return etWindPeriod;
//	}
//
//	public void setEtWindPeriod(Float etWindPeriod)
//	{
//		this.etWindPeriod = etWindPeriod;
//	}
//
//	public DictItem getEtWindPeriodSelect()
//	{
//		return etWindPeriodSelect;
//	}
//
//	public void setEtWindPeriodSelect(DictItem etWindPeriodSelect)
//	{
//		this.etWindPeriodSelect = etWindPeriodSelect;
//	}
//
//	public YesNo getEtMicroable()
//	{
//		return etMicroable;
//	}
//
//	public void setEtMicroable(YesNo etMicroable)
//	{
//		this.etMicroable = etMicroable;
//	}
//
//	public Float getEtMicroableAmount()
//	{
//		return etMicroableAmount;
//	}
//
//	public void setEtMicroableAmount(Float etMicroableAmount)
//	{
//		this.etMicroableAmount = etMicroableAmount;
//	}
//
//	public Float getEtMicroablePeriod()
//	{
//		return etMicroablePeriod;
//	}
//
//	public void setEtMicroablePeriod(Float etMicroablePeriod)
//	{
//		this.etMicroablePeriod = etMicroablePeriod;
//	}
//
//	public DictItem getEtMicroablePeriodSelect()
//	{
//		return etMicroablePeriodSelect;
//	}
//
//	public void setEtMicroablePeriodSelect(DictItem etMicroablePeriodSelect)
//	{
//		this.etMicroablePeriodSelect = etMicroablePeriodSelect;
//	}
//
//	public Report getReport()
//	{
//		return report;
//	}
//
//	public void setReport(Report report)
//	{
//		this.report = report;
//	}
//
//	@Override
//	public Serializable getId()
//	{
//		if (report != null)
//		{
//			return report.getId();
//		}
//		return null;
//	}
//
//	public DictItem getChFixedCostSelected() {
//		return chFixedCostSelected;
//	}
//
//	public void setChFixedCostSelected(DictItem chFixedCostSelected) {
//		this.chFixedCostSelected = chFixedCostSelected;
//	}
//
//	public YesNo getChUnusualEnergyUsingable() {
//		return chUnusualEnergyUsingable;
//	}
//
//	public void setChUnusualEnergyUsingable(YesNo chUnusualEnergyUsingable) {
//		this.chUnusualEnergyUsingable = chUnusualEnergyUsingable;
//	}
//
//	public YesNo getMgUnusualEnergyUsingable() {
//		return mgUnusualEnergyUsingable;
//	}
//
//	public void setMgUnusualEnergyUsingable(YesNo mgUnusualEnergyUsingable) {
//		this.mgUnusualEnergyUsingable = mgUnusualEnergyUsingable;
//	}
//
//	public YesNo getEtStUnusualEnergyUsingable() {
//		return etStUnusualEnergyUsingable;
//	}
//
//	public void setEtStUnusualEnergyUsingable(YesNo etStUnusualEnergyUsingable) {
//		this.etStUnusualEnergyUsingable = etStUnusualEnergyUsingable;
//	}
//
//	public YesNo getEtOffHUnusualEnergyUsingable() {
//		return etOffHUnusualEnergyUsingable;
//	}
//
//	public void setEtOffHUnusualEnergyUsingable(YesNo etOffHUnusualEnergyUsingable) {
//		this.etOffHUnusualEnergyUsingable = etOffHUnusualEnergyUsingable;
//	}
//
//	public YesNo getEtOffLUnusualEnergyUsingable() {
//		return etOffLUnusualEnergyUsingable;
//	}
//
//	public void setEtOffLUnusualEnergyUsingable(YesNo etOffLUnusualEnergyUsingable) {
//		this.etOffLUnusualEnergyUsingable = etOffLUnusualEnergyUsingable;
//	}
    
}
