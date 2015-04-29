package uk.co.quidos.gdsap.evaluation;

import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

public class Occupants extends AbsBusinessObject
{
	private static final long serialVersionUID = 4942309491702694440L;
	
	private Report report;
    private int occupantsNumber;
    private DictItem showerType;
    private YesNo showersPerable;
    private float showersPerDay;
    private YesNo bathsPerable;
    private float bathsPerDay;
    
	@Override
	public Long getId()
	{
		if (report != null)
		{
			return report.getId();
		}
		return null;
	}
	public Report getReport()
	{
		return report;
	}
	public void setReport(Report report)
	{
		this.report = report;
	}
	public int getOccupantsNumber()
	{
		return occupantsNumber;
	}
	public DictItem getShowerType()
	{
		return showerType;
	}
	public void setOccupantsNumber(int occupantsNumber)
	{
		this.occupantsNumber = occupantsNumber;
	}
	public void setShowerType(DictItem showerType)
	{
		this.showerType = showerType;
	}
	public YesNo getShowersPerable()
	{
		return showersPerable;
	}
	public float getShowersPerDay()
	{
		return showersPerDay;
	}
	public YesNo getBathsPerable()
	{
		return bathsPerable;
	}
	public void setShowersPerable(YesNo showersPerable)
	{
		this.showersPerable = showersPerable;
	}
	public void setShowersPerDay(float showersPerDay)
	{
		this.showersPerDay = showersPerDay;
	}
	public void setBathsPerable(YesNo bathsPerable)
	{
		this.bathsPerable = bathsPerable;
	}
	public float getBathsPerDay() {
		return bathsPerDay;
	}
	public void setBathsPerDay(float bathsPerDay) {
		this.bathsPerDay = bathsPerDay;
	}
}