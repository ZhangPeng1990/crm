/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services;

import uk.co.quidos.gdsap.evaluation.Occupants;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
public interface OccupantsServiceMgr extends BusinessObjectServiceMgr
{
	/**
	 * 如Report 存在Occupants ，throws IllegaArgumentException
	 * @param occupants
	 * @return
	 */
	public Occupants addOccupants(Occupants occupants);
	
	/**
	 * 如Report 不存在Occupants ，throws IllegaArgumentException
	 * @param occupants
	 * @return
	 */
	public Occupants updateOccupants(Occupants occupants);
	
	/**
	 * @param reportId
	 * @return
	 */
	public Occupants getOccupants(long reportId);
	
	/**
	 * @param reportId
	 */
	public void deleteOccupants(long reportId);
	
}
