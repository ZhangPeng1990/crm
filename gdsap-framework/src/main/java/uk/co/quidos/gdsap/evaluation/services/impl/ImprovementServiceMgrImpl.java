/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import uk.co.quidos.gdsap.evaluation.Improvement;
import uk.co.quidos.gdsap.evaluation.Report;
import uk.co.quidos.gdsap.evaluation.Solution;
import uk.co.quidos.gdsap.evaluation.enums.Country;
import uk.co.quidos.gdsap.evaluation.enums.GreenDealCategoryCode;
import uk.co.quidos.gdsap.evaluation.enums.ImprovementScope;
import uk.co.quidos.gdsap.evaluation.enums.Language;
import uk.co.quidos.gdsap.evaluation.enums.RecommendationCategoryCode;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalImprovementMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalSolutionImprovementRelMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalImprovement;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalSolutionImprovementRelKey;
import uk.co.quidos.gdsap.evaluation.services.ImprovementServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.LandmarkDefineServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.ReportServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.SolutionServiceMgr;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;
import uk.co.quidos.gdsap.framework.sys.lang.enums.EnumUtils;

/**
 * @author shipeng
 *
 */
@Transactional
@Service("improvementServiceMgr")
public class ImprovementServiceMgrImpl extends AbsBusinessObjectServiceMgr
		implements ImprovementServiceMgr
{
	@Autowired
	private GdsapEvalImprovementMapper gdsapEvalImprovementMapper;
	
	@Autowired
	private ReportServiceMgr reportServiceMgr;
	
	@Autowired
	private SolutionServiceMgr solutionServiceMgr;
	
	@Autowired
	private GdsapEvalSolutionImprovementRelMapper gdsapEvalSolutionImprovementRelMapper;
	
	@Autowired
	private LandmarkDefineServiceMgr landmarkDefineServiceMgr;
	
	public ReportServiceMgr getReportServiceMgr()
	{
		return reportServiceMgr;
	}

	public void setReportServiceMgr(ReportServiceMgr reportServiceMgr)
	{
		this.reportServiceMgr = reportServiceMgr;
	}

	public LandmarkDefineServiceMgr getLandmarkDefineServiceMgr()
	{
		return landmarkDefineServiceMgr;
	}

	public void setLandmarkDefineServiceMgr(LandmarkDefineServiceMgr landmarkDefineServiceMgr)
	{
		this.landmarkDefineServiceMgr = landmarkDefineServiceMgr;
	}

	public GdsapEvalSolutionImprovementRelMapper getGdsapEvalSolutionImprovementRelMapper()
	{
		return gdsapEvalSolutionImprovementRelMapper;
	}

	public void setGdsapEvalSolutionImprovementRelMapper(
			GdsapEvalSolutionImprovementRelMapper gdsapEvalSolutionImprovementRelMapper)
	{
		this.gdsapEvalSolutionImprovementRelMapper = gdsapEvalSolutionImprovementRelMapper;
	}

	public SolutionServiceMgr getSolutionServiceMgr()
	{
		return solutionServiceMgr;
	}

	public void setSolutionServiceMgr(SolutionServiceMgr solutionServiceMgr)
	{
		this.solutionServiceMgr = solutionServiceMgr;
	}

	public GdsapEvalImprovementMapper getGdsapEvalImprovementMapper()
	{
		return gdsapEvalImprovementMapper;
	}

	public void setGdsapEvalImprovementMapper(GdsapEvalImprovementMapper gdsapEvalImprovementMapper)
	{
		this.gdsapEvalImprovementMapper = gdsapEvalImprovementMapper;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.ImprovementServiceMgr#addImprovements(long, java.util.List)
	 */
	@Override
	public void addImprovements(long solutionId, List<Improvement> improvements)
	{
		Solution s = this.getSolutionServiceMgr().getSolution(solutionId);
		if (s == null)
		{
			throw new IllegalArgumentException();
		}
		if (!CollectionUtils.isEmpty(improvements))
		{
			for (Improvement i : improvements)
			{
				GdsapEvalSolutionImprovementRelKey rel = new GdsapEvalSolutionImprovementRelKey();
				rel.setImprovementId(i.getId());
				rel.setSolutionId(solutionId);
				this.getGdsapEvalSolutionImprovementRelMapper().insert(rel);
			}
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.ImprovementServiceMgr#getImprovement(int)
	 */
	@Override
	public Improvement getImprovement(long id)
	{
		GdsapEvalImprovement m = this.getGdsapEvalImprovementMapper().load(id);
		if (m != null)
		{
			Improvement i = this._do2bo(m);
			Report report = this.getReportServiceMgr().getReport(m.getReportId());
			i.setReport(report);
			return i;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.ImprovementServiceMgr#getImprovementsByReportId(long, uk.co.quidos.gdsap.evaluation.enums.ImprovementScope)
	 */
	@Override
	public List<Improvement> getImprovementsByReportId(long reportId,
			ImprovementScope scope)
	{
		Integer iScope = scope != null ? scope.getCode():null;
		List<GdsapEvalImprovement> ms = this.getGdsapEvalImprovementMapper().findByReportIdAndScope(new Long(reportId), iScope);
		if (!CollectionUtils.isEmpty(ms))
		{
			List<Improvement> is = new ArrayList<Improvement>();
			for (GdsapEvalImprovement m : ms)
			{
				Improvement i = this._do2bo(m);
				Report report = this.getReportServiceMgr().getReport(m.getReportId());
				i.setReport(report);
				is.add(i);
			}
			return is;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.ImprovementServiceMgr#getImprovementsBySolutionId(long)
	 */
	@Override
	public List<Improvement> getImprovementsBySolutionId(long solutionId)
	{
		List<GdsapEvalImprovement> ms = this.getGdsapEvalImprovementMapper().findBySolutionId(new Long(solutionId));
		if (!CollectionUtils.isEmpty(ms))
		{
			List<Improvement> is = new ArrayList<Improvement>();
			for (GdsapEvalImprovement m : ms)
			{
				Improvement i = this._do2bo(m);
				Report report = this.getReportServiceMgr().getReport(m.getReportId());
				i.setReport(report);
				is.add(i);
			}
			return is;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.ImprovementServiceMgr#updateImprovements(long, java.util.List)
	 */
	@Override
	public void updateImprovements(long solutionId,
			List<Improvement> improvements)
	{
		//删除GDSAP_EVAL_SOLUTION_IMPROVEMENT_REL 中 solutionId 对应的 improvement
		//从新插入GDSAP_EVAL_SOLUTION_IMPROVEMENT_REL 新数据
		if (this.getSolutionServiceMgr().getSolution(solutionId) == null)
		{
			throw new IllegalArgumentException();
		}
		this.getGdsapEvalSolutionImprovementRelMapper().deleteBySolutionId(solutionId);
		if (!CollectionUtils.isEmpty(improvements))
		{
			for (Improvement i : improvements)
			{
				GdsapEvalSolutionImprovementRelKey rel = new GdsapEvalSolutionImprovementRelKey();
				rel.setImprovementId(i.getId());
				rel.setSolutionId(solutionId);
				this.getGdsapEvalSolutionImprovementRelMapper().insert(rel);
			}
		}
	}

	/**
	 * Report 不进行初始化
	 * @param m
	 * @return
	 */
	private Improvement _do2bo(GdsapEvalImprovement m)
	{
		/*
		 * 	
		 	private Long id;
		    private Integer improvementCategory;
		    private String improvementType;
		    private Float typicalSaving;
		    private Float energyPerformanceRating;
		    private Float environmentalImpactRating;
		    private Integer recommendationCode;
		    private String indicativeCost;
		    private Integer greenDealCategory;
		    private Integer scope;
		    private Long reportId;
		    
		 */
		Improvement i = new Improvement();
		i.setId(m.getId());
		i.setImprovementCategory((RecommendationCategoryCode)EnumUtils.getByCode(m.getImprovementCategory(), RecommendationCategoryCode.class));
		i.setImprovementType(m.getImprovementType());
		i.setTypicalSaving(m.getTypicalSaving());
		i.setEnergyPerformanceRating(m.getEnergyPerformanceRating());
		i.setEnvironmentalImpactRating(m.getEnvironmentalImpactRating());
		i.setRecommendationCode(this.getLandmarkDefineServiceMgr().getRecommentation(m.getRecommendationCode(), Country.EAW, Language.EN));
		i.setIndicativeCost(m.getIndicativeCost());
		i.setGreenDealCategory((GreenDealCategoryCode)EnumUtils.getByCode(m.getGreenDealCategory(), GreenDealCategoryCode.class));
		i.setScope((ImprovementScope)EnumUtils.getByCode(m.getScope(), ImprovementScope.class));
		return i;
	}
}
