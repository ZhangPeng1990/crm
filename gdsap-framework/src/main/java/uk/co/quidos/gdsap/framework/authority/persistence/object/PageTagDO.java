package uk.co.quidos.gdsap.framework.authority.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class PageTagDO extends AbstractDO
{
	private static final long serialVersionUID = 8229081248968327169L;
	private String id;
	private String title;
	private String url;
	private Integer sequence;
	private String pagetagGroup;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
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

	public String getPagetagGroup()
	{
		return pagetagGroup;
	}

	public void setPagetagGroup(String pagetagGroup)
	{
		this.pagetagGroup = pagetagGroup;
	}
}