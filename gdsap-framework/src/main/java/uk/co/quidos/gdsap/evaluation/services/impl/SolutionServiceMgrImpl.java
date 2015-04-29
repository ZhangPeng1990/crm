/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import uk.co.quidos.common.util.io.FileStoreUtils;
import uk.co.quidos.gdsap.evaluation.Report;
import uk.co.quidos.gdsap.evaluation.Solution;
import uk.co.quidos.gdsap.evaluation.StandardRecommendationCalResult;
import uk.co.quidos.gdsap.evaluation.StandardRecommendationWrap;
import uk.co.quidos.gdsap.evaluation.enums.ReportLocation;
import uk.co.quidos.gdsap.evaluation.enums.SolutionType;
import uk.co.quidos.gdsap.evaluation.object.SolutionResult;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalEpcImprovementResultMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalPerFuelCalResultMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalRecommendationMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalSolutionCalResultMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalSolutionFuelCalResultMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalSolutionImprovementRelMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalSolutionIssueMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalSolutionMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalSolutionRecommendationRelMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalSolutionSeqMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalSolution;
import uk.co.quidos.gdsap.evaluation.services.CalResultServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.CalServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.ReportCommonServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.ReportServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.SolutionSeqServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.SolutionServiceMgr;
import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.exception.CalculateException;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;
import uk.co.quidos.gdsap.framework.sysconf.GlobalConfig;
import uk.co.quidos.gdsap.framework.sysconf.GlobalUtils;

/**
 * @author shipeng
 *
 */
@Transactional
@Service("solutionServiceMgr")
public class SolutionServiceMgrImpl extends AbsBusinessObjectServiceMgr implements SolutionServiceMgr
{

	@Autowired
	private GdsapEvalSolutionMapper gdsapEvalSolutionMapper;
	
	@Autowired
	private GdsapEvalSolutionSeqMapper gdsapEvalSolutionSeqMapper;
	
	@Autowired
	private GdsapEvalSolutionRecommendationRelMapper gdsapEvalSolutionRecommendationRelMapper;
	
	@Autowired
	private GdsapEvalSolutionImprovementRelMapper gdsapEvalSolutionImprovementRelMapper;
	
	@Autowired
	private GdsapEvalSolutionFuelCalResultMapper gdsapEvalSolutionFuelCalResultMapper;
	
	@Autowired
	private GdsapEvalSolutionCalResultMapper gdsapEvalSolutionCalResultMapper;
	
	@Autowired
	private GdsapEvalRecommendationMapper gdsapEvalRecommendationMapper;
	
	@Autowired
	private SolutionSeqServiceMgr solutionSeqServiceMgr;
	
	@Autowired
	private CalServiceMgr calServiceMgr;
	
	@Autowired
	private ReportServiceMgr reportServiceMgr;
	
	@Autowired
	private CalResultServiceMgr calResultServiceMgr;
	
	@Autowired
	private ReportCommonServiceMgr reportCommonServiceMgr;
	
	@Autowired
	private GdsapEvalEpcImprovementResultMapper gdsapEvalEpcImprovementResultMapper;
	
	@Autowired
	private GdsapEvalSolutionIssueMapper gdsapEvalSolutionIssueMapper;
	
	@Autowired
	private GdsapEvalPerFuelCalResultMapper gdsapEvalPerFuelCalResultMapper;
	
