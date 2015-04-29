/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import uk.co.quidos.gdsap.evaluation.CalResult;
import uk.co.quidos.gdsap.evaluation.EpcImprovementCalResult;
import uk.co.quidos.gdsap.evaluation.FuelCalResult;
import uk.co.quidos.gdsap.evaluation.PerFuelCalResult;
import uk.co.quidos.gdsap.evaluation.Recommendation;
import uk.co.quidos.gdsap.evaluation.Report;
import uk.co.quidos.gdsap.evaluation.ReportEpcImprovementCalResult;
import uk.co.quidos.gdsap.evaluation.Solution;
import uk.co.quidos.gdsap.evaluation.SolutionIssue;
import uk.co.quidos.gdsap.evaluation.StandardOption;
import uk.co.quidos.gdsap.evaluation.StandardRecommendationCalResult;
import uk.co.quidos.gdsap.evaluation.StandardRecommendationWrap;
import uk.co.quidos.gdsap.evaluation.StandardValue;
import uk.co.quidos.gdsap.evaluation.enums.Country;
import uk.co.quidos.gdsap.evaluation.enums.Language;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalEpcImprovementResultMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalPerFuelCalResultMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalReportEpcImprovementResultMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalSolutionCalResultMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalSolutionFuelCalResultMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalSolutionIssueMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalSolutionRecommendationRelMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalEpcImprovementResult;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalPerFuelCalResult;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalReportEpcImprovementResult;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalSolutionCalResult;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalSolutionFuelCalResult;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalSolutionIssue;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalSolutionRecommendationRel;
import uk.co.quidos.gdsap.evaluation.persistence.object.QueryRecommendationSolutionRecommendationRelResultMap;
import uk.co.quidos.gdsap.evaluation.services.CalResultServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.LandmarkDefineServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.ReportServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.SolutionServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.StandardRecommendationServiceMgr;
import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;
import uk.co.quidos.gdsap.framework.sys.lang.enums.EnumUtils;

/**
 * @author peng.shi
 *
 */
