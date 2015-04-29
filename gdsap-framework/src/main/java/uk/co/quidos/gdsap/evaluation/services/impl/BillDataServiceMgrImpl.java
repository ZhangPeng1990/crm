/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import uk.co.quidos.gdsap.evaluation.BillDataComm;
import uk.co.quidos.gdsap.evaluation.BillDataEle;
import uk.co.quidos.gdsap.evaluation.BillDataLPG;
import uk.co.quidos.gdsap.evaluation.BillDataMg;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalBillDataCommMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalBillDataEleMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalBillDataLpgMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalBillDataMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalBillDataMgMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalBillDataComm;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalBillDataEle;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalBillDataLpg;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalBillDataMg;
import uk.co.quidos.gdsap.evaluation.services.BillDataServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.DictServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.ReportServiceMgr;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
@Service("billDataServiceMgr")
@Transactional
public class BillDataServiceMgrImpl extends AbsBusinessObjectServiceMgr implements BillDataServiceMgr
{
	@Autowired
	private ReportServiceMgr reportServiceMgr;
	@Autowired
	private GdsapEvalBillDataMgMapper gdsapEvalBillDataMgMapper;
	@Autowired
	private GdsapEvalBillDataCommMapper gdsapEvalBillDataCommMapper;
	@Autowired
	private GdsapEvalBillDataMapper gdsapEvalBillDataMapper;
	@Autowired
	private GdsapEvalBillDataEleMapper gdsapEvalBillDataEleMapper;
	@Autowired
	private DictServiceMgr dictServiceMgr;
	@Autowired
	private GdsapEvalBillDataLpgMapper gdsapEvalBillDataLpgMapper;
	
	public GdsapEvalBillDataMapper getGdsapEvalBillDataMapper()
	{
		return gdsapEvalBillDataMapper;
	}

	public void setGdsapEvalBillDataMapper(GdsapEvalBillDataMapper gdsapEvalBillDataMapper)
	{
		this.gdsapEvalBillDataMapper = gdsapEvalBillDataMapper;
	}

	public ReportServiceMgr getReportServiceMgr()
	{
		return reportServiceMgr;
	}

	public void setReportServiceMgr(ReportServiceMgr reportServiceMgr)
	{
		this.reportServiceMgr = reportServiceMgr;
	}
	
	public DictServiceMgr getDictServiceMgr() {
		return dictServiceMgr;
	}

