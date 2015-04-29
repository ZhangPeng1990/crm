package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapEvalOccupants extends AbstractDO
{
	private static final long serialVersionUID = -4111778174182414055L;
	
	private Long reportId;
    private Integer occupantsNumber;
    private Integer showerType;
    private Integer showersPerable;
    private Float showersPerDay;
    private Integer bathsPerable;
    private Float bathsPerDay;
    
	public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Integer getOccupantsNumber() {
        return occupantsNumber;
    }

    public void setOccupantsNumber(Integer occupantsNumber) {
        this.occupantsNumber = occupantsNumber;
    }

    public Integer getShowerType() {
        return showerType;
    }

    public void setShowerType(Integer showerType) {
        this.showerType = showerType;
    }

    public Integer getShowersPerable() {
        return showersPerable;
    }

    public void setShowersPerable(Integer showersPerable) {
        this.showersPerable = showersPerable;
    }

    public Integer getBathsPerable() {
        return bathsPerable;
    }

    public void setBathsPerable(Integer bathsPerable) {
        this.bathsPerable = bathsPerable;
    }

	public Float getShowersPerDay()
	{
		return showersPerDay;
	}

	public Float getBathsPerDay()
	{
		return bathsPerDay;
	}

	public void setShowersPerDay(Float showersPerDay)
	{
		this.showersPerDay = showersPerDay;
	}

	public void setBathsPerDay(Float bathsPerDay)
	{
		this.bathsPerDay = bathsPerDay;
	}

}