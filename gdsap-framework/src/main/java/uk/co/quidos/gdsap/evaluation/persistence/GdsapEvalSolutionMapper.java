package uk.co.quidos.gdsap.evaluation.persistence;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalSolution;

@Repository
public interface GdsapEvalSolutionMapper extends BaseMapper<GdsapEvalSolution, Long>
{

	/**
	 * 根据标题查找GdsapEvalSolution
	 * @param title
	 * @return
	 */
	GdsapEvalSolution findByTitle(String title);
	
	/**
	 * 
	 * @param reportId
	 * @return
	 */
	List<GdsapEvalSolution> findByReportId(Long reportId);
	
	/**
	 * 
	 * @param reportId
	 * @param solutionType
	 * @return
	 */
	List<GdsapEvalSolution> findByReport(@Param("reportId") Long reportId, @Param("solutionType") Integer solutionType);
	/**
	 * @param reportId
	 * @param selected
	 */
	void updateSelectedByReportIdAndSelected(@Param("reportId") Long reportId, @Param("solutionType") Integer solutionType,@Param("selected")Integer selected);
	
	/**
	 * @param reportId
	 * @return
	 */
	GdsapEvalSolution findSelectedByReportId(Long reportId);
	
	/**
	 * @param solutionId
	 * @param pdfPath
	 * @param updateTime
	 */
	void updateSolutionPdfPathById(@Param("id")Long solutionId,@Param("solutionPdfPath")String pdfPath,@Param("updateTime")Date updateTime);
	
}