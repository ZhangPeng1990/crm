/**
 * 
 */
package uk.co.quidos.gdsap.framework.common.services;

import java.util.List;

import uk.co.quidos.gdsap.framework.common.Preference;
import uk.co.quidos.gdsap.framework.common.enums.PreType;
import uk.co.quidos.gdsap.framework.common.enums.PreferenceRelId;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
public interface PreferenceServiceMgr extends BusinessObjectServiceMgr
{
	public static final String SERVICE_NAME = "preferenceServiceMgr";
	
	/**
	 * 根据id获取Preference
	 * @param id
	 * @return
	 */
	Preference getPreference(PreferenceRelId relId);
	
	/**
	 * 添加Preference
	 * @param preference
	 * @return
	 * @throws ObjectDuplicateException
	 */
	Preference addPreference(Preference preference) throws ObjectDuplicateException;
	
	/**
	 * 更新Preference
	 * @param preference
	 * @return
	 */
	Preference updatePreference(Preference preference);
	
	/**
	 * 删除Preference
	 * @param id
	 */
	void deletePreference(String id);
	
	/**
	 * 管理员查询Preference
	 * @param preType
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Preference> getPreferences(PreType preType,int offset,int limit);
	
	/**
	 * 获取Preference数量
	 * @param preType
	 * @return
	 */
	int getTotalPreferences(PreType preType);
	
}
