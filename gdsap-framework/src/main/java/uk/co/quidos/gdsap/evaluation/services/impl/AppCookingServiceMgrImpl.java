/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import uk.co.quidos.gdsap.evaluation.AppCooking;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalAppCookingMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalAppCooking;
import uk.co.quidos.gdsap.evaluation.services.AppCookingServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.DictServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.ReportServiceMgr;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
@Transactional
@Service("appCookingServiceMgr")
public class AppCookingServiceMgrImpl extends AbsBusinessObjectServiceMgr implements AppCookingServiceMgr
{
	@Autowired
	private GdsapEvalAppCookingMapper gdsapEvalAppCookingMapper;
	
	@Autowired
	private ReportServiceMgr reportServiceMgr;
	
	@Autowired
	private DictServiceMgr dictServiceMgr;
	
	
	public GdsapEvalAppCookingMapper getGdsapEvalAppCookingMapper()
	{
		return gdsapEvalAppCookingMapper;
	}

	public void setGdsapEvalAppCookingMapper(GdsapEvalAppCookingMapper gdsapEvalAppCookingMapper)
	{
		this.gdsapEvalAppCookingMapper = gdsapEvalAppCookingMapper;
	}
	
	public ReportServiceMgr getReportServiceMgr() {
		return reportServiceMgr;
	}

	public void setReportServiceMgr(ReportServiceMgr reportServiceMgr) {
		this.reportServiceMgr = reportServiceMgr;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.AppCookingServiceMgr#addAppCooking(uk.co.quidos.gdsap.evaluation.AppCooking)
	 */
	@Override
	public AppCooking addAppCooking(AppCooking ac)
	{
		Assert.notNull(ac);
		GdsapEvalAppCooking gdsapEvalAppCooking = BeanUtils.gAppCookingToAppCooking(ac);
		gdsapEvalAppCookingMapper.insert(gdsapEvalAppCooking);
		this.reportServiceMgr.deleteSolutionsWithRecommendations(ac.getReport());
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.AppCookingServiceMgr#getAppCooking(long)
	 */
	@Override
	public AppCooking getAppCooking(long reportId)
	{
		GdsapEvalAppCooking gdsapEvalAppCooking = gdsapEvalAppCookingMapper.load(reportId);
		if(gdsapEvalAppCooking != null){
			AppCooking appCooking = BeanUtils.appCookingToGAppCooking(gdsapEvalAppCooking);
			appCooking.setReport(reportServiceMgr.getReport(gdsapEvalAppCooking.getReportId()));
			appCooking.setCookerType(dictServiceMgr.getDictItem(gdsapEvalAppCooking.getCookerType()));
			appCooking.setCookingFuel(dictServiceMgr.getDictItem(gdsapEvalAppCooking.getCookingFuel()));
			return appCooking;
		}
			return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.AppCookingServiceMgr#updateAppCooking(uk.co.quidos.gdsap.evaluation.AppCooking)
	 */
	@Override
	public AppCooking updateAppCooking(AppCooking ac)
	{
		Assert.notNull(ac);
		GdsapEvalAppCooking gdsapEvalAppCooking = gdsapEvalAppCookingMapper.load(ac.getId());
		gdsapEvalAppCooking = BeanUtils.gAppCookingToAppCooking(ac);
		gdsapEvalAppCookingMapper.update(gdsapEvalAppCooking);
		this.reportServiceMgr.deleteSolutionsWithRecommendations(ac.getReport());
		return ac;
	}

	public void deleteAppCooking(long reportId){
		gdsapEvalAppCookingMapper.delete(reportId);
	}

	public DictServiceMgr getDictServiceMgr() {
		return dictServiceMgr;
	}

	public void setDictServiceMgr(DictServiceMgr dictServiceMgr) {
		this.dictServiceMgr = dictServiceMgr;
	}
	
}
