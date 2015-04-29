/**
 * 
 */
package uk.co.quidos.gdsap.content.services;

import java.util.List;

import uk.co.quidos.gdsap.content.Article;
import uk.co.quidos.gdsap.content.exception.SingleObjectException;
import uk.co.quidos.gdsap.content.services.object.ConditionVO;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * @author peng.shi
 */
public interface ArticleServiceMgr extends BusinessObjectServiceMgr
{
	public static final String SERVICE_NAME = "articleServiceMgr";
	
	/**
	 * 通过id 获取Article对象,获取详细信息，包含 Content 内容部分 
	 * @param id
	 * @return
	 */
	Article getArticle(long id);
	
	/**
	 * @param category
	 * @return
	 */
	Article getArticleByCategory(long id);
	
	/**
	 * 完全初始化Article
	 * @param article
	 * @return
	 */
	Article initialArticle(Article article);
	
	/**
	 * 添加Article 对象
	 * @param article
	 * @return
	 * @throws SingleObjectException
	 */
	Article addArticle(Article article) throws SingleObjectException;
	
	/**
	 * 更新Article 对象
	 * @param article
	 * @return
	 */
	Article updateArticle(Article article);
	
	/**
	 * 删除Article 对象
	 * @param id
	 */
	void deleteArticle(long id);
	
	/**
	 * 通过Category 获取分页Article 数据
	 * @param category
	 * @param initCompletely 是否完全初始化Article
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Article> getArticles(ConditionVO vo,boolean initCompletely,int offset,int limit);
	
	/**
	 * 获取数量
	 * @param vo
	 * @return
	 */
	int getTotalArticles(ConditionVO vo);
	
	/**
	 * 获取当前文章所在栏目下前一条数据，根据InsertTime 排序算出
	 * @param article
	 * @return
	 */
	Article getPreArticle(long articleId);
	
	/**
	 * 获取当前文章所在栏目下下一条数据，根据InsertTime 排序算出
	 * @param article
	 * @return
	 */
	Article getNextArticle(long articleId);
	
	/**
	 * 转移Source 下的Article 到  Target 下
	 * @param source
	 * @param target
	 */
	void moveArticles(long source,long target);
	
}
