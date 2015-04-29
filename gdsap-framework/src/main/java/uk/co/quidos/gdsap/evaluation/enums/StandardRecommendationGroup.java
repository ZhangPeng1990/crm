/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import uk.co.quidos.gdsap.evaluation.StandardRecommendationWrap;
import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 */
public enum StandardRecommendationGroup implements BaseEnum<String>
{
	Insulation_Measures("Insulation Measures"),Lighting("Lighting"),
	Heating_Hot_Water("Heating and Hot Water"),
	Windows_Doors("Windows and doors"),Electricity_Generation("Electricity Generation");
	
	private String desc;
	private StandardRecommendationGroup(String desc)
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
	
	/**
	 * 获取
	 * @param wraps
	 * @return
	 */
	public static Map<StandardRecommendationGroup, List<StandardRecommendationWrap>> filterStandardRecommendationWraps(List<StandardRecommendationWrap> wraps)
	{
		Map<StandardRecommendationGroup, List<StandardRecommendationWrap>> maps = new HashMap<StandardRecommendationGroup, List<StandardRecommendationWrap>>();
		if (CollectionUtils.isEmpty(wraps))
		{
			StandardRecommendationGroup[] groups = StandardRecommendationGroup.values();
			for (int i=0;i<groups.length;i++)
			{
				maps.put(groups[i], null);
			}
		}
		else
		{
			StandardRecommendationGroup[] groups = StandardRecommendationGroup.values();
			for (int i=0;i<groups.length;i++)
			{
				List<StandardRecommendationWrap> wrapsGroup = new ArrayList<StandardRecommendationWrap>();
				for (StandardRecommendationWrap wrap : wraps)
				{
					if (wrap.getStandardRecommendation().getStandardRecommendationGroup().getCode().equals(groups[i].getCode()))
					{
						wrapsGroup.add(wrap);
					}
				}
				if (!CollectionUtils.isEmpty(wrapsGroup))
				{
					maps.put(groups[i], wrapsGroup);
				}
				else
				{
					maps.put(groups[i], null);
				}
			}
		}
		
		return maps;
	}
}
