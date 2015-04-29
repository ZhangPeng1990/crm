package uk.co.quidos.gdsap.evaluation.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalRecommendation;

@Repository
public interface GdsapEvalRecommendationMapper extends BaseMapper<GdsapEvalRecommendation, Long>
{
	List<GdsapEvalRecommendation> findByReportId(Long reportId);
	
	List<GdsapEvalRecommendation> findByReportAndType(@Param("reportId")Long reportId, @Param("recommendationType")Integer recommendationType);
	
	void deleteByReportAndType(@Param("reportId")Long reportId, @Param("recommendationType")Integer recommendationType);
	
	void deleteByReportId(long reportId);
}