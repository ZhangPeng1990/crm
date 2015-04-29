package uk.co.quidos.adminconsole.web.authority;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import uk.co.quidos.adminconsole.web.BaseController;
import uk.co.quidos.dal.BaseConditionVO;
import uk.co.quidos.gdsap.framework.authority.ACL;
import uk.co.quidos.gdsap.framework.authority.enums.ACLGroup;
import uk.co.quidos.gdsap.framework.authority.services.ACLServiceMgr;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;

@Controller
@RequestMapping("/authority/acl")
public class AclController extends BaseController{
	
	@Autowired
    ACLServiceMgr aclMgr;
	
	private List<ACLGroup> getGroups()
	{
		ACLGroup[] acls = ACLGroup.values();
		if(acls != null && acls.length > 0)
		{
			List<ACLGroup> listAcl = new ArrayList<ACLGroup>();
			for(ACLGroup acl : acls)
			{
				listAcl.add(acl);
			}
			return listAcl;
		}
		return null;
	}
	
	@RequestMapping("/index")
	public String index(Model model)
	{
		List<ACLGroup> listAcl = getGroups();
		model.addAttribute("groups",listAcl);
		return "/authority/acl/index";
	}
	
	@RequestMapping("/add")
	public String addAcl(Model model){
		List<ACLGroup> listAcl = getGroups();
		model.addAttribute("groups",listAcl);
		return "/authority/acl/add";
	}
	
	@RequestMapping("/insert")
	public ModelAndView insertAcl(ACL acl){
	    try {
			aclMgr.addAcl(acl);
		} catch (ObjectDuplicateException e) {
			e.printStackTrace();
			return ajaxDoneError("exists");
		}
		return ajaxDoneSuccess("Success");
	}
	
	@RequestMapping("/list/{group}")
	public String listByGroup(BaseConditionVO object, Model model,@PathVariable("group") ACLGroup group)
	{
		Map<ACLGroup,List<ACL>> acl=aclMgr.getAcls();
		List<ACL> list = acl.get(group);
		model.addAttribute("acl",list);
		int totalCount = 0;
		if(list != null)
		{
			totalCount = list.size();
		}
		model.addAttribute("group",group);
		model.addAttribute("acl",list);
		model.addAttribute("pageSize",object.getPageSize());
		model.addAttribute("totalCount",totalCount);
		return "/authority/acl/listIndex";
	}
	
	@RequestMapping("/skipUpdate/{id}")
	public String skipUpdate(@PathVariable("id") String id , Model model)
	{
		ACL acl = aclMgr.getAcl(id);
		model.addAttribute("acl",acl);
		List<ACLGroup> listAcl = getGroups();
		model.addAttribute("groups",listAcl);
		return "/authority/acl/edit";
	}
	
	@RequestMapping("/update")
	public ModelAndView update(ACL acl)
	{
		aclMgr.updateAcl(acl);
		return ajaxDoneSuccess("Success");
	}
}
