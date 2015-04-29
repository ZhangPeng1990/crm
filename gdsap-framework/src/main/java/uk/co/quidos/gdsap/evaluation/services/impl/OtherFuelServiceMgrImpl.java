/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import uk.co.quidos.gdsap.evaluation.OtherFuel;
import uk.co.quidos.gdsap.evaluation.Report;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapEvalOtherFuelMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapEvalOtherFuel;
import uk.co.quidos.gdsap.evaluation.services.DictServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.OtherFuelServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.ReportServiceMgr;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
@Service("otherFuelServiceMgr")
@Transactional
public class OtherFuelServiceMgrImpl extends AbsBusinessObjectServiceMgr implements OtherFuelServiceMgr
{
	@Autowired
	private GdsapEvalOtherFuelMapper gdsapEvalOtherFuelMapper;
	
	@Autowired
	private DictServiceMgr dictServiceMgr; 
	
	@Autowired
	ReportServiceMgr reportServiceMgr;
	
	public GdsapEvalOtherFuelMapper getGdsapEvalOtherFuelMapper()
	{
		return gdsapEvalOtherFuelMapper;
	}

	public void setGdsapEvalOtherFuelMapper(GdsapEvalOtherFuelMapper gdsapEvalOtherFuelMapper)
	{
		this.gdsapEvalOtherFuelMapper = gdsapEvalOtherFuelMapper;
	}
	

	public DictServiceMgr getDictServiceMgr() {
		return dictServiceMgr;
	}

	public void setDictServiceMgr(DictServiceMgr dictServiceMgr) {
		this.dictServiceMgr = dictServiceMgr;
	}

	public ReportServiceMgr getReportServiceMgr() {
		return reportServiceMgr;
	}

	public void setReportServiceMgr(ReportServiceMgr reportServiceMgr) {
		this.reportServiceMgr = reportServiceMgr;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.OtherFuelServiceMgr#addOtherFuel(uk.co.quidos.gdsap.evaluation.OtherFuel)
	 */
	@Override
	public void addOtherFuel(OtherFuel of)
	{
		Assert.notNull(of);
		gdsapEvalOtherFuelMapper.insert(BeanUtils.gOtherFuelToOtherFuel(of));
		this.reportServiceMgr.deleteSolutionsWithRecommendations(of.getReport());
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.OtherFuelServiceMgr#deleteOtherFuel(long)
	 */
	@Override
	public void deleteOtherFuel(long id)
	{
		gdsapEvalOtherFuelMapper.delete(id);
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.OtherFuelServiceMgr#getOtherFuel(long)
	 */
	@Override
	public OtherFuel getOtherFuel(long id)
	{
		GdsapEvalOtherFuel gdsapEvalOtherFuel = gdsapEvalOtherFuelMapper.load(id);
		if(gdsapEvalOtherFuel != null){
			OtherFuel otherFuel = BeanUtils.otherFuelToGOtherFuel(gdsapEvalOtherFuel);
			otherFuel.setReliablityLevel(gdsapEvalOtherFuel.getReliablityLevel() != null ? dictServiceMgr.getDictItem(gdsapEvalOtherFuel.getReliablityLevel()) : null);
			otherFuel.setUnitOfSale(gdsapEvalOtherFuel.getUnitOfSale() != null ? dictServiceMgr.getDictItem(gdsapEvalOtherFuel.getUnitOfSale()) : null);
			otherFuel.setPeriodSelect(gdsapEvalOtherFuel.getPeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalOtherFuel.getPeriodSelect()) : null);
			otherFuel.setFixedCostSelected(gdsapEvalOtherFuel.getFixedCostSelected() != null ? dictServiceMgr.getDictItem(gdsapEvalOtherFuel.getFixedCostSelected()) : null);
			otherFuel.setReport(reportServiceMgr.getReport(gdsapEvalOtherFuel.getReportId()));
			return otherFuel;
		}else{
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.OtherFuelServiceMgr#getOtherFuels(uk.co.quidos.gdsap.evaluation.Report, int)
	 */
	@Override
	public List<OtherFuel> getOtherFuels(Report report, int count)
	{
		if(count == 0){
			List<OtherFuel> listO = new ArrayList<OtherFuel>();
			List<GdsapEvalOtherFuel> lsitG = gdsapEvalOtherFuelMapper.findAll();
			for(GdsapEvalOtherFuel gdsapEvalOtherFuel : lsitG){
				OtherFuel otherFuel = BeanUtils.otherFuelToGOtherFuel(gdsapEvalOtherFuel);
				otherFuel.setReliablityLevel(gdsapEvalOtherFuel.getReliablityLevel() != null ? dictServiceMgr.getDictItem(gdsapEvalOtherFuel.getReliablityLevel()) : null);
				otherFuel.setUnitOfSale(gdsapEvalOtherFuel.getUnitOfSale() != null ? dictServiceMgr.getDictItem(gdsapEvalOtherFuel.getUnitOfSale()) : null);
				otherFuel.setPeriodSelect(gdsapEvalOtherFuel.getPeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalOtherFuel.getPeriodSelect()) : null);
				otherFuel.setFixedCostSelected(gdsapEvalOtherFuel.getFixedCostSelected() != null ? dictServiceMgr.getDictItem(gdsapEvalOtherFuel.getFixedCostSelected()) : null);
				otherFuel.setReport(reportServiceMgr.getReport(gdsapEvalOtherFuel.getReportId()));
				listO.add(otherFuel);
			}
			return listO;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.evaluation.services.OtherFuelServiceMgr#updateOtherFuel(uk.co.quidos.gdsap.evaluation.OtherFuel)
	 */
	@Override
	public void updateOtherFuel(OtherFuel of)
	{
		Assert.notNull(of);
		gdsapEvalOtherFuelMapper.update(BeanUtils.gOtherFuelToOtherFuel(of));
		this.reportServiceMgr.deleteSolutionsWithRecommendations(of.getReport());
	}
	
	public List<OtherFuel> getOtherFuelsByReportId(Long reportId){
		List<GdsapEvalOtherFuel> listG = gdsapEvalOtherFuelMapper.findByReportId(reportId);
		if(!CollectionUtils.isEmpty(listG)){
			List<OtherFuel> listO = new ArrayList<OtherFuel>();
			for(GdsapEvalOtherFuel gdsapEvalOtherFuel : listG){
				OtherFuel otherFuel = BeanUtils.otherFuelToGOtherFuel(gdsapEvalOtherFuel);
				otherFuel.setReliablityLevel(gdsapEvalOtherFuel.getReliablityLevel() != null ? dictServiceMgr.getDictItem(gdsapEvalOtherFuel.getReliablityLevel()) : null);
				otherFuel.setUnitOfSale(gdsapEvalOtherFuel.getUnitOfSale() != null ? dictServiceMgr.getDictItem(gdsapEvalOtherFuel.getUnitOfSale()) : null);
				otherFuel.setPeriodSelect(gdsapEvalOtherFuel.getPeriodSelect() != null ? dictServiceMgr.getDictItem(gdsapEvalOtherFuel.getPeriodSelect()) : null);
				otherFuel.setFixedCostSelected(gdsapEvalOtherFuel.getFixedCostSelected() != null ? dictServiceMgr.getDictItem(gdsapEvalOtherFuel.getFixedCostSelected()) : null);
				otherFuel.setReport(reportServiceMgr.getReport(gdsapEvalOtherFuel.getReportId()));
				listO.add(otherFuel);
			}
			return listO;
		}
		return null;
	}
	
	public void delOtherFuelByreport(Long reportId){
		gdsapEvalOtherFuelMapper.delteByReportId(reportId);
	}

}
