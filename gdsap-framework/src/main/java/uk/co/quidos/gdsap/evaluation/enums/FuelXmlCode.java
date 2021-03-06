/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.enums;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import uk.co.quidos.gdsap.evaluation.HeatingSystem;
import uk.co.quidos.gdsap.evaluation.OtherFuel;
import uk.co.quidos.gdsap.evaluation.Report;
import uk.co.quidos.gdsap.framework.enums.YesNo;
import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 *
 */
public enum FuelXmlCode implements BaseEnum<Integer>
{
	/*
	 * *************************************************
	 * GAS
	 * *************************************************
	 */
	main_gas("mains gas",FuelScope.Mains_Gas,26),
	bulk_LPG("bulk LPG",FuelScope.Other_Fuel,27),
	bottled_LPG("bottled LPG",FuelScope.Other_Fuel,3),
	
	/*
	 * *************************************************
	 * LPG_subject
	 * *************************************************
	 */
	LPG_subject_to_Special_Condition_18("LPG subject to Special Condition 18",FuelScope.LPG_subject,17),
	
	/*
	 * *************************************************
	 * Oil
	 * *************************************************
	 */
	heating_oil("heating oil",FuelScope.Other_Fuel,28),
	biodiesel_from_any_biomass_source("biodiesel from any biomass source",FuelScope.Other_Fuel,34),
	biodiesel_from_used_cooking_oil_only("biodiesel from used cooking oil only",FuelScope.Other_Fuel,35),
	rapeseed_oil("biodiesel from vegetable oil only",FuelScope.Other_Fuel,36),//992 updated to: biodiesel from vegetable oil only
	appliances_able_to_use_mineral_oil_or_liquid_biofuel("appliances able to use mineral oil or liquid biofuel",FuelScope.Other_Fuel,37),
	B30K("B30K",FuelScope.Other_Fuel,18),
	bioethanol_from_any_biomass_source("bioethanol from any biomass source",FuelScope.Other_Fuel,19),
	house_coal("house coal",FuelScope.Other_Fuel,33),
	anthracite("anthracite",FuelScope.Other_Fuel,5),
	manufactured_smokeless_fuel("manufactured smokeless fuel",FuelScope.Other_Fuel,15),
	wood_logs("wood logs",FuelScope.Other_Fuel,6),
	wood_pellts_in_bags("wood pellts （in bags for secondary heating）",FuelScope.Other_Fuel,16),
	wood_pellts_ulk_supply("wood pellts （bulk supply for main heating）",FuelScope.Other_Fuel,7),
	wood_chips("wood chips",FuelScope.Other_Fuel,8),
//	dual_fuel_appliance("dual fuel appliance (mineral and wood)",FuelScope.Other_Fuel,9),
	dual_fuel_appliance("solid mineral",FuelScope.Other_Fuel,9),
	
	/*
	 * *************************************************
	 * Electricity
	 * *************************************************
	 */
	standard_tariff("standard tariff",FuelScope.Electricity_tariff,29),
	tariff_high_rate_7_hour("7-hour tariff(high rate)",FuelScope.Electricity_tariff,29),
	tariff_low_rate_7_hour("7-hour tariff(low rate)",FuelScope.Electricity_tariff,29),
	tariff_high_rate_10_hour("10-hour tariff(high rate)",FuelScope.Electricity_tariff,29),
	tariff_low_rate_10_hour("10-hour tariff(low rate)",FuelScope.Electricity_tariff,29),
	tariff_high_rate_18_hour("18-hour tariff(high rate)",FuelScope.Electricity_tariff,29),
	tariff_low_rate_18_hour("18-hour tariff(low rate)",FuelScope.Electricity_tariff,29),
	heating_tariff_24_hour("24-hour heating tariff",FuelScope.Electricity_tariff,29),
	electricity_sold_to_grid("electricity sold to grid",FuelScope.Electricity_tariff,29),
	electricity_displaced_from_grid("electricity displaced from grid",FuelScope.Electricity_tariff,29),
	electricity_unspecified_tariff("electricity, unspecified tariff",FuelScope.Electricity_tariff,29),
	
