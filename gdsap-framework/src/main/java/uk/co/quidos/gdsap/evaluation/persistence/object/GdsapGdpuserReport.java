package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapGdpuserReport  extends AbstractDO {

	private static final long serialVersionUID = -2372013504724298567L;
	
	public GdsapGdpuserReport(){}
	
	public GdsapGdpuserReport(Long userId, Long reportId)
	{
		this.userId = userId;
		this.reportId = reportId;
	}
	
	private Long id;
	private Long userId;
	private Long reportId;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
}
