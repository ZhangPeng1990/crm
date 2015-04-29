package uk.co.quidos.gdsap.evaluation.calculate.input;

/**
 * 为了对应C#返回的json，字段首字母大写
 * @author tyr
 *
 */
public class CalImprovement_EPC {

	private int ImprovementNo;
	private MeasureCode MeasureCode;
	private double[] IndicativeCost;
	private double TypicalSavingsPerYear;
	private double CustomSavingsPerYear;
	private GreenDealType GreenDeal;
	private double InUse;
	
	
	public int getImprovementNo() {
		return ImprovementNo;
	}
	public void setImprovementNo(int improvementNo) {
		ImprovementNo = improvementNo;
	}
	public MeasureCode getMeasureCode() {
		return MeasureCode;
	}
	public void setMeasureCode(MeasureCode measureCode) {
		MeasureCode = measureCode;
	}
	public double[] getIndicativeCost() {
		return IndicativeCost;
	}
	public void setIndicativeCost(double[] indicativeCost) {
		IndicativeCost = indicativeCost;
	}
	public double getTypicalSavingsPerYear() {
		return TypicalSavingsPerYear;
	}
	public void setTypicalSavingsPerYear(double typicalSavingsPerYear) {
		TypicalSavingsPerYear = typicalSavingsPerYear;
	}
	public double getCustomSavingsPerYear() {
		return CustomSavingsPerYear;
	}
	public void setCustomSavingsPerYear(double customSavingsPerYear) {
		CustomSavingsPerYear = customSavingsPerYear;
	}
	public GreenDealType getGreenDeal() {
		return GreenDeal;
	}
	public void setGreenDeal(GreenDealType greenDeal) {
		GreenDeal = greenDeal;
	}
	public double getInUse() {
		return InUse;
	}
	public void setInUse(double inUse) {
		InUse = inUse;
	}
}
