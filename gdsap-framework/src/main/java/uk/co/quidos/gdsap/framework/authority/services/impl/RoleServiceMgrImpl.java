/**
 * 
 */
package uk.co.quidos.gdsap.framework.authority.services.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import uk.co.quidos.gdsap.framework.sys.lang.enums.EnumUtils;
import uk.co.quidos.gdsap.framework.authority.ACL;
import uk.co.quidos.gdsap.framework.authority.PageTag;
import uk.co.quidos.gdsap.framework.authority.Role;
import uk.co.quidos.gdsap.framework.authority.enums.ACLGroup;
import uk.co.quidos.gdsap.framework.authority.enums.PagetagGroup;
import uk.co.quidos.gdsap.framework.authority.persistence.AclDOMapper;
import uk.co.quidos.gdsap.framework.authority.persistence.PageTagDOMapper;
import uk.co.quidos.gdsap.framework.authority.persistence.RoleAclRelDOMapper;
import uk.co.quidos.gdsap.framework.authority.persistence.RoleDOMapper;
import uk.co.quidos.gdsap.framework.authority.persistence.RolePageTagRelDOMapper;
import uk.co.quidos.gdsap.framework.authority.persistence.object.AclDO;
import uk.co.quidos.gdsap.framework.authority.persistence.object.PageTagDO;
import uk.co.quidos.gdsap.framework.authority.persistence.object.RoleAclRelDOKey;
import uk.co.quidos.gdsap.framework.authority.persistence.object.RoleDO;
import uk.co.quidos.gdsap.framework.authority.persistence.object.RolePageTagRelDOKey;
import uk.co.quidos.gdsap.framework.authority.services.RoleServiceMgr;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.sys.services.AbstractServiceMgr;

/**
 * @author peng.shi
 */
@Service("roleServiceMgr")
public class RoleServiceMgrImpl extends AbstractServiceMgr implements RoleServiceMgr
{
	private static final long serialVersionUID = 6726564215850055283L;
	
	private RoleDOMapper roleDOMapper;
	private RolePageTagRelDOMapper rolePageTagRelDOMapper;
	private RoleAclRelDOMapper roleAclRelDOMapper;
	private PageTagDOMapper pageTagDOMapper;
	private AclDOMapper aclDOMapper;
	@Autowired
	public void setAclDOMapper(AclDOMapper aclDOMapper)
	{
		this.aclDOMapper = aclDOMapper;
	}
	@Autowired
	public void setPageTagDOMapper(PageTagDOMapper pageTagDOMapper)
	{
		this.pageTagDOMapper = pageTagDOMapper;
	}
	@Autowired
	public void setRolePageTagRelDOMapper(RolePageTagRelDOMapper rolePageTagRelDOMapper)
	{
		this.rolePageTagRelDOMapper = rolePageTagRelDOMapper;
	}
	@Autowired
	public void setRoleAclRelDOMapper(RoleAclRelDOMapper roleAclRelDOMapper)
	{
		this.roleAclRelDOMapper = roleAclRelDOMapper;
	}
	@Autowired
	public void setRoleDOMapper(RoleDOMapper roleDOMapper)
	{
		this.roleDOMapper = roleDOMapper;
	}

