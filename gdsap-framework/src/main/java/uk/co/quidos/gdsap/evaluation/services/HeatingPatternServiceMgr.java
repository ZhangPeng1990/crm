/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services;

import uk.co.quidos.gdsap.evaluation.HeatingPattern;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
public interface HeatingPatternServiceMgr extends BusinessObjectServiceMgr
{
	public static final String SERVICE_NAME = "heatingPatternServiceMgr";
	
	/**
	 * 获取HeatingPattern
	 * @param reportid
	 * @return
	 */
	HeatingPattern getHeatingPattern(long reportid);
	
	/**
	 * 添加HeatingPattern ，需要判断当前report 下是否存在 HeatingPattern，如果存在，则throws IllegalArgumentException();
	 * hp 下如果YesNo参数为null，则赋值默认值为YesNo.No
	 * @param hp
	 * @return
	 */
	HeatingPattern addHeatingPattern(HeatingPattern hp);
	
	/**
	 * 判断当前当前Report 下是否存在 HeatingPattern，如果不存在，throws IllegalArgumentException
	 * @param hp
	 * @return
	 */
	HeatingPattern updateHeatingPattern(HeatingPattern hp);
	
	/**
	 * 删除掉当前heatingPattern
	 * @param reportId
	 */
	void deleteHeatingPattern(long reportId);
	
}
