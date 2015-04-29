package uk.co.quidos.gdsap.evaluation.persistence;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalReport;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapGdpuserReport;

public interface GdsapGdpuserReportMapper extends
	BaseMapper<GdsapGdpuserReport, Long>{

	List<GdsapEvalReport> findPageBreakByCondition(
			@Param("userId") Long userId, @Param("rrn") String rrn,
			@Param("startInspectionDate") Date startInspectionDate,
			@Param("endInspectionDate") Date endInspectionDate,
			@Param("urpn") String urpn,
			@Param("transactionType") Integer transactionType,
			@Param("propertyType") Integer propertyType,
			@Param("reportStatus") Integer reportStatus,RowBounds rb);
	
	Integer findNumberByCondition(@Param("userId") Long userId,
			@Param("rrn") String rrn,
			@Param("startInspectionDate") Date startInspectionDate,
			@Param("endInspectionDate") Date endInspectionDate,
			@Param("urpn") String urpn,
			@Param("transactionType") Integer transactionType,
			@Param("propertyType") Integer propertyType,
			@Param("reportStatus") Integer reportStatus);
	
	void delete(@Param("userId") Long userId, @Param("reportId") Long reportId);
	
}
