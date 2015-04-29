/**
 * 
 */
package uk.co.quidos.gdsap.content.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import uk.co.quidos.gdsap.content.Article;
import uk.co.quidos.gdsap.content.Category;
import uk.co.quidos.gdsap.content.enums.CategoryType;
import uk.co.quidos.gdsap.content.exception.SingleObjectException;
import uk.co.quidos.gdsap.content.persistence.GdsapConArticleDetailMapper;
import uk.co.quidos.gdsap.content.persistence.GdsapConArticleMapper;
import uk.co.quidos.gdsap.content.persistence.object.GdsapConArticle;
import uk.co.quidos.gdsap.content.persistence.object.GdsapConArticleDetail;
import uk.co.quidos.gdsap.content.persistence.object.GdsapConArticleFullResultMap;
import uk.co.quidos.gdsap.content.services.ArticleServiceMgr;
import uk.co.quidos.gdsap.content.services.CategoryServiceMgr;
import uk.co.quidos.gdsap.content.services.object.ConditionVO;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
@Service("articleServiceMgr")
@Transactional
public class ArticleServiceMgrImpl extends AbsBusinessObjectServiceMgr implements ArticleServiceMgr
{
	@Autowired
	private CategoryServiceMgr categoryServiceMgr;
	@Autowired
	private GdsapConArticleDetailMapper gdsapConArticleDetailMapper;
	@Autowired
	private GdsapConArticleMapper gdsapConArticleMapper;
	
	/**
	 * @return the categoryServiceMgr
	 */
	public CategoryServiceMgr getCategoryServiceMgr()
	{
		return categoryServiceMgr;
	}

	/**
	 * @return the gdsapConArticleDetailMapper
	 */
	public GdsapConArticleDetailMapper getGdsapConArticleDetailMapper()
	{
		return gdsapConArticleDetailMapper;
	}

	/**
	 * @return the gdsapConArticleMapper
	 */
	public GdsapConArticleMapper getGdsapConArticleMapper()
	{
		return gdsapConArticleMapper;
	}

	/**
	 * @param categoryServiceMgr the categoryServiceMgr to set
	 */
	public void setCategoryServiceMgr(CategoryServiceMgr categoryServiceMgr)
	{
		this.categoryServiceMgr = categoryServiceMgr;
	}

	/**
	 * @param gdsapConArticleDetailMapper the gdsapConArticleDetailMapper to set
	 */
	public void setGdsapConArticleDetailMapper(GdsapConArticleDetailMapper gdsapConArticleDetailMapper)
	{
		this.gdsapConArticleDetailMapper = gdsapConArticleDetailMapper;
	}

