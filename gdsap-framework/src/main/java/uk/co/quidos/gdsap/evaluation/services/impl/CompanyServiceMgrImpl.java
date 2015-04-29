package uk.co.quidos.gdsap.evaluation.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import uk.co.quidos.gdsap.evaluation.Company;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalCompanyMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalCompany;
import uk.co.quidos.gdsap.evaluation.services.CompanyServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.object.ConditionVO;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;

@Service("companyServiceMgr")
@Transactional
public class CompanyServiceMgrImpl extends AbsBusinessObjectServiceMgr implements CompanyServiceMgr
{

	@Autowired
	private GdsapEvalCompanyMapper gdsapEvalCompanyMapper;

	@Override
	public Company addCompany(Company company) throws ObjectDuplicateException {
		Assert.notNull(company);
		company.setInsertTime(new Date());
		GdsapEvalCompany model = BeanUtils.toGdsapEvalCompany(company);
		try {
			this.gdsapEvalCompanyMapper.insert(model);
		} catch (DuplicateKeyException e) {
			throw new ObjectDuplicateException();
		}
		return company;
	}

	@Override
	public Company getCompany(String companyId) {
		GdsapEvalCompany com = gdsapEvalCompanyMapper.load(companyId);
		if(com != null)
		{
			return BeanUtils.toCompany(com);
		}
		return null;
	}

	@Override
	public Company updateCompanyInfo(Company company) {
		Assert.notNull(company);
		company.setUpdateTime(new Date());
		GdsapEvalCompany model = BeanUtils.toGdsapEvalCompany(company);
		this.gdsapEvalCompanyMapper.updateByPrimaryKeySelective(model);
		return company;
	}

	@Override
	public List<Company> getCompanysByConditionVO(ConditionVO vo , int offset , int limit) {
		String companyId = null;
		String companyName = null;
		
		if(vo != null && vo.getKeywords() != null && !vo.getKeywords().isEmpty())
		{
			companyId = vo.getKeywords();
			companyName = vo.getKeywords();
		}
		List<GdsapEvalCompany> allCompany = this.gdsapEvalCompanyMapper.findPageBreakByCondition(companyId, companyName, new RowBounds(offset, limit));
		
		
		if (!CollectionUtils.isEmpty(allCompany))
		{
			List<Company> companys = new ArrayList<Company>();
			for(GdsapEvalCompany company : allCompany){
				companys.add(BeanUtils.toCompany(company));
			}
			return companys;
		}
		return null;
	}
	
	public int countAll(ConditionVO vo){
		String companyId = null;
		String companyName = null;
		
		if(vo != null && vo.getKeywords() != null && !vo.getKeywords().isEmpty())
		{
			companyId = vo.getKeywords();
			companyName = vo.getKeywords();
		}
		return this.gdsapEvalCompanyMapper.countAll(companyId,companyName);
		
	}
}
