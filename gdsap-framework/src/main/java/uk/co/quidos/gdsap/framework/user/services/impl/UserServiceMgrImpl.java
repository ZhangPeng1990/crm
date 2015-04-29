/**
 * 
 */
package uk.co.quidos.gdsap.framework.user.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;
import uk.co.quidos.gdsap.framework.sys.business.OrderDirection;
import uk.co.quidos.gdsap.framework.user.User;
import uk.co.quidos.gdsap.framework.user.enums.AccessLevel;
import uk.co.quidos.gdsap.framework.user.enums.UserOrderField;
import uk.co.quidos.gdsap.framework.user.enums.UserStatus;
import uk.co.quidos.gdsap.framework.user.persistence.GdsapUsrUserMapper;
import uk.co.quidos.gdsap.framework.user.persistence.object.GdsapUsrUser;
import uk.co.quidos.gdsap.framework.user.services.UserServiceMgr;
import uk.co.quidos.gdsap.framework.user.services.object.ConditionVO;
import uk.co.quidos.gdsap.framework.user.utils.StringBuilder;

/**
 * @author peng.shi
 *
 */
@Transactional
@Service("userServiceMgr")
public class UserServiceMgrImpl extends AbsBusinessObjectServiceMgr implements UserServiceMgr
{
	
	@Autowired
	private GdsapUsrUserMapper gdsapUsrUserMapper;
	
	/**
	 * @return the gdsapUsrUserMapper
	 */
	public GdsapUsrUserMapper getGdsapUsrUserMapper()
	{
		return gdsapUsrUserMapper;
	}

