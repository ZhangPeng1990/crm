/**
 * 
 */
package uk.co.quidos.gdsap.framework.common.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 */
public enum PreType implements BaseEnum<Integer>
{
	/**
	 * 系统模板
	 */
	SystemTemplate("System Template",0),
	/**
	 * 系统邮件模板
	 */
	EmailTemplate("Email Template",1),
	/**
	 * 系统配置元素
	 */
	SystemConfig("System configuration",2);

	private String desc;
	private Integer code;
	
	private PreType(String desc,Integer code)
	{
		this.desc = desc;
		this.code = code;
	}
	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum#getCode()
	 */
	@Override
	public Integer getCode()
	{
		return this.code;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum#getDesc()
	 */
	@Override
	public String getDesc()
	{
		return this.desc;
	}
	
	/**
	 * 排除PreType列表
	 * @param excludePreTypes
	 * @return
	 */
	public static List<PreType> getPreTypes(PreType[] excludePreTypes)
	{
		PreType[] all = PreType.values();
		if (excludePreTypes == null || excludePreTypes.length <=0)
		{
			return Arrays.asList(all);
		}
		
		List<PreType> pts = new ArrayList<PreType>();
		for (PreType preType : all)
		{
			for (PreType ept : excludePreTypes)
			{
				if (preType.getCode().equals(ept.getCode()))
				{
					pts.add(ept);
				}
			}
		}
		return pts;
	}
}