	/**
	 * @param gdsapConArticleMapper the gdsapConArticleMapper to set
	 */
	public void setGdsapConArticleMapper(GdsapConArticleMapper gdsapConArticleMapper)
	{
		this.gdsapConArticleMapper = gdsapConArticleMapper;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.content.services.ArticleServiceMgr#addArticle(uk.co.quidos.gdsap.content.Article)
	 */
	@Override
	public Article addArticle(Article article) throws SingleObjectException
	{
		Assert.notNull(article);
		this._validate(article);
		
		//判断Category 是否当个类型
		Category category = article.getCategory();
		if (category.getCategoryType().equals(CategoryType.SingleItem))
		{
			//判断下是否存在文章
			ConditionVO vo = new ConditionVO();
			vo.setCategory(category);
			int total = this.getTotalArticles(vo);
			if (total >0)
			{
				throw new SingleObjectException();
			}
		}
		//添加
		GdsapConArticle model = BeanUtils.article2GdsapConArticle(article);
		model.setInsertTime(new Date());
		model.setUpdateTime(new Date());
		this.getGdsapConArticleMapper().insert(model);
		GdsapConArticleDetail modelDetail = new GdsapConArticleDetail();
		modelDetail.setArticleId(model.getId());
		modelDetail.setContent(article.getContent());
		this.getGdsapConArticleDetailMapper().insert(modelDetail);
		
		article = BeanUtils.gdsapArticleAll2Article(category, model, modelDetail);
		return article;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.content.services.ArticleServiceMgr#deleteArticle(long)
	 */
	@Override
	public void deleteArticle(long id)
	{
		GdsapConArticle model = this.getGdsapConArticleMapper().load(id);
		if (model != null)
		{
			this.getGdsapConArticleDetailMapper().delete(id);
			this.getGdsapConArticleMapper().delete(id);
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.content.services.ArticleServiceMgr#getArticle(long)
	 */
	@Override
	public Article getArticle(long id)
	{
		GdsapConArticle model = this.getGdsapConArticleMapper().load(id);
		if (model != null)
		{
			GdsapConArticleDetail modelDetail = this.getGdsapConArticleDetailMapper().load(id);
			Category category = this.getCategoryServiceMgr().getCategory(model.getCategoryId());
			Article article = BeanUtils.gdsapArticleAll2Article(category, model, modelDetail);
			return article;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.content.services.ArticleServiceMgr#getArticles(uk.co.quidos.gdsap.content.services.object.ConditionVO, boolean, int, int)
	 */
	@Override
	public List<Article> getArticles(ConditionVO vo, boolean initCompletly, int offset, int limit)
	{
		Long categoryId = null;
		String title = null;
		Date startInsertTime = null;
		Date endInsertTime = null;
		if (vo != null)
		{
			if (vo.getCategory() != null)
			{
				categoryId = vo.getCategory().getId();
			}
			title = vo.getTitle();
			startInsertTime = vo.getStartInsertTime();
			endInsertTime = vo.getEndInsertTime();
		}
		
		List<GdsapConArticleFullResultMap> fullResultMaps = this.getGdsapConArticleMapper().findPageBreakByCondition(categoryId, title, startInsertTime, endInsertTime, initCompletly, new RowBounds(offset,limit));

		if (!CollectionUtils.isEmpty(fullResultMaps))
		{
			List<Article> articles = new ArrayList<Article>();
			for (GdsapConArticleFullResultMap resultmap:fullResultMaps)
			{
				Article article = new Article();
				Category c = this.getCategoryServiceMgr().getCategory(resultmap.getCategoryId());
				article.setCategory(c);
				/*
				private Long id;
			    private Category category;
			    private String title;
			    private String summary;
			    private Date insertTime;
			    private Date updateTime;
			    */
				article.setId(resultmap.getId());
				article.setTitle(resultmap.getTitle());
				article.setSummary(resultmap.getSummary());
				article.setInsertTime(resultmap.getInsertTime());
				article.setUpdateTime(resultmap.getUpdateTime());
				article.setContent(resultmap.getContent());
				articles.add(article);
			}
			return articles;
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.content.services.ArticleServiceMgr#getNextArticle(uk.co.quidos.gdsap.content.Article)
	 */
	@Override
	public Article getNextArticle(long id)
	{
		Article article = this.getArticle(id);
		if (article == null)
		{
			return null;
		}
		Category category = article.getCategory();
		Long nextId = this.getGdsapConArticleMapper().findNextByInsertTime(category.getId(), article.getInsertTime());
		if (nextId != null)
		{
			return this.getArticle(nextId);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.content.services.ArticleServiceMgr#getPreArticle(uk.co.quidos.gdsap.content.Article)
	 */
	@Override
	public Article getPreArticle(long id)
	{
		Article article = this.getArticle(id);
		if (article == null)
		{
			return null;
		}
		Category category = article.getCategory();
		Long preId = this.getGdsapConArticleMapper().findPreByInsertTime(category.getId(), article.getInsertTime());
		if (preId != null)
		{
			return this.getArticle(preId);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.content.services.ArticleServiceMgr#getTotalArticles(uk.co.quidos.gdsap.content.services.object.ConditionVO)
	 */
	@Override
	public int getTotalArticles(ConditionVO vo)
	{
		Long categoryId = null;
		String title = null;
		Date startInsertTime = null;
		Date endInsertTime = null;
		if (vo != null)
		{
			if (vo.getCategory() != null)
			{
				categoryId = vo.getCategory().getId();
			}
			title = vo.getTitle();
			startInsertTime = vo.getStartInsertTime();
			endInsertTime = vo.getEndInsertTime();
		}
		return this.getGdsapConArticleMapper().findNumberByCondition(categoryId, title, startInsertTime, endInsertTime);
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.content.services.ArticleServiceMgr#initialArticle(uk.co.quidos.gdsap.content.Article)
	 */
	@Override
	public Article initialArticle(Article article)
	{
		Assert.notNull(article);
		Assert.notNull(article.getId());
		
		GdsapConArticle model = this.getGdsapConArticleMapper().load(article.getId());
		if (model == null)
		{
			throw new IllegalArgumentException();
		}
		Category c = this.getCategoryServiceMgr().getCategory(model.getCategoryId());
		GdsapConArticleDetail modelDetail = this.getGdsapConArticleDetailMapper().load(article.getId());
		return BeanUtils.gdsapArticleAll2Article(c, model, modelDetail);
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.content.services.ArticleServiceMgr#moveArticles(uk.co.quidos.gdsap.content.Category, uk.co.quidos.gdsap.content.Category)
	 */
	@Override
	public void moveArticles(long source, long target)
	{
		Category sourceCategory= this.getCategoryServiceMgr().getCategory(source);
		Category targetCategory = this.getCategoryServiceMgr().getCategory(target);
		if (sourceCategory == null ||  targetCategory == null)
		{
			throw new IllegalArgumentException();
		}
		this.getGdsapConArticleMapper().updateCategoryId(target, source);
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.content.services.ArticleServiceMgr#updateArticle(uk.co.quidos.gdsap.content.Article)
	 */
	@Override
	public Article updateArticle(Article article)
	{
		Assert.notNull(article);
		this._validate(article);
		Assert.notNull(article.getId());
		GdsapConArticle model = this.getGdsapConArticleMapper().load(article.getId());
		GdsapConArticleDetail modelDetail = this.getGdsapConArticleDetailMapper().load(article.getId());
		
		model.setCategoryId(article.getCategory().getId());
		model.setUpdateTime(new Date());
		model.setSummary(article.getSummary());
		model.setTitle(article.getTitle());
		model.setTopable(article.getTopable().getCode());
		model.setTopableEndTime(article.getTopableEndTime());
		model.setTopableStartTime(article.getTopableStartTime());
		
		modelDetail.setContent(article.getContent());
		
		this.getGdsapConArticleMapper().update(model);
		this.getGdsapConArticleDetailMapper().update(modelDetail);
		Category c = article.getCategory();
		return BeanUtils.gdsapArticleAll2Article(c, model, modelDetail);
	}
	
	private void _validate(Article article)
	{
		/*
		 * 	private Long id;
		    private Category category;
		    private String title;
		    private String summary;
		    private Date insertTime;
		    private Date updateTime;
		    private YesNo topable;
		    private Date topableStartTime;
		    private Date topableEndTime;
		    private String content;
		 */
		Assert.notNull(article.getCategory());
		Assert.hasText(article.getTitle());
		Assert.notNull(article.getTopable());
		Assert.hasText(article.getContent());
	}

	@Override
	public Article getArticleByCategory(long id)
	{
		Category c = this.getCategoryServiceMgr().getCategory(id);
		if (c == null || !c.getCategoryType().equals(CategoryType.SingleItem))
		{
			throw new IllegalArgumentException();
		}
		Long articleId = this.getGdsapConArticleMapper().findArticleIdByCategoryId(id);
		if (articleId != null)
		{
			Article article = this.getArticle(articleId);
			return article;
		}
		return null;
	}
	
}
