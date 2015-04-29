package uk.co.quidos.gdsap.evaluation.persistence.object;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import uk.co.quidos.dal.object.AbstractDO;

public class GdsapDictCond extends AbstractDO{

	private static final long serialVersionUID = 5129504946006028072L;
	private Integer id;
	private Integer dictItemId;
	private String condType;

	public GdsapDictCond(){
	}

	public GdsapDictCond(Integer id){
		this.id = id;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setDictItemId(Integer value) {
		this.dictItemId = value;
	}
	
	public Integer getDictItemId() {
		return this.dictItemId;
	}
	public void setCondType(String value) {
		this.condType = value;
	}
	
	public String getCondType() {
		return this.condType;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("DictItemId",getDictItemId())
			.append("CondType",getCondType())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof GdsapDictCond == false) return false;
		if(this == obj) return true;
		GdsapDictCond other = (GdsapDictCond)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

