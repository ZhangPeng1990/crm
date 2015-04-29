/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services;

import java.util.List;

import uk.co.quidos.gdsap.evaluation.Improvement;
import uk.co.quidos.gdsap.evaluation.enums.ImprovementScope;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * Improvement 业务方法
 * @author shipeng
 */
public interface ImprovementServiceMgr extends BusinessObjectServiceMgr
{
	/**
	 * 直接从DB中读取
	 * @param id
	 * @return
	 */
	Improvement getImprovement(long id);
	
	/**
	 * 获取Improvements ，通过Solution获取
	 * @param solution
	 * @return
	 */
	List<Improvement> getImprovementsBySolutionId(long solutionId);
	
	/**
	 * 获取当前Report下的Improvement列表，如果Scope 为Null，则返回当前Report 下的所有Improvement
	 * @param report
	 * @param scope
	 * @return
	 */
	List<Improvement> getImprovementsByReportId(long reportId,ImprovementScope scope);
	
	/**
	 * 添加Solution下的improvements
	 * @param solution
	 * @param improvements
	 * @return
	 */
	void addImprovements(long solutionId,List<Improvement> improvements);
	
	/**
	 * 更新Solution 下的 Improvements 
	 * @param solution
	 * @param improvements
	 * @return
	 */
	void updateImprovements(long solutionId,List<Improvement> improvements);
	
}
