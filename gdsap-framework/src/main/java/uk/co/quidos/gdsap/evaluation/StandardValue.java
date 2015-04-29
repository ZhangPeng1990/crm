package uk.co.quidos.gdsap.evaluation;

import uk.co.quidos.gdsap.evaluation.enums.ReportLocation;

/**
 * Improvement for OA Assessment (GDAR),
 * 用于封装默认的U-value
 * @author ZP
 * 
 */
public class StandardValue 
{
	private String desc;
	private String name;
	private Integer code;
	private String eawConfirmValue;
	private String eawValue;
	private String eawValueDesc;
	private String sctConfirmValue;
	private String sctValue;
	private String sctValueDesc;
	private String showValue; // 用于显示的U value, 需要根据国家来判断取u_value_EAW或u_value_SCT
	private ValueAttributes attribute;
	
	private String inputValue;//用于接收页面输入值
	private boolean u_valuesDeclarationSelected;//GDIP页面每个改进的每个U值输入框输入的U值大于0.18时弹出的声明勾选框
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getEawValue() {
		return eawValue;
	}
	public void setEawValue(String eawValue) {
		this.eawValue = eawValue;
	}
	public String getSctValue() {
		return sctValue;
	}
	public void setSctValue(String sctValue) {
		this.sctValue = sctValue;
	}
	public String getShowValue() {
		return showValue;
	}
	public void setShowValue(String showValue) {
		this.showValue = showValue;
	}
	public String getShowValue(ReportLocation local) {
		if(ReportLocation.EAW.equals(local) || ReportLocation.NIR.equals(local))
		{
			return this.eawValue;
		}
		else if(ReportLocation.SCT.equals(local))
		{
			return this.sctValue;
		}
		return showValue;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getInputValue() {
		return inputValue;
	}
	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}
	public boolean isU_valuesDeclarationSelected() {
		return u_valuesDeclarationSelected;
	}
	public void setU_valuesDeclarationSelected(boolean u_valuesDeclarationSelected) {
		this.u_valuesDeclarationSelected = u_valuesDeclarationSelected;
	}
	public ValueAttributes getAttribute() {
		return attribute;
	}
	public void setAttribute(ValueAttributes attribute) {
		this.attribute = attribute;
	}
	public String getEawValueDesc() {
		return eawValueDesc;
	}
	public void setEawValueDesc(String eawValueDesc) {
		this.eawValueDesc = eawValueDesc;
	}
	public String getSctValueDesc() {
		return sctValueDesc;
	}
	public void setSctValueDesc(String sctValueDesc) {
		this.sctValueDesc = sctValueDesc;
	}
	public String getEawConfirmValue() {
		return eawConfirmValue;
	}
	public void setEawConfirmValue(String eawConfirmValue) {
		this.eawConfirmValue = eawConfirmValue;
	}
	public String getSctConfirmValue() {
		return sctConfirmValue;
	}
	public void setSctConfirmValue(String sctConfirmValue) {
		this.sctConfirmValue = sctConfirmValue;
	}
}
