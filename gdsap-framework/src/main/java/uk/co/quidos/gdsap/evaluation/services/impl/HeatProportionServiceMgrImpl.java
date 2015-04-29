/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import uk.co.quidos.gdsap.evaluation.HeatProportion;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalHeatProportionMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalHeatProportion;
import uk.co.quidos.gdsap.evaluation.services.HeatProportionServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.ReportServiceMgr;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
@Transactional
@Service("heatProportionServiceMgr")
public class HeatProportionServiceMgrImpl extends AbsBusinessObjectServiceMgr implements HeatProportionServiceMgr
{
	@Autowired
	private GdsapEvalHeatProportionMapper gdsapEvalHeatProportionMapper;
	
	@Autowired
	private ReportServiceMgr reportServiceMgr;
	
	public GdsapEvalHeatProportionMapper getGdsapEvalHeatProportionMapper()
	{
		return gdsapEvalHeatProportionMapper;
	}

	public void setGdsapEvalHeatProportionMapper(GdsapEvalHeatProportionMapper gdsapEvalHeatProportionMapper)
	{
		this.gdsapEvalHeatProportionMapper = gdsapEvalHeatProportionMapper;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.HeatProportionServiceMgr#getHeatProportions(long)
	 */
	@Override
	public List<HeatProportion> getHeatProportions(long reportId)
	{
		List<GdsapEvalHeatProportion> listG = gdsapEvalHeatProportionMapper.findGdsapEvalHeatProportions(reportId);
		List<HeatProportion> list = new ArrayList<HeatProportion>();
		if(listG.size()>0){
			for(GdsapEvalHeatProportion hp : listG){
				HeatProportion htp = BeanUtils.gdsapToHeatProportion(hp);
				htp.setReportId(reportServiceMgr.getReport(hp.getReportId()));
				list.add(htp);
			}
		}
		return list;
	}
	
	public void delHeatProportionByReport(long reportId){
		gdsapEvalHeatProportionMapper.deleteByReportId(reportId);
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.HeatProportionServiceMgr#updateHeatProportion(uk.co.quidos.gdsap.evaluation.HeatProportion)
	 */
	@Override
	public void updateHeatProportion(HeatProportion heatProportion)
	{
		Assert.notNull(heatProportion);
		GdsapEvalHeatProportion gdsapEvalHeatProportion = BeanUtils.heatProportionToGdsap(heatProportion);
		gdsapEvalHeatProportionMapper.update(gdsapEvalHeatProportion);
		this.reportServiceMgr.deleteSolutionsWithRecommendations(heatProportion.getReportId());
	}

}
