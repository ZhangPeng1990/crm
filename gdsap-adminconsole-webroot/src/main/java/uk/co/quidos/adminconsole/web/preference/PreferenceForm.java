package uk.co.quidos.adminconsole.web.preference;

import java.util.Date;


public class PreferenceForm {

	private String preferenceRelId;
	private Integer preType;
	private String title;
	private String content;
	private Date insertTime;
	private Date updateTime;
	private Integer inputType;
	private Integer controlType;
	
	public String getPreferenceRelId() {
		return preferenceRelId;
	}
	public void setPreferenceRelId(String preferenceRelId) {
		this.preferenceRelId = preferenceRelId;
	}
	public Integer getPreType() {
		return preType;
	}
	public void setPreType(Integer preType) {
		this.preType = preType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	
}
