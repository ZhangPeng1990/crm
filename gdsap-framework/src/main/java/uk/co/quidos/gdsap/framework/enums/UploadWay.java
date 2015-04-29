package uk.co.quidos.gdsap.framework.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * report的来源或新建方式，code直接和数据库关联，勿修改
 * @author tyr
 *
 */
public enum UploadWay implements BaseEnum<Integer>{
	
	By_Xml_File("By Xml File",1), // assessor 通过页面直接上传一个EPC xml文件
	Retrieval_From_Repo("Retrieval From Repo",0), // assessor 在页面输入一个ECP rrn的方式来检索一份EPC xml文件
	Third_Party_Web_Service_pushOAToQuidos("Third Party Web Service pushOAToQuidos",2), // 第三方公司通过调用Qube 提供的pushOAToQuidos webservice新建
	Third_Party_Web_Service_lodgeOADirectlyToLandmark("third-party webservice lodged",6), // 第三方公司通过调用Qube 提供的lodgeOADirectlyToLandmark webservice新建
	Third_Party_Web_Service_pushGDIPToQuidos("Third Party Web Service pushGDIPToQuidos",3), // 第三方公司通过调用Qube 提供的pushGDIPToQuidos webservice新建
	Third_Party_Web_Service_pushGDIPToQuidos_AndLodge("Third Party Web Service pushGDIPToQuidos and Lodge",5), // 第三方公司通过调用Qube 提供的pushGDIPToQuidos_AndLodge webservice新建
	GDP_Retrieval_From_Repo("Retrieval From Repo",4), // GDP 用户在页面通过输入OA RRN 检索新建
	;
	
	
	private String desc;
	private Integer code;
	
	private UploadWay(String desc,Integer code)
	{
		this.desc = desc;
		this.code = code;
	}

	public Integer getCode() {
		return this.code;
	}

	public String getDesc() {
		// TODO Auto-generated method stub
		return this.desc;
	}
	
	public static UploadWay[] valuesForFrontEnd()
	{
		return new UploadWay[]{UploadWay.By_Xml_File, UploadWay.Retrieval_From_Repo};
	}
}
