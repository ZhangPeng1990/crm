package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapEvalSolutionFuelCalResult extends AbstractDO
{
	private static final long serialVersionUID = -6352000872599358116L;

	private Long id;

	private Long solutionId;

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

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getSolutionId()
	{
		return solutionId;
	}

	public void setSolutionId(Long solutionId)
	{
		this.solutionId = solutionId;
	}

	public Integer getFuelCode()
	{
		return fuelCode;
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
}