	public void setDictServiceMgr(DictServiceMgr dictServiceMgr) {
		this.dictServiceMgr = dictServiceMgr;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.BillDataServiceMgr#addBillData(uk.co.quidos.gdsap.evaluation.BillData)
	 */
//	@Override
//	public BillData addBillData(BillData billData)
//	{
//		Assert.notNull(billData);
//		gdsapEvalBillDataMapper.insert(BeanUtils.billDataToGBillData(billData));
//		this.reportServiceMgr.deleteSolutionsWithRecommendations(billData.getReport());
//		return billData;
//	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.BillDataServiceMgr#getBillData(long)
	 */
//	@Override
//	public BillData getBillData(long reportId)
//	{
//		GdsapEvalBillData gdsapEvalBillData = gdsapEvalBillDataMapper.load(reportId);
//		if(gdsapEvalBillData != null){
//			BillData billData = BeanUtils.gBillDataToBillData(gdsapEvalBillData);
//			billData.setReport(gdsapEvalBillData.getReportId() != null ? reportServiceMgr.getReport(gdsapEvalBillData.getReportId()) : null);
//			billData.setChReliablityLevel(gdsapEvalBillData.getChReliablityLevel() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getChReliablityLevel()) : null);
//			billData.setChPeriodSelect(gdsapEvalBillData.getChPeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getChPeriodSelect()) : null);
//			billData.setChFixedCostSelected(gdsapEvalBillData.getChFixedCostSelected() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getChFixedCostSelected()) : null);
//			billData.setEtElectricityTariff(gdsapEvalBillData.getEtElectricityTariff() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getEtElectricityTariff()) : null);
//			billData.setMgReliablityLevel(gdsapEvalBillData.getMgReliablityLevel() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getMgReliablityLevel()) : null);
//			billData.setMgPeriodSelect(gdsapEvalBillData.getMgPeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getMgPeriodSelect()) : null);
//			billData.setMgChargingBasis(gdsapEvalBillData.getMgChargingBasis() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getMgChargingBasis()) : null);
//			billData.setMgStAmountSelect(gdsapEvalBillData.getMgStAmountSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getMgStAmountSelect()) : null);
//			billData.setMgTwUnitsSelected(gdsapEvalBillData.getMgTwUnitsSelected() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getMgTwUnitsSelected()) : null);
//			billData.setEtStReliablityLevel(gdsapEvalBillData.getEtStReliablityLevel() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getEtStReliablityLevel()) : null);
//			billData.setEtStPeriodSelect(gdsapEvalBillData.getEtStPeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getEtStPeriodSelect()) : null);
//			billData.setEtStChargingBasis(gdsapEvalBillData.getEtStChargingBasis() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getEtStChargingBasis()) : null);
//			billData.setEtStStandingChargeAmountSelect(gdsapEvalBillData.getEtStStandingChargeAmountSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getEtStStandingChargeAmountSelect()) : null);
//			billData.setEtStUnitsAtThisPriceSelect(gdsapEvalBillData.getEtStUnitsAtThisPriceSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getEtStUnitsAtThisPriceSelect()) : null);
//			billData.setEtOffHReliablityLevel(gdsapEvalBillData.getEtOffHReliablityLevel() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getEtOffHReliablityLevel()) : null);
//			billData.setEtOffHPeriodSelect(gdsapEvalBillData.getEtOffHPeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getEtOffHPeriodSelect()) : null);
//			billData.setEtOffHChargingBasis(gdsapEvalBillData.getEtOffHChargingBasis() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getEtOffHChargingBasis()) : null);
//			billData.setEtOffHStandingChargeAmountSelect(gdsapEvalBillData.getEtOffHStandingChargeAmountSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getEtOffHStandingChargeAmountSelect()) : null);
//			billData.setEtOffHUnitsAtThisPriceSelect(gdsapEvalBillData.getEtOffHUnitsAtThisPriceSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getEtOffHUnitsAtThisPriceSelect()) : null);
//			billData.setEtOffLReliablityLevel(gdsapEvalBillData.getEtOffLReliablityLevel() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getEtOffLReliablityLevel()) : null);
//			billData.setEtOffLPeriodSelect(gdsapEvalBillData.getEtOffLPeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getEtOffLPeriodSelect()) : null);
//			billData.setEtOffLChargingBasis(gdsapEvalBillData.getEtOffLChargingBasis() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getEtOffLChargingBasis()) : null);
//			billData.setEtOffLStandingChargeAmountSelect(gdsapEvalBillData.getEtOffLStandingChargeAmountSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getEtOffLStandingChargeAmountSelect()) : null);
//			billData.setEtOffLUnitsAtThisPriceSelect(gdsapEvalBillData.getEtOffLUnitsAtThisPriceSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getEtOffLUnitsAtThisPriceSelect()) : null);
//			billData.setEtPvPeriodSelect(gdsapEvalBillData.getEtPvPeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getEtPvPeriodSelect()) : null);
//			billData.setEtWindPeriodSelect(gdsapEvalBillData.getEtWindPeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getEtWindPeriodSelect()) : null);
//			billData.setEtMicroablePeriodSelect(gdsapEvalBillData.getEtMicroablePeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalBillData.getEtMicroablePeriodSelect()) : null);
//			return billData;
//		}else{
//			return null;
//		}
//	}
	
	public void deleteBillData(long reportId){
		gdsapEvalBillDataMapper.delete(reportId);
	}

	@Override
	public BillDataComm addBillDataComm(BillDataComm billDataComm)
	{
		Assert.notNull(billDataComm);
		GdsapEvalBillDataComm tmpModel = this.gdsapEvalBillDataCommMapper.load(billDataComm.getReport().getId());
		if (tmpModel != null)
		{
			throw new IllegalArgumentException();
		}
		tmpModel = BeanUtils.toGdsapEvalBillDataComm(billDataComm);
		this.gdsapEvalBillDataCommMapper.insert(tmpModel);
		this.reportServiceMgr.deleteSolutionsWithRecommendations(billDataComm.getReport());
		return billDataComm;
	}

	@Override
	public BillDataMg addBillDataMg(BillDataMg billDataMg)
	{
		Assert.notNull(billDataMg);
		GdsapEvalBillDataMg tmpModel = this.gdsapEvalBillDataMgMapper.load(billDataMg.getReport().getId());
		if (tmpModel != null)
		{
			throw new IllegalArgumentException();
		}
		tmpModel = BeanUtils.toGdsapEvalBillDataMg(billDataMg);
		this.gdsapEvalBillDataMgMapper.insert(tmpModel);
		this.reportServiceMgr.deleteSolutionsWithRecommendations(billDataMg.getReport());
		return billDataMg;
	}

	@Override
	public void deleteBillDataComm(long reportId)
	{
		this.gdsapEvalBillDataCommMapper.delete(reportId);
	}

	@Override
	public void deleteBillDataMg(long reportId)
	{
		this.gdsapEvalBillDataMgMapper.delete(reportId);
	}

	@Override
	public BillDataComm getBillDataComm(long reportId)
	{
		GdsapEvalBillDataComm model = this.gdsapEvalBillDataCommMapper.load(reportId);
		if (model != null)
		{
			return BeanUtils.toBillDataComm(model);
		}
		return null;
	}

	@Override
	public BillDataMg getBillDataMg(long reportId)
	{
		GdsapEvalBillDataMg model = this.gdsapEvalBillDataMgMapper.load(reportId);
		if (model != null)
		{
			return BeanUtils.toBillDataMg(model);
		}
		return null;
	}

	@Override
	public BillDataComm updateBillDataComm(BillDataComm billDataComm)
	{
		Assert.notNull(billDataComm);
		GdsapEvalBillDataComm model = BeanUtils.toGdsapEvalBillDataComm(billDataComm);
		this.gdsapEvalBillDataCommMapper.update(model);
		this.reportServiceMgr.deleteSolutionsWithRecommendations(billDataComm.getReport());
		return billDataComm;
	}

	@Override
	public BillDataMg updateBillDataMg(BillDataMg billDataMg)
	{
		Assert.notNull(billDataMg);
		GdsapEvalBillDataMg model = BeanUtils.toGdsapEvalBillDataMg(billDataMg);
		this.gdsapEvalBillDataMgMapper.update(model);
		this.reportServiceMgr.deleteSolutionsWithRecommendations(billDataMg.getReport());
		return billDataMg;
	}

	@Override
	public BillDataEle addBillDataEle(BillDataEle billDataEle)
	{
		Assert.notNull(billDataEle);
		GdsapEvalBillDataEle tmpModel = this.gdsapEvalBillDataEleMapper.load(billDataEle.getId());
		if (tmpModel != null)
		{
			throw new IllegalArgumentException();
		}
		GdsapEvalBillDataEle model = BeanUtils.toGdsapEvalBillDataEle(billDataEle);
		this.gdsapEvalBillDataEleMapper.insert(model);
		this.reportServiceMgr.deleteSolutionsWithRecommendations(billDataEle.getReport());
		return billDataEle;
	}

	@Override
	public void deleteBillDataEle(long reportId)
	{
		this.gdsapEvalBillDataEleMapper.delete(reportId);
	}

	@Override
	public BillDataEle getBillDataEle(long reportId)
	{
		GdsapEvalBillDataEle model = this.gdsapEvalBillDataEleMapper.load(reportId);
		if (model != null)
		{
			return BeanUtils.toBillDataEle(model);
		}
		return null;
	}

	@Override
	public BillDataEle updateBillDataEle(BillDataEle billDataEle)
	{
		GdsapEvalBillDataEle tmpModel = this.gdsapEvalBillDataEleMapper.load(billDataEle.getId());
		if (tmpModel == null)
		{
			throw new IllegalArgumentException();
		}
		tmpModel = BeanUtils.toGdsapEvalBillDataEle(billDataEle);
		this.gdsapEvalBillDataEleMapper.update(tmpModel);
		this.reportServiceMgr.deleteSolutionsWithRecommendations(billDataEle.getReport());
		return billDataEle;
	}

	@Override
	public BillDataLPG addBillDataLPG(BillDataLPG billDataLPG) {
		GdsapEvalBillDataLpg model = BeanUtils.getGdsapEvalBillDataLpg(billDataLPG);
		gdsapEvalBillDataLpgMapper.insert(model);
		return billDataLPG;
	}

	@Override
	public void deleteBillDataLPG(long reportId) {
		gdsapEvalBillDataLpgMapper.delete(reportId);
		
	}

	@Override
	public BillDataLPG getBillDataLPG(long reportId) {
		GdsapEvalBillDataLpg model = gdsapEvalBillDataLpgMapper.load(reportId);
		if(model != null)
		{
			BillDataLPG billDataLPG = BeanUtils.getBillDataLPG(model);
			billDataLPG.setLpgReliablityLevel(dictServiceMgr.getDictItem(model.getLpgReliablityLevel()));
			billDataLPG.setLpgPeriodSelect(dictServiceMgr.getDictItem(model.getLpgPeriodSelect()));
			billDataLPG.setLpgChargingBasis(dictServiceMgr.getDictItem(model.getLpgChargingBasis()));
			billDataLPG.setLpgStAmountSelect(dictServiceMgr.getDictItem(model.getLpgStAmountSelect()));
			billDataLPG.setLpgTwUnitsSelected(dictServiceMgr.getDictItem(model.getLpgTwUnitsSelected()));
			return billDataLPG;
		}
		return null;
	}

	@Override
	public BillDataLPG updateBillDataLPG(BillDataLPG billDataLPG) {
		GdsapEvalBillDataLpg model = BeanUtils.getGdsapEvalBillDataLpg(billDataLPG);
		gdsapEvalBillDataLpgMapper.update(model);
		return billDataLPG;
	}
	
}
