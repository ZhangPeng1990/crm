/**
 * 
 */
package uk.co.quidos.gdsap.framework.log.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.log.LogType;
import uk.co.quidos.gdsap.framework.log.persistence.GdsapAltTypeMapper;
import uk.co.quidos.gdsap.framework.log.persistence.object.GdsapAltType;
import uk.co.quidos.gdsap.framework.log.services.LogTypeServiceMgr;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
@Transactional
@Service("logTypeServiceMgr")
public class LogTypeServiceMgrImpl extends AbsBusinessObjectServiceMgr implements LogTypeServiceMgr
{
	//@Autowired
	private GdsapAltTypeMapper gdsapAltTypeMapper;
	
	/**
	 * @return the gdsapAltTypeMapper
	 */
	public GdsapAltTypeMapper getGdsapAltTypeMapper()
	{
		return gdsapAltTypeMapper;
	}

	/**
	 * @param gdsapAltTypeMapper the gdsapAltTypeMapper to set
	 */
	public void setGdsapAltTypeMapper(GdsapAltTypeMapper gdsapAltTypeMapper)
	{
		this.gdsapAltTypeMapper = gdsapAltTypeMapper;
	}

	@Override
	public LogType getLogType(String id)
	{
		Assert.hasText(id);
		LogType cacheLogType = this.getFromCache(LogType.class, id);
		if (cacheLogType == null)
		{
			GdsapAltType modelOfLoad = this.getGdsapAltTypeMapper().load(id);
			if (modelOfLoad != null)
			{
				LogType logType = BeanUtils.do2bo(modelOfLoad);
				this.setInCache(logType);
				return logType;
			}
			return null;
		}
		return cacheLogType;
	}

	@Override
	public LogType addLogType(LogType logType) throws ObjectDuplicateException
	{
		Assert.notNull(logType);
		this._validate(logType);
		GdsapAltType modelOfLoad = this.getGdsapAltTypeMapper().load(logType.getId());
		if (modelOfLoad != null)
		{
			throw new ObjectDuplicateException();
		}
		GdsapAltType model = BeanUtils.bo2do(logType);
		this.getGdsapAltTypeMapper().insert(model);
		this.setInCache(logType);
		return logType;
	}

	@Override
	public LogType updateLogType(LogType logType)
	{
		Assert.notNull(logType);
		this._validate(logType);
		
		GdsapAltType modelOfLoad = this.getGdsapAltTypeMapper().load(logType.getId());
		if (modelOfLoad == null)
		{
			this.assertThrowIllegalArgumentException();
		}
		
		modelOfLoad.setDefContent(logType.getDefContent());
		modelOfLoad.setTitle(logType.getTitle());
		
		this.getGdsapAltTypeMapper().update(modelOfLoad);
		this.setInCache(logType);
		return logType;
	}

	@Override
	public List<LogType> getLogTypes(String title, int offset, int limit)
	{
		List<String> ids = this.getGdsapAltTypeMapper().findPageBreakAllIdsByTitle(title,new RowBounds(offset,limit));
		if (!CollectionUtils.isEmpty(ids))
		{
			List<LogType> types = new ArrayList<LogType>();
			for (String id : ids)
			{
				LogType type = this.getLogType(id);
				types.add(type);
			}
			return types;
		}
		return null;
	}

	@Override
	public int getTotalLogTypes(String title)
	{
		return this.getGdsapAltTypeMapper().findNumberByTitle(title);
	}
	
	private void _validate(LogType logType)
	{
		Assert.hasText(logType.getId());
		Assert.hasText(logType.getTitle());
		Assert.hasText(logType.getDefContent());
	}
}
