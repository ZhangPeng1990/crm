package uk.co.quidos.gdsap.framework.sys.business;

import uk.co.quidos.cache.CacheManager;

/**
 * <strong>BusinessObjectServiceMgr</strong><br>
 * <br> 
 * <strong>Create on : 2011-12-31<br></strong>
 * <p>
 * <strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong>
 * <p>
 * @author peng.shi peng.shi@ecointel.com.cn<br>
 * @version <strong>Ecointel v1.0.0</strong><br>
 */
public interface BusinessObjectServiceMgr {
	
	/**
	 * 获得当前配置的MemcacheManager
	 * @return
	 */
	CacheManager getMemCacheManager();
	
	/**
	 * 设定业务对象到Cache
	 * @param object
	 */
	<T extends AbsBusinessObject> void setInCache(T t);
	
	/**
	 * 从Cache中删除业务对象
	 * @param object
	 */
	<T extends AbsBusinessObject> void deleteFromCache(T t);
	
	/**
	 * 从Cache中获取业务对象
	 * @param clazz
	 * @param id
	 * @return
	 */
	<T extends AbsBusinessObject> T getFromCache(Class<T> clazz,java.io.Serializable id);
	
}
