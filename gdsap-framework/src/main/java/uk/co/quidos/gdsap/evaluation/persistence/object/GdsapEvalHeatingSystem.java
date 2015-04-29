package uk.co.quidos.gdsap.evaluation.persistence.object;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapEvalHeatingSystem extends AbstractDO
{
	private static final long serialVersionUID = -848072527878116459L;
	
	private Long reportId;
    private Integer knownable;
    private Float temperature;
    private Integer mHs;
    private Integer mHf;
    private Integer mHt;
    private Integer smHs;
    private Integer smHf;
    private Integer smHt;
    private Integer sHs;
    private Integer sHf;
    private Integer sHt;

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Integer getKnownable() {
        return knownable;
    }

    public void setKnownable(Integer knownable) {
        this.knownable = knownable;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Integer getmHs() {
        return mHs;
    }

    public void setmHs(Integer mHs) {
        this.mHs = mHs;
    }

    public Integer getmHf() {
        return mHf;
    }

    public void setmHf(Integer mHf) {
        this.mHf = mHf;
    }

    public Integer getmHt() {
        return mHt;
    }

    public void setmHt(Integer mHt) {
        this.mHt = mHt;
    }

    public Integer getSmHs() {
        return smHs;
    }

    public void setSmHs(Integer smHs) {
        this.smHs = smHs;
    }

    public Integer getSmHf() {
        return smHf;
    }

    public void setSmHf(Integer smHf) {
        this.smHf = smHf;
    }

    public Integer getSmHt() {
        return smHt;
    }

    public void setSmHt(Integer smHt) {
        this.smHt = smHt;
    }

    public Integer getsHs() {
        return sHs;
    }

    public void setsHs(Integer sHs) {
        this.sHs = sHs;
    }

    public Integer getsHf() {
        return sHf;
    }

    public void setsHf(Integer sHf) {
        this.sHf = sHf;
    }

    public Integer getsHt() {
        return sHt;
    }

    public void setsHt(Integer sHt) {
        this.sHt = sHt;
    }
}