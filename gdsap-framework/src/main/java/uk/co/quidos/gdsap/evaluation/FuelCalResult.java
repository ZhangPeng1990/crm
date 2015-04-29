/**
 * 
 */
package uk.co.quidos.gdsap.evaluation;

import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 *
 */
public class FuelCalResult extends AbsBusinessObject
{
	private static final long serialVersionUID = -7855993837591218931L;
	
	private Long id;
	private Solution solution;
	private Integer fuelCode;
	private Float otherFuelCost;
	private Float otherFuelUse;
	private Float scInput;
	private Float scTable;
	private Float upInput;
	private Float upTable;
	private Integer unitInput;
	private Integer unitTable;
	
	public Integer getUnitInput()
	{
		return unitInput;
	}

	public void setUnitInput(Integer unitInput)
	{
		this.unitInput = unitInput;
	}

	public Integer getUnitTable()
	{
		return unitTable;
	}

	public void setUnitTable(Integer unitTable)
	{
		this.unitTable = unitTable;
	}

	public Float getScInput()
	{
		return scInput;
	}

	public void setScInput(Float scInput)
	{
		this.scInput = scInput;
	}

	public Float getScTable()
	{
		return scTable;
	}

	public void setScTable(Float scTable)
	{
		this.scTable = scTable;
	}

	public Float getUpInput()
	{
		return upInput;
	}

	public void setUpInput(Float upInput)
	{
		this.upInput = upInput;
	}

	public Float getUpTable()
	{
		return upTable;
	}

	public void setUpTable(Float upTable)
	{
		this.upTable = upTable;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject#getId()
	 */
	@Override
	public Long getId()
	{
		return this.id;
	}

	public Solution getSolution()
	{
		return solution;
	}

	public Integer getFuelCode()
	{
		return fuelCode;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setSolution(Solution solution)
	{
		this.solution = solution;
	}

	public void setFuelCode(Integer fuelCode)
	{
		this.fuelCode = fuelCode;
	}

	public Float getOtherFuelCost()
	{
		return otherFuelCost;
	}

	public void setOtherFuelCost(Float otherFuelCost)
	{
		this.otherFuelCost = otherFuelCost;
	}

	public Float getOtherFuelUse()
	{
		return otherFuelUse;
	}

	public void setOtherFuelUse(Float otherFuelUse)
	{
		this.otherFuelUse = otherFuelUse;
	}

}
