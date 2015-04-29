/**
 * 
 */
package uk.co.quidos.gdsap.framework.sys.business;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;



/**
 * @author peng.shi
 */
public enum OrderDirection implements BaseEnum<String>
{
	desc("DESC"),asc("ASC");
	
	private OrderDirection(String dbOrder){
		this.dbOrder = dbOrder;
	}
	
	private String dbOrder;
	
	public String getDbOrder() {
		return dbOrder;
	}

	@Override
	public String getCode()
	{
		return this.toString().toUpperCase();
	}

	@Override
	public String getDesc()
	{
		return this.toString();
	}
	
}
