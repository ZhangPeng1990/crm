/**
 * 
 */
package uk.co.quidos.gdsap.framework.authority.services;

import java.util.Set;

import uk.co.quidos.gdsap.framework.authority.Role;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;


/**
 * @author peng.shi
 */
public interface RoleServiceMgr
{
	public static final String SERVICE_NAME = "roleServiceMgr";
	
	/**
	 * ACLs以及PageTags不能为null
	 * @param role
	 * @return
	 */
	Role addRole(Role role) throws ObjectDuplicateException;
	
	/**
	 * @param id
	 * @return
	 */
	Role getRole(String id);
	
	/**
	 * @param role
	 * @return
	 */
	Role updateRole(Role role);
	
	/**
	 * @return
	 */
	Set<Role> getRoles();
	
}
