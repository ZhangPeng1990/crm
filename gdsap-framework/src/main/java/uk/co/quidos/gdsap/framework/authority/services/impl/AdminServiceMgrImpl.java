package uk.co.quidos.gdsap.framework.authority.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import uk.co.quidos.common.util.exception.ASRuntimeException;
import uk.co.quidos.common.util.exception.UserException;
import uk.co.quidos.gdsap.framework.authority.ACL;
import uk.co.quidos.gdsap.framework.authority.Admin;
import uk.co.quidos.gdsap.framework.authority.persistence.object.AdminDO;
import uk.co.quidos.gdsap.framework.authority.persistence.object.AdminRoleRelDOKey;
import uk.co.quidos.gdsap.framework.authority.Role;
import uk.co.quidos.gdsap.framework.authority.persistence.AdminDOMapper;
import uk.co.quidos.gdsap.framework.authority.persistence.AdminRoleRelDOMapper;
import uk.co.quidos.gdsap.framework.authority.services.AdminServiceMgr;
import uk.co.quidos.gdsap.framework.authority.services.RoleServiceMgr;
import uk.co.quidos.gdsap.framework.authority.services.object.QueryAdminObject;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.sys.services.AbstractServiceMgr;
import uk.co.quidos.gdsap.framework.user.enums.AccessLevel;
import uk.co.quidos.gdsap.framework.user.enums.UserStatus;

import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

@Service("adminServiceMgr")
public class AdminServiceMgrImpl extends AbstractServiceMgr implements AdminServiceMgr
{

	private static final long serialVersionUID = -3865626210574984459L;
	
	private AdminRoleRelDOMapper adminRoleRelDOMapper;
	private RoleServiceMgr roleServiceMgr;
	private AdminDOMapper adminDOMapper;
	
	@Autowired
	public void setAdminRoleRelDOMapper(AdminRoleRelDOMapper adminRoleRelDOMapper) {
		this.adminRoleRelDOMapper = adminRoleRelDOMapper;
	}
	@Autowired
	public void setRoleServiceMgr(RoleServiceMgr roleServiceMgr) {
		this.roleServiceMgr = roleServiceMgr;
	}
	@Autowired
	public void setAdminDOMapper(AdminDOMapper adminDOMapper) {
		this.adminDOMapper = adminDOMapper;
	}
	
	@Override
	public Admin addAdmin(final Admin admin) throws ObjectDuplicateException {
		Assert.notNull(admin);
		Assert.notEmpty(admin.getRoles());
		AdminDO temp = this.adminDOMapper.findUserByName(admin.getUserName());
		if (temp != null)
		{
			throw new ObjectDuplicateException();
		}
		try
		{
			this.execute(new TransactionCallbackWithoutResult()
			{
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status)
				{
					admin.setInsertTime(new Date());
					admin.setUpdateTime(new Date());
					admin.setAccessLevel(AccessLevel.U00);
					AdminDO adminDO = BeanUtils.getAdminDO(admin);
					adminDOMapper.insert(adminDO);
					admin.setId(adminDO.getId());
					if(admin.getRoles() != null && admin.getRoles().size() > 0)
					{
						for(Role role : admin.getRoles())
						{
							AdminRoleRelDOKey key = new AdminRoleRelDOKey();
							key.setAdminId(admin.getId());
							key.setRoleId(role.getId());
							adminRoleRelDOMapper.insert(key);
						}
					}
				}
			});
		}
		catch (ASRuntimeException e)
		{
			if (e.getCause() instanceof ObjectDuplicateException)
			{
				throw new ObjectDuplicateException();
			}
		}
		
