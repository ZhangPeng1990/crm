package uk.co.quidos.gdsap.evaluation.calculate.output;

import com.google.gson.Gson;

public class RdSAPTrunk {

	private String xmlString;
	private String country;
	private Integer version;
	 
	public RdSAPTrunk(String xmlString, String country, Integer version)
    {
		this.xmlString = xmlString;
    	this.country = country;
    	this.version = version;
    }
	
	public String toJosn()
    {
    	String json = null;
    	Gson gson = new Gson();
    	json = gson.toJson(this);
    	return json;
    }
	
	public String getXmlString() {
		return xmlString;
	}
	public void setXmlString(String xmlString) {
		this.xmlString = xmlString;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	 
	 
}
