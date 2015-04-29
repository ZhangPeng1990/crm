package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapEvalBillDataLpg extends AbstractDO {

	private static final long serialVersionUID = -3527546951442586158L;

	private Long reportId;
	private Integer lpgReliablityLevel;
	private Float lpgGasUsed;
	private Integer lpgPeriodSelect;
	private Float lpgPeriod;
	private Integer lpgChargingBasis;
	private Integer lpgStAmountSelect;
	private Float lpgStAmount;
	private Float lpgStUnitPrice;
	private Float lpgTwInitialUnit;
	private Float lpgTwUnits;
	private Integer lpgTwUnitsSelected;
	private Float lpgTwFollowOn;
	private Integer lpgVatAble;
	private Integer lpgUnusualEnergyUsingable;
	private String lpgUnusualEnergyUsingableDes;

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public Integer getLpgReliablityLevel() {
		return lpgReliablityLevel;
	}

	public void setLpgReliablityLevel(Integer lpgReliablityLevel) {
		this.lpgReliablityLevel = lpgReliablityLevel;
	}

	public Float getLpgGasUsed() {
		return lpgGasUsed;
	}

	public void setLpgGasUsed(Float lpgGasUsed) {
		this.lpgGasUsed = lpgGasUsed;
	}

	public Integer getLpgPeriodSelect() {
		return lpgPeriodSelect;
	}

	public void setLpgPeriodSelect(Integer lpgPeriodSelect) {
		this.lpgPeriodSelect = lpgPeriodSelect;
	}

	public Float getLpgPeriod() {
		return lpgPeriod;
	}

	public void setLpgPeriod(Float lpgPeriod) {
		this.lpgPeriod = lpgPeriod;
	}

	public Integer getLpgChargingBasis() {
		return lpgChargingBasis;
	}

	public void setLpgChargingBasis(Integer lpgChargingBasis) {
		this.lpgChargingBasis = lpgChargingBasis;
	}

	public Integer getLpgStAmountSelect() {
		return lpgStAmountSelect;
	}

	public void setLpgStAmountSelect(Integer lpgStAmountSelect) {
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

	public Integer getLpgTwUnitsSelected() {
		return lpgTwUnitsSelected;
	}

	public void setLpgTwUnitsSelected(Integer lpgTwUnitsSelected) {
		this.lpgTwUnitsSelected = lpgTwUnitsSelected;
	}

	public Float getLpgTwFollowOn() {
		return lpgTwFollowOn;
	}

	public void setLpgTwFollowOn(Float lpgTwFollowOn) {
		this.lpgTwFollowOn = lpgTwFollowOn;
	}

	public Integer getLpgVatAble() {
		return lpgVatAble;
	}

	public void setLpgVatAble(Integer lpgVatAble) {
		this.lpgVatAble = lpgVatAble;
	}

	public Integer getLpgUnusualEnergyUsingable() {
		return lpgUnusualEnergyUsingable;
	}

	public void setLpgUnusualEnergyUsingable(Integer lpgUnusualEnergyUsingable) {
		this.lpgUnusualEnergyUsingable = lpgUnusualEnergyUsingable;
	}

	public String getLpgUnusualEnergyUsingableDes() {
		return lpgUnusualEnergyUsingableDes;
	}

	public void setLpgUnusualEnergyUsingableDes(
			String lpgUnusualEnergyUsingableDes) {
		this.lpgUnusualEnergyUsingableDes = lpgUnusualEnergyUsingableDes;
	}
}