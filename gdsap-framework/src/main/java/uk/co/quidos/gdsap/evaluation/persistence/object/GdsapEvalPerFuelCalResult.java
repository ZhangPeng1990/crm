package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapEvalPerFuelCalResult extends AbstractDO
{
	private static final long serialVersionUID = 3133335302090675563L;
	private Long id;
	private Long solutionId;
	private Integer fuelCode;
	private Float kwhSaving;
	private Float scFraction;

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

	public Float getKwhSaving()
	{
		return kwhSaving;
	}

	public void setKwhSaving(Float kwhSaving)
	{
		this.kwhSaving = kwhSaving;
	}

	public Float getScFraction()
	{
		return scFraction;
	}

	public void setScFraction(Float scFraction)
	{
		this.scFraction = scFraction;
	}
}