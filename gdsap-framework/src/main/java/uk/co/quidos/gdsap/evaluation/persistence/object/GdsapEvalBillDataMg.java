package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapEvalBillDataMg extends AbstractDO
{
	private static final long serialVersionUID = -5914203607284786617L;

	private Long reportId;

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

	private Integer mgUnusualEnergyUsingable;

	private String mgUnusualEnergyUsingableDes;

	public Long getReportId()
	{
		return reportId;
	}

	public void setReportId(Long reportId)
	{
		this.reportId = reportId;
	}

	public Integer getMgReliablityLevel()
	{
		return mgReliablityLevel;
	}

	public void setMgReliablityLevel(Integer mgReliablityLevel)
	{
		this.mgReliablityLevel = mgReliablityLevel;
	}

	public Float getMgGasUsed()
	{
		return mgGasUsed;
	}

	public void setMgGasUsed(Float mgGasUsed)
	{
		this.mgGasUsed = mgGasUsed;
	}

	public Integer getMgPeriodSelect()
	{
		return mgPeriodSelect;
	}

	public void setMgPeriodSelect(Integer mgPeriodSelect)
	{
		this.mgPeriodSelect = mgPeriodSelect;
	}

	public Float getMgPeriod()
	{
		return mgPeriod;
	}

	public void setMgPeriod(Float mgPeriod)
	{
		this.mgPeriod = mgPeriod;
	}

	public Integer getMgChargingBasis()
	{
		return mgChargingBasis;
	}

	public void setMgChargingBasis(Integer mgChargingBasis)
	{
		this.mgChargingBasis = mgChargingBasis;
	}

	public Integer getMgStAmountSelect()
	{
		return mgStAmountSelect;
	}

	public void setMgStAmountSelect(Integer mgStAmountSelect)
	{
		this.mgStAmountSelect = mgStAmountSelect;
	}

	public Float getMgStAmount()
	{
		return mgStAmount;
	}

	public void setMgStAmount(Float mgStAmount)
	{
		this.mgStAmount = mgStAmount;
	}

	public Float getMgStUnitPrice()
	{
		return mgStUnitPrice;
	}

	public void setMgStUnitPrice(Float mgStUnitPrice)
	{
		this.mgStUnitPrice = mgStUnitPrice;
	}

	public Float getMgTwInitialUnit()
	{
		return mgTwInitialUnit;
	}

	public void setMgTwInitialUnit(Float mgTwInitialUnit)
	{
		this.mgTwInitialUnit = mgTwInitialUnit;
	}

	public Float getMgTwUnits()
	{
		return mgTwUnits;
	}

	public void setMgTwUnits(Float mgTwUnits)
	{
		this.mgTwUnits = mgTwUnits;
	}

	public Integer getMgTwUnitsSelected()
	{
		return mgTwUnitsSelected;
	}

	public void setMgTwUnitsSelected(Integer mgTwUnitsSelected)
	{
		this.mgTwUnitsSelected = mgTwUnitsSelected;
	}

	public Float getMgTwFollowOn()
	{
		return mgTwFollowOn;
	}

	public void setMgTwFollowOn(Float mgTwFollowOn)
	{
		this.mgTwFollowOn = mgTwFollowOn;
	}

	public Integer getMgVatAble()
	{
		return mgVatAble;
	}

	public void setMgVatAble(Integer mgVatAble)
	{
		this.mgVatAble = mgVatAble;
	}

	public Integer getMgUnusualEnergyUsingable()
	{
		return mgUnusualEnergyUsingable;
	}

	public void setMgUnusualEnergyUsingable(Integer mgUnusualEnergyUsingable)
	{
		this.mgUnusualEnergyUsingable = mgUnusualEnergyUsingable;
	}

	public String getMgUnusualEnergyUsingableDes()
	{
		return mgUnusualEnergyUsingableDes;
	}

	public void setMgUnusualEnergyUsingableDes(String mgUnusualEnergyUsingableDes)
	{
		this.mgUnusualEnergyUsingableDes = mgUnusualEnergyUsingableDes;
	}
}