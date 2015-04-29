/**
 * 
 */
package uk.co.quidos.gdsap.framework.user.enums;

import uk.co.quidos.gdsap.framework.sys.lang.enums.BaseEnum;

/**
 * @author peng.shi
 */
public enum AccessLevel implements BaseEnum<Integer>
{
	U00("U0",0),U01("01",1),U02("02",2),U03("03",3),U04("04",4),
	U05("05",5),U06("06",6),U07("07",7),U08("08",8),U09("09",9);
	
	private String desc;
	private Integer code;
	
	private AccessLevel(String desc,Integer code)
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

}
