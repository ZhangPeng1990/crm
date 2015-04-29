/**
 * 
 */
package uk.co.quidos.gdsap.framework.authority.services.impl;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.util.CollectionUtils;

import uk.co.quidos.gdsap.framework.sys.lang.enums.EnumUtils;
import uk.co.quidos.dal.common.sequence.UUIDGenerator;
import uk.co.quidos.gdsap.framework.authority.PageTag;
import uk.co.quidos.gdsap.framework.authority.enums.PagetagGroup;
import uk.co.quidos.gdsap.framework.authority.persistence.PageTagDOMapper;
import uk.co.quidos.gdsap.framework.authority.persistence.RolePageTagRelDOMapper;
import uk.co.quidos.gdsap.framework.authority.persistence.object.PageTagDO;
import uk.co.quidos.gdsap.framework.authority.services.PageTagServiceMgr;
import uk.co.quidos.gdsap.framework.sys.services.AbstractServiceMgr;

/**
 * @author peng.shi
 */
@Service("pageTagServiceMgr")
public class PageTagServiceMgrImpl extends AbstractServiceMgr implements PageTagServiceMgr
{
	private static final long serialVersionUID = -4741889828666735471L;
	
	private PageTagDOMapper pageTagDOMapper;
	
	private RolePageTagRelDOMapper rolePageTagRelDOMapper;
	
	@Autowired
	public void setPageTagDOMapper(PageTagDOMapper pageTagDOMapper)
	{
		this.pageTagDOMapper = pageTagDOMapper;
	}
	
	@Autowired
	public void setRolePageTagRelDOMapper(RolePageTagRelDOMapper rolePageTagRelDOMapper) {
		this.rolePageTagRelDOMapper = rolePageTagRelDOMapper;
	}



	@Override
	public PageTag addPageTag(final PageTag pageTag)
	{
		pageTag.validate();
		this.execute(new TransactionCallbackWithoutResult()
		{
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status)
			{
				pageTag.setId(UUIDGenerator.nextId());
				PageTagDO model = new PageTagDO();
				model.setId(pageTag.getId());
				model.setPagetagGroup(pageTag.getPagetagGroup().getCode());
				model.setSequence(pageTag.getSequence());
				model.setTitle(pageTag.getTitle());
				model.setUrl(pageTag.getUrl());
				pageTagDOMapper.insert(model);
			}
		});
		return pageTag;
	}

	@Override
	public void deletePageTag(final String id)
	{
		this.execute(new TransactionCallbackWithoutResult()
		{

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pageTagDOMapper.deleteByPrimaryKey(id);
				rolePageTagRelDOMapper.deleteByPageTagId(id);
			}
			
		});
	}
	
	@Override
	public PageTag updatePageTag(final PageTag pageTag)
	{
		pageTag.validate();
		final PageTagDO tmpModel = this.pageTagDOMapper.selectByPrimaryKey(pageTag.getId());
		if (tmpModel == null)
		{
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult()
		{
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status)
			{
				tmpModel.setPagetagGroup(pageTag.getPagetagGroup().getCode());
				tmpModel.setSequence(pageTag.getSequence());
				tmpModel.setTitle(pageTag.getTitle());
				tmpModel.setUrl(pageTag.getUrl());
				pageTagDOMapper.updateByPrimaryKey(tmpModel);
			}
		});
		return pageTag;
	}

	@Override
	public PageTag getPageTag(String id)
	{
		PageTagDO model = this.pageTagDOMapper.selectByPrimaryKey(id);
		if (model != null)
		{
			PageTag pageTag = new PageTag();
			pageTag.setId(model.getId());
			pageTag.setPagetagGroup((PagetagGroup)EnumUtils.getByCode(model.getPagetagGroup(), PagetagGroup.class));
			pageTag.setSequence(model.getSequence());
			pageTag.setTitle(model.getTitle());
			pageTag.setUrl(model.getUrl());
			return pageTag;
		}
		return null;
	}

	@Override
	public Map<PagetagGroup, Set<PageTag>> getPageTags()
	{
		PagetagGroup[] groups = PagetagGroup.values();
		Map<PagetagGroup, Set<PageTag>> maps = new LinkedHashMap<PagetagGroup, Set<PageTag>>();
		
		for (PagetagGroup g : groups)
		{
			Set<PageTag> tags = maps.get(g);
			if (tags == null)
			{
				tags = new LinkedHashSet<PageTag>();
				maps.put(g, tags);
			}
			List<PageTagDO> models = this.pageTagDOMapper.findByGroup(g.getCode());
			if (!CollectionUtils.isEmpty(models))
			{
				for (PageTagDO model : models)
				{
					PageTag p = new PageTag();
					p.setId(model.getId());
					p.setPagetagGroup((PagetagGroup)EnumUtils.getByCode(model.getPagetagGroup(), PagetagGroup.class));
					p.setSequence(model.getSequence());
					p.setTitle(model.getTitle());
					p.setUrl(model.getUrl());
					tags.add(p);
				}
			}
		}
		return maps;
	}
}
