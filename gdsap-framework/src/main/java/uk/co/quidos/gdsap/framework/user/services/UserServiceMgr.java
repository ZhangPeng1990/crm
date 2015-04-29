/**
 * 
 */
package uk.co.quidos.gdsap.framework.user.services;

import java.util.List;

import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;
import uk.co.quidos.gdsap.framework.user.User;
import uk.co.quidos.gdsap.framework.user.enums.UserStatus;
import uk.co.quidos.gdsap.framework.user.services.object.ConditionVO;

/**
 * @author peng.shi
 */
public interface UserServiceMgr extends BusinessObjectServiceMgr
{
	public static final String SERVICE_NAME = "userServiceMgr";
	
	/**
	 * 通过Id 获取User
	 * @param id
	 * @return
	 */
	User getUser(long id);
	
	/**
	 * 通过用户名获取User
	 * @param username
	 * @return
	 */
	User getUserByUsername(String username);
	
	/**
	 * 通过Email获取用户信息
	 * @param email
	 * @return
	 */
	User getUserByEmail(String email);
	
	/**
	 * 管理员添加用户，不具备邮件发送功能，UserStatus 也直接置变成 Active状态.
	 * @param user
	 * @return
	 * @throws ObjectDuplicateException
	 */
	User addUser(User user) throws ObjectDuplicateException;
	
	/**
	 * 前台注册用户
	 * @param user
	 * @return
	 * @throws ObjectDuplicateException
	 */
	User registerUser(User user) throws ObjectDuplicateException;
	
	/**
	 * 用户登录
	 * @param userName userPwd
	 */
	boolean listUsers(String userName,String userPwd);
	
	/**
	 * 更新用户基本信息，不包含 UserStatus信息
	 * @param user
	 * @return
	 * @throws ObjectDuplicateException 
	 */
	User updateUser(User user);
	
	/**
	 * 更改用户名
	 */
	User updateUserName(User user) throws ObjectDuplicateException;
	
	/**
	 * 管理员根据用户Id 更新用户状态
	 * @param userId
	 * @return
	 */
	User updateUserStatus(long userId,UserStatus userStatus);
	
	/**
	 * 更新用户密码，并返回新的用户信息
	 * @param id
	 * @param newPassword
	 * @return
	 */
	User udpatePassword(long id,String newPassword);
	
	/**
	 * 查询Users,根据CondtionVo 内设置的条件
	 * @param vo
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<User> getUsers(ConditionVO vo,int offset,int limit);
	
	/**
	 * 获取数量
	 * @param vo
	 * @return
	 */
	int getTotalUsers(ConditionVO vo);
	
}
