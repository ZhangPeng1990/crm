/**
 * 
 */
package uk.co.quidos.gdsap.content.services.impl;

import net.sf.cglib.beans.BeanCopier;
import uk.co.quidos.gdsap.content.Article;
import uk.co.quidos.gdsap.content.Category;
import uk.co.quidos.gdsap.content.enums.CategoryType;
import uk.co.quidos.gdsap.content.persistence.object.GdsapConArticle;
import uk.co.quidos.gdsap.content.persistence.object.GdsapConArticleDetail;
import uk.co.quidos.gdsap.content.persistence.object.GdsapConCategory;
import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.sys.lang.enums.EnumUtils;

/**
 * @author shipeng
 *
 */
public class BeanUtils
{
	private static BeanCopier gdsapConCategoryCopier = BeanCopier.create(GdsapConCategory.class,Category.class, false);
	
	/**
	 * GdsapConCategory 转换类
	 * @param model
	 * @return
	 */
	public static Category do2bo(GdsapConCategory model)
	{
		/**
		 * 	private Long id;
		    private String title;
		    private Integer categoryType;
		    private Integer commentable;
		    private Integer sort;
		    private String desc;
		 */
		Category bo = new Category();
		gdsapConCategoryCopier.copy(model, bo, null);
		bo.setCategoryType((CategoryType)EnumUtils.getByCode(model.getCategoryType(), CategoryType.class));
		bo.setCommentable((YesNo)EnumUtils.getByCode(model.getCommentable(), YesNo.class));
		return bo;
	}
	
	private static BeanCopier categoryCopier = BeanCopier.create(Category.class,GdsapConCategory.class, false);
	
	/**
	 * Category 转换类
	 * @param category
	 * @return
	 */
	public static GdsapConCategory bo2do(Category category)
	{
		/**
		 * 	private Long id;
			private String title;
			private CategoryType categoryType;
			private YesNo commentable;
			private int sort;
			private String desc;
		 */
		GdsapConCategory model = new GdsapConCategory();
		categoryCopier.copy(category, model, null);
		model.setCategoryType(category.getCategoryType().getCode());
		model.setCommentable(category.getCommentable().getCode());
		model.setSort(category.getSort());
		return model;
	}
	
	public static void main(String[] args)
	{
		Category category = new Category();
		category.setCategoryType(CategoryType.MutiItem);
		category.setCommentable(YesNo.Yes);
		
		GdsapConCategory model = new GdsapConCategory();
		category = do2bo(model);
	}
	
	private static BeanCopier articleCopier = BeanCopier.create(Article.class, GdsapConArticle.class, false);
	
	public static GdsapConArticle article2GdsapConArticle(Article article)
	{
		GdsapConArticle model = new GdsapConArticle();
		articleCopier.copy(article, model, null);
		Category category = article.getCategory();
		if (category != null)
		{
			model.setCategoryId(category.getId());
		}
		
		/*
		 *	private Long id;
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
		model.setTopable(article.getTopable().getCode());
		
		return model;
	}
	
	private static BeanCopier gdsapConArticleCopier = BeanCopier.create(GdsapConArticle.class, Article.class, false);
	
	public static Article gdsapArticleAll2Article(Category category,GdsapConArticle gdsapConArticle,GdsapConArticleDetail gdsapConArticleDetail)
	{
		Article article = new Article();
		gdsapConArticleCopier.copy(gdsapConArticle, article, null);
		/*
		 * 	private Long id;
			private Long categoryId;
			private String title;
			private String summary;
			private Date insertTime;
			private Date updateTime;
			private Integer topable;
			private Date topableStartTime;
			private Date topableEndTime;
		 */
		article.setCategory(category);
		article.setTopable((YesNo)EnumUtils.getByCode(gdsapConArticle.getTopable(), YesNo.class));
		article.setContent(gdsapConArticleDetail.getContent());
		return article;
	}
	
	
}
