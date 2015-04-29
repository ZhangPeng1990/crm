/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services;

import uk.co.quidos.gdsap.evaluation.EpcEADetail;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
public interface EpcEADetailServiceMgr extends BusinessObjectServiceMgr
{
	/**
	 * 获取EpcEADDetail 数据
	 * @param id
	 * @return
	 */
	EpcEADetail getEpcEADetail(long id);
	
	EpcEADetail updateEpcEADetail(EpcEADetail epcEADetail);
	
	EpcEADetail addEpcEADetail(EpcEADetail epcEADetail);
}
