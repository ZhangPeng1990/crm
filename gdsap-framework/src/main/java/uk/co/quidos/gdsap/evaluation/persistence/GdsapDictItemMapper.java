package uk.co.quidos.gdsap.evaluation.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapDictItem;

@Repository
public interface GdsapDictItemMapper extends BaseMapper<GdsapDictItem,Integer>{
	List<GdsapDictItem> findDictItemByDictType(String dictType);
	List<GdsapDictItem> findDictItemByCondType(String condType);

	GdsapDictItem findByDictTypeCalCode(@Param("dictType") String dictType, @Param("calCode") Double calCode);
	GdsapDictItem findByDictTypeLodgeCode(@Param("dictType") String dictType, @Param("lodgeCode") String lodgeCode);
}
