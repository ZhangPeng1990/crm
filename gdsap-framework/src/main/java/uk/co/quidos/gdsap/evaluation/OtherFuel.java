/**
 * 
 */
package uk.co.quidos.gdsap.evaluation;

import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 *
 */
public class OtherFuel extends AbsBusinessObject
{
	private static final long serialVersionUID = -7037984873257253242L;
	
	private Long id;
    private DictItem reliablityLevel;
    private float fixedCost;
    private DictItem unitOfSale;
    private float unitPrice;
    private float unitsPurchasedNumber;
    private float totalCost;
    private DictItem periodSelect;
    private float period;
    private YesNo vatable;
    private Report report;
    private Integer fuelCode;
    private DictItem fixedCostSelected;
    private YesNo unusualEnergyUsingable;
    private String unusualEnergyUsingableDes;
    
	public YesNo getUnusualEnergyUsingable()
	{
		return unusualEnergyUsingable;
	}

	public void setUnusualEnergyUsingable(YesNo unusualEnergyUsingable)
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

	public Integer getFuelCode()
	{
		return fuelCode;
	}

	public void setFuelCode(Integer fuelCode)
	{
		this.fuelCode = fuelCode;
	}

	public DictItem getReliablityLevel()
	{
		return reliablityLevel;
	}

	public float getFixedCost()
	{
		return fixedCost;
	}

	public DictItem getUnitOfSale()
	{
		return unitOfSale;
	}

	public float getUnitPrice()
	{
		return unitPrice;
	}

	public float getUnitsPurchasedNumber()
	{
		return unitsPurchasedNumber;
	}

	public float getTotalCost()
	{
		return totalCost;
	}

	public DictItem getPeriodSelect()
	{
		return periodSelect;
	}

	public float getPeriod()
	{
		return period;
	}

	public YesNo getVatable()
	{
		return vatable;
	}

	public Report getReport()
	{
		return report;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setReliablityLevel(DictItem reliablityLevel)
	{
		this.reliablityLevel = reliablityLevel;
	}

	public void setFixedCost(float fixedCost)
	{
		this.fixedCost = fixedCost;
	}

	public void setUnitOfSale(DictItem unitOfSale)
	{
		this.unitOfSale = unitOfSale;
	}

	public void setUnitPrice(float unitPrice)
	{
		this.unitPrice = unitPrice;
	}

	public void setUnitsPurchasedNumber(float unitsPurchasedNumber)
	{
		this.unitsPurchasedNumber = unitsPurchasedNumber;
	}

	public void setTotalCost(float totalCost)
	{
		this.totalCost = totalCost;
	}

	public void setPeriodSelect(DictItem periodSelect)
	{
		this.periodSelect = periodSelect;
	}

	public void setPeriod(float period)
	{
		this.period = period;
	}

	public void setVatable(YesNo vatable)
	{
		this.vatable = vatable;
	}

	public void setReport(Report report)
	{
		this.report = report;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject#getId()
	 */
	@Override
	public Long getId()
	{
		return this.id;
	}

	public DictItem getFixedCostSelected() {
		return fixedCostSelected;
	}

	public void setFixedCostSelected(DictItem fixedCostSelected) {
		this.fixedCostSelected = fixedCostSelected;
	}

}