	/*
	 * *************************************************
	 * Community heating schemes
	 * *************************************************
	 */
	heat_from_boiler_main_gas("heat from boiler - main gas",FuelScope.Community_heating,20),
	heat_from_boiler_LPG("heat from boiler - LPG",FuelScope.Community_heating,21),
	heat_from_boiler_oil("heat from boiler - oil",FuelScope.Community_heating,22),
	heat_from_boiler_B30D("heat from boiler - B30D",FuelScope.Community_heating,23),
	heat_from_boiler_coal("heat from boiler - coal",FuelScope.Community_heating,24),
	heat_from_electric_heat_pump("heat from electric heat pump",FuelScope.Community_heating,25),
	heat_from_boiler_waste_combustion("heat from boiler - waste combustion",FuelScope.Community_heating,30),
	heat_from_boiler_biomass("heat from boiler - biomass",FuelScope.Community_heating,31),
	heat_from_boiler_biogas_landfill_or_sewage_gas("heat from boiler - biogas(landfill or sewage gas)",FuelScope.Community_heating,32),
	electricity_generated_by_CHP("electricity generated by CHP",FuelScope.Community_heating,25),
	electricity_for_pumping_in_distribution_network("electricity for pumping in distribution network",FuelScope.Community_heating,25);
	
	public static final int[] SOLIDS_CODES = new int[]{33, 5, 15, 6, 16, 7, 8, 9};
	
	public static final int[] OIL_CODES = new int[]{28, 34, 35, 36, 37, 18, 19};
	
	private String desc;
	private FuelScope fuelScope;
	private int code;
	private OtherFuel otherFuel;
	
	private FuelXmlCode(String desc,FuelScope fuelScope,int code)
	{
		this.desc = desc;
		this.fuelScope = fuelScope;
		this.code = code;
	}

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
	
	public OtherFuel getOtherFuel() {
		return otherFuel;
	}

	public void setOtherFuel(OtherFuel otherFuel) {
		this.otherFuel = otherFuel;
	}

	/**
	 * 获取FuelScope
	 * @return
	 */
	public FuelScope getFuelScope()
	{
		return this.fuelScope;
	}
	
