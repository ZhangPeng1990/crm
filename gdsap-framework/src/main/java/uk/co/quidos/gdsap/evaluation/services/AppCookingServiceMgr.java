/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services;

import uk.co.quidos.gdsap.evaluation.AppCooking;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * 每个AppCooking与Report为一对一关系，每个report 只存在一个AppCooking
 * @author peng.shi
 */
public interface AppCookingServiceMgr extends BusinessObjectServiceMgr
{
	public static final String SERVICE_NAME = "appCookingServiceMgr";
	
	/**
	 * 添加AppCooking，如果当前Report 下已经存在AppCooking ，则直接throws IllegalArgumentException
	 * @param ac
	 * @return
	 */
	AppCooking addAppCooking(AppCooking ac);
	
	/**
	 * 更新 AppCooking，如果当前Report 下不存在AppCooking，则throws IllegalArgumentException
	 * @param ac
	 * @return
	 */
	AppCooking updateAppCooking(AppCooking ac);
	
	/**
	 * 获取AppCooking
	 * @param reportId
	 * @return
	 */
	AppCooking getAppCooking(long reportId);
	
	/**
	 * 删除AppCooking
	 * @param reportId
	 */
	void deleteAppCooking(long reportId);
	
}
