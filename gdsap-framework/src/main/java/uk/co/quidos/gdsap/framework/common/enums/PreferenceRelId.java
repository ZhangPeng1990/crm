/**
 * 
 */
package uk.co.quidos.gdsap.framework.common.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 *
 */
public enum PreferenceRelId implements BaseEnum<String>
{
	Replace_Part_GDAR_LODGE_XML_TEMP("Replace_Part_GDAR_LODGE_XML_TEMP","Replace Part Gdoa lodge xml template"),
	GDAR_LODGE_XML_TEMPLATE("GDAR_LODGE_XML_TEMPLATE","Gdoa lodge xml template"),
	GDIP_LODGE_XML_TEMPLATE("GDIP_LODGE_XML_TEMPLATE","GDIP_LODGE_XML_TEMPLATE"),
	GDOA_LodgementError_TEMPLATE("GDOA_LodgementError_TEMPLATE","GDOA_LodgementError_TEMPLATE"),
	Quidos_Service_Response_TEMPLATE("Quidos_Service_Response_TEMPLATE","Quidos_Service_Response_TEMPLATE"),
	;
	
	private String code;
	private String desc;
	
	private PreferenceRelId(String code,String desc)
	{
		this.code = code;
		this.desc = desc;
	}
	@Override
	public String getCode()
	{
		return this.code;
	}

	@Override
	public String getDesc()
	{
		return this.desc;
	}
	
}
