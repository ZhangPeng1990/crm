package uk.co.quidos.gdsap.evaluation.persistence;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalSolutionSeq;

@Repository
public interface GdsapEvalSolutionSeqMapper
{
	int insert(GdsapEvalSolutionSeq model);

	void delete(Long reportId);
	
	GdsapEvalSolutionSeq load(@Param("reportId") Long reportId, @Param("solutionType") Integer solutionType);
	
	void update(GdsapEvalSolutionSeq model);

	void updateSelective(GdsapEvalSolutionSeq model);
	
}