/**
 * 
 */
package uk.co.quidos.gdsap.framework.authority.services.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import uk.co.quidos.gdsap.framework.authority.enums.ACLGroup;
import uk.co.quidos.gdsap.framework.authority.persistence.AclDOMapper;
import uk.co.quidos.gdsap.framework.authority.persistence.RoleAclRelDOMapper;
import uk.co.quidos.gdsap.framework.authority.persistence.object.AclDO;
import uk.co.quidos.gdsap.framework.authority.services.ACLServiceMgr;
import uk.co.quidos.gdsap.framework.authority.ACL;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.sys.lang.enums.EnumUtils;
import uk.co.quidos.gdsap.framework.sys.services.AbstractServiceMgr;

/**
 * @author peng.shi
 */
@Service("aclServiceMgr")
public class ACLServiceMgrImpl extends AbstractServiceMgr implements ACLServiceMgr
{
	private static final long serialVersionUID = 3673207143418771729L;
	
	private AclDOMapper aclDOMapper;
	private RoleAclRelDOMapper roleAclRelDOMapper;
	
	@Autowired
	public void setRoleAclRelDOMapper(RoleAclRelDOMapper roleAclRelDOMapper)
	{
		this.roleAclRelDOMapper = roleAclRelDOMapper;
	}
	@Autowired
	public void setAclDOMapper(AclDOMapper aclDOMapper)
	{
		this.aclDOMapper = aclDOMapper;
	}

	@Override
	public ACL addAcl(final ACL acl) throws ObjectDuplicateException
	{
		Assert.notNull(acl);
		AclDO model = this.aclDOMapper.selectByPrimaryKey(acl.getId());
		if (model != null)
		{
			throw new ObjectDuplicateException();
		}
		this.execute(new TransactionCallbackWithoutResult()
		{
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status)
			{
				AclDO temp = new AclDO();
				temp.setId(acl.getId());
				temp.setAclGroup(acl.getAclGroup().getCode());
				temp.setDes(acl.getDes());
				temp.setTitle(acl.getTitle());
				aclDOMapper.insert(temp);
			}
		});
		return acl;
	}

	@Override
	public ACL updateAcl(final ACL acl)
	{
		Assert.notNull(acl);
		final AclDO tmp = this.aclDOMapper.selectByPrimaryKey(acl.getId());
		if (tmp == null)
		{
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult()
		{
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status)
			{
				tmp.setAclGroup(acl.getAclGroup().getCode());
				tmp.setDes(acl.getDes());
				tmp.setTitle(acl.getTitle());
				aclDOMapper.updateByPrimaryKey(tmp);
			}
		});
		return acl;
	}

	@Override
	public void deleteAcl(final ACL acl)
	{
		Assert.notNull(acl);
		this.execute(new TransactionCallbackWithoutResult()
		{
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status)
			{
				aclDOMapper.deleteByPrimaryKey(acl.getId());
				roleAclRelDOMapper.deleteByAclId(acl.getId());
			}
		});
	}

	@Override
	public Map<ACLGroup,List<ACL>> getAcls()
	{
		List<AclDO> aclDOs = this.aclDOMapper.findAll();
		if (!CollectionUtils.isEmpty(aclDOs))
		{
			Map<ACLGroup,List<ACL>> datas = new LinkedHashMap<ACLGroup, List<ACL>>();
			for (ACLGroup group : ACLGroup.values())
			{
				datas.put(group, new ArrayList<ACL>());
			}
			for (AclDO aclDO : aclDOs)
			{
				ACLGroup aclGroup = (ACLGroup)EnumUtils.getByCode(aclDO.getAclGroup(), ACLGroup.class);
				ACL acl = new ACL();
				acl.setAclGroup(aclGroup);
				acl.setDes(aclDO.getDes());
				acl.setId(aclDO.getId());
				acl.setTitle(aclDO.getTitle());
				datas.get(aclGroup).add(acl);
			}
			return datas;
		}
		return null;
	}
	@Override
	public ACL getAcl(String id) {
		AclDO model = this.aclDOMapper.selectByPrimaryKey(id);
		if (model != null)
		{
			ACL acl = new ACL();
			acl.setId(model.getId());
			acl.setAclGroup((ACLGroup)EnumUtils.getByCode(model.getAclGroup(), ACLGroup.class));
			acl.setTitle(model.getTitle());
			acl.setDes(model.getDes());
			return acl;
		}
		return null;
	}
	
}
