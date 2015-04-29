package uk.co.quidos.gdsap.evaluation.calculate.input;

import uk.co.quidos.common.util.F;

public class PerFuelSaving {

	private int code;
	private double kwhSaving;
	private double standingChargeFraction;

	public double getKwhSaving() {
		return F.f4(kwhSaving);
	}
	public void setKwhSaving(double kwhSaving) {
		this.kwhSaving = kwhSaving;
	}
	public double getStandingChargeFraction() {
		return standingChargeFraction;
	}
	public void setStandingChargeFraction(double standingChargeFraction) {
		this.standingChargeFraction = standingChargeFraction;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getCode() {
		return code;
	}
}
