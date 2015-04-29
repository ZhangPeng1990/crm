/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 */
public enum RecommendationCategoryCode implements BaseEnum<Integer>
{
	RecommendationCategoryCode1("Lower cost - this is for backwards compatibility only and should not be used",1),
	RecommendationCategoryCode2("Higher cost - this is for backwards compatibility only and should not be used",2),
	RecommendationCategoryCode3("Further measure - this is for backwards compatibility only and should not be used",3),
	RecommendationCategoryCode4("Deselected.  This is for backwards compatibility only and should not be used.",4),
	RecommendationCategoryCode5("Normal measure",5),
	RecommendationCategoryCode6("Alternative measure",6);
	
	private String desc;
	private Integer code;
	
	private RecommendationCategoryCode(String desc,Integer code)
	{
		this.desc = desc;
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
	
}
