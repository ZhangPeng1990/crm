package uk.co.quidos.gdsap.evaluation.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import uk.co.quidos.gdsap.evaluation.DictItem;
import uk.co.quidos.gdsap.evaluation.enums.DictCondType;
import uk.co.quidos.gdsap.evaluation.enums.DictType;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapDictCondMapper;
import uk.co.quidos.gdsap.evaluation.persistence.GdsapDictItemMapper;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapDictCond;
import uk.co.quidos.gdsap.evaluation.persistence.object.GdsapDictItem;
import uk.co.quidos.gdsap.evaluation.services.DictServiceMgr;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;

@Service("dictServiceMgr")
@Transactional
public class DictServiceMgrImpl extends AbsBusinessObjectServiceMgr implements DictServiceMgr {

	@Autowired
	private GdsapDictItemMapper dictItemMapper;
	
	@Autowired
	private GdsapDictCondMapper dictCondMapper;
	
	
	public DictItem getDictItem(Integer id) {
		if (id == null) return null;
		
		GdsapDictItem gdsapDictItem = dictItemMapper.load(id);
		if (gdsapDictItem == null) return null;
		return new DictItem(gdsapDictItem);
	}

	public DictItem getDictItem(DictType dictType, Double calCode) {
		if (dictType == null || calCode == null) return null;
		
		GdsapDictItem gdsapDictItem = dictItemMapper.findByDictTypeCalCode(dictType.toString(), calCode);
		if (gdsapDictItem == null) return null;
		return new DictItem(gdsapDictItem);
	}
	
	public DictItem getDictItem(DictType dictType, String lodgeCode) {
		if (dictType == null || lodgeCode == null) return null;
		
		GdsapDictItem gdsapDictItem = dictItemMapper.findByDictTypeLodgeCode(dictType.toString(), lodgeCode);
		if (gdsapDictItem == null) return null;
		return new DictItem(gdsapDictItem);
	}
	
	public int getDictItemId(DictType dictType, double calCode) {

		return 0;
	}

	public List<DictItem> getDictItems(DictType dictType) {
		List<DictItem> bos = new ArrayList<DictItem>();
		List<GdsapDictItem> pos = dictItemMapper.findDictItemByDictType(dictType.toString());
		
		if (pos != null && pos.size()>0){
			for (GdsapDictItem po : pos) {
				bos.add(new DictItem(po));
			}
		}
		return bos;
	}

	public List<DictItem> getDictItems(DictCondType condType) {
		List<DictItem> bos = new ArrayList<DictItem>();
		List<GdsapDictItem> pos = dictItemMapper.findDictItemByCondType(condType.toString());
		
		if (pos != null && pos.size()>0){
			for (GdsapDictItem po : pos) {
				bos.add(new DictItem(po));
			}
		}
		return bos;
	}

	public void saveDictCond(DictCondType condType, Integer[] dictItemIds) {
		dictCondMapper.deleteAllByDictCond(condType.toString());
		for (Integer dictItemId : dictItemIds) {
			GdsapDictItem item = dictItemMapper.load(dictItemId);
			if (item != null) {
				GdsapDictCond gdsapDictCond = new GdsapDictCond();
				gdsapDictCond.setCondType(condType.toString());
				gdsapDictCond.setDictItemId(dictItemId);
				this.dictCondMapper.insert(gdsapDictCond);
			}
		}
	}

	@Override
	public DictItem updateDictItem(DictItem dictItem)
	{
		Assert.notNull(dictItem);
		GdsapDictItem model = new GdsapDictItem();
		model.setCalCode(dictItem.getCalCode());
		model.setDictType(dictItem.getDictType());
		model.setId(dictItem.getId());
		model.setLodgeCode(dictItem.getLodgeCode());
		model.setName(dictItem.getName());
		model.setSequence(dictItem.getSequence());
		this.dictItemMapper.update(model);
		return dictItem;
	}

}
