/**
 * 
 */
package uk.co.quidos.gdsap.framework.log;

import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 *
 */
public class LogType extends AbsBusinessObject
{
	private static final long serialVersionUID = -492311768786211000L;
	
	private String id;
	private String title;
	private String defContent;
	
	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
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
	 * @return the defContent
	 */
	public String getDefContent()
	{
		return defContent;
	}
	/**
	 * @param defContent the defContent to set
	 */
	public void setDefContent(String defContent)
	{
		this.defContent = defContent;
	}
	
	
}
