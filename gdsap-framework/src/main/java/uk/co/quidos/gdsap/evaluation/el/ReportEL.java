package uk.co.quidos.gdsap.evaluation.el;

import java.util.List;

import uk.co.quidos.gdsap.evaluation.DictItem;
import uk.co.quidos.gdsap.evaluation.enums.DictCondType;
import uk.co.quidos.gdsap.evaluation.enums.DictType;
import uk.co.quidos.gdsap.evaluation.services.DictServiceMgr;
import uk.co.quidos.gdsap.framework.sys.business.BusinessFactory;

public class ReportEL
{
	public static List<DictItem> getDictItems(String dictType)
	{
		DictServiceMgr dictServiceMgr = BusinessFactory.getInstance().getService("dictServiceMgr");
		return dictServiceMgr.getDictItems(DictType.valueOf(dictType));
	}

	public static List<DictItem> getDictCondItems(String condType)
	{
		DictServiceMgr dictServiceMgr = BusinessFactory.getInstance().getService("dictServiceMgr");
		return dictServiceMgr.getDictItems(DictCondType.valueOf(condType));
	}
}