		return admin;
	}

	@Override
	public Admin updateAdmin(final Admin admin) {
		this.execute(new TransactionCallbackWithoutResult()
		{
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status)
			{
				admin.setUpdateTime(admin.getUpdateTime());
				AdminDO adminDO = BeanUtils.getAdminDO(admin);
				adminDOMapper.update(adminDO);
				
				//更新Roles
				adminRoleRelDOMapper.deleteByAdminId(admin.getId());
				//插入
				if(admin.getRoles() != null && admin.getRoles().size() > 0)
				{
					for (Role role : admin.getRoles())
					{
						AdminRoleRelDOKey key = new AdminRoleRelDOKey();
						key.setAdminId(admin.getId());
						key.setRoleId(role.getId());
						adminRoleRelDOMapper.insert(key);
					}
				}
			}
		});
		return admin;
	}

	@Override
	public Admin updateAdminBaseInfo(Admin admin) {
		final Admin temp = this.getAdmin(admin.getId());
		if (temp == null)
		{
			throw new IllegalArgumentException();
		}
		temp.setEmail(admin.getEmail());
		temp.setFirstName(admin.getFirstName());
		temp.setSurName(admin.getSurName());
		temp.setTel(admin.getTel());
		temp.setUpdateTime(new Date());
		temp.setUserPwd(admin.getUserPwd());
		this.execute(new TransactionCallbackWithoutResult()
		{
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status)
			{
				AdminDO adminDO = BeanUtils.getAdminDO(temp);
				adminDOMapper.updateSelective(adminDO);
			}
		});
		return temp;
	}

	@Override
	public Admin getAdmin(int id) {
		AdminDO adminDO = adminDOMapper.load(new Long(id));
		if(adminDO != null)
		{
			Admin admin = BeanUtils.getAdmin(adminDO);
			if (!admin.getUserName().equals(AdminServiceMgr.DEFAULT_ROOT_NAME))
			{
				List<String> roleIds = this.adminRoleRelDOMapper.findRoleIdsByAdminId(admin.getId());
				if (CollectionUtils.isEmpty(roleIds))
				{
					throw new IllegalArgumentException();
				}
				Set<Role> roles = new LinkedHashSet<Role>();
				for (String roleId : roleIds)
				{
					Role role = this.roleServiceMgr.getRole(roleId);
					roles.add(role);
				}
				admin.setRoles(roles);
			}
			else
			{
				List<String> roleIds = this.adminRoleRelDOMapper.findRoleIdsByAdminId(admin.getId());
				if (CollectionUtils.isEmpty(roleIds))
				{
					throw new IllegalArgumentException();
				}
				Set<Role> roles = new LinkedHashSet<Role>();
				for (String roleId : roleIds)
				{
					Role role = this.roleServiceMgr.getRole(roleId);
					roles.add(role);
				}
				admin.setRoles(roles);
			}
			return admin;
		}
			return null;
	}

	@Override
	public Admin initialAdmin(Admin baseUser) {
		return null;
	}

	@Override
	public List<Admin> getAdmins(Role role, QueryAdminObject object) {
		String userName = null;
		String firstName = null;
		String surName = null;
		Integer userStatus = null;
		
		if(object != null)
		{
			if(object.getKeywords() != null && !object.getKeywords().equals(""))
			{
				userName = object.getKeywords();
				firstName = object.getKeywords();
				surName = object.getKeywords();
			}
			if(object.getUserStatus() != null)
			{
				userStatus = object.getUserStatus().getCode();
			}
		}
		
		RowBounds rb = new RowBounds(object.getStartIndex(), object.getPageSize());
		List<Integer> listIds = new ArrayList<Integer>();
		if(role != null)
		{
			listIds = adminRoleRelDOMapper.findAdminIdByRoleId(role.getId(),userName,firstName,surName,userStatus,rb);
		}
		else
		{
			listIds = adminRoleRelDOMapper.findAdminIdByRoleId(null,userName,firstName,surName,userStatus,rb);
		}
		
		List<Admin> listAdmin = new ArrayList<Admin>();
		if(!CollectionUtils.isEmpty(listIds))
		{
			for(Integer i : listIds)
			{
				Admin admin = this.getAdmin(i);
				listAdmin.add(admin);
			}
			
		}
		return listAdmin;
	}

	@Override
	public void deleteAdmin(Admin admin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTotalAdmins(Role role, QueryAdminObject object) {
		String userName = null;
		String firstName = null;
		String surName = null;
		Integer userStatus = null;
		
		if(object != null)
		{
			if(object.getKeywords() != null && !object.getKeywords().equals(""))
			{
				userName = object.getKeywords();
				firstName = object.getKeywords();
				surName = object.getKeywords();
			}
			if(object.getUserStatus() != null)
			{
				userStatus = object.getUserStatus().getCode();
			}
		}
		
		if(role != null)
		{
			return adminRoleRelDOMapper.getAdminNumByRoleId(role.getId(),userName,firstName,surName,userStatus);
		}
		else
		{
			return adminRoleRelDOMapper.getAdminNumByRoleId(null,userName,firstName,surName,userStatus);
		}
		
	}

	@Override
	public boolean hasAuthority(Admin admin, ACL acl) {
		Assert.notNull(admin);
		Assert.notEmpty(admin.getAcls());
		if (admin.isRoot() || admin.getAcls().contains(acl))
		{
			return true;
		}
		return false;
	}

	@Override
	public Admin getAdminByName(String username) {
		AdminDO adminDO = this.adminDOMapper.findUserByName(username);
		if(adminDO != null)
		{
			Admin admin = BeanUtils.getAdmin(adminDO);
			return admin;
		}
		return null;
	}

	@Override
	public Admin initialRootAdmin() throws ObjectDuplicateException {
		Admin admin = new Admin();
		admin.setUserName(DEFAULT_ROOT_NAME);
		admin.setUserPwd(DEFAULT_ROOT_PASSWORD);
		return this.addAdmin(admin);
	}
	
	@Override
	public Admin login(String username, String password) throws UserException
	{
		Assert.hasText(username);
		Assert.hasText(password);
		final AdminDO userDO = this.adminDOMapper.findByUsernameAndPwd(username, password);
		if (userDO == null)
		{
			throw new UserException(UserException.USER_PASSWORD_NOT_MATCH_ERROR_CODE);
		}
//		if (userDO.getUserStatus() != UserStatus.Active.getCode() && userDO.getUserType().equals(UserType.Auditor))
		if (userDO.getUserStatus() != UserStatus.Active.getCode())
		{
			throw new UserException(UserException.USER_INACTIVE_ERROR_CODE);
		}
		return BeanUtils.getAdmin(userDO);
	}

}
