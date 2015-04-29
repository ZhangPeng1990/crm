/**
 * 
 */
package uk.co.quidos.gdsap.framework.authority;

import uk.co.quidos.gdsap.framework.authority.enums.PagetagGroup;
import uk.co.quidos.gdsap.framework.sys.business.AbstractBusinessObject;


/**
 * @author peng.shi
 */
public class PageTag extends AbstractBusinessObject
{
	private static final long serialVersionUID = 2149651559991398065L;
	private String id;
	private String title;
	private String url;
	private Integer sequence;
	private PagetagGroup pagetagGroup;
	
	@Override
	public String getId()
	{
		return id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public Integer getSequence()
	{
		return sequence;
	}

	public void setSequence(Integer sequence)
	{
		this.sequence = sequence;
	}

	public PagetagGroup getPagetagGroup()
	{
		return pagetagGroup;
	}

	public void setPagetagGroup(PagetagGroup pagetagGroup)
	{
		this.pagetagGroup = pagetagGroup;
	}

	public void setId(String id)
	{
		this.id = id;
	}
	
}
