package uk.co.quidos.gdsap.content.persistence.object;

import java.util.Date;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapConArticle extends AbstractDO{

	private static final long serialVersionUID = -8250818075064767446L;

	private Long id;

    private Long categoryId;

    private String title;

    private String summary;

    private Date insertTime;

    private Date updateTime;

    private Integer topable;

    private Date topableStartTime;

    private Date topableEndTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getTopable() {
        return topable;
    }

    public void setTopable(Integer topable) {
        this.topable = topable;
    }

    public Date getTopableStartTime() {
        return topableStartTime;
    }

    public void setTopableStartTime(Date topableStartTime) {
        this.topableStartTime = topableStartTime;
    }

    public Date getTopableEndTime() {
        return topableEndTime;
    }

    public void setTopableEndTime(Date topableEndTime) {
        this.topableEndTime = topableEndTime;
    }
}