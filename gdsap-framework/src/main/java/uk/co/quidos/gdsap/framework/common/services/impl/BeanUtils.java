package uk.co.quidos.gdsap.framework.common.services.impl;


import uk.co.quidos.gdsap.framework.common.Preference;
import uk.co.quidos.gdsap.framework.common.enums.ControlType;
import uk.co.quidos.gdsap.framework.common.enums.InputType;
import uk.co.quidos.gdsap.framework.common.enums.PreType;
import uk.co.quidos.gdsap.framework.common.enums.PreferenceRelId;
import uk.co.quidos.gdsap.framework.common.persistence.object.GdsapSysPreference;
import uk.co.quidos.gdsap.framework.sys.lang.enums.EnumUtils;

/**
 * @author peng.shi
 *
 */
public class BeanUtils
{	
	public static GdsapSysPreference bo2do(Preference preference)
	{
		GdsapSysPreference gdsapSysPreference = new GdsapSysPreference();
		gdsapSysPreference.setTitle(preference.getTitle());
		gdsapSysPreference.setPreType(preference.getPreType().getCode());
		gdsapSysPreference.setInsertTime(preference.getInsertTime());
		gdsapSysPreference.setUpdateTime(preference.getUpdateTime());
		gdsapSysPreference.setInputType(preference.getInputType().getCode());
		gdsapSysPreference.setControlType(preference.getControlType().getCode());
		gdsapSysPreference.setContent(preference.getContent());
		gdsapSysPreference.setId(preference.getPreferenceRelId().getCode());
		return gdsapSysPreference;
	}
	
	public static Preference do2bo(GdsapSysPreference gdsapSysPreference)
	{
		Preference preference = new Preference();
		preference.setPreType((PreType)EnumUtils.getByCode(gdsapSysPreference.getPreType(), PreType.class));
		preference.setTitle(gdsapSysPreference.getTitle());
		preference.setContent(gdsapSysPreference.getContent());
		preference.setInsertTime(gdsapSysPreference.getInsertTime());
		preference.setUpdateTime(gdsapSysPreference.getUpdateTime());
		preference.setInputType((InputType)EnumUtils.getByCode(gdsapSysPreference.getInputType(),InputType.class));
		preference.setControlType((ControlType)EnumUtils.getByCode(gdsapSysPreference.getControlType(),ControlType.class));
		preference.setPreferenceRelId((PreferenceRelId)EnumUtils.getByCode(gdsapSysPreference.getId(), PreferenceRelId.class));
		return preference;
	}
	
}
