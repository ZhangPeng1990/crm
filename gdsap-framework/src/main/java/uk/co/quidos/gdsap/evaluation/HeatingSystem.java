/**
 * 
 */
package uk.co.quidos.gdsap.evaluation;

import org.springframework.util.Assert;

import uk.co.quidos.gdsap.evaluation.utils.HeatingSystemUtils;
import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 *
 */
public class HeatingSystem extends AbsBusinessObject
{
	private static final long serialVersionUID = -6327714262567134192L;
	
	private Report report;
    private YesNo knownable;
    private float temperature;
    private DictItem mHs;
    private DictItem mHf;
    private DictItem mHt;
    private DictItem smHs;
    private DictItem smHf;
    private DictItem smHt;
    private DictItem sHs;
    private DictItem sHf;
    private DictItem sHt;
    
    public int getMhsXmlCode()
    {
    	if (this.mHs.getCalCode() == 0)
    	{
    		return 1;
    	}
    	if (this.mHs.getCalCode() == 1)
    	{
    		return 0;
    	}
    	Assert.notNull(this.mHt);
    	return HeatingSystemUtils.getXmlCode(Integer.parseInt(this.mHt.getLodgeCode()));
    }
    
    public int getSmhsXmlCode()
    {
    	if (this.smHs.getCalCode() == 0)
    	{
    		return 1;
    	}
    	if (this.smHs.getCalCode() == 1)
    	{
    		return 0;
    	}
    	Assert.notNull(smHt);
    	return HeatingSystemUtils.getXmlCode(Integer.parseInt(this.smHt.getLodgeCode()));
    }
    
    public int getShtXmlCode()
    {
    	if (this.sHs.getCalCode() == 0)
    	{
    		return 1;
    	}
    	if (this.sHs.getCalCode() == 1)
    	{
    		return 0;
    	}
    	Assert.notNull(this.sHt);
    	return HeatingSystemUtils.getXmlCode(Integer.parseInt(this.sHt.getLodgeCode()));
    }
    
	public Report getReport()
	{
		return report;
	}

	public YesNo getKnownable()
	{
		return knownable;
	}

	public float getTemperature()
	{
		return temperature;
	}

	public DictItem getmHs()
	{
		return mHs;
	}

	public DictItem getmHf()
	{
		return mHf;
	}

	public DictItem getmHt()
	{
		return mHt;
	}

	public DictItem getSmHs()
	{
		return smHs;
	}

	public DictItem getSmHf()
	{
		return smHf;
	}

	public DictItem getSmHt()
	{
		return smHt;
	}

	public DictItem getsHs()
	{
		return sHs;
	}

	public DictItem getsHf()
	{
		return sHf;
	}

	public DictItem getsHt()
	{
		return sHt;
	}

	public void setReport(Report report)
	{
		this.report = report;
	}

	public void setKnownable(YesNo knownable)
	{
		this.knownable = knownable;
	}

	public void setTemperature(float temperature)
	{
		this.temperature = temperature;
	}

	public void setmHs(DictItem mHs)
	{
		this.mHs = mHs;
	}

	public void setmHf(DictItem mHf)
	{
		this.mHf = mHf;
	}

	public void setmHt(DictItem mHt)
	{
		this.mHt = mHt;
	}

	public void setSmHs(DictItem smHs)
	{
		this.smHs = smHs;
	}

	public void setSmHf(DictItem smHf)
	{
		this.smHf = smHf;
	}

	public void setSmHt(DictItem smHt)
	{
		this.smHt = smHt;
	}

	public void setsHs(DictItem sHs)
	{
		this.sHs = sHs;
	}

	public void setsHf(DictItem sHf)
	{
		this.sHf = sHf;
	}

	public void setsHt(DictItem sHt)
	{
		this.sHt = sHt;
	}
	
	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject#getId()
	 */
	@Override
	public Long getId()
	{
		if (report != null)
		{
			return report.getId();
		}
		return null;
	}

}
