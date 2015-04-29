/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services;

import java.util.List;

import uk.co.quidos.gdsap.evaluation.Recommendation;
import uk.co.quidos.gdsap.evaluation.RelatedPartyDisclosure;
import uk.co.quidos.gdsap.evaluation.enums.Country;
import uk.co.quidos.gdsap.evaluation.enums.Language;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
public interface LandmarkDefineServiceMgr extends BusinessObjectServiceMgr
{
	public static final String SERVICE_NAME = "landmarkDefineServiceMgr";
	
	/**
	 * 通过Id，country，language 获取Recommendation
	 * @param id
	 * @param country
	 * @param language
	 * @return
	 */
	Recommendation getRecommentation(int id,Country country,Language language);
	
	/**
	 * 根据Ids，country，language 获取Recommendation列表
	 * @param ids
	 * @param country
	 * @param language
	 * @return
	 */
	List<Recommendation> getRecommentations(int[] ids,Country country,Language language);
	
	/**
	 * 获取RelatedPartyDisclorure
	 * @param id
	 * @param language
	 * @return
	 */
	RelatedPartyDisclosure getRelatedPartyDisclosure(int id,Language language);
	
	/**
	 * 获取RelatedPartyDisclosure 列表
	 * @param language
	 * @return
	 */
	List<RelatedPartyDisclosure> getRelatedPartyDisclosures(Language language);
	
}
