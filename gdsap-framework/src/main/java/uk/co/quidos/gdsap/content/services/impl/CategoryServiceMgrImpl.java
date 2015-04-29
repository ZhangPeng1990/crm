/**
 * 
 */
package uk.co.quidos.gdsap.content.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import uk.co.quidos.gdsap.content.Category;
import uk.co.quidos.gdsap.content.persistence.GdsapConArticleDetailMapper;
import uk.co.quidos.gdsap.content.persistence.GdsapConArticleMapper;
import uk.co.quidos.gdsap.content.persistence.GdsapConCategoryMapper;
import uk.co.quidos.gdsap.content.persistence.object.GdsapConCategory;
import uk.co.quidos.gdsap.content.services.ArticleServiceMgr;
import uk.co.quidos.gdsap.content.services.CategoryServiceMgr;
import uk.co.quidos.gdsap.content.services.object.ConditionVO;
import uk.co.quidos.gdsap.framework.exception.ObjectHasSubObjectException;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
@Service("categoryServiceMgr")
@Transactional
public class CategoryServiceMgrImpl extends AbsBusinessObjectServiceMgr implements CategoryServiceMgr
{
	@Autowired
	private GdsapConCategoryMapper gdsapConCategoryMapper;
	
	@Autowired
	private ArticleServiceMgr articleServiceMgr;
	
	@Autowired
	private GdsapConArticleMapper gdsapConArticleMapper;
	
	@Autowired
	private GdsapConArticleDetailMapper gdsapConArticleDetailMapper;
	
	public GdsapConArticleMapper getGdsapConArticleMapper()
	{
		return gdsapConArticleMapper;
	}

	public void setGdsapConArticleMapper(GdsapConArticleMapper gdsapConArticleMapper)
	{
		this.gdsapConArticleMapper = gdsapConArticleMapper;
	}

	public GdsapConArticleDetailMapper getGdsapConArticleDetailMapper()
	{
		return gdsapConArticleDetailMapper;
	}

	public void setGdsapConArticleDetailMapper(GdsapConArticleDetailMapper gdsapConArticleDetailMapper)
	{
		this.gdsapConArticleDetailMapper = gdsapConArticleDetailMapper;
	}

	public ArticleServiceMgr getArticleServiceMgr()
	{
		return articleServiceMgr;
	}

	public void setArticleServiceMgr(ArticleServiceMgr articleServiceMgr)
	{
		this.articleServiceMgr = articleServiceMgr;
	}

	/**
	 * @return the gdsapConCategoryMapper
	 */
	public GdsapConCategoryMapper getGdsapConCategoryMapper()
	{
		return gdsapConCategoryMapper;
	}

	/**
	 * @param gdsapConCategoryMapper the gdsapConCategoryMapper to set
	 */
	public void setGdsapConCategoryMapper(GdsapConCategoryMapper gdsapConCategoryMapper)
	{
		this.gdsapConCategoryMapper = gdsapConCategoryMapper;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.content.services.CategoryServiceMgr#addCategory(uk.co.quidos.gdsap.content.Category)
	 */
	@Override
	public Category addCategory(Category category)
	{
		Assert.notNull(category);
		this._validateCategory(category);
		GdsapConCategory model = BeanUtils.bo2do(category);
		model.setId(null);
		this.getGdsapConCategoryMapper().insert(model);
		category = BeanUtils.do2bo(model);
		this.setInCache(category);
		System.out.println("insert id : " + category.getId());
		return category;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.content.services.CategoryServiceMgr#deleteCategory(long)
	 */
	@Override
	public void deleteCategory(long id) throws ObjectHasSubObjectException
	{
		Category category = this.getCategory(id);
		if (category != null)
		{
			ConditionVO vo = new ConditionVO();
			vo.setCategory(category);
			int count = this.getArticleServiceMgr().getTotalArticles(vo);
			if (count >0)
			{
				throw new ObjectHasSubObjectException();
			}
			this.getGdsapConCategoryMapper().delete(id);
			this.deleteFromCache(category);
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.content.services.CategoryServiceMgr#forceDeleteCategory(long)
	 */
	@Override
	public void forceDeleteCategory(long id)
	{
		GdsapConCategory model = this.getGdsapConCategoryMapper().load(id);
		if (model != null)
		{
			//删除 Article Detail
			this.getGdsapConArticleDetailMapper().deleteByCategoryId(id);
			//删除 Article
			this.getGdsapConArticleMapper().deleteByCategoryId(id);
			//删除Category
			this.getGdsapConCategoryMapper().delete(id);
			this.deleteFromCache(BeanUtils.do2bo(model));
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.content.services.CategoryServiceMgr#getCategory(long)
	 */
	@Override
	public Category getCategory(long id)
	{
		Category category = this.getFromCache(Category.class, id);
		if (category == null)
		{
			GdsapConCategory model = this.getGdsapConCategoryMapper().load(id);
			if (model != null)
			{
				category = BeanUtils.do2bo(model);
				this.setInCache(category);
				return category;
			}
			return null;
		}
		return category;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.content.services.CategoryServiceMgr#getCategorys()
	 */
	@Override
	public List<Category> getCategorys()
	{
		List<Long> ids = this.getGdsapConCategoryMapper().findAllIds();
		if (!CollectionUtils.isEmpty(ids))
		{
			List<Category> bos = new ArrayList<Category>();
			for (Long id : ids)
			{
				bos.add(this.getCategory(id));
			}
			return bos;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.content.services.CategoryServiceMgr#getTotalCategorys()
	 */
	@Override
	public int getTotalCategorys()
	{
		return this.getGdsapConCategoryMapper().countAll();
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.content.services.CategoryServiceMgr#updateCategory(uk.co.quidos.gdsap.content.Category)
	 */
	@Override
	public Category updateCategory(Category category)
	{
		Assert.notNull(category);
		_validateCategory(category);
		Assert.notNull(category.getId());
		GdsapConCategory model = this.getGdsapConCategoryMapper().load(category.getId());
		model.setCategoryType(category.getCategoryType().getCode());
		model.setCommentable(category.getCommentable().getCode());
		model.setDes(category.getDes());
		model.setSort(category.getSort());
		model.setTitle(category.getTitle());
		this.getGdsapConCategoryMapper().update(model);
		category = BeanUtils.do2bo(model);
		this.setInCache(category);
		return category;
	}

	/**
	 * 验证框架
	 * @param category
	 */
	private void _validateCategory(Category category)
	{
		Assert.hasText(category.getTitle());
		Assert.notNull(category.getCategoryType());
		Assert.notNull(category.getCommentable());	
	}
	
}
