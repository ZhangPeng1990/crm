/**
 * 
 */
package uk.co.quidos.gdsap.framework.common.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import uk.co.quidos.gdsap.framework.common.Preference;
import uk.co.quidos.gdsap.framework.common.enums.PreType;
import uk.co.quidos.gdsap.framework.common.enums.PreferenceRelId;
import uk.co.quidos.gdsap.framework.common.persistence.GdsapSysPreferenceMapper;
import uk.co.quidos.gdsap.framework.common.persistence.object.GdsapSysPreference;
import uk.co.quidos.gdsap.framework.common.services.PreferenceServiceMgr;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;
import uk.co.quidos.gdsap.framework.sys.lang.enums.EnumUtils;

/**
 * @author peng.shi
 *
 */
@Service("preferenceServiceMgr")
@Transactional
public class PreferenceServiceMgrImpl extends AbsBusinessObjectServiceMgr implements PreferenceServiceMgr 
{
	@Autowired
	private GdsapSysPreferenceMapper gdsapSysPreferenceMapper;
	
	/**
	 * @return the gdsapSysPreferenceMapper
	 */
	public GdsapSysPreferenceMapper getGdsapSysPreferenceMapper()
	{
		return gdsapSysPreferenceMapper;
	}

	/**
	 * @param gdsapSysPreferenceMapper the gdsapSysPreferenceMapper to set
	 */
	public void setGdsapSysPreferenceMapper(GdsapSysPreferenceMapper gdsapSysPreferenceMapper)
	{
		this.gdsapSysPreferenceMapper = gdsapSysPreferenceMapper;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.common.services.PreferenceServiceMgr#getPreference(java.lang.String)
	 */
	@Override
	public Preference getPreference(PreferenceRelId relId)
	{
		Assert.notNull(relId);
		GdsapSysPreference model = this.getGdsapSysPreferenceMapper().load(relId.getCode());

		if (model != null)
		{
			Preference bo = BeanUtils.do2bo(model);
			return bo;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.common.services.PreferenceServiceMgr#addPreference(uk.co.quidos.gdsap.framework.common.Preference)
	 */
	@Override
	public Preference addPreference(Preference preference) throws ObjectDuplicateException
	{
		Assert.notNull(preference);
		this._validateAssertPreference(preference);
		
		GdsapSysPreference model = BeanUtils.bo2do(preference);
		GdsapSysPreference temp = this.getGdsapSysPreferenceMapper().load(preference.getId());
		if (temp != null)
		{
			throw new ObjectDuplicateException();
		}
		Date insertTime = new Date();
		model.setInsertTime(insertTime);
		model.setUpdateTime(insertTime);
		this.getGdsapSysPreferenceMapper().insert(model);
		
		preference = BeanUtils.do2bo(model);
		return preference;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.common.services.PreferenceServiceMgr#updatePreference(uk.co.quidos.gdsap.framework.common.Preference)
	 */
	@Override
	public Preference updatePreference(Preference preference)
	{
		_validateAssertPreference(preference);
		GdsapSysPreference modelOfLoad = this.getGdsapSysPreferenceMapper().load(preference.getId());
		
		if (modelOfLoad == null)
		{
			this.assertThrowIllegalArgumentException();
		}
		
		modelOfLoad.setContent(preference.getContent());
		modelOfLoad.setControlType(preference.getControlType().getCode());
		modelOfLoad.setInputType(preference.getInputType().getCode());
		modelOfLoad.setPreType(preference.getPreType().getCode());
		modelOfLoad.setTitle(preference.getTitle());
		modelOfLoad.setUpdateTime(new Date());
		this.getGdsapSysPreferenceMapper().update(modelOfLoad);
		preference = BeanUtils.do2bo(modelOfLoad);
		return preference;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.common.services.PreferenceServiceMgr#deletePreference(uk.co.quidos.gdsap.framework.common.Preference)
	 */
	@Override
	public void deletePreference(String id)
	{
		Assert.hasText(id);
		GdsapSysPreference model = this.getGdsapSysPreferenceMapper().load(id);
		if (model != null)
		{
			this.getGdsapSysPreferenceMapper().delete(id);
		}
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.common.services.PreferenceServiceMgr#getPreferences(uk.co.quidos.gdsap.framework.common.enums.PreType, int, int)
	 */
	@Override
	public List<Preference> getPreferences(PreType preType, int offset, int limit)
	{
		Integer iPreType = null;
		if (preType != null)
		{
			iPreType = preType.getCode();
		}
		
		List<String> ids = this.getGdsapSysPreferenceMapper().findPageBreakIdsByPreType(iPreType, new RowBounds(offset, limit));
		if (!CollectionUtils.isEmpty(ids))
		{
			List<Preference> preferences = new ArrayList<Preference>();
			for (String id : ids)
			{
				preferences.add(this.getPreference((PreferenceRelId)EnumUtils.getByCode(id, PreferenceRelId.class)));
			}
			return preferences;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.common.services.PreferenceServiceMgr#getTotalPreferences(uk.co.quidos.gdsap.framework.common.enums.PreType)
	 */
	@Override
	public int getTotalPreferences(PreType preType)
	{
		Integer iPreType = null;
		if (preType != null)
		{
			iPreType = preType.getCode();
		}
		return this.getGdsapSysPreferenceMapper().findNumberByPreType(iPreType);
	}

	private void _validateAssertPreference(Preference preference)
	{
		//private String id;
		Assert.hasText(preference.getId());
		//private PreType preType;
		Assert.notNull(preference.getPreType());
		//private String title;
		Assert.hasText(preference.getTitle());
		//private String content;
		Assert.hasText(preference.getContent());
		//private InputType inputType;
		Assert.notNull(preference.getInputType());
		//private ControlType controlType;
		Assert.notNull(preference.getControlType());
		
	}
}
