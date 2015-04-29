package uk.co.quidos.gdsap.content.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapConCategory extends AbstractDO {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1882198260169534018L;

	private Long id;
    private String title;
    private Integer categoryType;
    private Integer commentable;
    private Integer sort;
    private String des;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    public Integer getCommentable() {
        return commentable;
    }

    public void setCommentable(Integer commentable) {
        this.commentable = commentable;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
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