	/**
	 * 添加流程: 调用计算接口,并返回响应的结果集合,插入到相应的表中 
	 * 1. 添加到 Solution 表中 
	 * 2. 将每个关联关系添加到GDSAP_EVAL_SOLUTION_RECOMMENDATION_REL,查看StandardRecommendation中选定的Option，并添加的KeyValue中,并插入响应的结果 
	 * 3. 插入其他燃料结果集合 GDSAP_EVAL_SOLUTION_FUEL_CAL_RESULT 
	 * 4. 插入到结果集合表中
	 * @throws Exception 
	 */
	public Solution addSolution(Solution solution,List<StandardRecommendationWrap> srsw) throws CalculateException, Exception 
	{
		Assert.notNull(solution);
		Assert.notEmpty(srsw);
		
		Report report = solution.getReport();
		//insert solution
		
		GdsapEvalSolution solutionModel = BeanUtils.SolutionToGSolution(solution);
		solutionModel.setInsertTime(new Date());
		solutionModel.setUpdateTime(new Date());
		solutionModel.setSelected(YesNo.No.getCode());
		gdsapEvalSolutionMapper.insert(solutionModel);
		
		//计算结果处理
		try {
			calServiceMgr.calSolution(solutionModel.getId(), srsw);
		}
		catch (CalculateException ce)
		{
			this.deleteSolution(solutionModel.getId());
			throw ce;
		}
		catch (Exception e2) {
			this.deleteSolution(solutionModel.getId());
			throw e2;
		}
		solution = BeanUtils.do2bo(solutionModel);
		solution.setReport(report);
		
		//放入到xml文件到磁盘上
		String lodgeXmlContent = null;
		try {
			lodgeXmlContent = this.reportCommonServiceMgr.solutionCalResultXml(solution, srsw);
		} catch (Exception e1) {
			this.deleteSolution(solution.getId());
			e1.printStackTrace();
			throw new CalculateException(e1.getMessage());
		}
		String filename = UUID.randomUUID().toString();
		String datedir = GlobalUtils.dateDir(new Date());

		String parentDir = GlobalConfig.getInstance().getFSDir();
		if (!new File(parentDir + datedir).exists())
		{
			new File(parentDir + datedir).mkdirs();
		}
		File lodgeFile = new File(parentDir + datedir + filename + ".xml");
		try
		{
			lodgeFile.createNewFile();
			if (report.getReportLocation().equals(ReportLocation.EAW) || report.getReportLocation().equals(ReportLocation.NIR)) {
				FileUtils.writeStringToFile(lodgeFile, lodgeXmlContent, "utf-8");
				reportCommonServiceMgr.prettyXmlFormat(lodgeFile,solution.getReport().getReportLocation());
			}else{
				FileUtils.writeStringToFile(lodgeFile, lodgeXmlContent, "utf-8");
				reportCommonServiceMgr.prettyXmlFormat(lodgeFile,solution.getReport().getReportLocation());
			}
			
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		solution.setSolutionLodgeXmlPath(datedir + filename + ".xml");
		solutionModel = BeanUtils.bo2do(solution);
		this.gdsapEvalSolutionMapper.update(solutionModel);
		return solution;
	}

	public Solution reCreateXMLBySolution(Solution solution)
	{
		String parentDir = GlobalConfig.getInstance().getFSDir();
		FileStoreUtils.deleteFile(parentDir + solution.getSolutionPdfPath());
		FileStoreUtils.deleteFile(parentDir + solution.getSolutionLodgeXmlPath());
		
		//放入到xml文件到磁盘上
		String lodgeXmlContent = null;
		try {
			lodgeXmlContent = this.reportCommonServiceMgr.solutionCalResultXml(solution, null);
		} catch (CalculateException e1) {
			e1.printStackTrace();
		}
		String filename = UUID.randomUUID().toString();
		String datedir = GlobalUtils.dateDir(new Date());

		if (!new File(parentDir + datedir).exists())
		{
			new File(parentDir + datedir).mkdirs();
		}
		File lodgeFile = new File(parentDir + datedir + filename + ".xml");
		Report report = solution.getReport();
		try
		{
			lodgeFile.createNewFile();
			if (report.getReportLocation().equals(ReportLocation.EAW) || report.getReportLocation().equals(ReportLocation.NIR)) {
				FileUtils.writeStringToFile(lodgeFile, lodgeXmlContent, "utf-8");
				reportCommonServiceMgr.prettyXmlFormat(lodgeFile,solution.getReport().getReportLocation());
			}else{
				FileUtils.writeStringToFile(lodgeFile, lodgeXmlContent, "utf-8");
				reportCommonServiceMgr.prettyXmlFormat(lodgeFile,solution.getReport().getReportLocation());
			}
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		solution.setSolutionLodgeXmlPath(datedir + filename + ".xml");
		GdsapEvalSolution solutionModel = BeanUtils.bo2do(solution);
		this.gdsapEvalSolutionMapper.update(solutionModel);
		return solution;
	}
	
	@Override
	public String autoBuildSolutionTitle(long reportId, SolutionType solutionType)
	{
		long nextSeq = solutionSeqServiceMgr.nextSeq(reportId, solutionType);
		if (nextSeq >= 1 && nextSeq <= 9)
		{
			return SOLUTION_TITLE_PREFIX + " " + "0" + nextSeq;
		}

		return SOLUTION_TITLE_PREFIX + " " + nextSeq;
	}

	@Override
	public void deleteSolution(long solutionId)
	{
		FileStoreUtils.deleteFile(gdsapEvalSolutionMapper.load(solutionId).getSolutionPdfPath());
		FileStoreUtils.deleteFile(GlobalConfig.getInstance().getFSDir() + gdsapEvalSolutionMapper.load(solutionId).getSolutionLodgeXmlPath());
		FileStoreUtils.deleteFile(GlobalConfig.getInstance().getFSDir() + gdsapEvalSolutionMapper.load(solutionId).getSolutionPartLodgeXmlPath());
		gdsapEvalSolutionFuelCalResultMapper.deleteBySolution(solutionId);
		gdsapEvalSolutionRecommendationRelMapper.deleteBySolution(solutionId);
		gdsapEvalSolutionCalResultMapper.delete(solutionId);
		gdsapEvalSolutionMapper.delete(solutionId);
		gdsapEvalSolutionIssueMapper.delete(solutionId);
		gdsapEvalEpcImprovementResultMapper.deleteBySolutionId(solutionId);
		gdsapEvalPerFuelCalResultMapper.deleteBySolutionId(solutionId);
	}

	@Override
	public Solution getSolution(long id)
	{
		GdsapEvalSolution gdsapEvalSolution = gdsapEvalSolutionMapper.load(id);
		if(gdsapEvalSolution != null){
			Solution solution = BeanUtils.GSolutionToSolution(gdsapEvalSolution);
			solution.setReport(reportServiceMgr.getReport(gdsapEvalSolution.getReportId()));
			return solution;
		}
		return null;
	}

	@Override
	public List<Solution> getSolutions(long reportId, SolutionType solutionType)
	{
		Report r = this.getReportServiceMgr().getReport(reportId);
		List<GdsapEvalSolution> listG = gdsapEvalSolutionMapper.findByReport(reportId, solutionType.getCode());
		if(listG != null){
			List<Solution> listSolution = new ArrayList<Solution>();
			for(GdsapEvalSolution gdsapEvalSolution : listG){
				Solution solution = BeanUtils.GSolutionToSolution(gdsapEvalSolution);
				solution.setReport(r);
				listSolution.add(solution);
			}
			return listSolution;
		}
		return null;
	}

	@Override
	public Solution reAddSolution(Solution solution,List<StandardRecommendationWrap> srsw) throws CalculateException, Exception
	{
		String oldLodgeXmlPath = solution.getSolutionLodgeXmlPath();
		oldLodgeXmlPath = GlobalConfig.getInstance().getFSDir() + oldLodgeXmlPath;
		
		Assert.notNull(solution);
		Assert.notEmpty(srsw);
		
		Report report = solution.getReport();
		
		//insert solution
		solution.setUpdateTime(new Date());
		
		//计算结果处理
		String lodgeXmlContent = this.reportCommonServiceMgr.solutionCalResultXml(solution, srsw);
		calServiceMgr.calSolution(solution.getId(), srsw);
		
		String partLigPath = gdsapEvalSolutionMapper.load(solution.getId()).getSolutionPartLodgeXmlPath();
		solution.setSolutionPartLodgeXmlPath(partLigPath);
		GdsapEvalSolution solutionModel = BeanUtils.SolutionToGSolution(solution);
		solutionModel.setUpdateTime(new Date());
		
		String filename = UUID.randomUUID().toString();
		String datedir = GlobalUtils.dateDir(new Date());
		
		String parentDir = GlobalConfig.getInstance().getFSDir();
		if (!new File(parentDir + datedir).exists())
		{
			new File(parentDir + datedir).mkdirs();
		}
		File lodgeFile = new File(parentDir + datedir + filename + ".xml");
		try
		{
			lodgeFile.createNewFile();
			if (report.getReportLocation().equals(ReportLocation.EAW) || report.getReportLocation().equals(ReportLocation.NIR)) {
				FileUtils.writeStringToFile(lodgeFile, lodgeXmlContent,"utf-8");
				reportCommonServiceMgr.prettyXmlFormat(lodgeFile,solution.getReport().getReportLocation());
			}else{
				FileUtils.writeStringToFile(lodgeFile, lodgeXmlContent,"utf-8");
				reportCommonServiceMgr.prettyXmlFormat(lodgeFile,solution.getReport().getReportLocation());
			}
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		solutionModel.setSolutionLodgeXmlPath(datedir + filename + ".xml");
		gdsapEvalSolutionMapper.update(solutionModel);
		//Solution 文件保存
		solution = BeanUtils.do2bo(solutionModel);
		solution.setReport(report);
		
		if (new File(oldLodgeXmlPath).exists())
		{
			new File(oldLodgeXmlPath).delete();
		}
		return solution;
	}

	@Override
	public Solution selectSolution(long solutionId)
	{
		GdsapEvalSolution gdsapEvalSolution = gdsapEvalSolutionMapper.load(solutionId);
		if(gdsapEvalSolution == null){
			throw new IllegalArgumentException();
		}
		gdsapEvalSolutionMapper.updateSelectedByReportIdAndSelected(gdsapEvalSolution.getReportId(), gdsapEvalSolution.getSolutionType(), YesNo.No.getCode());
		gdsapEvalSolution.setSelected(YesNo.Yes.getCode());
		gdsapEvalSolution.setUpdateTime(new Date());
		this.gdsapEvalSolutionMapper.update(gdsapEvalSolution);
		return BeanUtils.do2bo(gdsapEvalSolution);
	}
	
	@Override
	public Solution getSelectedSolution(long reportId)
	{
		Report report = this.getReportServiceMgr().getReport(reportId);
		if (report == null)
		{
			throw new IllegalArgumentException();
		}
		GdsapEvalSolution model = this.gdsapEvalSolutionMapper.findSelectedByReportId(reportId);
		if (model != null)
		{
			return BeanUtils.do2bo(model);
		}
		return null;
	}

	@Override
	public List<SolutionResult> getSolutionsWithResult(long reportId, SolutionType solutionType)
	{
		List<Solution> solutions = this.getSolutions(reportId, solutionType);
		if (!CollectionUtils.isEmpty(solutions))
		{
			List<SolutionResult> results = new ArrayList<SolutionResult>();
			for (Solution solution : solutions)
			{
				SolutionResult sr = new SolutionResult();
				List<StandardRecommendationCalResult> calResult = this.calResultServiceMgr.getStandardRecommendationCalResults(solution.getId());
				sr.setStandardRecommendationCalResults(calResult);
				sr.setTotalEstimatedAnnualSavings(this.calResultServiceMgr.totalEstimatedAnnualSavings(calResult));
				sr.setTotalEstimatedCostsEnd(this.calResultServiceMgr.totalEstimatedCostsEnd(calResult));
				sr.setTotalEstimatedCostsStart(this.calResultServiceMgr.totalEstimatedCostsStart(calResult));
				sr.setTotalTypicalAnnualSavings(this.calResultServiceMgr.totalTypicalAnnualSavings(calResult));
				sr.setSolution(solution);
				sr.setSolutionIssue(this.calResultServiceMgr.getSolutionIssue(solution));
				results.add(sr);
			}
			return results;
		}
		return null;
	}

	@Override
	public void deleteSolutionResults(long solutionId)
	{
		gdsapEvalSolutionFuelCalResultMapper.deleteBySolution(solutionId);
		gdsapEvalSolutionRecommendationRelMapper.deleteBySolution(solutionId);
		gdsapEvalSolutionCalResultMapper.delete(solutionId);
		gdsapEvalEpcImprovementResultMapper.delete(solutionId);
		gdsapEvalSolutionIssueMapper.delete(solutionId);
		gdsapEvalEpcImprovementResultMapper.deleteBySolutionId(solutionId);
		gdsapEvalPerFuelCalResultMapper.deleteBySolutionId(solutionId);
	}

	public Solution updateSolution(Solution solution)
	{
		GdsapEvalSolution model = BeanUtils.bo2do(solution);
		gdsapEvalSolutionMapper.update(model);
		Solution s = BeanUtils.do2bo(model);
		return s;
	}
	
	public GdsapEvalSolutionMapper getGdsapEvalSolutionMapper() {
		return gdsapEvalSolutionMapper;
	}

	public void setGdsapEvalSolutionMapper(
			GdsapEvalSolutionMapper gdsapEvalSolutionMapper) {
		this.gdsapEvalSolutionMapper = gdsapEvalSolutionMapper;
	}

	public GdsapEvalSolutionSeqMapper getGdsapEvalSolutionSeqMapper() {
		return gdsapEvalSolutionSeqMapper;
	}

	public void setGdsapEvalSolutionSeqMapper(
			GdsapEvalSolutionSeqMapper gdsapEvalSolutionSeqMapper) {
		this.gdsapEvalSolutionSeqMapper = gdsapEvalSolutionSeqMapper;
	}

	public GdsapEvalSolutionRecommendationRelMapper getGdsapEvalSolutionRecommendationRelMapper() {
		return gdsapEvalSolutionRecommendationRelMapper;
	}

	public void setGdsapEvalSolutionRecommendationRelMapper(
			GdsapEvalSolutionRecommendationRelMapper gdsapEvalSolutionRecommendationRelMapper) {
		this.gdsapEvalSolutionRecommendationRelMapper = gdsapEvalSolutionRecommendationRelMapper;
	}

	public GdsapEvalSolutionImprovementRelMapper getGdsapEvalSolutionImprovementRelMapper() {
		return gdsapEvalSolutionImprovementRelMapper;
	}

	public void setGdsapEvalSolutionImprovementRelMapper(
			GdsapEvalSolutionImprovementRelMapper gdsapEvalSolutionImprovementRelMapper) {
		this.gdsapEvalSolutionImprovementRelMapper = gdsapEvalSolutionImprovementRelMapper;
	}

	public GdsapEvalSolutionFuelCalResultMapper getGdsapEvalSolutionFuelCalResultMapper() {
		return gdsapEvalSolutionFuelCalResultMapper;
	}

	public void setGdsapEvalSolutionFuelCalResultMapper(
			GdsapEvalSolutionFuelCalResultMapper gdsapEvalSolutionFuelCalResultMapper) {
		this.gdsapEvalSolutionFuelCalResultMapper = gdsapEvalSolutionFuelCalResultMapper;
	}

	public GdsapEvalSolutionCalResultMapper getGdsapEvalSolutionCalResultMapper() {
		return gdsapEvalSolutionCalResultMapper;
	}

	public void setGdsapEvalSolutionCalResultMapper(
			GdsapEvalSolutionCalResultMapper gdsapEvalSolutionCalResultMapper) {
		this.gdsapEvalSolutionCalResultMapper = gdsapEvalSolutionCalResultMapper;
	}

	public SolutionSeqServiceMgr getSolutionSeqServiceMgr() {
		return solutionSeqServiceMgr;
	}

	public void setSolutionSeqServiceMgr(SolutionSeqServiceMgr solutionSeqServiceMgr) {
		this.solutionSeqServiceMgr = solutionSeqServiceMgr;
	}

	public CalServiceMgr getCalServiceMgr() {
		return calServiceMgr;
	}

	public void setCalServiceMgr(CalServiceMgr calServiceMgr) {
		this.calServiceMgr = calServiceMgr;
	}

	public ReportServiceMgr getReportServiceMgr() {
		return reportServiceMgr;
	}

	public void setReportServiceMgr(ReportServiceMgr reportServiceMgr) {
		this.reportServiceMgr = reportServiceMgr;
	}

	public GdsapEvalRecommendationMapper getGdsapEvalRecommendationMapper() {
		return gdsapEvalRecommendationMapper;
	}

	public void setGdsapEvalRecommendationMapper(
			GdsapEvalRecommendationMapper gdsapEvalRecommendationMapper) {
		this.gdsapEvalRecommendationMapper = gdsapEvalRecommendationMapper;
	}
	
}
