package uk.co.quidos.gdsap.evaluation.services;

import java.util.List;

import uk.co.quidos.gdsap.evaluation.CalResult;
import uk.co.quidos.gdsap.evaluation.EpcImprovementCalResult;
import uk.co.quidos.gdsap.evaluation.FuelCalResult;
import uk.co.quidos.gdsap.evaluation.PerFuelCalResult;
import uk.co.quidos.gdsap.evaluation.Report;
import uk.co.quidos.gdsap.evaluation.ReportEpcImprovementCalResult;
import uk.co.quidos.gdsap.evaluation.Solution;
import uk.co.quidos.gdsap.evaluation.SolutionIssue;
import uk.co.quidos.gdsap.evaluation.StandardRecommendationCalResult;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
public interface CalResultServiceMgr extends BusinessObjectServiceMgr
{
	public static final String SERVICE_NAME = "calResultServiceMgr";
	
	/**
	 * 获取solution单一计算结果
	 * @param solutionId
	 * @return
	 */
	CalResult getCalResult(long solutionId);
	
	/**
	 * 获取燃料计算结果
	 * @param solutionId
	 * @return
	 */
	List<FuelCalResult> getFuelCalResults(long solutionId);
	
	/**
	 * 获取改进建议计算结果
	 * @param solutionId
	 * @return
	 */
	List<StandardRecommendationCalResult> getStandardRecommendationCalResults(long solutionId);
	
	/**
	 * Total ESTIMATED_COSTS_START 总和
	 * @param srCalResult
	 * @return
	 */
	float totalEstimatedCostsStart(List<StandardRecommendationCalResult> srCalResult);
	
	/**
	 * Total ESTIMATED_COSTS_END 总和
	 * @param srCalResult
	 * @return
	 */
	float totalEstimatedCostsEnd(List<StandardRecommendationCalResult> srCalResult);
	
	/**
	 * Total ESTIMATED_ANNUAL_SAVINGS 总和
	 * @param srCalResult
	 * @return
	 */
	float totalEstimatedAnnualSavings(List<StandardRecommendationCalResult> srCalResult);
	
	/**
	 * Total TYPICAL_ANNUAL_SAVINGS 总和
	 * @param srCalResult
	 * @return
	 */
	float totalTypicalAnnualSavings(List<StandardRecommendationCalResult> srCalResult);
	
	/**
	 * 添加CalResult
	 * @param calResult
	 * @return
	 */
	CalResult addCalResult(CalResult calResult);
	
	/**
	 * 添加FuelCalResult 列表
	 * @param solution
	 * @param fuelCalResults
	 * @return
	 */
	List<FuelCalResult> addFuelCalResults(List<FuelCalResult> fuelCalResults);
	
	/**
	 * 添加StandardRecommendationCalResult
	 * @param solution
	 * @param standardRecommendationCalResults
	 * @return
	 */
	List<StandardRecommendationCalResult> addStandardRecommendationCalResults(List<StandardRecommendationCalResult> standardRecommendationCalResults);
	
	/**
	 * 获取EpcImprovementCalResult列表
	 * @param solution
	 * @return
	 */
	List<EpcImprovementCalResult> getEpcImprovementCalResults(Solution solution);
	
	/**
	 * 添加EpcImprovementCalResults 
	 * @param epcImprovementCalResults
	 * @return
	 */
	List<EpcImprovementCalResult> addEpcImprovementCalResults(List<EpcImprovementCalResult> epcImprovementCalResults);
	
	/**
	 * 添加SolutionIssue
	 * @param solutionIssue
	 * @return
	 */
	SolutionIssue addSolutionIssue(SolutionIssue solutionIssue);
		
	/**
	 * 获取SOlutionIssue
	 * @param solution
	 * @return
	 */
	SolutionIssue getSolutionIssue(Solution solution);
	
	/**
	 * 添加PerfuelCalResult
	 * @param perFuelCalResults
	 * @return
	 */
	List<PerFuelCalResult> addPerFuelCalResults(List<PerFuelCalResult> perFuelCalResults);
	
	/**
	 * 获取PerFuelCalResults
	 * @param solution
	 * @return
	 */
	List<PerFuelCalResult> getPerFuelCalResults(Solution solution);
	
	/**
	 * 获取ReportEpcImprovementCalResult
	 * @param id
	 * @return
	 */
	ReportEpcImprovementCalResult getReportEpcImprovementCalResult(long id);
	
	/**
	 * 添加ReportEpcImprovementCalResult
	 * @param reportEpcImprovementCalResult
	 * @return
	 */
	ReportEpcImprovementCalResult addReportEpcImprovementCalResult(ReportEpcImprovementCalResult reportEpcImprovementCalResult);
	
	/**
	 * 通过Report获取ReportEpcImprovementCalResults
	 * @param report
	 * @return
	 */
	List<ReportEpcImprovementCalResult> getReportEpcImprovementCalResults(Report report);
	
	/**
	 * 通过Report删除ReportEpcImprovementCalResult
	 * @param report
	 */
	void deleteReportEpcImprovementCalResults(Report report);
	
}
