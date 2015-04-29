/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 *
 */
public enum FuelScope implements BaseEnum<String>
{
	Community_heating("Community heating"),
	Electricity_tariff("Electricity tariff"),
	Mains_Gas("Mains Gas"),
	Other_Fuel("Other Fuel"),
	LPG_subject("LPG Subject");
	
	private String desc;
	
	private FuelScope(String desc)
	{
		this.desc = desc;
	}
	@Override
	public String getCode()
	{
		return this.toString();
	}

	@Override
	public String getDesc()
	{
		return this.desc;
	}

}
