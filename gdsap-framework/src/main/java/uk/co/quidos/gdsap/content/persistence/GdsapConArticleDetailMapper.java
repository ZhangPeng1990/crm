package uk.co.quidos.gdsap.content.persistence;

import org.springframework.stereotype.Repository;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.content.persistence.object.GdsapConArticleDetail;

@Repository
public interface GdsapConArticleDetailMapper extends BaseMapper<GdsapConArticleDetail,Long>
{
	void deleteByCategoryId(Long categoryId);
	
}