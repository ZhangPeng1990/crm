package uk.co.quidos.gdsap.evaluation.calculate.input;

import com.google.gson.Gson;


public class CalFuelType {
	
	 public static  final String community_energy_CHP = "Community energy CHP";
     public static  final String community_energy = "Community energy";
     public static  final String electricity = "Electricity";
     public static  final String _24Hour_electricity = "Electricity 24 Hour";
     public static  final String electricity_high_rate = "Electricity high-rate";
     public static  final String electricity_low_rate = "Electricity low-rate";
     public static  final String mains_gas = "Mains gas";
     public static  final String other_fuel_1 = "Other fuel 1";
     public static  final String other_fuel_2 = "Other fuel 2";
     public static  final String other_fuel_3 = "Other fuel 3";
     public static  final String other_fuel_4 = "Other fuel 4";
     public static  final String other_fuel_5 = "Other fuel 5";

     public static final String[] allFuelType = {community_energy_CHP,community_energy,electricity,_24Hour_electricity,electricity_high_rate,
    	 electricity_low_rate,mains_gas,other_fuel_1,other_fuel_2,other_fuel_3,other_fuel_4,other_fuel_5};
    private Integer EnumType;
 	private String name;
 	private String unit;
 	private Integer billType;
 	
 	
 	public static void main(String[] args) {
 		CalFuelType cf = new CalFuelType();
 		cf.setBillType(2);
 		cf.setEnumType(6);
 		cf.setName("Mainsgas");
 		cf.setUnit("£/year");
 		
 		Gson gson = new Gson();
 		String json = gson.toJson(cf);
 		System.out.println(json);
 		
 		CalFuelType cft = gson.fromJson(json, CalFuelType.class);
 		System.out.println(cft);
	}
 	public String getName() {
 		return name;
 	}
 	public void setName(String name) {
 		this.name = name;
 	}
 	public String getUnit() {
 		return unit;
 	}
 	public void setUnit(String unit) {
 		this.unit = unit;
 	}
	public Integer getEnumType() {
		return EnumType;
	}
	public void setEnumType(Integer enumType) {
		EnumType = enumType;
	}
	public Integer getBillType() {
		return billType;
	}
	public void setBillType(Integer billType) {
		this.billType = billType;
	}
}
