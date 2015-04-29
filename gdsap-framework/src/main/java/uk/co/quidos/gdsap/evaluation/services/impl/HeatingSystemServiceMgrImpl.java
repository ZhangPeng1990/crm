/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import uk.co.quidos.gdsap.evaluation.HeatingSystem;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalHeatingSystemMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalHeatingSystem;
import uk.co.quidos.gdsap.evaluation.services.DictServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.HeatingSystemServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.ReportServiceMgr;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
@Transactional
@Service("heatingSystemServiceMgr")
public class HeatingSystemServiceMgrImpl extends AbsBusinessObjectServiceMgr implements HeatingSystemServiceMgr
{
	@Autowired
	private GdsapEvalHeatingSystemMapper gdsapEvalHeatingSystemMapper;
	
	@Autowired
	private DictServiceMgr dictServiceMgr;
	
	@Autowired
	private ReportServiceMgr reportServiceMgr;
	
	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.HeatingSystemServiceMgr#getHeatingSystem(long)
	 */
	@Override
	public HeatingSystem getHeatingSystem(long reportId)
	{
		GdsapEvalHeatingSystem gdsapEvalHeatingSystem = gdsapEvalHeatingSystemMapper.load(reportId);
		HeatingSystem heatingSystem = BeanUtils.gheatingSystemToHeatingSystem(gdsapEvalHeatingSystem);
		if(heatingSystem != null){
			transition(gdsapEvalHeatingSystem,heatingSystem);
		}
		return heatingSystem;
	}

	/**
	 * 属性转换方法
	 */
	public void transition(GdsapEvalHeatingSystem gdsapEvalHeatingSystem,HeatingSystem heatingSystem){
		heatingSystem.setReport(reportServiceMgr.getReport(gdsapEvalHeatingSystem.getReportId()));
		heatingSystem.setmHs(dictServiceMgr.getDictItem(gdsapEvalHeatingSystem.getmHs()));
		heatingSystem.setmHf(dictServiceMgr.getDictItem(gdsapEvalHeatingSystem.getmHf()));
		heatingSystem.setmHt(dictServiceMgr.getDictItem(gdsapEvalHeatingSystem.getmHt()));
		heatingSystem.setSmHs(dictServiceMgr.getDictItem(gdsapEvalHeatingSystem.getSmHs()));
		heatingSystem.setSmHf(dictServiceMgr.getDictItem(gdsapEvalHeatingSystem.getSmHf()));
		heatingSystem.setSmHt(dictServiceMgr.getDictItem(gdsapEvalHeatingSystem.getSmHt()));
		heatingSystem.setsHs(dictServiceMgr.getDictItem(gdsapEvalHeatingSystem.getsHs()));
		heatingSystem.setsHf(dictServiceMgr.getDictItem(gdsapEvalHeatingSystem.getsHf()));
		heatingSystem.setsHt(dictServiceMgr.getDictItem(gdsapEvalHeatingSystem.getsHt()));
	}
	
	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.HeatingSystemServiceMgr#updateHeatingSystem(uk.co.quidos.gdsap.evaluation.HeatingSystem)
	 */
	@Override
	public HeatingSystem updateHeatingSystem(HeatingSystem hs)
	{
		Assert.notNull(hs);
		GdsapEvalHeatingSystem gdsapEvalHeatingSystem = BeanUtils.heatingSystemToGheatingSystem(hs);
		gdsapEvalHeatingSystemMapper.update(gdsapEvalHeatingSystem);
		this.reportServiceMgr.deleteSolutionsWithRecommendations(hs.getReport());
		return hs;
	}

	public HeatingSystem addHeatingSystem(HeatingSystem hs){
		Assert.notNull(hs);
		GdsapEvalHeatingSystem gdsapEvalHeatingSystem = BeanUtils.heatingSystemToGheatingSystem(hs);
		gdsapEvalHeatingSystemMapper.insert(gdsapEvalHeatingSystem);
		this.reportServiceMgr.deleteSolutionsWithRecommendations(hs.getReport());
		return hs;
	}
	
	public void deleteHeatingSystem(long reportId){
		gdsapEvalHeatingSystemMapper.delete(reportId);
	}
	
	public DictServiceMgr getDictServiceMgr()
	{
		return dictServiceMgr;
	}

	public void setDictServiceMgr(DictServiceMgr dictServiceMgr)
	{
		this.dictServiceMgr = dictServiceMgr;
	}

	public GdsapEvalHeatingSystemMapper getGdsapEvalHeatingSystemMapper()
	{
		return gdsapEvalHeatingSystemMapper;
	}

	public void setGdsapEvalHeatingSystemMapper(GdsapEvalHeatingSystemMapper gdsapEvalHeatingSystemMapper)
	{
		this.gdsapEvalHeatingSystemMapper = gdsapEvalHeatingSystemMapper;
	}

	public ReportServiceMgr getReportServiceMgr() {
		return reportServiceMgr;
	}

	public void setReportServiceMgr(ReportServiceMgr reportServiceMgr) {
		this.reportServiceMgr = reportServiceMgr;
	}
}
