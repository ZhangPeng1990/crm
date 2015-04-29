package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapEvalBillDataComm extends AbstractDO
{
	private static final long serialVersionUID = 2757470994612595389L;

	private Long reportId;

	private Integer chFuelCode;

	private Integer chReliablityLevel;

	private Float chEnergyUsed;

	private Integer chPeriodSelect;

	private Float chPeriod;

	private Float chFixedCost;

	private Integer chFixedCostSelected;

	private Float chUnitPrice;

	private Integer chUnusualEnergyUsingable;

	private Integer vatable;

	private String chUnusualEnergyUsingableDes;

	public Long getReportId()
	{
		return reportId;
	}

	public void setReportId(Long reportId)
	{
		this.reportId = reportId;
	}

	public Integer getChFuelCode()
	{
		return chFuelCode;
	}

	public void setChFuelCode(Integer chFuelCode)
	{
		this.chFuelCode = chFuelCode;
	}

	public Integer getChReliablityLevel()
	{
		return chReliablityLevel;
	}

	public void setChReliablityLevel(Integer chReliablityLevel)
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

	public Integer getChPeriodSelect()
	{
		return chPeriodSelect;
	}

	public void setChPeriodSelect(Integer chPeriodSelect)
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

	public Integer getChFixedCostSelected()
	{
		return chFixedCostSelected;
	}

	public void setChFixedCostSelected(Integer chFixedCostSelected)
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

	public Integer getChUnusualEnergyUsingable()
	{
		return chUnusualEnergyUsingable;
	}

	public void setChUnusualEnergyUsingable(Integer chUnusualEnergyUsingable)
	{
		this.chUnusualEnergyUsingable = chUnusualEnergyUsingable;
	}

	public Integer getVatable()
	{
		return vatable;
	}

	public void setVatable(Integer vatable)
	{
		this.vatable = vatable;
	}

	public String getChUnusualEnergyUsingableDes()
	{
		return chUnusualEnergyUsingableDes;
	}

	public void setChUnusualEnergyUsingableDes(String chUnusualEnergyUsingableDes)
	{
		this.chUnusualEnergyUsingableDes = chUnusualEnergyUsingableDes;
	}
}