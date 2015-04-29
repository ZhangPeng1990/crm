package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapEvalRecommendation extends AbstractDO
{
	private static final long serialVersionUID = 6839784518500166100L;
	
	private Long id;
	private Integer recommendationCode;
	private Integer recommendationType;
	private Long reportId;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Integer getRecommendationCode()
	{
		return recommendationCode;
	}

	public void setRecommendationCode(Integer recommendationCode)
	{
		this.recommendationCode = recommendationCode;
	}
	public Integer getRecommendationType() {
		return recommendationType;
	}
	public void setRecommendationType(Integer recommendationType) {
		this.recommendationType = recommendationType;
	}

	public Long getReportId()
	{
		return reportId;
	}

	public void setReportId(Long reportId)
	{
		this.reportId = reportId;
	}
}