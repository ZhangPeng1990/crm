/**
 * 
 */
package uk.co.quidos.gdsap.framework.authority.services;

import java.util.List;
import java.util.Map;

import uk.co.quidos.gdsap.framework.authority.ACL;
import uk.co.quidos.gdsap.framework.authority.enums.ACLGroup;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;

/**
 * @author peng.shi
 */
public interface ACLServiceMgr
{
	public static final String SERVICE_NAME = "aclServiceMgr";
	
	/**
	 * @param acl
	 * @return
	 * @throws ObjectDuplicateException
	 */
	ACL addAcl(ACL acl) throws ObjectDuplicateException;
	
	/**
	 * @param id
	 * @return
	 */
	ACL getAcl(String id);
	
	/**
	 * @param acl
	 * @return
	 */
	ACL updateAcl(ACL acl);
	
	/**
	 * @param acl
	 */
	void deleteAcl(ACL acl);
	
	/**
	 * 获取全部ACLs，在页面分组进行展示 
	 * @return
	 */
	Map<ACLGroup,List<ACL>> getAcls();
	
}
