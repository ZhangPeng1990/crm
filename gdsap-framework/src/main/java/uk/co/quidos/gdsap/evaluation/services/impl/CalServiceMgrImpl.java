/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import pw.itcircle.javaBeanFactory.factory.JavaBeanFactory;
import uk.co.quidos.common.util.time.DateFormatUtils;
import uk.co.quidos.gdsap.evaluation.AppCooking;
import uk.co.quidos.gdsap.evaluation.BillDataComm;
import uk.co.quidos.gdsap.evaluation.BillDataEle;
import uk.co.quidos.gdsap.evaluation.BillDataLPG;
import uk.co.quidos.gdsap.evaluation.BillDataMg;
import uk.co.quidos.gdsap.evaluation.CalResult;
import uk.co.quidos.gdsap.evaluation.EpcImprovementCalResult;
import uk.co.quidos.gdsap.evaluation.FuelCalResult;
import uk.co.quidos.gdsap.evaluation.HeatProportion;
import uk.co.quidos.gdsap.evaluation.HeatingPattern;
import uk.co.quidos.gdsap.evaluation.HeatingSystem;
import uk.co.quidos.gdsap.evaluation.Occupants;
import uk.co.quidos.gdsap.evaluation.OtherFuel;
import uk.co.quidos.gdsap.evaluation.PerFuelCalResult;
import uk.co.quidos.gdsap.evaluation.Report;
import uk.co.quidos.gdsap.evaluation.ReportEpcImprovementCalResult;
import uk.co.quidos.gdsap.evaluation.Solution;
import uk.co.quidos.gdsap.evaluation.SolutionIssue;
import uk.co.quidos.gdsap.evaluation.StandardOption;
import uk.co.quidos.gdsap.evaluation.StandardRecommendation;
import uk.co.quidos.gdsap.evaluation.StandardRecommendationCalResult;
import uk.co.quidos.gdsap.evaluation.StandardRecommendationWrap;
import uk.co.quidos.gdsap.evaluation.StandardValue;
import uk.co.quidos.gdsap.evaluation.calculate.CalOutError;
import uk.co.quidos.gdsap.evaluation.calculate.input.CalFuelPrice;
import uk.co.quidos.gdsap.evaluation.calculate.input.CalFuelType;
import uk.co.quidos.gdsap.evaluation.calculate.input.CalImprovement;
import uk.co.quidos.gdsap.evaluation.calculate.input.CalImprovement_EPC;
import uk.co.quidos.gdsap.evaluation.calculate.input.CalOutput;
import uk.co.quidos.gdsap.evaluation.calculate.input.ImprovementIssue;
import uk.co.quidos.gdsap.evaluation.calculate.input.OccupancyParameters;
import uk.co.quidos.gdsap.evaluation.calculate.input.PerFuelSaving;
import uk.co.quidos.gdsap.evaluation.calculate.input.RDSAP;
import uk.co.quidos.gdsap.evaluation.calculate.output.OATrunk;
import uk.co.quidos.gdsap.evaluation.calculate.output.RdSAPTrunk;
import uk.co.quidos.gdsap.evaluation.enums.SolutionType;
import uk.co.quidos.gdsap.evaluation.enums.TariffType;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalRecommendationMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalReportMapper;
import uk.co.quidos.gdsap.evaluation.services.AppCookingServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.BillDataServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.CalResultServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.CalServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.HeatProportionServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.HeatingPatternServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.HeatingSystemServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.OccupantsServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.OtherFuelServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.ReportServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.SolutionServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.StandardRecommendationServiceMgr;
import uk.co.quidos.gdsap.evaluation.utils.DocumentUtil;
import uk.co.quidos.gdsap.evaluation.utils.FileUtil;
import uk.co.quidos.gdsap.evaluation.utils.RestClientUtil;
import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.exception.CalculateException;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;
import uk.co.quidos.gdsap.framework.sysconf.GlobalConfig;
import uk.co.quidos.gdsap.framework.sysconf.GlobalUtils;

/**
 * @author peng.shi
 */
@Service("calServiceMgr")
@Transactional
public class CalServiceMgrImpl extends AbsBusinessObjectServiceMgr implements CalServiceMgr
{
	@Autowired
	private StandardRecommendationServiceMgr standardRecommendationServiceMgr;
	@Autowired
	private SolutionServiceMgr solutionServiceMgr;
	@Autowired
	private OccupantsServiceMgr occupantsServiceMgr;
	@Autowired
	private HeatingSystemServiceMgr heatingSystemServiceMgr;
	@Autowired
	private HeatProportionServiceMgr heatProportionServiceMgr; 
	@Autowired
	private HeatingPatternServiceMgr heatingPatternServiceMgr;
	@Autowired
	private AppCookingServiceMgr appCookingServiceMgr;
	@Autowired
	private BillDataServiceMgr billDataServiceMgr;
	@Autowired
	private OtherFuelServiceMgr otherFuelServiceMgr;
	@Autowired
	private GdsapEvalRecommendationMapper gdsapEvalRecommendationMapper;
	@Autowired
	private CalResultServiceMgr calResultServiceMgr;
	@Autowired
	private ReportServiceMgr reportServiceMgr;
	@Autowired
	private GdsapEvalReportMapper gdsapEvalReportMapper;

	// TODO ZP test , delete
	private final String CL = File.separator;
	
	public SolutionServiceMgr getSolutionServiceMgr()
	{
		return solutionServiceMgr;
	}

	public void setSolutionServiceMgr(SolutionServiceMgr solutionServiceMgr)
	{
		this.solutionServiceMgr = solutionServiceMgr;
	}

	public StandardRecommendationServiceMgr getStandardRecommendationServiceMgr()
	{
		return standardRecommendationServiceMgr;
	}

	public void setStandardRecommendationServiceMgr(StandardRecommendationServiceMgr standardRecommendationServiceMgr)
	{
		this.standardRecommendationServiceMgr = standardRecommendationServiceMgr;
	}

