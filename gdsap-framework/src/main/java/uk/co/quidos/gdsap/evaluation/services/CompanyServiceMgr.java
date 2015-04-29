package uk.co.quidos.gdsap.evaluation.services;

import java.util.List;

import uk.co.quidos.gdsap.evaluation.Company;
import uk.co.quidos.gdsap.evaluation.services.object.ConditionVO;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

public interface CompanyServiceMgr extends BusinessObjectServiceMgr
{
	public static final String SERVICE_NAME = "companyServiceMgr";
	
	Company addCompany(Company company) throws ObjectDuplicateException;
	
	Company getCompany(String companyId);
	
	/**
	 * 更改公司基本信息，名称地址等
	 * @param company
	 * @return
	 */
	Company updateCompanyInfo(Company company);
	
	/**
	 * 获取公司list， 
	 * 可查询 companyId和 companyName;
	 * @param vo
	 * @return
	 */
	List<Company> getCompanysByConditionVO(ConditionVO vo,int offset,int limit);
	
	int countAll(ConditionVO vo);
}
