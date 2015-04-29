/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import uk.co.quidos.gdsap.evaluation.EpcEADetail;
import uk.co.quidos.gdsap.evaluation.Report;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalEpcEaDetailMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalEpcEaDetail;
import uk.co.quidos.gdsap.evaluation.services.EpcEADetailServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.ReportServiceMgr;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
@Service("epcEADetailServiceMgr")
@Transactional
public class EpcEADetailServiceMgrImpl extends AbsBusinessObjectServiceMgr implements EpcEADetailServiceMgr
{
	@Autowired
	private GdsapEvalEpcEaDetailMapper gdsapEvalEpcEaDetailMapper;
	@Autowired
	private ReportServiceMgr reportServiceMgr;
	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.EpcEADetailServiceMgr#getEpcEADetail(long)
	 */
	@Override
	public EpcEADetail getEpcEADetail(long id)
	{
		GdsapEvalEpcEaDetail model = this.gdsapEvalEpcEaDetailMapper.load(id);
		Report report = this.reportServiceMgr.getReport(id);
		Assert.notNull(report);
		if (model != null)
		{
			EpcEADetail vo = BeanUtils.gdsapEvalEpcEaDetail2EpcEADetail(model);
			vo.setReport(report);
			return vo;
		}
		return null;
	}
	
	@Override
	public EpcEADetail updateEpcEADetail(EpcEADetail epcEADetail) {
		Assert.notNull(epcEADetail);
		GdsapEvalEpcEaDetail model = BeanUtils.epcEADetail2GdsapEvalEpcEaDetail(epcEADetail);
		this.gdsapEvalEpcEaDetailMapper.update(model);
		return epcEADetail;
	}

	@Override
	public EpcEADetail addEpcEADetail(EpcEADetail epcEADetail) {
		Assert.notNull(epcEADetail);
		GdsapEvalEpcEaDetail model = BeanUtils.epcEADetail2GdsapEvalEpcEaDetail(epcEADetail);
		this.gdsapEvalEpcEaDetailMapper.insert(model);
		return epcEADetail;
	}

}
