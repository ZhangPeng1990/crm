/**
 * 
 */
package uk.co.quidos.gdsap.framework.log.services;

import java.util.List;

import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.log.LogType;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
public interface LogTypeServiceMgr extends BusinessObjectServiceMgr
{
	public static final String SERVICE_NAME = "logTypeServiceMgr";
	
	/**
	 * 获取LogType
	 * @param id
	 * @return
	 */
	LogType getLogType(String id);
	
	/**
	 * 添加LogType
	 * @param logType
	 * @return
	 * @throws ObjectDuplicateException
	 */
	LogType addLogType(LogType logType) throws ObjectDuplicateException;
	
	/**
	 * 更新LogType
	 * @param logType
	 * @return
	 */
	LogType updateLogType(LogType logType);
	
	/**
	 * 获取列表
	 * @param title
	 * @return
	 */
	List<LogType> getLogTypes(String title,int offset,int limit);
	
	/**
	 * 获取数量
	 * @param title
	 * @return
	 */
	int getTotalLogTypes(String title);
	
}
