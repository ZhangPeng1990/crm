package uk.co.quidos.adminconsole.web.authority;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import uk.co.quidos.adminconsole.web.BaseController;
import uk.co.quidos.dal.BaseConditionVO;
import uk.co.quidos.gdsap.framework.authority.ACL;
import uk.co.quidos.gdsap.framework.authority.PageTag;
import uk.co.quidos.gdsap.framework.authority.Role;
import uk.co.quidos.gdsap.framework.authority.enums.PagetagGroup;
import uk.co.quidos.gdsap.framework.authority.enums.ACLGroup;
import uk.co.quidos.gdsap.framework.authority.services.ACLServiceMgr;
import uk.co.quidos.gdsap.framework.authority.services.PageTagServiceMgr;
import uk.co.quidos.gdsap.framework.authority.services.RoleServiceMgr;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;


@Controller
@RequestMapping("/role/")
public class RoleController extends BaseController{

	@Autowired
	RoleServiceMgr roleServiceMgr;
	@Autowired
	PageTagServiceMgr pageTagServiceMgr;
	@Autowired
	ACLServiceMgr aclServiceMgr;
	
	@RequestMapping("index")
	public String index(Model model, BaseConditionVO object)
	{
		Set<Role> roles = roleServiceMgr.getRoles();
		model.addAttribute("roles",roles);
		
		int totalCount = roles != null ? roles.size() : 0;
		model.addAttribute("totalCount",totalCount);
		model.addAttribute("pageSize",object.getPageSize());
		
		return "/authority/role/index";
	}
	
	@RequestMapping("add")
	public String add(Model model)
	{
		//获取全部PsgeTag
		PagetagGroup[] pgs = PagetagGroup.values();
		Map<PagetagGroup, Set<PageTag>> map = pageTagServiceMgr.getPageTags();
		Set<PageTag> allPsgeTags = new LinkedHashSet<PageTag>();
		for(PagetagGroup pg : pgs)
		{
			Set<PageTag> setPageTags = map.get(pg);
			allPsgeTags.addAll(setPageTags);
		}
		model.addAttribute("allPsgeTags", allPsgeTags);
		
		//获取全部ACL
		ACLGroup[] aclGroups = ACLGroup.values();
		Map<ACLGroup, List<ACL>> acMap = aclServiceMgr.getAcls();
		Set<ACL> allAcls = new LinkedHashSet<ACL>();
		for(ACLGroup aclGroup : aclGroups)
		{
			List<ACL> listacl = acMap.get(aclGroup);
			allAcls.addAll(listacl);
		}
		model.addAttribute("allAcls", allAcls);
		
		return "/authority/role/add";
	}
	
	@RequestMapping("insert")
	public ModelAndView insert(@ModelAttribute("form")RoleForm form)
	{
		try {
			Role role = form.getRole();
			String stringPageTags = form.getPageTags();
			String pageTagIds[] = stringPageTags.split(",");
			
			Set<PageTag> listPageTag = new LinkedHashSet<PageTag>();
			if(pageTagIds != null && pageTagIds.length > 0)
			{
				for(String id : pageTagIds)
				{
					PageTag p = pageTagServiceMgr.getPageTag(id);
					listPageTag.add(p);
				}
			}
			
			role.setPageTags(listPageTag);
			
			String aclString = form.getAcls();
			String[] aclIds = aclString.split(",");
			Set<ACL> listAcl = new LinkedHashSet<ACL>();
			if(aclIds != null && aclIds.length > 0)
			{
				for(String id : aclIds)
				{
					ACL a = aclServiceMgr.getAcl(id);
					listAcl.add(a);
				}
			}
			
			role.setAcls(listAcl);
			
			roleServiceMgr.addRole(role);
		} catch (ObjectDuplicateException e) {
			e.printStackTrace();
		}
		return ajaxDoneSuccess("Success");
	}
	
	@RequestMapping("skipUpdate/{id}")
	public String skipUpdate(@PathVariable("id") String id , Model model)
	{
		Role role = roleServiceMgr.getRole(id);
		model.addAttribute("role",role);
		
		Set<PageTag> setPageTag = role.getPageTags();
		
		//获取全部PsgeTag
		PagetagGroup[] pgs = PagetagGroup.values();
		Map<PagetagGroup, Set<PageTag>> map = pageTagServiceMgr.getPageTags();
		Set<PageTag> allPsgeTags = new LinkedHashSet<PageTag>();//allPsgeTags是全部的PsgeTag
		for(PagetagGroup pg : pgs)
		{
			Set<PageTag> setPageTags = map.get(pg);
			allPsgeTags.addAll(setPageTags);
		}
		
		String pageTagIds = "";
		for(PageTag p : setPageTag)
		{
			pageTagIds += p.getId() + ",";
			if(allPsgeTags.contains(p))
			{
				allPsgeTags.remove(p);
			}
		}
		
		model.addAttribute("allPsgeTags", allPsgeTags);
		model.addAttribute("pageTagIds", pageTagIds);
		
		Set<ACL> setAcl = role.getAcls();
		
		//获取全部ACL
		ACLGroup[] aclGroups = ACLGroup.values();
		Map<ACLGroup, List<ACL>> acMap = aclServiceMgr.getAcls();
		Set<ACL> allAcls = new LinkedHashSet<ACL>();
		for(ACLGroup aclGroup : aclGroups)
		{
			List<ACL> listacl = acMap.get(aclGroup);
			allAcls.addAll(listacl);
		}
		
		String aclIds = "";
		for(ACL a : setAcl)
		{
			aclIds += a.getId() + ",";
			if(allAcls.contains(a))
			{
				allAcls.remove(a);
			}
		}
		
		model.addAttribute("allAcls", allAcls);
		model.addAttribute("aclIds", aclIds);
		
		return "/authority/role/edit";
	}
	
	@RequestMapping("update")
	public ModelAndView update(@ModelAttribute("form")RoleForm form)
	{
		Role role = form.getRole();
		String stringPageTags = form.getPageTags();
		String pageTagIds[] = stringPageTags.split(",");
		
		List<PageTag> listPageTag = new LinkedList<PageTag>();
		if(pageTagIds != null && pageTagIds.length > 0)
		{
			for(String id : pageTagIds)
			{
				PageTag p = pageTagServiceMgr.getPageTag(id);
				listPageTag.add(p);
			}
		}
		
		if(!CollectionUtils.isEmpty(listPageTag))
		{
			Set<PageTag> pageTagSet = new LinkedHashSet<PageTag>();
			pageTagSet.addAll(listPageTag);
			role.setPageTags(pageTagSet);
		}
		
		String aclString = form.getAcls();
		String[] aclIds = aclString.split(",");
		List<ACL> listAcl = new LinkedList<ACL>();
		if(aclIds != null && aclIds.length > 0)
		{
			for(String id : aclIds)
			{
				ACL a = aclServiceMgr.getAcl(id);
				listAcl.add(a);
			}
		}
		
		if(!CollectionUtils.isEmpty(listAcl))
		{
			Set<ACL> aclSet = new LinkedHashSet<ACL>();
			aclSet.addAll(listAcl);
			role.setAcls(aclSet);
		}
		
		roleServiceMgr.updateRole(role);
		return ajaxDoneSuccess("Success");
	}
}
