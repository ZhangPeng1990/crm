/**
 * 
 */
package uk.co.quidos.gdsap.evaluation;

import uk.co.quidos.gdsap.evaluation.enums.Language;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 *
 */
public class RelatedPartyDisclosure extends AbsBusinessObject
{

	private static final long serialVersionUID = -8953314459983375630L;
	
	/**
	 * xml 中对应的 Disclosure-Code
	 */
	private Integer id;
	/**
	 * xml 中的Disclosure-Text
	 */
	private String desc;
	/**
	 * 语言
	 */
	private Language language;
	
	/**
	 * @return the language
	 */
	public Language getLanguage()
	{
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(Language language)
	{
		this.language = language;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject#getId()
	 */
	@Override
	public Integer getId()
	{
		return this.id;
	}

	/**
	 * @return the desc
	 */
	public String getDesc()
	{
		return desc;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc)
	{
		this.desc = desc;
	}

}
