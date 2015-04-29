package uk.co.quidos.gdsap.framework.sys.services;


import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import uk.co.quidos.gdsap.framework.sys.business.AbstractBusinessObject;
import uk.co.quidos.cache.CacheManager;
import uk.co.quidos.cache.CacheManagerFactory;
import uk.co.quidos.cache.Constants;

/**
 * @author shipeng
 */
public abstract class AbstractServiceMgr extends TransactionTemplate
{
	private static final long serialVersionUID = -3254640629590025158L;
	
	@Autowired
	@Override
	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		super.setTransactionManager(transactionManager);
	}

	public CacheManager getMemCacheManager()
	{
		CacheManager cm = CacheManagerFactory.getInstance().getMemCacheManager();
		if (cm == null) {
			throw new ExceptionInInitializerError("Cache Manager Initial Error,Please Check cache config.");
		}
		return cm;
	}
	
	public <T extends AbstractBusinessObject> String getBusinessObjectCacheKey(Class<T> clazz,Serializable id)
	{
		Assert.notNull(clazz);
		Assert.notNull(id);
		return clazz.getName() + "_" + id;
	}
	
	public <T extends AbstractBusinessObject> String getBusinessObjectCacheKeyMutex(Class<T> clazz,Serializable id)
	{
		Assert.notNull(clazz);
		Assert.notNull(id);
		return Constants.MUTEX_SUFFIX + clazz.getName() + "_" + id;
	}
}
