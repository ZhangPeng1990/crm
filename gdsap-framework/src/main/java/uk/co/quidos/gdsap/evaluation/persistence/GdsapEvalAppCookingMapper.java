package uk.co.quidos.gdsap.evaluation.persistence;

import org.springframework.stereotype.Repository;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalAppCooking;

@Repository
public interface GdsapEvalAppCookingMapper extends BaseMapper<GdsapEvalAppCooking,Long>
{
    
}