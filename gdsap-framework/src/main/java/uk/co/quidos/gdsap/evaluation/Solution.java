/**
 * 
 */
package uk.co.quidos.gdsap.evaluation;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import uk.co.quidos.gdsap.evaluation.enums.ReportLocation;
import uk.co.quidos.gdsap.evaluation.enums.SolutionType;
import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;
import uk.co.quidos.gdsap.framework.sysconf.GlobalConfig;

/**
 * @author peng.shi
 *
 */
public class Solution extends AbsBusinessObject
{
	private static final long serialVersionUID = -3862220069239572480L;
	
	private Long id;
    private String title;
    private Date insertTime;
    private Date updateTime;
    private Report report;
    private String des;
    private YesNo selected;
    private String solutionLodgeXmlPath;
    private String solutionPartLodgeXmlPath;//引擎返回的部分lig保存路径
    private String solutionPdfPath;
    private SolutionType solutionType;
    
	public String getSolutionLodgeXmlPath()
	{
		return solutionLodgeXmlPath;
	}
	public void setSolutionLodgeXmlPath(String solutionLodgeXmlPath)
	{
		this.solutionLodgeXmlPath = solutionLodgeXmlPath;
	}
	public YesNo getSelected()
	{
		return selected;
	}
	public void setSelected(YesNo selected)
	{
		this.selected = selected;
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
	public Report getReport()
	{
		return report;
	}
	public void setReport(Report report)
	{
		this.report = report;
	}
	public String getDes()
	{
		return des;
	}
	public void setDes(String des)
	{
		this.des = des;
	}
	
	/**
	 * 获取xml文件内容
	 * @return
	 */
	public String getXmlContent() {
		if (this.report == null || this.getReport().getId() == null || StringUtils.isEmpty(this.getSolutionLodgeXmlPath())) {
			return null;
		}
		String xmlContent = null;
		if (report.getReportLocation().equals(ReportLocation.EAW) || report.getReportLocation().equals(ReportLocation.NIR)) {
			try
			{
				xmlContent = FileUtils.readFileToString(new File(GlobalConfig.getInstance().getFSDir() + solutionLodgeXmlPath), "utf-8");
			} catch (IOException e)
			{
				e.printStackTrace();
				throw new IllegalArgumentException();
			}
		}else{
			try
			{
				xmlContent = FileUtils.readFileToString(new File(GlobalConfig.getInstance().getFSDir() + solutionLodgeXmlPath), "utf-16");
			} catch (IOException e)
			{
				e.printStackTrace();
				throw new IllegalArgumentException();
			}
		}
		return xmlContent;
	}
	public String getSolutionPdfPath() {
		return solutionPdfPath;
	}
	public void setSolutionPdfPath(String solutionPdfPath) {
		this.solutionPdfPath = solutionPdfPath;
	}
	public SolutionType getSolutionType() {
		return solutionType;
	}
	public void setSolutionType(SolutionType solutionType) {
		this.solutionType = solutionType;
	}
	public String getSolutionPartLodgeXmlPath() {
		return solutionPartLodgeXmlPath;
	}
	public void setSolutionPartLodgeXmlPath(String solutionPartLodgeXmlPath) {
		this.solutionPartLodgeXmlPath = solutionPartLodgeXmlPath;
	}
}
