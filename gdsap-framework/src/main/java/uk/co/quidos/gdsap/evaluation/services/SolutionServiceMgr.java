/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services;

import java.util.List;

import uk.co.quidos.gdsap.evaluation.Solution;
import uk.co.quidos.gdsap.evaluation.StandardRecommendationWrap;
import uk.co.quidos.gdsap.evaluation.enums.SolutionType;
import uk.co.quidos.gdsap.evaluation.object.SolutionResult;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * Solution 相关业务
 * @author shipeng
 *
 */
public interface SolutionServiceMgr extends BusinessObjectServiceMgr 
{
	/**
	 * 
	 */
	public static final String SERVICE_NAME = "solutionServiceMgr";
	
	/**
	 * title 命名方式 : Scenario-01 如果序列为1~9的整数，则前端补零
	 */
	public static final String SOLUTION_TITLE_PREFIX = "Scenario";
	
	/**
	 * 添加Solution基本信息,如果title 重复，则throws 
	 * 添加流程:
	 * 调用计算接口,并返回响应的结果集合,插入到相应的表中
	 * 1. 添加到 Solution 表中
	 * 2. 将每个关联关系添加到GDSAP_EVAL_SOLUTION_RECOMMENDATION_REL,查看StandardRecommendation中选定的Option，并添加的KeyValue中,并插入响应的结果
	 * 3. 插入其他燃料结果集合 GDSAP_EVAL_SOLUTION_FUEL_CAL_RESULT
	 * 4. 插入到结果集合表中
	 * @param solution
	 * @return
	 * @throws Exception 
	 */
	Solution addSolution(Solution solution,List<StandardRecommendationWrap> srsw) throws Exception;
	
	/**
	 * 重新生成lodgeXmlContent
	 * @param solution
	 * @return
	 */
	Solution reCreateXMLBySolution(Solution solution);
	
	/**
	 * 自动生成Solution名称,根据数量report下的结果结合生成序列
	 * @param reportId
	 * @return
	 */
	String autoBuildSolutionTitle(long reportId, SolutionType solutionType);
	
	/**
	 * 1. 重新调用计算接口，并返回响应结果
	 * 2. 删除和solution相关的结果集合
	 * 3. 添加到关联表中，
	 * 4. 插入到其他燃料表中
	 * 5. 插入到结果集合表中
	 * @param solution
	 * @return
	 * @throws Exception 
	 */
	Solution reAddSolution(Solution solution,List<StandardRecommendationWrap> srsw) throws Exception;
	
	/**
	 * 删除 Solution
	 * @param solutionId
	 */
	void deleteSolution(long solutionId);
	
	/**
	 * 获取Solutions列表
	 * @param reportId
	 * @return
	 */
	List<Solution> getSolutions(long reportId, SolutionType solutionType);
	
	/**
	 * 获取Solution 
	 * @param id
	 * @return
	 */
	Solution getSolution(long id);
	
	/**
	 * 选定 Solution 状态
	 * @param solutionId
	 * @return
	 */
	Solution selectSolution(long solutionId);
	
	/**
	 * 获取选定的solution
	 * @param reportId
	 * @return
	 */
	Solution getSelectedSolution(long reportId);
	
	/**
	 * 获取Solutions 以及 计算结果
	 * @param report
	 * @return
	 */
	List<SolutionResult> getSolutionsWithResult(long reportId, SolutionType solutionType);
	
	/**
	 * 清除Solution 结果集合
	 * @param solutionId
	 */
	void deleteSolutionResults(long solutionId);
	
	Solution updateSolution(Solution solution);
}
