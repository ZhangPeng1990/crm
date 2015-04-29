package uk.co.quidos.gdsap.evaluation.persistence;

import org.springframework.stereotype.Repository;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapDictCond;

@Repository
public interface GdsapDictCondMapper extends BaseMapper<GdsapDictCond,Integer>{
	
	void deleteAllByDictCond(String condType);
}
