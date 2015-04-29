package uk.co.quidos.gdsap.framework.common.persistence.object;

import java.util.Date;

import uk.co.quidos.dal.object.AbstractDO;

/**
 * @author peng.shi
 *
 */
public class GdsapSysPreference extends AbstractDO
{
	private static final long serialVersionUID = 9021825368955010954L;

	private String id;

    private String title;

    private Integer preType;

    private Date insertTime;

    private Date updateTime;

    private Integer inputType;

    private Integer controlType;

    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPreType() {
        return preType;
    }

    public void setPreType(Integer preType) {
        this.preType = preType;
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

    public Integer getInputType() {
        return inputType;
    }

    public void setInputType(Integer inputType) {
        this.inputType = inputType;
    }

    public Integer getControlType() {
        return controlType;
    }

    public void setControlType(Integer controlType) {
        this.controlType = controlType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}