package uk.co.quidos.gdsap.evaluation.persistence;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalReport;

/**
 * @author peng.shi
 */
@Repository
public interface GdsapEvalReportMapper extends
		BaseMapper<GdsapEvalReport, Long> {
	List<GdsapEvalReport> findPageBreakByCondition(
			@Param("userId") Long userId, 
			@Param("startInspectionDate") Date startInspectionDate,
			@Param("endInspectionDate") Date endInspectionDate,
			@Param("reportStatus") Integer reportStatus,
			@Param("keywords") String keywords,
			@Param("transactionType") Integer transactionType,
			@Param("propertyType") Integer propertyType, RowBounds rb);

	Integer findNumberByCondition(@Param("userId") Long userId,
			@Param("startInspectionDate") Date startInspectionDate,
			@Param("endInspectionDate") Date endInspectionDate,
			@Param("reportStatus") Integer reportStatus,
			@Param("keywords") String keywords,
			@Param("transactionType") Integer transactionType,
			@Param("propertyType") Integer propertyType);

	/**
	 * admin调用
	 * 
	 * @param rb
	 * @return
	 */
	List<GdsapEvalReport> adminfindByCondition(
			@Param("startInspectionDate") Date startInspectionDate,
			@Param("endInspectionDate") Date endInspectionDate,
			@Param("organisationCertificationNumber") String organisationCertificationNumber,
			@Param("userName") String userName,
			@Param("reportStatus") Integer reportStatus, @Param("keywords") String keywords, 
			RowBounds rb);


	/**
	 * admin调用
	 * 
	 * @return
	 */
	Integer adminfindNumberByCondition(
			@Param("startInspectionDate") Date startInspectionDate,
			@Param("endInspectionDate") Date endInspectionDate,
			@Param("organisationCertificationNumber") String organisationCertificationNumber,
			@Param("userName") String userName,
			@Param("reportStatus") Integer reportStatus, 
			@Param("keywords") String keywords);

	/**
	 * @param rrn
	 * @return
	 */
	GdsapEvalReport findByRRN(String rrn);
	
	List<GdsapEvalReport> findByRRN_reportStatus(@Param("rrn") String rrn, @Param("reportStatus") Integer reportStatus);
	
	List<GdsapEvalReport> findByOARRN_reportStatus(@Param("oaRrn") String oaRrn, @Param("reportStatus") Integer reportStatus);
	
	GdsapEvalReport findByCompany_companyJobRef(@Param("companyId") String companyId, @Param("companyJobRef") String companyJobRef);

	/**
	 * @param oldReportCalXmlFile
	 * @param id
	 */
	void updateOldReportCalXmlFile(
			@Param("oldReportCalXmlFile") String oldReportCalXmlFile,
			@Param("id") Long id);

	/**
	 * @param unoccupiedPropertyable
	 * @param id
	 * @return
	 */
	int updateUnoccupiedPropertyable(
			@Param("unoccupiedPropertyable") Integer unoccupiedPropertyable,
			@Param("id") Long id);

}