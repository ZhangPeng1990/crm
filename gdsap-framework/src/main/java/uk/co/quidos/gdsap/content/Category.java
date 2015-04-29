/**
 * 
 */
package uk.co.quidos.gdsap.content;

import uk.co.quidos.gdsap.content.enums.CategoryType;
import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 *
 */
public class Category extends AbsBusinessObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1296855567813154428L;

	/**
	 * ID
	 */
	private Long id;
	
    /**
     *栏目标题
     */
    private String title;
    /**
     * 栏目类型，单篇文章或者多篇文章
     */
    private CategoryType categoryType;
    /**
     * 是否允许评论
     */
    private YesNo commentable;
    /**
     * 显示排序
     */
    private int sort;
    /**
     * 栏目介绍
     */
    private String des;
    /**
     * 栏目下文章总数
     */
    private int totalArticles;
    
	/**
	 * @return the totalArticles
	 */
	public int getTotalArticles()
	{
		return totalArticles;
	}
	/**
	 * @param totalArticles the totalArticles to set
	 */
	public void setTotalArticles(int totalArticles)
	{
		this.totalArticles = totalArticles;
	}
	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}
	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}
	/**
	 * @return the categoryType
	 */
	public CategoryType getCategoryType()
	{
		return categoryType;
	}
	/**
	 * @return the commentable
	 */
	public YesNo getCommentable()
	{
		return commentable;
	}
	/**
	 * @return the sort
	 */
	public int getSort()
	{
		return sort;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}
	/**
	 * @param categoryType the categoryType to set
	 */
	public void setCategoryType(CategoryType categoryType)
	{
		this.categoryType = categoryType;
	}
	/**
	 * @param commentable the commentable to set
	 */
	public void setCommentable(YesNo commentable)
	{
		this.commentable = commentable;
	}
	/**
	 * @param sort the sort to set
	 */
	public void setSort(int sort)
	{
		this.sort = sort;
	}
	public String getDes()
	{
		return des;
	}
	public void setDes(String des)
	{
		this.des = des;
	}
	
}
