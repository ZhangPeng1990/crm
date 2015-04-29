package uk.co.quidos.gdsap.framework.sys.business;


import java.io.Serializable;

/**
 * @author shipeng
 */
public abstract class AbstractBusinessObject implements Serializable
{
	private static final long serialVersionUID = 4390168867220575257L;
	private static final int PRIME = 31;
	/**
	 * 获取业务对象唯一标示
	 * @return
	 */
	public abstract Serializable getId();
	
//	@Override
//	public String toString()
//	{
//		return ToStringBuilder.reflectionToString(this);
//	}

	/*
	 * 重载equals，用来判断业务对象是否相同
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}

		boolean typeable = obj instanceof AbstractBusinessObject;
		if (!typeable)
		{
			return false;
		}
		AbstractBusinessObject bo = (AbstractBusinessObject) obj;
		if (this.getId().equals(bo.getId()))
		{
			return true;
		}
		return false;
	}

	/**
	 * 获取CacheKey,格式如下 ： com.vanstone.epp.content.Article_1,com.vanstone.epp.content.Category_1
	 * @return
	 */
	public String getCacheKey()
	{
		if (this.getId() == null)
		{
			throw new IllegalArgumentException();
		}
		return this.getClass().getName() + "_" + this.getId();
	}
	
	/**
	 * 转换成json
	 * @return
	 */
	public String toJson()
	{
		//Gson gson = new Gson();
		//return gson.toJson(this);
		return null;
	}
	
	/**
	 * 验证当前业务对象是否合法
	 */
	public void validate()
	{
		
	}
	
	/**
	 * 验证是否当前业务对象是否初始化
	 * @return
	 */
	public boolean initialable()
	{
		return true;
	}
	@Override
	public int hashCode()
	{
		if (this.getId() == null)
		{
			return super.hashCode();
		}
		else
		{
			int result = 1;
			return PRIME * result + this.getId().hashCode();
		}
	}
}
