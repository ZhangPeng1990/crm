package uk.co.quidos.gdsap.evaluation;

import java.io.Serializable;

import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 */
public class BillDataMg extends AbsBusinessObject
{
	private static final long serialVersionUID = -5914203607284786617L;

	private Report report ;
	
	private DictItem mgReliablityLevel;

	private Float mgGasUsed;

	private DictItem mgPeriodSelect;

	private Float mgPeriod;

	private DictItem mgChargingBasis;

	private DictItem mgStAmountSelect;

	private Float mgStAmount;

	private Float mgStUnitPrice;

	private Float mgTwInitialUnit;

	private Float mgTwUnits;

	private DictItem mgTwUnitsSelected;

	private Float mgTwFollowOn;

	private YesNo mgVatAble;
	
	private YesNo mgUnusualEnergyUsingable;
	
	private String mgUnusualEnergyUsingableDes;
	
	@Override
	public Serializable getId()
	{
		return report != null ? report.getId() : null;
	}

	public YesNo getMgUnusualEnergyUsingable()
	{
		return mgUnusualEnergyUsingable;
	}

	public void setMgUnusualEnergyUsingable(YesNo mgUnusualEnergyUsingable)
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

	public Report getReport()
	{
		return report;
	}

	public void setReport(Report report)
	{
		this.report = report;
	}

	public DictItem getMgReliablityLevel()
	{
		return mgReliablityLevel;
	}

	public void setMgReliablityLevel(DictItem mgReliablityLevel)
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

	public DictItem getMgPeriodSelect()
	{
		return mgPeriodSelect;
	}

	public void setMgPeriodSelect(DictItem mgPeriodSelect)
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

	public DictItem getMgChargingBasis()
	{
		return mgChargingBasis;
	}

	public void setMgChargingBasis(DictItem mgChargingBasis)
	{
		this.mgChargingBasis = mgChargingBasis;
	}

	public DictItem getMgStAmountSelect()
	{
		return mgStAmountSelect;
	}

	public void setMgStAmountSelect(DictItem mgStAmountSelect)
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

	public DictItem getMgTwUnitsSelected()
	{
		return mgTwUnitsSelected;
	}

	public void setMgTwUnitsSelected(DictItem mgTwUnitsSelected)
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

	public YesNo getMgVatAble()
	{
		return mgVatAble;
	}

	public void setMgVatAble(YesNo mgVatAble)
	{
		this.mgVatAble = mgVatAble;
	}
	
}