	/**
	 * 判断是否为OtherFuel
	 * @param code
	 * @return
	 */
	public static boolean isOtherFuel(int code)
	{
		FuelXmlCode[] fuelXmlCodes = FuelXmlCode.values();
		for (FuelXmlCode fuelXmlCode : fuelXmlCodes)
		{
			if (fuelXmlCode.getCode() == code && fuelXmlCode.getFuelScope().equals(FuelScope.Other_Fuel))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否为LPG_subject
	 * @param code
	 * @return
	 */
	public static boolean isLPGsubject(int code)
	{
		FuelXmlCode[] fuelXmlCodes = FuelXmlCode.values();
		for (FuelXmlCode fuelXmlCode : fuelXmlCodes)
		{
			if (fuelXmlCode.getCode() == code && fuelXmlCode.getFuelScope().equals(FuelScope.LPG_subject))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否为Mains_Gas
	 * @param code
	 * @return
	 */
	public static boolean isMains_Gas(int code){
		FuelXmlCode[] fuelXmlCodes = FuelXmlCode.values();
		for(FuelXmlCode fuelXmlCode : fuelXmlCodes){
			if(fuelXmlCode.getCode() == code && fuelXmlCode.getFuelScope().equals(FuelScope.Mains_Gas)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否为Community_heating
	 * @param code
	 * @return
	 */
	public static boolean isCommunity_heating(int code){
		FuelXmlCode[] fuelXmlCodes = FuelXmlCode.values();
		for(FuelXmlCode fuelXmlCode : fuelXmlCodes){
			if(fuelXmlCode.getCode() == code && fuelXmlCode.getFuelScope().equals(FuelScope.Community_heating)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否为Electricity_tariff
	 * @param code
	 * @return
	 */
	public static boolean isElectricity_tariff(int code){
		FuelXmlCode[] fuelXmlCodes = FuelXmlCode.values();
		for(FuelXmlCode fuelXmlCode : fuelXmlCodes){
			if(fuelXmlCode.getCode() == code && fuelXmlCode.getFuelScope().equals(FuelScope.Electricity_tariff)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 转化方法，把Integer类型的数组转化成List<Integer>
	 */
	public static List<Integer> changeInteger(Integer[] integerArray){
		if(integerArray != null && integerArray.length > 0){
			List<Integer> temp = new  ArrayList<Integer>();
			for(Integer i : integerArray){
				temp.add(i);
			}
			return temp;
		}
		return null;
	}
	
	/**
	 * 转化方法，把List<Integer>转化成Integer类型的数组
	 */
	public static Integer[] changeList(List<Integer> listInteger){
		if (!CollectionUtils.isEmpty(listInteger))
		{
			Integer[] array = listInteger.toArray(new Integer[listInteger.size()]);
			return array;
		}
		return null;
	}
	
	/**
	 * 获取isMains_Gas
	 * @param fuelCodes
	 * @return
	 */
	public static Integer[] getMains_Gas(int[] fuelCodes){
		Assert.notNull(fuelCodes);
		if (fuelCodes.length <=0)
		{
			throw new IllegalArgumentException();
		}
		List<Integer> temp = new ArrayList<Integer>();
		for (int i=0;i<fuelCodes.length;i++)
		{
			int code = fuelCodes[i];
			if (isMains_Gas(code))
			{
				temp.add(code);
			}
		}
		if (!CollectionUtils.isEmpty(temp))
		{
			Integer[] array = temp.toArray(new Integer[temp.size()]);
			return array;
		}
		return null;
	}
	
	/**
	 * 获取Community_heating
	 * @param fuelCodes
	 * @return
	 */
	public static Integer[] getCommunity_heating(int[] fuelCodes){
		Assert.notNull(fuelCodes);
		if (fuelCodes.length <=0)
		{
			throw new IllegalArgumentException();
		}
		List<Integer> temp = new ArrayList<Integer>();
		for (int i=0;i<fuelCodes.length;i++)
		{
			int code = fuelCodes[i];
			if (isCommunity_heating(code))
			{
				temp.add(code);
			}
		}
		if (!CollectionUtils.isEmpty(temp))
		{
			Integer[] array = temp.toArray(new Integer[temp.size()]);
			return array;
		}
		return null;
	}
	
	/**
	 * 获取Electricity_tariff
	 * @param fuelCodes
	 * @return
	 */
	public static Integer[] getElectricity_tariff(int[] fuelCodes){
		Assert.notNull(fuelCodes);
		if (fuelCodes.length <=0)
		{
			throw new IllegalArgumentException();
		}
		List<Integer> temp = new ArrayList<Integer>();
		for (int i=0;i<fuelCodes.length;i++)
		{
			int code = fuelCodes[i];
			if (isElectricity_tariff(code))
			{
				temp.add(code);
			}
		}
		if (!CollectionUtils.isEmpty(temp))
		{
			Integer[] array = temp.toArray(new Integer[temp.size()]);
			return array;
		}
		return null;
	}
	
	/**
	 * 获取OtherFuels
	 * @param fuelCodes
	 * @return
	 */
	public static Integer[] getOtherFuels(int[] fuelCodes)
	{
		Assert.notNull(fuelCodes);
		if (fuelCodes.length <=0)
		{
			throw new IllegalArgumentException();
		}
		List<Integer> temp = new ArrayList<Integer>();
		for (int i=0;i<fuelCodes.length;i++)
		{
			int code = fuelCodes[i];
			if (isOtherFuel(code))
			{
				temp.add(code);
			}
		}
		if (!CollectionUtils.isEmpty(temp))
		{
			Integer[] array = temp.toArray(new Integer[temp.size()]);
			return array;
		}
		return null;
	}
	
	/**
	 * 获取LPG_subject
	 * @param fuelCodes
	 * @return
	 */
	public static Integer[] getLPGsubject(int[] fuelCodes)
	{
		Assert.notNull(fuelCodes);
		if (fuelCodes.length <=0)
		{
			throw new IllegalArgumentException();
		}
		List<Integer> temp = new ArrayList<Integer>();
		for (int i=0;i<fuelCodes.length;i++)
		{
			int code = fuelCodes[i];
			if (isLPGsubject(code))
			{
				temp.add(code);
			}
		}
		if (!CollectionUtils.isEmpty(temp))
		{
			Integer[] array = temp.toArray(new Integer[temp.size()]);
			return array;
		}
		return null;
	}
	
	/**
	 * 返回AppCookFuelType的Code
	 * @param report
	 * @param heatingSystem
	 * @param cookingFuel
	 * @return
	 */
	public static int getAppCookFuelType(Report report,HeatingSystem heatingSystem,int cookingFuel)
	{
		Assert.notNull(report);
		Assert.notNull(heatingSystem);
		
		if (cookingFuel != 1 && cookingFuel !=2 && cookingFuel != 3 && cookingFuel !=4 && cookingFuel != 5 )
		{
			throw new IllegalArgumentException();
		}
		
		//if electric 3 
		if (cookingFuel == 3)
		{
			return 29;
		}
		//If Cooking Fuel = gas or gas/electric, (1,2)
		//If mains gas is available, 
		//gas = mains gas (26)
		//Else if main heating 或 water heating的燃料是bulk LPG(27)，
		//gas = bulk LPG (27)
		//Else, 
		//gas = bottled LPG (3)
		if (cookingFuel == 1 || cookingFuel ==2)
		{
			YesNo avaiable = report.getRdsapMainGasAvailable();
			if (avaiable == null)
			{
				throw new IllegalArgumentException();
			}
			if (avaiable.equals(YesNo.Yes))
			{
				return 26;
			}
			else
			{
				int mainHeatingCode = 0;
				//As RdSAP assessment
				if (heatingSystem.getmHs().getCalCode() == 1)
				{
					mainHeatingCode = report.getRdsapMhsFuel();
				}
				//Define room heater
				else
				{
					mainHeatingCode = (int)heatingSystem.getmHf().getCalCode();
				}
				int waterHeatingCode = report.getRdsapWhsFuel();
				if (mainHeatingCode == 27 || waterHeatingCode == 27)
				{
					return 27;
				}
				else
				{
					return 3;
				}
			}
		}
		//solid
		//If Cooking Fuel =solid，
		//If main heating system的燃料属于solid (33, 5, 15, 6, 16, 7, 8, 9)，
		// cooking fuel的燃料=main heating system的燃料
		//Else, 
		//cooking fuel的燃料= house coal (33)
		if (cookingFuel == 5) 
		{
			int mainHeatingCode = 0;
			//As RdSAP assessment
			if (heatingSystem.getmHs().getCalCode() == 1)
			{
				mainHeatingCode = report.getRdsapMhsFuel();
			}
			//Define room heater
			else
			{
				mainHeatingCode = (int)heatingSystem.getmHf().getCalCode();
			}
			if (_inIntArray(mainHeatingCode, SOLIDS_CODES))
			{
				return mainHeatingCode;
			}
			else
			{
				return 33;
			}
		}
		//If Cooking Fuel = oil (28, 34, 35, 36, 37, 18, 19)，
		//If main heating system的燃料属于oil，
		//cooking fuel的燃料=main heating system的燃料
		//Else, 
		//cooking fuel的燃料= heating oil (28)
		if (cookingFuel == 4)
		{
			int mainHeatingCode = 0;
			//As RdSAP assessment
			if (heatingSystem.getmHs().getCalCode() == 1)
			{
				mainHeatingCode = report.getRdsapMhsFuel();
			}
			//Define room heater
			else
			{
				mainHeatingCode = (int)heatingSystem.getmHf().getCalCode();
			}
			if (_inIntArray(mainHeatingCode, OIL_CODES))
			{
				return mainHeatingCode;
			}
			else
			{
				return 28;
			}
		}
		throw new IllegalArgumentException();
	}
	
	private static boolean _inIntArray(int value,int[] array)
	{
		if (array == null || array.length <=0)
		{
			return false;
		}
		for (int i=0;i<array.length;i++)
		{
			if (value == array[i])
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断数组中的重复数据,如果有则去除
	 */
	public static int[] removeRepeat(int[] fuelCodes){
		List<Integer> temp = new ArrayList<Integer>();
		if(fuelCodes.length > 0){
			for(int i = 0 ; i < fuelCodes.length ; i++){
				temp.add(fuelCodes[i]);
			}
			HashSet<Integer> h = new HashSet<Integer>();
			h.addAll(temp);
			temp.clear();
			temp.addAll(h);
		}
		if (!CollectionUtils.isEmpty(temp))
		{
			Integer[] Iarray = temp.toArray(new Integer[temp.size()]);
			int[] iarray = new int[Iarray.length];
			for(int i = 0 ; i < Iarray.length ; i++){
				iarray[i] = Iarray[i];
			}
			return iarray;
		}
		return null;
	}
}
