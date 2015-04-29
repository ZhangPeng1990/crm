/**
 * 
 */
package uk.co.quidos.gdsap.framework.sys.business;

/**
 * @author peng.shi
 */
public class BaseQueryObject
{
	private String[] fields;
	private OrderDirection orderDirection;
	
	public String[] getFields()
	{
		return fields;
	}
	public void setFields(String[] fields)
	{
		this.fields = fields;
	}
	public OrderDirection getOrderDirection()
	{
		return orderDirection;
	}
	public void setOrderDirection(OrderDirection orderDirection)
	{
		this.orderDirection = orderDirection;
	}
	
}
