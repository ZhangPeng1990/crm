/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.quidos.gdsap.evaluation.enums.SolutionType;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalSolutionSeqMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalSolutionSeq;
import uk.co.quidos.gdsap.evaluation.services.SolutionSeqServiceMgr;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
@Service("solutionSeqServiceMgr")
@Transactional
public class SolutionSeqServiceMgrImpl extends AbsBusinessObjectServiceMgr implements SolutionSeqServiceMgr
{
	@Autowired
	private GdsapEvalSolutionSeqMapper gdsapEvalSolutionSeqMapper;
	
	public GdsapEvalSolutionSeqMapper getGdsapEvalSolutionSeqMapper()
	{
		return gdsapEvalSolutionSeqMapper;
	}

	public void setGdsapEvalSolutionSeqMapper(GdsapEvalSolutionSeqMapper gdsapEvalSolutionSeqMapper)
	{
		this.gdsapEvalSolutionSeqMapper = gdsapEvalSolutionSeqMapper;
	}

	public long nextSeq(long reportId, SolutionType solutionType)
	{
		if (reportId <=0 || solutionType == null)
		{
			throw new IllegalArgumentException();
		}
		GdsapEvalSolutionSeq model = this.getGdsapEvalSolutionSeqMapper().load(reportId, solutionType.getCode());
		if (model == null)
		{
			model = new GdsapEvalSolutionSeq();
			model.setReportId(reportId);
			model.setSolutionType(solutionType.getCode());
			model.setSeqValue(DEFAULT_INIT_SEQ_VALUE);
			this.getGdsapEvalSolutionSeqMapper().insert(model);
			return DEFAULT_INIT_SEQ_VALUE;
		}
		int value = model.getSeqValue() + 1;
		model.setSeqValue(value);
		this.getGdsapEvalSolutionSeqMapper().update(model);
		return value;
	}

	@Override
	public void delete(long reportId)
	{
		this.gdsapEvalSolutionSeqMapper.delete(reportId);
	}
}
