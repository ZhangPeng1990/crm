package uk.co.quidos.gdsap.evaluation.calculate.output;

import java.io.FileInputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import com.google.gson.Gson;

public class OATrunk {

	private String RdSAPXml;
	private String OAXml;
	private String GDIPXml;
	private String country;
    private Integer version;
    
    public OATrunk(String RdSAPXml, String OAXml, String GDIPXml, String country, Integer version)
    {
    	this.RdSAPXml = RdSAPXml;
    	this.OAXml = OAXml;
    	this.GDIPXml = GDIPXml;
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
    
    public OATrunk toOATrunk(String json)
    {
    	Gson gson = new Gson();
    	OATrunk oa = (OATrunk) gson.fromJson(json, OATrunk.class);
    	return oa;
    }
    
    public static void main(String[] args) {
    	Document calDocument = getDocumentByURI("D:\\VS2012\\OA_LIG17_output\\OA-1_CAL.xml");
    	OATrunk oa = new OATrunk(null,calDocument.asXML(),null,"EAW",992);
    	System.out.println(oa.toJosn());
    	
    	OATrunk oa2 = oa.toOATrunk(oa.toJosn());
    	System.out.println(oa2.getOAXml());
	}
    
    public static Document getDocumentByURI(String path){
		SAXReader reader = new SAXReader();
        Document document = null;
		try {
			document = reader.read(new FileInputStream(path));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return document;
	}
    
	public String getRdSAPXml() {
		return RdSAPXml;
	}
	public void setRdSAPXml(String rdSAPXml) {
		RdSAPXml = rdSAPXml;
	}
	public String getOAXml() {
		return OAXml;
	}
	public void setOAXml(String oAXml) {
		OAXml = oAXml;
	}
	public String getGDIPXml() {
		return GDIPXml;
	}
	public void setGDIPXml(String gDIPXml) {
		GDIPXml = gDIPXml;
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