@Service("calResultServiceMgr")
@Transactional
public class CalResultServiceMgrImpl extends AbsBusinessObjectServiceMgr implements CalResultServiceMgr
{
	@Autowired
	private GdsapEvalSolutionRecommendationRelMapper gdsapEvalSolutionRecommendationRelMapper;
	@Autowired
	private StandardRecommendationServiceMgr standardRecommendationServiceMgr;
	@Autowired
	private SolutionServiceMgr solutionServiceMgr;
	@Autowired
	private GdsapEvalSolutionCalResultMapper gdsapEvalSolutionCalResultMapper;
	@Autowired
	private GdsapEvalSolutionFuelCalResultMapper gdsapEvalSolutionFuelCalResultMapper;
	@Autowired
	private GdsapEvalEpcImprovementResultMapper gdsapEvalEpcImprovementResultMapper;
	@Autowired
	private GdsapEvalReportEpcImprovementResultMapper gdsapEvalReportEpcImprovementResultMapper;
	@Autowired
	private GdsapEvalSolutionIssueMapper gdsapEvalSolutionIssueMapper;
	@Autowired
	private LandmarkDefineServiceMgr landmarkDefineServiceMgr;
	@Autowired
	private GdsapEvalPerFuelCalResultMapper gdsapEvalPerFuelCalResultMapper;
	@Autowired
	private ReportServiceMgr reportServiceMgr;
	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.CalResultServiceMgr#getCalResult(long)
	 */
	@Override
	public CalResult getCalResult(long solutionId)
	{
		Solution solution = this.solutionServiceMgr.getSolution(solutionId);
		Assert.notNull(solution);
		GdsapEvalSolutionCalResult model = this.gdsapEvalSolutionCalResultMapper.load(solutionId);
		if (model != null)
		{
			CalResult calResult = new CalResult();
			
			calResult.setActualHousehold(model.getActualHousehold());
			calResult.setCommEnergyCost(model.getCommEnergyCost());
			calResult.setCommEnergyUse(model.getCommEnergyUse());
			calResult.setEleCostLowrate(model.getEleCostLowrate());
			calResult.setElectricitySavings(model.getElectricitySavings());
			calResult.setEleOpCostHighrate(model.getEleOpCostHighrate());
			calResult.setEleOpUseHighrate(model.getEleOpUseHighrate());
			calResult.setEleSdCost(model.getEleSdCost());
			calResult.setEleSdUse(model.getEleSdUse());
			calResult.setEleUseLowrate(model.getEleUseLowrate());
			calResult.setGasSavings(model.getGasSavings());
			calResult.setMainsGasCost(model.getMainsGasCost());
			calResult.setMainsGasUse(model.getMainsGasUse());
			calResult.setOtherFuelSavings(model.getOtherFuelSavings());
			calResult.setPercentHeating(model.getPercentHeating());
			calResult.setPercentHotWater(model.getPercentHotWater());
			calResult.setSolution(solution);
			calResult.setTypicalHousehold(model.getTypicalHousehold());
			calResult.setCostReduction(model.getCostReduction());
			//ele comm
			calResult.setCommFuelCode(model.getCommFuelCode());
			calResult.setCommEnergyScInput(model.getCommEnergyScInput());
			calResult.setCommEnergyScTable(model.getCommEnergyScTable());
			calResult.setCommEnergyUpInput(model.getCommEnergyUpInput());
			calResult.setCommEnergyUpTable(model.getCommEnergyUpTable());
			//ele sd 
			calResult.setEleSdScInput(model.getEleSdScInput());
			calResult.setEleSdScTable(model.getEleSdScTable());
			calResult.setEleSdUpInput(model.getEleSdUpInput());
			calResult.setEleSdUpTable(model.getEleSdUpTable());
			//ele op h
			calResult.setEleOpScInputHighrate(model.getEleOpScInputHighrate());
			calResult.setEleOpScTableHighrate(model.getEleOpScTableHighrate());
			calResult.setEleOpUpInputHighrate(model.getEleOpUpInputHighrate());
			calResult.setEleOpUpTableHighrate(model.getEleOpUpTableHighrate());
			//ele op l
			calResult.setEleScInputLowrate(model.getEleScInputLowrate());
			calResult.setEleScTableLowrate(model.getEleScTableLowrate());
			calResult.setEleUpInputLowrate(model.getEleUpInputLowrate());
			calResult.setEleUpTableLowrate(model.getEleUpTableLowrate());
			//ele main gas
			calResult.setMainsGasScInput(model.getMainsGasScInput());
			calResult.setMainsGasScTable(model.getMainsGasScTable());
			calResult.setMainsGasUpInput(model.getMainsGasUpInput());
			calResult.setMainsGasUpTable(model.getMainsGasUpTable());
			//ele st
			calResult.setStOccupants(model.getStOccupants());
			calResult.setStAverageHours(model.getStAverageHours());
			calResult.setStThermostatSetting(model.getStThermostatSetting());
			calResult.setStUnheatedRooms(model.getStUnheatedRooms());
			calResult.setStRecommendOutsideable((YesNo)EnumUtils.getByCode(model.getStRecommendOutsideable(), YesNo.class));
			calResult.setStRecommendShowersable((YesNo)EnumUtils.getByCode(model.getStRecommendShowersable(), YesNo.class));
			//ele ac
			calResult.setAcOccupants(model.getAcOccupants());
			calResult.setAcAverageHours(model.getAcAverageHours());
			calResult.setAcThermostatSetting(model.getAcThermostatSetting());
			calResult.setAcUnheatedRooms(model.getAcUnheatedRooms());
			calResult.setAcRecommendOutsideable((YesNo)EnumUtils.getByCode(model.getAcRecommendOutsideable(), YesNo.class));
			calResult.setAcRecommendShowersable((YesNo)EnumUtils.getByCode(model.getAcRecommendShowersable(), YesNo.class));
			//ele space heating 等
			calResult.setSpaceHeating(model.getSpaceHeating());
			calResult.setWaterHeating(model.getWaterHeating());
			calResult.setEnergyTotal(model.getEnergyTotal());
			
			// fuel code 等
			calResult.setCommFuelCode(model.getCommFuelCode());
			calResult.setMgFuelCode(model.getMgFuelCode());
			calResult.setEleStFuelCode(model.getEleStFuelCode());
			calResult.setEleHFuelCode(model.getEleHFuelCode());
			calResult.setEleLFuelCode(model.getEleLFuelCode());
			calResult.setEleSt24FuelCode(model.getEleSt24FuelCode());
			
			calResult.setTotalEleSaving(model.getTotalEleSaving());
			calResult.setTotalGasSaving(model.getTotalGasSaving());
			calResult.setTotalOtherFuelSaving(model.getTotalOtherFuelSaving());
			
			//imp 
			calResult.setImpBoilerFuel(model.getImpBoilerFuel());
			calResult.setImpSingleGlazedPercentage(model.getImpSingleGlazedPercentage());
			
			calResult.setCommUnitTable(model.getCommUnitTable());
			calResult.setMgUnitTable(model.getMgUnitTable());
			calResult.setEleSdUnitTable(model.getEleSdUnitTable());
			calResult.setEleSd24UnitTable(model.getEleSd24UnitTable());
			calResult.setEleHUnitTable(model.getEleHUnitTable());
			calResult.setEleLUnitTable(model.getEleLUnitTable());
			calResult.setEleSd24Cost(model.getEleSd24Cost());
			calResult.setEleSd24Use(model.getEleSd24Use());
			calResult.setEleSd24ScInput(model.getEleSd24ScInput());
			calResult.setEleSd24ScTable(model.getEleSd24ScTable());
			calResult.setEleSd24UpInput(model.getEleSd24UpInput());
			calResult.setEleSd24UpTable(model.getEleSd24UpTable());
			
			calResult.setCommUnitInput(model.getCommUnitInput());
			calResult.setMgUnitInput(model.getMgUnitInput());
			calResult.setEleSdUnitInput(model.getEleSdUnitInput());
			calResult.setEleSd24UnitInput(model.getEleSd24UnitInput());
			calResult.setEleHUnitInput(model.getEleHUnitInput());
			calResult.setEleLUnitInput(model.getEleLUnitInput());
			
			calResult.setCommChpEnergyCost(model.getCommChpEnergyCost());
			calResult.setCommChpEnergyScInput(model.getCommChpEnergyScInput());
			calResult.setCommChpEnergyScTable(model.getCommChpEnergyScTable());
			calResult.setCommChpEnergyUpInput(model.getCommChpEnergyUpInput());
			calResult.setCommChpEnergyUpTable(model.getCommChpEnergyUpTable());
			calResult.setCommChpEnergyUse(model.getCommChpEnergyUse());
			calResult.setCommChpFuelCode(model.getCommChpFuelCode());
			calResult.setCommChpUnitInput(model.getCommChpUnitInput());
			calResult.setCommChpUnitTable(model.getCommChpUnitTable());
			
			return calResult;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.CalResultServiceMgr#getFuelCalResults(long)
	 */
	@Override
	public List<FuelCalResult> getFuelCalResults(long solutionId)
	{
		Solution s = this.solutionServiceMgr.getSolution(solutionId);
		List<GdsapEvalSolutionFuelCalResult> ms = this.gdsapEvalSolutionFuelCalResultMapper.findGdsapEvalSolutionFuelCalResults(solutionId);
		if (!CollectionUtils.isEmpty(ms))
		{
			List<FuelCalResult> results = new ArrayList<FuelCalResult>();
			for (GdsapEvalSolutionFuelCalResult r : ms)
			{
				FuelCalResult result = new FuelCalResult();
				result.setFuelCode(r.getFuelCode());
				result.setId(r.getId());
				result.setOtherFuelCost(r.getOtherFuelCost());
				result.setOtherFuelUse(r.getOtherFuelUse());
				result.setScInput(r.getScInput());
				result.setScTable(r.getScTable());
				result.setUpInput(r.getUpInput());
				result.setUpTable(r.getUpTable());
				result.setSolution(s);
				result.setUnitInput(r.getUnitInput());
				result.setUnitTable(r.getUnitTable());
				results.add(result);
			}
			return results;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.CalResultServiceMgr#getStandardRecommendationCalResults(long)
	 */
	@Override
	public List<StandardRecommendationCalResult> getStandardRecommendationCalResults(long solutionId)
	{
		List<QueryRecommendationSolutionRecommendationRelResultMap> rels = this.gdsapEvalSolutionRecommendationRelMapper.findRelBySolutionId(solutionId);
		if (!CollectionUtils.isEmpty(rels))
		{
			List<StandardRecommendationCalResult> calResults = new ArrayList<StandardRecommendationCalResult>();
			for (QueryRecommendationSolutionRecommendationRelResultMap qrsr:rels)
			{
				Solution solution = this.solutionServiceMgr.getSolution(qrsr.getSolutionId());
				StandardRecommendationWrap srWrap = this.standardRecommendationServiceMgr.getStandardRecommendationWrap(qrsr.getRecommendationId());
				srWrap.setSolutionType(solution.getSolutionType());
				StandardRecommendationCalResult calResult = BeanUtils.toStandardRecommendationCalResult(qrsr);
				calResult.setSolution(solution);
				calResult.setStandardRecommendationWrap(srWrap);
				Map<StandardOption, Integer> selectedOptionItems = new LinkedHashMap<StandardOption, Integer>();
				//初始化选定选项
				for (int i=0;i<=9;i++)
				{
					srWrap.getStandardRecommendation().setSolutionType(solution.getSolutionType());
					if (i == 0)
					{
						if (StringUtils.isEmpty(qrsr.getKey0()))
						{
							break;
						}
						StandardOption so = this.standardRecommendationServiceMgr.getStandardOption(srWrap.getStandardRecommendation(), qrsr.getKey0());
						selectedOptionItems.put(so, qrsr.getValue0());
					}
					if (i == 1)
					{
						if (StringUtils.isEmpty(qrsr.getKey1()))
						{
							break;
						}
						StandardOption so = this.standardRecommendationServiceMgr.getStandardOption(srWrap.getStandardRecommendation(), qrsr.getKey1());
						selectedOptionItems.put(so, qrsr.getValue1());
					}
					if (i == 2)
					{
						if (StringUtils.isEmpty(qrsr.getKey2()))
						{
							break;
						}
						StandardOption so = this.standardRecommendationServiceMgr.getStandardOption(srWrap.getStandardRecommendation(), qrsr.getKey2());
						selectedOptionItems.put(so, qrsr.getValue2());
					}
					if (i == 3)
					{
						if (StringUtils.isEmpty(qrsr.getKey3()))
						{
							break;
						}
						StandardOption so = this.standardRecommendationServiceMgr.getStandardOption(srWrap.getStandardRecommendation(), qrsr.getKey3());
						selectedOptionItems.put(so, qrsr.getValue3());
					}
					if (i == 4)
					{
						if (StringUtils.isEmpty(qrsr.getKey4()))
						{
							break;
						}
						StandardOption so = this.standardRecommendationServiceMgr.getStandardOption(srWrap.getStandardRecommendation(), qrsr.getKey4());
						selectedOptionItems.put(so, qrsr.getValue4());
					}
					if (i == 5)
					{
						if (StringUtils.isEmpty(qrsr.getKey5()))
						{
							break;
						}
						StandardOption so = this.standardRecommendationServiceMgr.getStandardOption(srWrap.getStandardRecommendation(), qrsr.getKey5());
						selectedOptionItems.put(so, qrsr.getValue5());
					}
					if (i == 6)
					{
						if (StringUtils.isEmpty(qrsr.getKey6()))
						{
							break;
						}
						StandardOption so = this.standardRecommendationServiceMgr.getStandardOption(srWrap.getStandardRecommendation(), qrsr.getKey6());
						selectedOptionItems.put(so, qrsr.getValue6());
					}
					if (i == 7)
					{
						if (StringUtils.isEmpty(qrsr.getKey7()))
						{
							break;
						}
						StandardOption so = this.standardRecommendationServiceMgr.getStandardOption(srWrap.getStandardRecommendation(), qrsr.getKey7());
						selectedOptionItems.put(so, qrsr.getValue7());
					}
					if (i == 8)
					{
						if (StringUtils.isEmpty(qrsr.getKey8()))
						{
							break;
						}
						StandardOption so = this.standardRecommendationServiceMgr.getStandardOption(srWrap.getStandardRecommendation(), qrsr.getKey8());
						selectedOptionItems.put(so, qrsr.getValue8());
					}
					if (i == 9)
					{
						if (StringUtils.isEmpty(qrsr.getKey9()))
						{
							break;
						}
						StandardOption so = this.standardRecommendationServiceMgr.getStandardOption(srWrap.getStandardRecommendation(), qrsr.getKey9());
						selectedOptionItems.put(so, qrsr.getValue9());
					}
				}
				calResult.getStandardRecommendationWrap().setSelectedOptionItems(selectedOptionItems);
				
				Map<StandardValue,String> selectedInputValues = new LinkedHashMap<StandardValue, String>();
				//初始化输入项
				for (int i=0;i<=5;i++)
				{
					srWrap.getStandardRecommendation().setSolutionType(solution.getSolutionType());
					if (i == 0)
					{
						if (StringUtils.isEmpty(qrsr.getInputKey0()))
						{
							break;
						}
						StandardValue so = this.standardRecommendationServiceMgr.getStandardValue(srWrap.getStandardRecommendation(), qrsr.getInputKey0());
						so.setInputValue(qrsr.getInputValue0());
						so.setU_valuesDeclarationSelected(qrsr.getCheckedValue0());
						selectedInputValues.put(so, qrsr.getInputValue0());
					}
					if (i == 1)
					{
						if (StringUtils.isEmpty(qrsr.getInputKey1()))
						{
							break;
						}
						StandardValue so = this.standardRecommendationServiceMgr.getStandardValue(srWrap.getStandardRecommendation(), qrsr.getInputKey1());
						so.setInputValue(qrsr.getInputValue1());
						so.setU_valuesDeclarationSelected(qrsr.getCheckedValue1());
						selectedInputValues.put(so, qrsr.getInputValue1());
					}
					if (i == 2)
					{
						if (StringUtils.isEmpty(qrsr.getInputKey2()))
						{
							break;
						}
						StandardValue so = this.standardRecommendationServiceMgr.getStandardValue(srWrap.getStandardRecommendation(), qrsr.getInputKey2());
						so.setInputValue(qrsr.getInputValue2());
						so.setU_valuesDeclarationSelected(qrsr.getCheckedValue2());
						selectedInputValues.put(so, qrsr.getInputValue2());
					}
					if (i == 3)
					{
						if (StringUtils.isEmpty(qrsr.getInputKey3()))
						{
							break;
						}
						StandardValue so = this.standardRecommendationServiceMgr.getStandardValue(srWrap.getStandardRecommendation(), qrsr.getInputKey3());
						so.setInputValue(qrsr.getInputValue3());
						so.setU_valuesDeclarationSelected(qrsr.getCheckedValue3());
						selectedInputValues.put(so, qrsr.getInputValue3());
					}
					if (i == 4)
					{
						if (StringUtils.isEmpty(qrsr.getInputKey4()))
						{
							break;
						}
						StandardValue so = this.standardRecommendationServiceMgr.getStandardValue(srWrap.getStandardRecommendation(), qrsr.getInputKey4());
						so.setInputValue(qrsr.getInputValue4());
						so.setU_valuesDeclarationSelected(qrsr.getCheckedValue4());
						selectedInputValues.put(so, qrsr.getInputValue4());
					}
					if (i == 5)
					{
						if (StringUtils.isEmpty(qrsr.getInputKey5()))
						{
							break;
						}
						StandardValue so = this.standardRecommendationServiceMgr.getStandardValue(srWrap.getStandardRecommendation(), qrsr.getInputKey5());
						so.setInputValue(qrsr.getInputValue5());
						so.setU_valuesDeclarationSelected(qrsr.getCheckedValue5());
						selectedInputValues.put(so, qrsr.getInputValue5());
					}
				}
				calResult.getStandardRecommendationWrap().setSelectedInputValues(selectedInputValues);
				calResults.add(calResult);
			}
			return calResults;
		}
		return null;
	}

	@Override
	public float totalEstimatedAnnualSavings(List<StandardRecommendationCalResult> srCalResults)
	{
		float total = 0f;
		if (!CollectionUtils.isEmpty(srCalResults))
		{
			for (StandardRecommendationCalResult sr : srCalResults)
			{
				total = total + sr.getEstimatedAnnualSavings();
			}
		}
		return total;
	}

	@Override
	public float totalEstimatedCostsEnd(List<StandardRecommendationCalResult> srCalResults)
	{

		float total = 0f;
		if (!CollectionUtils.isEmpty(srCalResults))
		{
			for (StandardRecommendationCalResult sr : srCalResults)
			{
				total = total + sr.getEstimatedCostsEnd();
			}
		}
		return total;
	}

	@Override
	public float totalEstimatedCostsStart(List<StandardRecommendationCalResult> srCalResults)
	{
		float total = 0f;
		if (!CollectionUtils.isEmpty(srCalResults))
		{
			for (StandardRecommendationCalResult sr : srCalResults)
			{
				total = total + sr.getEstimatedCostsStart();
			}
		}
		return total;
	}

	@Override
	public float totalTypicalAnnualSavings(List<StandardRecommendationCalResult> srCalResults)
	{
		float total = 0f;
		if (!CollectionUtils.isEmpty(srCalResults))
		{
			for (StandardRecommendationCalResult sr : srCalResults)
			{
				total = total + sr.getTypicalAnnualSavings();
			}
		}
		return total;
	}

	@Override
	public CalResult addCalResult(CalResult calResult)
	{
		GdsapEvalSolutionCalResult calResultDO = new GdsapEvalSolutionCalResult();
		calResultDO.setCalResultId(calResult.getSolution().getId());
		calResultDO.setActualHousehold(calResult.getActualHousehold());
		calResultDO.setTypicalHousehold(calResult.getTypicalHousehold());
		calResultDO.setElectricitySavings(calResult.getElectricitySavings());
		calResultDO.setGasSavings(calResult.getGasSavings());
		calResultDO.setOtherFuelSavings(calResult.getOtherFuelSavings());
		calResultDO.setPercentHeating(calResult.getPercentHeating());
		calResultDO.setPercentHotWater(calResult.getPercentHotWater());
		calResultDO.setCostReduction(calResult.getCostReduction());
		calResultDO.setCommEnergyCost(calResult.getCommEnergyCost());
		calResultDO.setCommEnergyUse(calResult.getCommEnergyUse());
		calResultDO.setEleSdCost(calResult.getEleSdCost());
		calResultDO.setEleSdUse(calResult.getEleSdUse());
		calResultDO.setEleOpCostHighrate(calResult.getEleOpCostHighrate());
		calResultDO.setEleOpUseHighrate(calResult.getEleOpUseHighrate());
		calResultDO.setEleCostLowrate(calResult.getEleCostLowrate());
		calResultDO.setEleUseLowrate(calResult.getEleUseLowrate());
		calResultDO.setMainsGasCost(calResult.getMainsGasCost());
		calResultDO.setMainsGasUse(calResult.getMainsGasUse());
		calResultDO.setCommFuelCode(calResult.getCommFuelCode());
		calResultDO.setMgFuelCode(calResult.getMgFuelCode());
		calResultDO.setEleStFuelCode(calResult.getEleStFuelCode());
		calResultDO.setEleHFuelCode(calResult.getEleHFuelCode());
		calResultDO.setEleLFuelCode(calResult.getEleLFuelCode());
		
		calResultDO.setCommEnergyScTable(calResult.getCommEnergyScTable());
		calResultDO.setCommEnergyUpInput(calResult.getCommEnergyUpInput());
		calResultDO.setCommEnergyUpTable(calResult.getCommEnergyUpTable());
		calResultDO.setEleSdCost(calResult.getEleSdCost());
		calResultDO.setEleSdUse(calResult.getEleSdUse());
		calResultDO.setEleSdScInput(calResult.getEleSdScInput());
		calResultDO.setEleSdScTable(calResult.getEleSdScTable());
		calResultDO.setEleSdUpInput(calResult.getEleSdUpInput());
		calResultDO.setEleSdUpTable(calResult.getEleSdUpTable());
		calResultDO.setEleOpCostHighrate(calResult.getEleOpCostHighrate());
		calResultDO.setEleOpUseHighrate(calResult.getEleOpUseHighrate());
		calResultDO.setEleOpScInputHighrate(calResult.getEleOpScInputHighrate());
		calResultDO.setEleOpScTableHighrate(calResult.getEleOpScTableHighrate());
		calResultDO.setEleOpUpInputHighrate(calResult.getEleOpUpInputHighrate());
		calResultDO.setEleOpUpTableHighrate(calResult.getEleOpUpTableHighrate());
		calResultDO.setEleCostLowrate(calResult.getEleCostLowrate());
		calResultDO.setEleUseLowrate(calResult.getEleUseLowrate());
		calResultDO.setEleScInputLowrate(calResult.getEleScInputLowrate());
		calResultDO.setEleScTableLowrate(calResult.getEleScTableLowrate());
		calResultDO.setEleUpInputLowrate(calResult.getEleUpInputLowrate());
		calResultDO.setEleUpTableLowrate(calResult.getEleUpTableLowrate());
		calResultDO.setMainsGasCost(calResult.getMainsGasCost());
		calResultDO.setMainsGasUse(calResult.getMainsGasUse());
		calResultDO.setMainsGasScInput(calResult.getMainsGasScInput());
		calResultDO.setMainsGasScTable(calResult.getMainsGasScTable());
		calResultDO.setMainsGasUpInput(calResult.getMainsGasUpInput());
		calResultDO.setMainsGasUpTable(calResult.getMainsGasUpTable());
		calResultDO.setSpaceHeating(calResult.getSpaceHeating());
		calResultDO.setWaterHeating(calResult.getWaterHeating());
		calResultDO.setEnergyTotal(calResult.getEnergyTotal());
		calResultDO.setStOccupants(calResult.getStOccupants());
		calResultDO.setStAverageHours(calResult.getStAverageHours());
		calResultDO.setStThermostatSetting(calResult.getStThermostatSetting());
		calResultDO.setStUnheatedRooms(calResult.getStUnheatedRooms());
		calResultDO.setStRecommendShowersable(calResult.getStRecommendShowersable() != null ? calResult.getStRecommendShowersable().getCode(): null);
		calResultDO.setStRecommendOutsideable(calResult.getStRecommendOutsideable() != null ? calResult.getStRecommendOutsideable().getCode(): null);
		calResultDO.setAcOccupants(calResult.getAcOccupants());
		calResultDO.setAcAverageHours(calResult.getAcAverageHours());
		calResultDO.setAcThermostatSetting(calResult.getAcThermostatSetting());
		calResultDO.setAcUnheatedRooms(calResult.getAcUnheatedRooms());
		calResultDO.setAcRecommendShowersable(calResult.getAcRecommendShowersable() != null ? calResult.getAcRecommendShowersable().getCode() : null);
		calResultDO.setAcRecommendOutsideable(calResult.getAcRecommendOutsideable() != null ? calResult.getAcRecommendOutsideable().getCode() : null);
		
		calResultDO.setTotalEleSaving(calResult.getTotalEleSaving());
		calResultDO.setTotalGasSaving(calResult.getTotalGasSaving());
		calResultDO.setTotalOtherFuelSaving(calResult.getTotalOtherFuelSaving());
		
		calResultDO.setImpBoilerFuel(calResult.getImpBoilerFuel());
		calResultDO.setImpSingleGlazedPercentage(calResult.getImpSingleGlazedPercentage());
		
		calResultDO.setCommUnitTable(calResult.getCommUnitTable());
		calResultDO.setMgUnitTable(calResult.getMgUnitTable());
		calResultDO.setEleSdUnitTable(calResult.getEleSdUnitTable());
		calResultDO.setEleSd24UnitTable(calResult.getEleSd24UnitTable());
		calResultDO.setEleHUnitTable(calResult.getEleHUnitTable());
		calResultDO.setEleLUnitTable(calResult.getEleLUnitTable());
		calResultDO.setEleSd24Cost(calResult.getEleSd24Cost());
		calResultDO.setEleSd24Use(calResult.getEleSd24Use());
		calResultDO.setEleSd24ScInput(calResult.getEleSd24ScInput());
		calResultDO.setEleSd24ScTable(calResult.getEleSd24ScTable());
		calResultDO.setEleSd24UpInput(calResult.getEleSd24UpInput());
		calResultDO.setEleSd24UpTable(calResult.getEleSd24UpTable());
		calResultDO.setEleSt24FuelCode(calResult.getEleSt24FuelCode());
		
		calResultDO.setCommUnitInput(calResult.getCommUnitInput());
		calResultDO.setMgUnitInput(calResult.getMgUnitInput());
		calResultDO.setEleSdUnitInput(calResult.getEleSdUnitInput());
		calResultDO.setEleSd24UnitInput(calResult.getEleSd24UnitInput());
		calResultDO.setEleHUnitInput(calResult.getEleHUnitInput());
		calResultDO.setEleLUnitInput(calResult.getEleLUnitInput());
		
		calResultDO.setCommChpEnergyCost(calResult.getCommChpEnergyCost());
		calResultDO.setCommChpEnergyScInput(calResult.getCommChpEnergyScInput());
		calResultDO.setCommChpEnergyScTable(calResult.getCommChpEnergyScTable());
		calResultDO.setCommChpEnergyUpInput(calResult.getCommChpEnergyUpInput());
		calResultDO.setCommChpEnergyUpTable(calResult.getCommChpEnergyUpTable());
		calResultDO.setCommChpEnergyUse(calResult.getCommChpEnergyUse());
		calResultDO.setCommChpFuelCode(calResult.getCommChpFuelCode());
		calResultDO.setCommChpUnitInput(calResult.getCommChpUnitInput());
		calResultDO.setCommChpUnitTable(calResult.getCommChpUnitTable());
		
		this.gdsapEvalSolutionCalResultMapper.insert(calResultDO);
		
		return calResult;
	}

	@Override
	public List<FuelCalResult> addFuelCalResults(List<FuelCalResult> fuelCalResults)
	{
		if (!CollectionUtils.isEmpty(fuelCalResults))
		{
			List<FuelCalResult> results = new ArrayList<FuelCalResult>();
			for (FuelCalResult res:fuelCalResults)
			{
				GdsapEvalSolutionFuelCalResult fuelCalResult = new GdsapEvalSolutionFuelCalResult();
				fuelCalResult.setFuelCode(res.getFuelCode());
				fuelCalResult.setOtherFuelCost(res.getOtherFuelCost());
				fuelCalResult.setOtherFuelUse(res.getOtherFuelUse());
				fuelCalResult.setSolutionId(res.getSolution().getId());
				fuelCalResult.setScInput(res.getScInput());
				fuelCalResult.setScTable(res.getScTable());
				fuelCalResult.setUpInput(res.getUpInput());
				fuelCalResult.setUpTable(res.getUpTable());
				fuelCalResult.setUnitInput(res.getUnitInput());
				fuelCalResult.setUnitTable(res.getUnitTable());
				
				this.gdsapEvalSolutionFuelCalResultMapper.insert(fuelCalResult);
				res.setId(fuelCalResult.getId());
				results.add(res);
			}
			return results;
		}
		return null;
	}

	@Override
	public List<StandardRecommendationCalResult> addStandardRecommendationCalResults(List<StandardRecommendationCalResult> srCalResults)
	{
		if (!CollectionUtils.isEmpty(srCalResults))
		{
			for (StandardRecommendationCalResult cr:srCalResults)
			{
				GdsapEvalSolutionRecommendationRel rel = BeanUtils.toGdsapEvalSolutionRecommendationRel(cr);
				rel.setRecommendationId(cr.getStandardRecommendationWrap().getId());
				rel.setSolutionId(cr.getSolution().getId());
				
				this.gdsapEvalSolutionRecommendationRelMapper.insert(rel);
			}
			return srCalResults;
		}
		return null;
	}

	@Override
	public List<EpcImprovementCalResult> getEpcImprovementCalResults(Solution solution)
	{
		Assert.notNull(solution);
		List<GdsapEvalEpcImprovementResult> models = this.gdsapEvalEpcImprovementResultMapper.findBySolutionId(solution.getId());
		if (!CollectionUtils.isEmpty(models))
		{
			List<EpcImprovementCalResult> calResults = new ArrayList<EpcImprovementCalResult>();
			for (GdsapEvalEpcImprovementResult m : models)
			{
				EpcImprovementCalResult calResult = BeanUtils.toEpcImprovementCalResult(m);
				Recommendation recommendation = this.landmarkDefineServiceMgr.getRecommentation(calResult.getImprovementNumber(), Country.EAW, Language.EN);
				calResult.setRecommendation(recommendation);
				calResults.add(calResult);
			}
			return calResults;
		}
		return null;
	}

	@Override
	public List<EpcImprovementCalResult> addEpcImprovementCalResults(List<EpcImprovementCalResult> epcImprovementCalResults)
	{
		Assert.notEmpty(epcImprovementCalResults);
		for (EpcImprovementCalResult epcImprovementCalResult : epcImprovementCalResults)
		{
			GdsapEvalEpcImprovementResult r = BeanUtils.toGdsapEvalEpcImprovementResult(epcImprovementCalResult);
			this.gdsapEvalEpcImprovementResultMapper.insert(r);
			epcImprovementCalResult.setId(r.getId());
		}
		return epcImprovementCalResults;
	}

	@Override
	public SolutionIssue addSolutionIssue(SolutionIssue solutionIssue)
	{
		Assert.notNull(solutionIssue);
		GdsapEvalSolutionIssue tmpModel = gdsapEvalSolutionIssueMapper.load(solutionIssue.getId());
		if (tmpModel != null)
		{
			throw new IllegalArgumentException();
		}
		tmpModel = BeanUtils.toGdsapEvalSolutionIssue(solutionIssue);
		gdsapEvalSolutionIssueMapper.insert(tmpModel);
		return solutionIssue;
	}

	@Override
	public SolutionIssue getSolutionIssue(Solution solution)
	{
		long id = solution.getId();
		GdsapEvalSolutionIssue model = this.gdsapEvalSolutionIssueMapper.load(id);
		if (model != null)
		{
			return BeanUtils.toSolutionIssue(model);
		}
		return null;
	}

	@Override
	public List<PerFuelCalResult> addPerFuelCalResults(List<PerFuelCalResult> perFuelCalResults)
	{
		Assert.notNull(perFuelCalResults);
		Assert.notEmpty(perFuelCalResults);
		for (PerFuelCalResult calResult : perFuelCalResults)
		{
			GdsapEvalPerFuelCalResult model = new GdsapEvalPerFuelCalResult();
			model.setFuelCode(calResult.getFuelCode());
			model.setKwhSaving(calResult.getKwhSaving());
			model.setScFraction(calResult.getScFraction());
			model.setSolutionId(calResult.getSolution().getId());
			this.gdsapEvalPerFuelCalResultMapper.insert(model);
			calResult.setId(model.getId());
		}
		return perFuelCalResults;
	}

	@Override
	public List<PerFuelCalResult> getPerFuelCalResults(Solution solution)
	{
		Assert.notNull(solution);
		List<GdsapEvalPerFuelCalResult> models = this.gdsapEvalPerFuelCalResultMapper.findBySolutionId(solution.getId());
		if (!CollectionUtils.isEmpty(models))
		{
			List<PerFuelCalResult> calResults = new ArrayList<PerFuelCalResult>();
			for (GdsapEvalPerFuelCalResult model : models)
			{
				PerFuelCalResult calResult = new PerFuelCalResult();
				calResult.setFuelCode(model.getFuelCode());
				calResult.setId(model.getId());
				calResult.setKwhSaving(model.getKwhSaving());
				calResult.setScFraction(model.getScFraction());
				calResult.setSolution(this.solutionServiceMgr.getSolution(model.getId()));
				calResults.add(calResult);
			}
			return calResults;
		}
		return null;
	}
	
	public ReportEpcImprovementCalResult getReportEpcImprovementCalResult(long id)
	{
		GdsapEvalReportEpcImprovementResult gdsapEvalReportEpcImprovementResult = this.gdsapEvalReportEpcImprovementResultMapper.load(id);
		if(gdsapEvalReportEpcImprovementResult != null)
		{
			ReportEpcImprovementCalResult reportEpcImprovementResult = BeanUtils.toReportEpcImprovementCalResult(gdsapEvalReportEpcImprovementResult);
			reportEpcImprovementResult.setReport(reportServiceMgr.getReport(gdsapEvalReportEpcImprovementResult.getId()));
			Recommendation recommendation = this.landmarkDefineServiceMgr.getRecommentation(gdsapEvalReportEpcImprovementResult.getImprovementNumber(), Country.EAW, Language.EN);
			reportEpcImprovementResult.setRecommendation(recommendation);
			return reportEpcImprovementResult;
		}
		return null;
	}
	
	public ReportEpcImprovementCalResult addReportEpcImprovementCalResult(ReportEpcImprovementCalResult reportEpcImprovementCalResult)
	{
		if(reportEpcImprovementCalResult != null)
		{
			GdsapEvalReportEpcImprovementResult gdsapEvalReportEpcImprovementResult = BeanUtils.toGdsapEvalReportEpcImprovementResult(reportEpcImprovementCalResult);
			this.gdsapEvalReportEpcImprovementResultMapper.insert(gdsapEvalReportEpcImprovementResult);
		}
		return null;
	}
	
	public List<ReportEpcImprovementCalResult> getReportEpcImprovementCalResults(Report report)
	{
		Assert.notNull(report);
		List<GdsapEvalReportEpcImprovementResult> list = this.gdsapEvalReportEpcImprovementResultMapper.findByReportId(report.getId());
		if(!CollectionUtils.isEmpty(list))
		{
			List<ReportEpcImprovementCalResult> listResult = new ArrayList<ReportEpcImprovementCalResult>();
			for(GdsapEvalReportEpcImprovementResult g : list)
			{
				ReportEpcImprovementCalResult r = BeanUtils.toReportEpcImprovementCalResult(g);
				r.setReport(report);
				Recommendation recommendation = this.landmarkDefineServiceMgr.getRecommentation(g.getImprovementNumber(), Country.EAW, Language.EN);
				r.setRecommendation(recommendation);
				listResult.add(r);
			}
			return listResult;
		}
		return null;
	}

	@Override
	public void deleteReportEpcImprovementCalResults(Report report) {
		Assert.notNull(report);
		this.gdsapEvalReportEpcImprovementResultMapper.deleteByReportId(report.getId());
	}
	
}