	/**
	 * @param gdsapUsrUserMapper the gdsapUsrUserMapper to set
	 */
	public void setGdsapUsrUserMapper(GdsapUsrUserMapper gdsapUsrUserMapper)
	{
		this.gdsapUsrUserMapper = gdsapUsrUserMapper;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.user.services.UserServiceMgr#addUser(uk.co.quidos.gdsap.framework.user.User)
	 */
	@Override
	public User addUser(User user) throws ObjectDuplicateException
	{
		Assert.notNull(user);
		Assert.hasText(user.getUserName());
//		Assert.hasText(user.getFirstName());
//		Assert.hasText(user.getSurName());
//		Assert.hasText(user.getEmail());
//		Assert.notNull(user.getAccessLevel());
		
		if (this.gdsapUsrUserMapper.findUserByName(user.getUserName()) != null)
		{
			throw new ObjectDuplicateException();
		}
		
		GdsapUsrUser gdsapUsrUser = BeanUtils.user2gdsapUsr(user);
		gdsapUsrUser.setInsertTime(new Date());
		gdsapUsrUser.setUpdateTime(new Date());
		gdsapUsrUser.setId(null);
		gdsapUsrUser.setAccessLevel(AccessLevel.U00.getCode());
		this.gdsapUsrUserMapper.insert(gdsapUsrUser);
		user = BeanUtils.gdsapUsr2User(gdsapUsrUser);
	    return user;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.user.services.UserServiceMgr#getUser(long)
	 */
	@Override
	public User getUser(long id)
	{
		GdsapUsrUser gdsapUsrUser = gdsapUsrUserMapper.load(id);
		if (gdsapUsrUser != null)
		{
			return BeanUtils.gdsapUsr2User(gdsapUsrUser);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.user.services.UserServiceMgr#getUserByUsername(java.lang.String)
	 */
	@Override
	public User getUserByUsername(String username)
	{ 
		GdsapUsrUser gdsapUsrUser = this.gdsapUsrUserMapper.findUserByName(username);
		if (gdsapUsrUser != null){
			return BeanUtils.gdsapUsr2User(gdsapUsrUser);
		}
		return null;
	}

	@Override
	public User getUserByEmail(String email)
	{
		GdsapUsrUser gdsapUsrUser = this.gdsapUsrUserMapper.findUserByEmail(email);
		if (gdsapUsrUser != null){
			return BeanUtils.gdsapUsr2User(gdsapUsrUser);
		}
		return null;
	}

	public boolean listUsers(String userName,String userPwd){
//		Assert.hasText(userName);
//		Assert.hasText(userPwd);
		List<User> listUser = new ArrayList<User>();
		List<GdsapUsrUser> listgUser = gdsapUsrUserMapper.findUserList(userName, userPwd);
		for(GdsapUsrUser gUser : listgUser){
			User user = BeanUtils.gdsapUsr2User(gUser);
			listUser.add(user);
		}
		return listUser.size() > 0;
	}
	
	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.user.services.UserServiceMgr#registerUser(uk.co.quidos.gdsap.framework.user.User)
	 */
	@Override
	public User registerUser(User user) throws ObjectDuplicateException
	{
		Assert.notNull(user);
		Assert.hasText(user.getUserName());
		Assert.hasText(user.getFirstName());
		Assert.hasText(user.getSurName());
		Assert.hasText(user.getEmail());
		Assert.notNull(user.getAccessLevel());
		
		if (gdsapUsrUserMapper.findUserByName(user.getUserName()) != null)
		{
			throw new ObjectDuplicateException();
		}
	    GdsapUsrUser gdsapUsrUser = BeanUtils.user2gdsapUsr(user);
	    
	    gdsapUsrUser.setInsertTime(new Date());
	    gdsapUsrUser.setUpdateTime(new Date());
	    
	    String registerCode = StringBuilder.buildRegsiterCode();
	    
	    gdsapUsrUser.setRegisterCode(registerCode);
		gdsapUsrUser.setUserStatus(UserStatus.InActive.getCode());
		gdsapUsrUserMapper.insert(gdsapUsrUser);
		
		user = BeanUtils.gdsapUsr2User(gdsapUsrUser);
	    return user;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.user.services.UserServiceMgr#udpatePassword(java.lang.String, java.lang.String)
	 */
	@Override
	public User udpatePassword(long id, String newPassword)
	{
		GdsapUsrUser gdsapUsrUser = this.getGdsapUsrUserMapper().load(id);
		if (gdsapUsrUser == null)
		{
			throw new IllegalArgumentException();
		}
		
		gdsapUsrUser.setUpdateTime(new Date());
		gdsapUsrUser.setUserPwd(newPassword);
		this.gdsapUsrUserMapper.update(gdsapUsrUser);
		return BeanUtils.gdsapUsr2User(gdsapUsrUser);
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.user.services.UserServiceMgr#updateUser(uk.co.quidos.gdsap.framework.user.User)
	 */
	@Override
	public User updateUser(User user)
	{ 
		GdsapUsrUser gdsapUsrUser = this.getGdsapUsrUserMapper().load(user.getId());
		if (gdsapUsrUser == null)
		{
			throw new IllegalArgumentException();
		}
		gdsapUsrUser = BeanUtils.user2gdsapUsr(user);
		gdsapUsrUser.setUpdateTime(new Date());
		//gdsapUsrUser.setAccessLevel(user.getAccessLevel().getCode());
		gdsapUsrUser.setEmail(user.getEmail());
		gdsapUsrUser.setFirstName(user.getFirstName());
		gdsapUsrUser.setSurName(user.getSurName());
		gdsapUsrUserMapper.update(gdsapUsrUser);
		user = BeanUtils.gdsapUsr2User(gdsapUsrUser);
		return user;
	}
	
	@Override
	public User updateUserName(User user) throws ObjectDuplicateException
	{ 
		GdsapUsrUser gdsapUsrUser = this.getGdsapUsrUserMapper().load(user.getId());
		if (gdsapUsrUser == null)
		{
			throw new IllegalArgumentException();
		}
		if (this.getGdsapUsrUserMapper().findUserByName(user.getUserName()) != null)
		{
			throw new ObjectDuplicateException();
		}
		
		gdsapUsrUser = BeanUtils.user2gdsapUsr(user);
		gdsapUsrUser.setUpdateTime(new Date());
		gdsapUsrUserMapper.update(gdsapUsrUser);
		user = BeanUtils.gdsapUsr2User(gdsapUsrUser);
		return user;
	}

	/* (non-Javadoc)
	 * @see uk.co.quidos.gdsap.framework.user.services.UserServiceMgr#updateUserStatus(java.lang.String, uk.co.quidos.gdsap.framework.user.enums.UserStatus)
	 */
	@Override
	public User updateUserStatus(long userId, UserStatus userStatus)
	{
		GdsapUsrUser gdsapUsrUser = this.getGdsapUsrUserMapper().load(userId);
		if (gdsapUsrUser == null)
		{
			throw new IllegalArgumentException();
		}
		
		gdsapUsrUser.setUpdateTime(new Date());
		gdsapUsrUser.setUserStatus(userStatus.getCode());
		this.gdsapUsrUserMapper.update(gdsapUsrUser);
		return BeanUtils.gdsapUsr2User(gdsapUsrUser);
	}

	@Override
	public int getTotalUsers(ConditionVO vo)
	{
		String userName = null;
		String assessorID = null;
		Date startInsertTime = null;
		Date endInsertTime = null;
		Integer userStatus = null;
		Integer userType = null;
		String firstName = null;
		String surName = null;
		String email = null;
		Integer accessLevel = null;
		if (vo != null)
		{
			userName = vo.getUserName();
			startInsertTime = vo.getStartInsertTime();
			endInsertTime = vo.getEndInsertTime();
			userStatus = vo.getUserStatus() != null ? vo.getUserStatus().getCode() : null;
			userType = vo.getUserType() != null ? vo.getUserType().getCode() : null;
			firstName = vo.getFirstName();
			surName = vo.getSurName();
			email = vo.getEmail();
			accessLevel = vo.getAccessLevel() != null ? vo.getAccessLevel().getCode() : null;
		}
		if(vo != null && vo.getKeywords() != null && !vo.getKeywords().isEmpty() && !vo.getKeywords().equals(""))
		{
			userName = vo.getKeywords();
			assessorID = vo.getKeywords();
			firstName = vo.getKeywords();
			surName = vo.getKeywords();
			email = vo.getKeywords();
		}
		return this.gdsapUsrUserMapper.findNumberByCondition(userName, assessorID, startInsertTime,endInsertTime, userStatus,userType, firstName, surName, email, accessLevel);
		
	}

	@Override
	public List<User> getUsers(ConditionVO vo, int offset, int limit)
	{
		String userName = null;
		String assessorID = null;
		Date startInsertTime = null;
		Date endInsertTime = null;
		Integer userStatus = null;
		Integer userType = null;
		String firstName = null;
		String surName = null;
		String email = null;
		Integer accessLevel = null;
		UserOrderField orderField = UserOrderField.INSERT_DATE;
		OrderDirection orderDirection = OrderDirection.desc;
		if (vo != null)
		{
			userName = vo.getUserName();
			startInsertTime = vo.getStartInsertTime();
			endInsertTime = vo.getEndInsertTime();
			userStatus = vo.getUserStatus() != null ? vo.getUserStatus().getCode() : null;
			userType = vo.getUserType() != null ? vo.getUserType().getCode() : null;
			firstName = vo.getFirstName();
			surName = vo.getSurName();
			email = vo.getEmail();
			accessLevel = vo.getAccessLevel() != null ? vo.getAccessLevel().getCode() : null;
			if(StringUtils.hasText(vo.getOrderField())){
				orderField = UserOrderField.valueOf(vo.getOrderField());
			}
			
			if(StringUtils.hasText(vo.getOrderDirection())){
				orderDirection = OrderDirection.valueOf(vo.getOrderDirection());
			}
		}
		if(vo != null && vo.getKeywords() != null && !vo.getKeywords().isEmpty() && !vo.getKeywords().equals(""))
		{
			userName = vo.getKeywords();
			assessorID = vo.getKeywords();
			firstName = vo.getKeywords();
			surName = vo.getKeywords();
			email = vo.getKeywords();
		}
		List<GdsapUsrUser> models = this.gdsapUsrUserMapper.findPageBreakByCondition(userName, assessorID, startInsertTime,
				endInsertTime, userStatus, userType,firstName, surName, email, accessLevel, orderField.getDbField(), orderDirection.getDbOrder(), new RowBounds(offset, limit));
		if (!CollectionUtils.isEmpty(models))
		{
			List<User> users = new ArrayList<User>();
			for (GdsapUsrUser model : models)
			{
				users.add(BeanUtils.gdsapUsr2User(model));
			}
			return users;
		}
		return null;
	}

	
}
