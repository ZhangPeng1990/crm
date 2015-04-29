/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services;

import java.util.List;

import uk.co.quidos.gdsap.evaluation.OtherFuel;
import uk.co.quidos.gdsap.evaluation.Report;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
public interface OtherFuelServiceMgr extends BusinessObjectServiceMgr
{
	/**
	 * 批量添加OtherFuel
	 * @param ofs
	 */
	public void addOtherFuel(OtherFuel of);
	
	/**
	 * 批量更新 OtherFuel
	 * @param report
	 * @param ofs
	 */
	public void updateOtherFuel(OtherFuel of);
	
	/**
	 * @param report
	 * @param count 如果为0 则，显示Report 下的全部OtherFuel 对象
	 * @return
	 */
	public List<OtherFuel> getOtherFuels(Report report,int count);
	
	/**
	 * 通过reportId查找OtherFuel
	 * @param reportId
	 * @return
	 */
	public List<OtherFuel> getOtherFuelsByReportId(Long reportId);
	
	/**
	 * 获取其他燃料
	 * @param id
	 * @return
	 */
	OtherFuel getOtherFuel(long id);
	
	/**
	 * @param id
	 */
	void deleteOtherFuel(long id);
	
	/**
	 * 根据reportId删除roport对应发的全部OtherFuel
	 */
	void delOtherFuelByreport(Long reportId);
}
