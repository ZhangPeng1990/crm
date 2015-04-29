package uk.co.quidos.gdsap.evaluation.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import uk.co.quidos.dal.BaseMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalCompany;

public interface GdsapEvalCompanyMapper extends BaseMapper<GdsapEvalCompany, String> {
	List<GdsapEvalCompany> findPageBreakByCondition(@Param("companyId")String companyId,
			@Param("companyName")String companyName, RowBounds rb);
    
	int countAll(@Param("companyId")String companyId,
			@Param("companyName")String name);
	
	void updateByPrimaryKeySelective(GdsapEvalCompany company);
}