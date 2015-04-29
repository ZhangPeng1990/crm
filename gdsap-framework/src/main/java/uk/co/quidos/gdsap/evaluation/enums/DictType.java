package uk.co.quidos.gdsap.evaluation.enums;

import java.util.ArrayList;

public enum DictType {
	//Occupants, showers and baths
	OSB_SHOWER_TYPE,
	//Heating systems and patterns
	MAIHS_HT_2,	//heating fuel
//	MAIHS_SAP_1,
	MAIHS_SAP_2,	//heating type description
	MAIHS_DEF,		
	SMHS_TYPE,
	SHS_TYPE,

	//Appliances and cooking
	AC_COOKER_TYPE,	
	AC_COOKING_FUEL,
	
	//Bill data reconciliation
	BILL_PERIOD,
	BILL_STANDING_CHARGE_UNIT,
	BILL_UNITS_AT_THIS_PRICE,
	BILL_ELECTRICITY_TARIFF,
	BILL_RENEWABLE_GENERATION_TYPE,
	BILL_RELIABLITY_LEVEL,//Electricity tariff
	BILL_Community_Heating_LEVEL,//Community heating
	BILL_OtherFuels_LEVEL,//OtherFuels
	BILL_CHARGING_BASIS,
	BILL_UNIT_OF_SALE;


	public String getId(){
		return this.toString();
	}
	public ArrayList<DictCondType> getDictCondTypes() {
		return DictCondType.values(this);
	}
	
	public static DictType getDictType(String id)
	{
		DictType[] dts = DictType.values();
		for (DictType dt : dts)
		{
			if (dt.getId().equals(id))
			{
				return dt;
			}
		}
		throw new IllegalArgumentException();
	}
}
