package uk.co.quidos.gdsap.evaluation.enums;

import java.util.ArrayList;

public enum DictCondType {
	
	//MAIHS MAIHS2 SEC_HS
	MAIHS_HT_2_T1("Define room heater | MAIHS_HT_2:26,27,3,17,28,33,5,15,6,9,29", DictType.MAIHS_HT_2),

	MAIHS_SAP_2_T1("Define room heater and MAIHS_HT_2==26, 27, 3, 17 | MAIHS_SAP_2: 601, 603, 605, 607, 609~612", DictType.MAIHS_SAP_2),
	MAIHS_SAP_2_T2("Define room heater and MAIHS_HT_2==28 | MAIHS_SAP_2: 621, 623", DictType.MAIHS_SAP_2),
	MAIHS_SAP_2_T3("Define room heater and MAIHS_HT_2==33, 5, 15, 6, 9 | MAIHS_SAP_2: 631, 633", DictType.MAIHS_SAP_2),
	MAIHS_SAP_2_T4("Define room heater and MAIHS_HT_2==29 | MAIHS_SAP_2: 691, 694, 693", DictType.MAIHS_SAP_2),
	MAIHS_SAP_2_T5("Define room heater and MAIHS_HT_2==19 | MAIHS_SAP_2: 123",DictType.MAIHS_SAP_2),
	MAIHS_SAP_2_T6("Define room heater and MAIHS_HT_2== | MAIHS_SAP_2: ",DictType.MAIHS_SAP_2),
	MAIHS_SAP_2_T7("Define room heater and MAIHS_HT_2== | MAIHS_SAP_2: ",DictType.MAIHS_SAP_2),
	
	//Appliances and cooking
	AC_COOKER_TYPE_T1("AppCooking.cookerType.calCode",DictType.AC_COOKER_TYPE),
	
	AC_COOKING_FUEL_T1("AppCooking.cookingFuel.calCode==1,2",DictType.AC_COOKING_FUEL),
	
	AC_COOKING_FUEL_T2("AppCooking.cookingFuel.calCode==3",DictType.AC_COOKING_FUEL),
	
	//stET or offET
	Standard_domestic_tariff("BillData.BILL_ELECTRICITY_TARIFF.calCode==1",DictType.BILL_ELECTRICITY_TARIFF),
	Off_peak_tariff("BillData.BILL_ELECTRICITY_TARIFF.calCode==2",DictType.BILL_ELECTRICITY_TARIFF),
	ET24_hour_tariff("BillData.BILL_ELECTRICITY_TARIFF.calCode==3",DictType.BILL_ELECTRICITY_TARIFF),
	ET_offpeak_stand("",DictType.BILL_ELECTRICITY_TARIFF);
	
	private final String name;
	private final DictType dictType;

	DictCondType(String name, DictType dictType) {
		this.name = name;
		this.dictType = dictType;
	}

	public String getId() {
		return this.toString();
	}

	public String getName() {
		return this.name;
	}

	public DictType getDictType(){
		return this.dictType;
	}
	static public ArrayList<DictCondType> values(DictType dictType) {
		ArrayList<DictCondType> condTypeList = new ArrayList<DictCondType>();

		DictCondType[] condTypes = DictCondType.values();
		for (DictCondType condType : condTypes) {
			if (condType.dictType.equals(dictType)) {
				condTypeList.add(condType);
			}
		}

		return condTypeList;
	}
	
}
