/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services;

import java.util.List;

import uk.co.quidos.gdsap.evaluation.Report;
import uk.co.quidos.gdsap.evaluation.StandardOption;
import uk.co.quidos.gdsap.evaluation.StandardRecommendation;
import uk.co.quidos.gdsap.evaluation.StandardRecommendationWrap;
import uk.co.quidos.gdsap.evaluation.StandardValue;
import uk.co.quidos.gdsap.evaluation.enums.SolutionType;
import uk.co.quidos.gdsap.framework.enums.EpcVersion;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * @author peng.shi
 */
public interface StandardRecommendationServiceMgr extends BusinessObjectServiceMgr
{
	public static final String SERVICE_NAME = "standardRecommendationServiceMgr";
	
	/**
	 * @param code
	 * @return
	 */
	StandardRecommendation getStandardRecommendation(int code, Report report);
	
	/**
	 * 获取 StandardOption 选项
	 * @param sr
	 * @param standardOptionCode
	 * @return
	 */
	StandardOption getStandardOption(StandardRecommendation sr,String standardOptionCode);
	
	/**
	 * 
	 * @param sr
	 * @param name
	 * @return
	 */
	StandardValue getStandardValue(StandardRecommendation sr, String name);
	
	/**
	 * 添加RecommendationWrap
	 * @param wrap
	 * @return
	 */
	List<StandardRecommendationWrap> addStandardRecommendation(long reportId, List<StandardRecommendation> recommendations);
	
	/**
	 * 通过ReportId 获取 StandardRecommendation
	 * @param reportId
	 * @return
	 */
	List<StandardRecommendationWrap> getStandardRecommendationWraps(long reportId, SolutionType recommendationType);
	
	/**
	 * 通过id获取StandardRecommendationWrap
	 * @param id
	 * @return
	 */
	StandardRecommendationWrap getStandardRecommendationWrap(long id);
	
	/**
	 * report 可为null
	 * 根据对应report的GDAR LIG, 加工返回到GDP用的GDIP的改进，根据GDAR LIG 的改进勾选情况来勾选GDP用的GDIP的改进
	 * @param report
	 * @param nodataWraps
	 * @return
	 */
	List<StandardRecommendationWrap> processNodataWraps(Report report, List<StandardRecommendationWrap> nodataWraps);
}
