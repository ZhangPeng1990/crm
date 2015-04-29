package uk.co.quidos.gdsap.framework.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

public enum EpcVersion implements BaseEnum<Integer>{
	
	EPC_OLD("epc version 16.x",0, 991),EPC_NEW("epc version 17.x",1, 992);
	
	private EpcVersion(String desc, Integer code, Integer versionNum)
	{
		this.desc = desc;
		this.code = code;
		this.versionNum = versionNum;
	}

	private String desc;
	private Integer code;
	private Integer versionNum;

	@Override
	public Integer getCode() {
		return this.code;
	}

	@Override
	public String getDesc() {
		return this.desc;
	}

	public Integer getVersionNum() {
		return versionNum;
	}
}
