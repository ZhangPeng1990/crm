package uk.co.quidos.gdsap.evaluation.calculate.input;

public class GdMeasureCode{ 
	
	private String EnumType;
	private String Code;
	private int Sequence;
	private String Name;
	private int Category;
	private int Lifespan;
	private Boolean Alternative;
	private int[] ImpNumbers;
	
	public String getEnumType() {
		return EnumType;
	}
	public void setEnumType(String enumType) {
		EnumType = enumType;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public int getSequence() {
		return Sequence;
	}
	public void setSequence(int sequence) {
		Sequence = sequence;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getCategory() {
		return Category;
	}
	public void setCategory(int category) {
		Category = category;
	}
	public int getLifespan() {
		return Lifespan;
	}
	public void setLifespan(int lifespan) {
		Lifespan = lifespan;
	}
	public Boolean getAlternative() {
		return Alternative;
	}
	public void setAlternative(Boolean alternative) {
		Alternative = alternative;
	}
	public int[] getImpNumbers() {
		return ImpNumbers;
	}
	public void setImpNumbers(int[] impNumbers) {
		ImpNumbers = impNumbers;
	}
}