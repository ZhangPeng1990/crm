package uk.co.quidos.gdsap.evaluation;

import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 */
public class SolutionIssue extends AbsBusinessObject
{
	private Solution solution;
	private YesNo stoneWalls;
	private YesNo systemBuild;
	private YesNo accessIssues;
	private YesNo highExposure;
	private YesNo narrowCavities;
	private YesNo cavityFillUnknown;
	private YesNo solidWallInsulationUnknown;
	private YesNo noLoftAccess;
	private YesNo flatRoofInsulationUnknown;
	private YesNo roofRoomInsulationUnknown;
	private YesNo floorInsulationUnknown;
	private YesNo noCylinderAccess;
	private YesNo mainsGasNeeded;
	
	public Solution getSolution()
	{
		return solution;
	}

	public void setSolution(Solution solution)
	{
		this.solution = solution;
	}

	public YesNo getStoneWalls()
	{
		return stoneWalls;
	}

	public void setStoneWalls(YesNo stoneWalls)
	{
		this.stoneWalls = stoneWalls;
	}

	public YesNo getSystemBuild()
	{
		return systemBuild;
	}

	public void setSystemBuild(YesNo systemBuild)
	{
		this.systemBuild = systemBuild;
	}

	public YesNo getAccessIssues()
	{
		return accessIssues;
	}

	public void setAccessIssues(YesNo accessIssues)
	{
		this.accessIssues = accessIssues;
	}

	public YesNo getHighExposure()
	{
		return highExposure;
	}

	public void setHighExposure(YesNo highExposure)
	{
		this.highExposure = highExposure;
	}

	public YesNo getNarrowCavities()
	{
		return narrowCavities;
	}

	public void setNarrowCavities(YesNo narrowCavities)
	{
		this.narrowCavities = narrowCavities;
	}

	public YesNo getCavityFillUnknown()
	{
		return cavityFillUnknown;
	}

	public void setCavityFillUnknown(YesNo cavityFillUnknown)
	{
		this.cavityFillUnknown = cavityFillUnknown;
	}

	public YesNo getSolidWallInsulationUnknown()
	{
		return solidWallInsulationUnknown;
	}

	public void setSolidWallInsulationUnknown(YesNo solidWallInsulationUnknown)
	{
		this.solidWallInsulationUnknown = solidWallInsulationUnknown;
	}

	public YesNo getNoLoftAccess()
	{
		return noLoftAccess;
	}

	public void setNoLoftAccess(YesNo noLoftAccess)
	{
		this.noLoftAccess = noLoftAccess;
	}

	public YesNo getFlatRoofInsulationUnknown()
	{
		return flatRoofInsulationUnknown;
	}

	public void setFlatRoofInsulationUnknown(YesNo flatRoofInsulationUnknown)
	{
		this.flatRoofInsulationUnknown = flatRoofInsulationUnknown;
	}

	public YesNo getRoofRoomInsulationUnknown()
	{
		return roofRoomInsulationUnknown;
	}

	public void setRoofRoomInsulationUnknown(YesNo roofRoomInsulationUnknown)
	{
		this.roofRoomInsulationUnknown = roofRoomInsulationUnknown;
	}

	public YesNo getFloorInsulationUnknown()
	{
		return floorInsulationUnknown;
	}

	public void setFloorInsulationUnknown(YesNo floorInsulationUnknown)
	{
		this.floorInsulationUnknown = floorInsulationUnknown;
	}

	public YesNo getNoCylinderAccess()
	{
		return noCylinderAccess;
	}

	public void setNoCylinderAccess(YesNo noCylinderAccess)
	{
		this.noCylinderAccess = noCylinderAccess;
	}

	public YesNo getMainsGasNeeded()
	{
		return mainsGasNeeded;
	}

	public void setMainsGasNeeded(YesNo mainsGasNeeded)
	{
		this.mainsGasNeeded = mainsGasNeeded;
	}

	private static final long serialVersionUID = 8341977194989401194L;

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject#getId()
	 */
	@Override
	public Long getId()
	{
		if (solution != null)
		{
			return solution.getId();
		}
		return null;
	}

}
