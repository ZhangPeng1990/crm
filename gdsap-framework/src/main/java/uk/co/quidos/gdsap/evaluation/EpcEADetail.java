/**
 * 
 */
package uk.co.quidos.gdsap.evaluation;

import java.util.Date;

import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

/**
 * @author peng.shi
 *
 */
public class EpcEADetail extends AbsBusinessObject
{
	private static final long serialVersionUID = -5062177203698546969L;

	private Report report;

    private String fullName;

    private String notifyLodgement;

    private String contactAddress1;

    private String contactAddress2;

    private String contactAddress3;

    private String posttown;

    private String postcode;

    private String website;

    private String email;

    private String fax;

    private String telephone;

    private String companyName;

    private String schemeName;

    private String schemeWebSite;

    private String certificateNumber;
    
    private String insurer;

    private String policyNo;

    private Date effectiveDate;

    private Date expiryDate;

    private String piLimit;
    
	@Override
	public Long getId()
	{
		if (report !=null)
		{
			return report.getId();
		}
		return null;
	}

	public String getInsurer()
	{
		return insurer;
	}

	public void setInsurer(String insurer)
	{
		this.insurer = insurer;
	}

	public String getPolicyNo()
	{
		return policyNo;
	}

	public void setPolicyNo(String policyNo)
	{
		this.policyNo = policyNo;
	}

	public Date getEffectiveDate()
	{
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate)
	{
		this.effectiveDate = effectiveDate;
	}

	public Date getExpiryDate()
	{
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate)
	{
		this.expiryDate = expiryDate;
	}

	public String getPiLimit()
	{
		return piLimit;
	}

	public void setPiLimit(String piLimit)
	{
		this.piLimit = piLimit;
	}

	public Report getReport()
	{
		return report;
	}

	public void setReport(Report report)
	{
		this.report = report;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	public String getNotifyLodgement()
	{
		return notifyLodgement;
	}

	public void setNotifyLodgement(String notifyLodgement)
	{
		this.notifyLodgement = notifyLodgement;
	}

	public String getContactAddress1()
	{
		return contactAddress1;
	}

	public void setContactAddress1(String contactAddress1)
	{
		this.contactAddress1 = contactAddress1;
	}

	public String getContactAddress2()
	{
		return contactAddress2;
	}

	public void setContactAddress2(String contactAddress2)
	{
		this.contactAddress2 = contactAddress2;
	}

	public String getContactAddress3()
	{
		return contactAddress3;
	}

	public void setContactAddress3(String contactAddress3)
	{
		this.contactAddress3 = contactAddress3;
	}

	public String getPosttown()
	{
		return posttown;
	}

	public void setPosttown(String posttown)
	{
		this.posttown = posttown;
	}

	public String getPostcode()
	{
		return postcode;
	}

	public void setPostcode(String postcode)
	{
		this.postcode = postcode;
	}

	public String getWebsite()
	{
		return website;
	}

	public void setWebsite(String website)
	{
		this.website = website;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getFax()
	{
		return fax;
	}

	public void setFax(String fax)
	{
		this.fax = fax;
	}

	public String getTelephone()
	{
		return telephone;
	}

	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}

	public String getCompanyName()
	{
		return companyName;
	}

	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}

	public String getSchemeName()
	{
		return schemeName;
	}

	public void setSchemeName(String schemeName)
	{
		this.schemeName = schemeName;
	}

	public String getSchemeWebSite()
	{
		return schemeWebSite;
	}

	public void setSchemeWebSite(String schemeWebSite)
	{
		this.schemeWebSite = schemeWebSite;
	}

	public String getCertificateNumber()
	{
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber)
	{
		this.certificateNumber = certificateNumber;
	}
    
    
}
