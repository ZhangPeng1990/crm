/**
 * 
 */
package uk.co.quidos.gdsap.evaluation;

import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author shipeng
 *
 */
public class BillDataLPG extends AbsBusinessObject {

	private static final long serialVersionUID = -8243683490942129233L;
	
	private Report report;
	private DictItem lpgReliablityLevel;
	private Float lpgGasUsed;
	private DictItem lpgPeriodSelect;
	private Float lpgPeriod;
	private DictItem lpgChargingBasis;
	private DictItem lpgStAmountSelect;
	private Float lpgStAmount;
	private Float lpgStUnitPrice;
	private Float lpgTwInitialUnit;
	private Float lpgTwUnits;
	private DictItem lpgTwUnitsSelected;
	private Float lpgTwFollowOn;
	private YesNo lpgVatAble;
	private YesNo lpgUnusualEnergyUsingable;
	private String lpgUnusualEnergyUsingableDes;
	
	@Override
	public Long getId() {
		if (this.report != null) {
			return this.report.getId();
		}
		return null;
	}
	public Report getReport() {
		return report;
	}
	public void setReport(Report report) {
		this.report = report;
	}
	public DictItem getLpgReliablityLevel() {
		return lpgReliablityLevel;
	}
	public void setLpgReliablityLevel(DictItem lpgReliablityLevel) {
		this.lpgReliablityLevel = lpgReliablityLevel;
	}
	public Float getLpgGasUsed() {
		return lpgGasUsed;
	}
	public void setLpgGasUsed(Float lpgGasUsed) {
		this.lpgGasUsed = lpgGasUsed;
	}
	public DictItem getLpgPeriodSelect() {
		return lpgPeriodSelect;
	}
	public void setLpgPeriodSelect(DictItem lpgPeriodSelect) {
		this.lpgPeriodSelect = lpgPeriodSelect;
	}
	public Float getLpgPeriod() {
		return lpgPeriod;
	}
	public void setLpgPeriod(Float lpgPeriod) {
		this.lpgPeriod = lpgPeriod;
	}
	public DictItem getLpgChargingBasis() {
		return lpgChargingBasis;
	}
	public void setLpgChargingBasis(DictItem lpgChargingBasis) {
		this.lpgChargingBasis = lpgChargingBasis;
	}
	public DictItem getLpgStAmountSelect() {
		return lpgStAmountSelect;
	}
	public void setLpgStAmountSelect(DictItem lpgStAmountSelect) {
		this.lpgStAmountSelect = lpgStAmountSelect;
	}
	public Float getLpgStAmount() {
		return lpgStAmount;
	}
	public void setLpgStAmount(Float lpgStAmount) {
		this.lpgStAmount = lpgStAmount;
	}
	public Float getLpgStUnitPrice() {
		return lpgStUnitPrice;
	}
	public void setLpgStUnitPrice(Float lpgStUnitPrice) {
		this.lpgStUnitPrice = lpgStUnitPrice;
	}
	public Float getLpgTwInitialUnit() {
		return lpgTwInitialUnit;
	}
	public void setLpgTwInitialUnit(Float lpgTwInitialUnit) {
		this.lpgTwInitialUnit = lpgTwInitialUnit;
	}
	public Float getLpgTwUnits() {
		return lpgTwUnits;
	}
	public void setLpgTwUnits(Float lpgTwUnits) {
		this.lpgTwUnits = lpgTwUnits;
	}
	public DictItem getLpgTwUnitsSelected() {
		return lpgTwUnitsSelected;
	}
	public void setLpgTwUnitsSelected(DictItem lpgTwUnitsSelected) {
		this.lpgTwUnitsSelected = lpgTwUnitsSelected;
	}
	public Float getLpgTwFollowOn() {
		return lpgTwFollowOn;
	}
	public void setLpgTwFollowOn(Float lpgTwFollowOn) {
		this.lpgTwFollowOn = lpgTwFollowOn;
	}
	public YesNo getLpgVatAble() {
		return lpgVatAble;
	}
	public void setLpgVatAble(YesNo lpgVatAble) {
		this.lpgVatAble = lpgVatAble;
	}
	public YesNo getLpgUnusualEnergyUsingable() {
		return lpgUnusualEnergyUsingable;
	}
	public void setLpgUnusualEnergyUsingable(YesNo lpgUnusualEnergyUsingable) {
		this.lpgUnusualEnergyUsingable = lpgUnusualEnergyUsingable;
	}
	public String getLpgUnusualEnergyUsingableDes() {
		return lpgUnusualEnergyUsingableDes;
	}
	public void setLpgUnusualEnergyUsingableDes(String lpgUnusualEnergyUsingableDes) {
		this.lpgUnusualEnergyUsingableDes = lpgUnusualEnergyUsingableDes;
	}
	

}