	@Override
	public List<StandardRecommendationWrap> calReport(long reportId, SolutionType solutionType) throws CalculateException
	{
		//Integer[] codes = new Integer[]{5,45,46,6,55,7,47,10,1,4,11,17,20,120,40,140,29,50,146,106};
		//删除掉当前Report 下的 
		//1. GDSAP_EVAL_RECOMMENDATION
		//2. GDSAP_EVAL_SOLUTION_RECOMMENDATION_REL
		//3. GDSAP_EVAL_SOLUTION
		//4. GDSAP_EVAL_SOLUTION_CAL_RESULT
		//5. GDSAP_EVAL_SOLUTION_FUEL_CAL_RESULT
		Report report = this.reportServiceMgr.getReport(reportId);
		if (report == null)
		{
			throw new IllegalArgumentException();
		}
		
		List<Solution> solutions = this.solutionServiceMgr.getSolutions(reportId, solutionType);
		if (!CollectionUtils.isEmpty(solutions))
		{
			for (Solution solution : solutions )
			{
				this.solutionServiceMgr.deleteSolution(solution.getId());
			}
		}
		this.gdsapEvalRecommendationMapper.deleteByReportAndType(reportId, solutionType.getCode());
		this.calResultServiceMgr.deleteReportEpcImprovementCalResults(report);
		//调用计算引擎并重新计算出StandardRecommendation
//		List<StandardRecommendation> srs = new ArrayList<StandardRecommendation>();
//		for (int i=0;i<codes.length;i++)
//		{
//			StandardRecommendation sr = this.standardRecommendationServiceMgr.getStandardRecommendation(codes[i]);
//			srs.add(sr);
//		}
		//List<StandardRecommendationWrap> wraps = this.standardRecommendationServiceMgr.addStandardRecommendation(reportId, srs);
		SAXReader reader = new SAXReader();
		Document doc;
		try
		{
			doc = reader.read(new StringReader(this.cal_ReturnResult(report, null, solutionType)));
			
			RDSAP rdsap = null;
			try {
				rdsap = (RDSAP)JavaBeanFactory.newInstance().createObject(doc, RDSAP.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			CalOutput output = rdsap.getDataOutput();
			
			Collection<CalImprovement> improvements = null;
			if(SolutionType.GDAR.equals(solutionType))
			{
				improvements = output.getRecommendation().getImprovements();
			}
			else //GDIP
			{
				improvements = output.getRecommendation().getImprovements_GDIP();
			}
			Collection<CalImprovement_EPC> improvementEPCs = output.getRecommendation_EPC().getImprovements_EPC();
			
			//EPC改进
			if (!CollectionUtils.isEmpty(improvementEPCs))
			{
				for (CalImprovement_EPC calImprovementEPC : improvementEPCs)
				{
					ReportEpcImprovementCalResult epcResult = new ReportEpcImprovementCalResult();
					epcResult.setImprovementType(calImprovementEPC.getMeasureCode().getName());
					epcResult.setImprovementNumber(calImprovementEPC.getImprovementNo());
					epcResult.setGreenDealCategory(calImprovementEPC.getGreenDeal().getCategory());
					epcResult.setEstimatedSaving((float)calImprovementEPC.getCustomSavingsPerYear());
					epcResult.setTypicalSaving((float)calImprovementEPC.getTypicalSavingsPerYear());
					double[] costRange = calImprovementEPC.getIndicativeCost();
					if (costRange != null && costRange.length ==2)
					{
						epcResult.setIndicativeCostStart(new Double(costRange[0]).floatValue());
						epcResult.setIndicativeCostEnd(new Double(costRange[1]).floatValue());
					}
					if (costRange != null && costRange.length ==1)
					{
						epcResult.setIndicativeCostStart(new Double(costRange[0]).floatValue());
						epcResult.setIndicativeCostEnd(new Double(costRange[0]).floatValue());
					}
					epcResult.setReport(report);
					epcResult.setInUseFactor(new Double(calImprovementEPC.getInUse()).floatValue());
					this.calResultServiceMgr.addReportEpcImprovementCalResult(epcResult);
				}
			}
			
			if (!CollectionUtils.isEmpty(improvements))
			{
				List<StandardRecommendation> srs = new ArrayList<StandardRecommendation>();
				for (CalImprovement calImprovement : improvements)
				{
					StandardRecommendation sr = this.standardRecommendationServiceMgr.getStandardRecommendation(calImprovement.getImprovementNo(), report);
					if (sr == null)
					{
						throw new IllegalArgumentException();
					}
					sr.setSolutionType(solutionType);
					srs.add(sr);
				}
				return this.standardRecommendationServiceMgr.addStandardRecommendation(report.getId(), srs);
			}
			
		} catch (DocumentException e)
		{
			e.printStackTrace();
			return null;
		}
		
		return null;
	}
	
	private CalImprovement _getCalImprovement(int code,Collection<CalImprovement> calImprovements)
	{
		if (!CollectionUtils.isEmpty(calImprovements))
		{
			for (CalImprovement i : calImprovements)
			{
				if (i.getImprovementNo() == code)
				{
					return i;
				}
			}
		}
		return null;
	}
	
	@Override
	public Map<String, Object> calSolution(long solutionId, List<StandardRecommendationWrap> srsw) throws CalculateException
	{
		Solution solution = this.solutionServiceMgr.getSolution(solutionId);
		Assert.notNull(solution);
		SolutionType solutionType = solution.getSolutionType();
		Assert.notNull(solutionType);
		Report report = solution.getReport();
		
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		
		String xml = this.cal_ReturnResult(report, srsw, solutionType);//xml是计算引擎webservice返回的计算结果数据，通过反射转换成对象
		
		//计算接口 负责计算整合以及GDOA原始数据整理xml，以及计算结果整理
		//清除以前计算结果
		solutionServiceMgr.deleteSolutionResults(solutionId);
				
		SAXReader reader = new SAXReader();
		Document doc;
		try
		{
			doc = reader.read(new StringReader(xml));
			//生成解析数据
			
			RDSAP rdsap = null;
			try {
				rdsap = (RDSAP)JavaBeanFactory.newInstance().createObject(doc, RDSAP.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			CalOutput output = rdsap.getDataOutput();
			
			//TODO SolutionCalResult 计算
			CalResult calResult = new CalResult();
			calResult.setSolution(solution);
			//calFuelBillData.
			
			calResult.setActualHousehold(output.getActualHousehold().floatValue());
			calResult.setTypicalHousehold(output.getTypicalHousehold().floatValue());
			calResult.setElectricitySavings(output.getElectricityRcost().floatValue());
			calResult.setGasSavings(output.getGasRcost().floatValue());
			calResult.setOtherFuelSavings(output.getOtherRcost().floatValue());
			calResult.setPercentHeating(output.getPercentOfEnergyByHeating().floatValue());
			calResult.setPercentHotWater(output.getPercentOfEnergyByHotWater().floatValue());
			calResult.setSpaceHeating(output.getSpaceHeating());
			calResult.setWaterHeating(output.getWaterHeating());
			calResult.setEnergyTotal(output.getTotalEnergyUse());
			
			// TODO ST_以及AC_ 开头 没有存储,已完成
			OccupancyParameters st = output.getStandardOccupancyParameters();
			if (st != null)
			{
				calResult.setStOccupants(st.getOccupants());
				calResult.setStAverageHours(_formatDateString(st.getAverageHoursOfHeating()));
				calResult.setStThermostatSetting(st.getThermostatSetting());
				calResult.setStUnheatedRooms(st.getUnheatedRooms());
				calResult.setStRecommendOutsideable(YesNo.bool2YesNo(st.isRecommendOutsideDrying()));
				calResult.setStRecommendShowersable(YesNo.bool2YesNo(st.isRecommendShowers()));
			}
			
			OccupancyParameters ac = output.getActOccupancyParameters();
			if (ac != null)
			{
				calResult.setAcOccupants(ac.getOccupants());
				calResult.setAcAverageHours(_formatDateString(ac.getAverageHoursOfHeating()));
				calResult.setAcThermostatSetting(ac.getThermostatSetting());
				calResult.setAcUnheatedRooms(ac.getUnheatedRooms());
				calResult.setAcRecommendOutsideable(YesNo.bool2YesNo(ac.isRecommendOutsideDrying()));
				calResult.setAcRecommendShowersable(YesNo.bool2YesNo(ac.isRecommendShowers()));
			}
			
			// TODO Cost Reduction 未知,Liveing Area Temperatrure.已完成
			calResult.setCostReduction(new Float(output.getLivingAreaTemperature()).floatValue());
			
			// TODO Imp 值存储
			// private Integer impBoilerFuel;
			// private Integer impSingleGlazedPercentage;
			calResult.setImpBoilerFuel(output.getBoilerFuel());
			calResult.setImpSingleGlazedPercentage(output.getExistingSingleGlazedPercentage());
			
			Map<String, CalFuelPrice> commCalFuelPrices = output.getCalFuelPriceList().getCommunityFuelPriceMap();
			if (commCalFuelPrices != null)
			{
				Double cost = commCalFuelPrices.get(CalFuelType.community_energy) != null ? commCalFuelPrices.get(CalFuelType.community_energy).getCost():null;
				Double use = commCalFuelPrices.get(CalFuelType.community_energy) != null ? commCalFuelPrices.get(CalFuelType.community_energy).getUse():null;
				Double scInput = commCalFuelPrices.get(CalFuelType.community_energy) != null ? commCalFuelPrices.get(CalFuelType.community_energy).getStandingChargeInput():null;
				Double scTable = commCalFuelPrices.get(CalFuelType.community_energy) != null ? commCalFuelPrices.get(CalFuelType.community_energy).getStandingChargeTable():null;
				Double upInput = commCalFuelPrices.get(CalFuelType.community_energy) != null ? commCalFuelPrices.get(CalFuelType.community_energy).getUitPriceInput():null;
				Double upTable = commCalFuelPrices.get(CalFuelType.community_energy) != null ? commCalFuelPrices.get(CalFuelType.community_energy).getUitPriceTable():null;
				Integer fuelCode = commCalFuelPrices.get(CalFuelType.community_energy) != null ? commCalFuelPrices.get(CalFuelType.community_energy).getHeatingFuelCode():null;
				Integer unitInput = commCalFuelPrices.get(CalFuelType.community_energy) != null ? commCalFuelPrices.get(CalFuelType.community_energy).getUnitCodeInput():null;
				Integer unitTable = commCalFuelPrices.get(CalFuelType.community_energy) != null ? commCalFuelPrices.get(CalFuelType.community_energy).getUnitCodeTable():null;
				
				calResult.setCommEnergyCost(cost != null ? cost.floatValue() : null);
				calResult.setCommEnergyUse(use != null ? use.floatValue() : null);
				calResult.setCommEnergyScInput(scInput != null ? scInput.floatValue() : null);
				calResult.setCommEnergyScTable(scTable != null ? scTable.floatValue() : null);
				calResult.setCommEnergyUpInput(upInput != null ? upInput.floatValue() : null);
				calResult.setCommEnergyUpTable(upTable != null ? upTable.floatValue() : null);
				calResult.setCommUnitInput(unitInput);
				calResult.setCommUnitTable(unitTable);
				calResult.setCommFuelCode(fuelCode);
			}
			
			
			//community_energy_chp
			Map<String, CalFuelPrice> commCHPCalFuelPrices = output.getCalFuelPriceList().getCommunityFuelPriceMap();
			if (commCalFuelPrices != null)
			{
				Double cost = commCHPCalFuelPrices.get(CalFuelType.community_energy_CHP) != null ? commCHPCalFuelPrices.get(CalFuelType.community_energy_CHP).getCost():null;
				Double use = commCHPCalFuelPrices.get(CalFuelType.community_energy_CHP) != null ? commCHPCalFuelPrices.get(CalFuelType.community_energy_CHP).getUse():null;
				Double scInput = commCHPCalFuelPrices.get(CalFuelType.community_energy_CHP) != null ? commCHPCalFuelPrices.get(CalFuelType.community_energy_CHP).getStandingChargeInput():null;
				Double scTable = commCHPCalFuelPrices.get(CalFuelType.community_energy_CHP) != null ? commCHPCalFuelPrices.get(CalFuelType.community_energy_CHP).getStandingChargeTable():null;
				Double upInput = commCHPCalFuelPrices.get(CalFuelType.community_energy_CHP) != null ? commCHPCalFuelPrices.get(CalFuelType.community_energy_CHP).getUitPriceInput():null;
				Double upTable = commCHPCalFuelPrices.get(CalFuelType.community_energy_CHP) != null ? commCHPCalFuelPrices.get(CalFuelType.community_energy_CHP).getUitPriceTable():null;
				Integer fuelCode = commCHPCalFuelPrices.get(CalFuelType.community_energy_CHP) != null ? commCHPCalFuelPrices.get(CalFuelType.community_energy_CHP).getHeatingFuelCode():null;
				Integer unitInput = commCHPCalFuelPrices.get(CalFuelType.community_energy_CHP) != null ? commCHPCalFuelPrices.get(CalFuelType.community_energy_CHP).getUnitCodeInput():null;
				Integer unitTable = commCHPCalFuelPrices.get(CalFuelType.community_energy_CHP) != null ? commCHPCalFuelPrices.get(CalFuelType.community_energy_CHP).getUnitCodeTable():null;
				
				calResult.setCommChpEnergyCost(cost != null ? cost.floatValue() : null);
				calResult.setCommChpEnergyUse(use != null ? use.floatValue() : null);
				calResult.setCommChpEnergyScInput(scInput != null ? scInput.floatValue() : null);
				calResult.setCommChpEnergyScTable(scTable != null ? scTable.floatValue() : null);
				calResult.setCommChpEnergyUpInput(upInput != null ? upInput.floatValue() : null);
				calResult.setCommChpEnergyUpTable(upTable != null ? upTable.floatValue() : null);
				calResult.setCommChpUnitInput(unitInput);
				calResult.setCommChpUnitTable(unitTable);
				calResult.setCommChpFuelCode(fuelCode);
			}
			
			
			Map<String, CalFuelPrice> elePrices = output.getCalFuelPriceList().getElectricityFuelPriceMap();
			if (elePrices != null)
			{
				//sd 设置
				Double costsd = elePrices.get(CalFuelType.electricity) != null ? elePrices.get(CalFuelType.electricity).getCost():null;
				Double usesd = elePrices.get(CalFuelType.electricity) != null ? elePrices.get(CalFuelType.electricity).getUse():null;
				
				Double scInputsd = elePrices.get(CalFuelType.electricity) != null ? elePrices.get(CalFuelType.electricity).getStandingChargeInput():null;
				Double scTablesd = elePrices.get(CalFuelType.electricity) != null ? elePrices.get(CalFuelType.electricity).getStandingChargeTable():null;
				Double upInputsd = elePrices.get(CalFuelType.electricity) != null ? elePrices.get(CalFuelType.electricity).getUitPriceInput():null;
				Double upTablesd = elePrices.get(CalFuelType.electricity) != null ? elePrices.get(CalFuelType.electricity).getUitPriceTable():null;
				Integer fuelcodesd = elePrices.get(CalFuelType.electricity) != null ? elePrices.get(CalFuelType.electricity).getHeatingFuelCode():null;
				
				Integer unitInput = elePrices.get(CalFuelType.electricity) != null ? elePrices.get(CalFuelType.electricity).getUnitCodeInput():null;
				Integer unitTable = elePrices.get(CalFuelType.electricity) != null ? elePrices.get(CalFuelType.electricity).getUnitCodeTable():null;
				
				calResult.setEleSdCost(costsd != null ? costsd.floatValue():null);
				calResult.setEleSdUse(usesd != null ? usesd.floatValue():null);
				
				calResult.setEleSdScInput(scInputsd != null ? scInputsd.floatValue():null);
				calResult.setEleSdScTable(scTablesd != null ? scTablesd.floatValue():null);
				calResult.setEleSdUpInput(upInputsd != null ? upInputsd.floatValue():null);
				calResult.setEleSdUpTable(upTablesd != null ? upTablesd.floatValue():null);
				calResult.setEleStFuelCode(fuelcodesd);
				calResult.setEleSdUnitInput(unitInput);
				calResult.setEleSdUnitTable(unitTable);
				
				Double costsd24 = elePrices.get(CalFuelType._24Hour_electricity) != null ? elePrices.get(CalFuelType._24Hour_electricity).getCost():null;
				Double usesd24 = elePrices.get(CalFuelType._24Hour_electricity) != null ? elePrices.get(CalFuelType._24Hour_electricity).getUse():null;
				
				Double scInputsd24 = elePrices.get(CalFuelType._24Hour_electricity) != null ? elePrices.get(CalFuelType._24Hour_electricity).getStandingChargeInput():null;
				Double scTablesd24 = elePrices.get(CalFuelType._24Hour_electricity) != null ? elePrices.get(CalFuelType._24Hour_electricity).getStandingChargeTable():null;
				Double upInputsd24 = elePrices.get(CalFuelType._24Hour_electricity) != null ? elePrices.get(CalFuelType._24Hour_electricity).getUitPriceInput():null;
				Double upTablesd24 = elePrices.get(CalFuelType._24Hour_electricity) != null ? elePrices.get(CalFuelType._24Hour_electricity).getUitPriceTable():null;
				Integer fuelcodesd24 = elePrices.get(CalFuelType._24Hour_electricity) != null ? elePrices.get(CalFuelType._24Hour_electricity).getHeatingFuelCode():null;
				
				Integer unitInput24 = elePrices.get(CalFuelType._24Hour_electricity) != null ? elePrices.get(CalFuelType._24Hour_electricity).getUnitCodeInput():null;
				Integer unitTable24 = elePrices.get(CalFuelType._24Hour_electricity) != null ? elePrices.get(CalFuelType._24Hour_electricity).getUnitCodeTable():null;
				
				calResult.setEleSd24Cost(costsd24 != null ? costsd24.floatValue():null);
				calResult.setEleSd24Use(usesd24 != null ? usesd24.floatValue():null);
				
				calResult.setEleSd24ScInput(scInputsd24 != null ? scInputsd24.floatValue():null);
				calResult.setEleSd24ScTable(scTablesd24 != null ? scTablesd24.floatValue():null);
				calResult.setEleSd24UpInput(upInputsd24 != null ? upInputsd24.floatValue():null);
				calResult.setEleSd24UpTable(upTablesd24 != null ? upTablesd24.floatValue():null);
				calResult.setEleSt24FuelCode(fuelcodesd24);
				calResult.setEleSd24UnitInput(unitInput24);
				calResult.setEleSd24UnitTable(unitTable24);
				
				//op hight
				Double costh = elePrices.get(CalFuelType.electricity_high_rate) != null ? elePrices.get(CalFuelType.electricity_high_rate).getCost():null;
				Double useh = elePrices.get(CalFuelType.electricity_high_rate) != null ? elePrices.get(CalFuelType.electricity_high_rate).getUse():null;
				
				Double scInputh = elePrices.get(CalFuelType.electricity_high_rate) != null ? elePrices.get(CalFuelType.electricity_high_rate).getStandingChargeInput():null;
				Double scTableh = elePrices.get(CalFuelType.electricity_high_rate) != null ? elePrices.get(CalFuelType.electricity_high_rate).getStandingChargeTable():null;
				Double upInputh = elePrices.get(CalFuelType.electricity_high_rate) != null ? elePrices.get(CalFuelType.electricity_high_rate).getUitPriceInput():null;
				Double upTableh = elePrices.get(CalFuelType.electricity_high_rate) != null ? elePrices.get(CalFuelType.electricity_high_rate).getUitPriceTable():null;
				Integer fuelcodeh = elePrices.get(CalFuelType.electricity_high_rate) != null ? elePrices.get(CalFuelType.electricity_high_rate).getHeatingFuelCode():null;
				
				Integer hunitTable = elePrices.get(CalFuelType.electricity_high_rate) != null ? elePrices.get(CalFuelType.electricity_high_rate).getUnitCodeTable():null;
				Integer hunitInput = elePrices.get(CalFuelType.electricity_high_rate) != null ? elePrices.get(CalFuelType.electricity_high_rate).getUnitCodeInput():null;
				
				calResult.setEleOpCostHighrate(costh != null ? costh.floatValue():null);
				calResult.setEleOpUseHighrate(useh != null ? useh.floatValue() : null);
				calResult.setEleOpScInputHighrate(scInputh != null ? scInputh.floatValue():null);
				calResult.setEleOpScTableHighrate(scTableh != null ? scTableh.floatValue():null);
				calResult.setEleOpUpInputHighrate(upInputh != null ? upInputh.floatValue():null);
				calResult.setEleOpUpTableHighrate(upTableh != null ? upTableh.floatValue():null);
				calResult.setEleHFuelCode(fuelcodeh);
				
				calResult.setEleHUnitInput(hunitInput);
				calResult.setEleHUnitTable(hunitTable);
				//op low
				Double costl = elePrices.get(CalFuelType.electricity_low_rate) != null ? elePrices.get(CalFuelType.electricity_low_rate).getCost():null;
				Double usel = elePrices.get(CalFuelType.electricity_low_rate) != null ? elePrices.get(CalFuelType.electricity_low_rate).getUse():null;
				
				Double scInputl = elePrices.get(CalFuelType.electricity_low_rate) != null ? elePrices.get(CalFuelType.electricity_low_rate).getStandingChargeInput():null;
				Double scTablel = elePrices.get(CalFuelType.electricity_low_rate) != null ? elePrices.get(CalFuelType.electricity_low_rate).getStandingChargeTable():null;
				Double upInputl = elePrices.get(CalFuelType.electricity_low_rate) != null ? elePrices.get(CalFuelType.electricity_low_rate).getUitPriceInput():null;
				Double upTablel = elePrices.get(CalFuelType.electricity_low_rate) != null ? elePrices.get(CalFuelType.electricity_low_rate).getUitPriceTable():null;
				Integer fuelcodel = elePrices.get(CalFuelType.electricity_low_rate) != null ? elePrices.get(CalFuelType.electricity_low_rate).getHeatingFuelCode():null;
				
				Integer lunitTable = elePrices.get(CalFuelType.electricity_low_rate) != null ? elePrices.get(CalFuelType.electricity_low_rate).getUnitCodeTable():null;
				Integer lunitInput = elePrices.get(CalFuelType.electricity_low_rate) != null ? elePrices.get(CalFuelType.electricity_low_rate).getUnitCodeInput():null;
				
				calResult.setEleCostLowrate(costl != null ? costl.floatValue():null);
				calResult.setEleUseLowrate(usel != null ? usel.floatValue() : null);
				calResult.setEleScInputLowrate(scInputl != null ? scInputl.floatValue():null);
				calResult.setEleScTableLowrate(scTablel != null ? scTablel.floatValue():null);
				calResult.setEleUpInputLowrate(upInputl != null ? upInputl.floatValue():null);
				calResult.setEleUpTableLowrate(upTablel != null ? upTablel.floatValue():null);
				calResult.setEleLFuelCode(fuelcodel);
				
				calResult.setEleLUnitInput(lunitInput);
				calResult.setEleLUnitTable(lunitTable);
				
			}
			
			Map<String, CalFuelPrice> mainsGasPrices = output.getCalFuelPriceList().getMainGasFuelPriceMap();
			if (mainsGasPrices != null)
			{
				Double costmg = mainsGasPrices.get(CalFuelType.mains_gas) != null ? mainsGasPrices.get(CalFuelType.mains_gas).getCost():null;
				Double usemg = mainsGasPrices.get(CalFuelType.mains_gas) != null ? mainsGasPrices.get(CalFuelType.mains_gas).getUse():null;
				Double scinputmg = mainsGasPrices.get(CalFuelType.mains_gas) != null ? mainsGasPrices.get(CalFuelType.mains_gas).getStandingChargeInput():null;
				Double sctablemg = mainsGasPrices.get(CalFuelType.mains_gas) != null ? mainsGasPrices.get(CalFuelType.mains_gas).getStandingChargeTable():null;
				Double upinputmg = mainsGasPrices.get(CalFuelType.mains_gas) != null ? mainsGasPrices.get(CalFuelType.mains_gas).getUitPriceInput():null;
				Double uptablemg = mainsGasPrices.get(CalFuelType.mains_gas) != null ? mainsGasPrices.get(CalFuelType.mains_gas).getUitPriceTable():null;
				Integer fuelcodemg = mainsGasPrices.get(CalFuelType.mains_gas) != null ? mainsGasPrices.get(CalFuelType.mains_gas).getHeatingFuelCode():null;
				
				Integer mgunitTable = mainsGasPrices.get(CalFuelType.mains_gas) != null ? mainsGasPrices.get(CalFuelType.mains_gas).getUnitCodeTable():null;
				Integer mgunitInput = mainsGasPrices.get(CalFuelType.mains_gas) != null ? mainsGasPrices.get(CalFuelType.mains_gas).getUnitCodeInput():null;
				
				calResult.setMainsGasCost(costmg != null ? costmg.floatValue():null);
				calResult.setMainsGasUse(usemg != null ? usemg.floatValue():null);
				calResult.setMainsGasScInput(scinputmg != null ? scinputmg.floatValue():null);
				calResult.setMainsGasScTable(sctablemg != null ? sctablemg.floatValue():null);
				calResult.setMainsGasUpInput(upinputmg != null ? upinputmg.floatValue():null);
				calResult.setMainsGasUpTable(uptablemg != null ? uptablemg.floatValue():null);
				calResult.setMgFuelCode(fuelcodemg);
				
				calResult.setMgUnitInput(mgunitInput);
				calResult.setMgUnitTable(mgunitTable);
			}
			
			calResult.setTotalEleSaving(new Double(output.getElectricityRcost()).floatValue());
			calResult.setTotalGasSaving(new Double(output.getGasRcost()).floatValue());
			calResult.setTotalOtherFuelSaving(new Double(output.getOtherRcost()).floatValue());
			
			this.calResultServiceMgr.addCalResult(calResult);
			
			List<StandardRecommendationCalResult> standardRecommendationCalResults = new ArrayList<StandardRecommendationCalResult>();
			
			Collection<CalImprovement> improvements = null;
			
			if(solutionType.getCode() == SolutionType.GDAR.getCode())
			{
				improvements = output.getRecommendation().getImprovements();
			}
			else if(solutionType.getCode() == SolutionType.GDIP.getCode())
			{
				improvements = output.getRecommendation().getImprovements_GDIP();
			}
			
			if (CollectionUtils.isEmpty(improvements))
			{
				throw new IllegalArgumentException("CalImprovement List empty!");
			}
			if (!CollectionUtils.isEmpty(srsw))
			{
				for (StandardRecommendationWrap wrap : srsw)
				{
					CalImprovement imp = this._getCalImprovement(wrap.getStandardRecommendation().getCode(), improvements);
					if (imp == null)
					{
						throw new IllegalArgumentException("CalImprovement is null.");
					}
					
					StandardRecommendationCalResult srCalResult = new StandardRecommendationCalResult();
					srCalResult.setSolution(solution);
					srCalResult.setStandardRecommendationWrap(wrap);
					srCalResult.setEstimatedAnnualSavings(new Float(imp.getCustomSavingsPerYear()));
					srCalResult.setGreenDealCategory(imp.getCategory());
					if (imp.getInUse() != null && imp.getInUse().length >0)
					{
						if (imp.getInUse().length ==1)
						{
							srCalResult.setInUseFactor(new Double(imp.getInUse()[0]).floatValue());
						}
						if (imp.getInUse().length ==2)
						{
							srCalResult.setInUseFactor(new Double(imp.getInUse()[0]).floatValue());
							srCalResult.setInUseFactor2(new Double(imp.getInUse()[1]).floatValue());
						}
					}
					double[] startends = imp.getIndicativeCost();
					if (startends == null || startends.length <=0)
					{
						srCalResult.setEstimatedCostsStart(0f);
						srCalResult.setEstimatedCostsEnd(0f);
					}
					else if (startends != null && startends.length ==1 )
					{
						srCalResult.setEstimatedCostsStart(new Double(startends[0]).floatValue());
						srCalResult.setEstimatedCostsEnd(new Double(startends[0]).floatValue());
					}
					else if (startends != null && startends.length == 2)
					{
						srCalResult.setEstimatedCostsStart(new Double(startends[0]).floatValue());
						srCalResult.setEstimatedCostsEnd(new Double(startends[1]).floatValue());
					}
					else 
					{
						throw new IllegalArgumentException(" StandardRecommendation Result Error : Estimated start end not match.");
					}
					
					srCalResult.setTypicalAnnualSavings(new Double(imp.getTypicalSavingsPerYear()).floatValue());
					srCalResult.setStandardRecommendationWrap(wrap);
					
					//TODO Wall wallConstruction 赋值 未完成
					List<Integer> wallLodgeCodes = imp.getConstructionLodgeCode();
					if (!CollectionUtils.isEmpty(wallLodgeCodes))
					{
						if (wallLodgeCodes.size() > 3)
						{
							throw new IllegalArgumentException();
						}
						for (int i=0;i<wallLodgeCodes.size();i++)
						{
							if (i == 0)
							{
								srCalResult.setWallConstruction1(wallLodgeCodes.get(0).toString());
							}
							if (i == 1)
							{
								srCalResult.setWallConstruction2(wallLodgeCodes.get(1).toString());
							}
							if (i == 2 )
							{
								srCalResult.setWallConstruction3(wallLodgeCodes.get(2).toString());
							}
						}
					}
					//TODO END Wall WallConstruction
					
					Map<StandardOption, Integer> selectedOptionItems = wrap.getSelectedOptionItems();
					if (!CollectionUtils.isEmpty(selectedOptionItems))
					{
						//有选项值
						Set<StandardOption> options = selectedOptionItems.keySet();
						int index = 0;
						for (StandardOption so : options)
						{
							if (index == 0)
							{
								srCalResult.setKey0(so.getCode());
								srCalResult.setValue0(selectedOptionItems.get(so));
							}
							if (index == 1)
							{
								srCalResult.setKey1(so.getCode());
								srCalResult.setValue1(selectedOptionItems.get(so));
							}
							if (index == 2)
							{
								srCalResult.setKey2(so.getCode());
								srCalResult.setValue2(selectedOptionItems.get(so));
							}
							if (index == 3)
							{
								srCalResult.setKey3(so.getCode());
								srCalResult.setValue3(selectedOptionItems.get(so));
							}
							if (index == 4)
							{
								srCalResult.setKey4(so.getCode());
								srCalResult.setValue4(selectedOptionItems.get(so));
							}
							if (index == 5)
							{
								srCalResult.setKey5(so.getCode());
								srCalResult.setValue5(selectedOptionItems.get(so));
							}
							if (index == 6)
							{
								srCalResult.setKey6(so.getCode());
								srCalResult.setValue6(selectedOptionItems.get(so));
							}
							if (index == 7)
							{
								srCalResult.setKey7(so.getCode());
								srCalResult.setValue7(selectedOptionItems.get(so));
							}
							if (index == 8)
							{
								srCalResult.setKey8(so.getCode());
								srCalResult.setValue8(selectedOptionItems.get(so));
							}
							if (index == 9)
							{
								srCalResult.setKey9(so.getCode());
								srCalResult.setValue9(selectedOptionItems.get(so));
							}
							index = index + 1;
						}
					}
					Map<StandardValue, String> selectedInputValues = wrap.getSelectedInputValues();
					if (!CollectionUtils.isEmpty(selectedInputValues))
					{
						//有值
						Set<StandardValue> values = selectedInputValues.keySet();
						int index = 0;
						for (StandardValue so : values)
						{
							if (index == 0)
							{
								srCalResult.setInputKey0(so.getName());
								srCalResult.setInputValue0(so.getInputValue());
								srCalResult.setCheckedKey0(so.getName());
								srCalResult.setCheckedValue0(so.isU_valuesDeclarationSelected());
							}
							if (index == 1)
							{
								srCalResult.setInputKey1(so.getName());
								srCalResult.setInputValue1(so.getInputValue());
								srCalResult.setCheckedKey1(so.getName());
								srCalResult.setCheckedValue1(so.isU_valuesDeclarationSelected());
							}
							if (index == 2)
							{
								srCalResult.setInputKey2(so.getName());
								srCalResult.setInputValue2(so.getInputValue());
								srCalResult.setCheckedKey2(so.getName());
								srCalResult.setCheckedValue2(so.isU_valuesDeclarationSelected());
							}
							if (index == 3)
							{
								srCalResult.setInputKey3(so.getName());
								srCalResult.setInputValue3(so.getInputValue());
								srCalResult.setCheckedKey3(so.getName());
								srCalResult.setCheckedValue3(so.isU_valuesDeclarationSelected());
							}
							if (index == 4)
							{
								srCalResult.setInputKey4(so.getName());
								srCalResult.setInputValue4(so.getInputValue());
								srCalResult.setCheckedKey4(so.getName());
								srCalResult.setCheckedValue4(so.isU_valuesDeclarationSelected());
							}
							if (index == 5)
							{
								srCalResult.setInputKey5(so.getName());
								srCalResult.setInputValue5(so.getInputValue());
								srCalResult.setCheckedKey5(so.getName());
								srCalResult.setCheckedValue5(so.isU_valuesDeclarationSelected());
							}
							index = index + 1;
						}
					}
					standardRecommendationCalResults.add(srCalResult);
				}
				standardRecommendationCalResults = this.calResultServiceMgr.addStandardRecommendationCalResults(standardRecommendationCalResults);
			}
			
			List<FuelCalResult> fuelCalResults = new ArrayList<FuelCalResult>();
			CalFuelPrice cfp1 = output.getCalFuelPriceList().getAllFuelPriceMap().get(CalFuelType.other_fuel_1);
			if (cfp1 != null)
			{
				FuelCalResult r = new FuelCalResult();
				r.setFuelCode(cfp1.getHeatingFuelCode());
				r.setOtherFuelCost(new Double(cfp1.getCost()).floatValue());
				r.setOtherFuelUse(new Double(cfp1.getUse()).floatValue());
				r.setScInput(new Double(cfp1.getStandingChargeInput()).floatValue());
				r.setScTable(new Double(cfp1.getStandingChargeTable()).floatValue());
				r.setUpInput(new Double(cfp1.getUitPriceInput()).floatValue());
				r.setUpTable(new Double(cfp1.getUitPriceTable()).floatValue());
				r.setSolution(solution);
				r.setUnitInput(cfp1.getUnitCodeInput());
				r.setUnitTable(cfp1.getUnitCodeTable());
				
				fuelCalResults.add(r);
			}
			
			CalFuelPrice cfp2 = output.getCalFuelPriceList().getAllFuelPriceMap().get(CalFuelType.other_fuel_2);
			if (cfp2 != null)
			{
				FuelCalResult r = new FuelCalResult();
				r.setFuelCode(cfp2.getHeatingFuelCode());
				r.setOtherFuelCost(new Double(cfp2.getCost()).floatValue());
				r.setOtherFuelUse(new Double(cfp2.getUse()).floatValue());
				r.setScInput(new Double(cfp2.getStandingChargeInput()).floatValue());
				r.setScTable(new Double(cfp2.getStandingChargeTable()).floatValue());
				r.setUpInput(new Double(cfp2.getUitPriceInput()).floatValue());
				r.setUpTable(new Double(cfp2.getUitPriceTable()).floatValue());
				r.setSolution(solution);
				r.setUnitInput(cfp2.getUnitCodeInput());
				r.setUnitTable(cfp2.getUnitCodeTable());
				
				fuelCalResults.add(r);
			}
			CalFuelPrice cfp3 = output.getCalFuelPriceList().getAllFuelPriceMap().get(CalFuelType.other_fuel_3);
			if (cfp3 != null)
			{
				FuelCalResult r = new FuelCalResult();
				r.setFuelCode(cfp3.getHeatingFuelCode());
				r.setOtherFuelCost(new Double(cfp3.getCost()).floatValue());
				r.setOtherFuelUse(new Double(cfp3.getUse()).floatValue());
				r.setScInput(new Double(cfp3.getStandingChargeInput()).floatValue());
				r.setScTable(new Double(cfp3.getStandingChargeTable()).floatValue());
				r.setUpInput(new Double(cfp3.getUitPriceInput()).floatValue());
				r.setUpTable(new Double(cfp3.getUitPriceTable()).floatValue());
				r.setSolution(solution);
				
				r.setUnitInput(cfp3.getUnitCodeInput());
				r.setUnitTable(cfp3.getUnitCodeTable());
				
				fuelCalResults.add(r);
			}
			CalFuelPrice cfp4 = output.getCalFuelPriceList().getAllFuelPriceMap().get(CalFuelType.other_fuel_4);
			if (cfp4 != null)
			{
				FuelCalResult r = new FuelCalResult();
				r.setFuelCode(cfp4.getHeatingFuelCode());
				r.setOtherFuelCost(new Double(cfp4.getCost()).floatValue());
				r.setOtherFuelUse(new Double(cfp4.getUse()).floatValue());
				r.setScInput(new Double(cfp4.getStandingChargeInput()).floatValue());
				r.setScTable(new Double(cfp4.getStandingChargeTable()).floatValue());
				r.setUpInput(new Double(cfp4.getUitPriceInput()).floatValue());
				r.setUpTable(new Double(cfp4.getUitPriceTable()).floatValue());
				r.setSolution(solution);
				r.setUnitInput(cfp4.getUnitCodeInput());
				r.setUnitTable(cfp4.getUnitCodeTable());
				
				fuelCalResults.add(r);
			}
			CalFuelPrice cfp5 = output.getCalFuelPriceList().getAllFuelPriceMap().get(CalFuelType.other_fuel_5);
			if (cfp5 != null)
			{
				FuelCalResult r = new FuelCalResult();
				r.setFuelCode(cfp5.getHeatingFuelCode());
				r.setOtherFuelCost(new Double(cfp5.getCost()).floatValue());
				r.setOtherFuelUse(new Double(cfp5.getUse()).floatValue());
				r.setScInput(new Double(cfp5.getStandingChargeInput()).floatValue());
				r.setScTable(new Double(cfp5.getStandingChargeTable()).floatValue());
				r.setUpInput(new Double(cfp5.getUitPriceInput()).floatValue());
				r.setUpTable(new Double(cfp5.getUitPriceTable()).floatValue());
				r.setSolution(solution);
				
				r.setUnitInput(cfp5.getUnitCodeInput());
				r.setUnitTable(cfp5.getUnitCodeTable());
				
				fuelCalResults.add(r);
			}
			
			if (!CollectionUtils.isEmpty(fuelCalResults))
			{
				this.calResultServiceMgr.addFuelCalResults(fuelCalResults);
			}
			
			//TODO 计算生成EpcImprovements,还未完成
			Collection<CalImprovement_EPC> improvementEPCs = output.getRecommendation_EPC().getImprovements_EPC();
			
			if (!CollectionUtils.isEmpty(improvementEPCs))
			{
				List<EpcImprovementCalResult> epcImprovementCalResults = new ArrayList<EpcImprovementCalResult>();
				for (CalImprovement_EPC calImprovementEPC : improvementEPCs)
				{
					EpcImprovementCalResult epcResult = new EpcImprovementCalResult();
					epcResult.setImprovementType(calImprovementEPC.getMeasureCode().getName());
					epcResult.setImprovementNumber(calImprovementEPC.getImprovementNo());
					epcResult.setGreenDealCategory(calImprovementEPC.getGreenDeal().getCategory());
					epcResult.setEstimatedSaving((float)calImprovementEPC.getCustomSavingsPerYear());
					epcResult.setTypicalSaving((float)calImprovementEPC.getTypicalSavingsPerYear());
					double[] costRange = calImprovementEPC.getIndicativeCost();
					if (costRange != null && costRange.length ==2)
					{
						epcResult.setIndicativeCostStart(new Double(costRange[0]).floatValue());
						epcResult.setIndicativeCostEnd(new Double(costRange[1]).floatValue());
					}
					if (costRange != null && costRange.length ==1)
					{
						epcResult.setIndicativeCostStart(new Double(costRange[0]).floatValue());
						epcResult.setIndicativeCostEnd(new Double(costRange[0]).floatValue());
					}
					epcResult.setSolution(solution);
					epcResult.setInUseFactor(new Double(calImprovementEPC.getInUse()).floatValue());
					epcImprovementCalResults.add(epcResult);
				}
				if (!CollectionUtils.isEmpty(epcImprovementCalResults))
				{					
					this.calResultServiceMgr.addEpcImprovementCalResults(epcImprovementCalResults);
				}
			}
			
			//TODO 计算 PerFuelSaving
			List<PerFuelSaving> perfuelSavings = output.getPerFuelSavingList();
			if (!CollectionUtils.isEmpty(perfuelSavings))
			{
				List<PerFuelCalResult> perFuelCalResults = new ArrayList<PerFuelCalResult>();
				for (PerFuelSaving saving : perfuelSavings)
				{
					PerFuelCalResult perFuelCalResult = new PerFuelCalResult();
					perFuelCalResult.setFuelCode(saving.getCode());
					perFuelCalResult.setKwhSaving(new Double(saving.getKwhSaving()).floatValue());
					perFuelCalResult.setScFraction(new Double(saving.getStandingChargeFraction()).floatValue());
					perFuelCalResult.setSolution(solution);
					perFuelCalResults.add(perFuelCalResult);
				}
				this.calResultServiceMgr.addPerFuelCalResults(perFuelCalResults);
			}
			
			//TODO 计算 Issue 计算结果
			Map<ImprovementIssue, Boolean> issueMaps = output.getImprovementIssue();
			if (!CollectionUtils.isEmpty(issueMaps))
			{
				SolutionIssue issue = new SolutionIssue();
				issue.setAccessIssues(issueMaps.get(ImprovementIssue.AccessIssues) != null ? YesNo.bool2YesNo(issueMaps.get(ImprovementIssue.AccessIssues)):YesNo.No);
				issue.setCavityFillUnknown(issueMaps.get(ImprovementIssue.CavityFillUnknown) != null ? YesNo.bool2YesNo(issueMaps.get(ImprovementIssue.CavityFillUnknown)):YesNo.No);
				issue.setFlatRoofInsulationUnknown(issueMaps.get(ImprovementIssue.FlatRoofInsulationUnknown) != null ? YesNo.bool2YesNo(issueMaps.get(ImprovementIssue.FlatRoofInsulationUnknown)):YesNo.No);
				issue.setFloorInsulationUnknown(issueMaps.get(ImprovementIssue.FloorInsulationUnknown) != null ? YesNo.bool2YesNo(issueMaps.get(ImprovementIssue.FloorInsulationUnknown)):YesNo.No);
				issue.setHighExposure(issueMaps.get(ImprovementIssue.HighExposure) != null ? YesNo.bool2YesNo(issueMaps.get(ImprovementIssue.HighExposure)):YesNo.No);
				issue.setMainsGasNeeded(issueMaps.get(ImprovementIssue.MainsGasNeeded) != null ? YesNo.bool2YesNo(issueMaps.get(ImprovementIssue.MainsGasNeeded)):YesNo.No);
				issue.setNarrowCavities(issueMaps.get(ImprovementIssue.NarrowCavities) != null ? YesNo.bool2YesNo(issueMaps.get(ImprovementIssue.NarrowCavities)):YesNo.No);
				issue.setNoCylinderAccess(issueMaps.get(ImprovementIssue.NoCylinderAccess) != null ? YesNo.bool2YesNo(issueMaps.get(ImprovementIssue.NoCylinderAccess)):YesNo.No);
				issue.setNoLoftAccess(issueMaps.get(ImprovementIssue.NoLoftAccess) != null ? YesNo.bool2YesNo(issueMaps.get(ImprovementIssue.NoLoftAccess)):YesNo.No);
				issue.setRoofRoomInsulationUnknown(issueMaps.get(ImprovementIssue.RoofRoomInsulationUnknown) != null ? YesNo.bool2YesNo(issueMaps.get(ImprovementIssue.RoofRoomInsulationUnknown)):YesNo.No);
				issue.setSolidWallInsulationUnknown(issueMaps.get(ImprovementIssue.SolidWallInsulationUnknown) != null ? YesNo.bool2YesNo(issueMaps.get(ImprovementIssue.SolidWallInsulationUnknown)):YesNo.No);
				issue.setSolution(solution);
				issue.setStoneWalls(issueMaps.get(ImprovementIssue.StoneWalls) != null ? YesNo.bool2YesNo(issueMaps.get(ImprovementIssue.StoneWalls)):YesNo.No);
				issue.setSystemBuild(issueMaps.get(ImprovementIssue.SystemBuild) != null ? YesNo.bool2YesNo(issueMaps.get(ImprovementIssue.SystemBuild)):YesNo.No);
				
				this.calResultServiceMgr.addSolutionIssue(issue);
			}
			else
			{
				SolutionIssue issue = new SolutionIssue();
				issue.setAccessIssues(YesNo.No);
				issue.setCavityFillUnknown(YesNo.No);
				issue.setFlatRoofInsulationUnknown(YesNo.No);
				issue.setFloorInsulationUnknown(YesNo.No);
				issue.setHighExposure(YesNo.No);
				issue.setMainsGasNeeded(YesNo.No);
				issue.setNarrowCavities(YesNo.No);
				issue.setNoCylinderAccess(YesNo.No);
				issue.setNoLoftAccess(YesNo.No);
				issue.setRoofRoomInsulationUnknown(YesNo.No);
				issue.setSolidWallInsulationUnknown(YesNo.No);
				issue.setSolution(solution);
				issue.setStoneWalls(YesNo.No);
				issue.setSystemBuild(YesNo.No);
				this.calResultServiceMgr.addSolutionIssue(issue);
			}
			//解析数据，并保存结果数据，赋值
			data.put(CalServiceMgr.Cal_Result_Key, calResult);
			data.put(CalServiceMgr.SRC_Cal_Result_List_Key, standardRecommendationCalResults);
			data.put(CalServiceMgr.Fuel_Cal_Result_List_Key, null);
		} catch (DocumentException e)
		{
			e.printStackTrace();
		}
		
		return data;
	}

	@Override
	public Document prepareCal(Report report, List<StandardRecommendationWrap> srsw) throws CalculateException 
	{
		String oldReportCalXmlFile = report.getOldReportCalXmlFile();
		//判断计算原始计算文件是否存在
		
		String calXml = null;
		
		if (StringUtils.isEmpty(oldReportCalXmlFile) || StringUtils.isBlank(oldReportCalXmlFile)
				|| !(new File(GlobalConfig.getInstance().getFSDir() + oldReportCalXmlFile).exists()))
		{
			
			String tmpStr = null;
			Document tmpDocument = DocumentUtil.readDocument(new File(GlobalConfig.getInstance().getFSDir() + report.getReportXmlFile()));
			tmpStr = tmpDocument.asXML();
			RdSAPTrunk rdsap = new RdSAPTrunk(tmpStr, report.getReportLocation().toString(), report.getEpcVersion().getVersionNum());
			calXml = RestClientUtil.post(GlobalConfig.getInstance().getCalWebserviceAddr(), rdsap.toJosn());
			
			CalOutError error = null;
			try {
				error = (CalOutError)JavaBeanFactory.newInstance().createObject(calXml, CalOutError.class);
			} catch (Exception e) {
			}
			
			if(error != null){
				System.out.println(error.getStack());
				throw new CalculateException(error.getMessage());
			}
			String dateDir = GlobalUtils.dateDir(new Date());
			String tmpCalFileName = UUID.randomUUID().toString() + ".xml";
			if (!new File(GlobalConfig.getInstance().getFSDir() + dateDir).exists())
			{
				new File(GlobalConfig.getInstance().getFSDir() + dateDir).mkdirs();
			}
			File tmpCalFile = new File(GlobalConfig.getInstance().getFSDir() + dateDir + tmpCalFileName);
			try
			{
				tmpCalFile.createNewFile();
				PrintWriter pw = new PrintWriter(tmpCalFile);
				pw.write(calXml);
				pw.close();
			} catch (IOException e)
			{
				e.printStackTrace();
				throw new IllegalArgumentException(e);
			}
			
			//插入到DB中
			this.gdsapEvalReportMapper.updateOldReportCalXmlFile(dateDir + tmpCalFileName, report.getId());
		}
		else 
		{
			String tmpPath = GlobalConfig.getInstance().getFSDir() + report.getOldReportCalXmlFile();
			try
			{
				calXml = FileUtils.readFileToString(new File(tmpPath));
			} catch (IOException e)
			{
				e.printStackTrace();
				throw new IllegalArgumentException(e);
			}
		}
		
		Document document;
		document = DocumentUtil.readDocument(calXml);
		
		Element rootElt = document.getRootElement();//获取根节点
		
		//生成 xml GD 对应的元素 Element		
		Element GDElement = rootElt.addElement("GD");//建立节点
		if(report.getPostcode() != null)
		{
			GDElement.addElement("postcode").setText(report.getPostcode());	
		}
		
		if(report.isPrepareCalunoccupiedProperty())
		{
			GDElement.addElement("calculateUnoccupiedProperty").setText("1");
		}
		else
		{
			//Occupants
			Occupants occupants = occupantsServiceMgr.getOccupants(report.getId());
			
			if(occupants != null){
				Element OccupantsElement = GDElement.addElement("Occupants");
				
				Element inspectionDateElement = OccupantsElement.addElement("InspectionDate");
				inspectionDateElement.setText(DateFormatUtils.format(report.getInspectionDate(), "yyyy/MM/dd HH:mm:ss"));
				
				Element NumOfOccupantsElement = OccupantsElement.addElement("NumOfOccupants");
				NumOfOccupantsElement.setText(String.valueOf(occupants.getOccupantsNumber()));
				
				Element ShowerTypeElement = OccupantsElement.addElement("ShowerType");
				ShowerTypeElement.setText(occupants.getShowerType() != null ? String.valueOf((int)occupants.getShowerType().getCalCode()) : "");
				
				Element ShowerPerDayKnownElement = OccupantsElement.addElement("ShowerPerDayKnown");
				ShowerPerDayKnownElement.setText(occupants.getShowersPerable() != null ? String.valueOf(occupants.getShowersPerable().getCode()) : "");
				
				Element ShowerPerDayElement = OccupantsElement.addElement("ShowerPerDay");
				ShowerPerDayElement.setText(String.valueOf(occupants.getShowersPerDay()));
				
				Element BathPerDayKnownElement = OccupantsElement.addElement("BathPerDayKnown");
				BathPerDayKnownElement.setText(occupants.getBathsPerable() != null ? String.valueOf(occupants.getBathsPerable().getCode()) : "");
				
				Element BathPerDayElement = OccupantsElement.addElement("BathPerDay");
				BathPerDayElement.setText(String.valueOf(occupants.getBathsPerDay()));
			}
			
			//HeatingSystems
			HeatingSystem heatingSystem = heatingSystemServiceMgr.getHeatingSystem(report.getId());
			Element HeatingSystemsElement = GDElement.addElement("HeatingSystems");
			if(heatingSystem != null)
			{
					Element LivingAreaTemperatureKnownElement = HeatingSystemsElement.addElement("LivingAreaTemperatureKnown");
					LivingAreaTemperatureKnownElement.setText(heatingSystem.getKnownable() != null ? String.valueOf(heatingSystem.getKnownable().getCode()) : "");
					
							if(heatingSystem.getKnownable() != null && heatingSystem.getKnownable().getCode() != 1){
						Element LivingAreaTemperatureElement = HeatingSystemsElement.addElement("LivingAreaTemperature");
						LivingAreaTemperatureElement.setText(String.valueOf(0));
					}else{
						Element LivingAreaTemperatureElement = HeatingSystemsElement.addElement("LivingAreaTemperature");
						LivingAreaTemperatureElement.setText(String.valueOf(heatingSystem.getTemperature()));
					}
					
					Element DefineMainHeatingSystemElement = HeatingSystemsElement.addElement("DefineMainHeatingSystem");
					DefineMainHeatingSystemElement.setText(heatingSystem.getmHs() != null ? String.valueOf((int)heatingSystem.getmHs().getCalCode()) : "");
					
					if((int)heatingSystem.getmHs().getCalCode() == 1){
					}else{
						Element MainHeatingFuelElement = HeatingSystemsElement.addElement("MainHeatingFuel");
						MainHeatingFuelElement.setText(heatingSystem.getmHf() != null ? String.valueOf((int)heatingSystem.getmHf().getCalCode()) : "");
						
						Element MainHeatingTypeElement = HeatingSystemsElement.addElement("MainHeatingType");
						MainHeatingTypeElement.setText(heatingSystem.getmHt() != null ? String.valueOf((int)heatingSystem.getmHt().getCalCode()) : "");
					}
					
					Element SecDefineMainHeatingSystemElement = HeatingSystemsElement.addElement("SecDefineMainHeatingSystem");
					SecDefineMainHeatingSystemElement.setText(heatingSystem.getSmHs() != null ? String.valueOf((int)heatingSystem.getSmHs().getCalCode()) : "");
					
					if((int)heatingSystem.getSmHs().getCalCode() == 1){
					}else{
						Element SecMainHeatingFuelElement = HeatingSystemsElement.addElement("SecMainHeatingFuel");
						SecMainHeatingFuelElement.setText(heatingSystem.getSmHf() != null ? String.valueOf((int)heatingSystem.getSmHf().getCalCode()) : "");
						
						Element SecMainHeatingTypeElement = HeatingSystemsElement.addElement("SecMainHeatingType");
						SecMainHeatingTypeElement.setText(heatingSystem.getSmHt() != null ? String.valueOf((int)heatingSystem.getSmHt().getCalCode()) : "");
					}
					
					
					Element SecDefineHeatingSystemElement = HeatingSystemsElement.addElement("SecDefineHeatingSystem");
					SecDefineHeatingSystemElement.setText(heatingSystem.getsHs() != null ? String.valueOf((int)heatingSystem.getsHs().getCalCode()) : "");
					
			        if((int)heatingSystem.getsHs().getCalCode() == 0 || (int)heatingSystem.getsHs().getCalCode() == 1){
					}else{
						Element SecHeatingFuelElement = HeatingSystemsElement.addElement("SecHeatingFuel");
						SecHeatingFuelElement.setText(heatingSystem.getsHf() != null ? String.valueOf((int)heatingSystem.getsHf().getCalCode()) : "");
						
						Element SecHeatingTypeElement = HeatingSystemsElement.addElement("SecHeatingType");
						SecHeatingTypeElement.setText(heatingSystem.getsHt() != null ? String.valueOf((int)heatingSystem.getsHt().getCalCode()) : "");
					}
			}
	       

			
			//HeatProportion
			List<HeatProportion> listhp = heatProportionServiceMgr.getHeatProportions(report.getId());
			if(!CollectionUtils.isEmpty(listhp)){
				String tempArr = "";
				for(HeatProportion heatProportion : listhp){
					tempArr += String.valueOf(heatProportion.getMain1().getCode()) + "|" + 
				                             String.valueOf(heatProportion.getMain2().getCode()) + "|" +
				                             String.valueOf(heatProportion.getSecondary().getCode()) + "|" +
				                             String.valueOf(heatProportion.getHeatedPartially().getCode()) + "|" +
				                             String.valueOf(heatProportion.getNotable().getCode()) + "\n";
				}
				Element ProportionOfHeatElement = HeatingSystemsElement.addElement("ProportionOfHeat");
				ProportionOfHeatElement.setText(tempArr);
			}else{
				Element ProportionOfHeatElement = HeatingSystemsElement.addElement("ProportionOfHeat");
				ProportionOfHeatElement.setText("");
			}
			
			//HeatingPattern
			HeatingPattern heatingPattern = heatingPatternServiceMgr.getHeatingPattern(report.getId());
			if(heatingPattern != null){
				String tempArr = "";
				tempArr = heatingPattern.getN1On() + "|" + heatingPattern.getN1Off() + "|" +
						   heatingPattern.getA1On() + "|" + heatingPattern.getA1Off() + "\n" +
						   heatingPattern.getN2On() + "|" + heatingPattern.getN2Off() + "|" +
						   heatingPattern.getA2On() + "|" + heatingPattern.getA2Off() + "\n" +
						   heatingPattern.getN3On() + "|" + heatingPattern.getN3Off() + "|" +
						   heatingPattern.getA3On() + "|" + heatingPattern.getA3Off() + "\n" +
						   heatingPattern.getN4On() + "|" + heatingPattern.getN4Off() + "|" +
						   heatingPattern.getA4On() + "|" + heatingPattern.getA4Off();
				Element HeatingPatternsElement = HeatingSystemsElement.addElement("HeatingPatterns");
				HeatingPatternsElement.setText(tempArr);
				Element NumOfAlternativeDaysElement = HeatingSystemsElement.addElement("NumOfAlternativeDays");
				NumOfAlternativeDaysElement.setText(String.valueOf(heatingPattern.getDays()));
			}else{
				Element HeatingPatternsElement = HeatingSystemsElement.addElement("HeatingPatterns");
				HeatingPatternsElement.setText("");
				Element NumOfAlternativeDaysElement = HeatingSystemsElement.addElement("NumOfAlternativeDays");
				NumOfAlternativeDaysElement.setText("");
			}
			
			//AppCooking
			AppCooking appCooking = appCookingServiceMgr.getAppCooking(report.getId());
			if(appCooking != null){
				Element AppAndCookElement = GDElement.addElement("AppAndCook");
				
				Element ProportionTumbleDryerPerElement = AppAndCookElement.addElement("ProportionTumbleDryerPer");
				ProportionTumbleDryerPerElement.setText(String.valueOf(appCooking.getDryProportion()));
				
				Element IsSpaceAvailableForDryingClothesOutsideElement = AppAndCookElement.addElement("IsSpaceAvailableForDryingClothesOutside");
				IsSpaceAvailableForDryingClothesOutsideElement.setText(appCooking.getDryingClothesSpacable() != null ? String.valueOf(appCooking.getDryingClothesSpacable().getCode()) : "");
				
				Element NumOfFridgeFreezersElement = AppAndCookElement.addElement("NumOfFridgeFreezers");
				NumOfFridgeFreezersElement.setText(String.valueOf(appCooking.getFridgeFreezersNumber()));
				
				Element NumOfStandAloneFridgesElement = AppAndCookElement.addElement("NumOfStandAloneFridges");
				NumOfStandAloneFridgesElement.setText(String.valueOf(appCooking.getFridgesNumber()));
				
				Element NumOfStandAloneFreezersElement = AppAndCookElement.addElement("NumOfStandAloneFreezers");
				NumOfStandAloneFreezersElement.setText(String.valueOf(appCooking.getFreezersNumber()));
				
				Element CookerTypeElement = AppAndCookElement.addElement("CookerType");
				CookerTypeElement.setText(appCooking.getCookerType() != null ? String.valueOf((int)appCooking.getCookerType().getCalCode()) : "");
				
				Element CookerFuelElement = AppAndCookElement.addElement("CookerFuel");
				CookerFuelElement.setText(appCooking.getCookingFuel() != null ? String.valueOf((int)appCooking.getCookingFuel().getCalCode()) : "");
			}
			
			//BillData
			BillDataEle billDataEle = billDataServiceMgr.getBillDataEle(report.getId());
			BillDataComm billDataComm = billDataServiceMgr.getBillDataComm(report.getId());
			BillDataMg billDataMg = billDataServiceMgr.getBillDataMg(report.getId());
			BillDataLPG billDataLPG = billDataServiceMgr.getBillDataLPG(report.getId());
			
			Element BillDataElement = GDElement.addElement("BillData");
			if (billDataEle != null)
			{
				//社区供暖部分
				if (billDataComm != null)
				{
					boolean isPrint = true;
					if(billDataComm.getChReliablityLevel() != null && (int) billDataComm.getChReliablityLevel().getCalCode() == 0)
					{
						isPrint = false;
					}
					
					if(isPrint)
					{
						Element CommunityElement = BillDataElement.addElement("Community");
						if (billDataComm.getChReliablityLevel() != null)
						{
							Element ReliablityLevelElement = CommunityElement.addElement("ReliablityLevel");
							ReliablityLevelElement.setText(billDataComm.getChReliablityLevel() != null ? String.valueOf((int) billDataComm
									.getChReliablityLevel().getCalCode()) : "");
						}
						if (billDataComm.getChFuelCode() != null)
						{
							Element fuelCodeElement = CommunityElement.addElement("FuelCode");
							fuelCodeElement.setText(billDataComm.getChFuelCode().toString());
						}
						if (billDataComm.getChEnergyUsed() != null)
						{
							Element EnergyUsedElement = CommunityElement.addElement("EnergyUsed");
							EnergyUsedElement.setText(billDataComm.getChEnergyUsed() != null ? String.valueOf(billDataComm.getChEnergyUsed()) : "");
						}
						if (billDataComm.getChPeriod() != null)
						{
							Element PeriodElement = CommunityElement.addElement("Period");
							PeriodElement.setText(billDataComm.getChPeriod() != null ? String.valueOf(billDataComm.getChPeriod()) : "");
						}
						if (billDataComm.getChPeriodSelect() != null)
						{
							Element PeriodTypeElement = CommunityElement.addElement("PeriodType");
							PeriodTypeElement.setText(billDataComm.getChPeriodSelect() != null ? String.valueOf((int) billDataComm
									.getChPeriodSelect().getCalCode()) : "");
						}
						if (billDataComm.getChFixedCost() != null)
						{
							Element FixedCostElement = CommunityElement.addElement("FixedCost");
							FixedCostElement.setText(billDataComm.getChFixedCost() != null ? String.valueOf(billDataComm.getChFixedCost()) : "");
						}
						if (billDataComm.getChFixedCostSelected() != null)
						{
							Element FixedCostTypeElement = CommunityElement.addElement("FixedCostType");
							FixedCostTypeElement.setText(billDataComm.getChFixedCostSelected() != null ? String.valueOf((int) billDataComm
									.getChFixedCostSelected().getCalCode()) : "");
						}
						if (billDataComm.getChUnitPrice() != null)
						{
							Element UnitPriceElement = CommunityElement.addElement("UnitPrice");
							UnitPriceElement.setText(billDataComm.getChUnitPrice() != null ? String.valueOf(billDataComm.getChUnitPrice()) : "");
						}
						Element PriceIncludesVATElement = CommunityElement.addElement("PriceIncludesVAT");
						PriceIncludesVATElement.setText(billDataComm.getVatable() != null ? String.valueOf(billDataComm.getVatable().getCode()) : YesNo.No.getCode().toString());
						
						Element coUnusualEnergyPresentKnownElement = CommunityElement.addElement("UnusualEnergyPresentKnown");
						coUnusualEnergyPresentKnownElement.setText(billDataComm.getChUnusualEnergyUsingable() != null ? billDataComm.getChUnusualEnergyUsingable().getCode().toString() : YesNo.No.getCode().toString());
						if (billDataComm.getChUnusualEnergyUsingable() != null
								&& billDataComm.getChUnusualEnergyUsingable().getCode().intValue() == 1)
						{
							Element coUnususlDESElement = CommunityElement.addElement("UnususlDES");
							coUnususlDESElement.setText(billDataComm.getChUnusualEnergyUsingableDes() != null ? billDataComm
									.getChUnusualEnergyUsingableDes() : "");
						}
					}
				}

				// Electricity start...
				boolean stElectricityIsPrint = true;
				boolean hElectricityIsPrint = true;
				boolean lElectricityIsPrint = false;
				boolean pvElectricityIsPrint = false;
				boolean windElectricityIsPrint = false;
				boolean etWindElectricityIsPrint = false;
				boolean etMicroableElectricityIsPrint = false;
				
				if(billDataEle.getEtStReliablityLevel() != null && (int) billDataEle.getEtStReliablityLevel().getCalCode() == 0)
				{
					stElectricityIsPrint = false;
				}
				if(billDataEle.getEtOffHReliablityLevel() != null && (int) billDataEle.getEtOffHReliablityLevel().getCalCode() == 0)
				{
					hElectricityIsPrint = false;
				}
				if(billDataEle.getEtOffLReliablityLevel() != null && billDataEle.getEtOffLReliablityLevel().getCalCode() == 2)
				{
					lElectricityIsPrint = true;
				}
				if (billDataEle.getEtPvable() != null && billDataEle.getEtPvable().getCode().intValue() == 1)
				{
					pvElectricityIsPrint = true;
				}
				if (billDataEle.getEtWindable() != null && billDataEle.getEtWindable().getCode().intValue() == 1)
				{
					etWindElectricityIsPrint = true;
				}
				if (billDataEle.getEtMicroable() != null && billDataEle.getEtMicroable().getCode().intValue() == 1)
				{
					etMicroableElectricityIsPrint = true;
				}
				if(stElectricityIsPrint || hElectricityIsPrint || lElectricityIsPrint || pvElectricityIsPrint || windElectricityIsPrint || etWindElectricityIsPrint || etMicroableElectricityIsPrint)
				{
					Element electricityElement = BillDataElement.addElement("Electricity");
					
					String fuelNumber = "0";
					if(billDataEle != null && (billDataEle.getEtElectricityTariff().getCalCode() == 1 || billDataEle.getEtElectricityTariff().getCalCode()==3)
							&& (billDataEle.getEtStReliablityLevel() != null && billDataEle.getEtStReliablityLevel().getCalCode() != 0))
					{
						fuelNumber = "39";
					}
					electricityElement.addElement("FuelNumber").setText(fuelNumber);
					
					String fuelNumber_H = "0";
					if(billDataEle != null && (billDataEle.getEtElectricityTariff().getCalCode() == 2 && (billDataEle.getEtOffHReliablityLevel().getCalCode() !=0)))
					{
						if(billDataEle.getOffPeakType() != null)
						{
							if(billDataEle.getOffPeakType().getCode() == 2)
							{
								fuelNumber_H = "40";
							}
							if(billDataEle.getOffPeakType().getCode() == 3)
							{
								fuelNumber_H = "44";
							}
						}
						
					}
					electricityElement.addElement("FuelNumber_H").setText(fuelNumber_H);
					
					String fuelNumber_L = "0";
					if(billDataEle.getEtOffLReliablityLevel() != null && billDataEle.getEtOffLReliablityLevel().getCalCode() != 0)
					{
						if(billDataEle.getOffPeakType() != null)
						{
							if(billDataEle.getOffPeakType().getCode() == 2)
							{
								fuelNumber_L = "41";
							}
							if(billDataEle.getOffPeakType().getCode() == 3)
							{
								fuelNumber_L = "45";
							}
						}
					}
					electricityElement.addElement("FuelNumber_L").setText(fuelNumber_L);
					
					if (billDataEle.getEtElectricityTariff() != null)
					{
						Element electricityTariffElement = electricityElement.addElement("ElectricityTariff");
						electricityTariffElement.setText(billDataEle.getEtElectricityTariff() != null ? String.valueOf((int) billDataEle
								.getEtElectricityTariff().getCalCode()) : "");
					}

					if (billDataEle.getEtElectricityTariff() != null && (billDataEle.getEtElectricityTariff().getCalCode() == 1 || billDataEle.getEtElectricityTariff().getCalCode() ==3))
					{
						// 没有高低电价 -- st
						Element reliablityLevelHElement = electricityElement.addElement("ReliablityLevel-H");
						reliablityLevelHElement.setText(billDataEle.getEtStReliablityLevel() != null ? String.valueOf((int) billDataEle
								.getEtStReliablityLevel().getCalCode()) : "");

						Element electricityUsedHElement = electricityElement.addElement("ElectricityUsed-H");
						electricityUsedHElement.setText(billDataEle.getEtStElectricityUsed() != null ? billDataEle.getEtStElectricityUsed().toString() : "");

						Element periodHElement = electricityElement.addElement("Period-H");
						periodHElement.setText(billDataEle.getEtStPeriod() != null ? billDataEle.getEtStPeriod().toString() : "");

						Element periodTypeHElement = electricityElement.addElement("PeriodType-H");
						periodTypeHElement.setText(billDataEle.getEtStPeriodSelect() != null ? String.valueOf((int) billDataEle.getEtStPeriodSelect()
								.getCalCode()) : "1");

						Element priceIncludesVATHElement = electricityElement.addElement("PriceIncludesVAT-H");
						priceIncludesVATHElement.setText(billDataEle.getEtStVatable() != null ? billDataEle.getEtStVatable().getCode().toString()
								: YesNo.No.getCode().toString());

						Element chargingBasisHElement = electricityElement.addElement("ChargingBasis-H");
						chargingBasisHElement.setText(billDataEle.getEtStChargingBasis() != null ? String.valueOf((int) billDataEle
								.getEtStChargingBasis().getCalCode()) : "");
						if (billDataEle.getEtStChargingBasis() != null && (int) billDataEle.getEtStChargingBasis().getCalCode() == 1)
						{
							Element standingChargeAmountHElement = electricityElement.addElement("StandingChargeAmount-H");
							standingChargeAmountHElement.setText(billDataEle.getEtStStandingChargeAmount() != null ? billDataEle
									.getEtStStandingChargeAmount().toString() : "");

							Element standingChargeTypeHElement = electricityElement.addElement("StandingChargeType-H");
							standingChargeTypeHElement.setText(billDataEle.getEtStStandingChargeAmountSelect() != null ? String.valueOf((int) billDataEle
									.getEtStStandingChargeAmountSelect().getCalCode()) : "");

							Element unitPriceHElement = electricityElement.addElement("UnitPrice-H");
							unitPriceHElement.setText(billDataEle.getEtStUnitPrice() != null ? billDataEle.getEtStUnitPrice().toString() : "");
						}
						if (billDataEle.getEtStChargingBasis() != null && (int) billDataEle.getEtStChargingBasis().getCalCode() == 2)
						{
							Element initialUnitPriceHElement = electricityElement.addElement("InitialUnitPrice-H");
							initialUnitPriceHElement.setText(billDataEle.getEtStInitialUnitPrice() != null ? billDataEle.getEtStInitialUnitPrice()
									.toString() : "");

							Element thisUnitPriceHElement = electricityElement.addElement("ThisUnitPrice-H");
							thisUnitPriceHElement.setText(billDataEle.getEtStUnitsAtThisPrice() != null ? billDataEle.getEtStUnitsAtThisPrice()
									.toString() : "");

							Element thisPriceTypeHElement = electricityElement.addElement("ThisPriceType-H");
							thisPriceTypeHElement.setText(billDataEle.getEtStUnitsAtThisPriceSelect() != null ? String.valueOf((int) billDataEle
									.getEtStUnitsAtThisPriceSelect().getCalCode()) : "");

							Element followUnitPriceHElement = electricityElement.addElement("FollowUnitPrice-H");
							followUnitPriceHElement.setText(billDataEle.getEtStFollowonUnitPrice() != null ? billDataEle.getEtStFollowonUnitPrice()
									.toString() : "");
						}
						Element stunusualEnergyPresentKnownHElement = electricityElement.addElement("UnusualEnergyPresentKnown-H");
						stunusualEnergyPresentKnownHElement.setText(billDataEle.getEtStUnusualEnergyUsingable() != null ? billDataEle.getEtStUnusualEnergyUsingable().getCode().toString() : YesNo.No.getCode().toString());
						if (billDataEle.getEtStUnusualEnergyUsingable() != null && billDataEle.getEtStUnusualEnergyUsingable().getCode().intValue() == 1)
						{
							Element unususlDESHElement = electricityElement.addElement("UnususlDES-H");
							unususlDESHElement.setText(billDataEle.getEtStUnusualEnergyUsingableDes());
						}
						
						// 24 电价
						if (billDataEle.getEtElectricityTariff().getCalCode() ==3)
						{
							Element reliablityLevelLElement = electricityElement.addElement("ReliablityLevel-L");
							reliablityLevelLElement.setText(billDataEle.getEt24ReliablityLevel() != null ? String.valueOf((int) billDataEle
									.getEt24ReliablityLevel().getCalCode()) : "");
			
							Element electricityUsedLElement = electricityElement.addElement("ElectricityUsed-L");
							electricityUsedLElement.setText(billDataEle.getEt24ElectricityUsed() != null ? billDataEle.getEt24ElectricityUsed().toString() : "");
			
							Element periodLElement = electricityElement.addElement("Period-L");
							periodLElement.setText(billDataEle.getEt24Period() != null ? billDataEle.getEt24Period().toString() : "");
			
							Element periodTypeLElement = electricityElement.addElement("PeriodType-L");
							periodTypeLElement.setText(billDataEle.getEt24PeriodSelect() != null ? String.valueOf((int) billDataEle.getEt24PeriodSelect()
									.getCalCode()) : "1");
			
							Element priceIncludesVATLElement = electricityElement.addElement("PriceIncludesVAT-L");
							priceIncludesVATLElement.setText(billDataEle.getEt24Vatable() != null ? billDataEle.getEt24Vatable().getCode().toString()
									: YesNo.No.getCode().toString());
			
							Element chargingBasisLElement = electricityElement.addElement("ChargingBasis-L");
							chargingBasisLElement.setText(billDataEle.getEt24ChargingBasis() != null ? String.valueOf((int) billDataEle
									.getEt24ChargingBasis().getCalCode()) : "");
							if (billDataEle.getEt24ChargingBasis() != null && (int) billDataEle.getEt24ChargingBasis().getCalCode() == 1)
							{
								Element standingChargeAmountLElement = electricityElement.addElement("StandingChargeAmount-L");
								standingChargeAmountLElement.setText(billDataEle.getEt24StandingChargeAmount() != null ? billDataEle
										.getEt24StandingChargeAmount().toString() : "");
			
								Element standingChargeTypeLElement = electricityElement.addElement("StandingChargeType-L");
								standingChargeTypeLElement.setText(billDataEle.getEt24StandingChargeAmountSelect() != null ? String.valueOf((int) billDataEle
										.getEt24StandingChargeAmountSelect().getCalCode()) : "");
			
								Element unitPriceLElement = electricityElement.addElement("UnitPrice-L");
								unitPriceLElement.setText(billDataEle.getEt24UnitPrice() != null ? billDataEle.getEt24UnitPrice().toString() : "");
							}
							if (billDataEle.getEt24ChargingBasis() != null && (int) billDataEle.getEt24ChargingBasis().getCalCode() == 2)
							{
								Element initialUnitPriceLElement = electricityElement.addElement("InitialUnitPrice-L");
								initialUnitPriceLElement.setText(billDataEle.getEt24InitialUnitPrice() != null ? billDataEle.getEt24InitialUnitPrice()
										.toString() : "");
			
								Element thisUnitPriceLElement = electricityElement.addElement("ThisUnitPrice-L");
								thisUnitPriceLElement.setText(billDataEle.getEt24UnitsAtThisPrice() != null ? billDataEle.getEt24UnitsAtThisPrice()
										.toString() : "");
			
								Element thisPriceTypeLElement = electricityElement.addElement("ThisPriceType-L");
								thisPriceTypeLElement.setText(billDataEle.getEt24UnitsAtThisPriceSelect() != null ? String.valueOf((int) billDataEle
										.getEt24UnitsAtThisPriceSelect().getCalCode()) : "");
			
								Element followUnitPriceLElement = electricityElement.addElement("FollowUnitPrice-L");
								followUnitPriceLElement.setText(billDataEle.getEt24FollowonUnitPrice() != null ? billDataEle.getEt24FollowonUnitPrice()
										.toString() : "");
							}
							Element stunusualEnergyPresentKnownLElement = electricityElement.addElement("UnusualEnergyPresentKnown-L");
							stunusualEnergyPresentKnownLElement.setText(billDataEle.getEt24UnusualEnergyUsingable() != null ? billDataEle.getEt24UnusualEnergyUsingable().getCode().toString() : YesNo.No.getCode().toString());
							if (billDataEle.getEt24UnusualEnergyUsingable() != null && billDataEle.getEt24UnusualEnergyUsingable().getCode().intValue() == 1)
							{
								Element unususlDESLElement = electricityElement.addElement("UnususlDES-L");
								unususlDESLElement.setText(billDataEle.getEt24UnusualEnergyUsingableDes());
							}
						}
					} else
					{
						//高低电价--高电价
						Element reliablityLevelHElement = electricityElement.addElement("ReliablityLevel-H");
						reliablityLevelHElement.setText(billDataEle.getEtOffHReliablityLevel() != null ? String.valueOf((int) billDataEle
								.getEtOffHReliablityLevel().getCalCode()) : "");

						Element electricityUsedHElement = electricityElement.addElement("ElectricityUsed-H");
						electricityUsedHElement.setText(billDataEle.getEtOffHElectricityUsed() != null ? billDataEle.getEtOffHElectricityUsed()
								.toString() : "");

						Element periodHElement = electricityElement.addElement("Period-H");
						periodHElement.setText(billDataEle.getEtOffHPeriod() != null ? billDataEle.getEtOffHPeriod().toString() : "");

						Element periodTypeHElement = electricityElement.addElement("PeriodType-H");
						periodTypeHElement.setText(billDataEle.getEtOffHPeriodSelect() != null ? String.valueOf((int) billDataEle.getEtOffHPeriodSelect()
								.getCalCode()) : "1");

						Element priceIncludesVATHElement = electricityElement.addElement("PriceIncludesVAT-H");
						priceIncludesVATHElement.setText(billDataEle.getEtOffHVatable() != null ? billDataEle.getEtOffHVatable().getCode().toString()
								: YesNo.No.getCode().toString());

						Element chargingBasisHElement = electricityElement.addElement("ChargingBasis-H");
						chargingBasisHElement.setText(billDataEle.getEtOffHChargingBasis() != null ? String.valueOf((int) billDataEle
								.getEtOffHChargingBasis().getCalCode()) : "");
						if (billDataEle.getEtOffHChargingBasis() != null && (int) billDataEle.getEtOffHChargingBasis().getCalCode() == 1)
						{
							Element standingChargeAmountHElement = electricityElement.addElement("StandingChargeAmount-H");
							standingChargeAmountHElement.setText(billDataEle.getEtOffHStandingChargeAmount() != null ? billDataEle
									.getEtOffHStandingChargeAmount().toString() : "");

							Element standingChargeTypeHElement = electricityElement.addElement("StandingChargeType-H");
							standingChargeTypeHElement.setText(billDataEle.getEtOffHStandingChargeAmountSelect() != null ? String
									.valueOf((int) billDataEle.getEtOffHStandingChargeAmountSelect().getCalCode()) : "");

							Element unitPriceHElement = electricityElement.addElement("UnitPrice-H");
							unitPriceHElement.setText(billDataEle.getEtOffHUnitPrice() != null ? billDataEle.getEtOffHUnitPrice().toString() : "");
						}
						if (billDataEle.getEtOffHChargingBasis() != null && (int) billDataEle.getEtOffHChargingBasis().getCalCode() == 2)
						{
							Element initialUnitPriceHElement = electricityElement.addElement("InitialUnitPrice-H");
							initialUnitPriceHElement.setText(billDataEle.getEtOffHInitialUnitAmount() != null ? billDataEle.getEtOffHInitialUnitAmount()
									.toString() : "");

							Element thisUnitPriceHElement = electricityElement.addElement("ThisUnitPrice-H");
							thisUnitPriceHElement.setText(billDataEle.getEtOffHUnitsAtThisPrice() != null ? billDataEle.getEtOffHUnitsAtThisPrice()
									.toString() : "");

							Element thisPriceTypeHElement = electricityElement.addElement("ThisPriceType-H");
							thisPriceTypeHElement.setText(billDataEle.getEtOffHUnitsAtThisPriceSelect() != null ? String.valueOf((int) billDataEle.getEtOffHUnitsAtThisPriceSelect().getCalCode()) : "");

							Element followUnitPriceHElement = electricityElement.addElement("FollowUnitPrice-H");
							followUnitPriceHElement.setText(billDataEle.getEtOffHFollow() != null ? billDataEle.getEtOffHFollow().toString() : "");
						}
						Element stunusualEnergyPresentKnownHElement = electricityElement.addElement("UnusualEnergyPresentKnown-H");
						stunusualEnergyPresentKnownHElement.setText(billDataEle.getEtOffHUnusualEnergyUsingable() != null ? billDataEle.getEtOffHUnusualEnergyUsingable().getCode().toString() : YesNo.No.getCode().toString());
						if (billDataEle.getEtOffHUnusualEnergyUsingable() != null && billDataEle.getEtOffHUnusualEnergyUsingable().getCode().intValue() == 1)
						{
							Element unususlDESHElement = electricityElement.addElement("UnususlDES-H");
							unususlDESHElement.setText(billDataEle.getEtOffHUnusualEnergyUsingableDes());
						}
					}

					if (billDataEle.getEtOffLReliablityLevel() != null && billDataEle.getEtOffLReliablityLevel().getCalCode() == 2)
					{
						Element reliablityLevelLElement = electricityElement.addElement("ReliablityLevel-L");
						reliablityLevelLElement.setText(billDataEle.getEtOffLReliablityLevel() != null ? String.valueOf((int) billDataEle
								.getEtOffLReliablityLevel().getCalCode()) : "");

						Element electricityUsedLElement = electricityElement.addElement("ElectricityUsed-L");
						electricityUsedLElement.setText(billDataEle.getEtOffLElectricityUsed() != null ? billDataEle.getEtOffLElectricityUsed()
								.toString() : "");

						Element periodLElement = electricityElement.addElement("Period-L");
						periodLElement.setText(billDataEle.getEtOffLPeriod() != null ? billDataEle.getEtOffLPeriod().toString() : "");

						Element periodTypeLElement = electricityElement.addElement("PeriodType-L");
						periodTypeLElement.setText(billDataEle.getEtOffLPeriodSelect() != null ? String.valueOf((int) billDataEle.getEtOffLPeriodSelect()
								.getCalCode()) : "1");

						Element priceIncludesVATLElement = electricityElement.addElement("PriceIncludesVAT-L");
						priceIncludesVATLElement.setText(billDataEle.getEtOffLVatable() != null ? billDataEle.getEtOffLVatable().getCode().toString()
								: YesNo.No.getCode().toString());

						Element chargingBasisLElement = electricityElement.addElement("ChargingBasis-L");
						chargingBasisLElement.setText(billDataEle.getEtOffLChargingBasis() != null ? String.valueOf((int) billDataEle.getEtOffLChargingBasis().getCalCode()) : "");
						if (billDataEle.getEtOffLChargingBasis() != null && (int) billDataEle.getEtOffLChargingBasis().getCalCode() == 1)
						{
							Element standingChargeAmountLElement = electricityElement.addElement("StandingChargeAmount-L");
							standingChargeAmountLElement.setText(billDataEle.getEtOffLStandingChargeAmount() != null ? billDataEle.getEtOffLStandingChargeAmount().toString() : "");

							Element standingChargeTypeLElement = electricityElement.addElement("StandingChargeType-L");
							standingChargeTypeLElement.setText(billDataEle.getEtOffLStandingChargeAmountSelect() != null ? String
									.valueOf((int) billDataEle.getEtOffLStandingChargeAmountSelect().getCalCode()) : "");

							Element unitPriceLElement = electricityElement.addElement("UnitPrice-L");
							unitPriceLElement.setText(billDataEle.getEtOffLUnitPrice() != null ? billDataEle.getEtOffLUnitPrice().toString() : "");
						}
						if (billDataEle.getEtOffLChargingBasis() != null && (int) billDataEle.getEtOffLChargingBasis().getCalCode() == 2)
						{
							Element initialUnitPriceLElement = electricityElement.addElement("InitialUnitPrice-L");
							initialUnitPriceLElement.setText(billDataEle.getEtOffLInitialUnitAmount() != null ? billDataEle.getEtOffLInitialUnitAmount()
									.toString() : "");

							Element thisUnitPriceLElement = electricityElement.addElement("ThisUnitPrice-L");
							thisUnitPriceLElement.setText(billDataEle.getEtOffLUnitsAtThisPrice() != null ? billDataEle.getEtOffLUnitsAtThisPrice()
									.toString() : "");

							Element thisPriceTypeLElement = electricityElement.addElement("ThisPriceType-L");
							thisPriceTypeLElement.setText(billDataEle.getEtOffLUnitsAtThisPriceSelect() != null ? String.valueOf((int) billDataEle
									.getEtOffLUnitsAtThisPriceSelect().getCalCode()) : "");

							Element followUnitPriceLElement = electricityElement.addElement("FollowUnitPrice-L");
							followUnitPriceLElement.setText(billDataEle.getEtOffLFollow() != null ? billDataEle.getEtOffLFollow().toString() : "");
						}
						Element lunusualEnergyPresentKnownHElement = electricityElement.addElement("UnusualEnergyPresentKnown-L");
						lunusualEnergyPresentKnownHElement.setText(billDataEle.getEtOffLUnusualEnergyUsingable() != null ? billDataEle.getEtOffLUnusualEnergyUsingable().getCode().toString() : YesNo.No.getCode().toString());
						if (billDataEle.getEtOffLUnusualEnergyUsingable() != null && billDataEle.getEtOffLUnusualEnergyUsingable().getCode().intValue() == 1)
						{
							Element lususlDESHElement = electricityElement.addElement("UnususlDES-L");
							lususlDESHElement.setText(billDataEle.getEtOffLUnusualEnergyUsingableDes());
						}
					}

					Element generatedPVElement = electricityElement.addElement("Generated-PV");
					generatedPVElement.setText(billDataEle.getEtPvable() != null ? billDataEle.getEtPvable().getCode().toString() : YesNo.No.getCode()
							.toString());

					if (billDataEle.getEtPvable() != null && billDataEle.getEtPvable().getCode().intValue() == 1)
					{
						Element amountPVElement = electricityElement.addElement("Amount-PV");
						amountPVElement.setText(billDataEle.getEtPvAmount() != null ? billDataEle.getEtPvAmount().toString() : "");

						Element periodPVElement = electricityElement.addElement("Period-PV");
						periodPVElement.setText(billDataEle.getEtPvPeriod() != null ? billDataEle.getEtPvPeriod().toString() : "");

						Element periodTypePVElement = electricityElement.addElement("PeriodType-PV");
						periodTypePVElement.setText(billDataEle.getEtStPeriodSelect() != null ? String.valueOf((int) billDataEle.getEtStPeriodSelect()
								.getCalCode()) : "");
					}

					Element generatedWindElement = electricityElement.addElement("Generated-Wind");
					generatedWindElement.setText(billDataEle.getEtWindable() != null ? billDataEle.getEtWindable().getCode().toString() : YesNo.No
							.getCode().toString());

					if (billDataEle.getEtWindable() != null && billDataEle.getEtWindable().getCode().intValue() == 1)
					{
						Element amountWindElement = electricityElement.addElement("Amount-Wind");
						amountWindElement.setText(billDataEle.getEtWindAmount() != null ? billDataEle.getEtWindAmount().toString() : "");

						Element periodWindElement = electricityElement.addElement("Period-Wind");
						periodWindElement.setText(billDataEle.getEtWindPeriod() != null ? billDataEle.getEtWindPeriod().toString() : "");

						Element periodTypeWindElement = electricityElement.addElement("PeriodType-Wind");
						periodTypeWindElement.setText(billDataEle.getEtWindPeriodSelect() != null ? String.valueOf((int) billDataEle.getEtWindPeriodSelect().getCalCode()) : "");
					}

					Element generatedCHPElement = electricityElement.addElement("Generated-CHP");
					generatedCHPElement.setText(billDataEle.getEtMicroable() != null ? billDataEle.getEtMicroable().getCode().toString() : YesNo.No
							.getCode().toString());

					if (billDataEle.getEtMicroable() != null && billDataEle.getEtMicroable().getCode().intValue() == 1)
					{
						Element amountCHPElement = electricityElement.addElement("Amount-CHP");
						amountCHPElement.setText(billDataEle.getEtMicroableAmount() != null ? billDataEle.getEtMicroableAmount().toString() : "");

						Element periodCHPElement = electricityElement.addElement("Period-CHP");
						periodCHPElement.setText(billDataEle.getEtMicroablePeriod() != null ? billDataEle.getEtMicroablePeriod().toString() : "");

						Element periodTypeCHPElement = electricityElement.addElement("PeriodType-CHP");
						periodTypeCHPElement.setText(billDataEle.getEtMicroablePeriodSelect() != null ? String.valueOf((int) billDataEle.getEtMicroablePeriodSelect().getCalCode()) : "");
					}
				}
				// Electricity end...
				
				// MainGas start...
				if (billDataMg != null)
				{
					boolean isPrint = true;
					if(billDataMg.getMgReliablityLevel() != null && (int) billDataMg.getMgReliablityLevel().getCalCode() == 0)
					{
						isPrint = false;
					}
					if(isPrint)
					{
						Element MainGasTariffElement = BillDataElement.addElement("MainGasTariff");

						if (billDataMg.getMgReliablityLevel() != null)
						{
							Element MGReliablityLevelElement = MainGasTariffElement.addElement("ReliablityLevel");
							MGReliablityLevelElement.setText(billDataMg.getMgReliablityLevel() != null ? String.valueOf((int) billDataMg
									.getMgReliablityLevel().getCalCode()) : "");
						}
						if (billDataMg.getMgGasUsed() != null)
						{
							Element GasUsedElement = MainGasTariffElement.addElement("GasUsed");
							GasUsedElement.setText(billDataMg.getMgGasUsed() != null ? String.valueOf(billDataMg.getMgGasUsed()) : "");
						}
						if (billDataMg.getMgPeriod() != null)
						{
							Element MGPeriodElement = MainGasTariffElement.addElement("Period");
							MGPeriodElement.setText(billDataMg.getMgPeriod() != null ? String.valueOf(billDataMg.getMgPeriod()) : "");
						}
						if (billDataMg.getMgPeriodSelect() != null)
						{
							Element MGPeriodTypeElement = MainGasTariffElement.addElement("PeriodType");
							MGPeriodTypeElement.setText(billDataMg.getMgPeriodSelect() != null ? String.valueOf((int) billDataMg.getMgPeriodSelect()
									.getCalCode()) : "");
						}
						if (billDataMg.getMgChargingBasis() != null)
						{
							Element ChargingBasisElement = MainGasTariffElement.addElement("ChargingBasis");
							ChargingBasisElement.setText(billDataMg.getMgChargingBasis() != null ? String.valueOf((int) billDataMg.getMgChargingBasis()
									.getCalCode()) : "");
						}
						if (billDataMg.getMgStAmount() != null)
						{
							Element StandingChargeAmountElement = MainGasTariffElement.addElement("StandingChargeAmount");
							StandingChargeAmountElement.setText(billDataMg.getMgStAmount() != null ? String.valueOf(billDataMg.getMgStAmount()) : "");
						}
						if (billDataMg.getMgStAmountSelect() != null)
						{
							Element StandingChargeTypeElement = MainGasTariffElement.addElement("StandingChargeType");
							StandingChargeTypeElement.setText(billDataMg.getMgStAmountSelect() != null ? String.valueOf((int) billDataMg
									.getMgStAmountSelect().getCalCode()) : "");
						}
						if (billDataMg.getMgStUnitPrice() != null)
						{
							Element MGUnitPriceElement = MainGasTariffElement.addElement("UnitPrice");
							MGUnitPriceElement.setText(billDataMg.getMgStUnitPrice() != null ? String.valueOf(billDataMg.getMgStUnitPrice()) : "");
						}
						if (billDataMg.getMgTwInitialUnit() != null)
						{
							Element InitialUnitPriceElement = MainGasTariffElement.addElement("InitialUnitPrice");
							InitialUnitPriceElement.setText(billDataMg.getMgTwInitialUnit() != null ? String.valueOf(billDataMg.getMgTwInitialUnit())
									: "");
						}
						if (billDataMg.getMgTwUnits() != null)
						{
							Element UnitThisPriceElement = MainGasTariffElement.addElement("UnitThisPrice");
							UnitThisPriceElement.setText(billDataMg.getMgTwUnits() != null ? String.valueOf(billDataMg.getMgTwUnits()) : "");
						}
						if (billDataMg.getMgTwUnitsSelected() != null)
						{
							Element UnitThisPriceTypeElement = MainGasTariffElement.addElement("UnitThisPriceType");
							UnitThisPriceTypeElement.setText(billDataMg.getMgTwUnitsSelected() != null ? String.valueOf((int) billDataMg
									.getMgTwUnitsSelected().getCalCode()) : "");
						}
						if (billDataMg.getMgTwFollowOn() != null)
						{
							Element FollowUnitPriceElement = MainGasTariffElement.addElement("FollowUnitPrice");
							FollowUnitPriceElement.setText(billDataMg.getMgTwFollowOn() != null ? String.valueOf(billDataMg.getMgTwFollowOn()) : "");
						}
						
						Element MGPriceIncludesVATElement = MainGasTariffElement.addElement("PriceIncludesVAT");
						MGPriceIncludesVATElement.setText(billDataMg.getMgVatAble() != null ? String.valueOf(billDataMg.getMgVatAble().getCode()) : YesNo.No.getCode().toString());
						
						Element mgUnusualEnergyPresentKnownElement = MainGasTariffElement.addElement("UnusualEnergyPresentKnown");
						mgUnusualEnergyPresentKnownElement.setText(billDataMg.getMgUnusualEnergyUsingable() != null ? billDataMg.getMgUnusualEnergyUsingable().getCode().toString() : YesNo.No.getCode().toString());
						if (billDataMg.getMgUnusualEnergyUsingable() != null)
						{
							Element mgUnususlDESElement = MainGasTariffElement.addElement("UnususlDES");
							mgUnususlDESElement.setText(billDataMg.getMgUnusualEnergyUsingableDes());
						}
					}
				}
				// MainGas end...
				
				// LPG start...
				if (billDataLPG != null)
				{
					boolean isPrint = true;
					if(billDataLPG.getLpgReliablityLevel() != null &&(int) billDataLPG.getLpgReliablityLevel().getCalCode() == 0)
					{
						isPrint = false;
					}
					
					if(isPrint)
					{
						Element LPGTariffElement = BillDataElement.addElement("LPGGasTariff");

						if (billDataLPG.getLpgReliablityLevel() != null)
						{
							Element MGReliablityLevelElement = LPGTariffElement.addElement("ReliablityLevel");
							MGReliablityLevelElement.setText(billDataLPG.getLpgReliablityLevel() != null ? String.valueOf((int) billDataLPG
									.getLpgReliablityLevel().getCalCode()) : "");
						}
						if (billDataLPG.getLpgGasUsed() != null)
						{
							Element GasUsedElement = LPGTariffElement.addElement("GasUsed");
							GasUsedElement.setText(billDataLPG.getLpgGasUsed() != null ? String.valueOf(billDataLPG.getLpgGasUsed()) : "");
						}
						if (billDataLPG.getLpgPeriod() != null)
						{
							Element MGPeriodElement = LPGTariffElement.addElement("Period");
							MGPeriodElement.setText(billDataLPG.getLpgPeriod() != null ? String.valueOf(billDataLPG.getLpgPeriod()) : "");
						}
						if (billDataLPG.getLpgPeriodSelect() != null)
						{
							Element MGPeriodTypeElement = LPGTariffElement.addElement("PeriodType");
							MGPeriodTypeElement.setText(billDataLPG.getLpgPeriodSelect() != null ? String.valueOf((int) billDataLPG.getLpgPeriodSelect()
									.getCalCode()) : "");
						}
						if (billDataLPG.getLpgChargingBasis() != null)
						{
							Element ChargingBasisElement = LPGTariffElement.addElement("ChargingBasis");
							ChargingBasisElement.setText(billDataLPG.getLpgChargingBasis() != null ? String.valueOf((int) billDataLPG.getLpgChargingBasis()
									.getCalCode()) : "");
						}
						if (billDataLPG.getLpgStAmount() != null)
						{
							Element StandingChargeAmountElement = LPGTariffElement.addElement("StandingChargeAmount");
							StandingChargeAmountElement.setText(billDataLPG.getLpgStAmount() != null ? String.valueOf(billDataLPG.getLpgStAmount()) : "");
						}
						if (billDataLPG.getLpgStAmountSelect() != null)
						{
							Element StandingChargeTypeElement = LPGTariffElement.addElement("StandingChargeType");
							StandingChargeTypeElement.setText(billDataLPG.getLpgStAmountSelect() != null ? String.valueOf((int) billDataLPG
									.getLpgStAmountSelect().getCalCode()) : "");
						}
						if (billDataLPG.getLpgStUnitPrice() != null)
						{
							Element MGUnitPriceElement = LPGTariffElement.addElement("UnitPrice");
							MGUnitPriceElement.setText(billDataLPG.getLpgStUnitPrice() != null ? String.valueOf(billDataLPG.getLpgStUnitPrice()) : "");
						}
						if (billDataLPG.getLpgTwInitialUnit() != null)
						{
							Element InitialUnitPriceElement = LPGTariffElement.addElement("InitialUnitPrice");
							InitialUnitPriceElement.setText(billDataLPG.getLpgTwInitialUnit() != null ? String.valueOf(billDataLPG.getLpgTwInitialUnit())
									: "");
						}
						if (billDataLPG.getLpgTwUnits() != null)
						{
							Element UnitThisPriceElement = LPGTariffElement.addElement("UnitThisPrice");
							UnitThisPriceElement.setText(billDataLPG.getLpgTwUnits() != null ? String.valueOf(billDataLPG.getLpgTwUnits()) : "");// 待确定
						}
						if (billDataLPG.getLpgTwUnitsSelected() != null)
						{
							Element UnitThisPriceTypeElement = LPGTariffElement.addElement("UnitThisPriceType");
							UnitThisPriceTypeElement.setText(billDataLPG.getLpgTwUnitsSelected() != null ? String.valueOf((int) billDataLPG
									.getLpgTwUnitsSelected().getCalCode()) : "");// 待确定
						}
						if (billDataLPG.getLpgTwFollowOn() != null)
						{
							Element FollowUnitPriceElement = LPGTariffElement.addElement("FollowUnitPrice");
							FollowUnitPriceElement.setText(billDataLPG.getLpgTwFollowOn() != null ? String.valueOf(billDataLPG.getLpgTwFollowOn()) : "");
						}
						
						Element MGPriceIncludesVATElement = LPGTariffElement.addElement("PriceIncludesVAT");
						MGPriceIncludesVATElement.setText(billDataLPG.getLpgVatAble() != null ? String.valueOf(billDataLPG.getLpgVatAble().getCode()) : YesNo.No.getCode().toString());
						
						Element mgUnusualEnergyPresentKnownElement = LPGTariffElement.addElement("UnusualEnergyPresentKnown");
						mgUnusualEnergyPresentKnownElement.setText(billDataLPG.getLpgUnusualEnergyUsingable() != null ? billDataLPG.getLpgUnusualEnergyUsingable().getCode().toString() : YesNo.No.getCode().toString());
						if (billDataLPG.getLpgUnusualEnergyUsingable() != null)
						{
							Element mgUnususlDESElement = LPGTariffElement.addElement("UnususlDES");
							mgUnususlDESElement.setText(billDataLPG.getLpgUnusualEnergyUsingableDes());
						}
					}
				}
			}
			// LPG end...
			
			//OtherFuels start...
			List<OtherFuel> listo = otherFuelServiceMgr.getOtherFuelsByReportId(report.getId());
			boolean isPrint = false;
			if(!CollectionUtils.isEmpty(listo))
			{
				for(OtherFuel otherFuel : listo)
				{
					if(otherFuel.getReliablityLevel() != null && (int)otherFuel.getReliablityLevel().getCalCode() != 0)
					{
						isPrint = true;
						break;
					}
				}
			}
			
			if(isPrint)
			{
				Element OtherFuelsElement = BillDataElement.addElement("OtherFuels");
				for(OtherFuel otherFuel : listo){
	                Element OtherFuelElement = OtherFuelsElement.addElement("OtherFuel");
					
					Element FuelCodeElement = OtherFuelElement.addElement("FuelCode");
					FuelCodeElement.setText(otherFuel.getFuelCode() != null ? String.valueOf((int)otherFuel.getFuelCode())  : "");
					
					Element OFReliablityLevelElement = OtherFuelElement.addElement("ReliablityLevel");
					OFReliablityLevelElement.setText(otherFuel.getReliablityLevel() != null ? String.valueOf((int)otherFuel.getReliablityLevel().getCalCode()) : "");
					
					Element OFFixedCostElement = OtherFuelElement.addElement("FixedCost");
					OFFixedCostElement.setText(String.valueOf(otherFuel.getFixedCost()));
					
					Element fixedCostTypeElement = OtherFuelElement.addElement("FixedCostType");
					fixedCostTypeElement.setText(otherFuel.getFixedCostSelected() != null ? String.valueOf((int)otherFuel.getFixedCostSelected().getCalCode()) : "");
					
					Element UnitOfSaleElement = OtherFuelElement.addElement("UnitOfSale");
					UnitOfSaleElement.setText(otherFuel.getUnitOfSale() != null ? String.valueOf((int)otherFuel.getUnitOfSale().getCalCode()) : "");
					
					Element OFUnitPriceElement = OtherFuelElement.addElement("UnitPrice");
					OFUnitPriceElement.setText(String.valueOf(otherFuel.getUnitPrice()));
					
					Element NumOfUnitsPurchasedElement = OtherFuelElement.addElement("NumOfUnitsPurchased");
					NumOfUnitsPurchasedElement.setText(String.valueOf(otherFuel.getUnitsPurchasedNumber()));
					
					Element TotalCostElement = OtherFuelElement.addElement("TotalCost");
					TotalCostElement.setText(String.valueOf(otherFuel.getTotalCost()));
					
					Element OFPeriodElement = OtherFuelElement.addElement("Period");
					OFPeriodElement.setText(String.valueOf(otherFuel.getPeriod()));
					
					Element OFPeriodTypeElement = OtherFuelElement.addElement("PeriodType");
					OFPeriodTypeElement.setText(otherFuel.getPeriodSelect() != null ? String.valueOf((int)otherFuel.getPeriodSelect().getCalCode()) : "");
					
					Element OFPriceIncludesVATElement = OtherFuelElement.addElement("PriceIncludesVAT");
					OFPriceIncludesVATElement.setText(otherFuel.getVatable() != null ? String.valueOf(otherFuel.getVatable().getCode()) : YesNo.No.getCode().toString());
					
					Element oFunusualEnergyPresentKnownElement = OtherFuelElement.addElement("UnusualEnergyPresentKnown");
					oFunusualEnergyPresentKnownElement.setText(otherFuel.getUnusualEnergyUsingable() != null ? String.valueOf(otherFuel.getUnusualEnergyUsingable().getCode()) : YesNo.No.getCode().toString());
					if(otherFuel.getUnusualEnergyUsingable() != null && otherFuel.getUnusualEnergyUsingable().getCode().intValue() == 1){
						Element oFUnususlDESElement = OtherFuelElement.addElement("UnususlDES");
						oFUnususlDESElement.setText(otherFuel.getUnusualEnergyUsingableDes() != null ? otherFuel.getUnusualEnergyUsingableDes() : "");
					}
				}
			}
			//OtherFuels end...
			
			Element unoccupiedPropertyElement = GDElement.addElement("UnoccupiedProperty");
			unoccupiedPropertyElement.setText(report.getUnoccupiedPropertyable() != null ? report.getUnoccupiedPropertyable().getCode().toString() : YesNo.No.getCode().toString());
			//Selected-Improvements
			if(!CollectionUtils.isEmpty(srsw)){
				SolutionType type = srsw.get(0).getSolutionType();
				Element SelectedImprovementsElement = null;
				if(SolutionType.GDAR.equals(type))
				{
					SelectedImprovementsElement = GDElement.addElement("Selected-Improvements");
					
				}
				else if(SolutionType.GDIP.equals(type))//GDIP Selected-Improvements
				{
					Element gdipExtensionElement = GDElement.addElement("gdipExtension").addElement("Unoccupied-Property");
					gdipExtensionElement.setText((report.getUnoccupiedPropertyable() != null && report.getUnoccupiedPropertyable().equals(YesNo.Yes)) ? "true" : "false");
					
					SelectedImprovementsElement = GDElement.addElement("GDIP-Selected-Improvements");
				}
				
				for(StandardRecommendationWrap srw : srsw){
					
					Element ImprovementElement = SelectedImprovementsElement.addElement("Improvement");
					ImprovementElement.addElement("improvementLetter").addText(srw.getStandardRecommendation().getItem());
					
					Map<StandardOption, Integer> options = srw.getSelectedOptionItems();
					if(!CollectionUtils.isEmpty(options)){
						Set<StandardOption> soSet = options.keySet();
						for (StandardOption so : soSet)
						{
							Element optionElement = ImprovementElement.addElement("Option");
							optionElement.addElement("value").setText(so.getLis().get(options.get(so)));
							optionElement.addElement("name").setText(so.getCode());
						}
					}
					
					Map<StandardValue, String> u_values = srw.getSelectedInputValues();
					if(!CollectionUtils.isEmpty(u_values)){
						Set<StandardValue> soSet = u_values.keySet();
						for (StandardValue so : soSet)
						{
							Element optionElement = ImprovementElement.addElement("Option");
							optionElement.addElement("value").setText(so.getInputValue().toString());
							optionElement.addElement("name").setText(so.getName());
						}
					}
				}
			}
		}
		//生成计算文件end
		
		return document;
	}
	
	@Override
	public String cal_ReturnResult(Report report, List<StandardRecommendationWrap> srsw, SolutionType calculateType) throws CalculateException 
	{
		SolutionType solutionType = SolutionType.GDAR; //增加solutionType, 为了解决GDAR和GDIP计算是用的PDCF版本不一致问题
		if(calculateType != null)
		{
			solutionType = calculateType;
		}
		Document calDocument = this.prepareCal(report, srsw);
		OATrunk oaTrunk = new OATrunk(null, calDocument.asXML(), null, report.getReportLocation().toString(), report.getEpcVersion().getVersionNum());
		// TODO ZP test , delete
		String folderPath = CL + "var" + CL + "www" + CL + "gdoa992" + CL + "qube_cal_files" + CL;
		if(getOSname().equals("windows")){
			folderPath = "D:" + CL + "gdsap" + CL + "upload" + CL + "qube_cal_files" + CL;
		}
		
		File file = new File(folderPath);
		if(!file.exists()){
			file.mkdirs();
		}
		
		String calXmlFileName = report.getRrn() + ".xml";
		String saveUri = folderPath + calXmlFileName;
		
		String requestJsonFileName = report.getRrn() + "_requestJson.txt";
		String saverequestJsonFileUri = folderPath + requestJsonFileName;
		
		File calXmlFile = new File(saveUri);
		if(calXmlFile.exists()){
			calXmlFile.delete();
		}
		
		try {
			this.writeDocToXMLFile(calDocument, saveUri, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileUtil.setFileContent(saverequestJsonFileUri, oaTrunk.toJosn());
		// TODO ZP test , delete
				
		//调用C# webservice进行计算，返回计算结果xml文件
		String requestUrl = GlobalConfig.getInstance().getCalResultXmlWebserviceAddr();
		if(solutionType != null && solutionType.equals(SolutionType.GDIP))
		{
			requestUrl = GlobalConfig.getInstance().getGDIPCalResultXmlWebserviceAddr();
		}
		String calResult = RestClientUtil.post(requestUrl, oaTrunk.toJosn());
		
		// TODO ZP test , delete
		String resultFileName = report.getRrn() + "_result.xml";
		String saveresultFileNameUri = folderPath + resultFileName;
		FileUtil.setFileContent(saveresultFileNameUri, calResult);
		// TODO ZP test , delete
		
		CalOutError error = null;
		try {
			error = (CalOutError)JavaBeanFactory.newInstance().createObject(calResult, CalOutError.class);
		} catch (Exception e) {
		}
		if(error != null){
			System.out.println(error.getStack());
			String errorMessage = "calculate error";
			if(error.getErrorCode().equals("02"));
			{
				errorMessage = error.getMessage();
			}
			throw new CalculateException(errorMessage);
		}
		return calResult;
	}

	/**
	 * TODO qube3 为了取计算文件临时使用，上线时删除-->
	 * 把Document对象写进系统文件的xml文件中
	 * @param document
	 * @param uri
	 * @return
	 * @throws IOException
	 */
	private void writeDocToXMLFile(Document document, String uri, boolean isFormat) throws IOException{
		XMLWriter writer;
		
		if(isFormat){
			OutputFormat format = OutputFormat.createPrettyPrint(); //设置XML文档输出格式
			writer = new XMLWriter(new FileWriter(uri),format);
		}else{
			writer = new XMLWriter(new FileWriter(uri));
		}
		
		// lets write to a file
        writer.write(document);
        writer.close();
	}
	
	/**
	 * 判断操作系统是 linux 或 windows
	 * @param args
	 */
	private static String getOSname()
    {
        String os = System.getProperties().getProperty("os.name").toLowerCase();
        if(os.indexOf("windows") != -1){
        	return "windows";
        }
        
        if(os.indexOf("linux") != -1){
        	return "linux";
        }
        
        return "";
      }
	
	// TODO qube3 为了取计算文件临时使用，上线时删除===
	
	private Document stringToDocument(String xml) throws DocumentException{
		Document document = DocumentHelper.parseText(xml);
		return document;
	}
	
	@Override
	public TariffType EleTariffType(Report report) throws CalculateException
	{
		Assert.notNull(report);
		String xml = this.cal_ReturnResult(report, null, null);
		Document document;
		try
		{
			document = stringToDocument(xml);
			
			RDSAP rdsap = null;
			try {
				rdsap = (RDSAP)JavaBeanFactory.newInstance().createObject(document, RDSAP.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			int type = rdsap.getJudgeType();
			if (type == 1)
			{
				return TariffType.Standard;
			} else if (type == 2)
			{
				return TariffType.Off_peak_7;
			} else if (type == 3)
			{
				return TariffType.Off_peak_10;
			} else if (type == 4)
			{
				return TariffType.Hour_24;
			} else if (type == 5)
			{
				return TariffType.Off_peak_18;
			}
			else
			{
				throw new IllegalArgumentException();
			}
		} catch (DocumentException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private String _formatDateString(String str) {
		if (StringUtils.isEmpty(str) || StringUtils.isBlank(str)) {
			return str;
		}
		String[] strs = str.split(":");
		StringBuffer sb = new StringBuffer();
		if (strs[0].length() <= 1) {
			sb.append("0" + strs[0]);
		}else{
			sb.append(strs[0]);
		}
		sb.append(":");
		if (strs[1].length() <= 1) {
			sb.append("0" + strs[1]);
		}else{
			sb.append(strs[1]);
		}
		return sb.toString();
	}
}
