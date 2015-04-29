/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import uk.co.quidos.gdsap.evaluation.HeatingPattern;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalHeatingPatternMapper;
import uk.co.quidos.gdsap.evaluation.services.HeatingPatternServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.ReportServiceMgr;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalHeatingPattern;

/**
 * @author peng.shi
 *
 */
@Service("heatingPatternServiceMgr")
@Transactional
public class HeatingPatternServiceMgrImpl extends AbsBusinessObjectServiceMgr implements HeatingPatternServiceMgr
{
	@Autowired
	private GdsapEvalHeatingPatternMapper gdsapEvalHeatingPatternMapper;
	
	@Autowired
	private ReportServiceMgr reportServiceMgr;
	
	public GdsapEvalHeatingPatternMapper getGdsapEvalHeatingPatternMapper()
	{
		return gdsapEvalHeatingPatternMapper;
	}

	public void setGdsapEvalHeatingPatternMapper(GdsapEvalHeatingPatternMapper gdsapEvalHeatingPatternMapper)
	{
		this.gdsapEvalHeatingPatternMapper = gdsapEvalHeatingPatternMapper;
	}
	
	public ReportServiceMgr getReportServiceMgr() {
		return reportServiceMgr;
	}

	public void setReportServiceMgr(ReportServiceMgr reportServiceMgr) {
		this.reportServiceMgr = reportServiceMgr;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.HeatingPatternServiceMgr#addHeatingPattern(uk.co.quidos.gdsap.evaluation.HeatingPattern)
	 */
	@Override
	public HeatingPattern addHeatingPattern(HeatingPattern hp)
	{
		Assert.notNull(hp);
		gdsapEvalHeatingPatternMapper.insert(BeanUtils.heatingParToGdsap(hp));
		this.reportServiceMgr.deleteSolutionsWithRecommendations(hp.getReport());
		return hp;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.HeatingPatternServiceMgr#deleteHeatingPattern(long)
	 */
	@Override
	public void deleteHeatingPattern(long reportId)
	{
		gdsapEvalHeatingPatternMapper.delete(reportId);
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.HeatingPatternServiceMgr#getHeatingPattern(long)
	 */
	@Override
	public HeatingPattern getHeatingPattern(long reportid)
	{
		GdsapEvalHeatingPattern gdsapEvalHeatingPattern = gdsapEvalHeatingPatternMapper.load(reportid);
		if (gdsapEvalHeatingPattern != null)
		{
			HeatingPattern heatingPattern = BeanUtils.GdsapToheatingPar(gdsapEvalHeatingPattern);
			heatingPattern.setReport(reportServiceMgr.getReport(gdsapEvalHeatingPattern.getReportId()));
			return heatingPattern;
		}
			return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.HeatingPatternServiceMgr#updateHeatingPattern(uk.co.quidos.gdsap.evaluation.HeatingPattern)
	 */
	@Override
	public HeatingPattern updateHeatingPattern(HeatingPattern hp)
	{
		GdsapEvalHeatingPattern gdsapEvalHeatingPattern = BeanUtils.heatingParToGdsap(hp);
		if (gdsapEvalHeatingPattern == null)
		{
			throw new IllegalArgumentException();
		}
		gdsapEvalHeatingPatternMapper.update(gdsapEvalHeatingPattern);
		this.reportServiceMgr.deleteSolutionsWithRecommendations(hp.getReport());
		return hp;
	}

}
