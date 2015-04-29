package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapEvalOtherFuel extends AbstractDO 
{
	private static final long serialVersionUID = -6995997627066631108L;
	
	private Long id;

    private Integer reliablityLevel;

    private Float fixedCost;
    
    private Integer fixedCostSelected;
    
    private Integer unitOfSale;

    private Float unitPrice;

    private Float unitsPurchasedNumber;

    private Float totalCost;

    private Integer periodSelect;

    private Float period;

    private Integer vatable;
    
    private Long reportId;

    private Integer fuelCode;
    
    private Integer unusualEnergyUsingable;
    
    private String unusualEnergyUsingableDes;
    
	public Integer getUnusualEnergyUsingable()
	{
		return unusualEnergyUsingable;
	}

	public void setUnusualEnergyUsingable(Integer unusualEnergyUsingable)
	{
		this.unusualEnergyUsingable = unusualEnergyUsingable;
	}

	public String getUnusualEnergyUsingableDes()
	{
		return unusualEnergyUsingableDes;
	}

	public void setUnusualEnergyUsingableDes(String unusualEnergyUsingableDes)
	{
		this.unusualEnergyUsingableDes = unusualEnergyUsingableDes;
	}

	public Integer getFixedCostSelected()
	{
		return fixedCostSelected;
	}

	public void setFixedCostSelected(Integer fixedCostSelected)
	{
		this.fixedCostSelected = fixedCostSelected;
	}

	public Integer getFuelCode()
	{
		return fuelCode;
	}

	public void setFuelCode(Integer fuelCode)
	{
		this.fuelCode = fuelCode;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReliablityLevel() {
        return reliablityLevel;
    }

    public void setReliablityLevel(Integer reliablityLevel) {
        this.reliablityLevel = reliablityLevel;
    }

    public Float getFixedCost() {
        return fixedCost;
    }

    public void setFixedCost(Float fixedCost) {
        this.fixedCost = fixedCost;
    }

    public Integer getUnitOfSale() {
        return unitOfSale;
    }

    public void setUnitOfSale(Integer unitOfSale) {
        this.unitOfSale = unitOfSale;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getUnitsPurchasedNumber() {
        return unitsPurchasedNumber;
    }

    public void setUnitsPurchasedNumber(Float unitsPurchasedNumber) {
        this.unitsPurchasedNumber = unitsPurchasedNumber;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getPeriodSelect() {
        return periodSelect;
    }

    public void setPeriodSelect(Integer periodSelect) {
        this.periodSelect = periodSelect;
    }

    public Float getPeriod() {
        return period;
    }

    public void setPeriod(Float period) {
        this.period = period;
    }

    public Integer getVatable() {
        return vatable;
    }

    public void setVatable(Integer vatable) {
        this.vatable = vatable;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }
}