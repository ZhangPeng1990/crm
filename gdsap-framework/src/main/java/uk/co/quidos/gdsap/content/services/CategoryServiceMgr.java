/**
 * 
 */
package uk.co.quidos.gdsap.content.services;

import java.util.List;

import uk.co.quidos.gdsap.content.Category;
import uk.co.quidos.gdsap.framework.exception.ObjectHasSubObjectException;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * @author peng.shi
 * 需要缓冲支持
 */
public interface CategoryServiceMgr extends BusinessObjectServiceMgr
{
	/**
	 * 获取Category 信息
	 * @param id
	 * @return
	 */
	Category getCategory(long id);
	
	/**
	 * 添加Category信息
	 * @param category
	 * @return
	 */
	Category addCategory(Category category);
	
	/**
	 * 更新Category 信息
	 * @param category
	 * @return
	 */
	Category updateCategory(Category category);
	
	/**
	 * 删除Category信息
	 * @param id
	 * @throws 如Category 下存在Articles ，则throws 异常
	 */
	void deleteCategory(long id) throws ObjectHasSubObjectException;
	
	/**
	 * 强制删除Category，如存在文章，则一并删除掉
	 * @param id
	 */
	void forceDeleteCategory(long id);
	
	/**
	 * 获取Categorys 列表,根据sort 排列
	 * @return
	 */
	List<Category> getCategorys();
	
	/**
	 * 获取数量
	 * @return
	 */
	int getTotalCategorys();
	
}