	@Override
	public Role addRole(final Role role) throws ObjectDuplicateException
	{
		Assert.notNull(role);
		Assert.notEmpty(role.getPageTags());
		Assert.notEmpty(role.getAcls());
		
		RoleDO tmpModel = this.roleDOMapper.selectByPrimaryKey(role.getId());
		if (tmpModel != null)
		{
			throw new ObjectDuplicateException();
		}
		this.execute(new TransactionCallbackWithoutResult()
		{
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status)
			{
				RoleDO model = new RoleDO();
				model.setDes(role.getDes());
				model.setId(role.getId());
				model.setRolename(role.getRoleName());
				roleDOMapper.insert(model);
				
				for (PageTag tag : role.getPageTags())
				{
					RolePageTagRelDOKey key = new RolePageTagRelDOKey();
					key.setPagetagId(tag.getId());
					key.setRoleId(role.getId());
					rolePageTagRelDOMapper.insert(key);
				}
				for (ACL acl : role.getAcls())
				{
					RoleAclRelDOKey key = new RoleAclRelDOKey();
					key.setAclId(acl.getId());
					key.setRoleId(role.getId());
					roleAclRelDOMapper.insert(key);
				}
			}
		});
		return role;
	}

	@Override
	public Role getRole(String id)
	{
		RoleDO model = this.roleDOMapper.selectByPrimaryKey(id);
		if (model != null)
		{
			Role role = new Role();
			role.setId(model.getId());
			role.setRoleName(model.getRolename());
			role.setDes(model.getDes());
			
			List<PageTagDO> models = this.pageTagDOMapper.findByRoleId(id);
			if (!CollectionUtils.isEmpty(models))
			{
				Set<PageTag> tags = new LinkedHashSet<PageTag>();
				for (PageTagDO m : models)
				{
					PageTag tag = new PageTag();
					tag.setId(m.getId());
					tag.setPagetagGroup((PagetagGroup)EnumUtils.getByCode(m.getPagetagGroup(), PagetagGroup.class));
					tag.setSequence(m.getSequence());
					tag.setTitle(m.getTitle());
					tag.setUrl(m.getUrl());
					tags.add(tag);
				}
				role.setPageTags(tags);
			}
			else
			{
				throw new IllegalArgumentException();
			}
			
			List<AclDO> aclDOs = this.aclDOMapper.findByRoleId(role.getId());
			if (!CollectionUtils.isEmpty(aclDOs))
			{
				Set<ACL> acls = new LinkedHashSet<ACL>();
				for (AclDO aclDO : aclDOs)
				{
					ACL acl = new ACL();
					acl.setAclGroup((ACLGroup)EnumUtils.getByCode(aclDO.getAclGroup(),ACLGroup.class));
					acl.setDes(aclDO.getDes());
					acl.setId(aclDO.getId());
					acl.setTitle(aclDO.getTitle());
					acls.add(acl);
				}
				role.setAcls(acls);
			}
			else
			{
				throw new IllegalArgumentException();
			}
			return role;
		}
		return null;
	}

	@Override
	public Role updateRole(final Role role)
	{
		Assert.notNull(role);
		Assert.notEmpty(role.getPageTags());
		Assert.notEmpty(role.getAcls());
		final RoleDO model = this.roleDOMapper.selectByPrimaryKey(role.getId());
		if (model == null)
		{
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult()
		{
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status)
			{
				model.setDes(role.getDes());
				model.setRolename(role.getRoleName());
				roleDOMapper.updateByPrimaryKeyWithBLOBs(model);
				
				rolePageTagRelDOMapper.deleteByRoleId(role.getId());
				roleAclRelDOMapper.deleteByRoleId(role.getId());
				
				for (ACL acl : role.getAcls())
				{
					RoleAclRelDOKey key  =new RoleAclRelDOKey();
					key.setAclId(acl.getId());
					key.setRoleId(role.getId());
					roleAclRelDOMapper.insert(key);
				}
				for (PageTag tag : role.getPageTags())
				{
					RolePageTagRelDOKey key = new RolePageTagRelDOKey();
					key.setPagetagId(tag.getId());
					key.setRoleId(role.getId());
					rolePageTagRelDOMapper.insert(key);
				}
			}
		});
		return role;
	}

	@Override
	public Set<Role> getRoles()
	{
		List<String> ids = this.roleDOMapper.findAllIds();
		if (!CollectionUtils.isEmpty(ids))
		{
			Set<Role> roles = new LinkedHashSet<Role>();
			for (String id : ids)
			{
				Role role = this.getRole(id);
				roles.add(role);
			}
			return roles;
		}
		return null;
	}

}
