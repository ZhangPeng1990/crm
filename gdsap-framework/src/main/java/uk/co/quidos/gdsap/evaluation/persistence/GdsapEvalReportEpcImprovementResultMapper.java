package uk.co.quidos.gdsap.evaluation.persistence;

import uk.co.quidos.dal.BaseMapper;
import java.util.List;

import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalReportEpcImprovementResult;

public interface GdsapEvalReportEpcImprovementResultMapper extends BaseMapper<GdsapEvalReportEpcImprovementResult, Long>
{
	List<GdsapEvalReportEpcImprovementResult> findByReportId(Long reportId);
	
	void deleteByReportId(Long reportId);
}
