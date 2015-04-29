package uk.co.quidos.gdsap.evaluation.services;

import java.util.List;

import uk.co.quidos.gdsap.evaluation.HeatProportion;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
public interface HeatProportionServiceMgr extends BusinessObjectServiceMgr
{
	public static final String SERVICE_NAME = "heatProportionServiceMgr";
	
	/**
	 * 根据ReportId 获取 列表
	 * @param reportId
	 * @return
	 */
	List<HeatProportion> getHeatProportions(long reportId);
	
	/**
	 * 根据reportId 删除对应的全部HeatProportion
	 * @param reportId
	 */
	void delHeatProportionByReport(long reportId);
	
	/**
	 * 更新 HeatProportion。RoomType 修改无用,是由Report进行初始化时定义好的.
	 * @param heatProportion
	 */
	void updateHeatProportion(HeatProportion heatProportion);
	
}
