/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services;

import java.io.File;
import java.util.List;

import uk.co.quidos.gdsap.evaluation.EpcImprovementCalResult;
import uk.co.quidos.gdsap.evaluation.Solution;
import uk.co.quidos.gdsap.evaluation.StandardRecommendationCalResult;
import uk.co.quidos.gdsap.evaluation.StandardRecommendationWrap;
import uk.co.quidos.gdsap.evaluation.enums.ReportLocation;
import uk.co.quidos.gdsap.evaluation.object.TotalEpcImprovementCalResult;
import uk.co.quidos.gdsap.evaluation.object.TotalEstimatedCosts;
import uk.co.quidos.gdsap.evaluation.wsclient.ResponseObject;
import uk.co.quidos.gdsap.framework.exception.CalculateException;

/**
 * @author peng.shi
 *
 */
public interface ReportCommonServiceMgr
{
	/**
	 * 
	 */
	public static final String SERVICE_NAME = "reportCommonServiceMgr";
	
	/**
	 * 计算生成Solution的Xml文件,文件模板从preference中获取
	 * @param solution
	 * @return
	 */
	String solutionCalResultXml(Solution solution, List<StandardRecommendationWrap> srsw) throws CalculateException;
	
	public TotalEpcImprovementCalResult getTotalEpcImprovementCalResult(List<EpcImprovementCalResult> epcImprovementCalResults);
	
	public TotalEstimatedCosts getTotalEstimatedCosts(List<StandardRecommendationCalResult> srCalResuls);
	
	/**
	 * 上传Report
	 * @param solution
	 * @return
	 */
	ResponseObject lodgeReport(Solution solution);
	
	/**
	 * 根据邮编寻址
	 * @param postCode
	 * @return
	 */
	ResponseObject findAddressList(String postCode);
	/**
	 * 根据邮编寻址
	 * @param postCode
	 * @return
	 */
	ResponseObject findAddress(String  country, String id);
	
	/**
	 * @param lodgeFile
	 * @param reportLocation
	 */
	void prettyXmlFormat(File lodgeFile,ReportLocation reportLocation);
	
	public void getPdf(Solution solution);
	
}
