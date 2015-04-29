package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapEvalHeatProportion extends AbstractDO
{
	private static final long serialVersionUID = -7107404692390952407L;
	
	private Long id;
    private Long reportId;
    private Integer roomScope;
    private Integer main1;
    private Integer main2;
    private Integer secondary;
    private Integer heatedPartially;
    private Integer notable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Integer getRoomScope() {
        return roomScope;
    }

    public void setRoomScope(Integer roomScope) {
        this.roomScope = roomScope;
    }

    public Integer getMain1() {
        return main1;
    }

    public void setMain1(Integer main1) {
        this.main1 = main1;
    }

    public Integer getMain2() {
        return main2;
    }

    public void setMain2(Integer main2) {
        this.main2 = main2;
    }

    public Integer getSecondary() {
        return secondary;
    }

    public void setSecondary(Integer secondary) {
        this.secondary = secondary;
    }

    public Integer getHeatedPartially() {
        return heatedPartially;
    }

    public void setHeatedPartially(Integer heatedPartially) {
        this.heatedPartially = heatedPartially;
    }

    public Integer getNotable() {
        return notable;
    }

    public void setNotable(Integer notable) {
        this.notable = notable;
    }
}