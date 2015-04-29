package uk.co.quidos.gdsap.content.persistence;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.content.persistence.object.GdsapConArticle;
import uk.co.quidos.gdsap.content.persistence.object.GdsapConArticleFullResultMap;

@Repository
public interface GdsapConArticleMapper extends BaseMapper<GdsapConArticle, Long>
{
	public void deleteByCategoryId(Long categoryId);

	List<GdsapConArticleFullResultMap> findPageBreakByCondition(@Param("categoryId") Long categoryId, @Param("title") String title,
			@Param("startInsertTime") Date startInsertTime, @Param("endInsertTime") Date endInsertTime, @Param("initial")boolean initial,RowBounds rb);

	int findNumberByCondition(@Param("categoryId") Long categoryId, @Param("title") String title,
			@Param("startInsertTime") Date startInsertTime, @Param("endInsertTime") Date endInsertTime);

	void updateCategoryId(@Param("newCategoryId")long newCategoryId,@Param("oldCategoryId")long oldCategoryId);
	
	Long findNextByInsertTime(@Param("categoryId")Long categoryId,@Param("insertTime")Date insertTime);
	
	Long findPreByInsertTime(@Param("categoryId")Long categoryId,@Param("insertTime")Date insertTime);
	
	Long findArticleIdByCategoryId(@Param("categoryId") Long categoryId);
	
}