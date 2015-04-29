package uk.co.quidos.gdsap.evaluation.calculate.input;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CalFuelBillData {

	private Collection<CalFuelPrice> communityFuelPriceList;
	private Collection<CalFuelPrice> mainGasFuelPriceList;
	private Collection<CalFuelPrice> electricityFuelPriceList;
	private Collection<CalFuelPrice> allFuelPriceList;
	
	private Map<String,CalFuelPrice> communityFuelPriceMap;
	private Map<String,CalFuelPrice> mainGasFuelPriceMap;
	private Map<String,CalFuelPrice> electricityFuelPriceMap;
	private Map<String,CalFuelPrice> allFuelPriceMap;
	
	
	public Collection<CalFuelPrice> getCommunityFuelPriceList() {
		return communityFuelPriceList;
	}
	public void setCommunityFuelPriceList(
			Collection<CalFuelPrice> communityFuelPriceList) {
		this.communityFuelPriceList = communityFuelPriceList;
		if(communityFuelPriceList != null && communityFuelPriceList.size() == 1)
		{
			Map<String, CalFuelPrice> communityFuelPriceMap = new HashMap<String, CalFuelPrice>();
			communityFuelPriceMap.put(CalFuelType.community_energy, communityFuelPriceList.iterator().next());
			this.setCommunityFuelPriceMap(communityFuelPriceMap);
		}
	}
	public Collection<CalFuelPrice> getMainGasFuelPriceList() {
		return mainGasFuelPriceList;
	}
	public void setMainGasFuelPriceList(
			Collection<CalFuelPrice> mainGasFuelPriceList) {
		this.mainGasFuelPriceList = mainGasFuelPriceList;
		if(mainGasFuelPriceList != null && mainGasFuelPriceList.size() == 1)
		{
			Map<String, CalFuelPrice> mainGasFuelPriceMap = new HashMap<String, CalFuelPrice>();
			mainGasFuelPriceMap.put(CalFuelType.mains_gas, mainGasFuelPriceList.iterator().next());
			this.setCommunityFuelPriceMap(mainGasFuelPriceMap);
		}
	}
	public Collection<CalFuelPrice> getElectricityFuelPriceList() {
		return electricityFuelPriceList;
	}
	public void setElectricityFuelPriceList(
			Collection<CalFuelPrice> electricityFuelPriceList) {
		this.electricityFuelPriceList = electricityFuelPriceList;
		if(electricityFuelPriceList != null && electricityFuelPriceList.size() == 1)
		{
			Map<String, CalFuelPrice> electricityFuelPriceMap = new HashMap<String, CalFuelPrice>();
			electricityFuelPriceMap.put(CalFuelType.mains_gas, electricityFuelPriceList.iterator().next());
			this.setCommunityFuelPriceMap(electricityFuelPriceMap);
		}
	}
	public Collection<CalFuelPrice> getAllFuelPriceList() {
		return allFuelPriceList;
	}
	public void setAllFuelPriceList(Collection<CalFuelPrice> allFuelPriceList) {
		this.allFuelPriceList = allFuelPriceList;
		if(allFuelPriceList != null && allFuelPriceList.size() > 0)
		{
			Map<String, CalFuelPrice> allFuelPriceMapMap = new HashMap<String, CalFuelPrice>();
			for(CalFuelPrice cfp : allFuelPriceList)
			{
				if(include(cfp.getCalFuelType().getName(), CalFuelType.allFuelType))
				{
					allFuelPriceMapMap.put(cfp.getCalFuelType().getName(), cfp);
				}
			}
			this.setAllFuelPriceMap(allFuelPriceMapMap);
		}
	}
	public Map<String, CalFuelPrice> getCommunityFuelPriceMap() {
		return communityFuelPriceMap;
	}
	public void setCommunityFuelPriceMap(
			Map<String, CalFuelPrice> communityFuelPriceMap) {
		this.communityFuelPriceMap = communityFuelPriceMap;
	}
	public Map<String, CalFuelPrice> getMainGasFuelPriceMap() {
		return mainGasFuelPriceMap;
	}
	public void setMainGasFuelPriceMap(Map<String, CalFuelPrice> mainGasFuelPriceMap) {
		this.mainGasFuelPriceMap = mainGasFuelPriceMap;
	}
	public Map<String, CalFuelPrice> getElectricityFuelPriceMap() {
		return electricityFuelPriceMap;
	}
	public void setElectricityFuelPriceMap(
			Map<String, CalFuelPrice> electricityFuelPriceMap) {
		this.electricityFuelPriceMap = electricityFuelPriceMap;
	}
	public Map<String, CalFuelPrice> getAllFuelPriceMap() {
		return allFuelPriceMap;
	}
	public void setAllFuelPriceMap(Map<String, CalFuelPrice> allFuelPriceMap) {
		this.allFuelPriceMap = allFuelPriceMap;
	}
	
	private boolean include(String val, String ...vals)
	{
		boolean include = false;
		for(String s : vals)
		{
			if(s.equalsIgnoreCase(val))
			{
				include = true;
				return include;
			}
		}
		return include;
	}
}
