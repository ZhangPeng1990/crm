package uk.co.quidos.gdsap.framework.sys.business;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.quidos.cache.CacheUtils;


/**
 * <strong>AbsBusinessObject</strong><br>
 * <br> 
 * <strong>Create on : 2011-12-31<br></strong>
 * <p>
 * <strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong>
 * <p>
 * @author peng.shi peng.shi@ecointel.com.cn<br>
 * @version <strong>Ecointel v1.0.0</strong><br>
 */
public abstract class AbsBusinessObject implements Serializable{

	private static final long serialVersionUID = 8489377152416733605L;
	
	private static final int PRIME = 31;
	
	private Logger logger = null;
	
	public abstract Serializable getId();
	
	public AbsBusinessObject() {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.logger.debug("BusinessObject Initial.");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/* (non-Javadoc)
	 * @see cn.com.ecointel.roomyi.framework.sys.business.BusinessObject#getCacheKey()
	 */
	public String getCacheKey()
	{
		return CacheUtils.keyOfObject(this.getClass(), this.getId());
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
	
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (this.hashCode() == o.hashCode()) {
			return true;
		}

		if (o instanceof AbsBusinessObject) {
			AbsBusinessObject bo = (AbsBusinessObject) o;
			if (this.getId() != null && this.getId().equals(bo.getId())) {
				return true;
			}
		}
		return false;
	}
}
