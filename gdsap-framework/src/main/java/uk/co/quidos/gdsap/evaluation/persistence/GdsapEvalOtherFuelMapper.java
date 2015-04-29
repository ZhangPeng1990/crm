package uk.co.quidos.gdsap.evaluation.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalOtherFuel;

@Repository
public interface GdsapEvalOtherFuelMapper extends BaseMapper<GdsapEvalOtherFuel, Long>
{
	List<GdsapEvalOtherFuel> findByReportId(@Param("reportId") Long reportId);
	
	void delteByReportId (@Param("reportId") Long reportId);
}