package uk.co.quidos.gdsap.evaluation.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalHeatProportion;

@Repository
public interface GdsapEvalHeatProportionMapper extends  BaseMapper<GdsapEvalHeatProportion,Long>
{
	List<GdsapEvalHeatProportion> findGdsapEvalHeatProportions(long reportId);
	
	void deleteByReportId(long reportId);
}