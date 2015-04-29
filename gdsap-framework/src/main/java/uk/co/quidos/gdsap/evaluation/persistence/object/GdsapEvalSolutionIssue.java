package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapEvalSolutionIssue extends AbstractDO
{
	private static final long serialVersionUID = -1756426169342320987L;

	private Long solutionId;

	private Integer stoneWalls;

	private Integer systemBuild;

	private Integer accessIssues;

	private Integer highExposure;

	private Integer narrowCavities;

	private Integer cavityFillUnknown;

	private Integer solidWallInsulationUnknown;

	private Integer noLoftAccess;

	private Integer flatRoofInsulationUnknown;

	private Integer roofRoomInsulationUnknown;

	private Integer floorInsulationUnknown;

	private Integer noCylinderAccess;

	private Integer mainsGasNeeded;

	public Long getSolutionId()
	{
		return solutionId;
	}

	public void setSolutionId(Long solutionId)
	{
		this.solutionId = solutionId;
	}

	public Integer getStoneWalls()
	{
		return stoneWalls;
	}

	public void setStoneWalls(Integer stoneWalls)
	{
		this.stoneWalls = stoneWalls;
	}

	public Integer getSystemBuild()
	{
		return systemBuild;
	}

	public void setSystemBuild(Integer systemBuild)
	{
		this.systemBuild = systemBuild;
	}

	public Integer getAccessIssues()
	{
		return accessIssues;
	}

	public void setAccessIssues(Integer accessIssues)
	{
		this.accessIssues = accessIssues;
	}

	public Integer getHighExposure()
	{
		return highExposure;
	}

	public void setHighExposure(Integer highExposure)
	{
		this.highExposure = highExposure;
	}

	public Integer getNarrowCavities()
	{
		return narrowCavities;
	}

	public void setNarrowCavities(Integer narrowCavities)
	{
		this.narrowCavities = narrowCavities;
	}

	public Integer getCavityFillUnknown()
	{
		return cavityFillUnknown;
	}

	public void setCavityFillUnknown(Integer cavityFillUnknown)
	{
		this.cavityFillUnknown = cavityFillUnknown;
	}

	public Integer getSolidWallInsulationUnknown()
	{
		return solidWallInsulationUnknown;
	}

	public void setSolidWallInsulationUnknown(Integer solidWallInsulationUnknown)
	{
		this.solidWallInsulationUnknown = solidWallInsulationUnknown;
	}

	public Integer getNoLoftAccess()
	{
		return noLoftAccess;
	}

	public void setNoLoftAccess(Integer noLoftAccess)
	{
		this.noLoftAccess = noLoftAccess;
	}

	public Integer getFlatRoofInsulationUnknown()
	{
		return flatRoofInsulationUnknown;
	}

	public void setFlatRoofInsulationUnknown(Integer flatRoofInsulationUnknown)
	{
		this.flatRoofInsulationUnknown = flatRoofInsulationUnknown;
	}

	public Integer getRoofRoomInsulationUnknown()
	{
		return roofRoomInsulationUnknown;
	}

	public void setRoofRoomInsulationUnknown(Integer roofRoomInsulationUnknown)
	{
		this.roofRoomInsulationUnknown = roofRoomInsulationUnknown;
	}

	public Integer getFloorInsulationUnknown()
	{
		return floorInsulationUnknown;
	}

	public void setFloorInsulationUnknown(Integer floorInsulationUnknown)
	{
		this.floorInsulationUnknown = floorInsulationUnknown;
	}

	public Integer getNoCylinderAccess()
	{
		return noCylinderAccess;
	}

	public void setNoCylinderAccess(Integer noCylinderAccess)
	{
		this.noCylinderAccess = noCylinderAccess;
	}

	public Integer getMainsGasNeeded()
	{
		return mainsGasNeeded;
	}

	public void setMainsGasNeeded(Integer mainsGasNeeded)
	{
		this.mainsGasNeeded = mainsGasNeeded;
	}
}