package uk.co.quidos.gdsap.evaluation.persistence.object;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapDictItem extends AbstractDO{

	private static final long serialVersionUID = 7482497003102508548L;
	
	private Integer id;
	private String dictType;
	private String name;
	private Double calCode;
	private String lodgeCode;
	private Integer sequence;

	public GdsapDictItem(){
	}

	public GdsapDictItem(Integer id){
		this.id = id;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setDictType(String value) {
		this.dictType = value;
	}
	
	public String getDictType() {
		return this.dictType;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setCalCode(Double value) {
		this.calCode = value;
	}
	
	public Double getCalCode() {
		return this.calCode;
	}
	public void setLodgeCode(String value) {
		this.lodgeCode = value;
	}
	
	public String getLodgeCode() {
		return this.lodgeCode;
	}
	public void setSequence(Integer value) {
		this.sequence = value;
	}
	
	public Integer getSequence() {
		return this.sequence;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("DictType",getDictType())
			.append("Name",getName())
			.append("CalCode",getCalCode())
			.append("LodgeCode",getLodgeCode())
			.append("Sequence",getSequence())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof GdsapDictItem == false) return false;
		if(this == obj) return true;
		GdsapDictItem other = (GdsapDictItem)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

