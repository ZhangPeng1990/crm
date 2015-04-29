/**
 * 
 */
package uk.co.quidos.gdsap.framework.authority.services;

import java.util.Map;
import java.util.Set;

import uk.co.quidos.gdsap.framework.authority.PageTag;
import uk.co.quidos.gdsap.framework.authority.enums.PagetagGroup;


/**
 * @author peng.shi
 */
public interface PageTagServiceMgr
{
	public static final String SERVICE_NAME = "pageTagServiceMgr";
	
	/**
	 * 添加pageTag
	 * @param pageTag
	 * @return
	 */
	PageTag addPageTag(PageTag pageTag);
	
	/**
	 * 删除pageTag
	 */
	void deletePageTag(String id);
	
	/**
	 * @param pageTag
	 * @return
	 */
	PageTag updatePageTag(PageTag pageTag);
	
	/**
	 * @param id
	 * @return
	 */
	PageTag getPageTag(String id);
	
	/**
	 * @param parentPageTag
	 * @return
	 */
	Map<PagetagGroup, Set<PageTag>> getPageTags();
	
}
