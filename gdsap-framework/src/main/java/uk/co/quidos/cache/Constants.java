package uk.co.quidos.cache;


import java.util.Date;

/**
 * @author shipeng
 */
public final class Constants
{
	/**
	 * 5分钟
	 */
	public static final long MINUTE_OF_5 = 1000*60*5;
	
	/**
	 * 5分钟 日期格式 
	 */
	public static final Date MINUTE_OF_5_DATE = new Date(1000*60*5);
	
	/**
	 * 10分钟
	 */
	public static final long MINUTE_OF_10 = MINUTE_OF_5*2;
	
	/**
	 * 10分钟 日期格式 
	 */
	public static final Date MINUTE_OF_10_DATE = new Date(MINUTE_OF_5*2);
	
	/**
	 * 30分钟
	 */
	public static final long MINUTE_OF_30 = MINUTE_OF_5*6;
	
	/**
	 * 30分钟 日期格式 
	 */
	public static final Date MINUTE_OF_30_DATE = new Date(MINUTE_OF_5*6);
	
	/**
	 * 1小时
	 */
	public static final long MINUTE_OF_1H = MINUTE_OF_30*2;
	
	/**
	 * 1小时 日期格式 
	 */
	public static final Date MINUTE_OF_1H_DATE = new Date(MINUTE_OF_30*2);
	
	/**
	 * 12小时 
	 */
	public static final long MINUTE_OF_12H = MINUTE_OF_1H*12;
	
	/**
	 * 12小时 日期格式 
	 */
	public static final Date MINUTE_OF_12H_DATE = new Date(MINUTE_OF_1H*12);
	
	/**
	 * 24小时
	 */
	public static final long MINUTE_OF_24H = MINUTE_OF_12H*2;
	
	/**
	 * 24小时 日期格式 
	 */
	public static final Date MINUTE_OF_24H_DATE = new Date(MINUTE_OF_12H*2);
	
	/**
	 * 缓存信号量
	 */
	public static final String MUTEX_SUFFIX=  "_MUTEX_";
	
	/**
	 * 信号量值
	 */
	public static final String MUTEX_VALUE = "MUTEX_VALUE";
	
	/**
	 * 信号量时间
	 */
	public static final int MUTEX_TIME = 1000*60;
	
	/**
	 * 信号量时间日期类型
	 */
	public static final Date MUTEX_TIME_DATE = new Date(MUTEX_TIME);
	/**
	 * 从Cache获取等待信号量时间
	 */
	public static final long GET_FROM_CACHE_SLEEP_TIME = 1000;
	
}
