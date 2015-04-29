package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapEvalAppCooking extends AbstractDO
{
	private static final long serialVersionUID = -3998335604511052665L;
	
	private Long reportId;
    private Float dryProportion;
    private Integer dryingClothesSpacable;
    private Integer fridgeFreezersNumber;
    private Integer fridgesNumber;
    private Integer freezersNumber;
    private Integer cookerType;
    private Integer cookingFuel;

    public Integer getDryingClothesSpacable()
	{
		return dryingClothesSpacable;
	}

	public void setDryingClothesSpacable(Integer dryingClothesSpacable)
	{
		this.dryingClothesSpacable = dryingClothesSpacable;
	}

	public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Float getDryProportion()
	{
		return dryProportion;
	}

	public void setDryProportion(Float dryProportion)
	{
		this.dryProportion = dryProportion;
	}

	public Integer getFridgeFreezersNumber() {
        return fridgeFreezersNumber;
    }

    public void setFridgeFreezersNumber(Integer fridgeFreezersNumber) {
        this.fridgeFreezersNumber = fridgeFreezersNumber;
    }

    public Integer getFridgesNumber() {
        return fridgesNumber;
    }

    public void setFridgesNumber(Integer fridgesNumber) {
        this.fridgesNumber = fridgesNumber;
    }

    public Integer getFreezersNumber() {
        return freezersNumber;
    }

    public void setFreezersNumber(Integer freezersNumber) {
        this.freezersNumber = freezersNumber;
    }

    public Integer getCookerType() {
        return cookerType;
    }

    public void setCookerType(Integer cookerType) {
        this.cookerType = cookerType;
    }

	public Integer getCookingFuel()
	{
		return cookingFuel;
	}

	public void setCookingFuel(Integer cookingFuel)
	{
		this.cookingFuel = cookingFuel;
	}


}