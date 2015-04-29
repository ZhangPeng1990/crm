package uk.co.quidos.gdsap.evaluation;

import uk.co.quidos.gdsap.evaluation.Solution;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 */
public class PerFuelCalResult extends AbsBusinessObject
{
	private static final long serialVersionUID = -6207057194424125019L;

	private Long id;
	private Solution solution;
	private Integer fuelCode;
	private Float kwhSaving;
	private Float scFraction;

	public Solution getSolution()
	{
		return solution;
	}

	public void setSolution(Solution solution)
	{
		this.solution = solution;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
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
