/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;

import uk.co.quidos.gdsap.evaluation.Report;
import uk.co.quidos.gdsap.evaluation.StandardRecommendationWrap;
import uk.co.quidos.gdsap.evaluation.enums.SolutionType;
import uk.co.quidos.gdsap.evaluation.enums.TariffType;
import uk.co.quidos.gdsap.framework.exception.CalculateException;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * 计算接口
 * 负责计算整合以及GDOA原始数据整理xml，以及计算结果整理
 * @author peng.shi
 */
public interface CalServiceMgr extends BusinessObjectServiceMgr
{
	public static final String SERVICE_NAME = "calServiceMgr";
	
	/**
	 * 计算获取改进建议，并清除掉以前的改进建议,并保存到数据库中
	 * @param reportId
	 * @return
	 * @throws Exception 
	 */
	List<StandardRecommendationWrap> calReport(long reportId, SolutionType solutionType) throws Exception;
	
	/**
	 * StandardRecommendationCalResult Key，作用于Map中，对应结果集合为 List<StandardRecommendationCalResult>
	 */
	public static final String SRC_Cal_Result_List_Key = "SRC_Cal_Result_List";
	
	/**
	 * Fuel 计算结果列表 Key，作用于Key,对应结果集合为List<FuelCalResult>
	 */
	public static final String Fuel_Cal_Result_List_Key = "Fuel_Cal_Result_List";
	
	/**
	 * 公共计算结果Cal Result ,作用于 Cal_Result,对应结果对象为 CalResult
	 */
	public static final String Cal_Result_Key = "Cal_Result";
	
	/**
	 * 重新计算Solution
	 * 返回Map ： 
	 * @param solutionId
	 * @return
	 * @throws Exception 
	 */
	Map<String, Object> calSolution(long solutionId,List<StandardRecommendationWrap> srsw) throws Exception;
	
	/**
	 * 计算前的准备工作，返回完整的计算文件
	 * @param report
	 * @param srsw
	 * @return
	 * @throws Exception
	 */
	Document prepareCal(Report report,List<StandardRecommendationWrap> srsw) throws CalculateException;
	
	/**
	 * 计算并返回计算结果xml数据
	 * @param report
	 * @param srs
	 * @return
	 * @throws Exception 
	 */
	String cal_ReturnResult(Report report,List<StandardRecommendationWrap> srsw, SolutionType calculateType) throws CalculateException;
	
	/**
	 * 是否为标准电价
	 * @param report
	 * @return
	 * @throws Exception 
	 */
	TariffType EleTariffType(Report report) throws Exception;
	
}
