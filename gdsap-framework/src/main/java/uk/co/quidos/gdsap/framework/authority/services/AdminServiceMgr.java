/**
 * 
 */
package uk.co.quidos.gdsap.framework.authority.services;

import java.util.List;

import uk.co.quidos.common.util.exception.UserException;
import uk.co.quidos.gdsap.framework.authority.ACL;
import uk.co.quidos.gdsap.framework.authority.Admin;
import uk.co.quidos.gdsap.framework.authority.Role;
import uk.co.quidos.gdsap.framework.authority.services.object.QueryAdminObject;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;

/**
 * @author peng.shi
 */
public interface AdminServiceMgr
{
	public static final String SERVICE_NAME = "adminServiceMgr";
	
	/**
	 * 最高权限用户名称
	 */
	public static final String DEFAULT_ROOT_NAME = "ROOT";
	
	/**
	 * 系统初始化ROOT 密码
	 */
	public static final String DEFAULT_ROOT_PASSWORD = "ROOT";
	
	/**
	 * Set<Role>不能为null
	 * @param admin
	 * @return
	 * @throws ObjectDuplicateException
	 */
	Admin addAdmin(Admin admin) throws ObjectDuplicateException;
	
	/**
	 * 更新Admin信息，用户名改变不了.
	 * @param admin
	 * @return
	 */
	Admin updateAdmin(Admin admin);
	
	/**
	 * 更新Admin 基本信息
	 * @param admin
	 * @return
	 */
	Admin updateAdminBaseInfo(Admin admin);
	
	/**
	 * 获取Admin全部信息
	 * @param id
	 * @return
	 */
	Admin getAdmin(int id);
	
	/**
	 * 初始化Admin 全部信息
	 * @param baseUser
	 * @return
	 */
	Admin initialAdmin(Admin baseUser);
	
	/**
	 * 获取Role下的全部Admin列表
	 * @param role
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Admin> getAdmins(Role role, QueryAdminObject object);
	
	/**
	 * 删除管理员
	 * @param admin
	 */
	void deleteAdmin(Admin admin);
	
	/**
	 * @param role
	 * @return
	 */
	int getTotalAdmins(Role role,QueryAdminObject object);
	
	/**
	 * 判定是否有权限
	 * @param admin
	 * @param acl
	 * @return
	 */
	boolean hasAuthority(Admin admin,ACL acl);
	
	/**
	 * 通过用户名获取Admin全部信息
	 * @param username
	 * @return
	 */
	Admin getAdminByName(String username);
	
	/**
	 * 系统初始化Root用户
	 * @return
	 */
	Admin initialRootAdmin() throws ObjectDuplicateException;
	
	/*
	 * 登录
	 */
	Admin login(String username, String password) throws UserException;
}
