/**
 * 
 */
package uk.co.quidos.gdsap.framework.log.services.impl;

import net.sf.cglib.beans.BeanCopier;
import uk.co.quidos.gdsap.framework.log.AuditLog;
import uk.co.quidos.gdsap.framework.log.LogType;
import uk.co.quidos.gdsap.framework.log.persistence.object.GdsapAltLog;
import uk.co.quidos.gdsap.framework.log.persistence.object.GdsapAltType;

/**
 * 业务对象以及数据对象转换
 * 1. 完成持久化中基本类型与Enum转换等操作，类似于quidos-framework-1.0 中接口赋值
 * 2. 
 * @author peng.shi
 */
public class BeanUtils
{	
	private static BeanCopier gdsapAltType2LogTypeCopier = BeanCopier.create(GdsapAltType.class,LogType.class,false);
	/**
	 * GdsapAltType -> LogType
	 * @param model
	 * @return
	 */
	public static LogType do2bo(GdsapAltType model)
	{
		LogType bo = new LogType();
		gdsapAltType2LogTypeCopier.copy(model, bo, null);
		return bo;
	}
	
	private static BeanCopier gdsapAltLog2AuditLogCopier = BeanCopier.create(GdsapAltLog.class, AuditLog.class, false);
	/**
	 * GdsapAltLog -> AuditLog
     * private Integer level;
     * private Integer userType;
     * private String typeId; 需要传入LogType 参数
     * 以上几项需要自行转换
	 * @param model
	 * @return
	 */
	public static AuditLog do2bo(GdsapAltLog model)
	{
		AuditLog bo = new AuditLog();
		gdsapAltLog2AuditLogCopier.copy(model, bo, null);
		return bo;
	}
	
	private static BeanCopier logType2GdsapAltTypeCopier = BeanCopier.create(LogType.class, GdsapAltType.class, false);
	/**
	 * LogType -> GdsapAltType
	 * @param bo
	 * @return
	 */
	public static GdsapAltType bo2do(LogType bo)
	{
		GdsapAltType model = new GdsapAltType();
		logType2GdsapAltTypeCopier.copy(bo, model, null);
		return model;
	}
	
	private static BeanCopier auditLog2GdsapAltLogCopier = BeanCopier.create(AuditLog.class, GdsapAltLog.class, false);
	/**
	 * AuditLog -> GdsapAltLog
	 * @param bo
	 * @return
	 */
	public static GdsapAltLog bo2do(AuditLog bo)
	{
		GdsapAltLog model = new GdsapAltLog();
		auditLog2GdsapAltLogCopier.copy(bo, model, null);
		return model;
	}
}
