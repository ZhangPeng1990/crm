/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import uk.co.quidos.gdsap.evaluation.Occupants;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalOccupantsMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalOccupants;
import uk.co.quidos.gdsap.evaluation.services.DictServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.OccupantsServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.ReportServiceMgr;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
@Service("occupantsServiceMgr")
public class OccupantsServiceMgrImpl extends AbsBusinessObjectServiceMgr implements OccupantsServiceMgr
{
	@Autowired
	private GdsapEvalOccupantsMapper gdsapEvalOccupantsMapper;
	
	@Autowired
	private ReportServiceMgr reportServiceMgr;
	
	@Autowired
	private DictServiceMgr dictServiceMgr;
	
	public ReportServiceMgr getReportServiceMgr()
	{
		return reportServiceMgr;
	}

	public void setReportServiceMgr(ReportServiceMgr reportServiceMgr)
	{
		this.reportServiceMgr = reportServiceMgr;
	}

	public GdsapEvalOccupantsMapper getGdsapEvalOccupantsMapper()
	{
		return gdsapEvalOccupantsMapper;
	}

	public void setGdsapEvalOccupantsMapper(GdsapEvalOccupantsMapper gdsapEvalOccupantsMapper)
	{
		this.gdsapEvalOccupantsMapper = gdsapEvalOccupantsMapper;
	}

	public DictServiceMgr getDictServiceMgr() {
		return dictServiceMgr;
	}

	public void setDictServiceMgr(DictServiceMgr dictServiceMgr) {
		this.dictServiceMgr = dictServiceMgr;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.OccupantsServiceMgr#addOccupants(uk.co.quidos.gdsap.evaluation.Occupants)
	 */
	@Override
	public Occupants addOccupants(Occupants occupants)
	{
		Assert.notNull(occupants);
		gdsapEvalOccupantsMapper.insert(BeanUtils.gdsapToOccupants(occupants));
		this.reportServiceMgr.deleteSolutionsWithRecommendations(occupants.getReport());
		return occupants;
	}
	
	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.OccupantsServiceMgr#getOccupants(long)
	 */
	@Override
	public Occupants getOccupants(long reportId)
	{
		GdsapEvalOccupants gdsapEvalOccupants = gdsapEvalOccupantsMapper.load(reportId);
		Occupants occupants = BeanUtils.occupantsToGdsap(gdsapEvalOccupants);
		if(occupants != null){
			occupants.setReport(reportServiceMgr.getReport(gdsapEvalOccupants.getReportId()));
			occupants.setShowerType(dictServiceMgr.getDictItem(gdsapEvalOccupants.getShowerType()));
			return occupants;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.OccupantsServiceMgr#updateOccupants(uk.co.quidos.gdsap.evaluation.Occupants)
	 */
	@Override
	public Occupants updateOccupants(Occupants occupants)
	{
		Assert.notNull(occupants);
		gdsapEvalOccupantsMapper.update(BeanUtils.gdsapToOccupants(occupants));
		this.reportServiceMgr.deleteSolutionsWithRecommendations(occupants.getReport());
		return occupants;
	}
	
	public void deleteOccupants(long reportId){
		gdsapEvalOccupantsMapper.delete(reportId);
	}
	
}
