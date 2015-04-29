package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

/**
 * @author peng.shi
 *
 */
public class GdsapEvalImprovement extends AbstractDO {

	private static final long serialVersionUID = 2237407262003051443L;
	private Long id;
    private Integer improvementCategory;
    private String improvementType;
    private Float typicalSaving;
    private Float energyPerformanceRating;
    private Float environmentalImpactRating;
    private Integer recommendationCode;
    private String indicativeCost;
    private Integer greenDealCategory;
    private Integer scope;
    private Long reportId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getImprovementCategory() {
        return improvementCategory;
    }

    public void setImprovementCategory(Integer improvementCategory) {
        this.improvementCategory = improvementCategory;
    }

    public String getImprovementType() {
        return improvementType;
    }

    public void setImprovementType(String improvementType) {
        this.improvementType = improvementType;
    }

    public Float getTypicalSaving() {
        return typicalSaving;
    }

    public void setTypicalSaving(Float typicalSaving) {
        this.typicalSaving = typicalSaving;
    }

    public Float getEnergyPerformanceRating() {
        return energyPerformanceRating;
    }

    public void setEnergyPerformanceRating(Float energyPerformanceRating) {
        this.energyPerformanceRating = energyPerformanceRating;
    }

    public Float getEnvironmentalImpactRating() {
        return environmentalImpactRating;
    }

    public void setEnvironmentalImpactRating(Float environmentalImpactRating) {
        this.environmentalImpactRating = environmentalImpactRating;
    }

    public Integer getRecommendationCode() {
        return recommendationCode;
    }

    public void setRecommendationCode(Integer recommendationCode) {
        this.recommendationCode = recommendationCode;
    }

    public String getIndicativeCost() {
        return indicativeCost;
    }

    public void setIndicativeCost(String indicativeCost) {
        this.indicativeCost = indicativeCost;
    }

    public Integer getGreenDealCategory() {
        return greenDealCategory;
    }

    public void setGreenDealCategory(Integer greenDealCategory) {
        this.greenDealCategory = greenDealCategory;
    }

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }
}