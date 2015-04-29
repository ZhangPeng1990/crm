package uk.co.quidos.gdsap.evaluation.persistence.object;

import org.springframework.stereotype.Repository;

import uk.co.quidos.dal.object.AbstractDO;

@Repository
public class GdsapEvalSolutionImprovementRelKey extends AbstractDO 
{
	private static final long serialVersionUID = -4628338418501037160L;
	private Long improvementId;
    private Long solutionId;
    
    public Long getImprovementId() {
        return improvementId;
    }

    public void setImprovementId(Long improvementId) {
        this.improvementId = improvementId;
    }

    public Long getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(Long solutionId) {
        this.solutionId = solutionId;
    }
}