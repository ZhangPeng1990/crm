package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapEvalReportEpcImprovementResult extends AbstractDO {

	private static final long serialVersionUID = 6586668368602224369L;
	
	private Long id;
	
	private String improvementType;
	
	private Integer improvementNumber;
	
	private String greenDealCategory;
	
	private Float estimatedSaving;
	
	private Float typicalSaving;
	
	private Float indicativeCostStart;
	
	private Float indicativeCostEnd;
	
	private Float inUseFactor;
	
	private Long reportId;
	
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
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
}
