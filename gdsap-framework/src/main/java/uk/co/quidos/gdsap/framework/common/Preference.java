/**
 * 
 */
package uk.co.quidos.gdsap.framework.common;

import java.util.Date;

import uk.co.quidos.gdsap.framework.common.enums.ControlType;
import uk.co.quidos.gdsap.framework.common.enums.InputType;
import uk.co.quidos.gdsap.framework.common.enums.PreType;
import uk.co.quidos.gdsap.framework.common.enums.PreferenceRelId;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 */
public class Preference extends AbsBusinessObject
{
	private static final long serialVersionUID = 2806270312811525936L;
	
	private PreferenceRelId preferenceRelId;
	private PreType preType;
	private String title;
	private String content;
	private Date insertTime;
	private Date updateTime;
	private InputType inputType;
	private ControlType controlType;
	
	/**
	 * @return the inputType
	 */
	public InputType getInputType()
	{
		return inputType;
	}

	/**
	 * @param inputType the inputType to set
	 */
	public void setInputType(InputType inputType)
	{
		this.inputType = inputType;
	}

	/**
	 * @return the controlType
	 */
	public ControlType getControlType()
	{
		return controlType;
	}

	/**
	 * @param controlType the controlType to set
	 */
	public void setControlType(ControlType controlType)
	{
		this.controlType = controlType;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.sys.business.BusinessObject#getId()
	 */
	@Override
	public String getId()
	{
		return this.preferenceRelId.getCode();
	}

	/**
	 * @return the preType
	 */
	public PreType getPreType()
	{
		return preType;
	}

	/**
	 * @param preType the preType to set
	 */
	public void setPreType(PreType preType)
	{
		this.preType = preType;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

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
	 * @return the insertTime
	 */
	public Date getInsertTime()
	{
		return insertTime;
	}

	/**
	 * @param insertTime the insertTime to set
	 */
	public void setInsertTime(Date insertTime)
	{
		this.insertTime = insertTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime()
	{
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public PreferenceRelId getPreferenceRelId()
	{
		return preferenceRelId;
	}

	public void setPreferenceRelId(PreferenceRelId preferenceRelId)
	{
		this.preferenceRelId = preferenceRelId;
	}
	
}
