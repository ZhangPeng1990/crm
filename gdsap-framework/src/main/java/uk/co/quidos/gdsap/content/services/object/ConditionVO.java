/**
 * 
 */
package uk.co.quidos.gdsap.content.services.object;

import java.util.Date;

import uk.co.quidos.gdsap.content.Category;

/**
 * @author peng.shi
 *
 */
public class ConditionVO
{
	private Category category;
	private String title;
	private Date startInsertTime;
	private Date endInsertTime;
	
	/**
	 * @return the category
	 */
	public Category getCategory()
	{
		return category;
	}
	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}
	/**
	 * @return the startInsertTime
	 */
	public Date getStartInsertTime()
	{
		return startInsertTime;
	}
	/**
	 * @return the endInsertTime
	 */
	public Date getEndInsertTime()
	{
		return endInsertTime;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category)
	{
		this.category = category;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}
	/**
	 * @param startInsertTime the startInsertTime to set
	 */
	public void setStartInsertTime(Date startInsertTime)
	{
		this.startInsertTime = startInsertTime;
	}
	/**
	 * @param endInsertTime the endInsertTime to set
	 */
	public void setEndInsertTime(Date endInsertTime)
	{
		this.endInsertTime = endInsertTime;
	}
	
}
