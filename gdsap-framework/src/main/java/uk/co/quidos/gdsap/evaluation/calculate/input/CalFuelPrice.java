package uk.co.quidos.gdsap.evaluation.calculate.input;

public class CalFuelPrice {

	private String Name;
	private String Unit;
	private CalFuelType CalFuelType;
	private int	heatingFuelCode;
	private double Use;
	private double Cost;
	private double StandingChargeInput;
	private double StandingChargeTable;
	private double UitPriceInput;
	private double UitPriceTable;
	private final int UnitCodeTable=1;
	private Integer UnitCodeInput;
	
	
	public CalFuelType getCalFuelType() {
		return CalFuelType;
	}
	public void setCalFuelType(CalFuelType calFuelType) {
		CalFuelType = calFuelType;
	}
	public int getHeatingFuelCode() {
		return heatingFuelCode;
	}
	public void setHeatingFuelCode(int heatingFuelCode) {
		this.heatingFuelCode = heatingFuelCode;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
	public double getUse() {
		return Use;
	}
	public void setUse(double use) {
		Use = use;
	}
	public double getCost() {
		return Cost;
	}
	public void setCost(double cost) {
		Cost = cost;
	}
	public double getStandingChargeInput() {
		return StandingChargeInput;
	}
	public void setStandingChargeInput(double standingChargeInput) {
		StandingChargeInput = standingChargeInput;
	}
	public double getStandingChargeTable() {
		return StandingChargeTable;
	}
	public void setStandingChargeTable(double standingChargeTable) {
		StandingChargeTable = standingChargeTable;
	}
	public double getUitPriceInput() {
		return UitPriceInput;
	}
	public void setUitPriceInput(double uitPriceInput) {
		UitPriceInput = uitPriceInput;
	}
	public double getUitPriceTable() {
		return UitPriceTable;
	}
	public void setUitPriceTable(double uitPriceTable) {
		UitPriceTable = uitPriceTable;
	}
	public Integer getUnitCodeInput() {
		return UnitCodeInput;
	}
	public void setUnitCodeInput(Integer unitCodeInput) {
		UnitCodeInput = unitCodeInput;
	}
	public int getUnitCodeTable() {
		return UnitCodeTable;
	}
}
