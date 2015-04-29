/**
 * 
 */
package uk.co.quidos.gdsap.content;

import java.util.Date;

import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 *
 */
public class Article extends AbsBusinessObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5786818835747856374L;
	
	private Long id;
    private Category category;
    private String title;
    private String summary;
    private Date insertTime;
    private Date updateTime;
    /**
     * 是否顶置
     */
    private YesNo topable;
    /**
     * 如果topableStartTime 以及 topableEndTime 都为空，则永不失效始终定制
     */
    private Date topableStartTime;
    private Date topableEndTime;
    private String content;
    
	/**
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content)
	{
		this.content = content;
	}
	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}
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
	 * @return the summary
	 */
	public String getSummary()
	{
		return summary;
	}
	/**
	 * @return the insertTime
	 */
	public Date getInsertTime()
	{
		return insertTime;
	}
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime()
	{
		return updateTime;
	}
	/**
	 * @return the topable
	 */
	public YesNo getTopable()
	{
		return topable;
	}
	/**
	 * @return the topableStartTime
	 */
	public Date getTopableStartTime()
	{
		return topableStartTime;
	}
	/**
	 * @return the topableEndTime
	 */
	public Date getTopableEndTime()
	{
		return topableEndTime;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
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
	 * @param summary the summary to set
	 */
	public void setSummary(String summary)
	{
		this.summary = summary;
	}
	/**
	 * @param insertTime the insertTime to set
	 */
	public void setInsertTime(Date insertTime)
	{
		this.insertTime = insertTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}
	/**
	 * @param topable the topable to set
	 */
	public void setTopable(YesNo topable)
	{
		this.topable = topable;
	}
	/**
	 * @param topableStartTime the topableStartTime to set
	 */
	public void setTopableStartTime(Date topableStartTime)
	{
		this.topableStartTime = topableStartTime;
	}
	/**
	 * @param topableEndTime the topableEndTime to set
	 */
	public void setTopableEndTime(Date topableEndTime)
	{
		this.topableEndTime = topableEndTime;
	}
	
}
