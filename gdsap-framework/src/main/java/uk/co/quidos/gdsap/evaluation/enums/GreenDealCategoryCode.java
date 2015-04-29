/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 *
 */
public enum GreenDealCategoryCode implements BaseEnum<String>
{
	GreenDealCategoryCode1("1. Not eligible for Green Deal","1",1),
	GreenDealCategoryCode2("2. Eligible with additional finance","2",2),
	GreenDealCategoryCode3("3. Eligible without additional finance","3",3),
	GreenDealCategoryCode4("Not assessed.  Use for alternative measures","NI",4);
	
	private String desc;
	private String code;
	private Integer saveToDBcode;//保存到数据库的值
	
	private GreenDealCategoryCode(String desc,String code,Integer saveToDBcode)
	{
		this.desc = desc;
		this.code = code;
		this.saveToDBcode = saveToDBcode;
	}
	
	@Override
	public String getCode()
	{
		return this.code;
	}
	
	public Integer getSaveToDBcode() {
		return this.saveToDBcode;
	}

	@Override
	public String getDesc()
	{
		return this.desc;
	}

}
