package uk.co.quidos.gdsap.content.persistence;

import org.springframework.stereotype.Repository;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.content.persistence.object.GdsapConCategory;

@Repository
public interface GdsapConCategoryMapper extends BaseMapper<GdsapConCategory, Long>
{
	
}