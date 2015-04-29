package uk.co.quidos.gdsap.framework.user.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

public enum UserOrderField implements BaseEnum<Integer>{

	USERNAME("USER_NAME"),ASSESSOR_ID("ASSESSOR_ID"),
	Org_Cert_Number("ORGANISATION_CERTIFICATION_NUMBER"),
	EMAIL("EMAIL"),INSERT_DATE("INSERT_TIME"),
	ACCESS_LEVEL("ACCESS_LEVEL"),STATUS("USER_STATUS"),
	USER_TYPE("USER_TYPE");
	
	private UserOrderField(String dbField){
		this.dbField = dbField;
	}
	
	private String dbField;

	public String getDbField() {
		return dbField;
	}

	
	@Override
	public Integer getCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}
}
