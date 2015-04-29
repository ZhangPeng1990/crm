package uk.co.quidos.gdsap.evaluation.enums;

import uk.co.quidos.gdsap.framework.enums.UserType;
import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 */
public enum ReportStatus implements BaseEnum<Integer>
{
	//code 请按状态的级别升序
	GDIP_WebService_Canceled("GDIP Canceled by third-party webService", -10),
	GDAR_WebService_Canceled("Canceled by third-party webService", -5),
	GDIP_Canceled("GDIP Canceled", -2),
	Canceled("Canceled", -1),
	In_Process("In Process", 0), 
	Lodging("Lodging",1), 
	Lodged("Lodged", 10),
	
	GDIP_In_Process("GDIP In Process", 15), 
	GDIP_Lodging("GDIP Lodging",20), 
	GDIP_Lodged_By_Assessor("GDIP Lodged By Assessor", 25),
	GDIP_reLodged_By_Assessor("GDIP reLodged By Assessor", 26),
	GDIP_Lodged_By_Provider("GDIP Lodged By Provider", 30),
	GDIP_WebService_Lodged("GDIP Lodged By WebService from third-party webService", 35),
	GDIP_reLodged_By_Provider("GDIP reLodged By Provider", 40);

	private ReportStatus(String desc, Integer code)
	{
		this.desc = desc;
		this.code = code;
	}

	public static ReportStatus[] vals(UserType type)
	{
		if(UserType.GDA.equals(type))
		{
			return new ReportStatus[]{ReportStatus.In_Process, ReportStatus.Lodged};
		}
		
		if(UserType.GDP.equals(type))
		{
			return new ReportStatus[]{ReportStatus.GDIP_Lodged_By_Provider, ReportStatus.GDIP_reLodged_By_Provider};
		}
		
		return ReportStatus.values();
	}
	
	private String desc;
	private Integer code;

	@Override
	public Integer getCode()
	{
		return this.code;
	}

	@Override
	public String getDesc()
	{
		return this.desc;
	}

}
