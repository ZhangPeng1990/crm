/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services;

import uk.co.quidos.gdsap.evaluation.HeatingSystem;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * @author peng.shi
 */
public interface HeatingSystemServiceMgr extends BusinessObjectServiceMgr
{
	public static final String SERVICE_NAME = "heatingSystemServiceMgr";
	
	/**
	 * 更新 HeatingSystem ，数据初始化是从 ReportService.addReport 中进行初始化，不允许删除，只允许编辑
	 * @param hs
	 * @return
	 */
	HeatingSystem updateHeatingSystem(HeatingSystem hs);
	
	/**
	 * 获取HeatingSystem 通过ReportId 获取 HeatingSystem
	 * @param reportId
	 * @return
	 */
	HeatingSystem getHeatingSystem(long reportId);
	
	/**
	 * @param hs
	 * @return
	 */
	HeatingSystem addHeatingSystem(HeatingSystem hs);
	
	void deleteHeatingSystem(long reportId);
}
