package uk.co.quidos.gdsap.evaluation;

public class ValueAttributes {

	private boolean readonly = false;
	
	private InputType inputType;
	private boolean required = false;
	private boolean haveDefaultValue = false;
	private ValueType valueType;
	private boolean needConfirm = false;
	
	public String getCssCalss() {
		String cssClass = "class=replace";
		if(this.required)
		{
			cssClass = "required" + " ";
		}
		
		if(this.valueType != null)
		{
			switch (this.valueType) {
			case DOUBLE:
				cssClass = cssClass.replaceAll("replace", "'number '");
				break;
			case INTEGER:
				cssClass = cssClass.replaceAll("replace", "'number '");
				break;
			case TIME:
				cssClass = cssClass.replaceAll("replace", "'date'") + " dateFmt='HH:mm' mmStep='15'" + " readonly='readonly'";
			default:
				break;
			}
		}
		if(this.needConfirm)
		{
			cssClass = cssClass + " needConfirm='true'";
			
			String jsFuction = " onchange='showMeasureDeclaration(this, false, false)'";
			cssClass = cssClass + jsFuction;
		}
		
		return cssClass;
	}
	
	public enum InputType
	{
		INPUT,
		SELECT;
	}
	
	public enum ValueType
	{
		INTEGER,DOUBLE,TEXT,TIME;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public InputType getInputType() {
		return inputType;
	}

	public void setInputType(InputType inputType) {
		this.inputType = inputType;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isHaveDefaultValue() {
		return haveDefaultValue;
	}

	public void setHaveDefaultValue(boolean haveDefaultValue) {
		this.haveDefaultValue = haveDefaultValue;
	}

	public ValueType getValueType() {
		return valueType;
	}

	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
	}

	public boolean isNeedConfirm() {
		return needConfirm;
	}

	public void setNeedConfirm(boolean needConfirm) {
		this.needConfirm = needConfirm;
	}
}
