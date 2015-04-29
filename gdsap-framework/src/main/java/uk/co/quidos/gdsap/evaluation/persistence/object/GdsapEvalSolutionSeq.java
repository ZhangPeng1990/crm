package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapEvalSolutionSeq extends AbstractDO {

	private static final long serialVersionUID = 6419425123031552977L;

	private Long reportId;

	private Integer solutionType;
	
    private Integer seqValue;

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Integer getSeqValue() {
        return seqValue;
    }

    public void setSeqValue(Integer seqValue) {
        this.seqValue = seqValue;
    }

	public Integer getSolutionType() {
		return solutionType;
	}

	public void setSolutionType(Integer solutionType) {
		this.solutionType = solutionType;
	}
}