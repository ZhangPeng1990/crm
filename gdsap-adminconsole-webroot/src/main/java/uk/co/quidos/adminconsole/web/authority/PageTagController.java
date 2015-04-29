package uk.co.quidos.adminconsole.web.authority;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uk.co.quidos.adminconsole.web.BaseController;
import uk.co.quidos.dal.BaseConditionVO;
import uk.co.quidos.gdsap.framework.authority.PageTag;
import uk.co.quidos.gdsap.framework.authority.enums.PagetagGroup;
import uk.co.quidos.gdsap.framework.authority.services.PageTagServiceMgr;

@Controller
@RequestMapping("/pageTag/")
public class PageTagController extends BaseController{

	@Autowired
	PageTagServiceMgr pageTagServiceMgr;
	
	private List<PagetagGroup> getGroups()
	{
		PagetagGroup[] pgs = PagetagGroup.values();
		if(pgs != null && pgs.length > 0)
		{
			List<PagetagGroup> listpgs = new ArrayList<PagetagGroup>();
			for(PagetagGroup pg : pgs)
			{
				listpgs.add(pg);
			}
			return listpgs;
		}
		return null;
	}
	
	@RequestMapping("index")
	public String index(Model model)
	{
		List<PagetagGroup> listpgs = getGroups();
		model.addAttribute("groups",listpgs);
		return "/authority/pageTag/index";
	}
	
	@RequestMapping("list/{group}")
	public String listByGroup(BaseConditionVO object, Model model,@PathVariable("group") PagetagGroup group)
	{
		Map<PagetagGroup,Set<PageTag>> map = pageTagServiceMgr.getPageTags();
		Set<PageTag> setPageTag = map.get(group);
		int totalCount = 0;
		if(setPageTag != null)
		{
			totalCount = setPageTag.size();
		}
		model.addAttribute("group",group);
		model.addAttribute("setPageTag",setPageTag);
		model.addAttribute("pageSize",object.getPageSize());
		model.addAttribute("totalCount",totalCount);
		return "/authority/pageTag/listIndex";
	}
	
	@RequestMapping("delete/{id}")
	public ModelAndView delete(@PathVariable("id") String id)
	{
		pageTagServiceMgr.deletePageTag(id);
		return ajaxDoneSuccess("Success");
	}
	
	@RequestMapping("add")
	public String add(Model model)
	{
		List<PagetagGroup> listpgs = getGroups();
		model.addAttribute("groups",listpgs);
		return "/authority/pageTag/add";
	}
	
	@RequestMapping("insert")
	public ModelAndView insert(PageTag pageTag)
	{
		pageTagServiceMgr.addPageTag(pageTag);
		return ajaxDoneSuccess("Success");
	}
	
	@RequestMapping("skipUpdate/{id}")
	public String skipUpdate(@PathVariable("id") String id , Model model)
	{
		PageTag pageTag = pageTagServiceMgr.getPageTag(id);
		model.addAttribute("pageTag",pageTag);
		List<PagetagGroup> listpgs = getGroups();
		model.addAttribute("groups",listpgs);
		return "/authority/pageTag/edit";
	}
	
	@RequestMapping("update")
	public ModelAndView update(@RequestParam("id") String id,PageTag pageTag)
	{
		PageTag pt = pageTagServiceMgr.getPageTag(id);
		pt.setPagetagGroup(pageTag.getPagetagGroup());
		pt.setSequence(pageTag.getSequence());
		pt.setTitle(pageTag.getTitle());
		pt.setUrl(pageTag.getUrl());
		pageTagServiceMgr.updatePageTag(pt);
		return ajaxDoneSuccess("Success");
	}
}
