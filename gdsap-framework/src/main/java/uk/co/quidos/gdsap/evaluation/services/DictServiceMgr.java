package uk.co.quidos.gdsap.evaluation.services;

import java.util.List;

import uk.co.quidos.gdsap.evaluation.DictItem;
import uk.co.quidos.gdsap.evaluation.enums.DictCondType;
import uk.co.quidos.gdsap.evaluation.enums.DictType;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

public interface DictServiceMgr extends BusinessObjectServiceMgr {
	
	/**
	 * 
	 */
	public static final String SERVICE_NAME=  "dictServiceMgr";
	
	DictItem getDictItem(Integer id);

	DictItem getDictItem(DictType dictType, Double calCode);
	
	DictItem getDictItem(DictType dictType, String lodgeCode);
	
	int getDictItemId(DictType dictType, double calCode);

	List<DictItem> getDictItems(DictType dictType);

	List<DictItem> getDictItems(DictCondType condType);

	void saveDictCond(DictCondType confType, Integer[] dictItemIds);
	
	DictItem updateDictItem(DictItem dictItem);
	
}
