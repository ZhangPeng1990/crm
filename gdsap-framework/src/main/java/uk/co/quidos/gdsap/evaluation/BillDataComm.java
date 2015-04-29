package uk.co.quidos.gdsap.evaluation;

import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 */
public class BillDataComm extends AbsBusinessObject
{
	private static final long serialVersionUID = 2757470994612595389L;

	private Report report;

	private Integer chFuelCode;
	
	private DictItem chReliablityLevel;

	private Float chEnergyUsed;

	private DictItem chPeriodSelect;

	private Float chPeriod;

	private Float chFixedCost;
	
	private DictItem chFixedCostSelected; 

	private Float chUnitPrice;

	private YesNo vatable;
	
	private String chUnusualEnergyUsingableDes;
	
	private YesNo chUnusualEnergyUsingable;
	
	@Override
	public Long getId()
	{
		return report != null ? report.getId() : null;
	}

	public String getChUnusualEnergyUsingableDes()
	{
		return chUnusualEnergyUsingableDes;
	}

	public void setChUnusualEnergyUsingableDes(String chUnusualEnergyUsingableDes)
	{
		this.chUnusualEnergyUsingableDes = chUnusualEnergyUsingableDes;
	}

	public YesNo getChUnusualEnergyUsingable()
	{
		return chUnusualEnergyUsingable;
	}

	public void setChUnusualEnergyUsingable(YesNo chUnusualEnergyUsingable)
	{
		this.chUnusualEnergyUsingable = chUnusualEnergyUsingable;
	}

	public Report getReport()
	{
		return report;
	}

	public void setReport(Report report)
	{
		this.report = report;
	}

	public Integer getChFuelCode()
	{
		return chFuelCode;
	}

	public void setChFuelCode(Integer chFuelCode)
	{
		this.chFuelCode = chFuelCode;
	}

	public DictItem getChReliablityLevel()
	{
		return chReliablityLevel;
	}

	public void setChReliablityLevel(DictItem chReliablityLevel)
	{
		this.chReliablityLevel = chReliablityLevel;
	}

	public Float getChEnergyUsed()
	{
		return chEnergyUsed;
	}

	public void setChEnergyUsed(Float chEnergyUsed)
	{
		this.chEnergyUsed = chEnergyUsed;
	}

	public DictItem getChPeriodSelect()
	{
		return chPeriodSelect;
	}

	public void setChPeriodSelect(DictItem chPeriodSelect)
	{
		this.chPeriodSelect = chPeriodSelect;
	}

	public Float getChPeriod()
	{
		return chPeriod;
	}

	public void setChPeriod(Float chPeriod)
	{
		this.chPeriod = chPeriod;
	}

	public Float getChFixedCost()
	{
		return chFixedCost;
	}

	public void setChFixedCost(Float chFixedCost)
	{
		this.chFixedCost = chFixedCost;
	}

	public DictItem getChFixedCostSelected()
	{
		return chFixedCostSelected;
	}

	public void setChFixedCostSelected(DictItem chFixedCostSelected)
	{
		this.chFixedCostSelected = chFixedCostSelected;
	}

	public Float getChUnitPrice()
	{
		return chUnitPrice;
	}

	public void setChUnitPrice(Float chUnitPrice)
	{
		this.chUnitPrice = chUnitPrice;
	}

	public YesNo getVatable()
	{
		return vatable;
	}

	public void setVatable(YesNo vatable)
	{
		this.vatable = vatable;
	}
	
}