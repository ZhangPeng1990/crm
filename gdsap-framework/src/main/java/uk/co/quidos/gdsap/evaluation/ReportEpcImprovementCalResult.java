package uk.co.quidos.gdsap.evaluation;


import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

public class ReportEpcImprovementCalResult extends AbsBusinessObject
{

	private static final long serialVersionUID = 1183363287018883072L;

	private Long id;
	private String improvementType;
	private Integer improvementNumber;
	private String greenDealCategory;
	private Float estimatedSaving;
	private Float typicalSaving;
	private Float indicativeCostStart;
	private Float indicativeCostEnd;
	private Float inUseFactor;
	private Report report;
	private Recommendation recommendation;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImprovementType() {
		return improvementType;
	}
	public void setImprovementType(String improvementType) {
		this.improvementType = improvementType;
	}
	public Integer getImprovementNumber() {
		return improvementNumber;
	}
	public void setImprovementNumber(Integer improvementNumber) {
		this.improvementNumber = improvementNumber;
	}
	public String getGreenDealCategory() {
		return greenDealCategory;
	}
	public void setGreenDealCategory(String greenDealCategory) {
		this.greenDealCategory = greenDealCategory;
	}
	public Float getEstimatedSaving() {
		return estimatedSaving;
	}
	public void setEstimatedSaving(Float estimatedSaving) {
		this.estimatedSaving = estimatedSaving;
	}
	public Float getTypicalSaving() {
		return typicalSaving;
	}
	public void setTypicalSaving(Float typicalSaving) {
		this.typicalSaving = typicalSaving;
	}
	public Float getIndicativeCostStart() {
		return indicativeCostStart;
	}
	public void setIndicativeCostStart(Float indicativeCostStart) {
		this.indicativeCostStart = indicativeCostStart;
	}
	public Float getIndicativeCostEnd() {
		return indicativeCostEnd;
	}
	public void setIndicativeCostEnd(Float indicativeCostEnd) {
		this.indicativeCostEnd = indicativeCostEnd;
	}
	public Float getInUseFactor() {
		return inUseFactor;
	}
	public void setInUseFactor(Float inUseFactor) {
		this.inUseFactor = inUseFactor;
	}
	public Report getReport() {
		return report;
	}
	public void setReport(Report report) {
		this.report = report;
	}
	public Recommendation getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(Recommendation recommendation) {
		this.recommendation = recommendation;
	}
}
