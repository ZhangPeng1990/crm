/**
 * 
 */
package uk.co.quidos.gdsap.framework.log.services.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import uk.co.quidos.common.util.DateUtil;
import uk.co.quidos.common.util.MessageUtil;
import uk.co.quidos.dal.common.sequence.UUIDGenerator;
import uk.co.quidos.gdsap.framework.enums.UserType;
import uk.co.quidos.gdsap.framework.log.AuditLog;
import uk.co.quidos.gdsap.framework.log.LogType;
import uk.co.quidos.gdsap.framework.log.enums.LogLevel;
import uk.co.quidos.gdsap.framework.log.persistence.GdsapAltLogMapper;
import uk.co.quidos.gdsap.framework.log.persistence.object.GdsapAltLog;
import uk.co.quidos.gdsap.framework.log.services.LogServiceMgr;
import uk.co.quidos.gdsap.framework.log.services.LogTypeServiceMgr;
import uk.co.quidos.gdsap.framework.log.services.object.ConditionVO;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;
import uk.co.quidos.gdsap.framework.sys.lang.enums.EnumUtils;

/**
 * @author peng.shi
 */
@Transactional
@Service("logServiceMgr")
public class LogServiceMgrImpl extends AbsBusinessObjectServiceMgr implements LogServiceMgr
{
	//@Autowired
	private GdsapAltLogMapper gdsapAltLogMapper;
	//@Autowired
	private LogTypeServiceMgr logTypeServiceMgr;
	
	/**
	 * @return the logTypeServiceMgr
	 */
	public LogTypeServiceMgr getLogTypeServiceMgr()
	{
		return logTypeServiceMgr;
	}

	/**
	 * @param logTypeServiceMgr the logTypeServiceMgr to set
	 */
	public void setLogTypeServiceMgr(LogTypeServiceMgr logTypeServiceMgr)
	{
		this.logTypeServiceMgr = logTypeServiceMgr;
	}

	/**
	 * @return the gdsapAltLogMapper
	 */
	public GdsapAltLogMapper getGdsapAltLogMapper()
	{
		return gdsapAltLogMapper;
	}

	/**
	 * @param gdsapAltLogMapper the gdsapAltLogMapper to set
	 */
	public void setGdsapAltLogMapper(GdsapAltLogMapper gdsapAltLogMapper)
	{
		this.gdsapAltLogMapper = gdsapAltLogMapper;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.log.services.LogServiceMgr#add(uk.co.quidos.gdsap.framework.log.AuditLog, java.lang.Object[])
	 */
	@Override
	public AuditLog add(AuditLog auditlog, Object[] params)
	{
		Assert.notNull(auditlog);
		Assert.notNull(auditlog.getLevel());
		Assert.notNull(auditlog.getLogType());
		Assert.hasText(auditlog.getLogType().getId());
		
		LogType tmpLogType = this.getLogTypeServiceMgr().getLogType(auditlog.getLogType().getId());
		if (tmpLogType == null)
		{
			this.assertThrowIllegalArgumentException();
		}
		
		String id = UUIDGenerator.nextId();
		GdsapAltLog model = BeanUtils.bo2do(auditlog);
		model.setId(id);
		model.setInsertTime(new Date());
		model.setLevel(auditlog.getLevel().getCode());
		model.setTypeId(tmpLogType.getId());
		if (auditlog.getUserType() != null)
		{
			model.setUserType(auditlog.getUserType().getCode());
		}
		
		if (!ArrayUtils.isEmpty(params))
		{
			String content = MessageUtil.pattern2String(tmpLogType.getDefContent(), params);
			model.setContent(content);
		}
		
		this.getGdsapAltLogMapper().insert(model);
		
		auditlog = BeanUtils.do2bo(model);
		
		auditlog.setLogType(tmpLogType);
		auditlog.setLevel((LogLevel)EnumUtils.getByCode(model.getLevel(), LogLevel.class));
		if (model.getUserType() != null)
		{
			auditlog.setUserType((UserType)EnumUtils.getByCode(model.getUserType(), UserType.class));
		}
		return auditlog;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.log.services.LogServiceMgr#getAuditLogs(uk.co.quidos.gdsap.framework.log.services.object.ConditionVO, int, int)
	 */
	@Override
	public List<AuditLog> getAuditLogs(ConditionVO conditonVO, int offset, int limit)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.log.services.LogServiceMgr#getTotalAuditLogs(uk.co.quidos.gdsap.framework.log.services.object.ConditionVO)
	 */
	@Override
	public int getTotalAuditLogs(ConditionVO conditionVO)
	{
		return 0;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.log.services.LogServiceMgr#deleteAuditLog(uk.co.quidos.gdsap.framework.log.AuditLog)
	 */
	@Override
	public void deleteAuditLog(AuditLog auditlog)
	{
		Assert.notNull(auditlog);
		Assert.hasText(auditlog.getId());
		GdsapAltLog model = this.getGdsapAltLogMapper().load(auditlog.getId());
		if (model != null)
		{
			this.getGdsapAltLogMapper().delete(auditlog.getId());
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.log.services.LogServiceMgr#deleteAuditLogs(java.util.Date, java.util.Date)
	 */
	@Override
	public void deleteAuditLogs(Date startInsertTime, Date endInsertTime)
	{
		if (startInsertTime == null && endInsertTime == null)
		{
			return;
		}
		if (startInsertTime != null)
		{
			startInsertTime = DateUtil.getStartTimeOfDate(startInsertTime);
		}
		if (endInsertTime != null)
		{
			endInsertTime = DateUtil.getEndTimeOfDate(endInsertTime);
		}
		this.getGdsapAltLogMapper().deleteByInsertTimeRange(startInsertTime, endInsertTime);
	}
	
}
