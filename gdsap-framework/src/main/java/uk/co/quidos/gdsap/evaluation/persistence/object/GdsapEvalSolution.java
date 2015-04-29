package uk.co.quidos.gdsap.evaluation.persistence.object;

import java.util.Date;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapEvalSolution extends AbstractDO 
{
	private static final long serialVersionUID = -1330722843525362415L;
	
	private Long id;
	private String title;
	private Date insertTime;
	private Date updateTime;
	private Integer selected;
	private Long reportId;
	private String des;
	private String solutionLodgeXmlPath;
	private String solutionPartLodgeXmlPath;
	private String solutionPdfPath;
	private Integer solutionType;
	
	public String getSolutionLodgeXmlPath()
	{
		return solutionLodgeXmlPath;
	}

	public void setSolutionLodgeXmlPath(String solutionLodgeXmlPath)
	{
		this.solutionLodgeXmlPath = solutionLodgeXmlPath;
	}

	public String getSolutionPartLodgeXmlPath() {
		return solutionPartLodgeXmlPath;
	}

	public void setSolutionPartLodgeXmlPath(String solutionPartLodgeXmlPath) {
		this.solutionPartLodgeXmlPath = solutionPartLodgeXmlPath;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Date getInsertTime()
	{
		return insertTime;
	}

	public void setInsertTime(Date insertTime)
	{
		this.insertTime = insertTime;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public Integer getSelected()
	{
		return selected;
	}

	public void setSelected(Integer selected)
	{
		this.selected = selected;
	}

	public Long getReportId()
	{
		return reportId;
	}

	public void setReportId(Long reportId)
	{
		this.reportId = reportId;
	}

	public String getDes()
	{
		return des;
	}

	public void setDes(String des)
	{
		this.des = des;
	}

	public String getSolutionPdfPath() {
		return solutionPdfPath;
	}

	public void setSolutionPdfPath(String solutionPdfPath) {
		this.solutionPdfPath = solutionPdfPath;
	}

	public Integer getSolutionType() {
		return solutionType;
	}

	public void setSolutionType(Integer solutionType) {
		this.solutionType = solutionType;
	}
}