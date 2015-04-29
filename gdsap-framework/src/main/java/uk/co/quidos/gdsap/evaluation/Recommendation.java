/**
 * 
 */
package uk.co.quidos.gdsap.evaluation;

import uk.co.quidos.gdsap.evaluation.enums.Country;
import uk.co.quidos.gdsap.evaluation.enums.Language;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 *
 */
public class Recommendation extends AbsBusinessObject
{
	private static final long serialVersionUID = -3460995727209664840L;
	
	/**
	 * xml 对应的 code
	 */
	private Integer id;
	/**
	 * xml 中summary
	 */
	private String summary;
	/**
	 * xml 中 heading
	 */
	private String heading;
	/**
	 * xml 中 description
	 */
	private String description;
	
	/**
	 * 语言
	 */
	private Language language;
	
	/**
	 * 国家
	 */
	private Country country;
	
	/**
	 * @return the language
	 */
	public Language getLanguage()
	{
		return language;
	}

	/**
	 * @return the country
	 */
	public Country getCountry()
	{
		return country;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(Language language)
	{
		this.language = language;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country)
	{
		this.country = country;
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
	 * @return the summary
	 */
	public String getSummary()
	{
		return summary;
	}

	/**
	 * @return the heading
	 */
	public String getHeading()
	{
		return heading;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	/**
	 * @param heading the heading to set
	 */
	public void setHeading(String heading)
	{
		this.heading = heading;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
}
