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
public class AppCooking extends AbsBusinessObject
{
	private static final long serialVersionUID = 3709387129413610543L;
	
	private Report report;
    private float dryProportion;
    private YesNo dryingClothesSpacable;
    private int fridgeFreezersNumber;
    private int fridgesNumber;
    private int freezersNumber;
    private DictItem cookerType;
    private DictItem cookingFuel;
    
	public YesNo getDryingClothesSpacable()
	{
		return dryingClothesSpacable;
	}


	public void setDryingClothesSpacable(YesNo dryingClothesSpacable)
	{
		this.dryingClothesSpacable = dryingClothesSpacable;
	}


	public Report getReport()
	{
		return report;
	}

	public int getFridgeFreezersNumber()
	{
		return fridgeFreezersNumber;
	}


	public int getFridgesNumber()
	{
		return fridgesNumber;
	}

	public int getFreezersNumber()
	{
		return freezersNumber;
	}

	public void setReport(Report report)
	{
		this.report = report;
	}


	public float getDryProportion()
	{
		return dryProportion;
	}


	public void setDryProportion(float dryProportion)
	{
		this.dryProportion = dryProportion;
	}


	public void setFridgeFreezersNumber(int fridgeFreezersNumber)
	{
		this.fridgeFreezersNumber = fridgeFreezersNumber;
	}

	public void setFridgesNumber(int fridgesNumber)
	{
		this.fridgesNumber = fridgesNumber;
	}

	public void setFreezersNumber(int freezersNumber)
	{
		this.freezersNumber = freezersNumber;
	}

	public DictItem getCookerType() {
		return cookerType;
	}


	public void setCookerType(DictItem cookerType) {
		this.cookerType = cookerType;
	}


	public DictItem getCookingFuel() {
		return cookingFuel;
	}


	public void setCookingFuel(DictItem cookingFuel) {
		this.cookingFuel = cookingFuel;
	}


	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject#getId()
	 */
	@Override
	public Long getId()
	{
		if (this.report != null)
		{
			return report.getId();
		}
		return null;
	}

}
