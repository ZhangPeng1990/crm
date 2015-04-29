/*
 * Powered By [dwz4j-framework]
 * Web Site: http://j-ui.com
 * Google Code: http://code.google.com/p/dwz4j/
 * Generated 2012-07-05 14:45:19 by code generator
 */
package uk.co.quidos.gdsap.evaluation;

import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapDictItem;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

public class DictItem extends AbsBusinessObject{
	private static final long serialVersionUID = 1L;
	private GdsapDictItem gdsapDictItem;
	
	/* generateConstructor */
	public DictItem(){
		this.gdsapDictItem = new GdsapDictItem();
	}
	public DictItem(GdsapDictItem gdsapDictItem){
		this.gdsapDictItem = gdsapDictItem;
	}

	
	public Integer getId() {
		return this.gdsapDictItem.getId();
	}
	
	public void setId(Integer id)
	{
		this.gdsapDictItem.setId(id);
	}
	
	public String getDictType() {
		return this.gdsapDictItem.getDictType();
	}
	public void setDictType(String dictType) {
		this.gdsapDictItem.setDictType(dictType);
	}
	
	
	public String getName() {
		return this.gdsapDictItem.getName();
	}
	public void setName(String name) {
		this.gdsapDictItem.setName(name);
	}
	
	
	public double getCalCode() {
		Double value = this.gdsapDictItem.getCalCode();
		return value != null ? value : 0;
	}
	public void setCalCode(double calCode) {
		this.gdsapDictItem.setCalCode(calCode);
	}
	
	
	public String getLodgeCode() {
		return this.gdsapDictItem.getLodgeCode();
	}
	public void setLodgeCode(String lodgeCode) {
		this.gdsapDictItem.setLodgeCode(lodgeCode);
	}
	
	
	public int getSequence() {
		Integer value = this.gdsapDictItem.getSequence();
		return value != null ? value : 0;
	}
	public void setSequence(int sequence) {
		this.gdsapDictItem.setSequence(sequence);
	}
	

}

