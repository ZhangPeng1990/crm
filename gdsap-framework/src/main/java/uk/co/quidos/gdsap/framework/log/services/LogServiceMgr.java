/**
 * 
 */
package uk.co.quidos.gdsap.framework.log.services;

import java.util.Date;
import java.util.List;

import uk.co.quidos.gdsap.framework.log.AuditLog;
import uk.co.quidos.gdsap.framework.log.services.object.ConditionVO;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * @author peng.shi
 */
public interface LogServiceMgr extends BusinessObjectServiceMgr
{
	public static final String SERVICE_NAME = "logServiceMgr";
	
	/**
	 * 添加Auditlog
	 * @param auditlog
	 * @param params
	 * @return
	 */
	AuditLog add(AuditLog auditlog,Object[] params);
	
	/**
	 * 查询审计日志
	 * @param conditonVO
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<AuditLog> getAuditLogs(ConditionVO conditonVO,int offset,int limit);
	
	/**
	 * 获取审计日志数量(查询)
	 * @param conditionVO
	 * @return
	 */
	int getTotalAuditLogs(ConditionVO conditionVO);
	
	/**
	 * 删除Auditlog
	 * @param auditlog
	 */
	void deleteAuditLog(AuditLog auditlog);
	
	/**
	 * 删除Auditlogs
	 * @param startInsertTime
	 * @param endInsertTime
	 */
	void deleteAuditLogs(Date startInsertTime,Date endInsertTime);
	
}
