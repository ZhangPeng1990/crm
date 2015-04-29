/**
 * 
 */
package uk.co.quidos.gdsap.evaluation;

import uk.co.quidos.gdsap.evaluation.enums.RoomType;
import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 *
 */
public class HeatProportion extends AbsBusinessObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7843373033980601922L;

	private Long id;
    private Report reportId;
    private RoomType roomScope;
    private YesNo main1;
    private YesNo main2;
    private YesNo secondary;
    private YesNo heatedPartially;
    private YesNo notable;
    
	public Long getId()
	{
		return id;
	}
	public Report getReportId()
	{
		return reportId;
	}
	public YesNo getMain1()
	{
		return main1;
	}
	public YesNo getMain2()
	{
		return main2;
	}
	public YesNo getSecondary()
	{
		return secondary;
	}
	public YesNo getHeatedPartially()
	{
		return heatedPartially;
	}
	public YesNo getNotable()
	{
		return notable;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public void setReportId(Report reportId)
	{
		this.reportId = reportId;
	}
	public void setMain1(YesNo main1)
	{
		this.main1 = main1;
	}
	public void setMain2(YesNo main2)
	{
		this.main2 = main2;
	}
	public void setSecondary(YesNo secondary)
	{
		this.secondary = secondary;
	}
	public void setHeatedPartially(YesNo heatedPartially)
	{
		this.heatedPartially = heatedPartially;
	}
	public void setNotable(YesNo notable)
	{
		this.notable = notable;
	}
	public RoomType getRoomScope()
	{
		return roomScope;
	}
	public void setRoomScope(RoomType roomScope)
	{
		this.roomScope = roomScope;
	}
    
}
