package uk.co.quidos.gdsap.framework.sys.business;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import uk.co.quidos.cache.CacheManager;
import uk.co.quidos.cache.CacheManagerFactory;
import uk.co.quidos.cache.CacheUtils;

/**
 * <strong>AbsBusinessObjectServiceMgr</strong><br>
 * <br> 
 * <strong>Create on : 2011-12-31<br></strong>
 * <p>
 * <strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong>
 * <p>
 * @author peng.shi peng.shi@ecointel.com.cn<br>
 * @version <strong>Ecointel v1.0.0</strong><br>
 */
public abstract class AbsBusinessObjectServiceMgr implements BusinessObjectServiceMgr {

	protected Log log = null;

	public AbsBusinessObjectServiceMgr() {
		log = LogFactory.getLog(this.getClass());
	}
	
	public CacheManager getMemCacheManager()
	{
		CacheManager cm = CacheManagerFactory.getInstance().getMemCacheManager();
		if (cm == null) {
			throw new ExceptionInInitializerError("Cache Manager Initial Error,Please Check cache config.");
		}
		return cm;
	}

	/* (non-Javadoc)
	 * @see cn.com.ecointel.roomyi.framework.sys.business.BusinessObjectServiceMgr#setInCache(cn.com.ecointel.roomyi.framework.sys.business.BusinessObject)
	 */
	@Override
	public <T extends AbsBusinessObject> void setInCache(T t)
	{
		Assert.notNull(t);
		this.getMemCacheManager().set(CacheUtils.keyOfClass(t.getClass(), t.getId()),t);
	}

	/* (non-Javadoc)
	 * @see cn.com.ecointel.roomyi.framework.sys.business.BusinessObjectServiceMgr#deleteFromCache(cn.com.ecointel.roomyi.framework.sys.business.BusinessObject)
	 */
	@Override
	public <T extends AbsBusinessObject> void deleteFromCache(T t)
	{
		Assert.notNull(t);
		Assert.notNull(t.getId());
		this.getMemCacheManager().delete(CacheUtils.keyOfObject(t, t.getId()));
	}

	/* (non-Javadoc)
	 * @see cn.com.ecointel.roomyi.framework.sys.business.BusinessObjectServiceMgr#getFromCache(java.lang.Class, java.io.Serializable)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends AbsBusinessObject> T getFromCache(Class<T> clazz, Serializable id)
	{
		Assert.notNull(clazz);
		Assert.notNull(id);
		AbsBusinessObject obj = (AbsBusinessObject)this.getMemCacheManager().get(CacheUtils.keyOfClass(clazz, id));
		return (T)obj;
	}
	
	/**
	 * throws IllegalArgumentException
	 */
	public void assertThrowIllegalArgumentException()
	{
		throw new IllegalArgumentException();
	}
}
