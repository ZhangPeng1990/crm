package uk.co.quidos.gdsap.evaluation;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.CollectionUtils;

/**
 * Standard Option 对象封装
 * @author peng.shi
 */
public class StandardOption
{
	private String code;
	private String name;
	private String defaultValue;
	private Integer defaultValueCode;
	private String uValue;
	private Map<Integer, String> lis = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> descs = new LinkedHashMap<Integer, String>();
	
	public Map<Integer, String> getDescs()
	{
		return descs;
	}
	public void setDescs(Map<Integer, String> descs)
	{
		this.descs = descs;
	}
	public String getName()
	{
		return name;
	}
	public Map<Integer, String> getLis()
	{
		return lis;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setLis(Map<Integer, String> lis)
	{
		this.lis = lis;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public Integer getDefaultValueCode() {
		return defaultValueCode;
	}
	public void setDefaultValueCode(Integer defaultValueCode) {
		this.defaultValueCode = defaultValueCode;
	}
	public String getuValue() {
		return uValue;
	}
	public void setuValue(String uValue) {
		this.uValue = uValue;
	}
	/**
	 * 获取选项对应的值
	 * @param key
	 * @return
	 */
	public String getValue(Integer key)
	{
		if (!CollectionUtils.isEmpty(lis))
		{
			return lis.get(key);
		}
		return null;
	}
}
